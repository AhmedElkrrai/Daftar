package com.example.daftar.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.daftar.data.CustomerDAO;

@Database(entities = Customer.class, version = 1)
public abstract class CustomerDatabase extends RoomDatabase {

    private static CustomerDatabase instance;

    public abstract CustomerDAO customerDAO();

    public static synchronized CustomerDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CustomerDatabase.class, "customer_table")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

}