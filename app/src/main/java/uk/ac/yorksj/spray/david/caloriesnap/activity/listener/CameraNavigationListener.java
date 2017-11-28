package uk.ac.yorksj.spray.david.caloriesnap.activity.listener;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import uk.ac.yorksj.spray.david.caloriesnap.R;
import uk.ac.yorksj.spray.david.caloriesnap.activity.GalleryActivity;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.CameraFragment;

public class NavigationListener implements View.OnTouchListener {

    private char newScreenDirection;
    private boolean changingScreen = false;
    private float yEventStart = 0;
    private int threshhold = 5;
    private Class nextActivity;
    private Activity currentActivity;
    private CameraFragment parentFragment;

    public NavigationListener(char newScreenDirection, CameraFragment parentFragment, Class nextActivity){
        this.newScreenDirection = newScreenDirection;
        this.parentFragment = parentFragment;
        this.nextActivity = nextActivity;
    }

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
                            changeScreen('d');
                        }
                        else if(event.getY() < yEventStart - threshhold && newScreenDirection == 'u'){
                            changeScreen('u');
                        }
                    }
                    return true;
                default:
                    return true;
            }
            return true;
    }

    public void changeScreen(char newScreenDirection){
        Activity activity = parentFragment.getActivity();
        Intent intent = new Intent(activity, GalleryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_bottom);
        activity.finish();
    }
}
