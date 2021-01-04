package com.example.daftar.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.daftar.R;
import com.example.daftar.model.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.daftar.ui.ContactsActivity.EXTRA_CUSTOMER_NAME;

public class TransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        TextView transaction_customer_name_TV = findViewById(R.id.transaction_customer_name);
        Button given_cash_TV = findViewById(R.id.given_cash);
        Button taken_cash_TV = findViewById(R.id.taken_cash);

        Intent intent = getIntent();
        transaction_customer_name_TV.setText(intent.getStringExtra(EXTRA_CUSTOMER_NAME));

        RecyclerView mRecyclerView = findViewById(R.id.transaction_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        TransactionAdapter mAdapter = new TransactionAdapter();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        List<Transaction> mTransactionList = new ArrayList<>();
        mAdapter.setList(mTransactionList);

        LocalDateTime now = LocalDateTime.now();
        String date = now.toString();
        date = date.substring(0, 11);

        String finalDate = date;

        given_cash_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTransactionList.add(new Transaction("Note", finalDate, "2000", "دين"));
                mAdapter.notifyDataSetChanged();
            }
        });

        taken_cash_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTransactionList.add(new Transaction("Note", finalDate, "3000", "أداء"));
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}