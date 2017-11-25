package uk.ac.yorksj.spray.david.caloriesnap.activity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import uk.ac.yorksj.spray.david.caloriesnap.R;
import uk.ac.yorksj.spray.david.caloriesnap.activity.adapter.LanguageListAdapter;

/**
 * Uses Android-LocalizationActivity package. Source: https://github.com/akexorcist/Android-LocalizationActivity
 */
public class SettingsActivity extends LocalizationActivity implements ExpandableListView.OnChildClickListener {

    private String languagesHeader;
    private ExpandableListView languagesListView;
    private LanguageListAdapter languagesListAdapter;

    ArrayList<String> languageHeaders = new ArrayList<>();
    HashMap<String, List<String>> subHeadersMap;
    HashMap<String, List<Drawable>> iconsMap = new HashMap<>();

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
    }

    public HashMap<String, List<String>> createLanguagesSubHeaders(Resources res, List<String> headers){

        HashMap<String, List<String>> subHeadersMap = new HashMap<>();

        List<String> subHeaders = new ArrayList<>();
            subHeaders.add(res.getString(R.string.lang_eng));
            subHeaders.add(res.getString(R.string.lang_fra));
        subHeadersMap.put(headers.get(0), subHeaders);
        return subHeadersMap;
    }

    public HashMap<String, List<Drawable>> createLanguagesIcons(Resources res, List<String> headers){

        HashMap<String, List<Drawable>> iconsMap = new HashMap<>();

        List<Drawable> icons = new ArrayList<>();
            icons.add(res.getDrawable(R.drawable.icon_logo_color));
            icons.add(res.getDrawable(R.drawable.icon_logo_color));
        iconsMap.put(headers.get(0), icons);

        return iconsMap;
    }

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
}
//
//    public boolean onChildClick(ExpandableListView parent, View v,
//                                int groupPosition, int childPosition, long id) {
//
//        Toast.makeText(
//                getApplicationContext(),
//                listDataHeader.get(groupPosition)
//                        + " : "
//                        + listDataChild.get(
//                        listDataHeader.get(groupPosition)).get(
//                        childPosition), Toast.LENGTH_SHORT)
//                .show();
//        return false;
//    }
//}
