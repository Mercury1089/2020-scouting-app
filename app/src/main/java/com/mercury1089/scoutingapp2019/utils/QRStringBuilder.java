package com.mercury1089.scoutingapp2019.utils;

import java.util.LinkedHashMap;

public class QRStringBuilder {

    private static StringBuilder QRString = new StringBuilder();

    public static void appendToQRString(LinkedHashMap hm){
        Object[] keySet = hm.keySet().toArray();
        for (Object key : keySet) {
            key = "" + key;
            QRString.append(key).append(":").append(hm.get(key)).append(",");
        }
    }

    public static String getQRString(){
        return QRString.toString();
    }

    public static void clearQRString() {QRString = new StringBuilder(); }
}
