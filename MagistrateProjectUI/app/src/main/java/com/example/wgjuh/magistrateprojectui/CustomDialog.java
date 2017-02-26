package com.example.wgjuh.magistrateprojectui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.wgjuh.magistrateprojectui.adapter.UserRecordAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

/**
 * Created by wGJUH on 25.02.2017.
 */

public class CustomDialog extends Dialog implements View.OnClickListener{
    PieChart pieChart;
    float percent;
    String name;
    Button buttonAccept, buttonDecline;
    public CustomDialog(Context context, float percent, String name) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        this.percent = percent;
        this.name = name;
        initFields();
/*        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = this.getWindow();
        lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);*/
    }

    void initFields(){
        pieChart = (PieChart)findViewById(R.id.single_pie_chart);
        setPieChartSettings();
        buttonAccept = (Button)findViewById(R.id.button_accept);
        buttonDecline = (Button)findViewById(R.id.button_decline);
        buttonAccept.setOnClickListener(this);
        buttonDecline.setOnClickListener(this);

    }
    void setPieChartSettings(){

        // holder.pieChart.setUsePercentValues(true);
       pieChart.setData(setDataToPieChart());
       pieChart.setMaxAngle(360.0f * (percent / 100));
        Log.d(Constants.TAG,"Angle: " + pieChart.getMaxAngle());
        pieChart.setHoleRadius(70);
        pieChart.setTransparentCircleRadius(100);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setCenterText(name);
        //pieChart.setTouchEnabled(false);
        pieChart.invalidate();
    }
    PieData setDataToPieChart(){
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
        float yValue = percent;
        yValues.add(new PieEntry(yValue));
        PieDataSet pieDataSet = new PieDataSet(yValues,name);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.GRAY);
        return pieData;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_accept:
                break;
            case R.id.button_decline:
                dismiss();
                break;
            default:
                dismiss();
                break;
        }
    }
}
