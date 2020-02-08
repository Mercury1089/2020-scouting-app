package com.mercury1089.scoutingapp2019;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //assigning variables to their equivalent screen elements
        Button localStorageResetButton = findViewById(R.id.LocalStorageResetButton);
        Button backButton = findViewById(R.id.BackButton);
        Button clearQRCache = findViewById(R.id.ClearQRCodesButton);
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
                clearQRCache.callOnClick();
                Toast.makeText(SettingsActivity.this, "All variables successfully reset.", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, PregameActivity.class);
                startActivity(intent);
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
                HashMapManager.outputQRList(new String[0], SettingsActivity.this);
                qrList = HashMapManager.setupQRList(SettingsActivity.this);
                listAdapter = new ListAdapter(SettingsActivity.this, qrList);
                qrCodeSelector.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
                clearQRCache.setEnabled(false);
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
}