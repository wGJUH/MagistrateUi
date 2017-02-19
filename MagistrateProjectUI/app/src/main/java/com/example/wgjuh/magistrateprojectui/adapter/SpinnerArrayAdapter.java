package com.example.wgjuh.magistrateprojectui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wgjuh.magistrateprojectui.ArticleThemeValue;
import com.example.wgjuh.magistrateprojectui.Constants;
import com.example.wgjuh.magistrateprojectui.R;

import java.util.ArrayList;

/**
 * Created by wGJUH on 19.02.2017.
 */

public class SpinnerArrayAdapter extends ArrayAdapter<ArticleThemeValue> {

    ArrayList<ArticleThemeValue> articleThemeValues;
    Context context;
    public SpinnerArrayAdapter(Context context, ArrayList<ArticleThemeValue> articleThemeValues) {
        super(context,R.layout.fragment_themes_list_single_item, articleThemeValues);
        this.context = context;
        this.articleThemeValues = articleThemeValues;
    }

    @Override
    public int getCount() {
        return articleThemeValues.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.spinner_single_item, null);
        }
        Log.d(Constants.TAG, "getView: " + articleThemeValues.get(position).getArticleName());
        ((TextView)convertView.findViewById(R.id.single_text_item)).setText(articleThemeValues.get(position).getArticleName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.spinner_single_item, null);
        }
        Log.d(Constants.TAG, "getView: " + articleThemeValues.get(position).getArticleName());
        ((TextView)convertView.findViewById(R.id.single_text_item)).setText(articleThemeValues.get(position).getArticleName());
        return convertView;
    }
}
