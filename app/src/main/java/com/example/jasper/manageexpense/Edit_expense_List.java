package com.example.jasper.manageexpense;

import java.util.Date;

/**
 * Created by Techsoft - 001 on 4/22/2017.
 */

public class Edit_expense_List {
    private int id;
    private String name;
    private double amount;
    private Date date;
    private String note;

    Edit_expense_List(int id, String name, double amount, Date date, String note){
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.note = note;

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

    public double getAmount() { return amount; }

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
}
