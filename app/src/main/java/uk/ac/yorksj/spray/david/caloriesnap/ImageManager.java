package uk.ac.yorksj.spray.david.caloriesnap;

import java.io.Serializable;

/**
 * Created by david on 31/10/17.
 */

public class ImageManager implements Serializable{

    protected Image[] images;

    public ImageManager(){
        images = new Image[5];
    }

    public void addImage(Image img){
        if(checkImagesFull()){
            for(int i = 1; i<=images.length; i++){  //Remove the oldest image by bumping newer ones
                images[i - 1] = images[i];          //down a position
            }
            images[images.length] = img;            //Add the new image at the end
        }
        else{
            images[getFirstEmptyImage()] = img;
        }
    }

    public boolean checkImagesFull(){
        int imageCount = 0;
        for(Image img : images){
            if(null != img){
                imageCount++;
            }
        }
        return imageCount==images.length;
    }

    public int getFirstEmptyImage(){
        for(int i = 0; i<=images.length; i++){
            if (images[i] == null){
               return i;
            }
        }
        return images.length;
    }

    public Image[] getImages(){
        return images;
    }

    public Image getImage(int i) throws ArrayIndexOutOfBoundsException{
        return images[i];
    }
}
