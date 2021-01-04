package com.example.daftar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.daftar.R;

import static com.example.daftar.ui.ContactsActivity.EXTRA_CUSTOMER_NAME;

public class TransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        TextView transaction_customer_name_TV = findViewById(R.id.transaction_customer_name);
        Intent intent = getIntent();
        transaction_customer_name_TV.setText(intent.getStringExtra(EXTRA_CUSTOMER_NAME));

    }
}