package com.poras.passionate.dhope;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView splashImage = findViewById(R.id.iv_splash);
        Glide.with(this).asGif().load(R.drawable.hear).into(splashImage);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPref = getSharedPreferences("AppShare", Context.MODE_PRIVATE);
                boolean defaultValue = getResources().getBoolean(R.bool.is_logged);
                boolean isLoggedIn = sharedPref.getBoolean(getString(R.string.log_in_key), defaultValue);
                if (!isLoggedIn) {
                    Intent homeIntent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                } else {
                    Intent registerIntent = new Intent(SplashActivity.this, RegisterActivity.class);
                    startActivity(registerIntent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
