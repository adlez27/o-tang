package com.example.o_tang;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.UUID;

import io.realm.Realm;

@EActivity(R.layout.activity_registration)
public class RegistrationActivity extends AppCompatActivity {
    @ViewById(R.id.registrationUsername)
    EditText username;
    @ViewById(R.id.registrationPassword)
    EditText password;
    @ViewById(R.id.registrationPasswordConfirm)
    EditText passwordConfirm;

    Realm realm;

    @AfterViews
    public void init() {
        realm = Realm.getDefaultInstance();
    }

    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Click(R.id.registrationRegister)
    public void register() {
        String usernameText = username.getText().toString();
        String pwText = password.getText().toString();
        String pwConfText = passwordConfirm.getText().toString();

        if (usernameText.equals("")) {
            Toast t = Toast.makeText(this, "Name must not be blank", Toast.LENGTH_LONG);
            t.show();
        } else if (realm.where(User.class)
                .equalTo("username", usernameText)
                .findAll().size() > 0) {
            Toast t = Toast.makeText(this, "User already exists.", Toast.LENGTH_LONG);
            t.show();
        } else if (pwText.equals(pwConfText)) {
            realm.beginTransaction();
                User user = realm.createObject(User.class, UUID.randomUUID().toString());
                user.setUsername(usernameText);
                user.setPassword(pwText);
            realm.commitTransaction();

            Toast t = Toast.makeText(this, "Registration successful.", Toast.LENGTH_LONG);
            t.show();
            finish();
        } else {
            Toast t = Toast.makeText(this, "Confirm password does not match.", Toast.LENGTH_LONG);
            t.show();
        }
    }
}