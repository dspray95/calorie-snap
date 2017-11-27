package uk.ac.yorksj.spray.david.caloriesnap.tools.asynctask;

import android.graphics.Bitmap;

import android.os.AsyncTask;
import android.util.Log;

import uk.ac.yorksj.spray.david.caloriesnap.FoodItem;

/**
 * Created by david on 07/11/17.
 */

public class BitmapAsyncTask extends AsyncTask<Void, Void, Void> {

    public interface AsyncResponse {
        void processFinish(Bitmap bitmap);
    }

    public AsyncResponse delegate = null;
    private Bitmap bitmap;
    private FoodItem foodItem;

    public BitmapAsyncTask(AsyncResponse delegate, FoodItem foodItem){
        super();
        this.delegate = delegate;;
        this.foodItem = foodItem;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            foodItem.createImageBitmap();
            this.bitmap = foodItem.getBitmap();
        }catch(Exception e){
            Log.d("BACKGROUND_THREAD", "ERR_BITMAP");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        delegate.processFinish(this.bitmap);
    }
}
