package uk.ac.yorksj.spray.david.caloriesnap.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by david on 02/11/17.
 */

public class GalleryPagerAdapter extends FragmentStatePagerAdapter {

    public ArrayList<Fragment> fragments;
    private static int pos = 0;

    public GalleryPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getItemPosition (Object object)
    {
        return fragments.indexOf(object);
    }
    
    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int pos) {
        return fragments.get(pos);
    }

    public void setFragments(ArrayList<Fragment> fragments){
        this.fragments = fragments;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
