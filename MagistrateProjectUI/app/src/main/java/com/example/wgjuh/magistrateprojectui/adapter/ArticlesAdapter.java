package com.example.wgjuh.magistrateprojectui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wgjuh.magistrateprojectui.ArticleThemeValue;
import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.SqlHelper;

import java.util.ArrayList;

/**
 * Created by wGJUH on 25.02.2017.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    ArrayList<ArticleThemeValue> articleThemeValues;
    int userId;
    Context context;
    public ArticlesAdapter(ArrayList<ArticleThemeValue> articleThemeValues,int userId, Context context){
        this.articleThemeValues = articleThemeValues;
        this.userId = userId;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tests_list_recycler_view, parent,false);
        return new ArticlesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(articleThemeValues.get(position).getArticleName());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.recyclerView.setAdapter(new UserRecordAdapter(SqlHelper.getInstance(context).getUserRecordsByIdAndArticle(""+userId, articleThemeValues.get(position).getId()),context));
    }


    @Override
    public int getItemCount() {
        return articleThemeValues.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        RecyclerView recyclerView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_article_name);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.user_records_recycler_view);
        }
    }
}
