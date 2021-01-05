package com.example.daftar.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.daftar.data.TransactionDAO;

import java.util.List;

public class TransactionRepository {
    private TransactionDAO transactionDAO;
    private LiveData<List<Transaction>> allTransactions;

    public TransactionRepository(Application application) {

        TransactionDatabase transactionDatabase = TransactionDatabase.getInstance(application);

        transactionDAO = transactionDatabase.transactionDAO();

        allTransactions = transactionDAO.getAllTransactions();
    }

    public void insert(Transaction transaction) {
        new InsertTransactionAsyncTask(transactionDAO).execute(transaction);
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }

    private static class InsertTransactionAsyncTask extends AsyncTask<Transaction, Void, Void> {
        private TransactionDAO transactionDAO;

        public InsertTransactionAsyncTask(TransactionDAO transactionDAO) {
            this.transactionDAO = transactionDAO;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            transactionDAO.insert(transactions[0]);
            return null;
        }
    }
}