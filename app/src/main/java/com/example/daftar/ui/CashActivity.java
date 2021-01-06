package com.example.daftar.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.daftar.R;
import com.example.daftar.databinding.ActivityCashBinding;

import static com.example.daftar.ui.ContactsActivity.EXTRA_CUSTOMER_NAME;
import static com.example.daftar.ui.ContactsActivity.EXTRA_CUSTOMER_NUMBER;
import static com.example.daftar.ui.FragmentDashboard.EXTRA_CUSTOMER_TOTAL_CASH;
import static com.example.daftar.ui.FragmentDashboard.EXTRA_TRANSACTION_TYPE;


public class CashActivity extends AppCompatActivity {

    CashViewModel viewModel;
    public static final String EXTRA_CASH =
            "package com.example.room.EXTRA_CASH";
    public static final String EXTRA_NOTE =
            "package com.example.room.EXTRA_NOTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_cash);
        viewModel = ViewModelProviders.of(this).get(CashViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        Intent data = getIntent();
        String customerName = data.getStringExtra(EXTRA_CUSTOMER_NAME);

        binding.confirmCashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                String cash = binding.cashTV.getText().toString();
                if (!cash.equals(""))
                    cash = String.valueOf(Integer.parseInt(cash));
                String note = String.valueOf(binding.noteET.getText());

                intent.putExtra(EXTRA_CASH, cash);
                intent.putExtra(EXTRA_NOTE, note);
                intent.putExtra(EXTRA_CUSTOMER_NAME, customerName);

                if (cash.equals("") || cash.equals("0")) {
                    Toast.makeText(CashActivity.this, "Please Enter Transaction.", Toast.LENGTH_SHORT).show();
                    return;
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent data = getIntent();
        String customerName = data.getStringExtra(EXTRA_CUSTOMER_NAME);
        String totalCash = data.getStringExtra(EXTRA_CUSTOMER_TOTAL_CASH);
        String phoneNumber = data.getStringExtra(EXTRA_CUSTOMER_NUMBER);
        String transactionType = data.getStringExtra(EXTRA_TRANSACTION_TYPE);

        Intent intent = new Intent(CashActivity.this, TransactionActivity.class);
        intent.putExtra(EXTRA_CUSTOMER_NAME, customerName);

        intent.putExtra(EXTRA_CUSTOMER_TOTAL_CASH, totalCash);
        intent.putExtra(EXTRA_CUSTOMER_NUMBER, phoneNumber);
        intent.putExtra(EXTRA_TRANSACTION_TYPE, transactionType);

        startActivity(intent);
    }
}