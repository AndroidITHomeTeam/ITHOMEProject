package com.example.edgarpetrosian.ithome.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgarpetrosian.ithome.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {


    @Override
    public void onMapReady(GoogleMap googleMap) {
        google_Map = googleMap;
        Log.d(TAG, "onMapReady: Map is ready");
        if (mLocationPermissionsGranded) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                return;
            }
            google_Map.setMyLocationEnabled(true);

            // default my location button disable
            google_Map.getUiSettings().setMyLocationButtonEnabled(false);

            // custom my location button enabled
            init();

            // default my location button enabled
            // google_Map.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    private static final String TAG = "MapActivity";
    private static final float DEFAULT_ZOOM = 15f;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSTION_REQUSET_CODE = 1234;
    private boolean mLocationPermissionsGranded = false;

    private GoogleMap google_Map;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // widgets

    private ImageView mGps, callImage, mailImage, facebookImage, addressImage, webImage;
    private TextView call, mail, facebook, address,web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getID();
        setOnClickListener();
        getLocationPermisstion();
    }

    private void setOnClickListener() {
        callImage.setOnClickListener(this);
        mailImage.setOnClickListener(this);
        facebookImage.setOnClickListener(this);
        addressImage.setOnClickListener(this);
        webImage.setOnClickListener(this);
    }

    private void getID() {
        mGps = findViewById(R.id.gpsIconID);
        callImage = findViewById(R.id.call_image_id);
        mailImage = findViewById(R.id.mail_image_id);
        facebookImage = findViewById(R.id.facebook_image_id);
        addressImage = findViewById(R.id.address_image_id);
        webImage = findViewById(R.id.web_image_id);
        call = findViewById(R.id.call_text_id);
        mail = findViewById(R.id.mail_text_id);
        facebook = findViewById(R.id.facebook_text_id);
        address = findViewById(R.id.address_text_id);
        web = findViewById(R.id.web_text_id);

    }

    /// Get Device Location
    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranded) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM, "MyLocation");

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        google_Map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (title.equals("MyLocation")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            google_Map.addMarker(options);
        }
    }

    //  Get Location Permission
    private void getLocationPermisstion() {
        String[] permisstion = {
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "getLocationPermission: permissions ok !!!");
                mLocationPermissionsGranded = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permisstion, LOCATION_PERMISSTION_REQUSET_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permisstion,
                    LOCATION_PERMISSTION_REQUSET_CODE);
        }

    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranded = false;
        switch (requestCode) {
            case LOCATION_PERMISSTION_REQUSET_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranded = false;
                            Log.d(TAG, "onRequestPermissionsResult: not have permission ");

                            return;
                        }
                    }
                }
                Log.d(TAG, "onRequestPermissionsResult:permission is ok ");
                mLocationPermissionsGranded = true;
                initMap();
            }
        }
    }

    private void init() {
        mGps.setOnClickListener(v -> getDeviceLocation());
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        String text;
        Uri uri;
        switch (v.getId()) {
            case R.id.call_image_id:
                text = call.getText().toString();
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + text));
                startActivity(intent);
                break;
            case R.id.mail_image_id:
                try {
                    text = mail.getText().toString();
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + text));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                    intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case R.id.facebook_image_id:
                text = facebook.getText().toString();
                uri = Uri.parse(text);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                break;
            case R.id.web_image_id:
                text = web.getText().toString();
                uri = Uri.parse(text);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                break;
            case R.id.address_image_id:
                text = address.getText().toString();
                break;
        }
    }
}
/*
        map_button.setOnClickListener(view -> {
            try {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "edgarpetrosian@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                startActivity(intent);
            } catch(Exception e) {
                Toast.makeText(getContext(), "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        });

        call_button.setOnClickListener(view -> {
            String number = "tel:+37495918952";
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
        });
        facebook_button.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://www.facebook.com/edgarpetrosian");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        });
 */