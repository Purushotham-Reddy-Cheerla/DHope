package com.poras.passionate.dhope.webservices;

import android.os.AsyncTask;
import android.util.Log;

import com.poras.passionate.dhope.HopeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by purus on 3/4/2018.
 */


public class PostUserDataTask extends AsyncTask<JSONObject, Void, Void> {

    private OnUserRegistered mHandler;
    private Response response = null;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8;Express");

    public interface OnUserRegistered {
        void onUserRegistrationCompleted(String userID);
    }

    public PostUserDataTask(OnUserRegistered handler) {
        this.mHandler = handler;
    }

    @Override
    protected Void doInBackground(JSONObject... jsonObjects) {
        JSONObject json = jsonObjects[0];
        Log.e("JSOOOOOON", json.toString());
        return null;
        /*OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder()
                .url(HopeUtil.REGISTER_URL)
                .post(body)
                .build();

        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;*/
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}

