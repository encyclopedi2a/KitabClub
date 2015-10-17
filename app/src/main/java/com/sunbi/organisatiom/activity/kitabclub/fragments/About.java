package com.sunbi.organisatiom.activity.kitabclub.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunbi.organisatiom.activity.kitabclub.R;

public class About extends Fragment {
    private TextView textView,web,email;
    private ImageView aboutUs;
    private View view;
    private WebView webView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_about, container, false);
        web=(TextView)view.findViewById(R.id.web);
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadURL("http://www.kitabclub.com");
            }
        });
        email=(TextView)view.findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadURL("http://www.facebook.com");
            }
        });
        return view;
    }
    private void loadURL(String url){
        LayoutInflater inflater=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View views=inflater.inflate(R.layout.custom_about_view,null);
        WebView webView=(WebView)views.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new MyBrowser());
        webView.setInitialScale(1);
        webView.getSettings().setDisplayZoomControls(true);
        webView.loadUrl(url);
        builder.setView(views);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    private class MyBrowser extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView=(TextView)view.findViewById(R.id.description);
        textView.setText(Html.fromHtml(descriptionContent()));
        aboutUs=(ImageView)view.findViewById(R.id.aboutus);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(About.this).commit();
            }
        });
    }
    private String descriptionContent(){
        String description="<b>KitabClub</b><br><br>In the business for over many years , we at Kitabclub specialize in attaining exclusive books at incredible prices. We are also members of the internatonal " +
                "books Association which provides us access to the most luxurious and exclusive books nt he world.";
        return description;
    }
}
