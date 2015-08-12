package com.sunbi.organisatiom.activity.kitabclub;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.PictureAttributes;
import com.sunbi.organisatiom.activity.kitabclub.Helper.FacebookConfiguration;
import com.sunbi.organisatiom.activity.kitabclub.adapters.DrawerListAdapter;
import com.sunbi.organisatiom.activity.kitabclub.classes.SharedPreferenceValueProvider;
import com.sunbi.organisatiom.activity.kitabclub.connection.ConnectionManager;
import com.sunbi.organisatiom.activity.kitabclub.fragments.About;
import com.sunbi.organisatiom.activity.kitabclub.interfaces.LatestAdditionInterface;
import com.sunbi.organisatiom.activity.kitabclub.json.LatestAdditionJSON;
import com.sunbi.organisatiom.activity.kitabclub.models.ListItem;

import java.util.List;

public class Homepage extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView username, titleText, profileName, profileEmail;
    private LinearLayout bookList, myBooks;
    private ImageView logOut, faceBook, messages;
    private CircularImageView profilePicture;
    private Toolbar toolbar;
    private SimpleFacebook mSimpleFacebook;
    private LinearLayout bookContainer, messageLayout;
    private HorizontalScrollView scrollView;
    private WebView webView;
    private CircleProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        initialiseLayout();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        titleText.setText("");
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        username.setText(Html.fromHtml("Welcome<b> " + (new SharedPreferenceValueProvider(getApplicationContext()).returnPreferenceValue())));
        prepareDrawerList();
        prepareFacebookDrawer();
        setLatestAdditionBook(bookContainer);
        animateLatestAdditionBook();
        initialiseListener();
        setFacebookProfile();
    }


    private void initialiseLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleText = (TextView) findViewById(R.id.titletext);
        username = (TextView) findViewById(R.id.username);
        profileName = (TextView) findViewById(R.id.profileName);
        profileEmail = (TextView) findViewById(R.id.profileEmail);
        bookList = (LinearLayout) findViewById(R.id.bookList);
        myBooks = (LinearLayout) findViewById(R.id.mybooks);
        bookContainer = (LinearLayout) findViewById(R.id.bookContainer);
        messageLayout = (LinearLayout) findViewById(R.id.messagelayout);
        logOut = (ImageView) findViewById(R.id.messages);
        faceBook = (ImageView) findViewById(R.id.facebook);
        profilePicture = (CircularImageView) findViewById(R.id.profilePicture);
        scrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (CircleProgressBar) findViewById(R.id.progressBar);
        messages = (ImageView) findViewById(R.id.messages);
    }


    private void prepareDrawerList() {
        ListItem listItems[] = new ListItem[]{
                        new ListItem(R.mipmap.home, "Home"),
                        new ListItem(R.mipmap.about, "About"),
                        new ListItem(R.mipmap.signout, "Sign Out"),
                };
        DrawerListAdapter drawerListAdapter = new DrawerListAdapter(Homepage.this,R.layout.custom_drawer_list_view,listItems);
        mDrawerList.setAdapter(drawerListAdapter);
    }

    private void initialiseListener() {
        bookList.setOnClickListener(this);
        logOut.setOnClickListener(this);
        faceBook.setOnClickListener(this);
        messages.setOnClickListener(this);
        myBooks.setOnClickListener(this);
        mDrawerList.setOnItemClickListener(this);
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

    private void setFacebookProfile() {
        SimpleFacebook.setConfiguration(new FacebookConfiguration().getConfiguration());
        mSimpleFacebook = SimpleFacebook.getInstance(this);
        if (new ConnectionManager(getApplicationContext()).isConnectionToInternet()) {
            login();
        }
    }

    private void login() {
        mSimpleFacebook.login(new OnLoginListener() {
            @Override
            public void onLogin(String s, List<Permission> list, List<Permission> list1) {
                Log.i("Facebook:: ", s);
                PictureAttributes attributes = PictureAttributes.createPictureAttributes();
                attributes.setType(PictureAttributes.PictureType.SQUARE);
                attributes.setHeight(100);
                attributes.setHeight(100);
                Profile.Properties properties = new Profile.Properties.Builder()
                        .add(Profile.Properties.NAME)
                        .add(Profile.Properties.EMAIL)
                        .add(Profile.Properties.BIRTHDAY)
                        .add(Profile.Properties.PICTURE, attributes)
                        .build();
                mSimpleFacebook.getProfile(properties, mProfileListener);
            }

            @Override
            public void onCancel() {
                Log.i("Facebook:: ", "User Cancelled");
            }

            @Override
            public void onException(Throwable throwable) {
                Log.i("Facebook:: ", throwable.toString());
            }

            @Override
            public void onFail(String s) {
                Log.i("Facebook:: ", s);
            }
        });
    }

    OnProfileListener mProfileListener = new OnProfileListener() {
        @Override
        public void onComplete(Profile response) {
            super.onComplete(response);
            String username = response.getName();
            String userId = response.getId();
            String profileUrl = response.getPicture();
            String email = response.getEmail();
            Picasso.with(getApplicationContext())
                    .load(profileUrl).placeholder(R.drawable.orangearrow)
                    .into(profilePicture);
            profileName.setText(username);
            profileEmail.setText(email);


        }

        @Override
        public void onThinking() {
            Log.i("Facebook:: ", "Profile thinking in");
            super.onThinking();

        }

        @Override
        public void onException(Throwable throwable) {
            super.onException(throwable);
            Log.i("Facebook:: ", "Profile exception " + throwable.toString());
        }

        @Override
        public void onFail(String reason) {
            super.onFail(reason);
            Log.i("Facebook:: ", "Profile fail " + reason);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSimpleFacebook.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setLatestAdditionBook(final LinearLayout bookContainer) {

        new LatestAdditionJSON(getApplicationContext(), new LatestAdditionInterface() {
            @Override
            public void result(List<String> imagePathContainer) {
                for (String imagePath : imagePathContainer) {
                    final ImageView imageView = new ImageView(Homepage.this);
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
                    imageView.setAdjustViewBounds(true);
                    imageView.getLayoutParams().height = bookContainer.getHeight() - 20;
                    imageView.getLayoutParams().width = 150;
                    imageView.requestLayout();
                    Picasso.with(getApplicationContext())
                            .load(imagePath)
                            .placeholder(R.drawable.imagebackground).into(imageView);
                    bookContainer.addView(imageView);
                }
            }
        }).makeJsonArrayRequest();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void animateLatestAdditionBook() {
        ObjectAnimator animator = ObjectAnimator.ofInt(scrollView, "scrollX", 810);
        animator.setDuration(15000);
        animator.setRepeatCount(1000);
        animator.start();
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
            case R.id.mybooks:
                Intent mybooksIntent = new Intent(Homepage.this, MyBooks.class);
                startActivity(mybooksIntent);
                break;
            case R.id.messages:
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                webView.setVisibility(View.GONE);
                messageLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.facebook:
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                messageLayout.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
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
        } else if (mDrawerLayout.isDrawerOpen(Gravity.LEFT) || mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
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
