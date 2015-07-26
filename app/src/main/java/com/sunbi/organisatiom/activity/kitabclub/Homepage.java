package com.sunbi.organisatiom.activity.kitabclub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sunbi.organisatiom.activity.kitabclub.classes.SharedPreferenceValueProvider;

public class Homepage extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private int[] imageTitle = {R.drawable.book1, R.drawable.book1, R.drawable.book1, R.drawable.book1, R.drawable.book1, R.drawable.book1, R.drawable.book1, R.drawable.book1, R.drawable.book1};
    private TextView username, titleText;
    private LinearLayout bookList;
    private ImageView logOut;
    private Toolbar toolbar;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        initialiseLayout();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        titleText.setText("");
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        username.setText("Welcome " + Html.fromHtml(new SharedPreferenceValueProvider(getApplicationContext()).returnPreferenceValue()));
        setImageInLinerLayout(linearLayout);
        bookList.setOnClickListener(this);
        logOut.setOnClickListener(this);
    }

    private void initialiseLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleText = (TextView) findViewById(R.id.titletext);
        username = (TextView) findViewById(R.id.username);
        bookList = (LinearLayout) findViewById(R.id.bookList);
        linearLayout = (LinearLayout) findViewById(R.id.bookContainer);
        logOut = (ImageView) findViewById(R.id.messages);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setImageInLinerLayout(LinearLayout linearLayout) {
        for (int i = 0; i < imageTitle.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setMaxHeight(50);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            int screenSize = getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK;
            switch (screenSize) {
                case Configuration.SCREENLAYOUT_SIZE_LARGE:
                    break;
                case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                    lp.setMargins(15, 3, 5, 3);
                    break;
                case Configuration.SCREENLAYOUT_SIZE_SMALL:
                    break;
                default:

            }
            imageView.setLayoutParams(lp);
            imageView.setBackgroundResource(imageTitle[i]);
            imageView.setAdjustViewBounds(true);
            linearLayout.addView(imageView);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bookList:
                Intent intent = new Intent(Homepage.this, BookCatagories.class);
                startActivity(intent);
                break;
            case R.id.messages:
                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.preference_value, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isFirstTime", true);
                editor.commit();
                Intent intents =new Intent(Homepage.this,MainActivity.class);
                startActivity(intents);
                finish();
        }
    }
}
