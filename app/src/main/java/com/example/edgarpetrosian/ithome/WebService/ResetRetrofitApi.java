package com.example.edgarpetrosian.ithome.WebService;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface ResetRetrofitApi {
    @FormUrlEncoded
    @PUT("/ithome/forgot.php")
    public void updateUserPass(
            @Path("email")String email,
            @Field("password") String password,
            Callback<Response> callback
    );
}
