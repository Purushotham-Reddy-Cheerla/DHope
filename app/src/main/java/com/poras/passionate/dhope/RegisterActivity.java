package com.poras.passionate.dhope;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;
import com.poras.passionate.dhope.webservices.PostUserDataTask;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements PostUserDataTask.OnUserRegistered {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final String token = FirebaseInstanceId.getInstance().getToken();
        final EditText mName = findViewById(R.id.name);
        final EditText mContact = findViewById(R.id.contact);
        final EditText mAddress = findViewById(R.id.address);
        (findViewById(R.id.btn_register)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject userJson = new JSONObject();
                try {
                    userJson.put("name", mName.getText().toString().trim());
                    userJson.put("contact", mContact.getText().toString().trim());
                    userJson.put("address", mAddress.getText().toString().trim());
                    userJson.put("token", token);
                    PostUserDataTask task = new PostUserDataTask(RegisterActivity.this);
                    task.execute(userJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onUserRegistrationCompleted(String userID) {
        saveData(userID);
        Intent homeIntent = new Intent(this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

    private void saveData(String userID) {
        SharedPreferences sharedPref = getSharedPreferences("AppShare", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.userID), userID);
        editor.putBoolean(getString(R.string.log_in_key), true);
        editor.apply();
    }
}
