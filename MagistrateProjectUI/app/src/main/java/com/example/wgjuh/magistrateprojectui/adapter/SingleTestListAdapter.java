package com.example.wgjuh.magistrateprojectui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.wgjuh.magistrateprojectui.TestClass;

/**
 * Created by wGJUH on 28.02.2017.
 */

public class SingleTestListAdapter extends RecyclerView.Adapter<SingleTestListAdapter.ViewHolder> {

    TestClass testClass;
    SingleTestListAdapter(TestClass testClass){
        this.testClass = testClass;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return testClass.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
