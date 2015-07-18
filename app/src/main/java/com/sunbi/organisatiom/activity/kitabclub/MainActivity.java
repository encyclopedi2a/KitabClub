package com.sunbi.organisatiom.activity.kitabclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView signup;
    private TextView guestLogin;
    private EditText username, password;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(this);
        guestLogin = (TextView) findViewById(R.id.guestlogin);
        guestLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginButton:
                Intent intent = new Intent(MainActivity.this, Homepage.class);
                startActivity(intent);
                break;
            case R.id.signup:
                signup.setBackgroundResource(R.drawable.selector_state);
                break;
            case R.id.guestlogin:
                guestLogin.setBackgroundResource(R.drawable.selector_state);
                break;

        }
    }
}
