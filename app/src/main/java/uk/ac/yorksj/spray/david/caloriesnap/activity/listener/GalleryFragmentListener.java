package uk.ac.yorksj.spray.david.caloriesnap.activity.listener;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import uk.ac.yorksj.spray.david.caloriesnap.R;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.FurtherInfoFragment;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.GalleryFragment;

/**
 * Created by david on 12/11/17.
 */

public class GalleryFragmentListener implements View.OnTouchListener{

    private String TAG = "FRAGMENT_LISTENER";

    private float yEventStart;
    private int threshhold = 5;
    private char newScreenDirection;
    private boolean changingScreen = false;

    private FragmentManager fm;
    private GalleryFragment parentGalleryFragment;
    private FurtherInfoFragment furtherInfoFragment;
    private String identifier;
    private int state = 0;

    int kcalCount;

    public GalleryFragmentListener(FragmentManager fm, GalleryFragment parentGalleryFragment, int kcalCount,
                                   char newScreenDirection, String filename){
        this.fm = fm;
        this.parentGalleryFragment = parentGalleryFragment;
        this.kcalCount = kcalCount;
        this.furtherInfoFragment = FurtherInfoFragment.newInstance(kcalCount);
        this.newScreenDirection = newScreenDirection;
        this.identifier = filename;
    }


    public void changeScreen(){
        FrameLayout holder = (FrameLayout) parentGalleryFragment.getView().findViewById(R.id.further_info_layout);

        FragmentTransaction ft = fm.beginTransaction();
        switch(state){
            case(0):
                ft.setCustomAnimations(R.anim.slide_from_top, R.anim.slide_to_bottom);
                ft.add(holder.getId(), furtherInfoFragment, identifier);
                state = 1;
                newScreenDirection = 'u';
                Log.d(TAG, "Adding further info fragment");
                break;
            case(1):
                ft.setCustomAnimations(R.anim.slide_from_bottom, R.anim.slide_to_top);
                ft.remove(fm.findFragmentByTag(identifier));
                newScreenDirection = 'd';
                state = 0;
                Log.d(TAG, "Removing further info fragment");
                break;
        }
        parentGalleryFragment.toggleDetails();
        ft.commit();
        fm.executePendingTransactions();

    }

    public boolean onTouch(View v, MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                yEventStart = event.getY();
                break;
            case (MotionEvent.ACTION_MOVE):
                if(!changingScreen){
                    if(event.getY() > yEventStart + threshhold && newScreenDirection == 'd'){
                        Log.d(TAG, "Changing screen d");
                        changeScreen();
                    }
                    else if(event.getY() < yEventStart - threshhold && newScreenDirection == 'u'){
                        Log.d(TAG, "Changing screen u");
                        changeScreen();
                    }
                }
                return true;
            default:
                return true;
        }
        return true;
    }
}
