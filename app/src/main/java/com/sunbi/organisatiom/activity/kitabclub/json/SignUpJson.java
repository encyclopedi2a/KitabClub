package com.sunbi.organisatiom.activity.kitabclub.json;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dd.CircularProgressButton;
import com.sunbi.organisatiom.activity.kitabclub.Helper.AppController;
import com.sunbi.organisatiom.activity.kitabclub.SignUp;
import com.sunbi.organisatiom.activity.kitabclub.models.SignUpDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gokarna on 8/3/15.
 */
public class SignUpJson {
    private Context context;
    private SignUpDTO signUpDTO;
    private StringRequest signInRequest;
    private CircularProgressButton signUpButton;

    public SignUpJson(Context context, SignUpDTO signUpDTO, CircularProgressButton signUpButton) {
        this.context = context;
        this.signUpDTO = signUpDTO;
        this.signUpButton = signUpButton;
    }

    String tag_json_obj = "json_obj_req";

    String url = "http://thesunbihosting.com/demo/book_store/json/register";


    public void postJsonValue() {
        signInRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Login Response", response.toString());
                try {
                    JSONObject responseValue = new JSONObject(response);
                    String status = responseValue.getString("status");
                    String message = responseValue.getString("message");
                    Log.d("Login Response", status);
                    if (status.equals("true") && message.equals("success")) {
                        signUpButton.setProgress(100);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((SignUp) (context)).finish();
                                ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                        }, 1500);
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        signUpButton.setProgress(0);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
                Toast.makeText(context, "Error in Connection!!! Try Again...", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", signUpDTO.getUsername());
                params.put("password", signUpDTO.getPassword());
                params.put("email", signUpDTO.getEmail());
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(signInRequest, tag_json_obj);

    }
}

