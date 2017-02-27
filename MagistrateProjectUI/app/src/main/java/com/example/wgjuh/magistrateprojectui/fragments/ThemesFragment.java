package com.example.wgjuh.magistrateprojectui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.wgjuh.magistrateprojectui.Constants;
import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.adapter.ListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ThemesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ThemesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThemesFragment extends AbstractFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private boolean isTesting;
    private int articleId;
    private int userId;
    private RecyclerView themesRecyclerView;

    private OnFragmentInteractionListener mListener;


    public ThemesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters

    public static ThemesFragment newInstance(int articleId, boolean isTesting, int userID) {
        ThemesFragment fragment = new ThemesFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARTICLE_ID, articleId);
        args.putInt(Constants.USER_ID, userID);
        args.putBoolean(Constants.IS_TESTING, isTesting);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            articleId = getArguments().getInt(Constants.ARTICLE_ID);
            userId = getArguments().getInt(Constants.USER_ID);
            isTesting = getArguments().getBoolean(Constants.IS_TESTING);
        }
        this.setLayoutId(R.layout.fragment_themes);
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
        themesRecyclerView = (RecyclerView) rootView.findViewById(R.id.themes_recycler_view);
        themesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        themesRecyclerView.setAdapter(new ListAdapter(this, sqlHelper.getThemes(articleId), isTesting,userId));

    }

    @Override
    void setClickListeners() {

    }

}
