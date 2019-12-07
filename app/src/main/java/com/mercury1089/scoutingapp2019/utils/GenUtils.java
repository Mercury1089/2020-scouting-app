package com.mercury1089.scoutingapp2019.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

public class GenUtils {
    public static final String VAR = "example constant";

    public static int getAColor(Context c, int id) {
        return ContextCompat.getColor(c, id);
    }


}
