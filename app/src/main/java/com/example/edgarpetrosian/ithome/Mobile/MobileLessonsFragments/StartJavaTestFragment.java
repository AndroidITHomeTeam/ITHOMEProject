package com.example.edgarpetrosian.ithome.Mobile.MobileLessonsFragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgarpetrosian.ithome.Activity.LoginActivity;
import com.example.edgarpetrosian.ithome.Activity.ProfileActivity;
import com.example.edgarpetrosian.ithome.R;
import com.example.edgarpetrosian.ithome.WebService.QuizResult;
import com.example.edgarpetrosian.ithome.WebService.QuizResultRetrofitApi;
import com.example.edgarpetrosian.ithome.WebService.model.AppResponse;
import com.example.edgarpetrosian.ithome.db_engine.Engine;
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
public class StartJavaTestFragment extends Fragment implements View.OnClickListener {
    public static final String URL = "http://distance-learning.ga/";//http://distance-learning.ga/ithome/java_quizfinish.php
    private TextView wromgScoreView, correctScoreView, questionView;
    private Button answare1, answare2, answare3, answare4, btnStart;
    private View view;
    private List<AppResponse> list = new ArrayList<>();
    private ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;
    private Toolbar toolbar;
    private int count, click;
    private int correctScore, wrongScore;
    private LinearLayout correctScoreViewID, wrongScoreViewID, linearTextID, linearButtonID;
    private boolean clicked = true;
    private RelativeLayout relativeLayout;

    public StartJavaTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_start_java_test, container, false);
        getID();
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        getArguments().getInt("position");
        getArguments().getString("coursesname");
        getArguments().getInt("image");
        setOnClickListen();
        correctScoreView.setText("0");
        wromgScoreView.setText("0");
        list.addAll(Engine.getInstance().getAppResponseModel());
        return view;
    }

    private void getID() {
        relativeLayout = view.findViewById(R.id.javaTestLevel1RelativeID);
        correctScoreView = view.findViewById(R.id.corrctQuizID);
        wromgScoreView = view.findViewById(R.id.wrongQuizID);
        questionView = view.findViewById(R.id.questionID);
        answare1 = view.findViewById(R.id.answare1ID);
        answare2 = view.findViewById(R.id.answare2ID);
        answare3 = view.findViewById(R.id.answare3ID);
        answare4 = view.findViewById(R.id.answare4ID);
        btnStart = view.findViewById(R.id.btnStartTestID);
        correctScoreViewID = view.findViewById(R.id.correctScoreViewID);
        wrongScoreViewID = view.findViewById(R.id.wrongScoreViewID);
        linearButtonID = view.findViewById(R.id.linearButtonID);
        linearTextID = view.findViewById(R.id.linearTextID);
//        toolbar = (Toolbar) view.findViewById(R.id.generalToolBarID);
    }

    private void setOnClickListen() {
        btnStart.setOnClickListener(this);
        answare1.setOnClickListener(this);
        answare2.setOnClickListener(this);
        answare3.setOnClickListener(this);
        answare4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.answare1ID:
                click++;
                examination(answare1, count);
                count++;
                clicked = true;
                btnClickableFalse(clicked);
                break;
            case R.id.answare2ID:
                click++;
                examination(answare2, count);
                count++;
                clicked = true;
                btnClickableFalse(clicked);
                break;
            case R.id.answare3ID:
                click++;
                examination(answare3, count);
                count++;
                clicked = true;
                btnClickableFalse(clicked);
                break;
            case R.id.answare4ID:
                click++;
                examination(answare4, count);
                count++;
                clicked = true;
                btnClickableFalse(clicked);
                break;
            case R.id.btnStartTestID:
                getBtnVisable();
                if (count < list.size()) {
                    Toast.makeText(getContext(), "count -> " + count, Toast.LENGTH_SHORT).show();
                    chooseAnswerOfTest(count);
                }
                btnStart.setText("Next");
                if (count > 0) {
                    clicked = false;
                    btnClickableFalse(clicked);
                }
                if (click == list.size()) {
                    btnStart.setText("Done");
                }
                if (btnStart.getText().toString().equals("Done")) {
                    profileQuizeRequest(correctScore, LoginActivity.getEmail);
                }
                break;

        }

    }

    // THis method set the btn clickable or not
    private void btnClickableFalse(boolean clicks) {
        if (clicks) {
            answare1.setClickable(false);
            answare2.setClickable(false);
            answare3.setClickable(false);
            answare4.setClickable(false);
        } else {
            answare1.setClickable(true);
            answare2.setClickable(true);
            answare3.setClickable(true);
            answare4.setClickable(true);
        }
    }

    // After clicked on START btn , all views going to  VISIBLE
    private void getBtnVisable() {
        linearTextID.setVisibility(View.VISIBLE);
        linearButtonID.setVisibility(View.VISIBLE);
        correctScoreViewID.setVisibility(View.VISIBLE);
        wrongScoreViewID.setVisibility(View.VISIBLE);
    }

    // THis Method checking , are user clicked true answer , and calculate quiz
    private void examination(Button button, int count) {
        if (count < list.size()) {
            if (button.getText().toString().equals(list.get(count).getAnswer())) {
                correctScore++;
            } else {
                wrongScore++;
            }
        }
        correctScoreView.setText("" + correctScore);
        wromgScoreView.setText("" + wrongScore);
    }

    // THis Method choosing next question and answares from array
    private void chooseAnswerOfTest(int count) {
        questionView.setText(list.get(count).getQuestion());
        answare1.setText(list.get(count).getOption_a());
        answare2.setText(list.get(count).getOption_b());
        answare3.setText(list.get(count).getOption_c());
        answare4.setText(list.get(count).getOption_d());
    }

    public void onResume() {
        super.onResume();
        // Set title bar
        ((ProfileActivity) getActivity())
                .setActionBarTitle("Test Level 1");
    }

    // This Method set the Quiz inTo DATA BASE
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
                        showDialog("Quiz set", code, correctScore);
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

    //THis method showing alert dialog if yor Quiz is set in to Data Base and going to Profile Activity
    public void showDialog(String title, final String code, int quiz) {
        alertDialog.setTitle(title);
        if (code.equals("true") || code.equals("false")) {
            alertDialog.setMessage("You have collected " + quiz + " points");
            alertDialog.setPositiveButton("Ok", (dialog, which) -> {
                dialog.dismiss();
                if (code.equals("true")) {
                    Intent intent = new Intent(getContext(), ProfileActivity.class);
                    intent.putExtra("test correctScore", correctScore);
                    startActivity(intent);
                }
            });

        }
        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }
}
