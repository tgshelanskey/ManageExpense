package com.example.jasper.manageexpense;

/**
 * Created by Techsoft - 001 on 4/12/2017.
 */

public class Tab1_ListView {
    private int id;
    private String name;
    private int budget;

    public Tab1_ListView (int id, String name, int budget){
        this.id = id;
        this.name = name;
        this.budget = budget;
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

    public int getBudget() {return budget;}

    public void setBudget(int budget) {this.budget = budget;}
}
