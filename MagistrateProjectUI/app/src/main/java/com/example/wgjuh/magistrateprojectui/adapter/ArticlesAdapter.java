package com.example.wgjuh.magistrateprojectui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
    SqlHelper sqlHelper;
    public ArticlesAdapter(ArrayList<ArticleThemeValue> articleThemeValues,int userId, Context context){
        this.articleThemeValues = articleThemeValues;
        this.userId = userId;
        this.context = context;
        sqlHelper = SqlHelper.getInstance(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_tests_list_recycler_view_item, parent,false);
        return new ArticlesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(articleThemeValues.get(position).getArticleName());
        holder.procent = sqlHelper.getTestResultForArticle(articleThemeValues.get(position).getId(),userId);
        holder.articleProgress.setText(Integer.toString(holder.procent)+"%");
        holder.progressBar.setProgress(holder.procent);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.recyclerView.setAdapter(new UserRecordAdapter(sqlHelper.getUserRecordsByIdAndArticle(""+userId, articleThemeValues.get(position).getId()),context, userId));
    }


    @Override
    public int getItemCount() {
        return articleThemeValues.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView, articleProgress;
        ProgressBar progressBar;
        RecyclerView recyclerView;
        int procent = 0;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_article_name);
            articleProgress = (TextView) itemView.findViewById(R.id.tv_progress_horizontal);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBarSingleTest);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.user_records_recycler_view);
        }
    }
}
