package com.sunbi.organisatiom.activity.kitabclub.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sunbi.organisatiom.activity.kitabclub.SignUp;

/**
 * Created by gokarna on 7/23/15.
 */
public class ConnectionSignUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI||netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_MOBILE)
            SignUp.getInstace().updateTheLoginButton(true);
        else
            SignUp.getInstace().updateTheLoginButton(false);
    }
}
