package uk.ac.yorksj.spray.david.caloriesnap;

import org.junit.Test;

import static org.junit.Assert.*;

public class FoodItemTest {
    @Test
    public void calculateKcalCount() throws Exception {
        FoodItem item = new FoodItem("file/path");
        item.calculateKcalCount();
        int kcalCount = item.getKcalCount();
        assertTrue(kcalCount >= 0 && kcalCount <= 1000);
    }

    @Test
    public void getKcalCount() throws Exception {
        FoodItem item = new FoodItem("file/path");
        item.calculateKcalCount();
        int kcalCount = item.getKcalCount();
        assertTrue(kcalCount >= 0);
    }

    @Test
    public void getImagePath() throws Exception {
        String filepath = "file/path";
        FoodItem item = new FoodItem(filepath);
        String imagePath = item.getImagePath();
        assertEquals(imagePath, filepath);
    }
}