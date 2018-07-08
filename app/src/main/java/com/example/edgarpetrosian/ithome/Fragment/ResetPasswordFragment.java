package com.example.edgarpetrosian.ithome.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.edgarpetrosian.ithome.R;
import com.example.edgarpetrosian.ithome.WebService.ResetRetrofitApi;
import com.example.edgarpetrosian.ithome.WebService.SignUpJsonModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ResetPasswordFragment extends Fragment implements View.OnClickListener {
    public static final String URL = "http://distance-learning.ga/";
    private View view;
    private EditText email, password, confirmPassword;
    private Button btnResetPassword;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        getID();
        btnResetPassword.setOnClickListener(this);
        return view;
    }

    private void getID() {
        email = view.findViewById(R.id.profileEmailID);
        password = view.findViewById(R.id.profilePasswordID);
        confirmPassword = view.findViewById(R.id.profileConfirmPasswordID);
        btnResetPassword = view.findViewById(R.id.btnResetPasswordID);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnResetPasswordID:
                if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                    insertNewPasswordInToDB(email.getText().toString(), password.getText().toString());
                }
                break;
        }
    }

    private void insertNewPasswordInToDB(String email, String password) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(URL)
                .build();
        ResetRetrofitApi api = adapter.create(ResetRetrofitApi.class);
        api.updateUserPass(email, password, new Callback<Response>() {
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
                    if (code.equals("reg_true")) {
                        Toast.makeText(getContext(), "Your new password is success..." + message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

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
}
