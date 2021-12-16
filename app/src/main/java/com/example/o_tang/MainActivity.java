package com.example.o_tang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewById(R.id.mainPerson)
    TextView name;
    @ViewById(R.id.mainTotalReceive)
    TextView receiveTotalText;
    @ViewById(R.id.mainTotalDebt)
    TextView debtTotalText;

    Realm realm;
    SharedPreferences prefs;

    @AfterViews
    public void init() {
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("o-tang", MODE_PRIVATE);

        String userId = prefs.getString("uuid", "");
        User user = realm.where(User.class)
                .equalTo("uuid", userId)
                .findFirst();

        name.setText(user.getUsername());

        RealmResults<Transaction> userTransactions = realm.where(Transaction.class)
                .equalTo("userId", userId)
                .findAll();

        double receiveTotal = 0.0;
        double debtTotal = 0.0;
        for(Transaction t : userTransactions) {
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
