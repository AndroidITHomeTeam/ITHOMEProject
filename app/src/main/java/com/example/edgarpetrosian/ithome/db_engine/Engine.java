package com.example.edgarpetrosian.ithome.db_engine;

import android.content.Context;

import com.example.edgarpetrosian.ithome.WebService.model.AppResponse;
import com.example.edgarpetrosian.ithome.WebService.model.AppResponseNews;
import com.example.edgarpetrosian.ithome.db_engine.local_db.Services;

import java.util.List;

public class Engine {

    private static Engine engine = null;
    private Services services = null;
    private List<AppResponse> AppResponseModel;

    public List<AppResponse> getAppResponseModel() {
        return AppResponseModel;
    }

    public void setAppResponseModel(List<AppResponse> modelMobile) {
        this.AppResponseModel = modelMobile;
    }

    private Engine() {
    }

    public static Engine getInstance() {
        return
                (engine == null) ? engine = new Engine() :
                        engine;
    }

    private List<AppResponseNews> appResponseNewsModel;

    public List<AppResponseNews> getAppResponseNewsModel() {
        return appResponseNewsModel;
    }

    public void setAppResponseNewsModel(List<AppResponseNews> appResponseNewsModel) {
        this.appResponseNewsModel = appResponseNewsModel;
    }

    public Services getServices(Context context) {
        return (services == null) ? services = new Services(context) : services;
    }

}