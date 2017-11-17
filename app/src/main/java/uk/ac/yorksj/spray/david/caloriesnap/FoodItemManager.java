package uk.ac.yorksj.spray.david.caloriesnap;

import android.content.SharedPreferences;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by david on 31/10/17.
 */

public class FoodItemManager implements Serializable{

    protected FoodItem[] images;
    private SharedPreferences prefs;
    private String totalKcalTag;

    public FoodItemManager(SharedPreferences prefs, String totalKcalTag){
        this.prefs = prefs;
        this.totalKcalTag = totalKcalTag;
        this.images = new FoodItem[5];
    }

    public void addFoodItem(FoodItem img){
        img.writeKCalTotal(prefs, totalKcalTag);
        if(checkFoodItemsFull()){
            images = removeFoodItem(images);    //delete the oldest item
            for(int i = 1; i<images.length; i++){  //Bump newer items down one to add new to the end
                images[i - 1] = images[i];          // TODO: Old image file cleanup
            }
            images[images.length -1] = img;            //Add the new image at the end
        }
        else{
            images[getFirstEmptyFoodItem()] = img;
        }
    }

    public FoodItem[] removeFoodItem(FoodItem[] foodItems){
        String imageToDelete = foodItems[0].getImagePath();
        //todo delete image
        foodItems[0] = null;
        return images;
    }

    public boolean checkFoodItemsFull(){
        int imageCount = 0;
        for(FoodItem img : images){
            if(null != img){
                imageCount++;
            }
        }
        return imageCount==images.length;
    }

    public int getFirstEmptyFoodItem(){
        int firstEmptyItem = 0;
        for(int i = 0; i<=images.length; i++){
            if (images[i] == null){
                firstEmptyItem = i;
                return firstEmptyItem;
            }
        }
        return firstEmptyItem;
    }

    public FoodItem[] getFoodItems(){
        return images;
    }

    public FoodItem getFoodItem(int i) throws ArrayIndexOutOfBoundsException{
        return images[i];
    }
}
