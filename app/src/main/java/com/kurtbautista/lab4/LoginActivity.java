package com.kurtbautista.lab4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private String savedUsername;
    private String savedPassword;
    private boolean savedRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);
        setTitle("Food Reviews Login");

        try
        {
            prefs = getSharedPreferences("register_data", MODE_PRIVATE);
            savedUsername = prefs.getString("username", "");
            savedPassword = prefs.getString("password", "");
            savedRememberMe = prefs.getBoolean("remember", false);

            ((EditText)findViewById(R.id.usernameText)).setText(savedUsername);
            ((EditText)findViewById(R.id.passwordText)).setText(savedPassword);
            ((CheckBox)findViewById(R.id.rememberCheckBox)).setChecked(true);
        }
        catch(NullPointerException e)
        {
            savedUsername = "";
            savedPassword = "";
            savedRememberMe = false;
        }
    }

    public void signIn(View v) {
        EditText username = (EditText)findViewById(R.id.usernameText);
        EditText password = (EditText)findViewById(R.id.passwordText);
        CheckBox remember = (CheckBox)findViewById(R.id.rememberCheckBox);

        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).equalTo("username", username.getText().toString()).findFirst();
        try {
            if (password.getText().toString().equals(user.getPassword())) {
                Intent i = new Intent(this, com.kurtbautista.lab4.MainActivity.class);
                i.putExtra("user", user.getId());
                if(remember.isChecked()) {
                    prefs = getSharedPreferences("register_data", MODE_PRIVATE);
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putString("username", username.getText().toString());
                    edit.putString("password", password.getText().toString());
                    edit.putBoolean("remember", true);
                    edit.apply();
                }
                startActivity(i);
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        }
        catch (NullPointerException e) {
            Toast.makeText(this, "User does not exit", Toast.LENGTH_SHORT).show();
        }
    }

    public void register(View v) {
        Intent i = new Intent(this, com.kurtbautista.lab4.RegisterActivity.class);
        startActivity(i);
    }
}
