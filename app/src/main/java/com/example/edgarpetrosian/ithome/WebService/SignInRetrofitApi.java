package com.example.edgarpetrosian.ithome.WebService;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface SignInRetrofitApi {
    @FormUrlEncoded
    @POST("/ithome/login.php")
    public  void insertuserlog(
            @Field("email")String email,
            @Field("password")String password,
            Callback<Response> callback
    );
}
