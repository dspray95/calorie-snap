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
    ArrayList<Fragment> fragmentListHolder;
    FragmentManager fm;
    BackgroundBitmapAsyncTask background;

    public GalleryPagerAdapter(FragmentManager fm, ArrayList<FoodItem> itemList, Resources res) {
        super(fm);
        final FragmentManager fragmentManager = fm;
        this.itemList = itemList;
        this.fragmentList = new ArrayList<>();
        this.fragmentList.add(GalleryFragment.newInstance(itemList.get(itemList.size() - 1), res)); //Add the latest item first on the ui thread
        this.fragmentListHolder = new ArrayList<>();
        this.fragmentListHolder.add(null);

        for(FoodItem item : itemList){
            if(itemList.indexOf(item) != itemList.size() -1){ //check to see if the item is not the latest item
                boolean isLastItemToAdd = itemList.indexOf(item) == itemList.size() - 2 ? true : false;
                new BackgroundBitmapAsyncTask(new BackgroundBitmapAsyncTask.AsyncResponse() { //create a background task and run on that thread
                    @Override
                    public void processFinish(GalleryFragment fragment, boolean lastItem) {
                        fragmentListHolder.add(fragment);
                        if(lastItem){
                            updateFragments();
                        }
                    }
                }, res, item, isLastItemToAdd).execute();
            }
        }
    }

    public void updateFragments(){
        this.fragmentList.addAll(fragmentListHolder);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        //Display the latest added fragment first
//        int zeroIndexedSize = fragmentList.size() - 1; //Get the 0 indexed size
//        if(zeroIndexedSize > 0){
//            return fragmentList.get(zeroIndexedSize - position); //goes through the list from highest to lowest
//        }
//        else{
//            return fragmentList.get(position);
//        }
        return fragmentList.get(position);
    }
}
