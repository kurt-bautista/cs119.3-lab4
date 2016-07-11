package com.kurtbautista.lab4;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);
        setTitle("Create a new user");
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to cancel?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        super.onBackPressed();
    }

    public void createUser(View v) {
        Realm realm = Realm.getDefaultInstance();
        final EditText username = (EditText)findViewById(R.id.newUsernameText);
        final EditText password = (EditText)findViewById(R.id.newPasswordText);
        EditText password2 = (EditText)findViewById(R.id.repeatPasswordText);
        boolean newUser = realm.where(User.class).equalTo("username", username.getText().toString()).findFirst() == null;
        boolean match = password.getText().toString().equals(password2.getText().toString());

        if(newUser) {
            if(match) {
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        User u = realm.createObject(User.class);
                        u.setId(UUID.randomUUID().toString());
                        u.setUsername(username.getText().toString());
                        u.setPassword(password.getText().toString());
                    }
                });
                finish();
            }
            else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
        }
    }
}
