package uk.ac.yorksj.spray.david.caloriesnap;

import java.io.Serializable;

/**
 * Created by david on 31/10/17.
 */

public class FoodItemManager implements Serializable{

    protected FoodItem[] images;

    public FoodItemManager(){
        images = new FoodItem[5];
    }

    public void addFoodItem(FoodItem img){
        if(checkFoodItemsFull()){
            for(int i = 1; i<=images.length; i++){  //Remove the oldest image by bumping newer ones
                images[i - 1] = images[i];          //down a position TODO: Old image file cleanup
            }
            images[images.length -1] = img;            //Add the new image at the end
        }
        else{
            images[getFirstEmptyFoodItem()] = img;
        }
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
        for(int i = 0; i<=images.length; i++){
            if (images[i] == null){
               return i;
            }
        }
        return images.length;
    }

    public FoodItem[] getFoodItems(){
        return images;
    }

    public FoodItem getFoodItem(int i) throws ArrayIndexOutOfBoundsException{
        return images[i];
    }
}
