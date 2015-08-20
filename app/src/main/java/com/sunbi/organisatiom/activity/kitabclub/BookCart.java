package com.sunbi.organisatiom.activity.kitabclub;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookCart extends AppCompatActivity {
    private ImageView favourite;
    private ImageView cart, bookImage;
    private TextView cartCount, bookName, bookPrice, bookDescription;

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
        setContent();
    }

    private void initialiseView() {
        favourite = (ImageView) findViewById(R.id.favourite);
        cart = (ImageView) findViewById(R.id.cart);
        cartCount = (TextView) findViewById(R.id.countcart);
        bookImage = (ImageView) findViewById(R.id.bookimage);
        bookName = (TextView) findViewById(R.id.bookname);
        bookPrice = (TextView) findViewById(R.id.bookprice);
        bookDescription = (TextView) findViewById(R.id.bookdescription);
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

    private void setContent() {
        ArrayList<String> content = getIntent().getStringArrayListExtra("parameters");
        bookName.setText(content.get(1));
        Picasso.with(this)
                .load(content.get(2))
                .placeholder(R.drawable.imagebackground).into(bookImage);
        bookPrice.setText("$"+content.get(5));
        bookDescription.setText(content.get(8));
    }
}
