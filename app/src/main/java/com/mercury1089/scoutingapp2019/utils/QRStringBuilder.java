package com.mercury1089.scoutingapp2019.utils;

import java.util.HashMap;

public class QRStringBuilder {
    private String ScouterName;
    private String TeamNumber;
    private String matchNumberInput;
    private String firstAlliancePartner;
    private String secondAlliancePartner;
    private String allianceColor;

    public QRStringBuilder() {
        String ScouterName = "";
        String TeamNumber = "";
        String matchNumberInput = "";
        String firstAlliancePartner = "";
        String secondAlliancePartner = "";
        String allianceColor = "";
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
        //QRString.append(setupHashMap.get("StartingPosition")).append(",");
        QRString.append(setupHashMap.get("HABLine")).append(",");
        //QRString.append(setupHashMap.get("StartingGameObject")).append(",");
        //QRString.append(setupHashMap.get("ClimbLevel")).append(",");
        //QRString.append(setupHashMap.get("ClimbPartners")).append(",");
        //QRString.append(setupHashMap.get("SelfOrWithHelp")).append(",");
        QRString.append(setupHashMap.get("FellOver")).append(",");
        QRString.append(setupHashMap.get("NoShow")).append(",");
        //QRString.append(setupHashMap.get("TeleopPrepop"));

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
}
