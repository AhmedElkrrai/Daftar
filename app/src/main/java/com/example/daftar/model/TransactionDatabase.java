package com.example.daftar.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.daftar.data.TransactionDAO;


@Database(entities = Transaction.class, version = 1)

public abstract class TransactionDatabase extends RoomDatabase {
    private static TransactionDatabase instance;

    public abstract TransactionDAO transactionDAO();

    public static synchronized TransactionDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TransactionDatabase.class, "transaction_table")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
