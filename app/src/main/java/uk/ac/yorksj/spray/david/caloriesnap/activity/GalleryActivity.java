package uk.ac.yorksj.spray.david.caloriesnap.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;

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
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.FurtherInfoFragment;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.GalleryFragment;
import uk.ac.yorksj.spray.david.caloriesnap.activity.fragments.HelpFragment;

public class GalleryActivity extends LocalizationActivity implements
        GalleryFragment.OnFragmentInteractionListener,
        FurtherInfoFragment.OnFragmentInteractionListener,
        HelpFragment.OnFragmentInteractionListener{

    private String ITEM_MANAGER_CREATED = "ITEM_MANAGER_CREATED";
    private FoodItemManager imageManager;
    private String imageManagerFilename;
    private ArrayList<FoodItem> foodItemList;
    private ViewPager viewPager;
    private GalleryPagerAdapter galleryPagerAdapter;
    private Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        imageManagerFilename = getExternalFilesDir(null) + "/item.manager";
        File imageManagerFile = new File(imageManagerFilename);
        //We need an image manager if we dont have one.
        String TAG_TOTAL_KCAL = getResources().getString(R.string.tag_total_kcal);
        if(imageManagerFile.exists()) {
                imageManager = loadImageManager(imageManagerFilename);
                if(null == imageManager){ //if loading fails, make a new manager
                    imageManager = new FoodItemManager(TAG_TOTAL_KCAL);
                    saveImageManager(imageManager, imageManagerFilename);
                }
        }
        else{
            imageManager = new FoodItemManager(TAG_TOTAL_KCAL);
            saveImageManager(imageManager, imageManagerFilename);
            SharedPreferences sharedPref = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(ITEM_MANAGER_CREATED, true);
            editor.commit();
        }

        //Check if we have an item to add
        if(getIntent().hasExtra("ADDING_ITEM")){
            Bundle extras = getIntent().getExtras();
            String filename = extras.getString("FILE");
            imageManager.addFoodItem(new FoodItem(filename), getSharedPreferences(TAG_TOTAL_KCAL, Context.MODE_PRIVATE));
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
        //Give the application a chance to load in the bitmaps before setting the fragments
        mHandler.postDelayed(new Runnable() {
            public void run() {
                viewPager = (ViewPager)findViewById(R.id.gallery_view_pager);
                viewPager.setAdapter(galleryPagerAdapter);
                viewPager.setOffscreenPageLimit(4); //fixes further info fragment getting stuck
            }
        }, 3000);
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

    public void onPause() {
        super.onPause();
        galleryPagerAdapter.recycleBitmaps();
    }

    public void onResume(){
        super.onResume();
//        galleryPagerAdapter.trySetBitmaps();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
