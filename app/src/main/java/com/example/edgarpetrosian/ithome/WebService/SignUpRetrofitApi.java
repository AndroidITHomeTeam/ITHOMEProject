package com.example.edgarpetrosian.ithome.WebService;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface SignUpRetrofitApi {
    @FormUrlEncoded
    @POST("/ithome/register.php")
    public void insertUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            Callback<Response> callback
    );
}