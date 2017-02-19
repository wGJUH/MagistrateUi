package com.example.wgjuh.magistrateprojectui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wgjuh.magistrateprojectui.ArticleThemeValue;
import com.example.wgjuh.magistrateprojectui.Constants;
import com.example.wgjuh.magistrateprojectui.R;

import java.util.ArrayList;

/**
 * Created by wGJUH on 19.02.2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    ArrayList<ArticleThemeValue> items;
    public ListAdapter( ArrayList<ArticleThemeValue>  items){
        Log.d(Constants.TAG,"ListAdpater: " + items.size());
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(Constants.TAG,"onCreateViewHolder ");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_themes_list_single_item,parent,false);
        ViewHolder viewHolder = new ViewHolder((TextView) v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(Constants.TAG,"onBindViewHolder ");
        holder.textView.setText(items.get(position).getArticleName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public ViewHolder(TextView itemView) {
            super(itemView);
            textView = itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //if()
        }
    }
}
