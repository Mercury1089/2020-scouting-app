package com.mercury1089.scoutingapp2019.utils;

import java.util.ArrayList;

public class LocationGroupList {
    protected static ArrayList<LocationGroup> list = new ArrayList<LocationGroup>();
    protected static LocationGroup find(String name) {
        return list.get(list.indexOf(name));
    }
    protected static void add(LocationGroup loc) {
        list.add(loc);
    }
}
