package com.mercury1089.scoutingapp2019;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.Serializable;
import java.util.HashMap;

import static com.mercury1089.scoutingapp2019.utils.GenUtils.getAColor;
import static com.mercury1089.scoutingapp2019.utils.QRStringBuilder.MakeQRString;

public class Climb extends AppCompatActivity {

    
    //bootstrap buttons
    private BootstrapButton OnHABButton;
    private BootstrapButton OffHABButton;

    private Button GenerateQRButton;

    //boolean variables
    private boolean isOnHAB = false;
    private boolean isOffHAB = false;
    
    private HashMap<String, String> setupHashMap;
    private HashMap<String, String> scoreHashMap;

    private ProgressDialog progressDialog;

    public final static int QRCodeSize = 500;

    public String QRValue = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climb);

        BootstrapButton SetupButton = findViewById(R.id.ClimbSetupButton);
        SetupButton.setEnabled(false);

        BootstrapButton SandstormButton = findViewById(R.id.ClimbSandstormButton);
        SandstormButton.setEnabled(false);

        BootstrapButton TeleopButton = findViewById(R.id.ClimbTeleopButton);
        TeleopButton.setEnabled(false);

        BootstrapButton ClimbButton = findViewById(R.id.ClimbClimbButton);

        OnHABButton = findViewById(R.id.OnHabButton);
        OffHABButton = findViewById(R.id.OffHabButton);

        GenerateQRButton = findViewById(R.id.ClimbGenerateQRButton);



        defaultButtonState(SetupButton);
        defaultButtonState(SandstormButton);
        defaultButtonState(TeleopButton);
        selectedButtonColors(ClimbButton);

        defaultButtonState(OnHABButton);
        defaultButtonState(OffHABButton);

        GenerateQRButton.setEnabled(true);

        GenerateQRButton.setBackgroundColor(getAColor(Climb.this, R.color.light));
        GenerateQRButton.setTextColor(getAColor(Climb.this, R.color.grey));

        Serializable setupData = getIntent().getSerializableExtra("setupHashMap");
        setupHashMap = (HashMap<String, String>) setupData;
        if (setupHashMap.get("FellOver") != null && Integer.parseInt(setupHashMap.get("FellOver")) == 1) {
            disableAllButtons();
            generateQR('E');
        }
        else {
            disableAllButtons();
            yesOrNOButtons('E');
            generateQR('D');
        }


        Serializable scoreData = getIntent().getSerializableExtra("scoreHashMap");
        scoreHashMap = (HashMap<String, String>) scoreData;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    private void defaultButtonState (BootstrapButton button) {
        button.setBackgroundColor(getAColor(Climb.this, R.color.light));
        button.setTextColor(getAColor(Climb.this, R.color.grey));
    }
    public void selectedButtonColors(BootstrapButton button) {
        button.setBackgroundColor(getAColor(Climb.this, R.color.orange));
        button.setTextColor(getAColor(Climb.this, R.color.light));
    }
    public void disabledButtonColors(BootstrapButton button) {
        button.setBackgroundColor(getAColor(Climb.this, R.color.savedefault));
        button.setTextColor(getAColor(Climb.this, R.color.textdefault));
    }
    public void activeButtonColors(Button button) {
        button.setBackgroundColor(getAColor(Climb.this, R.color.saveactive));
        button.setTextColor(getAColor(Climb.this, R.color.light));
    }

    /*public void enableAllButtons() {
        OnHABButton.setEnabled(true);
        OffHABButton.setEnabled(true);
        Level1Button.setEnabled(true);
        Level2Button.setEnabled(true);
        Level3Button.setEnabled(true);
        OnYourOwnButton.setEnabled(true);
        WithHelpButton.setEnabled(true);
        HasLiftedButton.setEnabled(true);
        HasNotLiftedButton.setEnabled(true);
        OnePartnerButton.setEnabled(true);
        TwoPartnerButton.setEnabled(true);

        defaultButtonState(OnHABButton);
        defaultButtonState(OffHABButton);
        defaultButtonState(Level1Button);
        defaultButtonState(Level2Button);
        defaultButtonState(Level3Button);
        defaultButtonState(OnYourOwnButton);
        defaultButtonState(WithHelpButton);
        defaultButtonState(HasLiftedButton);
        defaultButtonState(HasNotLiftedButton);
        defaultButtonState(OnePartnerButton);
        defaultButtonState(TwoPartnerButton);
    }*/

    public void disableAllButtons() {
        OnHABButton.setEnabled(false);
        OffHABButton.setEnabled(false);

        disabledButtonColors(OnHABButton);
        disabledButtonColors(OffHABButton);
    }

    public void yesOrNOButtons(char enabledOrDisabled) {
        if (enabledOrDisabled == 'E') {
            OnHABButton.setEnabled(true);
            OffHABButton.setEnabled(true);
            defaultButtonState(OnHABButton);
            defaultButtonState(OffHABButton);
        }
        else {
            OnHABButton.setEnabled(false);
            OffHABButton.setEnabled(false);
            disabledButtonColors(OnHABButton);
            disabledButtonColors(OffHABButton);

        }
        isOnHAB = false;
        isOffHAB = false;
    }

    public void generateQR (char enabledOrDisabled) {
        if (enabledOrDisabled == 'E') {
            GenerateQRButton.setEnabled(true);
            activeButtonColors(GenerateQRButton);
        }
        else {
            GenerateQRButton.setEnabled(false);
            GenerateQRButton.setBackgroundColor(getAColor(Climb.this, R.color.savedefault));
            GenerateQRButton.setTextColor(getAColor(Climb.this, R.color.textdefault));
        }
    }


    public void SetupClick (View view) {
        Intent intent = new Intent(Climb.this, MainActivity.class);
        intent.putExtra("setupHashMap", setupHashMap);
        intent.putExtra("setupHashMap", setupHashMap);
        startActivity(intent);
    }
    public void SandstormClick (View view) {
        Intent intent = new Intent(Climb.this, Sandstorm.class);
        intent.putExtra("setupHashMap", setupHashMap);
        intent.putExtra("scoreHashMap", scoreHashMap);
        startActivity(intent);
    }
    public void TeleopClick (View view) {
        Intent intent = new Intent(Climb.this, Teleop.class);
        intent.putExtra("setupHashMap", setupHashMap);
        intent.putExtra("scoreHashMap", scoreHashMap);
        startActivity(intent);
    }

    public void OnHABClick (View view) {
        isOnHAB = !isOnHAB;
        if (!isOnHAB) {
            defaultButtonState(OnHABButton);
            yesOrNOButtons('E');
            isOnHAB = false;
        }
        else {
            defaultButtonState(OffHABButton);
            selectedButtonColors(OnHABButton);
            isOnHAB = true;
            isOffHAB = false;
        }
        generateQR('E');
    }

    public void OffHABClick (View view) {
        isOffHAB = !isOffHAB;
        if (!isOffHAB) {
            defaultButtonState(OnHABButton);
            isOffHAB = false;
            yesOrNOButtons('E');
        }
        else {
            defaultButtonState(OnHABButton);
            selectedButtonColors(OffHABButton);
            isOnHAB = false;
            isOffHAB = true;
        }

        generateQR('D');
    }


    public void GenerateQRClick (View view) {
        progressDialog = new ProgressDialog(Climb.this, R.style.LoadingDialogStyle);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        QRRunnable qrRunnable = new QRRunnable();
        new Thread(qrRunnable).start();
    }
    Bitmap TextToImageEncode(String Value) throws WriterException {
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
                        getAColor(Climb.this, R.color.colorPrimaryDark) : getAColor(Climb.this, R.color.bootstrap_dropdown_divider);
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    class QRRunnable implements Runnable {
        @Override
        public void run() {

            try {

                QRValue = MakeQRString(setupHashMap, scoreHashMap);

                Log.d("QRString",QRValue);

                final Bitmap bitmap = TextToImageEncode(QRValue);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final AlertDialog.Builder qrDialog = new AlertDialog.Builder(Climb.this);

                        View view1 = getLayoutInflater().inflate(R.layout.qr_popup, null);
                        ImageView imageView = view1.findViewById(R.id.imageView);
                        Switch CheckSwitch = view1.findViewById(R.id.checkSwitch);
                        TextView teamNumber = view1.findViewById(R.id.TeamNumberQR);
                        TextView matchNumber = view1.findViewById(R.id.MatchNumberQR);
                        final Button goBackToMain = view1.findViewById(R.id.GoBackButton);
                        imageView.setImageBitmap(bitmap);
                        qrDialog.setView(view1);
                        final AlertDialog dialog = qrDialog.create();

                        progressDialog.dismiss();
                        teamNumber.setText("Team Number: " + setupHashMap.get("TeamNumber"));
                        matchNumber.setText("Match Number: " + setupHashMap.get("MatchNumber"));

                        dialog.show();

                        CheckSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    goBackToMain.setEnabled(true);
                                    goBackToMain.setBackgroundColor(getAColor(Climb.this, (R.color.blue)));
                                    goBackToMain.setTextColor(getAColor(Climb.this, R.color.light));
                                }

                            }

                        });
                        goBackToMain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                                Intent intent = new Intent (Climb.this, MainActivity.class);
                                intent.putExtra("LEFTORRIGHT",setupHashMap.get("LeftOrRight"));
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
            catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }
}
