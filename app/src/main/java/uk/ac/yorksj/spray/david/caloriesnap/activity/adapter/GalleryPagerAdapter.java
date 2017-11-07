package uk.ac.yorksj.spray.david.caloriesnap.activity.adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Gallery;

import java.lang.reflect.Array;
import java.util.ArrayList;

import uk.ac.yorksj.spray.david.caloriesnap.FoodItem;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.GalleryFragment;
import uk.ac.yorksj.spray.david.caloriesnap.asynctask.BackgroundBitmapAsyncTask;

/**
 * Created by david on 02/11/17.
 */

public class GalleryPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<FoodItem> itemList;
    ArrayList<GalleryFragment> fragmentList;
    ArrayList<Fragment> fragmentListHolder;
    DetailOnPageChangeListener listener;
    FragmentManager fm;
    BackgroundBitmapAsyncTask background;

    public GalleryPagerAdapter(FragmentManager fm, ArrayList<FoodItem> itemList, Resources res) {
        super(fm);
        this.itemList = itemList;
        this.listener = new DetailOnPageChangeListener();
        this.fragmentList = new ArrayList<>();
        this.fragmentList.add(GalleryFragment.newInstance(itemList.get(itemList.size() - 1), res, true)); //Add the latest item first on the ui thread
        this.fragmentListHolder = new ArrayList<>();
        this.fragmentListHolder.add(null);

        for(FoodItem item : itemList){
            if(itemList.indexOf(item) != itemList.size() -1) { //check to see if the item is not the latest item
                GalleryFragment.newInstance(item, res, false);
            }
        }
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {

        GalleryFragment fragment = fragmentList.get(position);

        if(fragment.hasBitmap()){
            return fragment;
        }
        else if(fragment.trySetBitmap()){
            return fragment;
        }
        else{
            position = this.listener.getCurrentPage();
            return fragmentList.get(position);
        }
    }

    public class DetailOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {

        private int currentPage;

        @Override
        public void onPageSelected(int position) {
            currentPage = position;
        }

        public final int getCurrentPage() {
            return currentPage;
        }
    }
}
