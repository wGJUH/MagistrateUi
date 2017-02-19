package com.example.wgjuh.magistrateprojectui.activity;


import android.support.v4.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.example.wgjuh.magistrateprojectui.Constants;
import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.fragments.AbstractFragment;
import com.example.wgjuh.magistrateprojectui.fragments.LoginFragment;
import com.example.wgjuh.magistrateprojectui.fragments.RegistrationFragment;

import static com.example.wgjuh.magistrateprojectui.Constants.*;

public class LoginActivity extends AppCompatActivity implements AbstractFragment.OnFragmentInteractionListener {
    private FrameLayout frameLoginPage;
    private LoginFragment loginFragment;
    private Animation slide_in_left,slide_out_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginFragment = new LoginFragment();
        frameLoginPage = ((FrameLayout)findViewById(R.id.frame_login_page));
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(frameLoginPage.getId(), loginFragment,TAG_LOGIN_FRAGMENT).commit();
    }



    @Override
    public void onFragmentInteraction(String tag) {
        Log.d(Constants.TAG,"tag is: " + tag);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right, android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                .replace(frameLoginPage.getId(),new RegistrationFragment(),Constants.TAG_REGISTRATION_FRAGMENT)
                .addToBackStack(Constants.TAG_REGISTRATION_FRAGMENT).commit();
    }

}
