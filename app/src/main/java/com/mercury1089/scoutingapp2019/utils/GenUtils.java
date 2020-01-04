package com.mercury1089.scoutingapp2019.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import androidx.core.content.ContextCompat;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mercury1089.scoutingapp2019.R;

import java.util.HashMap;

public class GenUtils {
    public final static int QRCodeSize = 500;

    public static int getAColor(Context c, int id) {
        return ContextCompat.getColor(c, id);
    }

    public static void defaultButtonState (Context context, BootstrapButton button) {
        button.setBackgroundColor(GenUtils.getAColor(context, R.color.light));
        button.setTextColor(GenUtils.getAColor(context, R.color.grey));
        button.setRounded(true);
    }
    public static void selectedButtonState(Context context, BootstrapButton button) {
        button.setBackgroundColor(GenUtils.getAColor(context, R.color.orange));
        button.setTextColor(GenUtils.getAColor(context, R.color.light));
        button.setRounded(true);
    }

    public static void disableScoringDiagram (char c) {
        switch (c) {
            case 'A':
            case 'P':
                HashMap<String, LocationGroup> panelList = LocationGroupList.getLocations("Panel");
                for (String key : panelList.keySet()) {
                    LocationGroup lg = LocationGroupList.list.get(key);
                    lg.disableLocation();
                    LocationGroupList.list.replace(key, lg);
                }
                if (c == 'P')
                    break;
            case 'C':
                HashMap<String, LocationGroup>  cargoList = LocationGroupList.getLocations("Cargo");
                for (String key : cargoList.keySet()) {
                    LocationGroup lg = LocationGroupList.list.get(key);
                    lg.disableLocation();
                    LocationGroupList.list.replace(key, lg);
                }
        }
    }

    public static void enableScoringDiagram (char c) {
        switch (c) {
            case 'A':
            case 'P':
                HashMap<String, LocationGroup>  panelList = LocationGroupList.getLocations("Panel");
                for (String key : panelList.keySet()) {
                    LocationGroup lg = LocationGroupList.list.get(key);
                    lg.enableLocation();
                    LocationGroupList.list.replace(key, lg);
                }
                if (c == 'P')
                    break;
            case 'C':
                HashMap<String, LocationGroup>  cargoList = LocationGroupList.getLocations("Cargo");
                for (String key : cargoList.keySet()) {
                    LocationGroup lg = LocationGroupList.list.get(key);
                    lg.enableLocation();
                    LocationGroupList.list.replace(key, lg);
                }
        }
    }

    public static Bitmap TextToImageEncode(String Value, Context context) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRCodeSize, QRCodeSize, null
            );
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++)
                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getAColor(context, R.color.colorPrimaryDark) : getAColor(context, R.color.bootstrap_dropdown_divider);
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
