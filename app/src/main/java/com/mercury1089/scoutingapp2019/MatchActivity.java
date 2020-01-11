package com.mercury1089.scoutingapp2019;

import com.google.android.material.tabs.TabLayout;
import com.mercury1089.scoutingapp2019.utils.GenUtils;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mercury1089.scoutingapp2019.utils.QRStringBuilder;
import java.util.HashMap;

public class MatchActivity extends AppCompatActivity {

    //variables that store elements of the screen for the output variables
    private BootstrapButton exitButton;
    private TabLayout tabs;
    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    public HashMap<String, String> setupHashMap;
    public HashMap<String, String> autonHashMap;
    public HashMap<String, String> teleopHashMap;
    public HashMap<String, String> endgameHashMap;

    //for QR code generator
    public final static int QRCodeSize = 500;
    Bitmap bitmap;
    String QRValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        //initializers
        exitButton = findViewById(R.id.ExitButton);

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        SetupData.checkNullOrEmpty(SetupData.HASH.SETUP);
        setupHashMap = SetupData.getSetupHashMap();

        //click methods
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder cancelDialog = new AlertDialog.Builder(MatchActivity.this);
                View view = getLayoutInflater().inflate(R.layout.exit_confirm_popup, null);

                BootstrapButton exitConfirm = view.findViewById(R.id.ExitConfirm);
                BootstrapButton cancelConfirm = view.findViewById(R.id.CancelConfirm);
                final AlertDialog dialog = cancelDialog.create();

                dialog.setView(view);
                dialog.show();

                cancelConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                exitConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        SetupData.setDefaultValues(SetupData.HASH.AUTON);
                        SetupData.setDefaultValues(SetupData.HASH.TELEOP);
                        SetupData.setDefaultValues(SetupData.HASH.ENDGAAME);

                        Intent intent = new Intent(MatchActivity.this, PregameActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    //back button
    @Override
    public void onBackPressed() {
        exitButton.performClick();
    }

    //call methods

    //template for implementing a button click for a rectangle for starting position
    /*public void LL1Click (View view) {
        setupHashMap.put("StartingPosition", "LL1");
        startButtonCheck();
        clearButtonCheck();
        makeCirclesInvisible();
        LL1Circle.setVisibility(View.VISIBLE);
    }*/

    //QR Generation
    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.QR_CODE,
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
                        GenUtils.getAColor(MatchActivity.this, R.color.colorPrimaryDark) : GenUtils.getAColor(MatchActivity.this, R.color.bootstrap_dropdown_divider);
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
    /*
    public void autonClicked(View view){
        setContentView(view);
    }*/

    class QRRunnable implements Runnable {
        @Override
        public void run() {
            /*QRString.append(setupHashMap.get("ScouterName")).append(",");
            QRString.append(setupHashMap.get("MatchNumber")).append(",");
            QRString.append(setupHashMap.get("TeamNumber")).append(",");
            QRString.append(setupHashMap.get("FirstAlliancePartner")).append(",");
            QRString.append(setupHashMap.get("SecondAlliancePartner")).append(",");
            QRString.append(setupHashMap.get("AllianceColor")).append(",");
            QRString.append(setupHashMap.get("LeftOrRight")).append(",");
            QRString.append("").append(",");
            QRString.append("").append(",");
            QRString.append("").append(",");
            QRString.append("").append(",");
            QRString.append("").append(",");
            QRString.append("").append(",");
            QRString.append("").append(",");
            QRString.append(setupHashMap.get("NoShow")).append(",");
            QRString.append("").append(",");*/
            QRValue = QRStringBuilder.MakeQRString(setupHashMap, null);

            try {
                bitmap = TextToImageEncode(QRValue);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final AlertDialog.Builder qrDialog = new AlertDialog.Builder(MatchActivity.this);
                        View view1 = getLayoutInflater().inflate(R.layout.qr_popup, null);
                        ImageView imageView = view1.findViewById(R.id.imageView);
                        imageView.setImageBitmap(bitmap);
                        qrDialog.setView(view1);
                        final AlertDialog dialog = qrDialog.create();

                        dialog.show();
                    }
                });
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }
}