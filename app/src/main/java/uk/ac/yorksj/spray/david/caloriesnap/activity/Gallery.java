package uk.ac.yorksj.spray.david.caloriesnap.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import uk.ac.yorksj.spray.david.caloriesnap.ImageManager;
import uk.ac.yorksj.spray.david.caloriesnap.R;

public class Gallery extends AppCompatActivity {

    ImageManager imageManager;
    String imageManagerFilename;
    boolean imageManagerCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        if(!imageManagerCreated) {
            imageManager = new ImageManager();
            imageManagerCreated = true;
            imageManagerFilename = getExternalFilesDir(null) + "imageManager";
            saveImageManager(imageManager, imageManagerFilename);
        }
        else{
            imageManager = loadImageManager();
        }

        Bundle extras = getIntent().getExtras();
        String fileName = extras.getString("file");


        Toast.makeText(this, fileName, Toast.LENGTH_LONG).show();
    }

    public void saveImageManager(ImageManager imageManager, String filename){
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename)));
            oos.writeObject(imageManager);
            oos.flush();
            oos.close();
        }
        catch(Exception e)
        {
            Log.v("Serialization Error: ",e.getMessage());
            e.printStackTrace();
        }
    }

    public ImageManager loadImageManager(){
        ImageManager imageManager;
        return imageManager;
    }

}
