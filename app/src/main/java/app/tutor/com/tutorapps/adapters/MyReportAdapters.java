package app.tutor.com.tutorapps.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.LinkedList;

import app.tutor.com.tutorapps.R;
import app.tutor.com.tutorapps.customview.RobotoMediam;
import app.tutor.com.tutorapps.pojo.ReportDataModel;

/**
 * Created by Saikat's Mac on 14/05/16.
 */
public class MyReportAdapters extends RecyclerView.Adapter<MyReportAdapters.ViewHolder> {

    LinkedList<ReportDataModel> subName;
    Context mContext = null;

    protected String[] mParties = null;

    public MyReportAdapters(Context mContext, LinkedList<ReportDataModel> subName) {
        super();
        this.subName = subName;
        this.mContext = mContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.items_my_report, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            holder.subName.setText(subName.get(position).getExamSubject());
            holder.subSet.setText(subName.get(position).getQuestionSet());
            holder.subDate.setText(subName.get(position).getExamDate());

//=========Chart Management

            mParties = new String[]{"Unattended " + subName.get(position).getTotalQuestion(), "Right " + subName.get(position).getCorrectAns(), "Wrong " + subName.get(position).getWrongAns()};

            holder.pChart.setUsePercentValues(true);
            holder.pChart.setDescription("");
            holder.pChart.setExtraOffsets(1, 1, 1, 1);

            holder.pChart.setDragDecelerationFrictionCoef(0.95f);

            holder.pChart.setDrawHoleEnabled(true);
            holder.pChart.setHoleColor(Color.WHITE);

            holder.pChart.setTransparentCircleColor(Color.WHITE);
            holder.pChart.setTransparentCircleAlpha(110);

            holder.pChart.setHoleRadius(4f);
            holder.pChart.setTransparentCircleRadius(2f);

            holder.pChart.setDrawCenterText(true);

            holder.pChart.setRotationAngle(0);
            // enable rotation of the chart by touch
            holder.pChart.setRotationEnabled(true);
            holder.pChart.setHighlightPerTapEnabled(true);

            // mChart.setUnit(" €");
            // mChart.setDrawUnitsInChart(true);

            // add a selection listener
            //mChart.setOnChartValueSelectedListener(this);

            setData(3, position, holder.pChart);

            holder.pChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
            // mChart.spin(2000, 0, 360);

            Legend l = holder.pChart.getLegend();
            l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
            l.setXEntrySpace(10f);
            l.setYEntrySpace(0f);
            l.setYOffset(0f);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return subName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RobotoMediam subName = null, subSet = null, subDate = null;
        PieChart pChart = null;

        public ViewHolder(View itemView) {
            super(itemView);
            subName = (RobotoMediam) itemView.findViewById(R.id.sub_name);
            subSet = (RobotoMediam) itemView.findViewById(R.id.sub_set);
            subDate = (RobotoMediam) itemView.findViewById(R.id.date);
            pChart = (PieChart) itemView.findViewById(R.id.chart1);
        }

    }


    private void setData(int count, int position, PieChart mChart) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();


        if (subName.get(position).getExamSubject().equalsIgnoreCase("MockTest")) {
            int temp_ = (int) (((float) subName.get(position).getTotalQuestion() / 200.00f) * 100.00f);
            //Logger.showMessage("UNATTENDED", "UNATTENDED% " + temp_);
            yVals1.add(new Entry(temp_, 0));
            temp_ = (int) (((float) subName.get(position).getCorrectAns() / 200.00f) * 100.00f);
            yVals1.add(new Entry(temp_, 1));
            temp_ = (int) (((float) subName.get(position).getWrongAns() / 200.00f) * 100.00f);
            yVals1.add(new Entry(temp_, 2));
        } else {
            int temp_ = (int) (((float) subName.get(position).getTotalQuestion() / 25.00f) * 100.00f);
            //Logger.showMessage("UNATTENDED", "UNATTENDED% " + temp_);
            yVals1.add(new Entry(temp_, 0));
            temp_ = (int) (((float) subName.get(position).getCorrectAns() / 25.00f) * 100.00f);
            yVals1.add(new Entry(temp_, 1));
            temp_ = (int) (((float) subName.get(position).getWrongAns() / 25.00f) * 100.00f);
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
