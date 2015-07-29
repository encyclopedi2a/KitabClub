package com.sunbi.organisatiom.activity.kitabclub.json;

import android.content.Context;
import android.os.AsyncTask;

import com.dd.CircularProgressButton;
import com.sunbi.organisatiom.activity.kitabclub.interfaces.LoginInterface;
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
 * Created by gokarna on 7/29/15.
 */
public class LoginJSON extends AsyncTask<Void, Void, Boolean> {
    private LoginInterface loginInterface;
    private Context context;
    private SignUpDTO signUpDTO;
    private CircularProgressButton circularProgressButton;
    private Boolean result;
    private String text;

    public LoginJSON(Context context, CircularProgressButton circularProgressButton, SignUpDTO signUpDTO, LoginInterface loginInterface) {
        this.context = context;
        this.circularProgressButton = circularProgressButton;
        this.signUpDTO = signUpDTO;
        this.loginInterface = loginInterface;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://gokarna.byethost31.com/login.php");
        JSONObject json = new JSONObject();

        try {
            // JSON data:
            json.put("username", signUpDTO.getUsername());
            json.put("password", signUpDTO.getPassword());

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
    protected void onPostExecute(Boolean result) {
        if (loginInterface != null) {
            if (text.trim().equalsIgnoreCase("success")) {
                loginInterface.result(true);
            } else {
                loginInterface.result(false);
            }
        }
    }
}
