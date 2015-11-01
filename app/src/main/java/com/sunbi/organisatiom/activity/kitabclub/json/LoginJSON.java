package com.sunbi.organisatiom.activity.kitabclub.json;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dd.CircularProgressButton;
import com.sunbi.organisatiom.activity.kitabclub.Helper.AppController;
import com.sunbi.organisatiom.activity.kitabclub.interfaces.LoginInterface;
import com.sunbi.organisatiom.activity.kitabclub.models.SignUpDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gokarna on 8/5/15.
 */
public class LoginJSON {
    private Context context;
    private SignUpDTO signUpDTO;
    private StringRequest signInRequest;
    private CircularProgressButton signUpButton;
    private LoginInterface loginInterface;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public LoginJSON(Context context, SignUpDTO signUpDTO, CircularProgressButton signUpButton, LoginInterface loginInterface) {
        this.context = context;
        this.signUpDTO = signUpDTO;
        this.signUpButton = signUpButton;
        this.loginInterface = loginInterface;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    String tag_json_obj = "json_obj_req";

    String url = "http://thesunbihosting.com/demo/book_store/json/login";


    public void postJsonValue() {
        signInRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Login Response", response.toString());
                try {
                    JSONObject responseValue = new JSONObject(response);
                    final String status = responseValue.getString("status");
                    final String message = responseValue.getString("message");
                    Log.d("Login Response", status);
                    signUpButton.setProgress(100);

                    if (loginInterface != null) {
                        if (status.equals("true") && message.equals("login successfull")) {
                            Log.d("Message type:",responseValue.getString("user_id"));
                            editor.putString("userId", responseValue.getString("user_id"));
                            loginInterface.result(true);
                        } else {
                            loginInterface.result(false);
                        }
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
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(signInRequest, tag_json_obj);

    }
}


