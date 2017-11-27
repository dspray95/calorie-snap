package uk.ac.yorksj.spray.david.caloriesnap.activity.listener;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import uk.ac.yorksj.spray.david.caloriesnap.R;
import uk.ac.yorksj.spray.david.caloriesnap.activity.CameraActivity;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.FurtherInfoFragment;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.GalleryFragment;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.HelpFragment;

/**
 * Created by david on 12/11/17.
 */

public class GalleryFragmentListener implements View.OnTouchListener{

    private String TAG = "FRAGMENT_LISTENER";
    private String FI_IDENTIFIER;
    private String HELP_IDENTIFIER;

    private float yEventStart;
    private int threshhold = 5;
    private char newScreenDirection;
    private boolean changingScreen = false;

    private FragmentManager fm;
    private GalleryFragment parentGalleryFragment;
    private FurtherInfoFragment furtherInfoFragment;

    private int state = 0;
    private Handler mHandler;
    int kcalCount;

    public GalleryFragmentListener(FragmentManager fm, GalleryFragment parentGalleryFragment, int kcalCount,
                                   char newScreenDirection, String filename){
        this.fm = fm;
        this.parentGalleryFragment = parentGalleryFragment;
        this.kcalCount = kcalCount;
        this.furtherInfoFragment = FurtherInfoFragment.newInstance(kcalCount);
        this.newScreenDirection = newScreenDirection;
        this.FI_IDENTIFIER = filename;
        this.HELP_IDENTIFIER = "help_fragment";
    }


    public void changeScreen(){
        FrameLayout holder = (FrameLayout) parentGalleryFragment.getView().findViewById(R.id.further_info_layout);

        FragmentTransaction ft = fm.beginTransaction();
        switch(state){
            case(0):
                ft.setCustomAnimations(R.anim.slide_from_top, R.anim.slide_to_bottom);
                ft.add(holder.getId(), furtherInfoFragment, FI_IDENTIFIER);
                state = 1;
                newScreenDirection = 'u';
                Log.d(TAG, "Adding further info fragment");
                break;
            case(1):
                ft.setCustomAnimations(R.anim.slide_from_bottom, R.anim.slide_to_top);
                ft.remove(fm.findFragmentByTag(FI_IDENTIFIER));
                newScreenDirection = 'd';
                state = 0;
                Log.d(TAG, "Removing further info fragment");
                break;
            case(2):
                ft.setCustomAnimations(R.anim.slide_from_bottom, R.anim.slide_to_top);
                ft.remove(fm.findFragmentByTag(HELP_IDENTIFIER));
                newScreenDirection = 'd';
                state = 0;
                Log.d(TAG, "Removing help fragment");
        }
        parentGalleryFragment.toggleDetails();
        ft.commit();
        fm.executePendingTransactions();

        //Use a background thread to give the animation a chance to complete before stating to the app
        //that we're done changing screens.
        //This prevents an issue where swiping down from the FI fragment took the user all the way to
        //the camera screen
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                changingScreen = false;
            }
        }, 500);
    }

    public void addHelpFragment(){

        HelpFragment helpFragment = HelpFragment.newInstance("gallery");

        FragmentTransaction ft = fm.beginTransaction();
        FrameLayout holder = (FrameLayout) parentGalleryFragment.getView().findViewById(R.id.further_info_layout);
        ft.setCustomAnimations(R.anim.slide_from_top, R.anim.slide_to_bottom);
        ft.add(holder.getId(), furtherInfoFragment, HELP_IDENTIFIER);
        state = 2;
        newScreenDirection = 'u';
        Log.d(TAG, "Adding further info fragment");
    }

    public FurtherInfoFragment getFurtherInfoFragment(){
        return this.furtherInfoFragment;
    }

    public boolean onTouch(View v, MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                yEventStart = event.getY();
                break;
            case (MotionEvent.ACTION_MOVE):
                if(!changingScreen){
                    if(event.getY() > yEventStart + threshhold && newScreenDirection == 'd'){ //Swipe Down
                        changingScreen = true;
                        changeScreen();
                        Log.d(TAG, "Changing screen d");
                    }
                    else if(event.getY() < yEventStart - (threshhold + 10)){ //Swipe up
                        changingScreen = true;
                        Log.d(TAG, "Changing screen u");
                        if(state == 0){
                            Activity activity = parentGalleryFragment.getActivity();
                            Intent intent = new Intent(activity, CameraActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);
                            activity.overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top);
                            activity.finish();
                        }
                        else{
                            changeScreen();
                        }
                    }
                }
                return true;
            default:
                return true;
        }
        return true;
    }
}
