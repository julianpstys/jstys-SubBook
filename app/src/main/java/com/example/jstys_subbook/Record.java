/* Record
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
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;

/**
 * This class is loaded when an entry of the List is clicked on. Shows
 * options to edit, delete and go back to the list class (SubscriptionList).
 *
 * Private EditText instance variables for name, date, cost and comment of
 * each record are present. Includes a private ArrayList of SubscriptionRecord
 * classes, which are loaded upon class initialization and saved when editing
 * or deleting a record. The 'position' instance variable is passed from the
 * list class, and is the position that was clicked on the ListView.
 *
 * Contains methods for editing a record and deleting a record. The SubscriptionRecord
 * array is then saved to the file once either of these methods are called. If the
 * record is edited, then a Snackbar is shown, implemented in the makeSnackBarEdit method.
 * (see https://developer.android.com/reference/android/support/design/widget/Snackbar.html fore
 * details about the Snackbar)
 *
 * @author Julian Stys
 */
public class Record extends SubscriptionParent {
    private ArrayList<SubscriptionRecord> subList;
    private int position;
    private EditText nameField;
    private EditText dateField;
    private EditText costField;
    private EditText commentField;

    /**
     * Defines the EditText instance variables, then loads the the
     * SubscriptionRecord array from file. The text of the EditTexts
     * are then set to be the name, date, cost and comments from the
     * subscription record that has been clicked from the list.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        nameField = (EditText) findViewById(R.id.subname2);
        dateField = (EditText) findViewById(R.id.subdate2);
        costField = (EditText) findViewById(R.id.subcost2);
        commentField = (EditText) findViewById(R.id.subcomments2);

        subList=loadFromFile();

        // Source: https://stackoverflow.com/questions/4233873/how-do-i-get-extra-data-from-intent-on-android
        Intent intent = getIntent();
        position = intent.getIntExtra("intPosition",0);
        nameField.setText(subList.get(position).getName());
        dateField.setText(subList.get(position).getDate());
        costField.setText(String.valueOf(subList.get(position).getCost()));
        commentField.setText(subList.get(position).getComment());
    }

    /**
     * Edits an existing subscription. Then saves to file to save changes.
     * Calls makeSnackBarEdit to show user that update was successful.
     *
     * @param view
     */
    public void editSubscription(View view){
        String name = nameField.getText().toString();
        String date = dateField.getText().toString();
        float cost = Float.parseFloat(costField.getText().toString());
        String comment = commentField.getText().toString();
        SubscriptionRecord record = new SubscriptionRecord(name, date, cost, comment);
        subList.set(position, record);
        makeSnackBarEdit();
        saveInFile(subList);
    }

    /**
     * Deletes a subscription record, then saves to the file.
     *
     * @param view
     */
    public void deleteSubscription(View view){
        subList.remove(position);
        saveInFile(subList);
        Intent intent = new Intent(this, SubscriptionList.class);
        startActivity(intent);

    }

    /**
     * Makes a Snackbar, showing that a record has been updated.
     *
     */
    private void makeSnackBarEdit() {
        // Source: https://stackoverflow.com/questions/31746300/how-to-show-snackbar-at-top-of-the-screen/31746370
        Snackbar snack = Snackbar.make(findViewById(R.id.activity_record), "Subscription has been updated", Snackbar.LENGTH_LONG);
        snack.show();
    }
}
