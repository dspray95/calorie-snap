package uk.ac.yorksj.spray.david.caloriesnap.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Handles the creation of a single bitmap from a filepath
 */

public class BitmapHandler {
    public Bitmap getBitmapFromPath(String path) throws Exception{
        File f = new File(path);
        if(f.exists())
        {
            BitmapFactory.Options iniOptions = new BitmapFactory.Options();
            iniOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(f.getAbsolutePath(), iniOptions);
            iniOptions.inJustDecodeBounds = false;
            iniOptions.inSampleSize = 2;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Bitmap bmp = Bitmap.createBitmap(BitmapFactory.decodeFile(f.getAbsolutePath(), iniOptions));
            return bmp;
        }
        else{
            throw new Exception("IMAGE NOT FOUND"); //TODO handle exception
        }
    }
}
