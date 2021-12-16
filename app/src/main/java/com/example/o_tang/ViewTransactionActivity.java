package com.example.o_tang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_view_transaction)
public class ViewTransactionActivity extends AppCompatActivity {
    @ViewById(R.id.viewComplete)
    Button ViewComplete;
    @ViewById(R.id.viewPerson)
    TextView ViewPerson;
    Realm realm;
    SharedPreferences prefs;

    public void init(){
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("o-tang", MODE_PRIVATE);
        String uuid = prefs.getString("uuid", "");
        realm.where(Transaction.class)
                .equalTo("userId", uuid)
                .equalTo("isActive", true)
                .findFirst();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);
    }
    @Click(R.id.viewComplete)
    public void CompleteTransactionClicked(Transaction transaction){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        Transaction CT = realm.where(Transaction.class)
                .equalTo("uuid", transaction.getUuid())
                .findFirst();
        realm.beginTransaction();
        CT.setOwed(false);
        CT.setDateCompleted(c);
        CT.setActive(false);



    }

    @Click(R.id.viewDelete)
    public void delete(Transaction transaction){
        realm.beginTransaction();
        realm.where(Transaction.class)
                .equalTo("uuid", transaction.getUuid())
                .findAll()
                .deleteFirstFromRealm();
        realm.commitTransaction();
    }
}