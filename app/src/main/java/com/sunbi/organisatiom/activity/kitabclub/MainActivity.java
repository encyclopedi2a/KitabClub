package com.sunbi.organisatiom.activity.kitabclub;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.sunbi.organisatiom.activity.kitabclub.connection.ConnectionReceiver;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {
    private TextView signup;
    private TextView guestLogin;
    private EditText username, password;
    private CircularProgressButton loginButton;
    private static MainActivity mainActivityInstance;
    private BroadcastReceiver connectionReceiver;
    private RelativeLayout relativeLayout;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityInstance = this;
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.parentLayout);
        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (CircularProgressButton) findViewById(R.id.loginButton);
        loginButton.setIndeterminateProgressMode(true);
        loginButton.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        loginButton.setStrokeColor(getResources().getColor(R.color.buttonColor));
        loginButton.setText("LOGIN");
        loginButton.setOnClickListener(this);
        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(this);
        guestLogin = (TextView) findViewById(R.id.guestlogin);
        connectionReceiver = new ConnectionReceiver();
        intentFilter = new IntentFilter();
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
        try {
              registerReceiver(connectionReceiver, intentFilter);
        } catch (IllegalArgumentException ex) {

        }
        super.onPostResume();

    }

    @Override
    protected void onDestroy() {
        try {
             unregisterReceiver(connectionReceiver);
        } catch (IllegalArgumentException ex) {

        }
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        try {
             unregisterReceiver(connectionReceiver);
        } catch (IllegalArgumentException ex) {

        }
        super.onPause();

    }

    @Override
    public void onGlobalLayout() {
        ImageView imageView = (ImageView) findViewById(R.id.logo);
        int heightDiff = relativeLayout.getRootView().getHeight() - relativeLayout.getHeight();
        if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
    }
}
