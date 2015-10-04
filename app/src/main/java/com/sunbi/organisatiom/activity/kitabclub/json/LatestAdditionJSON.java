package com.sunbi.organisatiom.activity.kitabclub.json;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.sunbi.organisatiom.activity.kitabclub.Helper.AppController;
import com.sunbi.organisatiom.activity.kitabclub.interfaces.LatestAdditionInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gokarna on 8/5/15.
 */
public class LatestAdditionJSON {
    private Context context;
    private String arrayUrl = "http://thesunbihosting.com/demo/book_store/json/latestBook";
    private LatestAdditionInterface latestAdditionInterface;
    private List<String> imagePathContainer;

    public LatestAdditionJSON(Context context, LatestAdditionInterface latestAdditionInterface) {
        this.context = context;
        this.latestAdditionInterface = latestAdditionInterface;
    }

    public void makeJsonArrayRequest() {
        imagePathContainer = new ArrayList<String>();
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(arrayUrl,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject responseJSONObject = (JSONObject) response
                                .getJSONObject(i);
                        String imagePath = responseJSONObject.getString("cover_image");
                        imagePathContainer.add(imagePath);
                        imagePathContainer.add("http://thesunbihosting.com/demo/book_store/uploads/book_cover/bxvOIKgfGaE7.jpg");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (latestAdditionInterface != null) {
                    latestAdditionInterface.result(imagePathContainer);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Toast.makeText(context,
                        "Unable to fetch file from server, Check your internet connection and try again", Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayReq);
    }
}
