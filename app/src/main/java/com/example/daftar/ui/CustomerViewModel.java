package com.example.daftar.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.daftar.model.Contact;
import com.example.daftar.model.Customer;
import com.example.daftar.model.CustomerRepository;

import java.util.HashSet;
import java.util.List;


public class CustomerViewModel extends AndroidViewModel {

    private CustomerRepository repository;
    private LiveData<List<Customer>> allCustomers;
    public MutableLiveData<String> totalPaymentMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> totalDebtMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<HashSet<String>> customerNames = new MutableLiveData<>();

    public CustomerViewModel(@NonNull Application application) {
        super(application);
        repository = new CustomerRepository(application);
        allCustomers = repository.getAllCustomers();
        customerNames.setValue(new HashSet<>());
    }

    public void insert(Customer customer) {
        repository.insert(customer);
    }

    public void update(Customer customer) {
        repository.update(customer);
    }

    public LiveData<List<Customer>> getAllCustomers() {
        return allCustomers;
    }

    public void updateTotalPayment(int cash) {
        totalPaymentMutableLiveData.setValue(cash + " ج.م.");
    }

    public void updateTotalDebt(int cash) {
        totalDebtMutableLiveData.setValue(cash + " ج.م.");
    }

    public void updateCustomerNames(String name) {
        HashSet<String> set = customerNames.getValue();
        set.add(name);
        customerNames.setValue(set);
    }
}