package com.sunbi.organisatiom.activity.kitabclub.classes;

import android.widget.EditText;

/**
 * Created by gokarna on 7/26/15.
 */
public class LoginValidation {
    private EditText username;
    private EditText password;
    private EditText reTypePassword;
    private boolean flag = false;

    public LoginValidation(EditText username, EditText password, EditText reTypePassword) {
        this.username = username;
        this.password = password;
        this.reTypePassword = reTypePassword;
    }

    public boolean validateLogin() {
        if (username.getText().toString().equals("")) {
            username.setError("Username field cannot be blank");
            flag = true;
        } else if (password.getText().toString().equals("")) {
            password.setError("Password field cannot be blank");
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public boolean validateSignUp() {
        if (username.getText().toString().equals("")) {
            username.setError("Username field cannot be blank");
            flag = true;
        } else if (password.getText().toString().equals("")) {
            password.setError("Password field cannot be blank");
            flag = true;
        } else if (reTypePassword.getText().toString().equals("") || (!password.getText().toString().equals(reTypePassword.getText().toString()))) {
            reTypePassword.setError("password do not match");
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }
}
