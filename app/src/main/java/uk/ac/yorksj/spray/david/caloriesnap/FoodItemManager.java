package uk.ac.yorksj.spray.david.caloriesnap;

import android.content.SharedPreferences;

import java.io.Serializable;

/**
 * Handler object for the previour 5 foodItems created
 */

public class FoodItemManager implements Serializable{

    private String TAG_TOTAL_KCAL;
    private FoodItem[] images; //List of previous 5 food items

    public FoodItemManager(String totalKcalTag){
        this.TAG_TOTAL_KCAL = totalKcalTag;
        this.images = new FoodItem[5];
    }

    /**
     * Adds food item to the list and removes the oldest item
     * @param img FoodItem to add
     * @param prefs
     */
    public void addFoodItem(FoodItem img, SharedPreferences prefs){
        img.writeKCalTotal(prefs, TAG_TOTAL_KCAL);
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

    /**
     * Delete food item
     * @param foodItems
     * @return
     */
    public FoodItem[] removeFoodItem(FoodItem[] foodItems){
        String imageToDelete = foodItems[0].getImagePath();
        //todo delete image
        foodItems[0] = null;
        return images;
    }

    /**
     * Check if there are already 5 FoodItems in the food item array
     * @return
     */
    public boolean checkFoodItemsFull(){
        int imageCount = 0;
        for(FoodItem img : images){
            if(null != img){
                imageCount++;
            }
        }
        return imageCount==images.length;
    }

    /**
     * Used when the food item array is not full
     * @return the first index that does not hold an object
     */
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
