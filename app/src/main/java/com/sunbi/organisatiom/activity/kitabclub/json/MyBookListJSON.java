package com.sunbi.organisatiom.activity.kitabclub.json;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.sunbi.organisatiom.activity.kitabclub.Helper.AppController;
import com.sunbi.organisatiom.activity.kitabclub.adapters.CustomMyBooksListAdapter;
import com.sunbi.organisatiom.activity.kitabclub.models.ListRow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gokarna on 8/4/15.
 */
public class MyBookListJSON {
    private Context context;
    private ListView listView;
    private CircleProgressBar progressBar;
    private String arrayUrl = "http://gokarna.byethost31.com/connect.php";
    private List<ListRow> listItems;
    private List<String> imageListItems;

    public MyBookListJSON(Context context, ListView listView, CircleProgressBar progressBar) {
        this.context = context;
        this.listView = listView;
        this.progressBar = progressBar;
        listItems = new ArrayList<>();
        imageListItems = new ArrayList();
    }

    public void makeJsonArrayRequest() {
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(arrayUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    // Parsing json array response
                    // loop through each json object
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject person = (JSONObject) response
                                .getJSONObject(i);
                        String collegeName = person.getString("colz_name");
                        String imageURL = person.getString("image_path");
                        ListRow item = new ListRow(collegeName, imageURL);
                        listItems.add(item);

                    }
                    CustomMyBooksListAdapter adapter = new CustomMyBooksListAdapter(listItems, context);
                    listView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

                //hidepDialog();
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

