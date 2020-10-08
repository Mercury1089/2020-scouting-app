package com.mercury1089.scoutingapp2019;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mercury1089.scoutingapp2019.utils.GenUtils;
import com.mercury1089.scoutingapp2019.utils.ListAdapter;
import com.mercury1089.scoutingapp2019.utils.QRStringBuilder;
import java.util.LinkedHashMap;

public class SettingsActivity extends AppCompatActivity {

    private LinkedHashMap settingsHashMap;
    private String[] qrList;
    private ListView qrCodeSelector;
    private ListAdapter listAdapter;
    private Dialog loading_alert;

    //for QR code generator
    public final static int QRCodeSize = 500;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //assigning variables to their equivalent screen elements
        Button localStorageResetButton = findViewById(R.id.LocalStorageResetButton);
        Button backButton = findViewById(R.id.BackButton);
        Button clearQRCache = findViewById(R.id.ClearQRCodesButton);
        Button passwordSettingsButton = findViewById(R.id.PasswordSettingsButton);
        Button getConfigCode = findViewById(R.id.GetConfigCode);
        ImageButton muteButton = findViewById(R.id.MuteButton);

        qrCodeSelector = findViewById(R.id.QRCodeListView);

        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.SETTINGS);
        settingsHashMap = HashMapManager.getSettingsHashMap();
        qrList = HashMapManager.setupQRList(getApplicationContext());

        listAdapter = new ListAdapter(this, qrList);
        addQRCodes();

        if(settingsHashMap.get("NothingToSeeHere").equals("1")) {
            muteButton.setVisibility(View.VISIBLE);
            muteButton.setEnabled(true);
        } else {
            muteButton.setVisibility(View.INVISIBLE);
            muteButton.setEnabled(false);
        }

        try {
            String requiredPassword = HashMapManager.pullSettingsPassword(SettingsActivity.this)[1];
            passwordSettingsButton.setSelected(requiredPassword.equals("Y"));
        } catch (Exception e) {
            passwordSettingsButton.setSelected(false);
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
                Toast.makeText(SettingsActivity.this, "All variables successfully reset.", Toast.LENGTH_SHORT).show();
            }
        });

        passwordSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = SettingsActivity.this;
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.enter_password_popup);

                TextView passwordField = dialog.findViewById(R.id.PasswordField);
                Switch requirePasswordSwitch = dialog.findViewById(R.id.SettingsPasswordSwitch);
                Button doneButton = dialog.findViewById(R.id.DoneButton);
                Button cancelButton = dialog.findViewById(R.id.CancelButton);

                String[] passwordData = HashMapManager.pullSettingsPassword(context);
                try {
                    passwordField.setText(passwordData[0]);
                    requirePasswordSwitch.setChecked(passwordData[1].equals("Y"));
                } catch (Exception e) {}

                passwordField.setHint(settingsHashMap.get("DefaultPassword").toString());

                dialog.show();

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = passwordField.getText().toString(), requiredPassword = requirePasswordSwitch.isChecked() ? "Y" : "N";
                        HashMapManager.saveSettingsPassword(new String[]{password, requiredPassword}, context);
                        dialog.dismiss();
                        HashMapManager.saveSettingsPassword(new String[]{password, requiredPassword}, context);
                        passwordSettingsButton.setSelected(requiredPassword.equals("Y"));
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        getConfigCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading_alert = new Dialog(SettingsActivity.this);
                loading_alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
                loading_alert.setContentView(R.layout.loading_screen);
                loading_alert.setCancelable(false);
                loading_alert.show();

                SettingsActivity.QRRunnable qrRunnable = new SettingsActivity.QRRunnable();
                new Thread(qrRunnable).start();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, PregameActivity.class);
                startActivity(intent);
                finish();
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
                Dialog dialog = new Dialog(SettingsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.clear_qr_cache_confirm);

                Button clearConfirm = dialog.findViewById(R.id.ClearConfirm);
                Button cancelConfirm = dialog.findViewById(R.id.CancelConfirm);

                dialog.show();

                clearConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMapManager.outputQRList(new String[0], SettingsActivity.this);
                        qrList = HashMapManager.setupQRList(SettingsActivity.this);
                        listAdapter = new ListAdapter(SettingsActivity.this, qrList);
                        qrCodeSelector.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
                        clearQRCache.setEnabled(false);
                        dialog.dismiss();
                    }
                });

                cancelConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public void addQRCodes(){
        qrCodeSelector.setAdapter(listAdapter);
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
            bitMatrix = new MultiFormatWriter().encode(Value, BarcodeFormat.DATA_MATRIX.QR_CODE, QRCodeSize, QRCodeSize, null
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

        @Override
        public void run() {
            QRStringBuilder.buildConfigString();
            try {
                Bitmap bitmap = TextToImageEncode(QRStringBuilder.getConfigString());
                SettingsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Dialog dialog = new Dialog(SettingsActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.popup_config_qr);

                        ImageView imageView = dialog.findViewById(R.id.imageView);
                        TextView qrName = dialog.findViewById(R.id.QRName);
                        TextView setupNum = dialog.findViewById(R.id.SetupNum);
                        TextView eventNum = dialog.findViewById(R.id.EventNum);
                        Button closeButton = dialog.findViewById(R.id.CloseButton);
                        imageView.setImageBitmap(bitmap);

                        dialog.setCancelable(false);

                        qrName.setText(settingsHashMap.get("QRName").toString());
                        setupNum.setText(GenUtils.padLeftZeros(settingsHashMap.get("SetupNum").toString(), 2));
                        eventNum.setText(GenUtils.padLeftZeros(settingsHashMap.get("EventNum").toString(), 2));

                        loading_alert.dismiss();

                        dialog.show();

                        closeButton.setOnClickListener(new View.OnClickListener() {
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