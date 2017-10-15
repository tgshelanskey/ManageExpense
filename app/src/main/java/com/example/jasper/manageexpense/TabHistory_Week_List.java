package com.example.jasper.manageexpense;

import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by Techsoft-003 on 3/16/2017.
 */

public class TabHistory_Week_List {
    private int id;
    private String name;
    private double amount;
    private Date date;
    private String note;
    private String currency; // pGhale: declaring string currency
    private String payment; //pGhale US11: declaring string payment
    private String location; //pGhale US10: declaring string location

    public TabHistory_Week_List(int id, String name, double amount, Date date, String note, String currency, String payment, String location) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.note = note;
        this.currency = currency; //pGhale: adding currency value in TabHistory_Week_List object
        this.payment = payment; //pGhale US11: adding payment value in TabHistory_Week_List object
        this.location = location; //pGhale US10: adding location value in TabHistory_Week_List object
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

    public String getPayment(){return payment;} // pGhale US11: creating a method getPayment

    public void setPayment(String payment) {this.payment =payment;} //pGhale US11: creating method setPayment

    public String getLocation(){return location;} // pGhale US10: creating a method getLocation

    public void setLocation(String location) {this.location = location;} //pGhale US10: creating method setLocation

}
