package com.example.wgjuh.magistrateprojectui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wgjuh.magistrateprojectui.Constants;
import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.SqlHelper;
import com.example.wgjuh.magistrateprojectui.adapter.ListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TextbookFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TextbookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TextbookFragment extends AbstractFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int articleId;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    private SqlHelper sqlHelper;

    public TextbookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param articleId Parameter 1.
     * @return A new instance of fragment TextbookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TextbookFragment newInstance(int articleId) {
        TextbookFragment fragment = new TextbookFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARTICLE_ID, articleId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            articleId = getArguments().getInt(Constants.ARTICLE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_textbook, container, false);
        initFields(rootView);
        return rootView;

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
        sqlHelper = SqlHelper.getInstance(getActivity());
        recyclerView = (RecyclerView)rootView.findViewById(R.id.library_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d(Constants.TAG,"TextBookRecive articleId = " + articleId);
        recyclerView.setAdapter(new ListAdapter(this,sqlHelper.getBooksForArticle(articleId)));
    }

    @Override
    void setClickListeners() {

    }

}
