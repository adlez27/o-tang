package com.example.o_tang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {
    @ViewById(R.id.loginUsername)
    EditText username;
    @ViewById(R.id.loginPassword)
    EditText password;
    @ViewById(R.id.loginRememberMe)
    CheckBox rememberMe;

    Realm realm;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @AfterViews
    public void init() {
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("o-tang", MODE_PRIVATE);
        editor = prefs.edit();

        boolean savedRemember = prefs.getBoolean("rememberMe", false);
        String uuid = prefs.getString("uuid", "");
        if (savedRemember) {
            User user = realm.where(User.class).equalTo("uuid", uuid).findFirst();
            username.setText(user.getUsername());
            password.setText(user.getPassword());
            rememberMe.setChecked(true);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Click(R.id.loginLogin)
    public void login() {
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();

        RealmResults<User> userSearch = realm.where(User.class)
                .equalTo("username", usernameText)
                .findAll();

        if (userSearch.size() < 1) {
            Toast t = Toast.makeText(this, "No users registered", Toast.LENGTH_LONG);
            t.show();
        } else {
            User user = userSearch.get(0);
            if (passwordText.equals(user.getPassword())) {
                editor.putString("uuid", user.getUuid());
                editor.putBoolean("rememberMe", rememberMe.isChecked());
                editor.commit();
                MainActivity_.intent(this).start();
            } else {
                Toast t = Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_LONG);
                t.show();
            }
        }
    }

    @Click(R.id.loginRegister)
    public void register() {
        RegistrationActivity_.intent(this).start();
    }

    @Click(R.id.loginAdmin)
    public void admin() {
        AdminActivity_.intent(this).start();
    }
}