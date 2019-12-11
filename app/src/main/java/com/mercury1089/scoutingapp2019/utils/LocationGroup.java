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
        counterText.setText(counter);
    }
    public void decreaseCounter() {
        counter -= 1;
        counterText.setText(counter);
    }

    //enable/disable buttons
    public void enableLocation() {
        counterText.setEnabled(true);
        badge.setEnabled(true);
        if (name.charAt(2) == 'P') {
            if (counter > 0) {
                badge.setColor(GenUtils.YELLOW);
                counterText.setTextColor(GenUtils.getAColor(context, R.color.textdefault));
            }
            else {
                badge.setColor(GenUtils.LIGHT_YELLOW);
                counterText.setTextColor(GenUtils.getAColor(context, R.color.textdefault));
            }
        } else if (name.charAt(2) == 'C') {
            if (counter > 0) {
                badge.setColor(GenUtils.ORANGE);
                counterText.setTextColor(GenUtils.getAColor(context, R.color.light));
            }
            else {
                badge.setColor(GenUtils.LIGHT_ORANGE);
                counterText.setTextColor(GenUtils.getAColor(context, R.color.textdefault));
            }
        }
    }
    public void disableLocation() {
        counterText.setEnabled(false);
        badge.setEnabled(false);
        if (name.charAt(2) == 'P') {
            if (counter > 0) {
                badge.setColor(GenUtils.YELLOW);
                counterText.setTextColor(GenUtils.getAColor(context, R.color.textdefault));
            }
            else {
                badge.setColor(GenUtils.BLACK);
                counterText.setTextColor(GenUtils.getAColor(context, R.color.light));
            }
        } else if (name.charAt(2) == 'C') {
            if (counter > 0) {
                badge.setColor(GenUtils.GREEN);
                counterText.setTextColor(GenUtils.getAColor(context, R.color.light));
            }
            else {
                badge.setColor(GenUtils.BLACK);
                counterText.setTextColor(GenUtils.getAColor(context, R.color.light));
            }
        }
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
    public String getName() {
        return name;
    }

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