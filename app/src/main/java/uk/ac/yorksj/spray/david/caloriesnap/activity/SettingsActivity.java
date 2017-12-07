package uk.ac.yorksj.spray.david.caloriesnap.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.TextView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import uk.ac.yorksj.spray.david.caloriesnap.R;
import uk.ac.yorksj.spray.david.caloriesnap.activity.adapter.LanguageListAdapter;

/**
 * Handles user preferences
 * Includes high-contrast mode, auto-text-to-speech settings, and lanugage settings
 * Uses Android-LocalizationActivity package. Source: https://github.com/akexorcist/Android-LocalizationActivity
 */
public class SettingsActivity extends LocalizationActivity
        implements ExpandableListView.OnChildClickListener, Switch.OnCheckedChangeListener{

    private String HIGH_CONTRAST_TAG = "HIGH_CONTRAST";

    private ExpandableListView languagesListView;
    private LanguageListAdapter languagesListAdapter;

    private ArrayList<String> languageHeaders = new ArrayList<>();
    private HashMap<String, List<String>> subHeadersMap;
    private HashMap<String, List<Drawable>> iconsMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Resources res = getResources();
        languagesListView = (ExpandableListView) findViewById(R.id.list_languages);

        //Setup languages list content
        languageHeaders = new ArrayList<>();
            languageHeaders.add(res.getString(R.string.prefs_language));
        subHeadersMap = createLanguagesSubHeaders(res, languageHeaders);
        iconsMap = createLanguagesIcons(res, languageHeaders);

        //Create and set languages list adapter
        languagesListAdapter = new LanguageListAdapter(this, languageHeaders, subHeadersMap, iconsMap);
        languagesListView.setAdapter(languagesListAdapter);

        languagesListView.setOnChildClickListener(this);
        //Setup switches for text-to-speech and high contrast
        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
        boolean highContrast = prefs.getBoolean(HIGH_CONTRAST_TAG, false);
        Switch highContrastSwitch = (Switch) findViewById(R.id.switch_high_contrast);
        if(highContrast){
            highContrastSwitch.setChecked(true);
        }
        else{
            highContrastSwitch.setChecked(false);
        }
        highContrastSwitch.setOnCheckedChangeListener(this);
    }

    /**
     * Creates subheader entries for the language list
     * Lists all supported languages
     * @param res
     * @param headers List of all subheaders (eg. English, French, German...)
     * @return
     */
    public HashMap<String, List<String>> createLanguagesSubHeaders(Resources res, List<String> headers){

        HashMap<String, List<String>> subHeadersMap = new HashMap<>();

        List<String> subHeaders = new ArrayList<>();
            subHeaders.add(res.getString(R.string.lang_eng));
            subHeaders.add(res.getString(R.string.lang_fra));
        subHeadersMap.put(headers.get(0), subHeaders);
        return subHeadersMap;
    }

    /**
     * Create the language flag icons for the related subheaders
     * @param res
     * @param headers
     * @return
     */
    public HashMap<String, List<Drawable>> createLanguagesIcons(Resources res, List<String> headers){

        HashMap<String, List<Drawable>> iconsMap = new HashMap<>();

        List<Drawable> icons = new ArrayList<>();
            icons.add(res.getDrawable(R.drawable.icon_logo_color));
            icons.add(res.getDrawable(R.drawable.icon_logo_color));
        iconsMap.put(headers.get(0), icons);

        return iconsMap;
    }

    /**
     * Listener for switches
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if(isChecked){
            editor.putBoolean(HIGH_CONTRAST_TAG, true);
        }
        else{
            editor.putBoolean(HIGH_CONTRAST_TAG, false);
        }
        editor.commit();
    }

    /**
     * Listener for language subheaders
     * Sets the locale to the tapped language
     * @param parent
     * @param v
     * @param groupPosition
     * @param childPosition
     * @param id
     * @return
     */
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
                switch (childPosition){ //TODO make more explicit
                    case 0:
                        setLanguage(Locale.UK);
                        break;
                    case 1:
                        setLanguage(Locale.FRANCE);
                        break;
                }
        return false;
    }

    /**
     * Workaround for going back to the gallery activity.
     * Issue due to the way the gallery fragments are instantiated,
     * when reloading savedinstancestate objects were returning null.
     * This resolves the issue by recreating the activity.
     * finish() is called on GalleryActivity when SettingsActivity is accessed via the
     * GalleryFragment.
     */
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }
}
