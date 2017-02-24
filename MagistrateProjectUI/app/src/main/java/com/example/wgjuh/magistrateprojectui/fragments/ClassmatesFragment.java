package com.example.wgjuh.magistrateprojectui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.wgjuh.magistrateprojectui.Constants;
import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.SqlHelper;
import com.example.wgjuh.magistrateprojectui.adapter.ClassmatesAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClassmatesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClassmatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassmatesFragment extends AbstractFragment {
    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String GROUP_ID = "group_id";

    // Rename and change types of parameters
    private int groupId, userId;

    private ClassmatesAdapter classmatesAdapter;
    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    public ClassmatesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param groupId Parameter 1.
     * @return A new instance of fragment ClassmatesFragment.
     */
    // Rename and change types and number of parameters
    public static ClassmatesFragment newInstance(int groupId, int userId) {
        ClassmatesFragment fragment = new ClassmatesFragment();
        Bundle args = new Bundle();
        args.putInt(GROUP_ID, groupId);
        args.putInt(Constants.USER_ID, userId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupId = getArguments().getInt(GROUP_ID);
            userId = getArguments().getInt(Constants.USER_ID);
        }
        this.setLayoutId(R.layout.fragment_classmates);
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
        recyclerView = (RecyclerView)rootView.findViewById(R.id.classmates_recycler_view);
        classmatesAdapter = new ClassmatesAdapter(sqlHelper.getClassmatesByGroupId(groupId, userId));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(classmatesAdapter);
    }

    @Override
    void setClickListeners() {

    }


}
