package com.example.daftar.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.daftar.model.Transaction;

import java.util.List;

@Dao
public interface TransactionDAO {
    @Insert
    void insert(Transaction transaction);

    @Query("SELECT * FROM transaction_table")
    LiveData<List<Transaction>> getAllTransactions();
}
