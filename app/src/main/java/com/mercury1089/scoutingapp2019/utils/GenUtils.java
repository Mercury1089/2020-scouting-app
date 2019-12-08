package com.mercury1089.scoutingapp2019.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

public class GenUtils {
    public static final int YELLOW = Color.rgb(248, 231, 28);
    public static final int LIGHT_YELLOW = Color.rgb(255, 255, 217);
    public static final int ORANGE = Color.rgb(255, 152, 0);
    public static final int LIGHT_ORANGE = Color.rgb(221, 172, 107);
    public static final int RED = Color.rgb(91,24,24);
    public static final int BLACK = Color.rgb(30,30,30);
    public static final int GREEN = Color.rgb(45, 192, 103);

    public static int getAColor(Context c, int id) {
        return ContextCompat.getColor(c, id);
    }


}
