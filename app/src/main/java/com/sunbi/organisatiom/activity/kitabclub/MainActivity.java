package com.sunbi.organisatiom.activity.kitabclub;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
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
import com.sunbi.organisatiom.activity.kitabclub.classes.LoginValidation;
import com.sunbi.organisatiom.activity.kitabclub.connection.ConnectionManager;
import com.sunbi.organisatiom.activity.kitabclub.connection.ConnectionLoginReceiver;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {
    private TextView signup;
    private TextView guestLogin;
    private EditText username, password;
    private CircularProgressButton loginButton;
    private static MainActivity mainActivityInstance;
    private RelativeLayout relativeLayout;
    private ConnectionLoginReceiver receiver;
    private static int count = 0;
    public static final String preference_value = "username_preference";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //determining if apps is opened for the first time then call login activity otherwise called homepage activity
        sharedPreferences = getSharedPreferences(MainActivity.preference_value, MODE_PRIVATE);
        if (!(sharedPreferences.getBoolean("isFirstTime", true))) {
            callHomepage();
        }
        mainActivityInstance = this;
        setContentView(R.layout.activity_main);
        initialiseView();
        loginButton.setIndeterminateProgressMode(true);
        loginButton.setBackgroundResource(R.drawable.selector_state);
        //set the initial state of button
        setLoginButtonInitialState();
        registerBroadCastReceiver();
        initialiseListener();
    }

    //this is register so that login button automatically react on internet appear and gone
    private void registerBroadCastReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new ConnectionLoginReceiver();
        this.registerReceiver(receiver, filter);
    }

    private void setLoginButtonInitialState() {
        if (new ConnectionManager(getApplicationContext()).isConnectionToInternet()) {
            loginButton.setBackgroundColor(getResources().getColor(R.color.buttonColor));
            loginButton.setStrokeColor(getResources().getColor(R.color.buttonColor));
            loginButton.setText("LOGIN");
        } else {
            loginButton.setBackgroundColor(Color.RED);
            loginButton.setStrokeColor(Color.RED);
            loginButton.setText("No Internet Connection");
        }
    }

    private void initialiseListener() {
        loginButton.setOnClickListener(this);
        signup.setOnClickListener(this);
        guestLogin.setOnClickListener(this);
        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    private void initialiseView() {
        relativeLayout = (RelativeLayout) findViewById(R.id.parentLayout);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (CircularProgressButton) findViewById(R.id.loginButton);
        signup = (TextView) findViewById(R.id.signup);
        guestLogin = (TextView) findViewById(R.id.guestlogin);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup:
                signup.setBackgroundResource(R.drawable.selector_state);
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.guestlogin:
                guestLogin.setBackgroundResource(R.drawable.selector_state);
                if (!(loginButton.getText().equals("No Internet Connection"))) {
                    //this counter value is not required for normal button but the progress button limitation make me do this
                    if (count == 0) {
                        loginButton.setProgress(50);
                        count++;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                holdLoginDataInSharedPreference("<b>guest</b>");
                                callHomepage();
                            }
                        }, 3000);

                    }
                }
                break;
            case R.id.loginButton:
                if (!(loginButton.getText().equals("No Internet Connection"))) {
                    //this counter value is not required for normal button but the progress button limitation make me do this
                    if (count == 0) {
                        if (!new LoginValidation(username, password, null,null).validateLogin()) {
                            loginButton.setProgress(50);
                            count++;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    holdLoginDataInSharedPreference(username.getText().toString());
                                    callHomepage();
                                }
                            }, 3000);
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            this.unregisterReceiver(receiver);
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
            count = 0;
        } else {
            loginButton.setBackgroundColor(Color.RED);
            loginButton.setStrokeColor(Color.RED);
            loginButton.setText("No Internet Connection");
        }
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

    //holding data in shared preference so that once login user not not to login again
    private void holdLoginDataInSharedPreference(String userField) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", userField);
        editor.putBoolean("isFirstTime", false);
        editor.commit();
    }

    private void callHomepage() {
        Intent intent = new Intent(MainActivity.this, Homepage.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
