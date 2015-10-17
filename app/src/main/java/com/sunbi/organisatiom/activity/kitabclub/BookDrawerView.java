package com.sunbi.organisatiom.activity.kitabclub;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import com.sunbi.organisatiom.activity.kitabclub.adapters.BookDrawerViewGridAdpater;
import com.sunbi.organisatiom.activity.kitabclub.classes.PurchaseBookList;
import com.sunbi.organisatiom.activity.kitabclub.interfaces.MyBooksInterface;

import java.util.ArrayList;
import java.util.List;

public class BookDrawerView extends AppCompatActivity {
    private GridView bookDrwerView;
    private static ArrayList<String> imagePath;
    private static ArrayList<String> bookPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_drawer_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        TextView titleText = (TextView) findViewById(R.id.titletext);
        titleText.setText("Book List");
        titleText.setTypeface(null, Typeface.BOLD);
        bookDrwerView = (GridView) findViewById(R.id.bookDrawerView);
        List<String> mAppList = new PurchaseBookList(getApplicationContext(), new MyBooksInterface() {
            @Override
            public void bookName(ArrayList<String> bookName) {
            }

            @Override
            public void bookPath(ArrayList<String> bookPath) {
                BookDrawerView.bookPath=bookPath;

            }

            @Override
            public void imagePath(ArrayList<String> imagePath) {
                BookDrawerView.imagePath = imagePath;
            }
        }).searchFolderRecursive();
            BookDrawerViewGridAdpater gridAdpater = new BookDrawerViewGridAdpater(this, imagePath, bookPath);
            bookDrwerView.setAdapter(gridAdpater);

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
