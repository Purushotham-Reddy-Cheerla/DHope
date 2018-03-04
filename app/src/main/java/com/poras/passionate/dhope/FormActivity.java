package com.poras.passionate.dhope;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.poras.passionate.dhope.data.HopeContract;
import com.poras.passionate.dhope.webservices.DonateService;

public class FormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 222;
    private String mCategory;
    private double mLat;
    private double mLong;
    private int mType;
    private String mQuantity;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mType = getIntent().getIntExtra("type", 0);

        Spinner spinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText quant = findViewById(R.id.quantity);
                mQuantity = quant.getText().toString().trim();
                if (ActivityCompat.checkSelfPermission(FormActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(FormActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(FormActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);

                } else {
                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(FormActivity.this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    mLat = location.getLatitude();
                                    mLong = location.getLongitude();
                                }
                            });
                }

                insertFormData();
                /*SharedPreferences sharedPref = getSharedPreferences("AppShare", Context.MODE_PRIVATE);
                String userId = sharedPref.getString(getString(R.string.userID), "");
                DonateService donateService = new DonateService(mCategory, mQuantity, userId, mType, String.valueOf(mLat), String.valueOf(mLong));
                donateService.donateServiceTask();*/

            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void insertFormData() {
        long timeNow = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HopeContract.HopeEntry.COLUMN_CATEGORY, mCategory);
        contentValues.put(HopeContract.HopeEntry.COLUMN_TYPE, mType);
        contentValues.put(HopeContract.HopeEntry.COLUMN_QUANTITY, mQuantity);
        contentValues.put(HopeContract.HopeEntry.COLUMN_LAT, mLat);
        contentValues.put(HopeContract.HopeEntry.COLUMN_LANG, mLong);
        contentValues.put(HopeContract.HopeEntry.COLUMN_TIME, timeNow);
        Uri uri = getContentResolver().insert(HopeContract.HopeEntry.CONTENT_URI, contentValues);
        Log.e("Dataaaabase Insertion", uri.toString());
        finish();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(FormActivity.this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    mLat = location.getLatitude();
                                    mLong = location.getLongitude();
                                }
                            });
                } else {
                    finish();
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                mCategory = "Blood";
                break;
            case 1:
                mCategory = "Food";
                break;
            case 2:
                mCategory = "Clothes";
                break;
            default:
                mCategory = "Blood";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
