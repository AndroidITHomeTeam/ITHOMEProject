package com.example.edgarpetrosian.ithome.Mobile.MobileLessonsFragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgarpetrosian.ithome.Activity.LoginActivity;
import com.example.edgarpetrosian.ithome.Activity.ProfileActivity;
import com.example.edgarpetrosian.ithome.WebService.model.AppResponse;
import com.example.edgarpetrosian.ithome.R;
import com.example.edgarpetrosian.ithome.db_engine.Engine;
import com.example.edgarpetrosian.ithome.WebService.QuizResult;
import com.example.edgarpetrosian.ithome.WebService.QuizResultRetrofitApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class JavaTestsFragment extends Fragment implements View.OnClickListener {
    public static final String URL = "http://distance-learning.ga/";//http://distance-learning.ga/ithome/java_quizfinish.php
    private TextView scoreView, questionView;
    private Button answare1, answare2, answare3, answare4;
    private View view;
    private List<AppResponse> list = new ArrayList<>();
    private ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;
    private int count = 0;
    private int score;

    public JavaTestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_java_tests, container, false);
        getID();
        setOnClickListen();
        scoreView.setText("");
        list.addAll(Engine.getInstance().getAppResponseModel());

        questionView.setText(list.get(count).getQuestion());
        if (count == 0) {
            answare1.setText(list.get(count).getOption_a());
            answare2.setText(list.get(count).getOption_b());
            answare3.setText(list.get(count).getOption_c());
            answare4.setText(list.get(count).getOption_d());
        }
        return view;
    }

    private void getID() {
        scoreView = view.findViewById(R.id.countID);
        questionView = view.findViewById(R.id.questionID);
        answare1 = view.findViewById(R.id.answare1ID);
        answare2 = view.findViewById(R.id.answare2ID);
        answare3 = view.findViewById(R.id.answare3ID);
        answare4 = view.findViewById(R.id.answare4ID);
    }

    private void setOnClickListen() {
        answare1.setOnClickListener(this);
        answare2.setOnClickListener(this);
        answare3.setOnClickListener(this);
        answare4.setOnClickListener(this);
    }

    public void onResume() {
        super.onResume();
        // Set title bar
        ((ProfileActivity) getActivity())
                .setActionBarTitle("Java Test Level 1");
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.answare1ID:
                count++;
                if (count <= list.size() - 1) {
                    examination(answare1, count);
                } else if (count <= 2) {
                    if (list.get(count).getOption_d().equals(list.get(count).getAnswer())) {
                        Toast.makeText(getContext(), "ChishtPatasxanna u verjinna", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "sxalPatasxanna u verjinna", Toast.LENGTH_LONG).show();

                    }
                } else {
                    profileQuizeRequest(score, LoginActivity.getEmail);
                }

                break;
            case R.id.answare2ID:
                count++;
                if (count <= list.size() - 1) {
                    examination(answare2, count);
                } else if (count <= 2) {
                    if (list.get(count).getOption_d().equals(list.get(count).getAnswer())) {
                        Toast.makeText(getContext(), "ChishtPatasxanna u verjinna", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "sxalPatasxanna u verjinna", Toast.LENGTH_LONG).show();

                    }
                } else {
                    profileQuizeRequest(score, LoginActivity.getEmail);
                }

                break;
            case R.id.answare3ID:
                count++;
                if (count <= list.size() - 1) {
                    examination(answare3, count);
                } else if (count <= 2) {
                    if (list.get(count).getOption_d().equals(list.get(count).getAnswer())) {
                        Toast.makeText(getContext(), "ChishtPatasxanna u verjinna", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "sxalPatasxanna u verjinna", Toast.LENGTH_LONG).show();

                    }
                } else {
                    profileQuizeRequest(score, LoginActivity.getEmail);
                }

                break;
            case R.id.answare4ID:
                count++;
                if (count <= list.size() - 1) {
                    examination(answare4, count);
                } else if (count <= 2) {
                    if (list.get(count).getOption_d().equals(list.get(count).getAnswer())) {
                        Toast.makeText(getContext(), "ChishtPatasxanna u verjinna", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "sxalPatasxanna u verjinna", Toast.LENGTH_LONG).show();

                    }
                } else {
                    profileQuizeRequest(score, LoginActivity.getEmail);
                }

                break;
        }
    }

    private void profileQuizeRequest(int quiz, String email) {
        alertDialog = new AlertDialog.Builder(getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait ...");
        progressDialog.setMessage("Connecting to server");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(URL)
                .build();
        QuizResultRetrofitApi api = adapter.create(QuizResultRetrofitApi.class);
        api.insertQuiz(quiz, email, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ObjectMapper mapper = new ObjectMapper();
                BufferedReader reader;
                String json;
                List<QuizResult> models;
                try {
                    reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    json = reader.readLine();
                    models = mapper.readValue(json, new TypeReference<List<QuizResult>>() {
                    });
                    String code = models.get(0).getCode();
                    String message = models.get(0).getMessage();
                    progressDialog.dismiss();
                    if (code.equals("true")) {
                        showDialog("Quiz set", code, message);
                        Toast.makeText(getContext(), "true " + message, Toast.LENGTH_LONG).show();
                    }
                    if (code.equals("false")) {
                        Toast.makeText(getContext(), "false " + message, Toast.LENGTH_LONG).show();
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
        if (code.equals("true") || code.equals("false")) {
            alertDialog.setMessage(message);
            alertDialog.setPositiveButton("Ok", (dialog, which) -> {
                dialog.dismiss();
                if (code.equals("true")) {
                    Intent intent = new Intent(getContext(), ProfileActivity.class);
                    intent.putExtra("test score", score);
                    startActivity(intent);
                }
            });

        }
        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }

    private void examination(Button button, int count) {

        if (button.getText().toString().equals(list.get(count - 1).getAnswer())) {
            //  Toast.makeText(getContext(), "jisht ", Toast.LENGTH_LONG).show();
            questionView.setText(list.get(count).getQuestion());
            answare1.setText(list.get(count).getOption_a());
            answare2.setText(list.get(count).getOption_b());
            answare3.setText(list.get(count).getOption_c());
            answare4.setText(list.get(count).getOption_d());
            score++;
            Toast.makeText(getContext(), "Score " + score, Toast.LENGTH_LONG).show();
        } else {
            questionView.setText(list.get(count).getQuestion());
            answare1.setText(list.get(count).getOption_a());
            answare2.setText(list.get(count).getOption_b());
            answare3.setText(list.get(count).getOption_c());
            answare4.setText(list.get(count).getOption_d());
        }
        scoreView.setText("" + score);
    }

    private void answaresTruth(Button button, int count) {
        count++;
        if (count <= list.size() - 1) {
            examination(button, count);
        } else if (count <= 2) {
            if (list.get(count).getOption_d().equals(list.get(count).getAnswer())) {
                Toast.makeText(getContext(), "ChishtPatasxanna u verjinna", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "sxalPatasxanna u verjinna", Toast.LENGTH_LONG).show();

            }
        } else {
            profileQuizeRequest(score, LoginActivity.getEmail);
        }

    }
}