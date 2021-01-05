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

}
