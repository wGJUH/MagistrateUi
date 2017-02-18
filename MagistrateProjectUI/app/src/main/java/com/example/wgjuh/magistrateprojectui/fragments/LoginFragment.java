package com.example.wgjuh.magistrateprojectui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.SqlHelper;
import com.example.wgjuh.magistrateprojectui.activity.Constants;
import com.example.wgjuh.magistrateprojectui.activity.UserActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText email, password, recordBook;
    private SqlHelper sqlHelper;
    private Button button_login, button_registration;
    private Context context;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
/*        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        initFields(v);
        setClickListeners();
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
  /*  public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        Log.d(Constants.TAG,"OnAttachContext");
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag);
    }


    private void initFields(View rootView) {
        sqlHelper = SqlHelper.getInstance(getActivity());
        email = (EditText) rootView.findViewById(R.id.edit_text_login);
        password = (EditText) rootView.findViewById(R.id.edit_text_password);
        button_login = (Button) rootView.findViewById(R.id.button_login);
        button_registration = (Button)rootView.findViewById(R.id.button_registration);
    }

    private void setClickListeners() {
        button_login.setOnClickListener(this);
        button_registration.setOnClickListener(this);
    }

    private void setErrorToEditText(EditText editText, String error) {
        Log.e(Constants.TAG, "Error in " + editText.getId() + " with text: " + error);
        editText.setError(error);
        editText.requestFocus();
    }
    /*
        private void tryRegistration() {
            String email = this.email.getText().toString();
            String password = this.password.getText().toString();
            if (sqlHelper.isEmailExist(email)) {
                if (sqlHelper.isRecordBookExist()) {

                } else setErrorToEditText(this.password, "email or password wrong");
            }else setErrorToEditText(this.password, "email already exist does you forget your password ?");
        }
        private boolean registrateNewUser(){

        }*/
    private void tryLogin() {
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        if (checkEmailAndPassword(email, password)) {
            if (isUserExist(email, password)) {
                startUserActivityForId(sqlHelper.getIdByEmail(email));
            } else setErrorToEditText(this.password, "email or password wrong");
        }
    }

    private void startUserActivityForId(int id) {
        Log.d(Constants.TAG, "Starting activity for user with id: " + id);
        Intent intent = new Intent(getActivity(), UserActivity.class);
        intent.putExtra(Constants.USER_ID, id);
        startActivity(intent);
    }

    private boolean isUserExist(String email, String password) {
        return sqlHelper.isUserExist(email, password);
    }

    private boolean checkEmailAndPassword(String email, String password) {
        boolean isOk = true;
        if (!TextUtils.isEmpty(email)) {
            if (TextUtils.isEmpty(password)) {
                setErrorToEditText(this.password, "Does you forget input password?");
                isOk = false;
            }
        } else {
            setErrorToEditText(this.email, "Does you forget input email?");
            isOk = false;
        }
        return isOk;
    }

    // TODO: 18.02.2017 написать метод валидации пароля
    private boolean isPasswordValid() {
        return true;
    }
    private void prepareForRegistration(){
        recordBook.setVisibility(View.VISIBLE);
        email.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                tryLogin();

                break;
            case R.id.button_registration:
                Log.d(Constants.TAG,"Registration");

                if (mListener != null)
                    mListener.onFragmentInteraction(this.getTag());
                //prepareForRegistration();
                //  tryRegistration();
                break;
            default:
                break;
        }
    }
}
