package com.poras.passionate.dhope.webservices;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by purus on 3/4/2018.
 */

public class DonateService {
    private int mType;
    private String mCategory;
    private String mQuantity;
    private String mUserID;
    private String mLat;
    private String mLang;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public DonateService(String category, String quantity, String userID, int type, String lat, String lang) {
        this.mCategory = category;
        this.mQuantity = quantity;
        this.mUserID = userID;
        this.mType = type;
        this.mLat = lat;
        this.mLang = lang;
    }

    public void donateServiceTask() {
        JSONObject userJson = new JSONObject();
        try {
            userJson.put("category", mCategory);
            userJson.put("quantity", mQuantity);
            userJson.put("UID", mUserID);
            userJson.put("type", String.valueOf(mType));
            userJson.put("lat", mLat);
            userJson.put("long", mLang);
        } catch (JSONException e) {
            e.printStackTrace();
        }
      //  new PostDonateTask().execute(userJson);
    }

    private static class PostDonateTask extends AsyncTask<JSONObject, Void, Void> {


        @Override
        protected Void doInBackground(JSONObject... jsonObjects) {
            JSONObject json = jsonObjects[0];
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, json.toString());
            Request request = new Request.Builder()
                    .url("")
                    .post(body)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
