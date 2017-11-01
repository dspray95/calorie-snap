package uk.ac.yorksj.spray.david.caloriesnap.activity;

import android.content.Context;
import android.content.SharedPreferences;
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

    String IMAGE_MANAGER_CREATED = "IMAGE_MANAGER_CREATED";
    ImageManager imageManager;
    String imageManagerFilename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        //We need an image manager if we dont have one.
        if(checkImageManagerCreated()) {
            imageManager = loadImageManager(imageManagerFilename);


        }
        else{
            imageManager = new ImageManager();
            imageManagerFilename = getExternalFilesDir(null) + "imageManager";
            saveImageManager(imageManager, imageManagerFilename);
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(IMAGE_MANAGER_CREATED, true);
            editor.commit();
        }

        if(getIntent().hasExtra("ADDING_ITEM")){
            Bundle extras = getIntent().getExtras();
            String filename = extras.getString("FILE");
            Toast.makeText(this, filename, Toast.LENGTH_LONG).show();
        }
    }

    public boolean checkImageManagerCreated(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(IMAGE_MANAGER_CREATED, false);
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
