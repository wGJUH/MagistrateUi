package com.example.wgjuh.magistrateprojectui.fragments;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by wGJUH on 19.02.2017.
 */

public abstract class AbstractFragment extends Fragment {
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag);
    }
    abstract void initFields(View rootView);
    abstract void setClickListeners();
}
