package uk.ac.yorksj.spray.david.caloriesnap.activity.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import uk.ac.yorksj.spray.david.caloriesnap.FoodItem;
import uk.ac.yorksj.spray.david.caloriesnap.R;

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
    private Boolean firstfragment;
    private OnFragmentInteractionListener mListener;

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
        foodItem.createImageBitmap(firstFragment);
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
        ImageView backgroundImage = (ImageView) getView().findViewById(R.id.img_gallery_background);
        backgroundImage.setImageBitmap(bitmap);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodItem.setParentFragment(this);
        trySetBitmap();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "oncreateView fired");
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView lblKcalCount = (TextView) getView().findViewById(R.id.txt_kcalcount);
        lblKcalCount.setText(Integer.toString(foodItem.getKcalCount()));
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
