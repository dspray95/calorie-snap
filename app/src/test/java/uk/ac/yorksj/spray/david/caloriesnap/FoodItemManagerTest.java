package uk.ac.yorksj.spray.david.caloriesnap;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Test;
import org.mockito.Mockito;

import uk.ac.yorksj.spray.david.caloriesnap.activity.GalleryActivity;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;

public class FoodItemManagerTest {
    @Test
    public void addFoodItem() throws Exception {
        final SharedPreferences prefs = Mockito.mock(SharedPreferences.class);
        final SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
        FoodItemManager manager = new FoodItemManager("TOTAL_KCAL");
        FoodItem foodItem = new FoodItem("img/path");
        Mockito.when(prefs.edit()).thenReturn(editor);
        Mockito.when(editor.putInt(anyString(), anyInt())).thenReturn(editor);
        manager.addFoodItem(foodItem, prefs);
        assertTrue(manager.getFoodItems() != null);
    }

    @Test
    public void removeFoodItem() throws Exception {
        final SharedPreferences prefs = Mockito.mock(SharedPreferences.class);
        final SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);

        FoodItemManager manager = new FoodItemManager("TOTAL_KCAL");
        FoodItem foodItem = new FoodItem("img/path");

        Mockito.when(prefs.edit()).thenReturn(editor);
        Mockito.when(editor.putInt(anyString(), anyInt())).thenReturn(editor);
        manager.addFoodItem(foodItem, prefs);
        manager.removeFoodItem(manager.getFoodItems());
        FoodItem[] foodItems = manager.getFoodItems();
        assertTrue(foodItems[0] == null);
    }

    @Test
    public void checkFoodItemsFull() throws Exception {
        final SharedPreferences prefs = Mockito.mock(SharedPreferences.class);
        final SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);

        FoodItemManager manager = new FoodItemManager("TOTAL_KCAL");
        FoodItem[] foodItems = manager.getFoodItems();
        Mockito.when(prefs.edit()).thenReturn(editor);
        Mockito.when(editor.putInt(anyString(), anyInt())).thenReturn(editor);

        for(int i = 0; i <= foodItems.length; i++){
            FoodItem foodItem = new FoodItem("img/path");
            manager.addFoodItem(foodItem, prefs);
        }
        assertTrue(manager.checkFoodItemsFull() == true);
    }

    @Test
    public void getFirstEmptyFoodItem() throws Exception {
        final SharedPreferences prefs = Mockito.mock(SharedPreferences.class);
        final SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
        Mockito.when(prefs.edit()).thenReturn(editor);
        Mockito.when(editor.putInt(anyString(), anyInt())).thenReturn(editor);
        FoodItemManager manager = new FoodItemManager("TOTAL_KCAL");

        FoodItem foodItem = new FoodItem("img/path");

        manager.addFoodItem(foodItem, prefs);

        assertTrue(manager.getFirstEmptyFoodItem() == 1);
    }

    @Test
    public void getFoodItems() throws Exception {
        final SharedPreferences prefs = Mockito.mock(SharedPreferences.class);
        final SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
        Mockito.when(prefs.edit()).thenReturn(editor);
        Mockito.when(editor.putInt(anyString(), anyInt())).thenReturn(editor);
        FoodItemManager manager = new FoodItemManager("TOTAL_KCAL");

        FoodItem foodItem = new FoodItem("img/path");

        manager.addFoodItem(foodItem, prefs);

        assertTrue(manager.getFoodItems() != null);

    }

    @Test
    public void getFoodItem() throws Exception {
        final SharedPreferences prefs = Mockito.mock(SharedPreferences.class);
        final SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
        Mockito.when(prefs.edit()).thenReturn(editor);
        Mockito.when(editor.putInt(anyString(), anyInt())).thenReturn(editor);
        FoodItemManager manager = new FoodItemManager("TOTAL_KCAL");

        FoodItem foodItem = new FoodItem("img/path");

        manager.addFoodItem(foodItem, prefs);

        assertTrue(manager.getFoodItem(0) != null);
    }

}