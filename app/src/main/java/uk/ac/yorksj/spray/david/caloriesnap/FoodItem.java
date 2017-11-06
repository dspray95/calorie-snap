package uk.ac.yorksj.spray.david.caloriesnap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Random;

import static java.lang.System.out;


/**
 * Created by david on 31/10/17.
 */

public class FoodItem implements Serializable, Parcelable{

    private int kcalCount;
    private String imagePath;

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

    public Drawable getImageDrawable(Resources res) throws Exception{
        File f = new File(this.imagePath);
        if(f.exists())
        {
            BitmapFactory.Options iniOptions = new BitmapFactory.Options();
            iniOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(f.getAbsolutePath(), iniOptions);
            int iHeight = iniOptions.outHeight;
            int iWidth = iniOptions.outHeight;
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Bitmap bmp = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()),
                    0, 0, iWidth, iHeight, matrix, true), iWidth/2, iHeight/2, false);
            bmp.compress(Bitmap.CompressFormat.JPEG, 75, out);
            Bitmap compressed = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
            Drawable bitmapDrawable = new BitmapDrawable(compressed);
            bmp.recycle();
            return bitmapDrawable;
        }
        else{
            throw new Exception("IMAGE NOT FOUND");
        }
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
