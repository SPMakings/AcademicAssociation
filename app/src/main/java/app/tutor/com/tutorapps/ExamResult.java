package app.tutor.com.tutorapps;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import app.tutor.com.tutorapps.application.AAApplication;
import app.tutor.com.tutorapps.customview.RobotoBold;
import app.tutor.com.tutorapps.database.AADatabaseManager;
import app.tutor.com.tutorapps.helper.Logger;
import app.tutor.com.tutorapps.pojo.ReportDataModel;

public class ExamResult extends AppCompatActivity {


    public static String ACTION_MOCK = "ACTION_MOCK";

    int CORRECT = 0, WRONG = 0, UNATTENDED = 0;
    View loader;

    private PieChart mChart;

    protected String[] mParties = null;

    AADatabaseManager aDB = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_right_dark);
        aDB = new AADatabaseManager(getApplicationContext());

        loader = findViewById(R.id.loader);

        findViewById(R.id.start_exam).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExamResult.this, ExamManager.class);
                i.putExtra("SET_ID", getIntent().getStringExtra("SET_ID"));
                if (getIntent().getAction().equalsIgnoreCase(ACTION_MOCK)) {
                    i.setAction(ExamManager.ACTION_MOCK);
                } else {
                    i.setAction("classtest");
                }
                startActivity(i);
                finish();
            }
        });


        (new Calculating()).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

    }


    public class Calculating extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loader.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Set models
            loader.setVisibility(View.GONE);

            mParties = new String[]{"Unattended " + UNATTENDED, "Right " + CORRECT, "Wrong " + WRONG};

            mChart = (PieChart) findViewById(R.id.chart1);
            mChart.setUsePercentValues(true);
            mChart.setDescription("");
            mChart.setExtraOffsets(2, 2, 2, 2);

            mChart.setDragDecelerationFrictionCoef(0.95f);

//            tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
//
//            mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
//            if (getIntent().getAction().equalsIgnoreCase(ACTION_MOCK)) {
//                mChart.setCenterText("Total number of\nQuestions 200");
//            }else{
//                mChart.setCenterText("Total number of\nQuestions 25");
//            }

            mChart.setDrawHoleEnabled(true);
            mChart.setHoleColor(Color.WHITE);

            mChart.setTransparentCircleColor(Color.WHITE);
            mChart.setTransparentCircleAlpha(110);

            mChart.setHoleRadius(4f);
            mChart.setTransparentCircleRadius(2f);

            mChart.setDrawCenterText(true);

            mChart.setRotationAngle(0);
            // enable rotation of the chart by touch
            mChart.setRotationEnabled(true);
            mChart.setHighlightPerTapEnabled(true);

            // mChart.setUnit(" €");
            // mChart.setDrawUnitsInChart(true);

            // add a selection listener
            //mChart.setOnChartValueSelectedListener(this);

            setData(3);

            mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
            // mChart.spin(2000, 0, 360);

            Legend l = mChart.getLegend();
            l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
            l.setXEntrySpace(10f);
            l.setYEntrySpace(0f);
            l.setYOffset(0f);


            ((RobotoBold) findViewById(R.id.right)).setText("" + CORRECT);
            DecimalFormat twoDForm = new DecimalFormat("#.##");
            float temp2_ = (float) WRONG * 0.33333f;
            ((RobotoBold) findViewById(R.id.wrong)).setText("-" + twoDForm.format(temp2_));
            temp2_ = ((float) CORRECT - (float) WRONG * 0.33333f);
            ((RobotoBold) findViewById(R.id.total)).setText("" + twoDForm.format(temp2_));

        }

        @Override
        protected Void doInBackground(Void... params) {

            for (int i = 0; i < AAApplication.getInstance().getCURRENT_QUN_SET().size(); i++) {
                if (AAApplication.getInstance().getCURRENT_QUN_SET().get(i).getYourAns() != 0) {
                    if (AAApplication.getInstance().getCURRENT_QUN_SET().get(i).getYourAns()==AAApplication.getInstance().getCURRENT_QUN_SET().get(i).getCorrectAns()) {
                        CORRECT++;
                    } else {
                        WRONG++;
                    }
                }
            }

            if (getIntent().getAction().equalsIgnoreCase(ACTION_MOCK)) {
                UNATTENDED = 200 - (CORRECT + WRONG);
            } else {
                UNATTENDED = 25 - (CORRECT + WRONG);
            }
//            Logger.showMessage("UNATTENDED", "CORRECT " + CORRECT);
//            Logger.showMessage("UNATTENDED", "WRONG " + WRONG);
//            Logger.showMessage("UNATTENDED", "UNATTENDED " + UNATTENDED);

            return null;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM,yyyy hh:mm a");
        ReportDataModel temp_ = new ReportDataModel();
        temp_.setExamDate(df.format(c.getTime()));
        temp_.setQuestionSet(AAApplication.getInstance().getSELECTED_TEST_SET());
        temp_.setExamSubject(AAApplication.getInstance().getSELECTED_TEST());
        temp_.setCorrectAns(CORRECT);
        temp_.setWrongAns(WRONG);
        temp_.setTotalQuestion(UNATTENDED);
        aDB.inserReportData(temp_);
        Logger.showMessage("TAG", AAApplication.getInstance().getSELECTED_TEST());
        Logger.showMessage("TAG", AAApplication.getInstance().getSELECTED_TEST_SET());
        finish();
    }


    private void setData(int count) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();


        if (getIntent().getAction().equalsIgnoreCase(ACTION_MOCK)) {
            int temp_ = (int) (((float) UNATTENDED / 200.00f) * 100.00f);
            //Logger.showMessage("UNATTENDED", "UNATTENDED% " + temp_);
            yVals1.add(new Entry(temp_, 0));
            temp_ = (int) (((float) CORRECT / 200.00f) * 100.00f);
            yVals1.add(new Entry(temp_, 1));
            temp_ = (int) (((float) WRONG / 200.00f) * 100.00f);
            yVals1.add(new Entry(temp_, 2));
        } else {
            int temp_ = (int) (((float) UNATTENDED / 25.00f) * 100.00f);
            //Logger.showMessage("UNATTENDED", "UNATTENDED% " + temp_);
            yVals1.add(new Entry(temp_, 0));
            temp_ = (int) (((float) CORRECT / 25.00f) * 100.00f);
            yVals1.add(new Entry(temp_, 1));
            temp_ = (int) (((float) WRONG / 25.00f) * 100.00f);
            yVals1.add(new Entry(temp_, 2));
        }


        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count; i++)
            xVals.add(mParties[i % mParties.length]);

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.parseColor("#78909C"));
        colors.add(Color.parseColor("#8BC34A"));
        colors.add(Color.parseColor("#EF5350"));

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);
        mChart.invalidate();
    }

}
