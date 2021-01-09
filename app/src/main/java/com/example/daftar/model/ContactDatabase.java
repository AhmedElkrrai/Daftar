package com.example.daftar.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.daftar.data.ContactDAO;

@Database(entities = Contact.class, version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    private static ContactDatabase instance;

    public abstract ContactDAO contactDAO();

    public static synchronized ContactDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class, "contact_table")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}