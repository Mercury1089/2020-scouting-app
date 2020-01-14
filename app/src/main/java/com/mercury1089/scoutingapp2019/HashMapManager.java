package com.mercury1089.scoutingapp2019;

import java.util.HashMap;

public class HashMapManager {
    private static HashMap<String, String> setupHashMap = new HashMap<>();
    private static HashMap<String, String> autonHashMap = new HashMap<>();
    private static HashMap<String, String> teleopHashMap = new HashMap<>();
    private static HashMap<String, String> endgameHashMap = new HashMap<>();

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
    public HashMapManager(){
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
     * Used to get the autonHashMap
     * Call when an activity starts and assign to global variable
     *
     */
    public static HashMap<String, String> getAutonHashMap(){
        return autonHashMap;
    }

    /**
     *
     * Used to get the teleopHashMap
     * Call when an activity starts and assign to global variable
     *
     */
    public static HashMap<String, String> getTeleopHashMap(){
        return teleopHashMap;
    }

    /**
     *
     * Used to get the endgameHashMap
     * Call when an activity starts and assign to global variable
     *
     */
    public static HashMap<String, String> getEndgameHashMap(){
        return endgameHashMap;
    }

    /**
    *
    * Used to set the app wide setupHashMap
    * Call before leaving an activity to update the app wide setupHashMap
    *
     */
    public static void putSetupHashMap(HashMap<String, String> setupData){
        setupHashMap = setupData;
    }

    /**
     *
     * Used to set the app wide autonHashMap
     * Call before leaving an activity to update the app wide autonHashMap
     *
     */
    public static void putAutonHashMap(HashMap<String, String> autonData){
        autonHashMap = autonData;
    }

    /**
     *
     * Used to set the app wide teleopHashMap
     * Call before leaving an activity to update the app wide teleopHashMap
     *
     */
    public static void putTeleopHashMap(HashMap<String, String> teleopData){
        teleopHashMap = teleopData;
    }

    /**
     *
     * Used to set the app wide endgameHashMap
     * Call before leaving an activity to update the app wide endgameHashMap
     *
     */
    public static void putEndgameHashMap(HashMap<String, String> endgameData){
        endgameHashMap = endgameData;
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
                setupHashMap.put("ScouterName", "");
                setupHashMap.put("MatchNumber", "");
                setupHashMap.put("TeamNumber", "");
                setupHashMap.put("AlliancePartner1", "");
                setupHashMap.put("AlliancePartner2", "");
                setupHashMap.put("AllianceColor", "Blue");
                break;
            case AUTON:
                //include all the items that will be in the autonHashMap
                autonHashMap.put("Default", "0");
                break;
            case TELEOP:
                //include all the items that will be in the teleopHashMap
                teleopHashMap.put("Default", "0");
                break;
            case ENDGAAME:
                //include all the items that will be in the endgameHashMap
                endgameHashMap.put("Default", "0");
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
                if(teleopHashMap == null)
                    teleopHashMap = new HashMap<>();
                if(teleopHashMap.isEmpty()) {
                    setDefaultValues(HASH.TELEOP);
                    return true;
                }
            case ENDGAAME:
                if(endgameHashMap == null)
                    endgameHashMap = new HashMap<>();
                if(endgameHashMap.isEmpty()) {
                    setDefaultValues(HASH.ENDGAAME);
                    return true;
                }
        }
        return false;
    }

    /**
     *
     * resets all of the values of all of the HashMaps except for scouterName
     * It also increments the match number
     *
     */

    public static void setupNextMatch(){
        String scouterName = setupHashMap.get("ScouterName");
        String matchNumber = setupHashMap.get("MatchNumber");
        setDefaultValues(HASH.SETUP);
        setDefaultValues(HASH.AUTON);
        setDefaultValues(HASH.TELEOP);
        setDefaultValues(HASH.ENDGAAME);
        setupHashMap.put("ScouterName", scouterName);
        setupHashMap.put("MatchNumber", Integer.toString((Integer.parseInt(matchNumber) + 1)));
    }
}
