package uk.ac.yorksj.spray.david.caloriesnap;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by david on 31/10/17.
 */

public class Image implements Serializable{

    private int kcalCount;
    private String imagePath;

    public Image(String imagePath){
        this.imagePath = imagePath;
        this.kcalCount = calculateKcalCount();
    }

    protected int calculateKcalCount(){ //Writing a neural net to do this may take some considerable time...
        return new Random().nextInt(1000);
    }

    public int getKcalCount(){
        return this.kcalCount;
    }

    public String getImagePath(){
        return this.imagePath;
    }

}
