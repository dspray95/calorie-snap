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
 * Created by david on 27/11/17.
 */

public class HelpMenuListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] descriptions;
    private Integer[] images;

    public HelpMenuListAdapter(Activity context, String[] descriptions, Integer[] images) {
        super(context, R.layout.help_list_entry, descriptions);
        this.context = context;
        this.descriptions = descriptions;
        this.images = images;

    }

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
