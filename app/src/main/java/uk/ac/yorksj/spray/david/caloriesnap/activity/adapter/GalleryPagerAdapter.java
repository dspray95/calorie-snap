package uk.ac.yorksj.spray.david.caloriesnap.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uk.ac.yorksj.spray.david.caloriesnap.FragmentGallery;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.GalleryFragment;

/**
 * Created by david on 02/11/17.
 */

public class GalleryPagerAdapter extends FragmentPagerAdapter {

    public ArrayList<Fragment> fragments;

    public GalleryPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }


    @Override
    public int getItemPosition (Object object)
    {
        return fragments.indexOf(object);
    }

    public Object instantiateItem (ViewGroup container, int position)
    {
        View fragmentView = fragments.get(position).getView();
        container.addView (fragmentView);
        return fragmentView;
    }

    @Override
    public void destroyItem (ViewGroup container, int position, Object object)
    {
        container.removeView (fragments.get (position).getView());
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
