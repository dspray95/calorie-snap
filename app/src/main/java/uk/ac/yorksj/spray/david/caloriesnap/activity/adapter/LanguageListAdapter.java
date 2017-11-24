package uk.ac.yorksj.spray.david.caloriesnap.activity.adapter;

/**
 * Created by david on 24/11/17.
 */

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import uk.ac.yorksj.spray.david.caloriesnap.R;

public class LanguageListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> languagesHeaders; //Headers
    private HashMap<String, List<String>> languagesEntries; //<HeaderTitle, ChildTitle>
    private HashMap<String, List<Drawable>> languageFlags;

    public LanguageListAdapter(Context context, List<String> languagesHeaders,
                                       HashMap<String, List<String>> languagesEntries,
                                       HashMap<String, List<Drawable>> languageFlags) {
        this.context = context;
        this.languagesHeaders = languagesHeaders;
        this.languagesEntries = languagesEntries;
        this.languageFlags = languageFlags;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.languagesEntries.get(this.languagesHeaders.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.languages_entry, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.languages_entry);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.languagesEntries.get(this.languagesHeaders.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.languagesHeaders.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.languagesHeaders.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.languages_header, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.languages_title);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
