package com.sunbi.organisatiom.activity.kitabclub;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.sunbi.organisatiom.activity.kitabclub.connection.ConnectionManager;
import com.sunbi.organisatiom.activity.kitabclub.json.PushNotificationJSON;
import com.sunbi.organisatiom.activity.kitabclub.models.SQLiteData;
import com.sunbi.organisatiom.activity.kitabclub.sqlitedatabase.PushNotificationData;

import java.util.List;

public class PushNotification extends AppCompatActivity {
    private ListView pushNotification;
    private CircleProgressBar progressBar;
    private List<SQLiteData> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        TextView titleText = (TextView) findViewById(R.id.titletext);
        titleText.setText("Push Notifications");
        //calling for the json from server to get the push notification data
        progressBar = (CircleProgressBar) findViewById(R.id.progressBar);
        if (new ConnectionManager(this).isConnectionToInternet()) {
            new PushNotificationJSON(this,progressBar).makeJsonArrayRequest();
            data=new PushNotificationData(getApplicationContext()).getAllData();
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            data=new PushNotificationData(getApplicationContext()).getAllData();
        }
        pushNotification = (ListView) findViewById(R.id.push_notification);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                SQLiteData sqLiteData=data.get(position);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setPadding(10, 10, 10, 10);
                textView.setBackgroundColor(Color.WHITE);
                textView.setTag(String .valueOf(sqLiteData.getId()));
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.recycle, 0);
                textView.setText(Html.fromHtml(sqLiteData.getTitle()+"<br>"+"------------------------------------------"+"<br>"+sqLiteData.getNotification()));
                return view;
            }
        };
        pushNotification.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
