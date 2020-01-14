package com.mercury1089.scoutingapp2019;

import com.mercury1089.scoutingapp2019.utils.GenUtils;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mercury1089.scoutingapp2019.utils.QRStringBuilder;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class PregameActivity extends AppCompatActivity {
    //variables that should be outputted
    //private int isBlueAlliance = 0; //0 or 1
    //private int isRedAlliance = 0; //0 or 1
    private String noShow; //0 or 1

    //variables that store elements of the screen for the output variables
    private EditText scouterNameInput;
    private EditText matchNumberInput;
    private EditText teamNumberInput;
    private EditText firstAlliancePartnerInput;
    private EditText secondAlliancePartnerInput;
    private BootstrapButton blueButton;
    private BootstrapButton redButton;
    private Switch NoShowSwitch;
    private LinkedHashMap<String, String> setupHashMap;

    //for QR code generator
    public final static int QRCodeSize = 500;
    Bitmap bitmap;
    ProgressDialog progressDialog;

    //other variables
    BootstrapButton clearButton;
    BootstrapButton startButton;
    BootstrapButton settingsButton;

    boolean isQRButton = false;
    String leftOrRight;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregame);

        //initializers
        scouterNameInput = findViewById(R.id.ScouterNameInput);
        matchNumberInput = findViewById(R.id.MatchNumberInput);
        teamNumberInput = findViewById(R.id.TeamNumberInput);
        firstAlliancePartnerInput = findViewById(R.id.FirstAlliancePartnerInput);
        secondAlliancePartnerInput = findViewById(R.id.SecondAlliancePartnerInput);
        blueButton = findViewById(R.id.BlueButton);
        redButton = findViewById(R.id.RedButton);
        NoShowSwitch = findViewById(R.id.NoShowSwitch);
        clearButton = findViewById(R.id.ClearButton);
        startButton = findViewById(R.id.StartButton);
        settingsButton = findViewById(R.id.SettingsButton);

        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.SETUP);
        setupHashMap = HashMapManager.getSetupHashMap();

        //setting group buttons to default state
        blueDefault();
        redDefault();

        scouterNameInput.setText(setupHashMap.get("ScouterName"));
        matchNumberInput.setText(setupHashMap.get("MatchNumber"));
        teamNumberInput.setText(setupHashMap.get("TeamNumber"));
        firstAlliancePartnerInput.setText(setupHashMap.get("AlliancePartner1"));
        secondAlliancePartnerInput.setText(setupHashMap.get("AlliancePartner2"));

        startButtonCheck();
        clearButtonCheck();

        if(setupHashMap.get("NoShow").equals("1")){
            NoShowSwitch.setChecked((true));
            startButtonCheck();
            clearButtonCheck();
            startButton.setText(R.string.GenerateQRCode);
            isQRButton = true;
        }else{
            NoShowSwitch.setChecked(false);
            startButtonCheck();
            clearButtonCheck();
        }

        //starting listener to check the status of the switch
        NoShowSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                startButtonCheck();
                clearButtonCheck();
                if (isChecked) {
                    setupHashMap.put("NoShow", "1");
                    startButton.setText(R.string.GenerateQRCode);
                    isQRButton = true;
                } else {
                    setupHashMap.put("NoShow", "0");
                    startButton.setText(R.string.Start);
                    isQRButton = false;
                }
            }
        });

        scouterNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startButtonCheck();
                clearButtonCheck();
                setupHashMap.put("ScouterName", scouterNameInput.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        matchNumberInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startButtonCheck();
                clearButtonCheck();
                setupHashMap.put("MatchNumber", matchNumberInput.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        teamNumberInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startButtonCheck();
                clearButtonCheck();
                setupHashMap.put("TeamNumber", teamNumberInput.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        firstAlliancePartnerInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startButtonCheck();
                clearButtonCheck();
                setupHashMap.put("AlliancePartner1", firstAlliancePartnerInput.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        secondAlliancePartnerInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startButtonCheck();
                clearButtonCheck();
                setupHashMap.put("AlliancePartner2", secondAlliancePartnerInput.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        //click methods
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view){
                if(isQRButton) {
                    progressDialog = new ProgressDialog(PregameActivity.this, R.style.LoadingDialogStyle);
                    progressDialog.setMessage("Please Wait");
                    progressDialog.setCancelable(false);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();

                    HashMapManager.putSetupHashMap(setupHashMap);

                    PregameActivity.QRRunnable qrRunnable = new PregameActivity.QRRunnable();
                    new Thread(qrRunnable).start();
                } else {
                    HashMapManager.putSetupHashMap(setupHashMap);
                    Intent intent = new Intent(PregameActivity.this, MatchActivity.class);
                    startActivity(intent);
                }
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMapManager.putSetupHashMap(setupHashMap);
                Intent intent = new Intent(PregameActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder cancelDialog = new AlertDialog.Builder(PregameActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.clear_confirm_popup, null);

                BootstrapButton clearConfirm = view1.findViewById(R.id.ClearConfirm);
                BootstrapButton cancelConfirm = view1.findViewById(R.id.CancelConfirm);
                final AlertDialog dialog = cancelDialog.create();

                dialog.setView(view1);
                dialog.show();

                cancelConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                clearConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        HashMapManager.setDefaultValues(HashMapManager.HASH.SETUP);
                        setupHashMap = HashMapManager.getSetupHashMap();
                        scouterNameInput.setText("");
                        matchNumberInput.setText("");
                        teamNumberInput.setText("");
                        firstAlliancePartnerInput.setText("");
                        secondAlliancePartnerInput.setText("");
                        NoShowSwitch.setChecked(false);
                        startButtonCheck();
                        clearButtonCheck();
                    }
                });
            }
        });
    }

    //call methods
    private void startButtonCheck() {
        if(scouterNameInput.getText().length() > 0 &&
                matchNumberInput.getText().length() > 0 &&
                teamNumberInput.getText().length() > 0 &&
                firstAlliancePartnerInput.getText().length() > 0 &&
                secondAlliancePartnerInput.getText().length() > 0)
            startButton.setEnabled(true);
        else
            startButton.setEnabled(false);
    }

    private void clearButtonCheck() {
        if(scouterNameInput.getText().length() > 0 ||
                matchNumberInput.getText().length() > 0 ||
                teamNumberInput.getText().length() > 0 ||
                NoShowSwitch.isChecked() ||
                firstAlliancePartnerInput.getText().length() > 0 ||
                secondAlliancePartnerInput.getText().length() > 0)
            clearButton.setEnabled(true);
        else
            clearButton.setEnabled(false);
    }

    private void blueDefault () {
        blueButton.setBackgroundColor(GenUtils.getAColor(PregameActivity.this, R.color.light));
        blueButton.setTextColor(GenUtils.getAColor(PregameActivity.this, R.color.grey));
    }

    private void redDefault () {
        redButton.setBackgroundColor(GenUtils.getAColor(PregameActivity.this, R.color.light));
        redButton.setTextColor(GenUtils.getAColor(PregameActivity.this, R.color.grey));
    }

    //template for implementing a button click for a rectangle for starting position
    /*public void LL1Click (View view) {
        setupHashMap.put("StartingPosition", "LL1");
        startButtonCheck();
        clearButtonCheck();
        makeCirclesInvisible();
        LL1Circle.setVisibility(View.VISIBLE);
    }*/

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
                        GenUtils.getAColor(PregameActivity.this, R.color.colorPrimaryDark) : GenUtils.getAColor(PregameActivity.this, R.color.bootstrap_dropdown_divider);
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    class QRRunnable implements Runnable {
        @Override
        public void run() {
            HashMapManager.setDefaultValues(HashMapManager.HASH.AUTON);
            HashMapManager.setDefaultValues(HashMapManager.HASH.TELEOP);
            HashMapManager.setDefaultValues(HashMapManager.HASH.ENDGAAME);

            QRStringBuilder.appendToQRString(HashMapManager.getSetupHashMap());
            QRStringBuilder.appendToQRString(HashMapManager.getAutonHashMap());
            QRStringBuilder.appendToQRString(HashMapManager.getTeleopHashMap());
            QRStringBuilder.appendToQRString(HashMapManager.getEndgameHashMap());

            try {
                bitmap = TextToImageEncode(QRStringBuilder.getQRString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //PregameActivity.QRCodeGenerator qrCodeGenerator = new PregameActivity.QRCodeGenerator();
                        //qrCodeGenerator.execute();

                        final AlertDialog.Builder qrDialog = new AlertDialog.Builder(PregameActivity.this);
                        View view1 = getLayoutInflater().inflate(R.layout.qr_popup, null);
                        ImageView imageView = view1.findViewById(R.id.imageView);
                        Switch CheckSwitch = view1.findViewById(R.id.checkSwitch);
                        TextView teamNumber = view1.findViewById(R.id.TeamNumberQR);
                        TextView matchNumber = view1.findViewById(R.id.MatchNumberQR);
                        final Button goBackToMain = view1.findViewById(R.id.GoBackButton);
                        imageView.setImageBitmap(bitmap);
                        qrDialog.setView(view1);
                        final AlertDialog dialog = qrDialog.create();

                        //progressDialog.dismiss();
                        teamNumber.setText("Team Number: " + setupHashMap.get("TeamNumber"));
                        matchNumber.setText("Match Number: " + setupHashMap.get("MatchNumber"));
                        goBackToMain.setEnabled(false);
                        goBackToMain.setBackgroundColor(GenUtils.getAColor(PregameActivity.this, (R.color.savedefault)));
                        goBackToMain.setTextColor(GenUtils.getAColor(PregameActivity.this, R.color.savetextdefault));

                        progressDialog.dismiss();

                        dialog.show();

                        CheckSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    goBackToMain.setEnabled(true);
                                    goBackToMain.setBackgroundColor(GenUtils.getAColor(PregameActivity.this, (R.color.blue)));
                                    goBackToMain.setTextColor(GenUtils.getAColor(PregameActivity.this, R.color.light));
                                } else {
                                    goBackToMain.setEnabled(false);
                                    goBackToMain.setBackgroundColor(GenUtils.getAColor(PregameActivity.this, (R.color.defaultdisabled)));
                                    goBackToMain.setTextColor(GenUtils.getAColor(PregameActivity.this, R.color.textdefault));
                                }
                            }
                        });

                        goBackToMain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                //clear everything except field orientation
                                HashMapManager.setupNextMatch();
                                setupHashMap = HashMapManager.getSetupHashMap();
                                scouterNameInput.setText(setupHashMap.get("ScouterName"));
                                matchNumberInput.setText(setupHashMap.get("MatchNumber"));
                                teamNumberInput.setText(setupHashMap.get("TeamNumber"));
                                firstAlliancePartnerInput.setText(setupHashMap.get("AlliancePartner1"));
                                secondAlliancePartnerInput.setText(setupHashMap.get("AlliancePartner2"));
                            }
                        });
                    }
                });
            } catch (WriterException e){}
        }
    }
}