package com.example.wgjuh.magistrateprojectui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wgjuh.magistrateprojectui.Constants;
import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.TestClass;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SingleTestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SingleTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleTestFragment extends AbstractFragment {
    private int testId;
    private OnFragmentInteractionListener mListener;
    private TestClass testClass;


    public SingleTestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param testId Parameter 1.
     * @return A new instance of fragment SingleTestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingleTestFragment newInstance(int testId) {
        SingleTestFragment fragment = new SingleTestFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.TEST_ID, testId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            testId = getArguments().getInt(Constants.TEST_ID);
        }
        setLayoutId(R.layout.fragment_single_test);
    }

    @Override
    void initFields(View rootView) {
        testClass = new TestClass(testId,getActivity());
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
