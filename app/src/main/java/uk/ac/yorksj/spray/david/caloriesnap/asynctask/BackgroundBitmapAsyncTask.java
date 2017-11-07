package uk.ac.yorksj.spray.david.caloriesnap.asynctask;

import android.content.res.Resources;
import android.support.v4.app.Fragment;

import android.os.AsyncTask;

import java.lang.reflect.Array;
import java.util.ArrayList;

import uk.ac.yorksj.spray.david.caloriesnap.FoodItem;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.GalleryFragment;

/**
 * Created by david on 07/11/17.
 */

public class BackgroundBitmapAsyncTask extends AsyncTask<Void, Void, Void> {

    public interface AsyncResponse {
        void processFinish(GalleryFragment fragment, boolean lastItem);
    }

    public AsyncResponse delegate = null;
    public ArrayList<Fragment> fragmentArrayList;
    public ArrayList<FoodItem> foodItemArrayList;
    GalleryFragment fragment;
    public Resources res;
    protected FoodItem foodItem;
    protected boolean lastItem = false;

    public BackgroundBitmapAsyncTask(AsyncResponse delegate, Resources res, FoodItem item, boolean lastItem){
        super();
        this.delegate = delegate;;
        this.lastItem = lastItem;
        this.res = res;
        this.foodItemArrayList = new ArrayList<>();
        this.fragmentArrayList = new ArrayList();
        this.foodItem = item;
    }

    public BackgroundBitmapAsyncTask(AsyncResponse delegate, Resources res, FoodItem item){
        super();
        this.delegate = delegate;;
        this.res = res;
        this.foodItemArrayList = new ArrayList<>();
        this.fragmentArrayList = new ArrayList();
        this.foodItem = item;
    }

    @Override
    protected Void doInBackground(Void... params) {
        this.fragment = GalleryFragment.newInstance(this.foodItem, res);
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        if(lastItem){
            delegate.processFinish(this.fragment, true);
        }
        else{
            delegate.processFinish(this.fragment, false);
        }
    }

    public void addFoodItem(FoodItem item){
        this.foodItemArrayList.add(item);
    }
}
