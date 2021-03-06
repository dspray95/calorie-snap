package uk.ac.yorksj.spray.david.caloriesnap.activity.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import uk.ac.yorksj.spray.david.caloriesnap.FoodItem;
import uk.ac.yorksj.spray.david.caloriesnap.R;
import uk.ac.yorksj.spray.david.caloriesnap.activity.SettingsActivity;
import uk.ac.yorksj.spray.david.caloriesnap.activity.listener.GalleryNavigationListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GalleryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Gallery fragment displays calorie information for a specific meal,
 * with an image of the meal in the background.
 * Also contains functionality for settings, invert, and help buttons.
 * Contains the navigation manager which created a further information fragment on swiping down
 */
public class GalleryFragment extends Fragment implements View.OnClickListener, TextToSpeech.OnInitListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String FOOD_ITEM = "food_item";
    private String TAG = "GALLERY_FRAGMENT";

    // TODO: Rename and change types of parameters
    private FoodItem foodItem;
    private Bitmap bitmap;
    private OnFragmentInteractionListener mListener;
    private boolean detailsEnabled = true;
    private GalleryNavigationListener swipeListener;
    private int invertState = 0;
    private TextToSpeech tts;


    public GalleryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GalleryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(FoodItem foodItem, Resources res, boolean firstFragment) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putParcelable("food_item", foodItem);
        fragment.foodItem = foodItem;
        fragment.setArguments(args);
        //tts init
        return fragment;
    }

    public boolean hasBitmap(){
        return this.bitmap!=null ? true : false;
    }

    public boolean trySetBitmap(){
        if(foodItem.hasBitmap()){
            bitmap = foodItem.getBitmap();
            return true;
        }else{
            return false;
        }
    }

    public void setBackgroundImage(){
        if(!hasBitmap()){
            trySetBitmap();
        }
        ImageView backgroundImage = (ImageView) getView().findViewById(R.id.img_gallery_background);
        backgroundImage.setImageBitmap(bitmap);
    }

    public void recycleBitmap(){
        if(bitmap != null){
            bitmap.recycle();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trySetBitmap();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "oncreateView fired");
        trySetBitmap();
        tts = new TextToSpeech(getContext(), this);
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //High contrast mode (set in SettingsActivity) will ensure that there is always a large amount
        //of contrast between the foreground text and the background image by applying a brightness screen
        SharedPreferences prefs = getActivity().getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        boolean highContrast = prefs.getBoolean("HIGH_CONTRAST", false);

        if(highContrast){
            view.findViewById(R.id.img_high_contrast_background).setVisibility(View.VISIBLE);
        }
        else{
            view.findViewById(R.id.img_high_contrast_background).setVisibility(View.INVISIBLE);
        }

        //Add the kcal count to the appropriate label
        TextView lblKcalCount = (TextView) getView().findViewById(R.id.txt_kcalcount);
        lblKcalCount.setText(Integer.toString(foodItem.getKcalCount()));

        //listener setting
        this.swipeListener = new GalleryNavigationListener(getChildFragmentManager(), this, foodItem.getKcalCount(),
                'd', foodItem.getImagePath());
        view.setOnTouchListener(this.swipeListener);
        view.findViewById(R.id.btn_settings).setOnClickListener(this);
        view.findViewById(R.id.btn_invert).setOnClickListener(this);
        view.findViewById(R.id.btn_help).setOnClickListener(this);
        view.findViewById(R.id.btn_text_to_speech_gallery).setOnClickListener(this);
        setBackgroundImage();
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * Toggles the information and buttons of the fragment between visible/invisible
     * Used when creating the further information frgament.
     */
    public void toggleDetails(){
        ConstraintLayout detailsLayout = (ConstraintLayout) getView().findViewById(R.id.details);
        if(detailsEnabled){
            detailsLayout.animate().translationY(detailsLayout.getHeight()); //Move details out of the way
            detailsEnabled = false;
        }
        else{
            detailsLayout.animate().translationY(0); //brings them back
            detailsEnabled = true;
        }
    }

    /**
     * Inverts the colors of the GUI items
     */
    public void invert(){
        Resources res = getResources();
        TextView lblKcal = (TextView) getView().findViewById(R.id.txt_kcal);
        TextView lblKcalCount = (TextView) getView().findViewById(R.id.txt_kcalcount);
        ImageButton btnSettings = (ImageButton) getView().findViewById(R.id.btn_settings);
        ImageButton btnInvert = (ImageButton) getView().findViewById(R.id.btn_invert);
        ImageButton btnHelp = (ImageButton) getView().findViewById(R.id.btn_help);
        ImageButton btnTTS = (ImageButton) getView().findViewById(R.id.btn_text_to_speech_gallery);
        ImageView imgArrow = (ImageView) getView().findViewById(R.id.img_arrow);
        //TODO invert button colors;//

        switch(invertState){
            case 0:
                lblKcal.setTextColor(res.getColor(R.color.textDark));
                lblKcalCount.setTextColor(res.getColor(R.color.textDark));
                btnSettings.setColorFilter(res.getColor(R.color.textDark));
                btnInvert.setColorFilter(res.getColor(R.color.textDark));
                btnHelp.setColorFilter(res.getColor(R.color.textDark));
                btnTTS.setColorFilter(res.getColor(R.color.textDark));
                imgArrow.setColorFilter(res.getColor(R.color.textDark));
                swipeListener.getFurtherInfoFragment().setInvertState(1);
                invertState = 1;
                break;
            case 1:
                lblKcal.setTextColor(res.getColor(R.color.textBright));
                lblKcalCount.setTextColor(res.getColor(R.color.textBright));
                btnSettings.setColorFilter(res.getColor(R.color.textBright));
                btnInvert.setColorFilter(res.getColor(R.color.textBright));
                btnHelp.setColorFilter(res.getColor(R.color.textBright));
                btnTTS.setColorFilter(res.getColor(R.color.textBright));
                imgArrow.setColorFilter(res.getColor(R.color.textBright));
                swipeListener.getFurtherInfoFragment().setInvertState(0);
                invertState = 0;
                break;
        }
    }

    /**
     * Readout of the information on screen
     */
    public void textToSpeech(){
        Resources res = getResources();
        String tts1 = res.getString(R.string.tts_gallery_1);
        String tts2 = ((TextView) getView().findViewById(R.id.txt_kcalcount)).getText().toString();
        String tts3 = res.getString(R.string.tts_gallery_3);
        String speechSequence = tts1 + tts2 + tts3;

        tts.speak(speechSequence, TextToSpeech.QUEUE_FLUSH, null);
    }

    /**
     * Text to speech initialisation
     * @param status
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale currentLocale = getResources().getConfiguration().locale;
            tts.setLanguage(currentLocale);
        } else {
            Log.e("TTS", "Initialization failed");
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_settings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.btn_invert:
                invert();
                break;
            case R.id.btn_help:
                swipeListener.addHelpFragment();
                break;
            case R.id.btn_text_to_speech_gallery:
                textToSpeech();
                break;
        }
    }
}
