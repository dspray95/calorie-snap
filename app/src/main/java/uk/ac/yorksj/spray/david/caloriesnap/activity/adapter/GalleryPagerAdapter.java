package uk.ac.yorksj.spray.david.caloriesnap.activity.adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

import uk.ac.yorksj.spray.david.caloriesnap.FoodItem;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.GalleryFragment;

/**
 * Created by david on 02/11/17.
 */

public class GalleryPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<FoodItem> itemList;
    ArrayList<Fragment> fragmentList;

    public GalleryPagerAdapter(FragmentManager fm, ArrayList<FoodItem> itemList, Resources res) {
        super(fm);
        this.itemList = itemList;
        this.fragmentList = new ArrayList<>();
        for(FoodItem item : itemList){
            this.fragmentList.add(GalleryFragment.newInstance(item, res));
        }

    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    public void setItemList(ArrayList<FoodItem> itemList){
        this.itemList = itemList;
    }

}
