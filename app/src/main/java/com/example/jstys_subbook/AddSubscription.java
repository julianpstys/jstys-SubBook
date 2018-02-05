/* AddSubscription
 *
 * Version 1.0
 *
 * Feb 4, 2018
 *
 * Copyright 2018 jstys
 *
 */

package com.example.jstys_subbook;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class designed to add a subscription to the main list. This class
 * is loaded once the 'Add Subscription' button is pressed from the
 * activity_subscription_list.xml view.
 *
 * 4 private EditText variables for subscription name, date, cost and
 * comments are declared. An private array of SubscriptionRecords is also
 * delcared.
 *
 * The 'addSubscriptionView' is the method that adds a record to the existing
 * list. First, a check is done to make sure that the 3 required fields (name,
 * date, cost) are done correctly. The date field, in particular, has extra checks
 * to ensure that it is of the correct format (yyyy-mm-dd). If any checks fail, then
 * the background for these EditTexts flash red for a brief period of time, indicating
 * that they have errors in them. If these tests pass, then a new 'SubscriptionRecord'
 * with is made, and the input from the EditTexts are passed as constructor parameters.
 * 'makeSnackBarView' is called to notify the user that the subscription has been added
 * successfully.
 */
public class AddSubscription extends SubscriptionParent {

    private ArrayList<SubscriptionRecord> subList;

    private EditText subName;
    private EditText subDate;
    private EditText subCost;
    private EditText subComments;

    /**
     * Defines the EditText variables, and loads SubscriptionRecord
     * array from file.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subName = (EditText) findViewById(R.id.subname);
        subDate = (EditText) findViewById(R.id.subdate);
        subCost = (EditText) findViewById(R.id.subcost);
        subComments = (EditText) findViewById(R.id.subcomments);

        subList=loadFromFile();
    }

    /**
     * Transitions XML from activity_main.xml to activity_subscription_list.xml.
     * Called by the 'Show All Subscriptions' button.
     *
     * @param view
     */
    public void transitionViewSubList(View view) {
        Intent intent = new Intent(this, SubscriptionList.class);
        startActivity(intent);
    }

    /**
     * Adds a subscription record to the list. Checks if any texts are empty,
     * and that the date editText is of the correct format. Saves to the file
     * if a record has been succesfully added. Called by the 'Add Subscription'
     * button.
     *
     * @param view
     */
    public void addSubscription(View view) {
        Boolean correctFields = Boolean.TRUE;

        // Source: https://stackoverflow.com/questions/8976134/android-how-to-check-if-textview-is-null-or-not-null
        if (subName.getText().toString().matches("")) {
            subName.setBackgroundColor(Color.RED);
            correctFields = Boolean.FALSE;
            resetBackgroundColor(subName);
        }
        if (subCost.getText().toString().matches("")) {
            subCost.setBackgroundColor(Color.RED);
            correctFields = Boolean.FALSE;
            resetBackgroundColor(subCost);
        }
        if (subDate.getText().toString().matches("")) {
            subDate.setBackgroundColor(Color.RED);
            correctFields = Boolean.FALSE;
            resetBackgroundColor(subDate);
        }
        else if (subDate.getText().length() != 10){
            subDate.setBackgroundColor(Color.RED);
            correctFields = Boolean.FALSE;
            resetBackgroundColor(subDate);
        }
        else if (subDate.getText().charAt(4) != '-' || subDate.getText().charAt(7) != '-') {
            subDate.setBackgroundColor(Color.RED);
            correctFields = Boolean.FALSE;
            resetBackgroundColor(subDate);
        }
        if (correctFields.equals(Boolean.TRUE)) {
            makeSnackBarView();
            String name = subName.getText().toString();
            String date = subDate.getText().toString();
            float cost = Float.parseFloat(subCost.getText().toString());
            String comment = subComments.getText().toString();

            SubscriptionRecord record = new SubscriptionRecord(name, date, cost, comment);
            subList.add(record);
            saveInFile(subList);

        }
    }

    /**
     * Makes a Snackbar after a subscription record has been successfully
     * created. Displays to the user that record has been created
     *
     */
    private void makeSnackBarView() {
        // Source: https://stackoverflow.com/questions/31746300/how-to-show-snackbar-at-top-of-the-screen/31746370
        Snackbar snack = Snackbar.make(findViewById(R.id.activity_main), "Subscription has been added", Snackbar.LENGTH_LONG);
        snack.show();
    }

    /**
     * If any of the required fields are invalid, they flash red to show
     * which fields are incorrect. This method resets the fields back to
     * their original transparent colour
     *
     * @param view
     */
    private void resetBackgroundColor(final View view) {
        // https://stackoverflow.com/questions/3072173/how-to-call-a-method-after-a-delay-in-android
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                view.setBackgroundColor(Color.TRANSPARENT);
            }
        }, 2000);
    }
}

