package com.example.daftar.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.daftar.model.Contact;
import com.example.daftar.model.Customer;
import com.example.daftar.model.CustomerRepository;

import java.util.List;


public class CustomerViewModel extends AndroidViewModel {

    private CustomerRepository repository;
    private LiveData<List<Customer>> allCustomers;

    public CustomerViewModel(@NonNull Application application) {
        super(application);
        repository = new CustomerRepository(application);
        allCustomers = repository.getAllCustomers();
    }

    public void insert(Customer customer) {
        repository.insert(customer);
    }

    public LiveData<List<Customer>> getAllCustomers() {
        return allCustomers;
    }

}