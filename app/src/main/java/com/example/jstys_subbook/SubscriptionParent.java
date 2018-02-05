/* SubscriptionParent
 *
 * Version 1.0
 *
 * Feb 4, 2018
 *
 * Copyright 2018 jstys
 *
 */

package com.example.jstys_subbook;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * This class is the parent class for AddSubscription, SubscriptionList
 * and Record classes. Consists of a static String filename, used as the
 * name of the file that is loaded and saved upon entering and exiting
 * the app.
 *
 * Consists of 2 methods: loadFromFile converts the saved file to an array
 * of SubscriptionRecord classes. saveInFile saves the class to file format.
 * Both of these methods use the Gson library for class conversion
 *
 * @author Julian Stys
 */

public class SubscriptionParent extends AppCompatActivity {

    protected static final String FILENAME = "old_subscriptions.sav";

    /**
     * Using Gson, loads an array of SubscriptionRecord classes from file
     * names FILENAME. Returns the array once loaded
     * Source: lonelyTwitter lab demo
     *
     * @return ArrayList<SubscriptionRecord>
     */
    protected ArrayList<SubscriptionRecord> loadFromFile() {
        ArrayList<SubscriptionRecord> subList;
        try {
            FileInputStream fis = openFileInput(FILENAME);

            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // From https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            Type listType = new TypeToken<ArrayList<SubscriptionRecord>>() {
            }.getType();
            subList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            subList = new ArrayList<SubscriptionRecord>();
            e.printStackTrace();
        }
        return subList;
    }

    /**
     * Takes as input an array of SubscriptionRecord classes, then saves to file
     * FILENAME. Uses Gson to convert from array of classes to file format
     *
     * @param ArrayList<SubscriptionRecord>subList
     */
    protected void saveInFile(ArrayList<SubscriptionRecord> subList) { // String text, Date date
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(subList, out);
            out.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }

    }
}
