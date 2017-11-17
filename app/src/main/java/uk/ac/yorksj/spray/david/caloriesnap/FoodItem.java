package uk.ac.yorksj.spray.david.caloriesnap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.hardware.camera2.params.RggbChannelVector;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.Random;


/**
 * Created by david on 31/10/17.
 */

public class FoodItem implements Serializable, Parcelable{

    private int kcalCount;
    private String imagePath;
    private transient Bitmap bitmap;

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

    public void writeKCalTotal(SharedPreferences prefs, String TAG){
        int val = prefs.getInt(TAG, Context.MODE_PRIVATE);
//        if(val != null){
            val += this.kcalCount;
//        }
//        else{
//            val = this.kcalCount;
//        }
//        val += this.kcalCount;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(TAG, val);
        editor.commit();
    }

    public String getImagePath(){
        return this.imagePath;
    }

    public void createImageBitmap(){
        try {
            bitmap = new BitmapHandler().getBitmapFromPath(this.imagePath);
        }catch(Exception e){
            Log.d("FOREGROUND_THREAD", "ERR_BITMAP");
        }
   }

    public boolean hasBitmap(){
        return this.bitmap != null ? true : false;
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
