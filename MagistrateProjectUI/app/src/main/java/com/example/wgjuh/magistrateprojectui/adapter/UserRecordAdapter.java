package com.example.wgjuh.magistrateprojectui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wgjuh.magistrateprojectui.Constants;
import com.example.wgjuh.magistrateprojectui.CustomDialog;
import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.SingleRecord;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

/**
 * Created by wGJUH on 25.02.2017.
 */

public class UserRecordAdapter extends RecyclerView.Adapter<UserRecordAdapter.ViewHolder>  {

    ArrayList<SingleRecord> records;
    Context context;
    public UserRecordAdapter(ArrayList<SingleRecord> records, Context context){
        this.records = records;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_record_item, parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setPieChartSettings(holder, position);
        holder.textView.setText(records.get(position).getName());
    }

    void setPieChartSettings(ViewHolder holder, int position){

       // holder.pieChart.setUsePercentValues(true);
        holder.pieChart.setData(setDataToPieChart(position,records.get(position).getName()));
        holder.pieChart.setMaxAngle(360.0f * (records.get(position).getRecord() / 100));
        Log.d(Constants.TAG,"Angle: " + holder.pieChart.getMaxAngle());
        holder.pieChart.setHoleRadius(70);
        holder.pieChart.setTransparentCircleRadius(0);
        holder.pieChart.setCenterTextSize(10);
        holder.pieChart.getLegend().setEnabled(false);
        holder.pieChart.getDescription().setEnabled(false);
        holder.pieChart.setHoleColor(Color.TRANSPARENT);
        holder.pieChart.setCenterText(records.get(position).getRecord()+"%");
        holder.pieChart.setDrawEntryLabels(false);
        holder.pieChart.setRotationEnabled(false);
        holder.pieChart.dispatchSetSelected(false);
        holder.pieChart.setTouchEnabled(false);
        holder.pieChart.invalidate();
    }


    PieData setDataToPieChart(int position, String name){

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
        //ArrayList<String> xValues = new ArrayList<String>();
        float yValue = records.get(position).getRecord();
        String xValue = records.get(position).getName();
        yValues.add(new PieEntry(yValue));

        PieDataSet pieDataSet = new PieDataSet(yValues,name);
        pieDataSet.setDrawValues(false);
        //pieDataSet.setSliceSpace(0);
        //pieDataSet.setSelectionShift(5);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.GRAY);


        //xValues.add("");
        return pieData;
    }
    @Override
    public int getItemCount() {
        return records.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        PieChart pieChart;
        TextView textView;
        Context context;
        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            pieChart = (PieChart)itemView.findViewById(R.id.user_record_pie_chart);
            pieChart.setOnClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.text_user_record);

        }


        @Override
        public void onClick(View v) {
            SingleRecord singleRecord = records.get(getAdapterPosition());
            Log.d(Constants.TAG,"CLICK" + getAdapterPosition());
            CustomDialog customDialog = new CustomDialog(context,singleRecord.getRecord(),singleRecord.getName());
            customDialog.show();
        }
    }
}
