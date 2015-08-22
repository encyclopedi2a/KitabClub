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
    private TextView total;
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
        total=(TextView)findViewById(R.id.total);
        list=new DatabaseOperation(this).getAllData();
        CartListAdapter cartListAdapter=new CartListAdapter(getApplicationContext(),list,cartListView,total);
        cartListView.setAdapter(cartListAdapter);
        setTotalValue(total,list);
    }
    private void setTotalValue(TextView total,List<SQLiteData> list){
        int totalSum=0;
        for(int i=0; i<list.size();i++){
            SQLiteData data=(SQLiteData)list.get(i);
            totalSum=totalSum+(Integer.parseInt(data.getBookQuantity())*Integer.parseInt(data.getBookPrice()));
        }
        total.setText(String.valueOf("$"+totalSum));
    }
}
