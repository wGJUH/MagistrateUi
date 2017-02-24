package com.example.wgjuh.magistrateprojectui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.SqlHelper;

/**
 * Created by wGJUH on 19.02.2017.
 */

public abstract class AbstractFragment extends Fragment {
    int layoutId;
    public SqlHelper sqlHelper;
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag);
    }
    abstract void initFields(View rootView);
    abstract void setClickListeners();

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sqlHelper = SqlHelper.getInstance(getActivity());
        View rootView = inflater.inflate(layoutId, container, false);
        initFields(rootView);
        setClickListeners();
        return rootView;
    }
}
