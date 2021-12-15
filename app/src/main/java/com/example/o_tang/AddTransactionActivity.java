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

    @ViewById(R.id.addTransactionReceive)
    RadioButton transactionReceive;

    Realm realm;

    @AfterViews
    public void init() {
        realm = Realm.getDefaultInstance();
    }

    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Click(R.id.addTransactionSave)
    public void addTransaction() {
        String transactionPersonText = transactionPerson.getText().toString();
        Double transactionAmountDouble = Double.parseDouble(transactionAmount.getText().toString());
        String transactionContactDetailText = transactionContactDetails.getText().toString();
        if (transactionAmountDouble.equals("")){
            Toast t = Toast.makeText(this, "Amount must not be blank", Toast.LENGTH_LONG);
            t.show();
        }
        else if (transactionContactDetailText.equals("")){
            Toast t = Toast.makeText(this, "Contact Details must not be blank", Toast.LENGTH_LONG);
            t.show();
        }
        else if (transactionPersonText.equals("")) {
            Toast t = Toast.makeText(this, "Person must not be blank", Toast.LENGTH_LONG);
            t.show();
        } else {
            realm.beginTransaction();
            Transaction transaction = realm.createObject(Transaction.class, UUID.randomUUID().toString());
            transaction.setPerson(transactionPersonText);
            transaction.setAmount(transactionAmountDouble);
            transaction.setPerson(transactionPersonText);
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