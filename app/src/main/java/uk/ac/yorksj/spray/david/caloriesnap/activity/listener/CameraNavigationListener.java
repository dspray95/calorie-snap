package uk.ac.yorksj.spray.david.caloriesnap.activity.listener;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import uk.ac.yorksj.spray.david.caloriesnap.R;
import uk.ac.yorksj.spray.david.caloriesnap.activity.GalleryActivity;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.CameraFragment;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.HelpFragment;

/**
 * Handles swipe navigation for the camera fragament
 */
public class CameraNavigationListener implements View.OnTouchListener {

    private String HELP_IDENTIFIER = "camera";

    private char newScreenDirection;
    private boolean changingScreen = false;
    private float yEventStart = 0;
    private int threshhold = 5;
    private Class nextActivity;
    private Activity currentActivity;
    private CameraFragment parentFragment;
    private FragmentManager fm;
    private int state = 0;

    /**
     * Constructor
     *
     * @param fm parent fragment manager
     * @param newScreenDirection direction of swiping
     * @param parentFragment creator camera fragment
     * @param nextActivity gallery activity
     */
    public CameraNavigationListener(FragmentManager fm, char newScreenDirection, CameraFragment parentFragment, Class nextActivity){
        this.newScreenDirection = newScreenDirection;
        this.parentFragment = parentFragment;
        this.nextActivity = nextActivity;
        this.fm = fm;
    }

    /**
     * Fires when the user taps the screen
     * Checks if the user is swiping down or up then acts accordingly
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
            int action = MotionEventCompat.getActionMasked(event);
            switch (action) {
                case (MotionEvent.ACTION_DOWN):
                    yEventStart = event.getY();
                    break;
                case (MotionEvent.ACTION_MOVE):
                    if(!changingScreen){
                        if(event.getY() > yEventStart + threshhold && newScreenDirection == 'd'){
                            state = 0;
                            changeScreen();
                        }
                        else if(event.getY() < yEventStart - threshhold && newScreenDirection == 'u'){
                            changeScreen();
                        }
                    }
                    return true;
                default:
                    return true;
            }
            return true;
    }

    /**
     * Fired when the user taps the help button
     * Creates and animates in a help fragment for the camera screen
     */
    public void addHelpFragment(){

        HelpFragment helpFragment = HelpFragment.newInstance("camera");

        FragmentTransaction ft = fm.beginTransaction();
        FrameLayout holder = (FrameLayout) parentFragment.getView().findViewById(R.id.camera_help_holder);
        ft.setCustomAnimations(R.anim.slide_from_top, R.anim.slide_to_bottom);
        ft.add(holder.getId(), helpFragment, HELP_IDENTIFIER);
        ft.commit();
        fm.executePendingTransactions();
        parentFragment.getView().findViewById(R.id.button_capture).setVisibility(View.INVISIBLE);
        parentFragment.getView().findViewById(R.id.camera_help).setVisibility(View.INVISIBLE);
        state = 1;
        newScreenDirection = 'u';
    }

    /**
     * Fired from swiping, changes the screen depending on the state of the activity.
     * On state 0 we go to the gallery activity
     * State 1 means that the help fragment is active, so we hide it
     */
    public void changeScreen(){
        switch(state) {
            case 0:
                Activity activity = parentFragment.getActivity();
                Intent intent = new Intent(activity, GalleryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_bottom);
                activity.finish();
                break;
            case 1:
                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(R.anim.slide_from_bottom, R.anim.slide_to_top);
                ft.remove(fm.findFragmentByTag(HELP_IDENTIFIER));
                newScreenDirection = 'd';
                ft.commit();
                fm.executePendingTransactions();
                parentFragment.getView().findViewById(R.id.button_capture).setVisibility(View.VISIBLE);
                parentFragment.getView().findViewById(R.id.camera_help).setVisibility(View.VISIBLE);
                Log.d("CAMERA", "Removing help fragment");
                state = 0;
                break;
        }
    }
}
