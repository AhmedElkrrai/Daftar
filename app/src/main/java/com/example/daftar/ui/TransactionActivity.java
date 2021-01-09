package com.example.daftar.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.daftar.R;
import com.example.daftar.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.daftar.ui.CashActivity.EXTRA_CASH;
import static com.example.daftar.ui.CashActivity.EXTRA_NOTE;
import static com.example.daftar.ui.ContactsActivity.EXTRA_CUSTOMER_NAME;
import static com.example.daftar.ui.ContactsActivity.EXTRA_CUSTOMER_NUMBER;
import static com.example.daftar.ui.FragmentDashboard.EXTRA_CUSTOMER_TOTAL_CASH;
import static com.example.daftar.ui.FragmentDashboard.EXTRA_ID;
import static com.example.daftar.ui.FragmentDashboard.EXTRA_TRANSACTION_TYPE;
import static com.example.daftar.ui.FragmentDashboard.GREEN;
import static com.example.daftar.ui.FragmentDashboard.RED;

public class TransactionActivity extends AppCompatActivity {

    public static final int TRANSACTION_TYPE_GIVEN_REQUEST = 2;
    public static final int TRANSACTION_TYPE_TAKEN_REQUEST = 4;
    private static final int REQUEST_CALL_CODE = 9;

    public static final String TRANSACTION_TYPE_GIVEN = "دين";
    public static final String TRANSACTION_TYPE_TAKEN = "أداء";
    private String customerTotalCash;
    private String customerName;
    private String customerPhoneNumber;

    private TransactionViewModel transactionViewModel;

    private static final String TAG = "TransactionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        TextView transactionCustomerNameTV = findViewById(R.id.transaction_customer_name);
        TextView duePaymentTV = findViewById(R.id.due_payment_cash);
        TextView moneyUnitTV = findViewById(R.id.money_unit_3);

        Button givenCashBT = findViewById(R.id.given_cash);
        Button takenCashBT = findViewById(R.id.taken_cash);

        ImageButton call = findViewById(R.id.call);

        Intent intent = getIntent();
        customerName = intent.getStringExtra(EXTRA_CUSTOMER_NAME);
        customerTotalCash = intent.getStringExtra(EXTRA_CUSTOMER_TOTAL_CASH);
        customerPhoneNumber = intent.getStringExtra(EXTRA_CUSTOMER_NUMBER);
        String customerTransactionType = intent.getStringExtra(EXTRA_TRANSACTION_TYPE);

        transactionCustomerNameTV.setText(customerName);
        duePaymentTV.setText(customerTotalCash);

        if (customerTransactionType.equals(TRANSACTION_TYPE_GIVEN)) {
            int totalCash = Integer.parseInt(customerTotalCash);
            totalCash *= -1;
            customerTotalCash = String.valueOf(totalCash);
            duePaymentTV.setTextColor(RED);
            moneyUnitTV.setTextColor(RED);
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
                int totalCash = Integer.parseInt(s);
                if (totalCash < 0) {
                    duePaymentTV.setTextColor(RED);
                    moneyUnitTV.setTextColor(RED);
                    duePaymentTV.setText(String.valueOf(totalCash * -1));
                } else {
                    duePaymentTV.setTextColor(GREEN);
                    moneyUnitTV.setTextColor(GREEN);
                    duePaymentTV.setText(s);
                }
            }
        });

        Intent i = new Intent(TransactionActivity.this, CashActivity.class);

        i.putExtra(EXTRA_CUSTOMER_NAME, customerName);
        i.putExtra(EXTRA_CUSTOMER_TOTAL_CASH, customerTotalCash);
        i.putExtra(EXTRA_CUSTOMER_NUMBER, customerPhoneNumber);
        i.putExtra(EXTRA_TRANSACTION_TYPE, customerTransactionType);

        givenCashBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(i, TRANSACTION_TYPE_GIVEN_REQUEST);
            }
        });

        takenCashBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(i, TRANSACTION_TYPE_TAKEN_REQUEST);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LocalDateTime now = LocalDateTime.now();
        String date = now.toString();
        date = date.substring(0, 11);

        String cash = null, note = null, customerName = null;
        if (data != null) {
            cash = data.getStringExtra(EXTRA_CASH);
            note = data.getStringExtra(EXTRA_NOTE);
            customerName = data.getStringExtra(EXTRA_CUSTOMER_NAME);
        }

        if (requestCode == TRANSACTION_TYPE_GIVEN_REQUEST && resultCode == RESULT_OK) {
            Transaction transaction = new Transaction(note, date, cash, TRANSACTION_TYPE_GIVEN, customerName);

            int duePayment = Integer.parseInt(customerTotalCash) - Integer.parseInt(cash);
            customerTotalCash = String.valueOf(duePayment);
            transactionViewModel.updateDuePaymentMutableLiveData(duePayment);

            transactionViewModel.insert(transaction);
        } else if (requestCode == TRANSACTION_TYPE_TAKEN_REQUEST && resultCode == RESULT_OK) {
            Transaction transaction = new Transaction(note, date, cash, TRANSACTION_TYPE_TAKEN, customerName);

            int duePayment = Integer.parseInt(customerTotalCash) + Integer.parseInt(cash);
            customerTotalCash = String.valueOf(duePayment);
            transactionViewModel.updateDuePaymentMutableLiveData(duePayment);

            transactionViewModel.insert(transaction);
        }
    }

    public void makePhoneCall() {
        if (customerPhoneNumber.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(TransactionActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                String dial = "tel:" + customerPhoneNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            } else {
                //show a dialog to the user to ask him for the permission
                ActivityCompat.requestPermissions(TransactionActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_CODE);
            }
        }
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
}