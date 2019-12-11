package com.mercury1089.scoutingapp2019.utils;

import android.content.Context;
import android.widget.TextView;

import com.mercury1089.scoutingapp2019.R;
import com.mercury1089.scoutingapp2019.Sandstorm;

import at.markushi.ui.CircleButton;

public class LocationGroup extends LocationGroupList {
    private Context context;
    private TextView counterText;
    private CircleButton badge;
    private int counter;
    private String name;

    public LocationGroup(String n,Context c, TextView countDisplay, CircleButton circleButton, int count) {
        name = n;
        context = c;
        counterText = countDisplay;
        badge = circleButton;
        counter = count;
        list.put(name, this); //add to list of all locations
    }

    //setters
    public void setCounterText(String text) {
        counterText.setText(text);
    }

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

    //selected/deselected stykes
    public void selectLocation() {
        if (name.charAt(2) == 'P') {
            badge.setColor(GenUtils.YELLOW);
            counterText.setTextColor(GenUtils.getAColor(context, R.color.textdefault));
        }
        else {
            badge.setColor(GenUtils.ORANGE);
            counterText.setTextColor(GenUtils.getAColor(context, R.color.light));
        }
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
