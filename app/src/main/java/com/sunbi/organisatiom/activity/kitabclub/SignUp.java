package com.sunbi.organisatiom.activity.kitabclub;

import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dd.CircularProgressButton;
import com.sunbi.organisatiom.activity.kitabclub.classes.LoginValidation;
import com.sunbi.organisatiom.activity.kitabclub.connection.ConnectionManager;
import com.sunbi.organisatiom.activity.kitabclub.connection.ConnectionSignUpReceiver;
import com.sunbi.organisatiom.activity.kitabclub.json.UsernamePasswordSender;
import com.sunbi.organisatiom.activity.kitabclub.models.SignUpDTO;

public class SignUp extends AppCompatActivity implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {
    private EditText username;
    private EditText password;
    private EditText retypePassword;
    private CircularProgressButton signUp;
    private ConnectionSignUpReceiver receiver;
    private static SignUp activityInstance;
    private RelativeLayout relativeLayout;
    private static int count = 0;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInstance = this;
        setContentView(R.layout.activity_sign_up);
        initialiseView();
        signUp.setIndeterminateProgressMode(true);
        signUp.setBackgroundResource(R.drawable.selector_state);
        //set the initial state of button
        if (new ConnectionManager(getApplicationContext()).isConnectionToInternet()) {
            signUp.setBackgroundColor(getResources().getColor(R.color.buttonColor));
            signUp.setStrokeColor(getResources().getColor(R.color.buttonColor));
            signUp.setText("LOGIN");
        } else {
            signUp.setBackgroundColor(Color.RED);
            signUp.setStrokeColor(Color.RED);
            signUp.setText("No Internet Connection");
        }
        registerBroadCastReceiver();
        signUp.setOnClickListener(this);
        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    //return static class for broadcast receivers
    public static SignUp getInstace() {
        return activityInstance;
    }

    private void initialiseView() {
        relativeLayout = (RelativeLayout) findViewById(R.id.parentLayout);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        retypePassword = (EditText) findViewById(R.id.retypepassword);
        email = (EditText) findViewById(R.id.email);
        signUp = (CircularProgressButton) findViewById(R.id.loginButton);
    }

    private void registerBroadCastReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new ConnectionSignUpReceiver();
        this.registerReceiver(receiver, filter);
    }

    public void updateTheLoginButton(final boolean state) {

        if (state) {
            signUp.setBackgroundColor(getResources().getColor(R.color.buttonColor));
            signUp.setStrokeColor(getResources().getColor(R.color.buttonColor));
            signUp.setText("SIGN UP");
            count = 0;
        } else {
            signUp.setBackgroundColor(Color.RED);
            signUp.setStrokeColor(Color.RED);
            signUp.setText("No Internet Connection");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            this.unregisterReceiver(receiver);
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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.loginButton:
                if (!(signUp.getText().equals("No Internet Connection"))) {
                    //this counter value is not required for normal button but the progress button limitation make me do this
                    if (count == 0) {
                        if (!new LoginValidation(username, password, retypePassword, email).validateSignUp()) {
                            signUp.setProgress(50);
                            count++;
                            SignUpDTO signUpDTO = new SignUpDTO();
                            signUpDTO.setUsername(username.getText().toString());
                            signUpDTO.setPassword(username.getText().toString());
                            signUpDTO.setEmail(email.getText().toString());
                            new UsernamePasswordSender(getApplicationContext(),signUp, signUpDTO).execute();
                        }
                    }
                }
                break;
        }
    }
}
