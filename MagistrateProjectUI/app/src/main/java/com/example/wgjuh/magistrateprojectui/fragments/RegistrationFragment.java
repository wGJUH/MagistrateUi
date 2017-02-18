package com.example.wgjuh.magistrateprojectui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.SqlHelper;
import com.example.wgjuh.magistrateprojectui.activity.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistrationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText editTextRecorBook, editTextEmail, editTextPassword, editTextRepeatPassword, editTextFio;
    private TextView textViewRecordBook;
    private Button buttonNext, buttonRegistration;
    private SqlHelper sqlHelper;
    private ViewSwitcher viewSwitcher;

    private Animation slide_in_left, slide_out_right;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
/*        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlHelper = SqlHelper.getInstance(getActivity());
 /*       if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);
        initFields(rootView);
        setClickListeners();
        return rootView;
    }

    private void initFields(View rootView) {
        editTextRecorBook = (EditText) rootView.findViewById(R.id.edit_text_record_book);
        editTextEmail = (EditText) rootView.findViewById(R.id.edit_text_login);
        editTextPassword = (EditText) rootView.findViewById(R.id.edit_text_password);
        editTextRepeatPassword = (EditText) rootView.findViewById(R.id.edit_text_password_repeat);
        editTextFio = (EditText) rootView.findViewById(R.id.edit_text_fio);
        textViewRecordBook = (TextView)rootView.findViewById(R.id.textview_record_book);
        buttonNext = (Button) rootView.findViewById(R.id.button_next);
        buttonRegistration = ((Button) rootView.findViewById(R.id.button_registration));
        viewSwitcher = ((ViewSwitcher) rootView.findViewById(R.id.switcher_registration));
    }

    private void setClickListeners() {
        buttonNext.setOnClickListener(this);
        buttonRegistration.setOnClickListener(this);
    }
    // TODO: Rename method, update argument and hook method into UI event
/*    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

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

    private boolean isRecorBookExist(String recordBookNumber) {
        return sqlHelper.isRecordBookExist(recordBookNumber);
    }

    public String getEditTextRecorBookText() {
        return editTextRecorBook.getText().toString();
    }

    public String getEditTextEmailText() {
        return editTextEmail.getText().toString();
    }

    public String getEditTextPasswordText() {
        return editTextPassword.getText().toString();
    }

    public String getEditTextRepeatPasswordText() {
        return editTextRepeatPassword.getText().toString();
    }

    public String getEditTextFioText() {
        return editTextFio.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_next:
                verifyRecordBookNumber();
                break;
            case R.id.button_registration:
                tryBeginRegistration();
                break;
            default:
                break;
        }
        Log.d(Constants.TAG, "RecordBook: " + isRecorBookExist(getEditTextRecorBookText()));
    }

    private void tryBeginRegistration() {

        if (checkFioOk() && checkEmailOk() && checkPasswordOk() && checkRepeatPasswordOk()) {
            if (sqlHelper.addNewUser(getEditTextFioText(), getEditTextEmailText(), getEditTextPasswordText(), getEditTextRecorBookText()))
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            else
                Toast.makeText(getActivity(), "Извините при регистрации произошел сбои, попробуйте еще раз", Toast.LENGTH_SHORT).show();
        }
    }

    private void setErrorToEditText(EditText editText, String error) {
        Log.e(Constants.TAG, "Error in " + editText.getId() + " with text: " + error);
        editText.setError(error);
        editText.requestFocus();
    }

    private boolean checkEmailOk() {
        boolean isOk = false;
        if (!TextUtils.isEmpty(getEditTextEmailText())) {
            if (!sqlHelper.isEmailExist(getEditTextEmailText())) {
                isOk = true;
            } else setErrorToEditText(editTextEmail, "Email зарегистрирован");
        } else {
            setErrorToEditText(editTextEmail, "Введите Email");
        }
        return isOk;
    }

    private boolean checkPasswordOk() {
        boolean isOk = false;
        if (!TextUtils.isEmpty(getEditTextPasswordText()))
            isOk = true;
        else setErrorToEditText(editTextPassword, "Введите желаемый пароль");
        return isOk;
    }

    private boolean checkRepeatPasswordOk() {
        boolean isOk = false;
        if (!TextUtils.isEmpty(getEditTextRepeatPasswordText())) {
            if (getEditTextPasswordText().equals(getEditTextRepeatPasswordText()))
                isOk = true;
            else setErrorToEditText(editTextRepeatPassword, "Пароли не совпадают");
        } else setErrorToEditText(editTextRepeatPassword, "Повторите пароль");
        return isOk;
    }

    private boolean checkFioOk() {
        boolean isOk = false;
        if (!TextUtils.isEmpty(getEditTextFioText()))
            isOk = true;
        else setErrorToEditText(editTextFio, "Введите ФИО");
        return isOk;
    }


    private void verifyRecordBookNumber() {
        if (TextUtils.isEmpty(getEditTextRecorBookText())) {
            setErrorToEditText(editTextRecorBook, "Введите номер зачетки");
        } else if (sqlHelper.isRecordBookExist(getEditTextRecorBookText())) {
            if (!sqlHelper.isRecordBookRegistr(getEditTextRecorBookText())) {
                changeToRegForm();
            } else
                setErrorToEditText(editTextRecorBook, "Данный номер зачетной книжки уже зарегистрирован");
        } else
            setErrorToEditText(editTextRecorBook, "Данного номера нет в базе, обратитесь за помощью к руководителю");
    }

    private void changeToRegForm() {
        setAnimation();
        textViewRecordBook.setText("Номер Вашей зачетки: " + getEditTextRecorBookText());
        viewSwitcher.showNext();

    }

    private void setAnimation() {
        slide_in_left = AnimationUtils.loadAnimation(getActivity(),
                android.R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(getActivity(),
                android.R.anim.slide_out_right);

        viewSwitcher.setInAnimation(slide_in_left);
        viewSwitcher.setOutAnimation(slide_out_right);
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
        void onFragmentInteraction(Uri uri);
    }
}
