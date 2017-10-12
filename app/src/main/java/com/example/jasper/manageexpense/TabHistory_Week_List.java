package com.example.jasper.manageexpense;

import java.util.Date;

/**
 * Created by Techsoft-003 on 3/16/2017.
 */

public class TabHistory_Week_List {
    private int id;
    private String name;
    private double amount;
    private Date date;
    private String note;
    private String currency; // pGhale

    public TabHistory_Week_List(int id, String name, double amount, Date date, String note, String currency) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.note = note;
        this.currency = currency; //pGhale
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCurrency(){return currency;} // pGhale: creating a method getCurrency

    public void setCurrency(String currency) {this.currency =currency;} //pGhale: creating method setCurrency
}
