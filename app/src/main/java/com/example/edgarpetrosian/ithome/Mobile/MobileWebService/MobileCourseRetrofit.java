package com.example.edgarpetrosian.ithome.Mobile.MobileWebService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MobileCourseRetrofit implements Callback<MobileCoursesModel> {
    public static final String URL = "http://distance-learning.ga/";
    /*
    http://distance-learning.ga/ithome/questions/java/index.php
     */

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MobileCoursesRetrofitApi coursesAPI = retrofit.create(MobileCoursesRetrofitApi.class);

        Call<MobileCoursesModel> call = coursesAPI.getData();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<MobileCoursesModel> call, Response<MobileCoursesModel> response) {
        String question;
        MobileCoursesModel modelList = response.body();
        if (response.isSuccessful()) {
            question = modelList.getQuestion();
            System.out.println(question);//link@ ura?
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<MobileCoursesModel> call, Throwable t) {
        t.printStackTrace();
    }
}
