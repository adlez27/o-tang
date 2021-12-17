package com.example.o_tang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_history)
public class HistoryActivity extends ListTransactionsActivity{
    @ViewById(R.id.historyRecycler)
    RecyclerView historyRecycler;

    Realm realm;
    SharedPreferences prefs;

    @AfterViews
    public void init(){
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("o-tang", MODE_PRIVATE);
        String uuid = prefs.getString("uuid","");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        historyRecycler.setLayoutManager(layoutManager);

        RealmResults<Transaction> list = realm
                .where(Transaction.class)
                .equalTo("userId", uuid)
                .equalTo("isActive", false)
                .findAll();

        TransactionAdapter adapter = new TransactionAdapter(this, list, true);
        historyRecycler.setAdapter(adapter);
    }

    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void putInPrefs(Transaction transaction) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("transactionId", transaction.getUuid());
        editor.commit();
    }
}