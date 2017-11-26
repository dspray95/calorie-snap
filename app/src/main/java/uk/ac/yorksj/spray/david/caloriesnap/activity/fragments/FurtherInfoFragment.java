package uk.ac.yorksj.spray.david.caloriesnap.activity.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Locale;

import uk.ac.yorksj.spray.david.caloriesnap.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FurtherInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FurtherInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Uses MPAndroidChart package source: https://github.com/PhilJay/MPAndroidChart
 */
public class FurtherInfoFragment extends Fragment implements View.OnClickListener, TextToSpeech.OnInitListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "CALORIE_VALUE";
    private float RDA_MALE = 2500.0f;
    private float RDA_FEMALE = 2000.0f; //Needs to be float for coversion to percentage on pie chart

    private int invertState = 0;
    private OnFragmentInteractionListener mListener;
    private PieChart mPieChart;
    private int calorieValue;
    private GalleryFragment parent;

    private TextToSpeech tts;
    private int ttsPercentageFemale;
    private int ttsPercentageMale;

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
        tts = new TextToSpeech(getContext(), this);
        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int primaryColor = getPrimaryColor();
        setColors(primaryColor);
        mPieChart = (PieChart) view.findViewById(R.id.fi_pie_chart);
        mPieChart.setUsePercentValues(true);

        float percentageOfRdaMale = calorieValue/RDA_MALE;
        float percentageOfRdaFemale = calorieValue/RDA_FEMALE;
        float restOfRda = 1 - (percentageOfRdaFemale + percentageOfRdaMale);
        //Create percentage ints for tts
        ttsPercentageFemale = Math.round(percentageOfRdaFemale*100);
        ttsPercentageMale = Math.round(percentageOfRdaMale*100);
        //Pie chart setup
        ArrayList<Entry> pieChartYvalues = new ArrayList<>();
        pieChartYvalues.add(new Entry(percentageOfRdaFemale, 0));
        pieChartYvalues.add(new Entry(percentageOfRdaMale, 1));
        pieChartYvalues.add(new Entry(restOfRda, 2));

        PieDataSet pieChartDataSet = new PieDataSet(pieChartYvalues, "");

        int [] colorsArray = new int[]{R.color.pie_chart_female,    //custom colour values
                R.color.pie_chart_male,
                R.color.textBright};
        pieChartDataSet.setColors(ColorTemplate.createColors(getResources(), colorsArray)); //Always should be bright

        pieChartDataSet.setValueTextColor(getResources().getColor(R.color.textBright));
        pieChartDataSet.setValueTextSize(14f);
        ArrayList<String> pieChartKey = new ArrayList<>();
        pieChartKey.add(getResources().getString(R.string.female));
        pieChartKey.add(getResources().getString(R.string.male));
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
        mPieChart.setCenterTextColor(primaryColor);

        //Make legend adjustments
        int[] legendColorsArray = new int[]{
                R.color.pie_chart_female,
                R.color.pie_chart_male
        };

        ArrayList<String> legendStrings = new ArrayList<>();
            legendStrings.add(getResources().getString(R.string.female));
            legendStrings.add(getResources().getString(R.string.male));

        Legend pieChartLegend = mPieChart.getLegend();
        pieChartLegend.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        pieChartLegend.setTextColor(primaryColor);
        pieChartLegend.setTextSize(16f);
        pieChartLegend.setCustom(ColorTemplate.createColors(getResources(), legendColorsArray), legendStrings);

        TextView totalKcalCount = (TextView) getView().findViewById(R.id.fi_kcal_count);
        SharedPreferences prefs = getActivity().getSharedPreferences(getResources().getString(R.string.tag_total_kcal), Context.MODE_PRIVATE);
        totalKcalCount.setText(Integer.toString(prefs.getInt(getResources().getString(R.string.tag_total_kcal), Context.MODE_PRIVATE)));

        getView().findViewById(R.id.btn_fi_tts).setOnClickListener(this);
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

    public int getPrimaryColor(){
        int primaryColor;
        switch(invertState){
            case 0:
                primaryColor = getResources().getColor(R.color.textBright);
                break;
            case 1:
                primaryColor = getResources().getColor(R.color.textDark);
                break;
            default:
                primaryColor = getResources().getColor(R.color.textBright);
        }

        return primaryColor;
    }

    public int getPrimaryColorResource(){
        int primaryColor;
        switch(invertState){
            case 0:
                primaryColor = R.color.textBright;
                break;
            case 1:
                primaryColor = R.color.textDark;
                break;
            default:
                primaryColor = R.color.textBright;
        }

        return primaryColor;
    }

    public void setInvertState(int invertState){
        this.invertState = invertState;
    }

    public void setColors(int primaryColor){
        //Get views
        TextView lblPieChart = (TextView) getView().findViewById(R.id.fi_lbl_pie);
        TextView lblTotalKcal = (TextView) getView().findViewById(R.id.fi_lbl_total_kcal);
        TextView lblKcal = (TextView) getView().findViewById(R.id.fi_lbl_kcal);
        TextView kcalCount = (TextView) getView().findViewById(R.id.fi_kcal_count);
        ImageView divider1 = (ImageView) getView().findViewById(R.id.fi_divider1);
        ImageView divider2 = (ImageView) getView().findViewById(R.id.fi_divider2);
        ImageButton btnTTS = (ImageButton) getView().findViewById(R.id.btn_fi_tts);
        //set colors
        lblPieChart.setTextColor(primaryColor);
        lblTotalKcal.setTextColor(primaryColor);
        lblKcal.setTextColor(primaryColor);
        kcalCount.setTextColor(primaryColor);
        divider1.setBackgroundColor(primaryColor);
        divider2.setBackgroundColor(primaryColor);
        btnTTS.setColorFilter(primaryColor);
    }

    public void textToSpeech(){
        Resources res = getResources();
        //This meal contains $percentagefemale% of female and $percentmale % of male caloric rda.
        String tts1 = res.getString(R.string.tts_fi_1);
        String tts2 = Integer.toString(ttsPercentageFemale);
        String tts3 = res.getString(R.string.tts_fi_3);
        String tts4 = Integer.toString(ttsPercentageMale);
        String tts5 = res.getString(R.string.tts_fi_5);
        //you have counted $totalkcalcounted calories with this app.
        String tts6 = res.getString(R.string.tts_fi_6);
        String tts7 = ((TextView)getView().findViewById(R.id.fi_lbl_kcal)).getText().toString();
        String tts8 = res.getString(R.string.tts_fi_8);

        String speechSequence = tts1+tts2+tts3+tts4+tts5+tts6+tts7+tts8;
        //TODO sort this into an array and assemble the speechsequence from that. More elegant
        tts.speak(speechSequence, TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale currentLocale = getResources().getConfiguration().locale;
            tts.setLanguage(currentLocale);
        } else {
            Log.e("TTS", "Initialization failed");
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_fi_tts:
                textToSpeech();
                break;
        }
    }
}
