package com.example.edgarpetrosian.ithome.Mobile.MobileWebService;


import java.util.List;

import retrofit.http.GET;
import retrofit2.Call;

public interface MobileCoursesRetrofitApi {
    @GET("/ithome/questions/java/index.php")
    Call<MobileCoursesModel> getData();
}