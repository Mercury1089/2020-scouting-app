package com.mercury1089.scoutingapp2019.utils;

import com.mercury1089.scoutingapp2019.HashMapManager;

import java.util.LinkedHashMap;

public class QRStringBuilder {

    private static StringBuilder QRString = new StringBuilder();

    public static void buildQRString(){
        LinkedHashMap setup = HashMapManager.getSetupHashMap();
        LinkedHashMap auton = HashMapManager.getAutonHashMap();
        LinkedHashMap teleop = HashMapManager.getTeleopHashMap();
        LinkedHashMap climb = HashMapManager.getClimbHashMap();

        //Setup Data
        QRString.append(setup.get("ScouterName")).append(",");
        QRString.append(setup.get("MatchNumber")).append(",");
        QRString.append(setup.get("TeamNumber")).append(",");
        QRString.append(setup.get("AlliancePartner1")).append(",");
        QRString.append(setup.get("AlliancePartner2")).append(",");
        QRString.append(setup.get("AllianceColor")).append(",");
        QRString.append(setup.get("NoShow").equals("1") ? "y" : "n").append(",");
        QRString.append(setup.get("StartingPosition")).append(",");
        QRString.append(auton.get("CrossedInitiationLine")).append(",");
        QRString.append(teleop.get("StageTwo")).append(",");
        QRString.append(teleop.get("StageThree")).append(",");
        //Climbed, Leveled, or Parked
        if(climb.get("Leveled").equals("1"))
            QRString.append("L").append(",");
        else if(climb.get("Climbed").equals("1"))
            QRString.append("C").append(",");
        else if(climb.get("Parked").equals("1"))
            QRString.append("P").append(",");
        else
            QRString.append(" ").append(",");
        QRString.append(setup.get("FellOver")).append(",");

        //Event Data
        //Auton
        QRString.append("Auton").append(",");
        QRString.append(auton.get("OuterPortScored")).append(",");
        QRString.append(auton.get("InnerPortScored")).append(",");
        QRString.append(auton.get("LowerPortScored")).append(",");
        QRString.append(auton.get("UpperPortMissed")).append(",");
        QRString.append(auton.get("LowerPortMissed")).append(",");
        QRString.append(auton.get("Dropped")).append(",");
        //Teleop
        QRString.append("Teleop").append(",");
        QRString.append(teleop.get("OuterPortScored")).append(",");
        QRString.append(teleop.get("InnerPortScored")).append(",");
        QRString.append(teleop.get("LowerPortScored")).append(",");
        QRString.append(teleop.get("UpperPortMissed")).append(",");
        QRString.append(teleop.get("LowerPortMissed")).append(",");
        QRString.append(teleop.get("Dropped")).append(",");
    }

    public static String getQRString(){
        return QRString.toString();
    }

    public static void clearQRString() {QRString = new StringBuilder(); }
}
