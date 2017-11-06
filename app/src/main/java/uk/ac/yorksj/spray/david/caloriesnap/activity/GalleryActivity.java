package uk.ac.yorksj.spray.david.caloriesnap.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import uk.ac.yorksj.spray.david.caloriesnap.FoodItem;
import uk.ac.yorksj.spray.david.caloriesnap.FoodItemManager;
import uk.ac.yorksj.spray.david.caloriesnap.R;
import uk.ac.yorksj.spray.david.caloriesnap.activity.adapter.GalleryPagerAdapter;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.GalleryFragment;

public class GalleryActivity extends AppCompatActivity implements
        GalleryFragment.OnFragmentInteractionListener{

    String ITEM_MANAGER_CREATED = "ITEM_MANAGER_CREATED";
    FoodItemManager imageManager;
    String imageManagerFilename;
    ArrayList<FoodItem> foodItemList;
    ViewPager viewPager;
    GalleryPagerAdapter galleryPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        imageManagerFilename = getExternalFilesDir(null) + "/item.manager";
        File imageManagerFile = new File(imageManagerFilename);
        //We need an image manager if we dont have one.
        if(imageManagerFile.exists()) {
                imageManager = loadImageManager(imageManagerFilename);
        }
        else{
            imageManager = new FoodItemManager();
            saveImageManager(imageManager, imageManagerFilename);
            SharedPreferences sharedPref = getSharedPreferences("ITEM_MANAGER", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(ITEM_MANAGER_CREATED, true);
            editor.commit();
        }

        //Check if we have an item to add
        if(getIntent().hasExtra("ADDING_ITEM")){
            Bundle extras = getIntent().getExtras();
            String filename = extras.getString("FILE");
            imageManager.addFoodItem(new FoodItem(filename));
            saveImageManager(imageManager, imageManagerFilename); //Make sure to save the new FoodItemManager
            Toast.makeText(this, filename, Toast.LENGTH_LONG).show();
        }
        //Build the arraylist of fragments out of our items
        this.foodItemList = new ArrayList<>();
        for(FoodItem item  : imageManager.getFoodItems()){
            if(item != null){
                foodItemList.add(item);
            }
        }
        //Send fragments to pager adapter
        this.galleryPagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager(),
                foodItemList, getResources());
        viewPager = (ViewPager)findViewById(R.id.gallery_view_pager);
        viewPager.setAdapter(this.galleryPagerAdapter);
    }

    public void saveImageManager(FoodItemManager imageManager, String filename){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename)));
            oos.writeObject(imageManager);
            oos.flush();
            oos.close();
        }
        catch(Exception e) {
            Log.v("Serialization Error: ",e.getMessage());
//            e.printStackTrace();
        }
    }

    public FoodItemManager loadImageManager(String imageManagerFilename){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(imageManagerFilename));
            Object o = ois.readObject();
            return (FoodItemManager) o;
        }
        catch(Exception ex) {
//            Log.v("Serialization Error: ",ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
