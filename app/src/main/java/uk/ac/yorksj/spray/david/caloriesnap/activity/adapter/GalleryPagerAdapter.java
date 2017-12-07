package uk.ac.yorksj.spray.david.caloriesnap.activity.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import uk.ac.yorksj.spray.david.caloriesnap.FoodItem;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.GalleryFragment;
import uk.ac.yorksj.spray.david.caloriesnap.tools.asynctask.BitmapAsyncTask;

/**
 * Pager Adaptor for the gallery page
 * Handles the cycling of fragments in the gallery
 */

public class GalleryPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<FoodItem> itemList;
    private ArrayList<GalleryFragment> fragmentList;
    private ArrayList<Fragment> fragmentListHolder;

    /**
     * Constructor
     * @param fm parent fragment manager
     * @param itemList list of all fooditems to display in the gallery
     * @param res parent resource object
     */
    public GalleryPagerAdapter(FragmentManager fm, final ArrayList<FoodItem> itemList, Resources res) {
        super(fm);
        this.itemList = itemList;
        //Set up a list of contained fragments
        this.fragmentList = new ArrayList<>();
        this.fragmentList.add(GalleryFragment.newInstance(itemList.get(itemList.size() - 1), res, true)); //Add the latest item first on the ui thread
        this.itemList.get(itemList.size() - 1).createImageBitmap();
        this.fragmentList.get(0).trySetBitmap();

        this.fragmentListHolder = new ArrayList<>();
        this.fragmentListHolder.add(null);

        //Create a gallery fragment for each food item passed into the constructor
        for(FoodItem item : itemList){
            if(itemList.indexOf(item) != itemList.size() -1) { //check to see if the item is not the latest item
                fragmentList.add(GalleryFragment.newInstance(item, res, false));
                final int position = itemList.indexOf(item);
                //Set the fragment images on a background thread
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

    /**
     * Handles the display of a fragment from the list by the position variable
     * @param position current fragment position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        GalleryFragment fragment = fragmentList.get(position);
        fragment.trySetBitmap();
        return fragmentList.get(position);
    }

    /**
     * Recycle bitmaps for each fragment
     * Used for memory management
     */
    public void recycleBitmaps(){
        for(GalleryFragment fragment : fragmentList){
            fragment.recycleBitmap();
        }
    }
}
