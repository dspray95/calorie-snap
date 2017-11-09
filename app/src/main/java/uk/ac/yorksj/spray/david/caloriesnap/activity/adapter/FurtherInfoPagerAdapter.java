package uk.ac.yorksj.spray.david.caloriesnap.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by david on 09/11/17.
 */

public class FurtherInfoPagerAdapter extends FragmentStatePagerAdapter {

    Fragment fragment;

    public FurtherInfoPagerAdapter(FragmentManager fm, Fragment fragment){
        super(fm);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Fragment getItem(int position){
        return this.fragment;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
