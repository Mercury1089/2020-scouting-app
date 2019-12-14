package com.mercury1089.scoutingapp2019;



import android.content.Intent;



import android.os.Bundle;



import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;



import android.widget.Button;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mercury1089.scoutingapp2019.utils.GenUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class Settings extends AppCompatActivity {

    private BootstrapButton leftButton;
    private BootstrapButton rightButton;
    private Button localStorageResetButton;
    private Button saveButton;
    private boolean isLeft;
    private boolean isRight;
    private boolean isLocalStorageClicked;
    private boolean HasBeenCleared;
    private boolean isFirstTime;
    private String leftOrRight;
    private Button CancelButton;
    private Serializable setupData;

    private HashMap<String, String> setupHashMap;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        //setting variables to screen elements for changing their properties
        leftButton = findViewById(R.id.FieldSideLeft);
        rightButton = findViewById(R.id.FieldSideRight);
        localStorageResetButton = findViewById(R.id.LocalStorageResetButton);
        saveButton = findViewById(R.id.SaveButton);
        CancelButton = findViewById(R.id.CancelButton);

        //default values
        isLeft = false;
        isRight = false;
        isLocalStorageClicked = false;
        HasBeenCleared = false;
        isFirstTime = false;
        leftOrRight = "";
        leftDefault();
        rightDefault();
        String mainLeftOrRight = "";

        //create hashmap for data transfer between screens
        setupHashMap = new HashMap<>();
        setupData = getIntent().getSerializableExtra("setupHashMap");

        if (setupData != null) {
            setupHashMap = (HashMap<String, String>) setupData;
            mainLeftOrRight = setupHashMap.get("LeftOrRight");
        }
        else if (getIntent().getStringExtra("mainLeftOrRight") != null) {
            mainLeftOrRight = getIntent().getStringExtra("mainLeftOrRight");
        }
        else {
            CancelButton.setEnabled(false);
            isFirstTime = true;
        }
        if (mainLeftOrRight.equals("Left")) {
            leftSelected();
        } else if (mainLeftOrRight.equals("Right")) {
            rightSelected();
        }
    }

    //lets users click an arrow to go to the next textbox
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

    public void rightClick (View view) {
        if (!isRight) {
            rightSelected();
            if (!isFirstTime)
                CancelButton.setEnabled(true);
        }
        else {
            leftOrRight = "";
            rightDefault();
            disable(saveButton);
            disable(CancelButton);
        }
    }

    public void leftClick (View view) {
        if (!isLeft) {
            leftSelected();
            if (!isFirstTime)
                CancelButton.setEnabled(true);
        } else {
            leftOrRight = "";
            leftDefault();
            disable(saveButton);
            disable(CancelButton);
        }
    }

    public void localStorageResetClick (View view) {
        if (!isLocalStorageClicked) {
            isLocalStorageClicked = true;
            localStorageResetButton.setBackgroundColor(GenUtils.getAColor(Settings.this, R.color.genutils_orange));
            localStorageResetButton.setTextColor(GenUtils.getAColor(Settings.this, R.color.light));
            isFirstTime = true;
            disable(CancelButton);
        } else {
            localStorageResetDefault();
            if (!isFirstTime)
                CancelButton.setEnabled(true);
        }
    }

    private void localStorageResetDefault () {
        isLocalStorageClicked = false;
        localStorageResetButton.setBackgroundColor(GenUtils.getAColor(Settings.this, R.color.light));
        localStorageResetButton.setTextColor(GenUtils.getAColor(Settings.this, R.color.grey));
    }

    private void rightSelected () {
        isRight = true;
        leftOrRight = "Right";
        rightButton.setBackgroundColor(GenUtils.getAColor(Settings.this, R.color.genutils_orange));
        rightButton.setTextColor(GenUtils.getAColor(Settings.this, R.color.light));
        leftDefault();
        localStorageResetDefault();
        saveButton.setEnabled(true);
        saveButton.setBackgroundColor(GenUtils.getAColor(Settings.this, R.color.genutils_green));
        saveButton.setTextColor(GenUtils.getAColor(Settings.this, R.color.light));
    }

    private void leftSelected () {
        isLeft = true;
        leftOrRight = "Left";
        rightDefault();
        leftButton.setBackgroundColor(GenUtils.getAColor(Settings.this, R.color.genutils_orange));
        leftButton.setTextColor(GenUtils.getAColor(Settings.this, R.color.light));
        enable(saveButton);
        localStorageResetDefault();
    }

    private void rightDefault () {
        isRight = false;
        rightButton.setBackgroundColor(GenUtils.getAColor(Settings.this, R.color.light));
        rightButton.setTextColor(GenUtils.getAColor(Settings.this, R.color.grey));
    }

    private void leftDefault () {
        isLeft = false;
        leftButton.setBackgroundColor(GenUtils.getAColor(Settings.this, R.color.light));
        leftButton.setTextColor(GenUtils.getAColor(Settings.this, R.color.grey));
    }

    private void disable (Button button) {
        button.setBackgroundColor(GenUtils.getAColor(Settings.this, (R.color.savedefault)));
        button.setTextColor(GenUtils.getAColor(Settings.this, R.color.savetextdefault));
        button.setEnabled(false);
    }

    private void enable (Button button) {
        button.setBackgroundColor(GenUtils.getAColor(Settings.this, R.color.genutils_green));
        button.setTextColor(GenUtils.getAColor(Settings.this, R.color.light));
        button.setEnabled(true);
    }

    public void saveClick (View view) {
        if (isLocalStorageClicked) {
            //clear everything (including values from other screens)
            leftOrRight = "";
            leftDefault();
            rightDefault();
            localStorageResetDefault();
            disable(saveButton);
            setupHashMap.clear();
            setupHashMap.put("NoShow","0");
            setupHashMap.put("AllianceColor","");
            HasBeenCleared = true;
            Toast.makeText(Settings.this, "All variables successfully reset.", Toast.LENGTH_SHORT).show();
        }
        else {
            //save values and go to the setup screen (MainActivity)
            if (isLeft)
                leftOrRight = "Left";
            else if (isRight)
                leftOrRight = "Right";
            if (setupData == null) {
                setupHashMap.put("NoShow","0");
                setupHashMap.put("AllianceColor","");
            }
            setupHashMap.put("LeftOrRight",leftOrRight);

            Intent intent = new Intent(Settings.this, MainActivity.class);
            intent.putExtra("setupHashMap", setupHashMap);
            Serializable scoreData = getIntent().getSerializableExtra("scoreHashMap");
            HashMap<String, String> scoreHashMap;
            if (scoreData != null && !HasBeenCleared) {
                scoreHashMap = (HashMap<String, String>) scoreData;
                intent.putExtra("scoreHashMap", scoreHashMap);
            }
            startActivity(intent);
        }

    }

    public void cancelClick (View view) {
        //send back whatever values were there before settings was opened
        Intent intent = new Intent(Settings.this,MainActivity.class);
        intent.putExtra("LEFTORRIGHT", getIntent().getStringExtra("mainLeftOrRight"));
        intent.putExtra("setupHashMap", setupHashMap);
        Serializable scoreData = getIntent().getSerializableExtra("scoreHashMap");
        HashMap<String, String> scoreHashMap;
        if (scoreData != null && !HasBeenCleared) {
            scoreHashMap = (HashMap<String, String>) scoreData;
            intent.putExtra("scoreHashMap", scoreHashMap);
        }
        startActivity(intent);
    }
}