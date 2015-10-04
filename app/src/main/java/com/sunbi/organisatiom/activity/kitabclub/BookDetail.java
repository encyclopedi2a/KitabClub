package com.sunbi.organisatiom.activity.kitabclub;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sunbi.organisatiom.activity.kitabclub.classes.ImageDownloader;
import com.sunbi.organisatiom.activity.kitabclub.interfaces.PromoCodeResultHolder;
import com.sunbi.organisatiom.activity.kitabclub.json.PromoCodeJSON;

import java.util.ArrayList;

public class BookDetail extends AppCompatActivity implements View.OnClickListener {
    private ImageView favourite;
    private ImageView cart, bookImage;
    private TextView cartCount, bookName, bookPrice, bookDescription;
    private Button addCard;
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
        setContent();
        if(content.get(9).equals("8")){
            addCard.setText("DOWNLOAD");
        }
        addCard.setOnClickListener(this);
    }

    private void initialiseView() {
        addCard = (Button) findViewById(R.id.addCard);
        bookImage = (ImageView) findViewById(R.id.bookimage);
        bookName = (TextView) findViewById(R.id.bookname);
        bookPrice = (TextView) findViewById(R.id.bookprice);
        bookDescription = (TextView) findViewById(R.id.bookdescription);
    }

    private void setContent() {
        //set the cartCount by counting thhe number of record in android
        bookName.setText(content.get(1));
        Picasso.with(this)
                .load(content.get(2))
                .into(bookImage);
        bookPrice.setText(content.get(5));
        bookDescription.setText(content.get(8));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.addCard:
                if((addCard.getText().toString().equalsIgnoreCase("GET BOOKS"))){
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    View dialog = getLayoutInflater().inflate(R.layout.get_books_layout, null);
                    alertDialogBuilder.setView(dialog);
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    final Button usePromoDialog = (Button) dialog.findViewById(R.id.usepromocode);
                    Button buyWithGoogle = (Button) dialog.findViewById(R.id.buywithgoogle);
                    final EditText promoCode = (EditText) dialog.findViewById(R.id.promocode);
                    usePromoDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new PromoCodeJSON(getApplicationContext(), promoCode.getText().toString(), new PromoCodeResultHolder() {
                                @Override
                                public void result(String status) {
                                    if (status.equals("false")) {
                                        alertDialog.dismiss();
                                        new ImageDownloader(BookDetail.this,content.get(1),content.get(2),content.get(3)).execute();
                                    }
                                }
                            }).postJsonValue();
                        }
                    });
                    buyWithGoogle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(BookDetail.this, WalletTransaction.class);
                            startActivity(intent);

                        }
                    });

                }else{
                    new ImageDownloader(BookDetail.this,content.get(1),content.get(2),content.get(3)).execute();
                }

                /*
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
                }).returnTotalNumberofBooks();*/
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
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
