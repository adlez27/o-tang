package com.example.o_tang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Date;
import java.util.UUID;

import io.realm.Realm;

@EActivity(R.layout.activity_edit_transaction)
public class EditTransactionActivity extends AppCompatActivity {
    @ViewById(R.id.editDirectionDebt)
    RadioButton selectDebt;
    @ViewById(R.id.editDirectionReceive)
    RadioButton selectReceive;
    @ViewById(R.id.editAmount)
    EditText amount;
    @ViewById(R.id.editPerson)
    EditText person;
    @ViewById(R.id.editContactDetails)
    EditText contactDetails;

    Realm realm;
    SharedPreferences prefs;

    Transaction transaction;

    @AfterViews
    public void init() {
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("o-tang", MODE_PRIVATE);
        String transactionId = prefs.getString("transactionId", "");
        transaction = realm.where(Transaction.class)
                .equalTo("uuid", transactionId)
                .findFirst();

        selectDebt.setChecked(transaction.isOwed());
        selectReceive.setChecked(!transaction.isOwed());
        amount.setText(String.format("%.2f", transaction.getAmount()));
        person.setText(transaction.getPerson());
        contactDetails.setText(transaction.getContactDetails());
    }

    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Click(R.id.editOK)
    public void save() {
        String personText = person.getText().toString();
        String amountText = amount.getText().toString();
        String contactDetailsText = contactDetails.getText().toString();
        if (amountText.equals("")){
            Toast t = Toast.makeText(this, "Amount must not be blank", Toast.LENGTH_LONG);
            t.show();
        }
        else if (personText.equals("")) {
            Toast t = Toast.makeText(this, "Person must not be blank", Toast.LENGTH_LONG);
            t.show();
        }
        else if (contactDetailsText.equals("")){
            Toast t = Toast.makeText(this, "Contact Details must not be blank", Toast.LENGTH_LONG);
            t.show();
        } else {
            realm.beginTransaction();
            transaction.setOwed(selectDebt.isChecked());
            transaction.setAmount(Double.parseDouble(amountText));
            transaction.setPerson(personText);
            transaction.setContactDetails(contactDetailsText);
            transaction.setDateCreated(new Date());
            realm.commitTransaction();

            Toast t = Toast.makeText(this, "Transaction Edited", Toast.LENGTH_LONG);
            t.show();
            finish();
        }
    }

    @Click(R.id.editCancel)
    public void cancel() {
        finish();
    }
}