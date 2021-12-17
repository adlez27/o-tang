package com.example.o_tang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

@EActivity(R.layout.activity_view_transaction)
public class ViewTransactionActivity extends AppCompatActivity {
    @ViewById(R.id.viewPerson)
    TextView name;
    @ViewById(R.id.viewDirection)
    TextView directionLabel;
    @ViewById(R.id.viewAmount)
    TextView amount;
    @ViewById(R.id.viewContactDetails)
    TextView contactDetails;
    @ViewById(R.id.viewCreationDate)
    TextView date;

    Realm realm;
    SharedPreferences prefs;

    Transaction transaction;

    @AfterViews
    public void init(){
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("o-tang", MODE_PRIVATE);
        String transactionId = prefs.getString("transactionId", "");
        transaction = realm.where(Transaction.class)
                .equalTo("uuid", transactionId)
                .findFirst();

        name.setText(transaction.getPerson());
        if (transaction.isOwed()) {
            directionLabel.setText("YOU O: ");
            amount.setTextColor(Color.RED);
        } else {
            directionLabel.setText("O YOU: ");
            amount.setTextColor(Color.GREEN);
        }

        amount.setText(String.format("%.2f", transaction.getAmount()));
        contactDetails.setText(transaction.getContactDetails());

        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        date.setText(df.format(transaction.getDateCreated()));
    }

    @Click(R.id.viewComplete)
    public void complete(){
        realm.beginTransaction();
        transaction.setDateCompleted(new Date());
        transaction.setActive(false);
        realm.commitTransaction();
        finish();
        Toast t = Toast.makeText(this, "Transaction completed", Toast.LENGTH_LONG);
        t.show();
    }

    @Click(R.id.viewEdit)
    public void edit() {
        EditTransactionActivity_.intent(this).start();
    }

    @Click(R.id.viewDelete)
    public void delete(){
        realm.beginTransaction();
        realm.where(Transaction.class)
                .equalTo("uuid", transaction.getUuid())
                .findAll()
                .deleteFirstFromRealm();
        realm.commitTransaction();
        finish();
        Toast t = Toast.makeText(this, "Transaction deleted", Toast.LENGTH_LONG);
        t.show();
    }
}