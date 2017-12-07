package uk.ac.yorksj.spray.david.caloriesnap.activity.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import uk.ac.yorksj.spray.david.caloriesnap.R;
import uk.ac.yorksj.spray.david.caloriesnap.activity.adapter.HelpMenuListAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HelpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HelpFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Displays help information for relevant screens
 */
public class HelpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String HELP_TYPE = "HELP_TYPE";

    // TODO: Rename and change types of parameters
    private String mHelpType;

    private OnFragmentInteractionListener mListener;

    public HelpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment HelpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HelpFragment newInstance(String helpType) {
        HelpFragment fragment = new HelpFragment();
        Bundle args = new Bundle();
        args.putString(HELP_TYPE, helpType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mHelpType = getArguments().getString(HELP_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        String[] descriptions = getDescriptions(mHelpType);
        Integer[] images = getImages(mHelpType);

        HelpMenuListAdapter listAdapter = new HelpMenuListAdapter(getActivity(),
                descriptions, images);

        ListView helpList = getView().findViewById(R.id.help_list);
        helpList.setAdapter(listAdapter);
    }

    /**
     * Gather help descriptions depending on the helpType variable (usually the previous screen)
     * @param helpType
     * @return
     */
    public String[] getDescriptions(String helpType){

        Resources res = getResources();

        switch(helpType){
            case "gallery":
                return new String[]{
                        res.getString(R.string.help_gallery_settings),
                        res.getString(R.string.help_gallery_invert),
                        res.getString(R.string.help_gallery_help),
                        res.getString(R.string.help_gallery_tts),
                        res.getString(R.string.help_gallery_swipe_u),
                        res.getString(R.string.help_gallery_swipe_d),
                        res.getString(R.string.help_gallery_swipe_lr)
                };
            case "camera":
                return new String[]{
                        res.getString(R.string.help_camera_capture),
                        res.getString(R.string.help_camera_swipe_d)
                };
        }
        return null; //throw a nullpointer exception if we dont have a helptype, TODO handle
    }

    /**
     * Gathers the relative images for the relative descriptions
     * @param helpType
     * @return
     */
    public Integer[] getImages(String helpType){
        switch(helpType){
            case "gallery":
                return new Integer[]{
                        R.drawable.ic_settings_black_24dp,
                        R.drawable.ic_invert_colors_black_24dp,
                        R.drawable.ic_help_outline_black_24dp,
                        R.drawable.ic_volume_up_black_24dp,
                        R.drawable.ic_keyboard_arrow_up_black_24dp,
                        R.drawable.ic_keyboard_arrow_down_black_24dp,
                        R.drawable.ic_lr_arrows_black_24dp
                };
            case "camera":
                return new Integer[]{
                        R.drawable.ic_camera_capture_150dp,
                        R.drawable.ic_keyboard_arrow_down_black_24dp
                };
        }
        return null;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
