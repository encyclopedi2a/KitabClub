package com.sunbi.organisatiom.activity.kitabclub.json;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.sunbi.organisatiom.activity.kitabclub.Helper.AppController;
import com.sunbi.organisatiom.activity.kitabclub.adapters.BookCategoriesGridAdpater;
import com.sunbi.organisatiom.activity.kitabclub.models.GridRow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gokarna on 8/6/15.
 */
public class BookCategoriesJSON {
    private Context context;
    private CircleProgressBar progressBar;
    private GridView gridView;
    private List<GridRow> gridItems;
    private static final String URL = "http://thesunbihosting.com/demo/book_store/json/book_category";
    public BookCategoriesJSON(Context context, CircleProgressBar progressBar, GridView gridView) {
        this.context = context;
        this.progressBar = progressBar;
        this.gridView = gridView;
        gridItems=new ArrayList<>();
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
                        String bookName = responseJSONObject.getString("name");
                        String imageURL = responseJSONObject.getString("image");
                        String totalBooks = responseJSONObject.getString("totalBooks");
                        GridRow item = new GridRow(id,bookName,imageURL,totalBooks);
                        gridItems.add(item);
                    }
                    BookCategoriesGridAdpater gridAdpater = new BookCategoriesGridAdpater(context,gridItems);
                    gridView.setAdapter(gridAdpater);
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