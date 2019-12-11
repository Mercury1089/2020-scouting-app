package com.mercury1089.scoutingapp2019.utils;

import java.util.HashMap;

public class QRStringBuilder {
    public QRStringBuilder() {
    }

    public static String MakeQRString (HashMap setupHashMap, HashMap scoreHashMap) {
        StringBuilder QRString = new StringBuilder();

        //setupHashMap data
        QRString.append(setupHashMap.get("ScouterName")).append(",");
        QRString.append(setupHashMap.get("MatchNumber")).append(",");
        QRString.append(setupHashMap.get("TeamNumber")).append(",");
        QRString.append(setupHashMap.get("FirstAlliancePartner")).append(",");
        QRString.append(setupHashMap.get("SecondAlliancePartner")).append(",");
        QRString.append(setupHashMap.get("AllianceColor")).append(",");
        QRString.append(setupHashMap.get("LeftOrRight")).append(",");
        QRString.append(setupHashMap.get("HABLine")).append(",");
        QRString.append(setupHashMap.get("FellOver")).append(",");
        QRString.append(setupHashMap.get("NoShow"));


        /* removed vars:
        setupHashMap.get("StartingPosition"))
        setupHashMap.get("StartingGameObject"))
        setupHashMap.get("ClimbLevel"))
        setupHashMap.get("ClimbPartners"))
        setupHashMap.get("SelfOrWithHelp"))
        setupHashMap.get("TeleopPrepop"))
        */

        //scoreHashMap data
        if (scoreHashMap != null) {
            Object[] keySet = scoreHashMap.keySet().toArray();
            for (Object key : keySet) {
                key = "" + key;
                QRString.append(",").append(key).append(",");
                if (scoreHashMap.get(key) != null)
                    QRString.append(scoreHashMap.get(key));
            }
        }
        return QRString.toString();
    }

    public static HashMap<String,String> defaultSetupHashMap(String leftOrRight) {
        HashMap<String,String> setupHashMap = new HashMap<>();
        setupHashMap.put("ScouterName", "");
        setupHashMap.put("MatchNumber", "");
        setupHashMap.put("TeamNumber", "");
        setupHashMap.put("FirstAlliancePartner", "");
        setupHashMap.put("SecondAlliancePartner", "");
        setupHashMap.put("AllianceColor", "");
        setupHashMap.put("LeftOrRight", leftOrRight);
        setupHashMap.put("HABLine", "0");
        setupHashMap.put("FellOver", "0");
        setupHashMap.put("NoShow", "0");

        return setupHashMap;
    }
}
