package com.mercury1089.scoutingapp2019;

import com.mercury1089.scoutingapp2019.utils.GenUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mercury1089.scoutingapp2019.utils.QRStringBuilder;
import java.io.Serializable;
import java.util.HashMap;

public class MainActivity extends Fragment {
    //variables that should be outputted
    private int isBlueAlliance = 0; //0 or 1
    private int isRedAlliance = 0; //0 or 1

    //variables that store elements of the screen for the output variables
    private EditText scouterNameInput;
    private EditText matchNumberInput;
    private EditText teamNumberInput;
    private EditText firstAlliancePartnerInput;
    private EditText secondAlliancePartnerInput;
    private BootstrapButton blueButton;
    private BootstrapButton redButton;
    private Button settingsButton;

    private Switch noShowSwitch;

    private HashMap<String, String> setupHashMap;

    //for QR code generator
    public final static int QRCodeSize = 500;
    Bitmap bitmap;
    String QRValue;

    //other variables
    Button clearButton;
    Button startButton;

    boolean isQRButton = false;
    String leftOrRight;
    private ProgressDialog progressDialog;

    Activity context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    public void onStart() {
        super.onStart();

        //initializers
        scouterNameInput = context.findViewById(R.id.ScouterNameInput);
        matchNumberInput = context.findViewById(R.id.MatchNumberInput);
        teamNumberInput = context.findViewById(R.id.TeamNumberInput);
        firstAlliancePartnerInput = context.findViewById(R.id.FirstAlliancePartnerInput);
        secondAlliancePartnerInput = context.findViewById(R.id.SecondAlliancePartnerInput);
        blueButton = context.findViewById(R.id.BlueButton);
        redButton = context.findViewById(R.id.RedButton);
        settingsButton = context.findViewById(R.id.SettingsButton);

        setupHashMap = new HashMap<>();
        setupHashMap.put("NoShow", "0");
        setupHashMap.put("LeftOrRight", context.getIntent().getStringExtra("LEFTORRIGHT"));
        setupHashMap.put("AllianceColor", "Neither");
        noShowSwitch = context.findViewById(R.id.NoShowSwitch);
        clearButton = context.findViewById(R.id.ClearButton);
        startButton = context.findViewById(R.id.StartButton);
        settingsButton = context.findViewById(R.id.SettingsButton);

        //setting group buttons to default state
        blueDefault();
        redDefault();

        //setting up hashmap that will be transferred to the next screen with info
        if (context.getIntent().getStringExtra("LEFTORRIGHT") != null)
            leftOrRight = context.getIntent().getStringExtra("LEFTORRIGHT");
        if (setupHashMap.size() == 5) {
            Serializable setupData = context.getIntent().getSerializableExtra("setupHashMap");
            if (setupData != null) {
                setupHashMap = (HashMap<String, String>) setupData;
                if (setupHashMap.size() == 0) {
                    setupHashMap.put("NoShow", "0");
                    setupHashMap.put("LeftOrRight", context.getIntent().getStringExtra("LEFTORRIGHT"));
                    setupHashMap.put("AllianceColor", "");
                }
            }
        }

        startButtonCheck();
        clearButtonCheck();

        if (setupHashMap != null && setupHashMap.get("NoShow").equals("1")) {
            noShowSwitch.setChecked(true);
            startButtonCheck();
            clearButtonCheck();
            setupHashMap.put("NoShow", "1");
            startButton.setText(R.string.GenerateQRCode);
            isQRButton = true;
        } else if (setupHashMap.get("NoShow") != null && setupHashMap.get("NoShow").equals("0"))
            noShowSwitch.setChecked(false);

        //starting listener to check the status of the switch
        noShowSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startButtonCheck();
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
                setupHashMap.put(scouterNameInput.getTag().toString(), scouterNameInput.getText().toString());
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
                setupHashMap.put(matchNumberInput.getTag().toString(), matchNumberInput.getText().toString());
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
                setupHashMap.put(teamNumberInput.getTag().toString(), teamNumberInput.getText().toString());
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
                setupHashMap.put("FirstAlliancePartner", firstAlliancePartnerInput.getText().toString());
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
                setupHashMap.put("SecondAlliancePartner", secondAlliancePartnerInput.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //set listener for QR Code generator
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isQRButton) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog = new ProgressDialog(context, R.style.LoadingDialogStyle);
                            progressDialog.setMessage("Please Wait");
                            progressDialog.setCancelable(false);
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.show();
                        }
                    });
                    QRRunnable qrRunnable = new QRRunnable();
                    new Thread(qrRunnable).start();
                } else {
                    if (setupHashMap.get("LeftOrRight") != null) {
                        Intent intent = new Intent(context, Sandstorm.class);
                        intent.putExtra("setupHashMap", setupHashMap);
                        startActivity(intent);
                    }
                }
            }
        });

        //click methods
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Settings.class);
                intent.putExtra("mainLeftOrRight", leftOrRight);
                intent.putExtra("setupHashMap", setupHashMap);
                startActivity(intent);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder cancelDialog = new AlertDialog.Builder(context);
                View view1 = getLayoutInflater().inflate(R.layout.confirm_popup, null);

                BootstrapButton clearConfirm = view1.findViewById(R.id.GoToClimb);
                BootstrapButton cancelConfirm = view1.findViewById(R.id.cancelconfirm);
                final AlertDialog dialog = cancelDialog.create();

                dialog.setView(view1);
                dialog.show();

                cancelConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        scouterNameInput.setText(setupHashMap.get(scouterNameInput.getTag().toString()));
                        matchNumberInput.setText(setupHashMap.get(matchNumberInput.getTag().toString()));
                        teamNumberInput.setText(setupHashMap.get(teamNumberInput.getTag().toString()));
                        firstAlliancePartnerInput.setText(setupHashMap.get(firstAlliancePartnerInput.getTag().toString()));
                        secondAlliancePartnerInput.setText(setupHashMap.get(secondAlliancePartnerInput.getTag().toString()));

                        //showing saved values from before
                        if (setupHashMap.get("AllianceColor").equals("Blue")) {
                            blueButton.setBackgroundColor(GenUtils.getAColor(context, R.color.blue));
                            blueButton.setTextColor(GenUtils.getAColor(context, R.color.light));
                        } else if (setupHashMap.get("AllianceColor").equals("Red")) {
                            redButton.setBackgroundColor(GenUtils.getAColor(context, R.color.red));
                            redButton.setTextColor(GenUtils.getAColor(context, R.color.light));
                        }

                        if (setupHashMap != null && setupHashMap.get("NoShow").equals("1")) {
                            noShowSwitch.setChecked(true);
                            startButtonCheck();
                            clearButtonCheck();
                            setupHashMap.put("NoShow", "1");
                            startButton.setText(R.string.GenerateQRCode);
                            isQRButton = true;
                        } else if (setupHashMap.get("NoShow") != null && setupHashMap.get("NoShow").equals("0"))
                            noShowSwitch.setChecked(false);
                    }
                });

                clearConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        setupHashMap = QRStringBuilder.defaultSetupHashMap(leftOrRight);
                        scouterNameInput.setText("");
                        matchNumberInput.setText("");
                        teamNumberInput.setText("");
                        firstAlliancePartnerInput.setText("");
                        secondAlliancePartnerInput.setText("");
                        noShowSwitch.setChecked(false);
                        blueDefault();
                        redDefault();
                        clearButton.setEnabled(false);
                    }
                });

                blueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setupHashMap.put("AllianceColor", "Blue");
                        if (isBlueAlliance == 0) {
                            redDefault();
                            blueButton.setBackgroundColor(GenUtils.getAColor(context, R.color.blue));
                            blueButton.setTextColor(GenUtils.getAColor(context, R.color.light));
                            isBlueAlliance = 1;
                            if (!noShowSwitch.isChecked())
                                isQRButton = false;
                        }
                        else {
                            blueDefault();
                            setupHashMap.put("AllianceColor", "Neither");
                        }
                        startButtonCheck();
                        clearButtonCheck();
                    }
                });

                redButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setupHashMap.put("AllianceColor", "Red");
                        if (isRedAlliance == 0) {
                            blueDefault();
                            redButton.setBackgroundColor(GenUtils.getAColor(context, R.color.red));
                            redButton.setTextColor(GenUtils.getAColor(context, R.color.light));
                            isRedAlliance = 1;
                        }
                        else {
                            redDefault();
                            setupHashMap.put("AllianceColor", "Neither");
                        }
                        startButtonCheck();
                        clearButtonCheck();
                    }
                });
            }
        });
    }

    //call methods
    private void startButtonCheck() {
        if (noShowSwitch.isChecked())
            if (scouterNameInput.getText().length() > 0
                    && matchNumberInput.getText().length() > 0
                    && teamNumberInput.getText().length() > 0
                    && (setupHashMap.get("AllianceColor").equals("Blue")
                    || setupHashMap.get("AllianceColor").equals("Red")))
                startButton.setEnabled(true);
            else
                startButton.setEnabled(false);
        else
        if (scouterNameInput.getText().length() > 0
                && matchNumberInput.getText().length() > 0
                && teamNumberInput.getText().length() > 0
                && firstAlliancePartnerInput.getText().length() > 0
                && secondAlliancePartnerInput.getText().length() > 0
                && (setupHashMap.get("AllianceColor").equals("Blue")
                || setupHashMap.get("AllianceColor").equals("Red")) )
            startButton.setEnabled(true);
        else
            startButton.setEnabled(false);
    }

    private void clearButtonCheck() {
        if (scouterNameInput.getText().length() > 0
                || teamNumberInput.getText().length() > 0
                || matchNumberInput.getText().length() > 0
                || firstAlliancePartnerInput.getText().length() > 0
                || secondAlliancePartnerInput.getText().length() > 0
                || (setupHashMap.get("AllianceColor").equals("Blue")
                || setupHashMap.get("AllianceColor").equals("Red"))
                || noShowSwitch.isChecked())
            clearButton.setEnabled(true);
        else
            clearButton.setEnabled(false);
    }

    private void blueDefault () {
        isBlueAlliance = 0;
        blueButton.setBackgroundColor(GenUtils.getAColor(context, R.color.light));
        blueButton.setTextColor(GenUtils.getAColor(context, R.color.grey));
    }

    private void redDefault () {
        isRedAlliance = 0;
        redButton.setBackgroundColor(GenUtils.getAColor(context, R.color.light));
        redButton.setTextColor(GenUtils.getAColor(context, R.color.grey));
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
                        GenUtils.getAColor(context, R.color.colorPrimaryDark) : GenUtils.getAColor(context, R.color.bootstrap_dropdown_divider);
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

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
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final AlertDialog.Builder qrDialog = new AlertDialog.Builder(context);
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
                        goBackToMain.setEnabled(false);
                        goBackToMain.setBackgroundColor(GenUtils.getAColor(context, (R.color.savedefault)));
                        goBackToMain.setTextColor(GenUtils.getAColor(context, R.color.savetextdefault));

                        dialog.show();

                        CheckSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    goBackToMain.setEnabled(true);
                                    goBackToMain.setBackgroundColor(GenUtils.getAColor(context, (R.color.blue)));
                                    goBackToMain.setTextColor(GenUtils.getAColor(context, R.color.light));
                                }
                                else{
                                    goBackToMain.setEnabled(false);
                                    goBackToMain.setBackgroundColor(GenUtils.getAColor(context, (R.color.defaultdisabled)));
                                    goBackToMain.setTextColor(GenUtils.getAColor(context, R.color.textdefault));
                                }
                            }

                        });

                        goBackToMain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                //clear everything except field orientation
                                setupHashMap = QRStringBuilder.defaultSetupHashMap(setupHashMap.get("LeftOrRight"));
                                scouterNameInput.setText("");
                                matchNumberInput.setText("");
                                teamNumberInput.setText("");
                                firstAlliancePartnerInput.setText("");
                                secondAlliancePartnerInput.setText("");
                                noShowSwitch.setChecked(false);
                                blueDefault();
                                redDefault();
                            }
                        });
                    }
                });
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }
}