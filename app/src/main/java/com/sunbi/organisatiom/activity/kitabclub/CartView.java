package com.sunbi.organisatiom.activity.kitabclub;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.sunbi.organisatiom.activity.kitabclub.adapters.CartListAdapter;
import com.sunbi.organisatiom.activity.kitabclub.models.SQLiteData;
import com.sunbi.organisatiom.activity.kitabclub.sqlitedatabase.DatabaseOperation;

import java.util.List;

public class CartView extends AppCompatActivity {
    private ListView cartListView;
    private List<SQLiteData> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        TextView titleText = (TextView) findViewById(R.id.titletext);
        titleText.setText("Cart List");
        titleText.setTypeface(null, Typeface.BOLD);
        cartListView=(ListView)findViewById(R.id.cartlist);
        list=new DatabaseOperation(this).getAllData();
        CartListAdapter cartListAdapter=new CartListAdapter(getApplicationContext(),list);
        cartListView.setAdapter(cartListAdapter);
    }
}
