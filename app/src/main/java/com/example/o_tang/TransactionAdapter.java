package com.example.o_tang;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class TransactionAdapter extends RealmRecyclerViewAdapter<Transaction, TransactionAdapter.ViewHolder> {
    ListTransactionsActivity activity;

    public TransactionAdapter(ListTransactionsActivity activity, @Nullable OrderedRealmCollection<Transaction> data, boolean autoUpdate) {
        super(data, autoUpdate);
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView person, info;
        ConstraintLayout item;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            person = itemView.findViewById(R.id.transactionPerson);
            info = itemView.findViewById(R.id.transactionInfo);
            item = itemView.findViewById(R.id.View_Transaction);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = activity.getLayoutInflater().inflate(R.layout.transaction_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = getItem(position);

        holder.person.setText(transaction.getPerson());

        if (transaction.isOwed()) {
            holder.info.setText("You O: PHP " + String.format("%.2f",transaction.getAmount()));
            holder.info.setTextColor(Color.RED);
        } else {
            holder.info.setText("O You: PHP " + String.format("%.2f",transaction.getAmount()));
            holder.info.setTextColor(Color.GREEN);
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.putInPrefs(transaction);
                if (transaction.isActive()) {
                    ViewTransactionActivity_.intent(activity).start();
                } else {
                    ViewHistoryTransactionActivity_.intent(activity).start();
                }
            }
        });
    }
}
