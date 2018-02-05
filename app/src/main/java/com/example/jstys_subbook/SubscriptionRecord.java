/* SubscriptionRecord
 *
 * Version 1.0
 *
 * Feb 4, 2018
 *
 * Copyright 2018 jstys
 *
 */

package com.example.jstys_subbook;

/**
 * SubscriptionRecord is a standalone class that contains the data of a single
 * subscription record. An array of this class will make up the list, as seen in
 * SubscriptionList.
 *
 * Variables consist of 4 private instance variables, 'name', 'date', 'monthlyCost'
 * and 'comments', as described in the assignment description
 *
 * Constructor must be called using 4 parameters, which set the values for these
 * 4 instance variables. If 'comments' is left empty, then an empty String is passed
 * as argument. 4 getter methods are also available for the 4 instance variables, and
 * an editRecord method which edits an existing record is given. Finally, toString
 * defines the string format that the records will show in the ListView.
 *
 * @author Julian Stys
 */

public class SubscriptionRecord {

    private String name;
    private String date;
    private float monthlyCost;
    private String comments;

    SubscriptionRecord(String name, String date, float monthlyCost, String comments){
        this.name = name;
        this.date = date;
        this.monthlyCost = monthlyCost;
        this.comments = comments;
    }

    // Getters
    public String getName() {
        return this.name;
    }
    public String getDate() {
        return this.date;
    }
    public float getCost() {
        return this.monthlyCost;
    }
    public String getComment() {
        return this.comments;
    }

    // Setters
    public void editRecord(String name, String date, float monthlyCost, String comments){
        this.name = name;
        this.date = date;
        this.monthlyCost = monthlyCost;
        this.comments = comments;
    }

    /**
     * Defines what will be shown for each record in the list view
     *
     * @return String
     */
    public String toString() {
        return this.name+"\nDate: "+this.date+"\nMonthly Cost: $"+this.monthlyCost;
    }

}
