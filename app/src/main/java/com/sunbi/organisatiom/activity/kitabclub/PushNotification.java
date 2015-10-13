package com.sunbi.organisatiom.activity.kitabclub;

import android.content.Intent;
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

import com.sunbi.organisatiom.activity.kitabclub.models.SQLiteData;
import com.sunbi.organisatiom.activity.kitabclub.sqlitedatabase.PushNotificationData;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PushNotification extends AppCompatActivity {
    private ListView pushNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_notification);
        Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        TextView titleText = (TextView) findViewById(R.id.titletext);
        titleText.setText("Push Notifications");
        List<SQLiteData> messageList = new PushNotificationData(getApplicationContext()).getAllData();
        Set<String> hashLinkSet = new LinkedHashSet<>();
        Set<String> titleLinkSet=new LinkedHashSet<>();
        for (int i = 0; i < messageList.size(); i++) {
            SQLiteData sqLiteData = messageList.get(i);
            hashLinkSet.add(sqLiteData.getNotification());
            titleLinkSet.add(sqLiteData.getTitle());
        }
        final ArrayList<String> values = new ArrayList<>(hashLinkSet);
        final ArrayList<String> title=new ArrayList<>(titleLinkSet);
        pushNotification = (ListView) findViewById(R.id.push_notification);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,values){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view=super.getView(position,convertView,parent);
                TextView textView=(TextView)view.findViewById(android.R.id.text1);
                textView.setText(Html.fromHtml("<b>"+title.get(position)+"</b>"+"<br>"+values.get(position)));
                textView.setBackgroundColor(Color.parseColor("#ffffff"));
                textView.setTextSize(17);
                textView.setPadding(12,12,12,12);
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
