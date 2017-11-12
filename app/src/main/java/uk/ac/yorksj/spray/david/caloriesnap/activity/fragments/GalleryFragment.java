package uk.ac.yorksj.spray.david.caloriesnap.activity.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ViewDragHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import uk.ac.yorksj.spray.david.caloriesnap.FoodItem;
import uk.ac.yorksj.spray.david.caloriesnap.R;
import uk.ac.yorksj.spray.david.caloriesnap.activity.listener.GalleryFragmentListener;
import uk.ac.yorksj.spray.david.caloriesnap.activity.listener.NavigationListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GalleryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String FOOD_ITEM = "food_item";
    private static final String TAG = "GALLERY_FRAGMENT";

    // TODO: Rename and change types of parameters
    private FoodItem foodItem;
    private Bitmap bitmap;
    private OnFragmentInteractionListener mListener;
    private boolean detailsEnabled = true;
    private GalleryFragmentListener swipeListener;

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
        args.putParcelable(FOOD_ITEM, foodItem);
        fragment.foodItem = foodItem;
        fragment.setArguments(args);
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
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView lblKcalCount = (TextView) getView().findViewById(R.id.txt_kcalcount);
        lblKcalCount.setText(Integer.toString(foodItem.getKcalCount()));
        this.swipeListener = new GalleryFragmentListener(getFragmentManager(), this, foodItem.getKcalCount(), 'd');
        view.setOnTouchListener(this.swipeListener);
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

    public void toggleDetails(){
        TextView lblCount = (TextView) getView().findViewById(R.id.txt_kcalcount);
        TextView lblText = (TextView) getView().findViewById(R.id.txt_kcal);

        if(detailsEnabled){
            lblCount.setVisibility(View.INVISIBLE);
            lblText.setVisibility(View.INVISIBLE);
        }
        else{
            lblCount.setVisibility(View.VISIBLE);
            lblText.setVisibility(View.VISIBLE);
        }
    }

    public GalleryFragmentListener getNavigationListener(){
        return this.swipeListener;
    }
}
