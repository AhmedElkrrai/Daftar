package com.example.daftar.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.daftar.model.Contact;
import com.example.daftar.model.Customer;

import java.util.List;

@Dao
public interface CustomerDAO {
    @Insert
    void insert(Customer customer);

    @Update
    void update(Customer customer);

    @Query("SELECT * FROM customer_table")
    LiveData<List<Customer>> getAllCustomers();
}