package com.mercury1089.scoutingapp2019;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mercury1089.scoutingapp2019.utils.GenUtils;
import com.mercury1089.scoutingapp2019.utils.QRStringBuilder;

import java.util.LinkedHashMap;

public class SettingsActivity extends AppCompatActivity {

    private LinkedHashMap settingsHashMap;
    private String[] qrList;
    private LinearLayout qrCodeSelector;
    private AlertDialog loading_alert;
    public final static int QRCodeSize = 500;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //assigning variables to their equivalent screen elements
        Button localStorageResetButton = findViewById(R.id.LocalStorageResetButton);
        Button backButton = findViewById(R.id.BackButton);
        Button clearQRCache = findViewById(R.id.ClearQRCodesButton);
        ImageButton muteButton = findViewById(R.id.MuteButton);

        qrCodeSelector = findViewById(R.id.QRCodeSelector);

        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.SETTINGS);
        settingsHashMap = HashMapManager.getSettingsHashMap();
        qrList = HashMapManager.setupQRList(getApplicationContext());

        addQRCodes();

        if(settingsHashMap.get("NothingToSeeHere").equals("1")) {
            muteButton.setVisibility(View.VISIBLE);
            muteButton.setEnabled(true);
        } else {
            muteButton.setVisibility(View.INVISIBLE);
            muteButton.setEnabled(false);
        }

        if(qrList.length > 0)
            clearQRCache.setEnabled(true);
        else
            clearQRCache.setEnabled(false);

        localStorageResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMapManager.setDefaultValues(HashMapManager.HASH.SETTINGS);
                HashMapManager.setDefaultValues(HashMapManager.HASH.SETUP);
                HashMapManager.setDefaultValues(HashMapManager.HASH.AUTON);
                HashMapManager.setDefaultValues(HashMapManager.HASH.TELEOP);
                HashMapManager.setDefaultValues(HashMapManager.HASH.CLIMB);
                clearQRCache.callOnClick();
                Toast.makeText(SettingsActivity.this, "All variables successfully reset.", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, PregameActivity.class);
                startActivity(intent);
            }
        });

        muteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                settingsHashMap.put("NothingToSeeHere", "0");
                Toast.makeText(SettingsActivity.this, "Muted", Toast.LENGTH_SHORT).show();
                muteButton.setVisibility(View.INVISIBLE);
                muteButton.setEnabled(false);
            }
        });

        clearQRCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMapManager.outputQRList(new String[0], SettingsActivity.this);
                qrCodeSelector.removeAllViews();
                clearQRCache.setEnabled(false);
            }
        });
    }

    public void addQRCodes(){
        for(String qrString : qrList){
            Log.d("Stuff", "" + qrList.length);
            String[] qrData = qrString.split(",");
            String teamNumber = qrData[1];
            String matchNumber = qrData[2];
            Button btnTag = new Button(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)getResources().getDimension(R.dimen.button_height));
            layoutParams.setMargins(0,10,0,0);
            btnTag.setLayoutParams(layoutParams);
            btnTag.setBackground(getDrawable(R.drawable.button_states));
            btnTag.setText("Team Number: " + GenUtils.padLeftZeros(teamNumber, 2) + "    Match Number: " + GenUtils.padLeftZeros(matchNumber, 2));
            btnTag.setTextSize(getResources().getDimension(R.dimen.button_text_size));
            btnTag.setTextColor(getResources().getColor(R.color.ice));
            btnTag.setId(Integer.parseInt(teamNumber+matchNumber));
            btnTag.setTag(teamNumber + "~" + matchNumber + "~" + qrString);
            qrCodeSelector.addView(btnTag);
            btnTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder loading_dialog = new AlertDialog.Builder(SettingsActivity.this);
                    View loading_view = getLayoutInflater().inflate(R.layout.loading_screen, null);
                    loading_alert = loading_dialog.create();
                    loading_alert.setView(loading_view);
                    loading_alert.setCancelable(false);
                    loading_alert.show();

                    SettingsActivity.QRRunnable qrRunnable = new SettingsActivity.QRRunnable(v.getTag().toString().split("~"));
                    new Thread(qrRunnable).start();
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        HashMapManager.putSettingsHashMap(settingsHashMap);
    }

    //QR Generation
    private Bitmap TextToImageEncode(String Value) throws WriterException {
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
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ?
                        GenUtils.getAColor(SettingsActivity.this, R.color.black) : GenUtils.getAColor(SettingsActivity.this, R.color.white);
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    class QRRunnable implements Runnable {

        private String teamNum, matchNum, qrString;

        public QRRunnable(String[] qrData){
            teamNum = qrData[0];
            matchNum = qrData[1];
            qrString = qrData[2];
        }

        @Override
        public void run() {
            try {
                Bitmap bitmap = TextToImageEncode(qrString);
                SettingsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final AlertDialog.Builder qrDialog = new AlertDialog.Builder(SettingsActivity.this);
                        View view1 = getLayoutInflater().inflate(R.layout.popup_qr_cached, null);
                        ImageView imageView = view1.findViewById(R.id.imageView);
                        TextView teamNumber = view1.findViewById(R.id.TeamNumberQR);
                        TextView matchNumber = view1.findViewById(R.id.MatchNumberQR);
                        final Button goBackToMain = view1.findViewById(R.id.GoBackButton);
                        imageView.setImageBitmap(bitmap);
                        qrDialog.setView(view1);
                        final AlertDialog dialog = qrDialog.create();
                        dialog.setCancelable(false);

                        teamNumber.setText(GenUtils.padLeftZeros(teamNum, 2));
                        matchNumber.setText(GenUtils.padLeftZeros(matchNum, 2));

                        loading_alert.dismiss();

                        dialog.show();

                        goBackToMain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
            } catch (WriterException e){}
        }
    }
}