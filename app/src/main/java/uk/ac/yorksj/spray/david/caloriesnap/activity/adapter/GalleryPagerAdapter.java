package uk.ac.yorksj.spray.david.caloriesnap.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import uk.ac.yorksj.spray.david.caloriesnap.FoodItem;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.GalleryFragment;

/**
 * Created by david on 02/11/17.
 */

public class GalleryPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<FoodItem> itemList;

    public GalleryPagerAdapter(FragmentManager fm, ArrayList<FoodItem> itemList) {
        super(fm);
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return GalleryFragment.newInstance(itemList.get(position));
    }
}
