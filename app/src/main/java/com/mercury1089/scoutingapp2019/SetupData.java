package com.mercury1089.scoutingapp2019;

import java.util.HashMap;

public class SetupData {
    private static HashMap<String, String> setupHashMap;
    private static HashMap<String, String> autonHashMap;
    private static HashMap<String, String> teleopHashMap;
    private static HashMap<String, String> endgameHashMap;

    /**
     *
     * Enum for reference to each HashMap
     *
     */
    public enum HASH{
        SETUP, AUTON, TELEOP, ENDGAAME
    }

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
    public static HashMap<String, String> getAutonHashMap(){
        return autonHashMap;
    }
    public static HashMap<String, String> getTeleopHashMap(){
        return teleopHashMap;
    }
    public static HashMap<String, String> getEndgameHashMap(){
        return endgameHashMap;
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

    public static void putAutonHashMap(HashMap<String, String> autonData){
        autonHashMap = autonData;
    }

    public static void putTeleopHashMap(HashMap<String, String> teleopData){
        autonHashMap = teleopData;
    }

    public static void putEndgameHashMap(HashMap<String, String> endgameData){
        autonHashMap = endgameData;
    }

    /**
    *
    * Used to reset all the setupHashMap values to their default values
    * Fill in default values to prevent null pointer exceptions
    *
     */
    public static void setDefaultValues(HASH map){
        switch(map) {
            case SETUP:
                setupHashMap.put("NoShow", "0");
                setupHashMap.put("ScouterName", "John Doe");
                setupHashMap.put("MatchNumber", "00");
                setupHashMap.put("TeamNumber", "1089");
                setupHashMap.put("AlliancePartner1", "1089");
                setupHashMap.put("AlliancePartner2", "1089");
                setupHashMap.put("AllianceColor", "Blue");
                break;
            case AUTON:
                //include all the items that will be in the autonHashMap
                break;
            case TELEOP:
                //include all the items that will be in the teleopHashMap
                break;
            case ENDGAAME:
                //include all the items that will be in the endgameHashMap
                break;
        }
    }

    /**
    *
    * Checks if the setupHashMap is empty or null
    * if it is null, it instantiates it and calls setDefaultValues()
    * if it is empty, it calls setDefaultValues()
    *
     */
    public static boolean checkNullOrEmpty(HASH map){
        switch(map){
            case SETUP:
                if(setupHashMap == null)
                    setupHashMap = new HashMap<>();
                if(setupHashMap.isEmpty()) {
                    setDefaultValues(HASH.SETUP);
                    return true;
                }
            case AUTON:
                if(autonHashMap == null)
                    autonHashMap = new HashMap<>();
                if(autonHashMap.isEmpty()) {
                    setDefaultValues(HASH.AUTON);
                    return true;
                }
            case TELEOP:
                if(teleopHashMap == null) {
                    teleopHashMap = new HashMap<>();
                    return true;
                }

                if(teleopHashMap.isEmpty()) {
                    setDefaultValues(HASH.TELEOP);
                    return true;
                }
            case ENDGAAME:
                if(endgameHashMap == null) {
                    endgameHashMap = new HashMap<>();
                    return true;
                }

                if(endgameHashMap.isEmpty()) {
                    setDefaultValues(HASH.ENDGAAME);
                    return true;
                }
        }
        return false;
    }
}