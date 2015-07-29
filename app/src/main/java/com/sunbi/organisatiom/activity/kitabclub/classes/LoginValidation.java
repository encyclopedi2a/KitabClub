package com.sunbi.organisatiom.activity.kitabclub.classes;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gokarna on 7/26/15.
 */
public class LoginValidation {
    private EditText username;
    private EditText password;
    private EditText reTypePassword;
    private EditText email;
    private boolean flag = false;

    public LoginValidation(EditText username, EditText password, EditText reTypePassword, EditText email) {
        this.username = username;
        this.password = password;
        this.reTypePassword = reTypePassword;
        this.email = email;
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
        } else if (email.getText().toString().equals("")||!emailValidator(email.getText().toString())) {
            email.setError("Invalid email address");
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }
    private boolean emailValidator(String email){
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
