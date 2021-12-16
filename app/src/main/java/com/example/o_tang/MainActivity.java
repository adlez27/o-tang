package com.example.o_tang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.mainActiveButton)
    Button activeButton;

    Realm realm;
    SharedPreferences prefs;

    @AfterViews
    public void init() {
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("o-tang", MODE_PRIVATE);
    }

    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Click(R.id.mainActiveButton)
    public void viewActives()
    {
        ActiveTransactionsActivity_.intent(this).start();
    }

    @Click(R.id.mainHistoryButton)
    public void history(){ HistoryActivity_.intent(this).start();}

    @Click(R.id.mainAddButton)
    public void goToAddTransaction(){ AddTransactionActivity_.intent(this).start();}

}
