package com.example.daftar.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.daftar.R;
import com.example.daftar.model.Customer;
import com.example.daftar.model.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.daftar.ui.CashActivity.EXTRA_CASH;
import static com.example.daftar.ui.CashActivity.EXTRA_NOTE;
import static com.example.daftar.ui.ContactsActivity.EXTRA_CUSTOMER_NAME;
import static com.example.daftar.ui.ContactsActivity.EXTRA_CUSTOMER_NUMBER;

public class TransactionActivity extends AppCompatActivity {

    public static final int TRANSACTION_TYPE_GIVEN_REQUEST = 2;
    public static final int TRANSACTION_TYPE_TAKEN_REQUEST = 4;

    public static final String TRANSACTION_TYPE_GIVEN = "دين";
    public static final String TRANSACTION_TYPE_TAKEN = "أداء";

    private TransactionViewModel transactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        TextView transactionCustomerNameTV = findViewById(R.id.transaction_customer_name);
        Button given_cash_TV = findViewById(R.id.given_cash);
        Button taken_cash_TV = findViewById(R.id.taken_cash);

        Intent intent = getIntent();
        transactionCustomerNameTV.setText(intent.getStringExtra(EXTRA_CUSTOMER_NAME));
        String customerPhoneNumber = intent.getStringExtra(EXTRA_CUSTOMER_NUMBER);

        RecyclerView mRecyclerView = findViewById(R.id.transaction_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        TransactionAdapter mAdapter = new TransactionAdapter();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        List<Transaction> mTransactionList = new ArrayList<>();
        mAdapter.setList(mTransactionList);

        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        transactionViewModel.getAllTransactions().observe(TransactionActivity.this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                mAdapter.setList(transactions);
            }
        });

        given_cash_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this, CashActivity.class);
                startActivityForResult(intent, TRANSACTION_TYPE_GIVEN_REQUEST);
            }
        });

        taken_cash_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this, CashActivity.class);
                startActivityForResult(intent, TRANSACTION_TYPE_TAKEN_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LocalDateTime now = LocalDateTime.now();
        String date = now.toString();
        date = date.substring(0, 11);

        String cash = data.getStringExtra(EXTRA_CASH);
        String note = data.getStringExtra(EXTRA_NOTE);

        if (requestCode == TRANSACTION_TYPE_GIVEN_REQUEST) {
            Transaction transaction = new Transaction(note, date, cash, TRANSACTION_TYPE_GIVEN);
            transactionViewModel.insert(transaction);
        } else if (requestCode == TRANSACTION_TYPE_TAKEN_REQUEST) {
            Transaction transaction = new Transaction(note, date, cash, TRANSACTION_TYPE_TAKEN);
            transactionViewModel.insert(transaction);
        }

    }
}