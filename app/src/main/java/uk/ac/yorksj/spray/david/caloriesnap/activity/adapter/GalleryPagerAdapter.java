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
import uk.ac.yorksj.spray.david.caloriesnap.asynctask.BackgroundBitmapAsyncTask;

/**
 * Created by david on 02/11/17.
 */

public class GalleryPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<FoodItem> itemList;
    ArrayList<Fragment> fragmentList;
    BackgroundBitmapAsyncTask background;

    public GalleryPagerAdapter(FragmentManager fm, ArrayList<FoodItem> itemList, Resources res) {
        super(fm);

        this.background = new BackgroundBitmapAsyncTask(new BackgroundBitmapAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(ArrayList<Fragment> fragmentArrayList) {
                fragmentList.addAll(fragmentArrayList);
                Log.d("FRAGMENT_ADAPTER", "Async task finished, adding fragments");
                notifyDataSetChanged();
            }
        }, res);

        this.itemList = itemList;
        this.fragmentList = new ArrayList<>();
        for(FoodItem item : itemList){
            if(itemList.indexOf(item) == itemList.size() -1){ //check to see if the item is last in the list
                this.fragmentList.add(GalleryFragment.newInstance(item, res)); //Create the fragment on the ui thread
            }
            else{
                this.background.addFoodItem(item);
            }
        }
        background.execute();
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        //Display the latest added fragment first
        int zeroIndexedSize = fragmentList.size() - 1; //Get the 0 indexed size
        if(zeroIndexedSize > 0){
            return fragmentList.get(zeroIndexedSize - position); //goes through the list from highest to lowest
        }
        else{
            return fragmentList.get(position);
        }
    }

    public void setItemList(ArrayList<FoodItem> itemList){
        this.itemList = itemList;
    }

}
