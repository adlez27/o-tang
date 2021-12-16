package com.example.o_tang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Date;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_add_transaction)
public class AddTransactionActivity extends AppCompatActivity {
    @ViewById(R.id.addTransactionPerson)
    EditText transactionPerson;

    @ViewById(R.id.addTransactionAmount)
    EditText transactionAmount;

    @ViewById(R.id.addTransactionContactDetails)
    EditText transactionContactDetails;

    @ViewById(R.id.addTransactionDebt)
    RadioButton transactionDebt;

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

    @Click(R.id.addTransactionSave)
    public void addTransaction() {
        String person = transactionPerson.getText().toString();
        String amountText = transactionAmount.getText().toString();
        String contactDetails = transactionContactDetails.getText().toString();
        if (amountText.equals("")){
            Toast t = Toast.makeText(this, "Amount must not be blank", Toast.LENGTH_LONG);
            t.show();
        }
        else if (person.equals("")) {
            Toast t = Toast.makeText(this, "Person must not be blank", Toast.LENGTH_LONG);
            t.show();
        }
        else if (contactDetails.equals("")){
            Toast t = Toast.makeText(this, "Contact Details must not be blank", Toast.LENGTH_LONG);
            t.show();
        } else {
            realm.beginTransaction();
            Transaction transaction = realm.createObject(Transaction.class, UUID.randomUUID().toString());
            transaction.setUserId(prefs.getString("uuid",""));
            transaction.setOwed(transactionDebt.isChecked());
            transaction.setActive(true);
            transaction.setAmount(Double.parseDouble(amountText));
            transaction.setPerson(person);
            transaction.setContactDetails(contactDetails);
            transaction.setDateCreated(new Date());
            realm.commitTransaction();

            Toast t = Toast.makeText(this, "Transaction Added", Toast.LENGTH_LONG);
            t.show();
            finish();
        }
    }
    @Click(R.id.addTransactionCancel)
    public void register() {
        MainActivity_.intent(this).start();
    }
}