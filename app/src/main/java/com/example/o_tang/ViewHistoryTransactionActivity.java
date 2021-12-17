package com.example.o_tang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Locale;

import io.realm.Realm;

@EActivity(R.layout.activity_view_history_transaction)
public class ViewHistoryTransactionActivity extends AppCompatActivity {
    @ViewById(R.id.viewPerson)
    TextView name;
    @ViewById(R.id.viewDirection)
    TextView directionLabel;
    @ViewById(R.id.viewAmount)
    TextView amount;
    @ViewById(R.id.viewContactDetails)
    TextView contactDetails;
    @ViewById(R.id.viewCreationDate)
    TextView creationDate;
    @ViewById(R.id.viewCompletionDate)
    TextView completionDate;

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
        creationDate.setText(df.format(transaction.getDateCreated()));
        completionDate.setText(df.format(transaction.getDateCompleted()));
    }
}