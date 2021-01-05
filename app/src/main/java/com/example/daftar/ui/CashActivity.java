package com.example.daftar.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.daftar.R;
import com.example.daftar.databinding.ActivityCashBinding;


public class CashActivity extends AppCompatActivity {

    CashViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_cash);
        viewModel = ViewModelProviders.of(this).get(CashViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);



    }
}