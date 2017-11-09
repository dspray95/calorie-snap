package uk.ac.yorksj.spray.david.caloriesnap.activity.listener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    private boolean swappingFragments;
    private float yEventStart = 0;
    private int threshhold = 2;
    private Activity currentActivity;
    private Fragment nextFragment;
    private Fragment startFragment;
    private FragmentManager fm;
    private Class nextActivity;


    public NavigationListener(char newScreenDirection, Fragment startFragment, Fragment endFragment, FragmentManager fm){
        this.newScreenDirection = newScreenDirection;
        this.startFragment = startFragment;
        this.fm = fm;
        this.nextFragment = endFragment;
        this.swappingFragments = true;
    }

    public NavigationListener(char newScreenDirection, Activity context, Class nextActivity){
        this.newScreenDirection = newScreenDirection;
        this.currentActivity = context;
        this.nextActivity = nextActivity;
        this.swappingFragments = false;
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
                            if(swappingFragments){
                                changeFragments('u');
                            }
                            else{
                                changeScreen('u');
                            }
                            return true;
                        }
                        else if(event.getY() < yEventStart - threshhold && newScreenDirection == 'd'){
                            changingScreen = true;
                            if(swappingFragments){
                                changeFragments('d');
                            }
                            else{
                                changeScreen('d');
                            }
                            return true;
                        }
                    }
                    return true;
                default:
                    return true;
            }
            return true;
    }

    public void changeFragments(char newScreenDirection){
        FragmentTransaction ft = fm.beginTransaction();

        Bundle arguments = new Bundle();
        arguments.putBoolean("arg", true);
        nextFragment.setArguments(arguments);
        ft.add(R.id.gallery_layout, nextFragment);
        ft.commit();

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
