package com.example.wgjuh.magistrateprojectui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.wgjuh.magistrateprojectui.Constants;
import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.TestClass;

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
    private TestClass testClass;
    // TODO: Rename and change types of parameters
    private int testId;

    private OnFragmentInteractionListener mListener;

    public TestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId Parameter 1.
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestFragment newInstance(int userId) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.TEST_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            testId = getArguments().getInt(Constants.TEST_ID);
        }
        setLayoutId(R.layout.fragment_test);
    }

    // TODO: 26.02.2017 СДЕЛАТЬ ОТОБРАЖЕНИЕ ТЕСТОВ ПО ТЕМЕ 
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
