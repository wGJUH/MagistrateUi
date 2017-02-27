package com.example.wgjuh.magistrateprojectui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.SingleRecord;

import java.util.ArrayList;

/**
 * Created by wGJUH on 27.02.2017.
 */

public class TestsListAdapter extends RecyclerView.Adapter<TestsListAdapter.ViewHolder> {

    ArrayList<SingleRecord> singleRecords;

    public TestsListAdapter(ArrayList<SingleRecord> singleRecords){
        this.singleRecords = singleRecords;
    }
    //**
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_tests_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.testName.setText(singleRecords.get(position).getName());
        holder.testScore.setText(Float.toString(singleRecords.get(position).getRecord())+"%");
    }

    @Override
    public int getItemCount() {
        return singleRecords.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView testName,testScore;
        public ViewHolder(View itemView) {
            super(itemView);
            testName = (TextView)itemView.findViewById(R.id.text_single_test_name);
             testScore = (TextView)itemView.findViewById(R.id.text_single_test_score);
        }
    }


}