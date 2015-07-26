package com.sunbi.organisatiom.activity.kitabclub.classes;

import android.widget.EditText;

/**
 * Created by gokarna on 7/26/15.
 */
public class LoginValidation {
    private EditText username;
    private EditText password;
    private boolean flag=false;
    public LoginValidation(EditText username,EditText password){
        this.username=username;
        this.password=password;
    }
    public boolean validateLogin(){
        if(username.getText().toString().equals("")){
            username.setError("Username field cannot be blank");
            flag=true;
        }else if(password.getText().toString().equals("")){
            password.setError("Password field cannot be blank");
            flag=true;
        }else{
            flag=false;
        }
        return flag;
    }
}
