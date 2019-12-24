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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.io.Serializable;
import java.util.HashMap;
import static com.mercury1089.scoutingapp2019.utils.GenUtils.getAColor;
import static com.mercury1089.scoutingapp2019.utils.QRStringBuilder.MakeQRString;

public class Climb extends Fragment {
    //bootstrap buttons
    private BootstrapButton OnHABButton;
    private BootstrapButton OffHABButton;

    //navigation buttons
    BootstrapButton SetupButton;
    BootstrapButton SandstormButton;
    BootstrapButton TeleopButton;
    BootstrapButton ClimbButton;

    private Button GenerateQRButton;

    //boolean variables
    private boolean isOnHAB = false;
    private boolean isOffHAB = false;
    
    private HashMap<String, String> setupHashMap;
    private HashMap<String, String> scoreHashMap;

    private ProgressDialog progressDialog;

    public final static int QRCodeSize = 500;

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

        SetupButton = context.findViewById(R.id.ClimbSetupButton);
        SandstormButton = context.findViewById(R.id.ClimbSandstormButton);
        TeleopButton = context.findViewById(R.id.ClimbTeleopButton);
        ClimbButton = context.findViewById(R.id.ClimbClimbButton);

        SetupButton.setEnabled(false);
        SandstormButton.setEnabled(false);
        TeleopButton.setEnabled(false);

        OnHABButton = context.findViewById(R.id.OnHabButton);
        OffHABButton = context.findViewById(R.id.OffHabButton);

        GenerateQRButton = context.findViewById(R.id.ClimbGenerateQRButton);

        defaultButtonState(SetupButton);
        defaultButtonState(SandstormButton);
        defaultButtonState(TeleopButton);
        selectedButtonColors(ClimbButton);

        defaultButtonState(OnHABButton);
        defaultButtonState(OffHABButton);

        GenerateQRButton.setEnabled(true);

        GenerateQRButton.setBackgroundColor(getAColor(context, R.color.light));
        GenerateQRButton.setTextColor(getAColor(context, R.color.grey));

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

        SetupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("setupHashMap", setupHashMap);
                intent.putExtra("setupHashMap", setupHashMap);
                startActivity(intent);
            }
        });

        SandstormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Sandstorm.class);
                intent.putExtra("setupHashMap", setupHashMap);
                intent.putExtra("scoreHashMap", scoreHashMap);
                startActivity(intent);
            }
        });

        TeleopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Teleop.class);
                intent.putExtra("setupHashMap", setupHashMap);
                intent.putExtra("scoreHashMap", scoreHashMap);
                startActivity(intent);
            }
        });

        OnHABButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOnHAB = !isOnHAB;
                if (!isOnHAB) {
                    defaultButtonState(OnHABButton);
                    yesOrNOButtons('E');
                    isOnHAB = false;
                } else {
                    defaultButtonState(OffHABButton);
                    selectedButtonColors(OnHABButton);
                    isOnHAB = true;
                    isOffHAB = false;
                }
                generateQR('E');
            }
        });

        OffHABButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOffHAB = !isOffHAB;
                if (!isOffHAB) {
                    defaultButtonState(OnHABButton);
                    isOffHAB = false;
                    yesOrNOButtons('E');
                } else {
                    defaultButtonState(OnHABButton);
                    selectedButtonColors(OffHABButton);
                    isOnHAB = false;
                    isOffHAB = true;
                }
                generateQR('D');
            }
        });

        GenerateQRButton.setOnClickListener(new View.OnClickListener() {
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
                    for (int x = 0; x < bitMatrixWidth; x++)
                        pixels[offset + x] = bitMatrix.get(x, y) ?
                                getAColor(context, R.color.colorPrimaryDark) : getAColor(context, R.color.bootstrap_dropdown_divider);
                }
                Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
                bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
                return bitmap;
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
        } else {
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
        } else {
            GenerateQRButton.setEnabled(false);
            GenerateQRButton.setBackgroundColor(getAColor(context, R.color.savedefault));
            GenerateQRButton.setTextColor(getAColor(context, R.color.textdefault));
        }
    }






    class QRRunnable implements Runnable {
        @Override
        public void run() {

            try {
                QRValue = MakeQRString(setupHashMap, scoreHashMap);
                Log.d("QRString",QRValue);

                final Bitmap bitmap = TextToImageEncode(QRValue);
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

                        dialog.show();

                        CheckSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

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
