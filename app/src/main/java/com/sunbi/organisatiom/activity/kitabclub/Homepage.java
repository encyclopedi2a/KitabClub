package com.sunbi.organisatiom.activity.kitabclub;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.sunbi.organisatiom.activity.kitabclub.classes.SharedPreferenceValueProvider;
import com.sunbi.organisatiom.activity.kitabclub.fragments.About;

public class Homepage extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private int[] imageTitle = {R.drawable.guy, R.drawable.thistle, R.drawable.guy, R.drawable.thistle, R.drawable.guy, R.drawable.thistle, R.drawable.book1, R.drawable.book1, R.drawable.book1};
    private TextView username, titleText;
    private LinearLayout bookList;
    private ImageView logOut, faceBook;
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    private HorizontalScrollView scrollView;
    private WebView webView;
    private CircleProgressBar progressBar;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
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
        username.setText(Html.fromHtml("Welcome<b> " + (new SharedPreferenceValueProvider(getApplicationContext()).returnPreferenceValue())));
        prepareDrawerList();
        prepareFacebookDrawer();
        setImageInLinerLayout(linearLayout);
        ObjectAnimator animator = ObjectAnimator.ofInt(scrollView, "scrollX", 810);
        animator.setDuration(15000);
        animator.setRepeatCount(100);
        animator.start();
        bookList.setOnClickListener(this);
        logOut.setOnClickListener(this);
        faceBook.setOnClickListener(this);
        mDrawerList.setOnItemClickListener(this);
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
        faceBook = (ImageView) findViewById(R.id.facebook);
        scrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (CircleProgressBar) findViewById(R.id.progressBar);
    }


    private void prepareDrawerList() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1
                , new String[]{"  Home", "  About Us", "  Location", "  Sign Out"}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.parseColor("#767676"));
                return view;
            }
        };
        mDrawerList.setAdapter(arrayAdapter);
    }

    private void prepareFacebookDrawer() {
        webView.getSettings().setSupportZoom(true);
        webView.setInitialScale(1);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.facebook.com");
    }

    private void setImageInLinerLayout(LinearLayout linearLayout) {
        for (int i = 0; i < imageTitle.length; i++) {
            final ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.gravity = Gravity.CENTER;
            int screenSize = getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK;
            switch (screenSize) {
                case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                    lp.setMargins(15, 3, 5, 3);
                    break;
                default:
            }
            imageView.setLayoutParams(lp);
            imageView.setBackgroundResource(imageTitle[i]);
            imageView.setAdjustViewBounds(true);
            imageView.getLayoutParams().height = 200;
            imageView.getLayoutParams().width = 150;
            imageView.requestLayout();
            linearLayout.addView(imageView);
        }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bookList:
                Intent intent = new Intent(Homepage.this, BookCatagories.class);
                startActivity(intent);
                break;
            case R.id.messages:
                break;
            case R.id.facebook:
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment activeFragment = getSupportFragmentManager().findFragmentByTag("FRAGMENT_FEEDBACK");
        if (activeFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(activeFragment).commit();
        }
        switch (position) {
            case 0:
               //display simply a hove by removing the active fragment
                break;
            case 1:
                Fragment fragment1 = new About();
                fragmentTransaction.add(R.id.content_frame, fragment1, "FRAGMENT_FEEDBACK");
                break;
            case 2:
                Fragment fragment2 = new About();
                fragmentTransaction.add(R.id.content_frame, fragment2, "FRAGMENT_FEEDBACK");
                break;
            case 3:
                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.preference_value, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isFirstTime", true);
                editor.commit();
                Intent intents = new Intent(Homepage.this, MainActivity.class);
                startActivity(intents);
                finish();
                break;
        }
        fragmentTransaction.commit();
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("FRAGMENT_FEEDBACK");
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        } else {
            super.onBackPressed();
        }

    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }
}
