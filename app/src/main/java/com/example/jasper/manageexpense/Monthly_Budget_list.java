package com.example.jasper.manageexpense;

/**
 * Created by woodw on 10/26/2017.
 */

//Shelanskey US12 : Class to hold monthly budget expenses by category

public class Monthly_Budget_list {
    private String category;

    private double amount_spent;
    private double budget_amount;

    public Monthly_Budget_list (String category,  double amount_spent, double budget_amount) {
        this.category = category;

        this.amount_spent = amount_spent;
        this.budget_amount = budget_amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String id) {
        this.category = category;
    }



    public double getAmountSpent() {
        return amount_spent;
    }

    public void setAmountSpent(double amount_spent) {
        this.amount_spent = amount_spent;
    }

    public double getBudgetAmount() {
        return budget_amount;
    }

    public void setBudgetAmount(double budget_amount) {
        this.budget_amount = budget_amount;
    }
}
