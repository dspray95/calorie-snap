package uk.ac.yorksj.spray.david.caloriesnap.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
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
            imageManager = loadImageManager(imageManagerFilename);
        }

        Bundle extras = getIntent().getExtras();
        if(extras.getBoolean("ADDING_IMAGE")){
            String filename = extras.getString("FILE");
            Toast.makeText(this, filename, Toast.LENGTH_LONG).show();
        }
    }

    public void saveImageManager(ImageManager imageManager, String filename){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename)));
            oos.writeObject(imageManager);
            oos.flush();
            oos.close();
        }
        catch(Exception e) {
            Log.v("Serialization Error: ",e.getMessage());
            e.printStackTrace();
        }
    }

    public ImageManager loadImageManager(String imageManagerFilename){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(imageManagerFilename));
            Object o = ois.readObject();
            return (ImageManager) o;
        }
        catch(Exception ex) {
            Log.v("Serialization Error: ",ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
}
