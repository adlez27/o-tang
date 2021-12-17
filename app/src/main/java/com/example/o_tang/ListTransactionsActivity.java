package com.example.o_tang;

import androidx.appcompat.app.AppCompatActivity;

public abstract class ListTransactionsActivity extends AppCompatActivity {
    public abstract void putInPrefs(Transaction transaction);
}
