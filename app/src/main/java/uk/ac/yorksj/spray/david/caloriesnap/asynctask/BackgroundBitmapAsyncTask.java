package uk.ac.yorksj.spray.david.caloriesnap.asynctask;

import android.graphics.Bitmap;

import android.os.AsyncTask;
import android.util.Log;

import uk.ac.yorksj.spray.david.caloriesnap.BitmapHandler;

/**
 * Created by david on 07/11/17.
 */

public class BackgroundBitmapAsyncTask extends AsyncTask<Void, Void, Void> {

    public interface AsyncResponse {
        void processFinish(Bitmap bitmap);
    }

    public AsyncResponse delegate = null;
    String path;
    Bitmap bitmap;

    public BackgroundBitmapAsyncTask(AsyncResponse delegate, String path){
        super();
        this.delegate = delegate;;
        this.path = path;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            this.bitmap = new BitmapHandler().getBitmapFromPath(this.path);
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
