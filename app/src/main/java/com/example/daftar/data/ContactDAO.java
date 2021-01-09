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
public interface ContactDAO {
    @Insert
    void insert(Contact contact);

    @Query("SELECT * FROM contact_table")
    LiveData<List<Contact>> getAllContacts();
}