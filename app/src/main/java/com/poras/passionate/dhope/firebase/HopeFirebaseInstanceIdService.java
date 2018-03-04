package com.poras.passionate.dhope.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.poras.passionate.dhope.webservices.SendTokenToServerService;

/**
 * Created by purus on 3/4/2018.
 */

public class HopeFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static String LOG_TAG = HopeFirebaseInstanceIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(LOG_TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        SendTokenToServerService service = new SendTokenToServerService(getApplicationContext(), refreshedToken);
        service.sendTokenService();
    }

}
