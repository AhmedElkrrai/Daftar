package com.example.daftar.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.daftar.model.Customer;
import com.example.daftar.model.Transaction;
import com.example.daftar.model.TransactionRepository;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {

    private TransactionRepository repository;
    private LiveData<List<Transaction>> allTransactions;
    public MutableLiveData<String> duePaymentMutableLiveData = new MutableLiveData<>();

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepository(application);
        allTransactions = repository.getAllTransactions();
    }

    public void updateDuePaymentMutableLiveData(int cash) {
        duePaymentMutableLiveData.setValue(String.valueOf(cash));
    }

    public void insert(Transaction transaction) {
        repository.insert(transaction);
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }
}