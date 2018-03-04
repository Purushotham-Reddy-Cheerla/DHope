package com.poras.passionate.dhope.webservices;

import android.content.Context;
import android.os.AsyncTask;

import com.poras.passionate.dhope.HopeUtil;

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

public class SendTokenToServerService {
    private String mToken;
    private Context mContext;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    public SendTokenToServerService(Context context, String token) {
        this.mToken = token;
        this.mContext = context;
    }

    public void sendTokenService() {
        String userId = HopeUtil.getUserId(mContext);
        JSONObject userJson = new JSONObject();
        try {
            userJson.put("uid", userId);
            userJson.put("token", mToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
       // new TokenAsyncTask().execute(userJson);
    }

    private static class TokenAsyncTask extends AsyncTask<JSONObject, Void, Void> {

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
