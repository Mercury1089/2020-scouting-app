package com.mercury1089.scoutingapp2019;

import java.util.LinkedHashMap;

public class HashMapManager {
    private static LinkedHashMap<String, String> settingsHashMap = new LinkedHashMap<>();
    private static LinkedHashMap<String, String> setupHashMap = new LinkedHashMap<>();
    private static LinkedHashMap<String, String> autonHashMap = new LinkedHashMap<>();
    private static LinkedHashMap<String, String> teleopHashMap = new LinkedHashMap<>();
    private static LinkedHashMap<String, String> climbHashMap = new LinkedHashMap<>();

    /**
     *
     * Enum for reference to each HashMap
     *
     */
    public enum HASH{
        SETTINGS, SETUP, AUTON, TELEOP, CLIMB
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
     * Used to get the setttingsHashMap
     * Call when an activity starts and assign to global variable
     *
     */
    public static LinkedHashMap<String, String> getSettingsHashMap(){
        return settingsHashMap;
    }

    /**
    *
    * Used to get the setupHashMap
    * Call when an activity starts and assign to global variable
    *
     */
    public static LinkedHashMap<String, String> getSetupHashMap(){
        return setupHashMap;
    }

    /**
     *
     * Used to get the autonHashMap
     * Call when an activity starts and assign to global variable
     *
     */
    public static LinkedHashMap<String, String> getAutonHashMap(){
        return autonHashMap;
    }

    /**
     *
     * Used to get the teleopHashMap
     * Call when an activity starts and assign to global variable
     *
     */
    public static LinkedHashMap<String, String> getTeleopHashMap(){
        return teleopHashMap;
    }

    /**
     *
     * Used to get the climbHashMap
     * Call when an activity starts and assign to global variable
     *
     */
    public static LinkedHashMap<String, String> getClimbHashMap(){
        return climbHashMap;
    }

    /**
    *
    * Used to set the app wide setupHashMap
    * Call before leaving an activity to update the app wide setupHashMap
    *
     */
    public static void putSetupHashMap(LinkedHashMap<String, String> setupData){
        setupHashMap = setupData;
    }

    /**
     *
     * Used to set the app wide autonHashMap
     * Call before leaving an activity to update the app wide autonHashMap
     *
     */
    public static void putAutonHashMap(LinkedHashMap<String, String> autonData){
        autonHashMap = autonData;
    }

    /**
     *
     * Used to set the app wide teleopHashMap
     * Call before leaving an activity to update the app wide teleopHashMap
     *
     */
    public static void putTeleopHashMap(LinkedHashMap<String, String> teleopData){
        teleopHashMap = teleopData;
    }

    /**
     *
     * Used to set the app wide climbHashMap
     * Call before leaving an activity to update the app wide climbHashMap
     *
     */
    public static void putClimbHashMap(LinkedHashMap<String, String> climbData){
        climbHashMap = climbData;
    }

    /**
    *
    * Used to reset all the setupHashMap values to their default values
    * Fill in default values to prevent null pointer exceptions
    *
     */
    public static void setDefaultValues(HASH map){
        switch(map) {
            case SETTINGS:
                settingsHashMap.put("HashMapName", "Settings");
                settingsHashMap.put("NothingToSeeHere", "0");
            case SETUP:
                setupHashMap.put("HashMapName", "Setup");
                setupHashMap.put("ScouterName", "");
                setupHashMap.put("MatchNumber", "");
                setupHashMap.put("TeamNumber", "");
                setupHashMap.put("NoShow", "0");
                setupHashMap.put("AlliancePartner1", "");
                setupHashMap.put("AlliancePartner2", "");
                setupHashMap.put("AllianceColor", "");
                setupHashMap.put("StartingPosition", "");
                setupHashMap.put("FellOver", "0");
                break;
            case AUTON:
                //include all the items that will be in the autonHashMap
                autonHashMap.put("HashMapName", "Auton");
                autonHashMap.put("NumberPickedUp", "000");
                autonHashMap.put("NumberDropped",  "000");
                autonHashMap.put("UpperPortScored", "000");
                autonHashMap.put("LowerPortScored", "000");
                autonHashMap.put("UpperPortMissed", "000");
                autonHashMap.put("LowerPortMissed", "000");
                autonHashMap.put("CrossedInitiationLine", "0");
                break;
            case TELEOP:
                //include all the items that will be in the teleopHashMap
                teleopHashMap.put("HashMapName", "Teleop");
                teleopHashMap.put("NumberPickedUp", "000");
                teleopHashMap.put("NumberDropped",  "000");
                teleopHashMap.put("UpperPortScored", "000");
                teleopHashMap.put("LowerPortScored", "000");
                teleopHashMap.put("UpperPortMissed", "000");
                teleopHashMap.put("LowerPortMissed", "000");
                teleopHashMap.put("StageTwo", "0");
                teleopHashMap.put("StageThree", "0");
                break;
            case CLIMB:
                //include all the items that will be in the climbHashMap
                climbHashMap.put("HashMapName", "Climb");
                climbHashMap.put("Climbed", "0");
                climbHashMap.put("Leveled", "0");
                climbHashMap.put("Parked", "0");
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
            case SETTINGS:
                if(settingsHashMap == null)
                    settingsHashMap = new LinkedHashMap<>();
                if(settingsHashMap.isEmpty()) {
                    setDefaultValues(HASH.SETTINGS);
                    return true;
                }
                break;
            case SETUP:
                if(setupHashMap == null)
                    setupHashMap = new LinkedHashMap<>();
                if(setupHashMap.isEmpty()) {
                    setDefaultValues(HASH.SETUP);
                    return true;
                }
                break;
            case AUTON:
                if(autonHashMap == null)
                    autonHashMap = new LinkedHashMap<>();
                if(autonHashMap.isEmpty()) {
                    setDefaultValues(HASH.AUTON);
                    return true;
                }
                break;
            case TELEOP:
                if(teleopHashMap == null)
                    teleopHashMap = new LinkedHashMap<>();
                if(teleopHashMap.isEmpty()) {
                    setDefaultValues(HASH.TELEOP);
                    return true;
                }
                break;
            case CLIMB:
                if(climbHashMap == null)
                    climbHashMap = new LinkedHashMap<>();
                if(climbHashMap.isEmpty()) {
                    setDefaultValues(HASH.CLIMB);
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
        String allianceColor = setupHashMap.get("AllianceColor");
        setDefaultValues(HASH.SETUP);
        setDefaultValues(HASH.AUTON);
        setDefaultValues(HASH.TELEOP);
        setDefaultValues(HASH.CLIMB);
        setupHashMap.put("ScouterName", scouterName);
        try {
            setupHashMap.put("MatchNumber", Integer.toString((Integer.parseInt(matchNumber) + 1)));
        } catch(NumberFormatException e){
            setupHashMap.put("MatchNumber", "0");
        }
        setupHashMap.put("AllianceColor", allianceColor);
    }
}
