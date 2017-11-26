package uk.ac.yorksj.spray.david.caloriesnap.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by david on 07/11/17.
 */

public class BitmapHandler {

    public Bitmap getBitmapFromPath(String path) throws Exception{
        File f = new File(path);
        if(f.exists())
        {
            BitmapFactory.Options iniOptions = new BitmapFactory.Options();
            iniOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(f.getAbsolutePath(), iniOptions);
//            int inHeight = iniOptions.outHeight;
//            int inWidth = iniOptions.outWidth;
            iniOptions.inJustDecodeBounds = false;
            iniOptions.inSampleSize = 2;
//            iniOptions.inDensity = in
            Matrix matrix = new Matrix();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Bitmap bmp = Bitmap.createBitmap(BitmapFactory.decodeFile(f.getAbsolutePath(), iniOptions));
//            Bitmap bmp = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeFile(f.getAbsolutePath(), iniOptions),
//                    0, 0, iWidth, iHeight, matrix, true), iWidth, iHeight, false);
//            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            Bitmap compressed = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
//            bmp.recycle();
            return bmp;
        }
        else{
            throw new Exception("IMAGE NOT FOUND");
        }
    }

}
