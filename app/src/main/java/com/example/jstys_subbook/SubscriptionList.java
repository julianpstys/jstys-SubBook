/* SubscriptionList
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
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Class that controls the ListView screen. Shows options to add new subscription,
 * show details of an existing subscription and displays the total monthly cost
 * of all subscriptions.
 *
 * 3 private instance variables include variables for the ListView which will control
 * and display all existing records, ArrayList<SubscriptionRecord> that is loaded and
 * stored into the ListView using an adapter upon class construction, and a TextView
 * that will show the total monthly cost.
 *
 * getMonthlyCost calculates all monthly subscriptions, and displays them in the
 * totalCostView TextView. transitionViewSublist transitions to the AddSubscription
 * class, and loads the activity_record.xml as the new intent
 *
 * @author Julian Stys
 */
public class SubscriptionList extends SubscriptionParent {

    private ListView subListView;
    private ArrayList<SubscriptionRecord> subList;
    private TextView totalCostView;

    /**
     * Defines the ListView and TextView attribute from the XML, and
     * initializes the array of SubscriptionRecords
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_list);
        subListView = (ListView) findViewById(R.id.listView);
        subList = new ArrayList<SubscriptionRecord>();
        totalCostView = (TextView) findViewById(R.id.monthlyCost);
    }

    /**
     * Loads array of SubscriptionRecords from file, and sets the ListView as
     * this array. The ListView is then made clickable, such that clicking on
     * a single entry would allow the user to edit/delete that entry.
     */
    @Override
    public void onStart() {
        super.onStart();
        subList=loadFromFile();
        getTotalCost();

        ArrayAdapter<SubscriptionRecord> subAdapter = new ArrayAdapter<SubscriptionRecord>(this, R.layout.activity_sub_record, subList);
        subListView.setAdapter(subAdapter);

        // Source: http://www.ezzylearning.com/tutorial/handling-android-listview-onitemclick-event
        subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String item = ((TextView)view).getText().toString();
                Intent intent = new Intent(view.getContext(), Record.class);
                intent.putExtra("intPosition", position);
                startActivity(intent);
            }
        });
    }

    /**
     * Sums the cost of each subscription, then displays the total
     * cost in a textView.
     */
    private void getTotalCost(){
        float totalCost=0;
        for(int i =0;i<subList.size();i++){
            totalCost += subList.get(i).getCost();
        }
        totalCostView.setText("Total Monthly Cost: $"+String.valueOf(String.format("%.2f",totalCost)));
    }

    /**
     * Transitions to the Add Subscription XML.
     *
     * @param view
     */
    public void transitionViewSubList(View view){
        saveInFile(subList);
        Intent intent = new Intent(this, AddSubscription.class);
        startActivity(intent);
    }
}
