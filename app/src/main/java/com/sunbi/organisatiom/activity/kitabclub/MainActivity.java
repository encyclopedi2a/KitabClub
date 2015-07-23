package com.sunbi.organisatiom.activity.kitabclub;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.sunbi.organisatiom.activity.kitabclub.connection.ConnectionManager;
import com.sunbi.organisatiom.activity.kitabclub.connection.ConnectionReceiver;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView signup;
    private TextView guestLogin;
    private EditText username, password;
    private CircularProgressButton loginButton;
    private static MainActivity mainActivityInstance;
    private BroadcastReceiver connectionReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityInstance = this;
        connectionReceiver = new ConnectionReceiver();
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (CircularProgressButton) findViewById(R.id.loginButton);
        loginButton.setIndeterminateProgressMode(true);
        loginButton.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        loginButton.setStrokeColor(getResources().getColor(R.color.buttonColor));
        loginButton.setOnClickListener(this);
        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(this);
        guestLogin = (TextView) findViewById(R.id.guestlogin);
        if (new ConnectionManager(getApplicationContext()).isConnectionToInternet()) {
            loginButton.setText("LOGIN");
        } else {
            loginButton.setBackgroundColor(Color.RED);
            loginButton.setStrokeColor(Color.RED);
            loginButton.setText("No Internet Connection");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup:
                signup.setBackgroundResource(R.drawable.selector_state);
                break;
            case R.id.guestlogin:
                guestLogin.setBackgroundResource(R.drawable.selector_state);
                break;
            case R.id.loginButton:
                if (loginButton.getText().equals("No Internet Connection")) {
                    //perform nothing
                } else {
                    loginButton.setProgress(50);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, Homepage.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    }, 3000);
                }

                break;
        }
    }

    //return static class for broadcast receivers
    public static MainActivity getInstace() {
        return mainActivityInstance;
    }

    public void updateTheLoginButton(final boolean state) {

        if (state) {
            loginButton.setBackgroundColor(getResources().getColor(R.color.buttonColor));
            loginButton.setStrokeColor(getResources().getColor(R.color.buttonColor));
            loginButton.setText("LOGIN");
        } else {
            loginButton.setBackgroundColor(Color.RED);
            loginButton.setStrokeColor(Color.RED);
            loginButton.setText("No Internet Connection");
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectionReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectionReceiver);
    }

    private void enableBroadcastReceiver() {
        PackageManager pm = getApplicationContext().getPackageManager();
        ComponentName componentName = new ComponentName(MainActivity.this, ConnectionReceiver.class);
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void disableBroadcastReceiver() {
        PackageManager pm = getApplicationContext().getPackageManager();
        ComponentName componentName = new ComponentName(MainActivity.this, PackageManager.class);
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
