package com.mercury1089.scoutingapp2019;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import java.util.LinkedHashMap;
import androidx.fragment.app.Fragment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mercury1089.scoutingapp2019.utils.GenUtils;
import com.mercury1089.scoutingapp2019.utils.QRStringBuilder;

public class Climb extends Fragment {
    //HashMaps for sending QR data between screens
    private LinkedHashMap<String, String> setupHashMap;
    private LinkedHashMap<String, String> climbHashMap;

    //Buttons
    private Button generateQRButton;

    //Switches
    private Switch climbedSwitch;
    private Switch leveledSwitch;
    private Switch parkedSwitch;

    //TextViews
    private TextView climbedID;
    private TextView leveledID;
    private TextView parkedID;

    //other variables
    private ProgressDialog progressDialog;
    public final static int QRCodeSize = 500;

    public static Climb newInstance() {
        Climb fragment = new Climb();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private MatchActivity context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = (MatchActivity) getActivity();
        return inflater.inflate(R.layout.fragment_climb, container, false);
    }

    public void onStart(){
        super.onStart();

        //linking variables to XML elements on the screen
        climbedID = getView().findViewById(R.id.IDClimbed);
        climbedSwitch = getView().findViewById(R.id.ClimbedSwitch);
        leveledID = getView().findViewById(R.id.IDLeveled);
        leveledSwitch = getView().findViewById(R.id.LeveledSwitch);
        parkedID = getView().findViewById(R.id.IDParked);
        parkedSwitch = getView().findViewById(R.id.ParkedSwitch);

        generateQRButton = getView().findViewById(R.id.GenerateQRButton);

        //set listeners for buttons
        climbedSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    climbHashMap.put("Climbed", "1");
                    parkedSwitch.setEnabled(false);
                    leveledSwitch.setEnabled(true);
                    climbHashMap.put("Parked", "0");
                } else {
                    climbHashMap.put("Climbed", "0");
                    climbHashMap.put("Leveled", "0");
                    parkedSwitch.setEnabled(true);
                    leveledSwitch.setEnabled(false);
                }
                updateXMLObjects();
            }
        });

        leveledSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                climbHashMap.put("Leveled", isChecked ? "1" : "0");
                updateXMLObjects();
            }
        });

        parkedSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    climbHashMap.put("Parked", "1");
                    climbedSwitch.setEnabled(false);
                    leveledSwitch.setEnabled(false);
                    climbHashMap.put("Climbed", "0");
                    climbHashMap.put("Leveled", "0");
                } else {
                    climbHashMap.put("Parked", "0");
                    climbedSwitch.setEnabled(true);
                }
                climbHashMap.put("Parked", isChecked ? "1" : "0");
                updateXMLObjects();
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

                HashMapManager.putSetupHashMap(setupHashMap);
                HashMapManager.putClimbHashMap(climbHashMap);

                Climb.QRRunnable qrRunnable = new Climb.QRRunnable();
                new Thread(qrRunnable).start();
            }
        });
    }

    private void updateXMLObjects(){
        climbedSwitch.setChecked(climbHashMap.get("Climbed") == "1");
        leveledSwitch.setChecked(climbHashMap.get("Leveled") == "1");
        parkedSwitch.setChecked(climbHashMap.get("Parked") == "1");

        if(climbHashMap.get("Climbed").equals("1")) {
            leveledSwitch.setEnabled(true);
            parkedSwitch.setEnabled(false);
        }
        if(climbHashMap.get("Parked").equals("1")) {
            climbedSwitch.setEnabled(false);
            leveledSwitch.setEnabled(false);
        } else if(climbHashMap.get("Climbed").equals("0")) {
            leveledSwitch.setEnabled(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming visible, then...
            if (isVisibleToUser) {
                setupHashMap = HashMapManager.getSetupHashMap();
                climbHashMap = HashMapManager.getClimbHashMap();
                updateXMLObjects();
                //set all objects in the fragment to their values from the HashMaps
            } else {
                HashMapManager.putSetupHashMap(setupHashMap);
                HashMapManager.putClimbHashMap(climbHashMap);
            }
        }
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
                        GenUtils.getAColor(context, R.color.design_default_color_primary_dark) : GenUtils.getAColor(context, R.color.bootstrap_dropdown_divider);
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    class QRRunnable implements Runnable {
        @Override
        public void run() {
            HashMapManager.checkNullOrEmpty(HashMapManager.HASH.AUTON);
            HashMapManager.checkNullOrEmpty(HashMapManager.HASH.TELEOP);
            HashMapManager.checkNullOrEmpty(HashMapManager.HASH.CLIMB);

            QRStringBuilder.appendToQRString(HashMapManager.getSetupHashMap());
            QRStringBuilder.appendToQRString(HashMapManager.getAutonHashMap());
            QRStringBuilder.appendToQRString(HashMapManager.getTeleopHashMap());
            QRStringBuilder.appendToQRString(HashMapManager.getClimbHashMap());

            try {
                Bitmap bitmap = TextToImageEncode(QRStringBuilder.getQRString());
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //PregameActivity.QRCodeGenerator qrCodeGenerator = new PregameActivity.QRCodeGenerator();
                        //qrCodeGenerator.execute();

                        final AlertDialog.Builder qrDialog = new AlertDialog.Builder(context);
                        View view1 = getLayoutInflater().inflate(R.layout.popup_qr, null);
                        ImageView imageView = view1.findViewById(R.id.imageView);
                        TextView teamNumber = view1.findViewById(R.id.TeamNumberQR);
                        TextView matchNumber = view1.findViewById(R.id.MatchNumberQR);
                        final Button goBackToMain = view1.findViewById(R.id.GoBackButton);
                        imageView.setImageBitmap(bitmap);
                        qrDialog.setView(view1);
                        final AlertDialog dialog = qrDialog.create();

                        //progressDialog.dismiss();
                        teamNumber.setText("Team Number: " + setupHashMap.get("TeamNumber"));
                        matchNumber.setText("Match Number: " + setupHashMap.get("MatchNumber"));

                        progressDialog.dismiss();

                        dialog.show();

                        goBackToMain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                HashMapManager.setupNextMatch();
                                Intent intent = new Intent(context, PregameActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                });
            } catch (WriterException e){}
        }
    }
}
