package com.mercury1089.scoutingapp2019.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mercury1089.scoutingapp2019.R;

import java.util.HashMap;

public class GenUtils {

    public static int getAColor(Context c, int id) {
        return ContextCompat.getColor(c, id);
    }

    public static void defaultButtonState (Context context, BootstrapButton button) {
        button.setBackgroundColor(GenUtils.getAColor(context, R.color.light));
        button.setTextColor(GenUtils.getAColor(context, R.color.grey));
        button.setRounded(true);
    }
    public static void selectedButtonState(Context context, BootstrapButton button) {
        button.setBackgroundColor(GenUtils.getAColor(context, R.color.orange));
        button.setTextColor(GenUtils.getAColor(context, R.color.light));
        button.setRounded(true);
    }

    public static void disableScoringDiagram (char c) {
        switch (c) {
            case 'A':
            case 'P':
                HashMap<String, LocationGroup> panelList = LocationGroupList.getLocations("Panel");
                for (String key : panelList.keySet()) {
                    LocationGroup lg = LocationGroupList.list.get(key);
                    lg.disableLocation();
                    LocationGroupList.list.replace(key, lg);
                }
                if (c == 'P')
                    break;
            case 'C':
                HashMap<String, LocationGroup>  cargoList = LocationGroupList.getLocations("Cargo");
                for (String key : cargoList.keySet()) {
                    LocationGroup lg = LocationGroupList.list.get(key);
                    lg.disableLocation();
                    LocationGroupList.list.replace(key, lg);
                }
        }
    }

    public static void enableScoringDiagram (char c) {
        switch (c) {
            case 'A':
            case 'P':
                HashMap<String, LocationGroup>  panelList = LocationGroupList.getLocations("Panel");
                for (String key : panelList.keySet()) {
                    LocationGroup lg = LocationGroupList.list.get(key);
                    lg.enableLocation();
                    LocationGroupList.list.replace(key, lg);
                }
                if (c == 'P')
                    break;
            case 'C':
                HashMap<String, LocationGroup>  cargoList = LocationGroupList.getLocations("Cargo");
                for (String key : cargoList.keySet()) {
                    LocationGroup lg = LocationGroupList.list.get(key);
                    lg.enableLocation();
                    LocationGroupList.list.replace(key, lg);
                }
        }
    }


}
