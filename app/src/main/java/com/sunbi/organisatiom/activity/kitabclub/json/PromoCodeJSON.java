package com.sunbi.organisatiom.activity.kitabclub.json;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sunbi.organisatiom.activity.kitabclub.Helper.AppController;
import com.sunbi.organisatiom.activity.kitabclub.interfaces.PromoCodeResultHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gokarna on 8/4/15.
 */
public class PromoCodeJSON {
    private String arrayUrl = "http://thesunbihosting.com/demo/book_store/json/checkPromoCode";
    private StringRequest bokSubCategoryRequest;
    private Context context;
    private String promoCode = null;
    private PromoCodeResultHolder promoCodeResultHolder;
    private static final String tag_json_obj = "TAG_REQUEST";

    public PromoCodeJSON(Context context, String promoCode,PromoCodeResultHolder promoCodeResultHolder) {
        this.context = context;
        this.promoCode = promoCode;
        this.promoCodeResultHolder=promoCodeResultHolder;
    }

    public void postJsonValue() {
        bokSubCategoryRequest = new StringRequest(Request.Method.POST, arrayUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Login Response", response.toString());
                try {
                    JSONObject responseValue = new JSONObject(response);
                    final String status = responseValue.getString("status");
                    if(status.equals("Code Matched")){
                        promoCodeResultHolder.result("true");
                    }else{
                        promoCodeResultHolder.result("false");
                    }
                    Log.d("Login Response", status);
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
                params.put("category_id", promoCode);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(bokSubCategoryRequest, tag_json_obj);

    }
}
