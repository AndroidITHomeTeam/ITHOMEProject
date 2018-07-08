package com.example.edgarpetrosian.ithome.api;

import com.example.edgarpetrosian.ithome.WebService.model.AppResponse;
import com.example.edgarpetrosian.ithome.WebService.model.AppResponseNews;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitGetApi {
    @GET("/ithome/questions/java/index.php")
    Call<List<AppResponse>> getDate();

    @GET("/posts/data.php")
    Call<List<AppResponseNews>> getNewsDate();
}
