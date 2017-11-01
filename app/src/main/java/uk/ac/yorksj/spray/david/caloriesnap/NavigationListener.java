package uk.ac.yorksj.spray.david.caloriesnap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by david on 01/11/17.
 */

public class NavigationListener implements View.OnTouchListener {

    char newScreenDirection;
    boolean changingScreen = false;
    float yEventStart = 0;
    Activity currentActivity;
    Class nextActivity;

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
                    if (event.getY() > yEventStart + 5 && !changingScreen){
                        changingScreen = true;
                        changeScreen();
                        return true;
                    }
                    return true;
                default:
                    return true;
            }
            return true;
    }

    public void changeScreen(){
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
