package com.mercury1089.scoutingapp2019.utils;

import java.util.HashMap;

public class LocationGroupList {
    protected static HashMap<String, LocationGroup> list = new HashMap<String, LocationGroup>();
    public static HashMap getLocations(String panelOrCargo) {
        HashMap<String, LocationGroup> locations = new HashMap<String, LocationGroup>();
        if (panelOrCargo.equals("Panel")) {
            for (String key : list.keySet()) {
                if (key.charAt(2) == 'P')
                    locations.put(key, list.get(key));
            }
        } else if (panelOrCargo.equals("Cargo")) {
            for (String key : list.keySet()) {
                if (key.charAt(2) == 'P')
                    locations.put(key, list.get(key));
            }
        }
        return locations;
    }
}
