package com.example.wgjuh.magistrateprojectui.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wgjuh.magistrateprojectui.Constants;
import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.adapter.ArticlesAdapter;
import com.example.wgjuh.magistrateprojectui.adapter.UserRecordAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;
import static com.example.wgjuh.magistrateprojectui.Constants.LAST_ARTICLE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends AbstractFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LineChart lineChart;
    // TODO: Rename and change types of parameters
    private int userId;

    private OnFragmentInteractionListener mListener;

    private TextView textView_user;
    private RecyclerView recyclerView;



    public UserFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(int userId) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_PARAM1);
        }

        Log.d(Constants.TAG,"UserFragment receive id: " + userId);
        setLayoutId(R.layout.fragment_user_activity);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    void initFields(View rootView) {
        textView_user = (TextView)rootView.findViewById(R.id.text_user_fragment);
        textView_user.setText("UserFragment receive id: " + userId);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.user_fragment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        String art = getActivity().getSharedPreferences(Constants.ARTICLE_PREF,MODE_PRIVATE).getString(LAST_ARTICLE,"");
        //recyclerView.setAdapter(new UserRecordAdapter(sqlHelper.getUserRecordsByIdAndArticle(userId, sqlHelper.getArticlesIdForName(art)),getActivity()));
        recyclerView.setAdapter(new ArticlesAdapter(sqlHelper.getArticlesForGroup(sqlHelper.getGroupIdByUserId(userId)),userId,getActivity()));
    }

    @Override
    void setClickListeners() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
/*    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
