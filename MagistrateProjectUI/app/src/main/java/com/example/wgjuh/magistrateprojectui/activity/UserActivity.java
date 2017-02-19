package com.example.wgjuh.magistrateprojectui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wgjuh.magistrateprojectui.Constants;
import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.SqlHelper;
import com.example.wgjuh.magistrateprojectui.fragments.AbstractFragment;
import com.example.wgjuh.magistrateprojectui.fragments.QuestionsFragment;
import com.example.wgjuh.magistrateprojectui.fragments.TextbookFragment;
import com.example.wgjuh.magistrateprojectui.fragments.UserFragment;

import java.util.ArrayList;

import static com.example.wgjuh.magistrateprojectui.Constants.ARTICLE_PREF;
import static com.example.wgjuh.magistrateprojectui.Constants.LAST_ARTICLE;
import static com.example.wgjuh.magistrateprojectui.Constants.TAG;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AbstractFragment.OnFragmentInteractionListener, AdapterView.OnItemSelectedListener{
    private FrameLayout contentFrame;
    private UserFragment userFragment;
    private Bundle userConfig;
    private int userId;
    private Spinner spinner;
    private SqlHelper sqlHelper;
    private SharedPreferences sharedPreferences;
    private Toolbar toolbar;
    private String currentArticle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        userId = getUserConfig().getInt(Constants.USER_ID);
        userFragment = UserFragment.newInstance(Integer.toString(userId));
        getFragmentTransaction()
                .add(R.id.content_user_frame, userFragment, UserFragment.class.getName()).commit();

        /**
         * timeles comment
         */
     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        sqlHelper = SqlHelper.getInstance(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        spinner = (Spinner)navigationView.getMenu().findItem(R.id.nav_spinner).getActionView();//findViewById(R.id.nav_spinner);
        spinner.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,sqlHelper.getArticlesForGroupId(sqlHelper.getGroupIdByUserId(userId))));
        spinner.setSelection(getIndexOfCurrentSharedArticle());
        spinner.setOnItemSelectedListener(this);
    }


    private FragmentTransaction getFragmentTransaction(){
        return getSupportFragmentManager().beginTransaction();
    }
    private int getIndexOfCurrentSharedArticle(){
        ArrayList<String>articles = sqlHelper.getArticlesForGroupId(sqlHelper.getGroupIdByUserId(userId));
        String sharedArticle = getSharedPreferences(Constants.ARTICLE_PREF,MODE_PRIVATE).getString(LAST_ARTICLE,"");
        return articles.indexOf(sharedArticle);
    }
    private Bundle getUserConfig() {
        Intent intent = getIntent();
        Bundle bundle = null;
        if (intent != null) {
            bundle = intent.getExtras();
            if (bundle == null) {
                Log.e(Constants.TAG, "Error in getting bundle from intent in userActivity");
            }
        } else {
            Log.e(Constants.TAG, "Error in getting intent in userActivity");
        }
        return bundle;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() != 0){
            getSupportFragmentManager().popBackStackImmediate();
        }else super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentTransaction fragmentTransaction = getFragmentTransaction();

        int id = item.getItemId();

        if (id == R.id.nav_textbook) {
            fragmentTransaction.replace(R.id.content_user_frame,new TextbookFragment(),TextbookFragment.class.getName());
        } else if (id == R.id.nav_questions) {
            fragmentTransaction.replace(R.id.content_user_frame,QuestionsFragment.newInstance(sqlHelper.getArticlesIdForName(getSpinnerSelection())),QuestionsFragment.class.getName());
        } else if (id == R.id.nav_classmates) {

        } else if (id == R.id.nav_results) {

        } else if (id == R.id.nav_about) {

        }

        fragmentTransaction.addToBackStack(null).commit();
//        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        Log.d(Constants.TAG,"Fragment: " + getSupportFragmentManager().getFragments());
        return true;
    }

    @Override
    public void onFragmentInteraction(String tag) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sharedPreferences = getSharedPreferences(ARTICLE_PREF,MODE_PRIVATE);
        switch (view.getId()){
            case android.R.id.text1:
                sharedPreferences.edit().putString(Constants.LAST_ARTICLE, getSpinnerSelection()).apply();
                toolbar.setTitle(getSpinnerSelection());
                break;
            default:
                break;
        }
    }

    private String getSpinnerSelection() {
        return spinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
