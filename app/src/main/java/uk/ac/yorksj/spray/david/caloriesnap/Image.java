package uk.ac.yorksj.spray.david.caloriesnap;

import java.io.Serializable;

/**
 * Created by david on 31/10/17.
 */

public class Image implements Serializable{

    private int kcalCount;
    private String imagePath;

    public Image(String imagePath){
        this.imagePath = imagePath;
    }

}
