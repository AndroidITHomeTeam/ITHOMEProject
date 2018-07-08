package com.example.edgarpetrosian.ithome.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgarpetrosian.ithome.AppAplication;
import com.example.edgarpetrosian.ithome.WebService.model.AppResponse;
import com.example.edgarpetrosian.ithome.Fragment.AppInfoFragment;
import com.example.edgarpetrosian.ithome.Fragment.ContactsFragment;
import com.example.edgarpetrosian.ithome.Fragment.CoursesFragment;
import com.example.edgarpetrosian.ithome.Fragment.ForumFragment;
import com.example.edgarpetrosian.ithome.Fragment.NewsJavaFragment;
import com.example.edgarpetrosian.ithome.R;
import com.example.edgarpetrosian.ithome.db_engine.Engine;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private FragmentTransaction fragmentTransaction;
    private ImageView checkedImage;
    private TextView profileName;
    private NavigationView navigationView;
    private View headerView;
    public static int quiz;
    public static Toolbar toolbar;

    private void runFadeInAnimation() {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        a.reset();
        CoordinatorLayout rl = findViewById(R.id.CoordinatorLayoutProfileActivityID);
        rl.clearAnimation();
        rl.startAnimation(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        runFadeInAnimation();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        checkedImage = headerView.findViewById(R.id.ptichkaID);
        profileName = headerView.findViewById(R.id.profileNameID);
        profileName.setText(getProfileName());
        quiz = getObject();
        checkCourse();
        AppAplication
                .appAplication
                .getNetworkService()
                .getDate()
                .enqueue(new Callback<List<AppResponse>>() {
                    @Override
                    public void onResponse(Call<List<AppResponse>> call, retrofit2.Response<List<AppResponse>> response) {
                        if (response.body() != null) {
                            Engine.getInstance().setAppResponseModel(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AppResponse>> call, Throwable t) {
                        t.getMessage();
                    }
                });
        Toast.makeText(this, "" + getObject(), Toast.LENGTH_SHORT).show();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.nav_curses:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack("");
                    fragmentTransaction.replace(R.id.conteyner, new CoursesFragment());
                    fragmentTransaction.commit();
                    item.setChecked(true);
                    drawer.closeDrawers();
                    break;
                case R.id.nav_news:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack(NewsJavaFragment.class.getName());
                    fragmentTransaction.replace(R.id.conteyner, new NewsJavaFragment());
                    fragmentTransaction.commit();
                    item.setChecked(true);
                    drawer.closeDrawers();
                    break;
                case R.id.nav_forum:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack("");
                    fragmentTransaction.replace(R.id.conteyner, new ForumFragment());
                    fragmentTransaction.commit();
                    item.setChecked(true);
                    drawer.closeDrawers();
                    break;
                case R.id.nav_contacts:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack("");
                    fragmentTransaction.replace(R.id.conteyner, new ContactsFragment());
                    fragmentTransaction.commit();
                    item.setChecked(true);
                    drawer.closeDrawers();
                    break;
                case R.id.nav_app_info:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack("");
                    fragmentTransaction.replace(R.id.conteyner, new AppInfoFragment());
                    fragmentTransaction.commit();
                    item.setChecked(true);
                    drawer.closeDrawers();
                    break;
                case R.id.nav_logout:
                    intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    intent.putExtra("delete", "delete");
                    startActivity(intent);
                    break;

            }

            return false;
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
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

    private int getObject() {
        int quiz = getIntent().getIntExtra("test correctScore", 0);
        return quiz;
    }

    private String getProfileName() {
        String userName = LoginActivity.getUsername.toUpperCase();
        if (getIntent().getStringExtra("username") != null) {
            userName = getIntent().getStringExtra("username").toUpperCase();
            return userName;
        }
        return userName;
    }

    private void checkCourse() {
        if (getObject() != 10) {
            checkedImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}


