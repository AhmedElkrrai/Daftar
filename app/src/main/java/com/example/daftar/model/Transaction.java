package com.example.daftar.model;

class Transaction {
    private String note;
    private String date;
    private String cash;
    public int totalCash;

    public Transaction(String note, String date, String cash) {
        this.note = note;
        this.date = date;
        this.cash = cash;
    }

    public Transaction(String date, String cash) {
        this.date = date;
        this.cash = cash;
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
