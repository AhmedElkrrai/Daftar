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

        binding.confirmCashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String cash = (String) binding.cashTV.getText();
                cash = String.valueOf(Integer.parseInt(cash));
                String note = String.valueOf(binding.noteET.getText());

                intent.putExtra(EXTRA_CASH, cash);
                intent.putExtra(EXTRA_NOTE, note);
                if (cash.equals("") || cash.equals("0")) {
                    Toast.makeText(CashActivity.this, "Please enter transaction.", Toast.LENGTH_SHORT).show();
                    return;
                }

                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}