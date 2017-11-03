package uk.ac.yorksj.spray.david.caloriesnap.activity.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import uk.ac.yorksj.spray.david.caloriesnap.R;

/**
 * Created by david on 01/11/17.
 */

public class NavigationListener implements View.OnTouchListener {

    private char newScreenDirection;
    private boolean changingScreen = false;
    private float yEventStart = 0;
    private int threshhold = 2;
    private Activity currentActivity;
    private Class nextActivity;

    public NavigationListener(char newScreenDirection, Activity context, Class nextActivity){
        this.newScreenDirection = newScreenDirection;
        this.currentActivity = context;
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
                        if(event.getY() > yEventStart + threshhold && newScreenDirection == 'u'){
                            changingScreen = true;
                            changeScreen('u');
                            return true;
                        }
                        else if(event.getY() < yEventStart - threshhold && newScreenDirection == 'd'){
                            changingScreen = true;
                            changeScreen('d');
                            return true;
                        }
                    }
                    return true;
                default:
                    return true;
            }
            return true;
    }

    public void changeScreen(char newScreenDirection){
        new Intent();
        Intent mIntent = new Intent(currentActivity, nextActivity);
        currentActivity.startActivity(mIntent);
        switch(newScreenDirection){
            case('u'):
                currentActivity.overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_bottom);
                break;
            case('d'):
                currentActivity.overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top);
                break;
        }
    }
}
