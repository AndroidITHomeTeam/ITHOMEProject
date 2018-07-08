package com.example.edgarpetrosian.ithome.WebService;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface QuizResultRetrofitApi {
    @FormUrlEncoded
    @POST("/ithome/java_quizfinish.php")
    public void insertQuiz(
            @Field("quizresult") int quizResult,
            @Field("email") String email,
            Callback<Response> callback);
}