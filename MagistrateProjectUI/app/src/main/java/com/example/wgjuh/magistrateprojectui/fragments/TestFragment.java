package com.example.wgjuh.magistrateprojectui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.wgjuh.magistrateprojectui.Constants;
import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.TestClass;
import com.example.wgjuh.magistrateprojectui.adapter.TestsListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends AbstractFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // TODO: Rename and change types of parameters
    private int themeId;
    private int userId;

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;






    public TestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param themeId Parameter 1.
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestFragment newInstance(int themeId, int userId) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.THEME_ID, themeId);
        args.putInt(Constants.USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            themeId = getArguments().getInt(Constants.THEME_ID);
            userId = getArguments().getInt(Constants.USER_ID);
        }
        setLayoutId(R.layout.fragment_test);
    }

    // TODO: 26.02.2017 СДЕЛАТЬ ОТОБРАЖЕНИЕ ТЕСТОВ ПО ТЕМЕ 
    @Override
    void initFields(View rootView) {
        recyclerView = (RecyclerView)rootView.findViewById(R.id.test_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new TestsListAdapter(sqlHelper.getUserRecordsByIdAndTheme(""+userId,themeId),this));

    }

    @Override
    void setClickListeners() {

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

}
