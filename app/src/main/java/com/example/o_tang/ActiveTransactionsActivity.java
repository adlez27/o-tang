package com.example.o_tang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_active_transactions)
public class ActiveTransactionsActivity extends AppCompatActivity {

    @ViewById(R.id.activeFilterDebt)
    Button filterDebt;
    @ViewById(R.id.activeFilterReceive)
    Button filterReceive;
    @ViewById(R.id.activeFilterAll)
    Button filterAll;
    @ViewById(R.id.activeTransactionsRecycler)
    RecyclerView transactionsRecycler;
    @ViewById(R.id.debtTotal)
    TextView debtTotal;
    @ViewById(R.id.receiveTotal)
    TextView receiveTotal;

    Realm realm;
    SharedPreferences prefs;

    @AfterViews
    public void init() {
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("o-tang", MODE_PRIVATE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        transactionsRecycler.setLayoutManager(layoutManager);

        String uuid = prefs.getString("uuid", "");
        RealmResults<Transaction> list = realm.where(Transaction.class)
                .contains("userId", uuid)
                .findAll();

        TransactionAdapter adapter = new TransactionAdapter(this, list, true);
        transactionsRecycler.setAdapter(adapter);
    }

    public void onDestroy() {
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