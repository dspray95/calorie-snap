package uk.ac.yorksj.spray.david.caloriesnap.activity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.yorksj.spray.david.caloriesnap.R;
import uk.ac.yorksj.spray.david.caloriesnap.activity.adapter.LanguageListAdapter;

public class SettingsActivity extends AppCompatActivity {

    private String languagesHeader;
    private ExpandableListView languagesListView;
    private LanguageListAdapter languagesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Resources res = getResources();
        languagesListView = (ExpandableListView) findViewById(R.id.list_languages);

        HashMap<String, List<String>> listChildren = new HashMap<>();
        HashMap<String, List<Drawable>> listFlagDrawables = new HashMap<>();

        ArrayList<String> headers = new ArrayList<>();
            headers.add(res.getString(R.string.prefs_language));

        languagesListAdapter = new LanguageListAdapter(this, headers,
                createLanguagesSubHeaders(res, headers), createLanguagesIcons(res, headers));

        // setting list adapter
        languagesListView.setAdapter(languagesListAdapter);
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

}
