package uk.ac.yorksj.spray.david.caloriesnap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Random;

import uk.ac.yorksj.spray.david.caloriesnap.asynctask.BackgroundBitmapAsyncTask;


/**
 * Created by david on 31/10/17.
 */

public class FoodItem implements Serializable, Parcelable{

    private int kcalCount;
    private String imagePath;
    private Bitmap bitmap;

    public FoodItem(String imagePath){
        this.imagePath = imagePath;
        this.kcalCount = calculateKcalCount();
    }

    protected int calculateKcalCount(){ //Writing a neural net to do this may take some considerable time...
        return new Random().nextInt(1000);
    }

    public int getKcalCount(){
        return this.kcalCount;
    }

    public String getImagePath(){
        return this.imagePath;
    }

    public void createImageBitmap(Resources res, boolean doInBackground){ //TODO Background threading

        if(doInBackground){
            new BackgroundBitmapAsyncTask(new BackgroundBitmapAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(Bitmap bitmap) {
                    bitmap = bitmap;
                    bitmap.recycle();;

                }
            }, this.imagePath).execute();
        }
    }

    public boolean hasBitmap(){
        return this.bitmap != null ? true, false;
    }

    public Bitmap getBitmap(){
        return this.bitmap;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(kcalCount);
        dest.writeString(imagePath);
    }

    public static final Parcelable.Creator<FoodItem> CREATOR = new Parcelable.Creator<FoodItem>() {
        @Override
        public FoodItem createFromParcel(Parcel in) {
            return new FoodItem(in);
        }

        @Override
        public FoodItem[] newArray(int size) {
            return new FoodItem[size];
        }
    };

    private FoodItem(Parcel in) {
        kcalCount = in.readInt();
        imagePath = in.readString();
    }
}
