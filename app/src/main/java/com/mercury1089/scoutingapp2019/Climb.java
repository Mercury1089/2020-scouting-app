package com.mercury1089.scoutingapp2019;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.zxing.WriterException;
import com.mercury1089.scoutingapp2019.utils.GenUtils;

import java.io.Serializable;
import java.util.HashMap;
import static com.mercury1089.scoutingapp2019.utils.GenUtils.getAColor;
import static com.mercury1089.scoutingapp2019.utils.QRStringBuilder.MakeQRString;

public class Climb extends Fragment {
    //bootstrap buttons
    private BootstrapButton onHABButton;
    private BootstrapButton offHABButton;

    //navigation buttons
    BootstrapButton setupButton;
    BootstrapButton sandstormButton;
    BootstrapButton teleopButton;
    BootstrapButton climbButton;

    private Button generateQRButton;

    //boolean variables
    private boolean isOnHAB = false;
    private boolean isOffHAB = false;

    private HashMap<String, String> setupHashMap;
    private HashMap<String, String> scoreHashMap;

    private ProgressDialog progressDialog;

    public String QRValue = "";


    Activity context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_climb, container, false);
    }

    public void onStart(){
        super.onStart();

        setupButton = context.findViewById(R.id.ClimbSetupButton);
        sandstormButton = context.findViewById(R.id.ClimbSandstormButton);
        teleopButton = context.findViewById(R.id.ClimbTeleopButton);
        climbButton = context.findViewById(R.id.ClimbClimbButton);

        setupButton.setEnabled(false);
        sandstormButton.setEnabled(false);
        teleopButton.setEnabled(false);

        onHABButton = context.findViewById(R.id.OnHabButton);
        offHABButton = context.findViewById(R.id.OffHabButton);

        generateQRButton = context.findViewById(R.id.ClimbGenerateQRButton);

        defaultButtonState(setupButton);
        defaultButtonState(sandstormButton);
        defaultButtonState(teleopButton);
        selectedButtonColors(climbButton);

        defaultButtonState(onHABButton);
        defaultButtonState(offHABButton);

        generateQRButton.setEnabled(true);

        generateQRButton.setBackgroundColor(getAColor(context, R.color.light));
        generateQRButton.setTextColor(getAColor(context, R.color.grey));

        Serializable setupData = context.getIntent().getSerializableExtra("setupHashMap");
        setupHashMap = (HashMap<String, String>) setupData;
        if (setupHashMap.get("FellOver") != null && Integer.parseInt(setupHashMap.get("FellOver")) == 1) {
            disableAllButtons();
            generateQR('E');
        } else {
            disableAllButtons();
            yesOrNOButtons('E');
            generateQR('D');
        }


        Serializable scoreData = context.getIntent().getSerializableExtra("scoreHashMap");
        scoreHashMap = (HashMap<String, String>) scoreData;

        setupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("setupHashMap", setupHashMap);
                intent.putExtra("setupHashMap", setupHashMap);
                startActivity(intent);
            }
        });

        sandstormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Sandstorm.class);
                intent.putExtra("setupHashMap", setupHashMap);
                intent.putExtra("scoreHashMap", scoreHashMap);
                startActivity(intent);
            }
        });

        teleopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Teleop.class);
                intent.putExtra("setupHashMap", setupHashMap);
                intent.putExtra("scoreHashMap", scoreHashMap);
                startActivity(intent);
            }
        });

        onHABButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOnHAB = !isOnHAB;
                if (!isOnHAB) {
                    defaultButtonState(onHABButton);
                    yesOrNOButtons('E');
                    isOnHAB = false;
                } else {
                    defaultButtonState(offHABButton);
                    selectedButtonColors(onHABButton);
                    isOnHAB = true;
                    isOffHAB = false;
                }
                generateQR('E');
            }
        });

        offHABButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOffHAB = !isOffHAB;
                if (!isOffHAB) {
                    defaultButtonState(onHABButton);
                    isOffHAB = false;
                    yesOrNOButtons('E');
                } else {
                    defaultButtonState(onHABButton);
                    selectedButtonColors(offHABButton);
                    isOnHAB = false;
                    isOffHAB = true;
                }
                generateQR('D');
            }
        });

        generateQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(context, R.style.LoadingDialogStyle);
                progressDialog.setMessage("Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                QRRunnable qrRunnable = new QRRunnable();
                new Thread(qrRunnable).start();
            }
        });
    }

    //call methods
    private void defaultButtonState (BootstrapButton button) {
        button.setBackgroundColor(getAColor(context, R.color.light));
        button.setTextColor(getAColor(context, R.color.grey));
    }
    public void selectedButtonColors(BootstrapButton button) {
        button.setBackgroundColor(getAColor(context, R.color.orange));
        button.setTextColor(getAColor(context, R.color.light));
    }
    public void disabledButtonColors(BootstrapButton button) {
        button.setBackgroundColor(getAColor(context, R.color.savedefault));
        button.setTextColor(getAColor(context, R.color.textdefault));
    }
    public void activeButtonColors(Button button) {
        button.setBackgroundColor(getAColor(context, R.color.saveactive));
        button.setTextColor(getAColor(context, R.color.light));
    }

    public void disableAllButtons() {
        onHABButton.setEnabled(false);
        offHABButton.setEnabled(false);

        disabledButtonColors(onHABButton);
        disabledButtonColors(offHABButton);
    }

    public void yesOrNOButtons(char enabledOrDisabled) {
        if (enabledOrDisabled == 'E') {
            onHABButton.setEnabled(true);
            offHABButton.setEnabled(true);
            defaultButtonState(onHABButton);
            defaultButtonState(offHABButton);
        } else {
            onHABButton.setEnabled(false);
            offHABButton.setEnabled(false);
            disabledButtonColors(onHABButton);
            disabledButtonColors(offHABButton);

        }
        isOnHAB = false;
        isOffHAB = false;
    }

    public void generateQR (char enabledOrDisabled) {
        if (enabledOrDisabled == 'E') {
            generateQRButton.setEnabled(true);
            activeButtonColors(generateQRButton);
        } else {
            generateQRButton.setEnabled(false);
            generateQRButton.setBackgroundColor(getAColor(context, R.color.savedefault));
            generateQRButton.setTextColor(getAColor(context, R.color.textdefault));
        }
    }






    class QRRunnable implements Runnable {
        @Override
        public void run() {

            try {
                QRValue = MakeQRString(setupHashMap, scoreHashMap);
                Log.d("QRString",QRValue);

                final Bitmap bitmap = GenUtils.TextToImageEncode(getContext(), QRValue);
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final AlertDialog.Builder qrDialog = new AlertDialog.Builder(context);

                        View view1 = getLayoutInflater().inflate(R.layout.qr_popup, null);
                        ImageView imageView = view1.findViewById(R.id.imageView);
                        Switch checkSwitch = view1.findViewById(R.id.checkSwitch);
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

                        checkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                   if (isChecked) {
                                    goBackToMain.setEnabled(true);
                                    goBackToMain.setBackgroundColor(getAColor(context, (R.color.blue)));
                                    goBackToMain.setTextColor(getAColor(context, R.color.light));
                                }

                            }

                        });
                        goBackToMain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                                Intent intent = new Intent (context, MainActivity.class);
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