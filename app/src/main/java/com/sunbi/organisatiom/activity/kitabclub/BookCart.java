package com.sunbi.organisatiom.activity.kitabclub;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BookCart extends AppCompatActivity {
    private ImageView favourite;
    private ImageView cart;
    private TextView cartCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbarcolor));
        setSupportActionBar(toolbar);
        TextView titleText = (TextView) findViewById(R.id.titletext);
        titleText.setText("Book Detail");
        titleText.setTypeface(null, Typeface.BOLD);
        initialiseView();
        prepareToolbar();
    }

    private void initialiseView() {
        favourite = (ImageView) findViewById(R.id.favourite);
        cart = (ImageView) findViewById(R.id.cart);
        cartCount=(TextView)findViewById(R.id.countcart);

    }

    private void prepareToolbar() {
        favourite.setImageResource(R.drawable.favourite);
        favourite.setAdjustViewBounds(true);
        favourite.setMaxHeight(60);
        cart.setImageResource(R.drawable.cart);
        cart.setAdjustViewBounds(true);
        cart.setMaxHeight(60);
        cart.setPadding(0, 0, 30, 0);
        cartCount.setVisibility(View.VISIBLE);
    }
}
