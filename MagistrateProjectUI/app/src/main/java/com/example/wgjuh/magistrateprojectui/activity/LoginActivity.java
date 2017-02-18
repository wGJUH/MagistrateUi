package com.example.wgjuh.magistrateprojectui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.SqlHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText email, password, recordBook;
    private Button button_login, button_registration;
    private SqlHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initFields();
        setClickListeners();
    }

    private void initFields() {
        sqlHelper = SqlHelper.getInstance(this);
        email = (EditText) findViewById(R.id.edit_text_login);
        password = (EditText) findViewById(R.id.edit_text_password);
        recordBook = (EditText)findViewById(R.id.edit_text_record_book);
        button_login = (Button) findViewById(R.id.button_login);
        button_registration = (Button) findViewById(R.id.button_registration);
    }

    private void setClickListeners() {
        button_registration.setOnClickListener(this);
        button_login.setOnClickListener(this);
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
        Intent intent = new Intent(this, UserActivity.class);
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
                prepareForRegistration();
              //  tryRegistration();
                break;
            default:
                break;
        }
    }
}
