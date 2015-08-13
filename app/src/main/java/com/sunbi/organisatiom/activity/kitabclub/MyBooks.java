package com.sunbi.organisatiom.activity.kitabclub;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sunbi.organisatiom.activity.kitabclub.adapters.CustomMyBooksListAdapter;
import com.sunbi.organisatiom.activity.kitabclub.classes.PurchaseBookList;
import com.sunbi.organisatiom.activity.kitabclub.interfaces.MyBooksInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyBooks extends AppCompatActivity implements ListView.OnItemClickListener {
    private ListView listView;
    private static ArrayList<String> bookName;
    private static ArrayList<String> bookPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        TextView titleText = (TextView) findViewById(R.id.titletext);
        titleText.setText("My Books");
        listView = (ListView) findViewById(R.id.listView);
        List<String> mAppList = new PurchaseBookList(getApplicationContext(), new MyBooksInterface() {
            @Override
            public void bookName(ArrayList<String> bookName) {
                MyBooks.bookName=bookName;
            }

            @Override
            public void bookPath(ArrayList<String> bookPath) {
                MyBooks.bookPath=bookPath;
            }
        }).searchFolderRecursive();
        CustomMyBooksListAdapter adapter = new CustomMyBooksListAdapter(bookName,bookPath, getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
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
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        File file=new File(MyBooks.bookPath.get(position));
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"application/epub");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }
    }
}
