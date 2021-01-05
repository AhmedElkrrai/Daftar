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
import static com.example.daftar.ui.FragmentDashboard.EXTRA_CUSTOMER_TOTAL_CASH;
import static com.example.daftar.ui.FragmentDashboard.EXTRA_ID;
import static com.example.daftar.ui.FragmentDashboard.EXTRA_TRANSACTION_TYPE;

public class TransactionActivity extends AppCompatActivity {

    public static final int TRANSACTION_TYPE_GIVEN_REQUEST = 2;
    public static final int TRANSACTION_TYPE_TAKEN_REQUEST = 4;

    public static final String TRANSACTION_TYPE_GIVEN = "دين";
    public static final String TRANSACTION_TYPE_TAKEN = "أداء";
    private String customerTotalCash;
    private String customerName;
    private String customerPhoneNumber;
    private String customerTransactionType;

    private TransactionViewModel transactionViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        TextView transactionCustomerNameTV = findViewById(R.id.transaction_customer_name);
        TextView duePaymentTV = findViewById(R.id.due_payment_cash);

        Button givenCashBT = findViewById(R.id.given_cash);
        Button takenCashBT = findViewById(R.id.taken_cash);

        Intent intent = getIntent();
        customerName = intent.getStringExtra(EXTRA_CUSTOMER_NAME);
        customerTotalCash = intent.getStringExtra(EXTRA_CUSTOMER_TOTAL_CASH);
        customerPhoneNumber = intent.getStringExtra(EXTRA_CUSTOMER_NUMBER);
        customerTransactionType = intent.getStringExtra(EXTRA_TRANSACTION_TYPE);

        transactionCustomerNameTV.setText(customerName);
        duePaymentTV.setText(customerTotalCash);

        if (customerTransactionType.equals(TRANSACTION_TYPE_GIVEN)) {
            int totalCash = Integer.parseInt(customerTotalCash);
            totalCash *= -1;
            customerTotalCash = String.valueOf(totalCash);
        }

        RecyclerView mRecyclerView = findViewById(R.id.transaction_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        TransactionAdapter mAdapter = new TransactionAdapter();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setCustomerName(customerName);

        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        transactionViewModel.getAllTransactions().observe(TransactionActivity.this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                mAdapter.setList(transactions);
            }
        });
        transactionViewModel.duePaymentMutableLiveData.observe(TransactionActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                duePaymentTV.setText(s);
            }
        });

        givenCashBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this, CashActivity.class);
                startActivityForResult(intent, TRANSACTION_TYPE_GIVEN_REQUEST);
            }
        });

        takenCashBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this, CashActivity.class);
                startActivityForResult(intent, TRANSACTION_TYPE_TAKEN_REQUEST);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        String transactionType;

        if (Integer.parseInt(customerTotalCash) > 0)
            transactionType = TRANSACTION_TYPE_TAKEN;
        else transactionType = TRANSACTION_TYPE_GIVEN;

        customerTotalCash = String.valueOf(Math.abs(Integer.parseInt(customerTotalCash)));

        data.putExtra(EXTRA_CUSTOMER_TOTAL_CASH, customerTotalCash);
        data.putExtra(EXTRA_TRANSACTION_TYPE, transactionType);
        data.putExtra(EXTRA_CUSTOMER_NAME, customerName);
        data.putExtra(EXTRA_CUSTOMER_NUMBER, customerPhoneNumber);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1)
            data.putExtra(EXTRA_ID, id);

        setResult(RESULT_OK, data);
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LocalDateTime now = LocalDateTime.now();
        String date = now.toString();
        date = date.substring(0, 11);

        assert data != null;
        String cash = data.getStringExtra(EXTRA_CASH);
        String note = data.getStringExtra(EXTRA_NOTE);

        if (requestCode == TRANSACTION_TYPE_GIVEN_REQUEST) {
            Transaction transaction = new Transaction(note, date, cash, TRANSACTION_TYPE_GIVEN, customerName);

            int duePayment = Integer.parseInt(customerTotalCash) - Integer.parseInt(cash);
            customerTotalCash = String.valueOf(duePayment);
            transactionViewModel.updateDuePaymentMutableLiveData(duePayment);

            transactionViewModel.insert(transaction);
        } else if (requestCode == TRANSACTION_TYPE_TAKEN_REQUEST) {
            Transaction transaction = new Transaction(note, date, cash, TRANSACTION_TYPE_TAKEN, customerName);

            int duePayment = Integer.parseInt(customerTotalCash) + Integer.parseInt(cash);
            customerTotalCash = String.valueOf(duePayment);
            transactionViewModel.updateDuePaymentMutableLiveData(duePayment);

            transactionViewModel.insert(transaction);
        }
    }
}