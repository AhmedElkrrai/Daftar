package com.example.daftar.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.daftar.data.CustomerDAO;

import java.util.List;

public class CustomerRepository {

    private CustomerDAO customerDAO;
    private LiveData<List<Customer>> allCustomers;

    public CustomerRepository(Application application) {

        CustomerDatabase customerDatabase = CustomerDatabase.getInstance(application);

        customerDAO = customerDatabase.customerDAO();

        allCustomers = customerDAO.getAllCustomers();
    }

    public void insert(Customer customer) {
        new InsertCustomerAsyncTask(customerDAO).execute(customer);
    }

    public void update(Customer customer) {
        new UpdateCustomerAsyncTask(customerDAO).execute(customer);
    }

    public LiveData<List<Customer>> getAllCustomers() {
        return allCustomers;
    }

    private static class InsertCustomerAsyncTask extends AsyncTask<Customer, Void, Void> {
        private CustomerDAO customerDAO;

        public InsertCustomerAsyncTask(CustomerDAO customerDAO) {
            this.customerDAO = customerDAO;
        }

        @Override
        protected Void doInBackground(Customer... customers) {
            customerDAO.insert(customers[0]);
            return null;
        }
    }

    private static class UpdateCustomerAsyncTask extends AsyncTask<Customer, Void, Void> {
        private CustomerDAO customerDAO;

        public UpdateCustomerAsyncTask(CustomerDAO customerDAO) {
            this.customerDAO = customerDAO;
        }

        @Override
        protected Void doInBackground(Customer... customers) {
            customerDAO.update(customers[0]);
            return null;
        }
    }
}