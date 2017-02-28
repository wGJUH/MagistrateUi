package com.example.wgjuh.magistrateprojectui.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.SingleRecord;
import com.example.wgjuh.magistrateprojectui.fragments.SingleTestFragment;
import com.example.wgjuh.magistrateprojectui.fragments.TestFragment;

import java.util.ArrayList;

/**
 * Created by wGJUH on 27.02.2017.
 */

public class TestsListAdapter extends RecyclerView.Adapter<TestsListAdapter.ViewHolder> {

    ArrayList<SingleRecord> singleRecords;
    Fragment fragment;
    public TestsListAdapter(ArrayList<SingleRecord> singleRecords, Fragment fragment){
        this.fragment = fragment;
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView testName,testScore;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            testName = (TextView)itemView.findViewById(R.id.text_single_test_name);
            testScore = (TextView)itemView.findViewById(R.id.text_single_test_score);
        }

        @Override
        public void onClick(View v) {
            SingleTestFragment singleTestFragment = SingleTestFragment.newInstance(singleRecords.get(getAdapterPosition()).getTestId());
            fragment.getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(R.id.content_user_frame, singleTestFragment, SingleTestFragment.class.getName())
                    .addToBackStack(fragment.getTag())
                    .commit();

        }
    }


}