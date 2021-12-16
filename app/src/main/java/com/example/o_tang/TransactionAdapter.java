package com.example.o_tang;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class TransactionAdapter extends RealmRecyclerViewAdapter<Transaction, TransactionAdapter.ViewHolder> {
    AppCompatActivity activity;

    public TransactionAdapter(AppCompatActivity activity, @Nullable OrderedRealmCollection<Transaction> data, boolean autoUpdate) {
        super(data, autoUpdate);
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView person, info;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            person = itemView.findViewById(R.id.transactionPerson);
            info = itemView.findViewById(R.id.transactionInfo);
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
        } else {
            holder.info.setText("O You: PHP " + String.format("%.2f",transaction.getAmount()));
        }

        // The whole item is meant to be clickable, so I think a click listener has to go here?
        // There isn't a specific button within the item
    }
}
