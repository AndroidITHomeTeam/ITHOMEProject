package com.example.edgarpetrosian.ithome.Mobile.MobileLessonsFragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class Permission {
    private Activity activity;
    private Context context;

    public Permission(Context context) {
        this.context = context;
        activity= (Activity) context;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void coonectStoragePermittions(){
        int REQUEST_PERMISSTION=0;
        try {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ){
                Toast.makeText(activity, "Permission OK", Toast.LENGTH_SHORT).show();
            }else {
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                                ,Manifest.permission.READ_EXTERNAL_STORAGE}
                        ,REQUEST_PERMISSTION);
            }
        }catch (Exception e){
            e.getMessage();
        }
    }
}
