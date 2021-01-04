package com.example.daftar.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customer_table")
public class Customer {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String customerName;
    private String totalCash;
    private String details;

    public Customer(int id, String customerName, String totalCash, String details) {
        this.id = id;
        this.customerName = customerName;
        this.totalCash = totalCash;
        this.details = details;
    }

    public Customer(String customerName, String money, String details) {
        this.customerName = customerName;
        this.totalCash = money;
        this.details = details;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(String totalCash) {
        this.totalCash = totalCash;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
