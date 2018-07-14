package com.example.edgarpetrosian.ithome.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgarpetrosian.ithome.Fragment.ResetPasswordFragment;
import com.example.edgarpetrosian.ithome.Models.ModelLogin;
import com.example.edgarpetrosian.ithome.R;
import com.example.edgarpetrosian.ithome.WebService.SignInRetrofitApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {
    public static final String URL = "http://distance-learning.ga/";
    private static final String TAG = "LoginActivity";
    private EditText password, email;
    private Button btnLogin;
    private TextView btnSignUp, btnForgot, contactUs;
    private AlertDialog.Builder alertDialog;
    private ProgressDialog progressDialog;
    private String getExtra;
    private SharedPreferences sPref;
    private SharedPreferences.Editor ed;
    private String SAVE_EMAIL = "SAVEEMAIL";
    private String SAVE_PASSWORD = "SAVEEPASSWORD";
    private String totalPreferences = "";
    private String emailPref = "";
    private String passwordPref = "";
    private FragmentTransaction fragmentTransaction;
    public static String getEmail;
    public static String getUsername;

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.item_animation_fall_down, R.anim.slide_up_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.item_animation_fall_down, R.anim.slide_up_dialog);
        // runFadeInAnimation();
        getViewID();
        getSupportActionBar().hide();
        email.setOnKeyListener(this);
        password.setOnKeyListener(this);
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnForgot.setOnClickListener(this);
        contactUs.setOnClickListener(this);
        totalPreferences = load();
        getEmail = subEmail();
        String getIntent = getIntent().getStringExtra("delete");

        if (subEmail().length() > 2 && subPassword().length() > 2 && getIntent == null) {
            progresLogin(subEmail(), subPassword());
        }
        if (getIntent != null && getIntent.equals("delete")) {
            sPref.edit()
                    .clear()
                    .apply();
        }
        getExtra = getIntent().getStringExtra("finish");
        if (getExtra != null && getExtra.equals("finish")) {
            finish();
        }

    }

    private void getViewID() {
        password = findViewById(R.id.loginPasswordeID);
        email = findViewById(R.id.loginEmailID);
        btnLogin = findViewById(R.id.btnLogInID);
        btnSignUp = findViewById(R.id.GoToSignUpID);
        btnForgot = findViewById(R.id.btnForgotID);
        contactUs = findViewById(R.id.contactUsID);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnLogInID:
                save();
                progresLogin(email.getText().toString(), password.getText().toString());
                break;
            case R.id.GoToSignUpID:
                intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.btnForgotID:
                fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack("");
                fragmentTransaction.add(R.id.loginConteiner, new ResetPasswordFragment());
                fragmentTransaction.commit();
                break;
            case R.id.contactUsID:
                if (isServicesOK()) {
                    startActivity(new Intent(LoginActivity.this, MapActivity.class));
                }
                break;
        }
    }

    public void progresLogin(String userEmail, String userPassword) {
        alertDialog = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait ...");
        progressDialog.setMessage("Connecting to server");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(URL)
                .build();

        SignInRetrofitApi api = adapter.create(SignInRetrofitApi.class);
        api.insertuserlog(userEmail, userPassword, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ObjectMapper mapper = new ObjectMapper();
                BufferedReader reader;
                String json;
                List<ModelLogin> models;

                try {
                    reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    json = reader.readLine();
                    models = mapper.readValue(json, new TypeReference<List<ModelLogin>>() {
                    });
                    String code = models.get(0).getCode();
                    String message = models.get(0).getMessage();
                    getUsername = message;
                    int quizResult = models.get(0).getQuizresult();
                    progressDialog.dismiss();
                    if (code.equals("login_true")) {
                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        intent.putExtra("model", quizResult);
                        intent.putExtra("username", message);
                        startActivity(intent);
                    }
                    if (code.equals("login_false")) {
                        showAlertDialog("wrong password or email ...", code, message);
                        progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    private void showAlertDialog(String title, final String code, String message) {
        alertDialog.setTitle(title);
        if (code.equals("login_true") || code.equals("login_false")) {
            alertDialog.setMessage(message);
            alertDialog.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());

        }
        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }

    private void save() {
        sPref = getPreferences(MODE_PRIVATE);
        ed = sPref.edit();
        ed.putString(SAVE_EMAIL, email.getText().toString());
        ed.putString(SAVE_PASSWORD, password.getText().toString());
        ed.commit();
    }

    private String load() {
        String reult;
        sPref = getPreferences(MODE_PRIVATE);
        reult = sPref.getString(SAVE_EMAIL, "");
        reult += ":" + sPref.getString(SAVE_PASSWORD, "");
        return reult;
    }

    private String subEmail() {
        int index = totalPreferences.indexOf(':');

        emailPref = totalPreferences.substring(0, index);

        return emailPref;
    }

    private String subPassword() {

        int index = totalPreferences.indexOf(':');
        passwordPref = totalPreferences.substring(index + 1);

        return passwordPref;
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services");
        int avalibale = GoogleApiAvailability
                .getInstance()
                .isGooglePlayServicesAvailable(LoginActivity.this);
        if (avalibale == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServicesOK: Google services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(avalibale)) {
            Log.d(TAG, "isServicesOK: Google services is not working");
            Toast.makeText(this, "HAMBAL", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "We can not make map", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
