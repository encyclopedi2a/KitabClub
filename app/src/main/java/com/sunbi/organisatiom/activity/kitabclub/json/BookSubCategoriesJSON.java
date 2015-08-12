package com.sunbi.organisatiom.activity.kitabclub.json;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.sunbi.organisatiom.activity.kitabclub.Helper.AppController;
import com.sunbi.organisatiom.activity.kitabclub.adapters.BookSubCategoriesGridAdpater;
import com.sunbi.organisatiom.activity.kitabclub.models.GridRow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gokarna on 8/4/15.
 */
public class BookSubCategoriesJSON {
    private String arrayUrl = "http://thesunbihosting.com/demo/book_store/json/book_sub_category";
    private StringRequest bokSubCategoryRequest;
    private Context context;
    private  CircleProgressBar progressBar;
    private GridView gridView;
    private List<GridRow> gridItems;
    private String id=null;
    private static final String tag_json_obj="TAG_REQUEST";
    public BookSubCategoriesJSON(Context context, GridView gridView, CircleProgressBar progressBar,String id) {
        this.context=context;
        this.gridView=gridView;
        this.progressBar=progressBar;
        this.id=id;
        gridItems=new ArrayList<>();
    }

    public void postJsonValue() {
        bokSubCategoryRequest= new StringRequest(Request.Method.GET,arrayUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Login Response", response.toString());
                try {
                    JSONArray responseValue = new JSONArray(response);
                    try {
                        for (int i = 0; i < responseValue.length(); i++) {
                            JSONObject responseJSONObject = (JSONObject) responseValue
                                    .getJSONObject(i);
                            String id = responseJSONObject.getString("id");
                            String bookName = responseJSONObject.getString("name");
                            String imageURL = responseJSONObject.getString("name");
                            String totalBooks = responseJSONObject.getString("totalBooks");
                            GridRow item = new GridRow(id,bookName,imageURL,totalBooks);
                            gridItems.add(item);
                        }
                        BookSubCategoriesGridAdpater gridAdpater = new BookSubCategoriesGridAdpater(context,gridItems);
                        gridView.setAdapter(gridAdpater);
                        progressBar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                       Log.d("Message:",e.getMessage());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("category_id",id);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(bokSubCategoryRequest, tag_json_obj);

    }
}
