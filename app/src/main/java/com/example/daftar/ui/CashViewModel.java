package com.example.daftar.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CashViewModel extends ViewModel {
    public MutableLiveData<String> cashMutableLiveData = new MutableLiveData<>();

    public void getCash(String digit) {
        if (cashMutableLiveData.getValue() == null)
            cashMutableLiveData.setValue("");
        cashMutableLiveData.setValue(cashMutableLiveData.getValue() + digit);
    }

    public void editCash() {
        if (cashMutableLiveData.getValue() != null && !cashMutableLiveData.getValue().equals("")) {
            String cash = cashMutableLiveData.getValue();
            cashMutableLiveData.setValue(cash.substring(0, cash.length() - 1));
        }
    }
}