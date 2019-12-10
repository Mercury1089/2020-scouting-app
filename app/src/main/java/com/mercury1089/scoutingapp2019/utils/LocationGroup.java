package com.mercury1089.scoutingapp2019.utils;

import android.widget.TextView;

import at.markushi.ui.CircleButton;

public class LocationGroup extends LocationGroupList {
    private TextView counterText;
    private CircleButton badge;
    private int counter;

    public LocationGroup(TextView countDisplay, CircleButton circleButton, int count) {
        counterText = countDisplay;
        badge = circleButton;
        counter = count;
        add(this); //add to list of all locations
    }

    //setters
    //increment counters
    public void increaseCounter() {
        counter += 1;
    }
    public void decreaseCounter() {
        counter -= 1;
    }

    //enable/disable buttons
    public void enableLocation() {
        counterText.setEnabled(true);
        badge.setEnabled(true);
    }
    public void disableLocation() {
        counterText.setEnabled(true);
        badge.setEnabled(true);
    }



    //getters
    public TextView getCounterText() {
        return counterText;
    }

    public CircleButton getBadge() {
        return badge;
    }

    public int getCounter() {
        return counter;
    }
}
