package com.example.daftar.model;

public class Transaction {
    private String note;
    private String date;
    private String cash;
    private String type;


    public Transaction(String note, String date, String cash, String type) {
        this.note = note;
        this.date = date;
        this.cash = cash;
        this.type = type;
    }

    public Transaction(String date, String cash, String type) {
        this.date = date;
        this.cash = cash;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }
}
