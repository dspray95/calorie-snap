package uk.ac.yorksj.spray.david.caloriesnap.activity.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import uk.ac.yorksj.spray.david.caloriesnap.R;

/**
 * List adapter for the help menu
 * Handles the creation of help menu lists (for both the gallery and camera help screens)
 */

public class HelpMenuListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] descriptions;
    private Integer[] images;

    /**
     * Constructor
     * @param context parent context
     * @param descriptions entries for the help list
     * @param images associated images for the descriptions
     */
    public HelpMenuListAdapter(Activity context, String[] descriptions, Integer[] images) {
        super(context, R.layout.help_list_entry, descriptions);
        this.context = context;
        this.descriptions = descriptions;
        this.images = images;
    }

    /**
     * Inflates each object of the list
     * @param position list item position
     * @param view View
     * @param parent ViewGroup
     * @return
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listRow = inflater.inflate(R.layout.help_list_entry, null, true);

        TextView helpDescr = listRow.findViewById(R.id.help_entry_text);
        ImageView helpImg = listRow.findViewById(R.id.help_entry_image);

        helpDescr.setText(descriptions[position]);
        helpImg.setImageResource(images[position]);
        return listRow;
    }
}
