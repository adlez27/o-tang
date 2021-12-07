package com.example.o_tang;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;

public class UserAdapter extends RealmRecyclerViewAdapter<User, UserAdapter.ViewHolder> {
    AdminActivity activity;
    Realm realm;

    public UserAdapter(AdminActivity activity, @Nullable OrderedRealmCollection<User> data, boolean autoUpdate) {
        super(data, autoUpdate);
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, password, transactions;
        ImageButton delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.userUsername);
            password = itemView.findViewById(R.id.userPassword);
            transactions = itemView.findViewById(R.id.userTransactions);
            delete = itemView.findViewById(R.id.userDelete);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = activity.getLayoutInflater().inflate(R.layout.user_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = getItem(position);

        holder.username.setText(user.getUsername());
        holder.password.setText(user.getPassword());

        realm = Realm.getDefaultInstance();
        int transactionCount = realm.where(Transaction.class)
                .equalTo("userId", user.getUuid())
                .findAll()
                .size();
        realm.close();

        holder.transactions.setText("# of transactions: " + transactionCount);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.delete(user);
            }
        });
    }
}
