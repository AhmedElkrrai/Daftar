package com.example.daftar.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.daftar.data.ContactDAO;

import java.util.List;

public class ContactRepository {

    private ContactDAO contactDAO;
    private LiveData<List<Contact>> allContacts;

    public ContactRepository(Application application) {

        ContactDatabase contactDatabase = ContactDatabase.getInstance(application);

        contactDAO = contactDatabase.contactDAO();

        allContacts = contactDAO.getAllContacts();
    }

    public void insert(Contact contact) {
        new InsertContactAsyncTask(contactDAO).execute(contact);
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    private static class InsertContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDAO contactDAO;

        public InsertContactAsyncTask(ContactDAO contactDAO) {
            this.contactDAO = contactDAO;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDAO.insert(contacts[0]);
            return null;
        }
    }
}