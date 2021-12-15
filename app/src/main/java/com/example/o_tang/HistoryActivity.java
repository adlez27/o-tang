package com.example.o_tang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_history)
public class HistoryActivity extends AppCompatActivity {
    @ViewById(R.id.historyRecycler)
    RecyclerView historyRecycler;

    Realm realm;
    @AfterViews
    public void init(){
        realm = Realm.getDefaultInstance();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        historyRecycler.setLayoutManager(layoutManager);

        RealmResults<Transaction> list = realm.where(Transaction.class).findAll();

        TransactionAdapter adapter = new TransactionAdapter(this, list, true);
        historyRecycler.setAdapter(adapter);
    }

    public void  onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void delete(Transaction transaction){
        realm.beginTransaction();
        realm.where(Transaction.class)
                .equalTo("uuid", transaction.getUuid())
                .findAll()
                .deleteFirstFromRealm();
        realm.commitTransaction();
    }
    
}