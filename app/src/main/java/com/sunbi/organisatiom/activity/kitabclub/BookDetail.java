package com.sunbi.organisatiom.activity.kitabclub;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sunbi.organisatiom.activity.kitabclub.classes.TotalBookValueProvider;
import com.sunbi.organisatiom.activity.kitabclub.interfaces.BooksPriceProvider;
import com.sunbi.organisatiom.activity.kitabclub.models.SQLiteData;
import com.sunbi.organisatiom.activity.kitabclub.sqlitedatabase.DatabaseOperation;

import java.util.ArrayList;

public class BookDetail extends AppCompatActivity implements View.OnClickListener {
    private ImageView favourite;
    private ImageView cart, bookImage;
    private TextView cartCount, bookName, bookPrice, bookDescription;
    private LinearLayout addCard;
    private ArrayList<String> content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_cart);
        content = getIntent().getStringArrayListExtra("parameters");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbarcolor));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        TextView titleText = (TextView) findViewById(R.id.titletext);
        titleText.setText("Book Detail");
        titleText.setTypeface(null, Typeface.BOLD);
        initialiseView();
        prepareToolbar();
        setContent();
        cart.setOnClickListener(this);
        addCard.setOnClickListener(this);
    }

    private void initialiseView() {
        cart = (ImageView) findViewById(R.id.cart);
        addCard = (LinearLayout) findViewById(R.id.addcard);
        cartCount = (TextView) findViewById(R.id.countcart);
        bookImage = (ImageView) findViewById(R.id.bookimage);
        bookName = (TextView) findViewById(R.id.bookname);
        bookPrice = (TextView) findViewById(R.id.bookprice);
        bookDescription = (TextView) findViewById(R.id.bookdescription);
    }

    private void prepareToolbar() {
        cart.setImageResource(R.drawable.cart);
        cart.setAdjustViewBounds(true);
        cart.setMaxHeight(60);
        cart.setPadding(0, 0, 30, 0);
        cartCount.setVisibility(View.VISIBLE);
    }

    private void setContent() {
        //set the cartCount by counting thhe number of record in android
        int count = new DatabaseOperation(BookDetail.this).countDataEntryFromDataBase();
        cartCount.setText(String.valueOf(count));
        bookName.setText(content.get(1));
        Picasso.with(this)
                .load(content.get(2))
                .placeholder(R.drawable.imagebackground).into(bookImage);
        bookPrice.setText("$" + content.get(5));
        bookDescription.setText(content.get(8));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.cart:
                Intent intent = new Intent(BookDetail.this, CartView.class);
                startActivity(intent);
                break;
            case R.id.addcard:
                new TotalBookValueProvider(this, bookPrice.getText().toString().substring(1), new BooksPriceProvider() {
                    @Override
                    public void setTotalPrice(String price, String quantity) {
                        //sending the value in the database for showing in the cart list
                        SQLiteData sqLiteData = new SQLiteData();
                        sqLiteData.setBookName(content.get(1));
                        sqLiteData.setBookImage(content.get(2));
                        sqLiteData.setBookQuantity(quantity);
                        sqLiteData.setBookPrice(content.get(5));
                        new DatabaseOperation(BookDetail.this).addRecord(sqLiteData);
                        int count = new DatabaseOperation(BookDetail.this).countDataEntryFromDataBase();
                        cartCount.setText(String.valueOf(count));
                    }
                }).returnTotalNumberofBooks();
                break;
        }

    }

    @Override
    protected void onResume() {
        int count = new DatabaseOperation(BookDetail.this).countDataEntryFromDataBase();
        cartCount.setText(String.valueOf(count));
        super.onResume();
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
