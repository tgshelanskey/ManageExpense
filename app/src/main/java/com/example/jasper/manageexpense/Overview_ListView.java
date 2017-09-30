package com.example.jasper.manageexpense;

/**
 * Created by Techsoft-003 on 3/15/2017.
 */

public class Overview_ListView {
    private int id;
    private String name;
    private double amount;
    private String date;
    private String note;


    public Overview_ListView (int id, String name, double amount, String date, String note){
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getNote() { return note;}

    public void setNote(String note) { this.note = note; }


}
