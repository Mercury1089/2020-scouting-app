package com.mercury1089.scoutingapp2019;

import com.mercury1089.scoutingapp2019.utils.GenUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mercury1089.scoutingapp2019.utils.QRStringBuilder;

import java.util.LinkedHashMap;

public class PregameActivity extends AppCompatActivity {

    //variables that store elements of the screen for the output variables

    //Buttons
    private ImageButton settingsButton;
    private Button blueButton;
    private Button redButton;
    private Button clearButton;
    private Button startButton;

    //Playing Field map
    private Button playingField;

    //Starting Position Buttons
    private Button LL2;
    private Button LL1;
    private Button LC;
    private Button LR1;
    private Button LR2;

    private Button RL2;
    private Button RL1;
    private Button RC;
    private Button RR1;
    private Button RR2;

    //Text Fields
    private EditText scouterNameInput;
    private EditText matchNumberInput;
    private EditText teamNumberInput;
    private EditText firstAlliancePartnerInput;
    private EditText secondAlliancePartnerInput;

    private TextView startingPositionID;
    private TextView startingPositionDescription;

    //Switches
    private Switch noShowSwitch;

    //HashMaps
    private LinkedHashMap<String, String> settingsHashMap;
    private LinkedHashMap<String, String> setupHashMap;

    private AlertDialog loading_alert;
    private ProgressDialog progressDialog;

    //for QR code generator
    public final static int QRCodeSize = 500;
    Bitmap bitmap;
    //ProgressDialog progressDialog;
    boolean isQRButton = false;

    //others
    private MediaPlayer rooster;
    private ImageView slackCenter;

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
        noShowSwitch = findViewById(R.id.NoShowSwitch);
        clearButton = findViewById(R.id.ClearButton);
        startButton = findViewById(R.id.StartButton);
        settingsButton = findViewById(R.id.SettingsButton);

        startingPositionID = findViewById(R.id.IDStartingPosition);
        startingPositionDescription = findViewById(R.id.IDStartingPositionDirections);
        playingField = findViewById(R.id.PlayingField);
        slackCenter = findViewById(R.id.SlackCenter);

        LL2 = findViewById(R.id.LL2);
        LL1 = findViewById(R.id.LL1);
        LC = findViewById(R.id.LC);
        LR1 = findViewById(R.id.LR1);
        LR2 = findViewById(R.id.LR2);

        RL2 = findViewById(R.id.RL2);
        RL1 = findViewById(R.id.RL1);
        RC = findViewById(R.id.RC);
        RR1 = findViewById(R.id.RR1);
        RR2 = findViewById(R.id.RR2);

        rooster = MediaPlayer.create(PregameActivity.this, R.raw.sound);
        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.SETTINGS);
        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.SETUP);
        settingsHashMap = HashMapManager.getSettingsHashMap();
        setupHashMap = HashMapManager.getSetupHashMap();

        //setting group buttons to default state
        updateXMLObjects(true);

        scouterNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setupHashMap.put("ScouterName", scouterNameInput.getText().toString());
                updateXMLObjects(false);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        matchNumberInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setupHashMap.put("MatchNumber", matchNumberInput.getText().toString());
                updateXMLObjects(false);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        teamNumberInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setupHashMap.put("TeamNumber", teamNumberInput.getText().toString());
                updateXMLObjects(false);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        firstAlliancePartnerInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setupHashMap.put("AlliancePartner1", firstAlliancePartnerInput.getText().toString());
                updateXMLObjects(false);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        secondAlliancePartnerInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setupHashMap.put("AlliancePartner2", secondAlliancePartnerInput.getText().toString());
                updateXMLObjects(false);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        //starting listener to check the status of the switch
        noShowSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setupHashMap.put("NoShow", isChecked ? "1" : "0");
                updateXMLObjects(false);
            }
        });

        //click methods
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMapManager.putSetupHashMap(setupHashMap);
                Intent intent = new Intent(PregameActivity.this, SettingsActivity.class);
                startActivity(intent);

                /*
                final AlertDialog.Builder cancelDialog = new AlertDialog.Builder(PregameActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.settings_password, null);

                TextView passwordField = view1.findViewById(R.id.PasswordField);
                Button confirm = view1.findViewById(R.id.ConfirmButton);
                Button cancel = view1.findViewById(R.id.CancelButton);
                final AlertDialog dialog = cancelDialog.create();

                dialog.setView(view1);
                dialog.show();

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(passwordField.getText().toString().equals("31415925")){
                            HashMapManager.putSetupHashMap(setupHashMap);
                            Intent intent = new Intent(PregameActivity.this, SettingsActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(PregameActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });

                cancel.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }));
                */
            }
        });

        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                setupHashMap.put("AllianceColor", setupHashMap.get("AllianceColor").equals("Blue") ? "" : "Blue");
                updateXMLObjects(false);
            }
        });

        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                setupHashMap.put("AllianceColor", setupHashMap.get("AllianceColor").equals("Red") ? "" : "Red");
                updateXMLObjects(false);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v){
                if(isQRButton) {
                    final AlertDialog.Builder cancelDialog = new AlertDialog.Builder(PregameActivity.this);
                    View view = getLayoutInflater().inflate(R.layout.generate_qrcode_confirm_popup, null);

                    Button generateQRButton = view.findViewById(R.id.GenerateQRButton);
                    Button cancelConfirm = view.findViewById(R.id.CancelConfirm);
                    final AlertDialog dialog = cancelDialog.create();

                    dialog.setView(view);
                    dialog.show();

                    generateQRButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final AlertDialog.Builder loading_dialog = new AlertDialog.Builder(PregameActivity.this);
                            View loading_view = getLayoutInflater().inflate(R.layout.loading_screen, null);
                            loading_alert = loading_dialog.create();
                            loading_alert.setView(loading_view);
                            loading_alert.setCancelable(false);
                            loading_alert.show();

                            HashMapManager.putSetupHashMap(setupHashMap);

                            PregameActivity.QRRunnable qrRunnable = new PregameActivity.QRRunnable();
                            new Thread(qrRunnable).start();
                            dialog.dismiss();
                        }
                    });

                    cancelConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                } else {
                    HashMapManager.putSetupHashMap(setupHashMap);
                    if(scouterNameInput.getText().toString().equals("Mercury") && matchNumberInput.getText().toString().equals("1") && teamNumberInput.getText().toString().equals("0") && firstAlliancePartnerInput.getText().toString().equals("8") && secondAlliancePartnerInput.getText().toString().equals("9")) {
                        settingsHashMap.put("NothingToSeeHere", "1");
                        HashMapManager.setDefaultValues(HashMapManager.HASH.SETUP);
                        setupHashMap = HashMapManager.getSetupHashMap();

                        clearStartingPos();
                        updateXMLObjects(true);
                        return;
                    } else if(scouterNameInput.getText().toString().equals("0x") && matchNumberInput.getText().toString().equals("441") && teamNumberInput.getText().toString().equals("1089") && firstAlliancePartnerInput.getText().toString().equals("1089") && secondAlliancePartnerInput.getText().toString().equals("1089")){
                        settingsHashMap.put("Slack", "1");
                        HashMapManager.setDefaultValues(HashMapManager.HASH.SETUP);
                        setupHashMap = HashMapManager.getSetupHashMap();

                        clearStartingPos();
                        updateXMLObjects(true);
                        return;
                    } else if(settingsHashMap.get("NothingToSeeHere").equals("1")) {
                        rooster.start();
                    }
                    Intent intent = new Intent(PregameActivity.this, MatchActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder cancelDialog = new AlertDialog.Builder(PregameActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.clear_confirm_popup, null);

                Button clearConfirm = view1.findViewById(R.id.ClearConfirm);
                Button cancelConfirm = view1.findViewById(R.id.CancelConfirm);
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
                        updateXMLObjects(true);
                    }
                });
            }
        });
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        updateXMLObjects(true);
    }*/

    //call methods
    /*public void onWindowFocusChanged(boolean hasFocus) {
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
    }*/

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void fieldPosClicked(View view){
        Button button = findViewById(view.getId());
        if(button.getCompoundDrawablesRelative()[2] != null) {
            clearStartingPos();
            setupHashMap.put("StartingPosition", "");
        } else {
            clearStartingPos();
            setupHashMap.put("StartingPosition", (String)button.getTag());
        }
        updateXMLObjects(false);
    }

    @NonNull
    private void setStartingPos(String pos){
        clearStartingPos();

        switch(pos){
            case "LR2":
                LR2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.starting_pos, 0);
                setupHashMap.put("StartingPosition", "LR2");
                break;
            case "LR1":
                LR1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.starting_pos, 0);
                setupHashMap.put("StartingPosition", "LR1");
                break;
            case "LC":
                LC.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.starting_pos, 0);
                setupHashMap.put("StartingPosition", "LC");
                break;
            case "LL1":
                LL1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.starting_pos, 0);
                setupHashMap.put("StartingPosition", "LL1");
                break;
            case "LL2":
                LL2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.starting_pos, 0);
                setupHashMap.put("StartingPosition", "LL2");
                break;
            case "RL2":
                RL2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.starting_pos, 0);
                setupHashMap.put("StartingPosition", "RL2");
                break;
            case "RL1":
                RL1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.starting_pos, 0);
                setupHashMap.put("StartingPosition", "RL1");
                break;
            case "RC":
                RC.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.starting_pos, 0);
                setupHashMap.put("StartingPosition", "RC");
                break;
            case "RR1":
                RR1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.starting_pos, 0);
                setupHashMap.put("StartingPosition", "RR1");
                break;
            case "RR2":
                RR2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.starting_pos, 0);
                setupHashMap.put("StartingPosition", "RR2");
                break;
        }
    }

    private void clearStartingPos(){
        LR2.setCompoundDrawablesRelative(null, null, null, null);
        LR1.setCompoundDrawablesRelative(null, null, null, null);
        LC.setCompoundDrawablesRelative(null, null, null, null);
        LL1.setCompoundDrawablesRelative(null, null, null, null);
        LL2.setCompoundDrawablesRelative(null, null, null, null);

        RL2.setCompoundDrawablesRelative(null, null, null, null);
        RL1.setCompoundDrawablesRelative(null, null, null, null);
        RC.setCompoundDrawablesRelative(null, null, null, null);
        RR1.setCompoundDrawablesRelative(null, null, null, null);
        RR2.setCompoundDrawablesRelative(null, null, null, null);
    }

    private void setStartingPosEnabled(boolean enable){
        LR2.setEnabled(enable);
        LR1.setEnabled(enable);
        LC.setEnabled(enable);
        LL1.setEnabled(enable);
        LL2.setEnabled(enable);

        RL2.setEnabled(enable);
        RL1.setEnabled(enable);
        RC.setEnabled(enable);
        RR1.setEnabled(enable);
        RR2.setEnabled(enable);
    }

    private void startButtonCheck() {
        if(scouterNameInput.getText().length() > 0 &&
                matchNumberInput.getText().length() > 0 &&
                teamNumberInput.getText().length() > 0 &&
                firstAlliancePartnerInput.getText().length() > 0 &&
                secondAlliancePartnerInput.getText().length() > 0 &&
                (blueButton.isSelected() || redButton.isSelected()) &&
                ((setupHashMap.get("NoShow").equals("0") && !setupHashMap.get("StartingPosition").equals("")) || setupHashMap.get("NoShow").equals("1")))
                startButton.setEnabled(true);
        else
            startButton.setEnabled(false);
    }

    private void clearButtonCheck() {
        if(scouterNameInput.getText().length() > 0 ||
                matchNumberInput.getText().length() > 0 ||
                teamNumberInput.getText().length() > 0 ||
                noShowSwitch.isChecked() ||
                firstAlliancePartnerInput.getText().length() > 0 ||
                secondAlliancePartnerInput.getText().length() > 0 ||
                blueButton.isSelected() || redButton.isSelected() ||
                !setupHashMap.get("StartingPosition").equals(""))
            clearButton.setEnabled(true);
        else
            clearButton.setEnabled(false);
    }

    private void updateXMLObjects(boolean updateText){
        if(updateText) {
            scouterNameInput.setText(setupHashMap.get("ScouterName"));
            matchNumberInput.setText(setupHashMap.get("MatchNumber"));
            teamNumberInput.setText(setupHashMap.get("TeamNumber"));
            firstAlliancePartnerInput.setText(setupHashMap.get("AlliancePartner1"));
            secondAlliancePartnerInput.setText(setupHashMap.get("AlliancePartner2"));
        }

        blueButton.setSelected(setupHashMap.get("AllianceColor").equals("Blue"));
        redButton.setSelected(setupHashMap.get("AllianceColor").equals("Red"));

        clearStartingPos();
        if(!setupHashMap.get("StartingPosition").equals(""))
            setStartingPos(setupHashMap.get("StartingPosition"));

        if(settingsHashMap.get("Slack").equals("1"))
            slackCenter.setVisibility(View.VISIBLE);

        if(setupHashMap.get("NoShow").equals("1")) {
            noShowSwitch.setChecked(true);

            startingPositionID.setEnabled(false);
            startingPositionDescription.setEnabled(false);
            clearStartingPos();
            playingField.setEnabled(false);
            setStartingPosEnabled(false);

            startButton.setPadding(185, 0, 185, 0);
            startButton.setText(R.string.GenerateQRCode);
            isQRButton = true;
        } else {
            noShowSwitch.setChecked(false);

            startingPositionID.setEnabled(true);
            startingPositionDescription.setEnabled(true);
            playingField.setEnabled(true);
            setStartingPosEnabled(true);

            startButton.setPadding(234, 0, 234, 0);
            startButton.setText(R.string.Start);
            isQRButton = false;
        }

        startButtonCheck();
        clearButtonCheck();
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
                        GenUtils.getAColor(PregameActivity.this, R.color.black) : GenUtils.getAColor(PregameActivity.this, R.color.white);
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
            HashMapManager.setDefaultValues(HashMapManager.HASH.CLIMB);

            QRStringBuilder.buildQRString();

            try {
                bitmap = TextToImageEncode(QRStringBuilder.getQRString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //PregameActivity.QRCodeGenerator qrCodeGenerator = new PregameActivity.QRCodeGenerator();
                        //qrCodeGenerator.execute();

                        final AlertDialog.Builder qrDialog = new AlertDialog.Builder(PregameActivity.this);
                        View view1 = getLayoutInflater().inflate(R.layout.popup_qr, null);
                        ImageView imageView = view1.findViewById(R.id.imageView);
                        TextView teamNumber = view1.findViewById(R.id.TeamNumberQR);
                        TextView matchNumber = view1.findViewById(R.id.MatchNumberQR);
                        final Button goBackToMain = view1.findViewById(R.id.GoBackButton);
                        imageView.setImageBitmap(bitmap);
                        qrDialog.setView(view1);
                        final AlertDialog dialog = qrDialog.create();
                        dialog.setCancelable(false);

                        //progressDialog.dismiss();
                        teamNumber.setText(setupHashMap.get("TeamNumber"));
                        matchNumber.setText(setupHashMap.get("MatchNumber"));
                        //goBackToMain.setBackgroundColor(GenUtils.getAColor(PregameActivity.this, (R.color.savedefault)));
                        //goBackToMain.setTextColor(GenUtils.getAColor(PregameActivity.this, R.color.savetextdefault));

                        //progressDialog.dismiss();
                        loading_alert.dismiss();

                        dialog.show();

                        goBackToMain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog.Builder cancelDialog = new AlertDialog.Builder(PregameActivity.this);
                                View view = getLayoutInflater().inflate(R.layout.setup_next_match_confirm_popup, null);

                                Button setupNextMatchButton = view.findViewById(R.id.SetupNextMatchButton);
                                Button cancelConfirm = view.findViewById(R.id.CancelConfirm);
                                final AlertDialog popupDialog = cancelDialog.create();

                                popupDialog.setView(view);
                                popupDialog.show();

                                setupNextMatchButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        QRStringBuilder.clearQRString(getApplicationContext());
                                        HashMapManager.setupNextMatch();
                                        setupHashMap = HashMapManager.getSetupHashMap();
                                        updateXMLObjects(true);
                                        dialog.dismiss();
                                        popupDialog.dismiss();
                                    }
                                });

                                cancelConfirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        popupDialog.dismiss();
                                    }
                                });
                            }
                        });
                    }
                });
            } catch (WriterException e){}
        }
    }
}