package uk.ac.yorksj.spray.david.caloriesnap.activity.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import uk.ac.yorksj.spray.david.caloriesnap.FoodItem;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.GalleryFragment;
import uk.ac.yorksj.spray.david.caloriesnap.asynctask.BitmapAsyncTask;

/**
 * Created by david on 02/11/17.
 */

public class GalleryPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<FoodItem> itemList;
    ArrayList<GalleryFragment> fragmentList;
    ArrayList<Fragment> fragmentListHolder;
//    DetailOnPageChangeListener listener;

    public GalleryPagerAdapter(FragmentManager fm, final ArrayList<FoodItem> itemList, Resources res) {
        super(fm);
        this.itemList = itemList;
//        this.listener = new DetailOnPageChangeListener();
        this.fragmentList = new ArrayList<>();

        this.fragmentList.add(GalleryFragment.newInstance(itemList.get(itemList.size() - 1), res, true)); //Add the latest item first on the ui thread
        this.itemList.get(itemList.size() - 1).createImageBitmap();
        this.fragmentList.get(0).trySetBitmap();

        this.fragmentListHolder = new ArrayList<>();
        this.fragmentListHolder.add(null);

        for(FoodItem item : itemList){
            if(itemList.indexOf(item) != itemList.size() -1) { //check to see if the item is not the latest item
                fragmentList.add(GalleryFragment.newInstance(item, res, false));
                final int position = itemList.indexOf(item);
                new BitmapAsyncTask(new BitmapAsyncTask.AsyncResponse() {
                    @Override
                    public void processFinish(Bitmap bitmap) {
                        fragmentList.get(position).trySetBitmap();
                    }
                }, item).execute();
                fragmentList.get(itemList.indexOf(item));
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
        fragment.trySetBitmap();
        return fragmentList.get(position);
    }
}
