package com.example.edgarpetrosian.ithome.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgarpetrosian.ithome.Activity.LoginActivity;
import com.example.edgarpetrosian.ithome.Activity.ProfileActivity;
import com.example.edgarpetrosian.ithome.R;
import com.example.edgarpetrosian.ithome.WebService.QuizResultRetrofitApi;
import com.example.edgarpetrosian.ithome.WebService.SignUpJsonModel;
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
public class ItemInfoFragment extends Fragment {
    public static final String URL = "http://distance-learning.ga/";
    //public static final String URL = "http://testing-project.tk/";
    private View view;
    private TextView coursText;
    private int index, count, imagePath;
    private Button btnCount;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;
    private String coursName;
    private ImageView imageView;

    public ItemInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item_info, container, false);
        getID();
        index = getArguments().getInt("position");
        coursName = getArguments().getString("coursesname");
        coursText.setText(coursName);
        imageView.setImageResource(getArguments().getInt("image"));
        btnCount.setOnClickListener(v -> {
            if (count == 5) {
                coursText.setVisibility(View.GONE);
                btnCount.setVisibility(View.GONE);
                profileQuizeRequest(count, LoginActivity.getEmail);
            }
            coursText.setText(String.valueOf(count));
            count++;
        });
        return view;
    }

    private void getID() {
        imageView = view.findViewById(R.id.imageID);
        coursText = view.findViewById(R.id.itemInfoTextID);
        btnCount = view.findViewById(R.id.btnCountID);
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
                List<SignUpJsonModel> models;
                try {
                    reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    json = reader.readLine();
                    models = mapper.readValue(json, new TypeReference<List<SignUpJsonModel>>() {
                    });
                    String code = models.get(0).getCode();
                    String message = models.get(0).getMessage();
                    progressDialog.dismiss();
                    if (code.equals("true")) {
                        showDialog("Quiz set", code, message);
                        Toast.makeText(getContext(), "" + message, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(), ProfileActivity.class));
                    }
                    if (code.equals("false")) {

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
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        }
        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }

    public void onResume() {
        super.onResume();

        // Set title bar
        ((ProfileActivity) getActivity())
                .setActionBarTitle("Course in " + coursName);

    }
}
