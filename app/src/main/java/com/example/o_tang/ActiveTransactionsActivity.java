package com.example.o_tang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_active_transactions)
public class ActiveTransactionsActivity extends ListTransactionsActivity {

    @ViewById(R.id.activeFilterDebt)
    Button filterDebt;
    @ViewById(R.id.activeFilterReceive)
    Button filterReceive;
    @ViewById(R.id.activeFilterAll)
    Button filterAll;
    @ViewById(R.id.activeTransactionsRecycler)
    RecyclerView transactionsRecycler;
    @ViewById(R.id.debtTotal)
    TextView debtTotalText;
    @ViewById(R.id.receiveTotal)
    TextView receiveTotalText;

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
                .equalTo("userId", uuid)
                .equalTo("isActive", true)
                .findAll();

        TransactionAdapter adapter = new TransactionAdapter(this, list, true);
        transactionsRecycler.setAdapter(adapter);

        double receiveTotal = 0.0;
        double debtTotal = 0.0;
        for(Transaction t : list) {
            if (t.isOwed())
                debtTotal += t.getAmount();
            else
                receiveTotal += t.getAmount();
        }

        receiveTotalText.setText(String.format("%.2f", receiveTotal));
        debtTotalText.setText(String.format("%.2f", debtTotal));
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