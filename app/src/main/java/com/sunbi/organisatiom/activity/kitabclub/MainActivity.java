package com.sunbi.organisatiom.activity.kitabclub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView username;
    private TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (TextView) findViewById(R.id.username);
        username.setOnClickListener(this);
        password = (TextView) findViewById(R.id.password);
        password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        username.setBackgroundResource(R.drawable.selector_state);
        password.setBackgroundResource(R.drawable.selector_state);
    }
}
