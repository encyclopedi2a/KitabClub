package com.sunbi.organisatiom.activity.kitabclub.json;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.sunbi.organisatiom.activity.kitabclub.Helper.AppController;
import com.sunbi.organisatiom.activity.kitabclub.models.SQLiteData;
import com.sunbi.organisatiom.activity.kitabclub.sqlitedatabase.PushNotificationData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gokarna on 8/6/15.
 */
public class PushNotificationJSON {
    private Context context;
    private CircleProgressBar progressBar;
    private static final String URL = "http://thesunbihosting.com/demo/book_store/json/notificationList";
    public PushNotificationJSON(Context context, CircleProgressBar progressBar) {
        this.context = context;
        this.progressBar = progressBar;
    }

    public void makeJsonArrayRequest() {
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject responseJSONObject = response
                                .getJSONObject(i);
                        String id = responseJSONObject.getString("id");
                        String title = responseJSONObject.getString("title");
                        String message = responseJSONObject.getString("message");
                        SQLiteData sqLiteData=new SQLiteData();
                        sqLiteData.setId(Integer.parseInt(id));
                        sqLiteData.setTitle("<b>"+title+"</b>");
                        sqLiteData.setNotification(message);
                        new PushNotificationData(context).addRecord(sqLiteData);
                    }
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(arrayRequest);
    }
}