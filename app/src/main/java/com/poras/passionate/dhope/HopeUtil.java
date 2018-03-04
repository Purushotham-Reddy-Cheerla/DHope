package com.poras.passionate.dhope;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by purus on 3/4/2018.
 */

public class HopeUtil {
    public static String USER_ID;
    public static final String REGISTER_URL = "http://ec2-18-216-7-75.us-east-2.compute.amazonaws.com:4300/Register";

    public static String getUserId(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("AppShare", Context.MODE_PRIVATE);
        return sharedPref.getString(context.getString(R.string.userID), "");
    }

    public static void saveUserId(String userID) {

    }
}
