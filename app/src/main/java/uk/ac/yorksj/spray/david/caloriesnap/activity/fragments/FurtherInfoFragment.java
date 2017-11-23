package uk.ac.yorksj.spray.david.caloriesnap.activity.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;

import uk.ac.yorksj.spray.david.caloriesnap.R;
import uk.ac.yorksj.spray.david.caloriesnap.activity.listener.NavigationListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FurtherInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FurtherInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FurtherInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "CALORIE_VALUE";
    private float RDA_MALE = 2500.0f;
    private float RDA_FEMALE = 2000.0f; //Needs to be float for coversion to percentage on pie chart

    private OnFragmentInteractionListener mListener;
    // TODO: Rename and change types of parameters
    private PieChart mPieChart;
    private int calorieValue;
    private GalleryFragment parent;

    public FurtherInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FurtherInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FurtherInfoFragment newInstance(int calorieValue) {
        FurtherInfoFragment fragment = new FurtherInfoFragment();
        Bundle args = new Bundle();
        args.putInt("CALORIE_VALUE", calorieValue);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            calorieValue = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.fragment_further_info, container, false);

        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPieChart = (PieChart) view.findViewById(R.id.pie_chart);
        mPieChart.setUsePercentValues(true);

        float percentageOfRdaMale = calorieValue/RDA_MALE;
        float percentageOfRdaFemale = calorieValue/RDA_FEMALE;
        float restOfRda = 1 - (percentageOfRdaFemale + percentageOfRdaMale);

        ArrayList<Entry> pieChartYvalues = new ArrayList<>();
        pieChartYvalues.add(new Entry(percentageOfRdaFemale, 0));
        pieChartYvalues.add(new Entry(percentageOfRdaMale, 1));
        pieChartYvalues.add(new Entry(restOfRda, 2));

        PieDataSet pieChartDataSet = new PieDataSet(pieChartYvalues, "");

        int [] colorsArray = new int[]{R.color.pie_chart_female,    //custom colour values
                R.color.pie_chart_male,
                R.color.pie_chart_remainder};
        pieChartDataSet.setColors(ColorTemplate.createColors(getResources(), colorsArray));

        pieChartDataSet.setValueTextColor(getResources().getColor(R.color.activeTextColor));
        pieChartDataSet.setValueTextSize(14f);
        ArrayList<String> pieChartKey = new ArrayList<>();
        pieChartKey.add(getResources().getString(R.string.male));
        pieChartKey.add(getResources().getString(R.string.female));
        pieChartKey.add("");
        PieData pieData = new PieData(pieChartKey, pieChartDataSet);

        pieData.setValueFormatter(new PercentFormatter());
        mPieChart.setData(pieData);
        //Customise chart
        mPieChart.setRotationEnabled(false);
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setTransparentCircleRadius(60f);
        mPieChart.setHoleRadius(55f);
        mPieChart.setHoleColor(Color.TRANSPARENT);
        mPieChart.setDescription("");
        mPieChart.setCenterText(getResources().getString(R.string.rda_percentage));
        mPieChart.setCenterTextSize(16f);
        mPieChart.setCenterTextColor(getResources().getColor(R.color.activeTextColor));

        //Make legend adjustments
        ArrayList<Integer> legendColors = new ArrayList<>();
            legendColors.add(R.color.pie_chart_female);
            legendColors.add(R.color.pie_chart_male);
        ArrayList<String> legendStrings = new ArrayList<>();
            legendStrings.add(getResources().getString(R.string.female));
            legendStrings.add(getResources().getString(R.string.male));

        Legend pieChartLegend = mPieChart.getLegend();
        pieChartLegend.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        pieChartLegend.setTextColor(getResources().getColor(R.color.activeTextColor));
        pieChartLegend.setTextSize(16f);
        pieChartLegend.setCustom(legendColors, legendStrings);
        mPieChart.notifyDataSetChanged();

        TextView totalKcalCount = (TextView) getView().findViewById(R.id.fi_kcal_count);
        SharedPreferences prefs = getActivity().getSharedPreferences(getResources().getString(R.string.tag_total_kcal), Context.MODE_PRIVATE);
        totalKcalCount.setText(Integer.toString(prefs.getInt(getResources().getString(R.string.tag_total_kcal), Context.MODE_PRIVATE)));
    }

    public void setParentFragment(GalleryFragment parent){
        this.parent = parent;
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
