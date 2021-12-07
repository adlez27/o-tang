package com.example.o_tang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_admin)
public class AdminActivity extends AppCompatActivity {
    @ViewById(R.id.adminRecycler)
    RecyclerView adminRecycler;

    Realm realm;

    @AfterViews
    public void init() {
        realm = Realm.getDefaultInstance();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        adminRecycler.setLayoutManager(layoutManager);

        RealmResults<User> list = realm.where(User.class).findAll();

        UserAdapter adapter = new UserAdapter(this, list, true);
        adminRecycler.setAdapter(adapter);
    }

    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void delete(User user){
        realm.beginTransaction();
        realm.where(User.class)
                .equalTo("uuid", user.getUuid())
                .findAll()
                .deleteFirstFromRealm();
        realm.commitTransaction();
    }

    @Click(R.id.adminClear)
    public void clearAll() {
        realm.beginTransaction();
        realm.where(User.class)
                .findAll()
                .deleteAllFromRealm();
        realm.commitTransaction();

        SharedPreferences.Editor editor = getSharedPreferences("o-tang", MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();

        Toast t = Toast.makeText(this, "All users cleared", Toast.LENGTH_LONG);
        t.show();
    }
}