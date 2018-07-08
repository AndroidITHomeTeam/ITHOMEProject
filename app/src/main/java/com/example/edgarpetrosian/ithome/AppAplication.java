package com.example.edgarpetrosian.ithome;

import android.app.Application;

import com.example.edgarpetrosian.ithome.api.ConstantsAPI;
import com.example.edgarpetrosian.ithome.api.RetrofitGetApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppAplication extends Application {
    public static AppAplication appAplication;
    @Override
    public void onCreate() {
        super.onCreate();
        appAplication=this;
    }

    public RetrofitGetApi getNetworkService(){
        return
                initRetrofit(ConstantsAPI.BASE_URL).create(RetrofitGetApi.class);
    }
    public RetrofitGetApi getNetworkServiceNews(){
        return initRetrofit(ConstantsAPI.BASE_URL).create(RetrofitGetApi.class);
    }

    private Retrofit initRetrofit(String baseUrl){
        return
                new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
