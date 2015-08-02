package com.sunbi.organisatiom.activity.kitabclub.json;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.sunbi.organisatiom.activity.kitabclub.SignUp;
import com.sunbi.organisatiom.activity.kitabclub.models.SignUpDTO;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by gokarna on 7/27/15.
 */
public class SignUpJSON extends AsyncTask<Void, Void, Void> {
    private String text = "";
    private CircularProgressButton signUpButton;
    private SignUpDTO signUp;
    private Context context;

    public SignUpJSON(Context context, CircularProgressButton signUpBotton, SignUpDTO signUp) {
        this.signUpButton = signUpBotton;
        this.signUp = signUp;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://thesunbihosting.com/demo/book_store/json/register");
        JSONObject json = new JSONObject();

        try {
            // JSON data:
            json.put("username", signUp.getUsername());
            json.put("password", signUp.getPassword());
            json.put("email", signUp.getEmail());

            JSONArray postjson = new JSONArray();
            postjson.put(json);

            // Post the data:
            httppost.setHeader("json", json.toString());
            httppost.getParams().setParameter("jsonpost", postjson);

            HttpResponse response = httpclient.execute(httppost);

            // for JSON:
            if (response != null) {
                InputStream is = response.getEntity().getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();

                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                text = sb.toString();
            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (text.trim().equalsIgnoreCase("success")) {
            signUpButton.setProgress(100);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((SignUp) (context)).finish();
                    ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }, 1500);
        } else {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
            signUpButton.setProgress(0);
        }
    }
}