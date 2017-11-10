package uk.ac.yorksj.spray.david.caloriesnap;

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
            int iHeight = iniOptions.outHeight;
            int iWidth = iniOptions.outWidth;
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Bitmap bmp = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()),
                    0, 0, iWidth, iHeight, matrix, true), iWidth/2, iHeight/2, false);
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, out);
            Bitmap compressed = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
            bmp.recycle();
            return compressed;
        }
        else{
            throw new Exception("IMAGE NOT FOUND");
        }
    }

}
