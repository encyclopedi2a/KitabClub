package com.sunbi.organisatiom.activity.kitabclub.json;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sunbi.organisatiom.activity.kitabclub.Helper.AppController;
import com.sunbi.organisatiom.activity.kitabclub.interfaces.LatestAdditionInterface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gokarna on 8/5/15.
 */
public class LatestAdditionJSON {
    private Context context;
    private String arrayUrl = "http://thesunbihosting.com/demo/book_store/json/latestBook";
    private LatestAdditionInterface latestAdditionInterface;
    public LatestAdditionJSON(Context context,LatestAdditionInterface latestAdditionInterface) {
        this.context = context;
        this.latestAdditionInterface=latestAdditionInterface;
    }

    public void makeJsonArrayRequest() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                arrayUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Parsing json object response
                    // response will be a json object
                    String imagePath = response.getString("cover_image");
                    if(latestAdditionInterface!=null){
                        latestAdditionInterface.result(imagePath);
                    }
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
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
