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
        void processFinish(ArrayList<Fragment> fragmentArrayList);
    }

    public AsyncResponse delegate = null;
    public ArrayList<Fragment> fragmentArrayList;
    public ArrayList<FoodItem> foodItemArrayList;
    public Resources res;

    public BackgroundBitmapAsyncTask(AsyncResponse delegate, Resources res){
        super();
        this.delegate = delegate;;
        this.res = res;
        this.foodItemArrayList = new ArrayList<>();
        this.fragmentArrayList = new ArrayList();
    }

    @Override
    protected Void doInBackground(Void... params) {
        for(FoodItem item : foodItemArrayList){
            fragmentArrayList.add(GalleryFragment.newInstance(item, res));
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        delegate.processFinish(this.fragmentArrayList);
    }

    public void addFoodItem(FoodItem item){
        this.foodItemArrayList.add(item);
    }
}
