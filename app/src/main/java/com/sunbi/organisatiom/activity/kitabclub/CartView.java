package com.sunbi.organisatiom.activity.kitabclub;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sunbi.organisatiom.activity.kitabclub.adapters.CartListAdapter;
import com.sunbi.organisatiom.activity.kitabclub.models.SQLiteData;
import com.sunbi.organisatiom.activity.kitabclub.sqlitedatabase.DatabaseOperation;

import java.util.List;

public class CartView extends AppCompatActivity implements View.OnClickListener {
    private ListView cartListView;
    private List<SQLiteData> list;
    private TextView total;
    private Button checkOut;

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
        cartListView = (ListView) findViewById(R.id.cartlist);
        total = (TextView) findViewById(R.id.total);
        list = new DatabaseOperation(this).getAllData();
        CartListAdapter cartListAdapter = new CartListAdapter(getApplicationContext(), list, cartListView, total);
        cartListView.setAdapter(cartListAdapter);
        setTotalValue(total, list);
        checkOut = (Button) findViewById(R.id.checkout);
        checkOut.setOnClickListener(this);
    }

    private void setTotalValue(TextView total, List<SQLiteData> list) {
        int totalSum = 0;
        for (int i = 0; i < list.size(); i++) {
            SQLiteData data = (SQLiteData) list.get(i);
            totalSum = totalSum + (Integer.parseInt(data.getBookQuantity()) * Integer.parseInt(data.getBookPrice()));
        }
        total.setText(String.valueOf("$" + totalSum));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.checkout:
                Intent intent = new Intent(this, WalletFormActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_sub_category, menu);
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
        return super.onOptionsItemSelected(item);
    }
}
