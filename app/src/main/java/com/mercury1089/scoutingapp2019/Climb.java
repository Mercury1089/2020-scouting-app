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
    private TextView endgameID;
    private TextView endgameDirections;
    private TextView climbedID;
    private TextView leveledID;
    private TextView parkedID;

    //other variables
    private ProgressDialog progressDialog;
    private AlertDialog loading_alert;
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
        endgameID = getView().findViewById(R.id.IDEndgame);
        endgameDirections = getView().findViewById(R.id.IDEndgameDirections);

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
                climbHashMap.put("CLP", isChecked ? "C" : "");
                updateXMLObjects();
            }
        });

        leveledSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(climbedSwitch.isChecked())
                    climbHashMap.put("CLP", isChecked ? "L" : "C");
                updateXMLObjects();
            }
        });

        parkedSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    climbHashMap.put("CLP", "P");
                    climbedSwitch.setEnabled(false);
                    climbedID.setEnabled(false);

                    leveledSwitch.setEnabled(false);
                    leveledID.setEnabled(false);
                } else {
                    climbHashMap.put("CLP", "");
                    climbedSwitch.setEnabled(true);
                    climbedID.setEnabled(true);
                }
                climbHashMap.put("Parked", isChecked ? "1" : "0");
                updateXMLObjects();
            }
        });

        generateQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder cancelDialog = new AlertDialog.Builder(context);
                View view = getLayoutInflater().inflate(R.layout.generate_qrcode_confirm_popup, null);

                Button generateQRButton = view.findViewById(R.id.GenerateQRButton);
                Button cancelConfirm = view.findViewById(R.id.CancelConfirm);
                final AlertDialog dialog = cancelDialog.create();

                dialog.setView(view);
                dialog.show();

                generateQRButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final AlertDialog.Builder loading_dialog = new AlertDialog.Builder(context);
                        View loading_view = getLayoutInflater().inflate(R.layout.loading_screen, null);
                        loading_alert = loading_dialog.create();
                        loading_alert.setView(loading_view);
                        loading_alert.setCancelable(false);
                        loading_alert.show();

                        HashMapManager.putSetupHashMap(setupHashMap);
                        HashMapManager.putClimbHashMap(climbHashMap);

                        Climb.QRRunnable qrRunnable = new Climb.QRRunnable();
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
            }
        });
    }

    private void updateXMLObjects(){
        climbedSwitch.setChecked(climbHashMap.get("CLP").equals("C") || climbHashMap.get("CLP").equals("L"));
        leveledSwitch.setChecked(climbHashMap.get("CLP").equals("L"));
        parkedSwitch.setChecked(climbHashMap.get("CLP").equals("P"));

        switch(climbHashMap.get("CLP")){
            case "C":
            case "L":
                leveledSwitch.setEnabled(true);
                leveledID.setEnabled(true);

                parkedSwitch.setEnabled(false);
                parkedID.setEnabled(false);
                break;
            case "P":
                climbedSwitch.setEnabled(false);
                climbedID.setEnabled(false);

                leveledSwitch.setEnabled(false);
                leveledID.setEnabled(false);
                break;
            default:
                climbedSwitch.setEnabled(true);
                climbedID.setEnabled(true);

                leveledSwitch.setEnabled(false);
                leveledID.setEnabled(false);

                parkedSwitch.setEnabled(true);
                parkedID.setEnabled(true);
        }

        if(setupHashMap.get("FellOver").equals("1")){
            endgameID.setEnabled(false);
            endgameDirections.setEnabled(false);

            climbedSwitch.setChecked(false);
            climbedSwitch.setEnabled(false);
            climbedID.setEnabled(false);

            leveledSwitch.setChecked(false);
            leveledSwitch.setEnabled(false);
            leveledID.setEnabled(false);

            parkedSwitch.setChecked(false);
            parkedSwitch.setEnabled(false);
            parkedID.setEnabled(false);

            climbHashMap.put("CLP", "");
        } else {
            endgameID.setEnabled(true);
            endgameDirections.setEnabled(true);
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
                        GenUtils.getAColor(context, R.color.black) : GenUtils.getAColor(context, R.color.white);
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

            QRStringBuilder.buildQRString();

            try {
                Bitmap bitmap = TextToImageEncode(QRStringBuilder.getQRString());
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final AlertDialog.Builder qrDialog = new AlertDialog.Builder(context);
                        View view1 = getLayoutInflater().inflate(R.layout.popup_qr, null);
                        ImageView imageView = view1.findViewById(R.id.imageView);
                        TextView teamNumber = view1.findViewById(R.id.TeamNumberQR);
                        TextView matchNumber = view1.findViewById(R.id.MatchNumberQR);
                        final Button goBackToMain = view1.findViewById(R.id.GoBackButton);
                        Switch scannedSwitch = view1.findViewById(R.id.ScannedSwitch);
                        Switch newScouterSwitch = view1.findViewById(R.id.NewScouterSwitch);
                        imageView.setImageBitmap(bitmap);
                        qrDialog.setView(view1);
                        final AlertDialog dialog = qrDialog.create();
                        dialog.setCancelable(false);

                        //progressDialog.dismiss();
                        teamNumber.setText(setupHashMap.get("TeamNumber"));
                        matchNumber.setText(setupHashMap.get("MatchNumber"));

                        loading_alert.dismiss();

                        dialog.show();

                        goBackToMain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog.Builder cancelDialog = new AlertDialog.Builder(context);
                                View view = getLayoutInflater().inflate(R.layout.setup_next_match_confirm_popup, null);

                                Button setupNextMatchButton = view.findViewById(R.id.SetupNextMatchButton);
                                Button cancelConfirm = view.findViewById(R.id.CancelConfirm);
                                final AlertDialog popupDialog = cancelDialog.create();

                                popupDialog.setView(view);
                                popupDialog.show();

                                setupNextMatchButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        QRStringBuilder.clearQRString(scannedSwitch.isChecked(), context);
                                        HashMapManager.setupNextMatch(newScouterSwitch.isChecked());
                                        Intent intent = new Intent(context, PregameActivity.class);
                                        startActivity(intent);
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
