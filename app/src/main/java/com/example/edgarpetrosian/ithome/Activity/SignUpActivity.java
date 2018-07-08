package com.example.edgarpetrosian.ithome.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.edgarpetrosian.ithome.R;
import com.example.edgarpetrosian.ithome.WebService.SignUpJsonModel;
import com.example.edgarpetrosian.ithome.WebService.SignUpRetrofitApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String URL = "http://distance-learning.ga/";

    private EditText name, email, password, confirmPassword;
    private Button btnSignUp;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getViewID();
        getSupportActionBar().hide();
        btnSignUp.setOnClickListener(this);
    }

    private void getViewID() {
        name = findViewById(R.id.nameID);
        email = findViewById(R.id.emailID);
        password = findViewById(R.id.passwordID);
        confirmPassword = findViewById(R.id.comfirmPasswordID);
        btnSignUp = findViewById(R.id.btnSignUpID);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUpID:
                if (confirmPassword.getText().toString().equals(password.getText().toString())) {
                    insertInToDataBase(name.getText().toString(), email.getText().toString(), password.getText().toString());
                }
                break;
        }
    }

    private void insertInToDataBase(String name, String email, String password) {
        alertDialog = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait ...");
        progressDialog.setMessage("Connecting to server");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(URL)
                .build();
        SignUpRetrofitApi api = adapter.create(SignUpRetrofitApi.class);
        api.insertUser(name, email, password, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ObjectMapper mapper = new ObjectMapper();
                BufferedReader reader;
                String json;
                Intent intent;
                List<SignUpJsonModel> jsonModels;
                try {
                    reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    json = reader.readLine();
                    jsonModels = mapper.readValue(json, new TypeReference<List<SignUpJsonModel>>() {
                    });
                    String code = jsonModels.get(0).getCode();
                    String message = jsonModels.get(0).getMessage();
                    progressDialog.dismiss();
                    if (code.equals("reg_true")) {
                        showDialog("Registration", code, message);
                    }
                    if (code.equals("reg_false")) {
                        showDialog("Error", code, message);
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

    public void showDialog(String title, final String code, String message) {
        alertDialog.setTitle(title);
        if (code.equals("reg_true") || code.equals("reg_false")) {
            alertDialog.setMessage(message);
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (code.equals("reg_true")) {
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    }
                }
            });

        }
        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }
}
