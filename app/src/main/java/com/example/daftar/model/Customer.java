package com.example.daftar.model;

public class Customer {
    private String customerName;
    private String money;
    private String details;

    public Customer(String customerName, String money, String details) {
        this.customerName = customerName;
        this.money = money;
        this.details = details;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
