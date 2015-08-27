package com.sunbi.organisatiom.activity.kitabclub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class BookReader extends AppCompatActivity {
    private WebView webview;
    private String displayString;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_reader);
        displayString = getIntent().getExtras().getString("display");
        webview = (WebView) findViewById(R.id.bookReader);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().getLoadsImagesAutomatically();
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setDefaultTextEncodingName("utf-8");
        String text = "<html><body style=\"text-align:justify\"> %s </body></Html>";
        if (displayString != null)
            webview.loadDataWithBaseURL(null, String.format(text, displayString), "text/html", "utf-8",null);
    }
  }
