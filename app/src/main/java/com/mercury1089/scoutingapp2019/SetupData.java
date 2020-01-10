package com.mercury1089.scoutingapp2019;

import java.util.HashMap;

public class SetupData {
    private static HashMap<String, String> setupHashMap;

    /**
    *
    * Used to access the setup HashMap from an activity
    *
     */
    public SetupData(){
        // Nothing to see here
    }

    /**
    *
    * Used to get the setupHashMap
    * Call when an activity starts and assign to global variable
    *
     */
    public static HashMap<String, String> getSetupHashMap(){
        return setupHashMap;
    }

    /**
    *
    * Used to set the global setupHashMap
    * Call before leaving an activity to update the global setupHashMap
    *
     */
    public static void putSetupHashMap(HashMap<String, String> setupData){
        setupHashMap = setupData;
    }

    /**
    *
    * Used to reset all the setupHashMap values to their default values
    * Fill in default values to prevent null pointer exceptions
    *
     */
    public static void setDefaultValues(){
        setupHashMap.put("NoShow","0");
        setupHashMap.put("ScouterName","John Doe");
        setupHashMap.put("MatchNumber","00");
        setupHashMap.put("TeamNumber","1089");
        setupHashMap.put("AlliancePartner1","1089");
        setupHashMap.put("AlliancePartner2","1089");
        setupHashMap.put("AllianceColor","Blue");
    }

    /**
    *
    * Checks if the setupHashMap is empty or null
    * if it is null, it instantiates it and calls setDefaultValues()
    * if it is empty, it calls setDefaultValues()
    *
     */
    public static void checkNullOrEmpty(){
        if(setupHashMap == null)
            setupHashMap = new HashMap<>();

        if(setupHashMap.isEmpty())
            setDefaultValues();
    }
}