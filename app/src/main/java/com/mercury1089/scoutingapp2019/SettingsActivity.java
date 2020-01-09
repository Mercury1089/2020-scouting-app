package com.mercury1089.scoutingapp2019;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mercury1089.scoutingapp2019.utils.GenUtils;
import java.io.Serializable;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
public class SettingsActivity extends AppCompatActivity {

    private BootstrapButton leftButton;
    private BootstrapButton rightButton;
    private BootstrapButton localStorageResetButton;
    private BootstrapButton saveButton;
    private boolean isLocalStorageClicked;
    private boolean isFirstTime;
    private String leftOrRight;
    private BootstrapButton cancelButton;

    private HashMap<String, String> setupHashMap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setting variables to screen elements for changing their properties
        leftButton = findViewById(R.id.FieldSideLeft);
        rightButton = findViewById(R.id.FieldSideRight);
        localStorageResetButton = findViewById(R.id.LocalStorageResetButton);
        saveButton = findViewById(R.id.SaveButton);
        cancelButton = findViewById(R.id.CancelButton);

        //default values
        isLocalStorageClicked = false;
        isFirstTime = false;
        leftOrRight = "left";
        leftSelected();

        //create hashmap for data transfer between screens
        Serializable setupData = getIntent().getSerializableExtra("setupHashMap");
        if(setupData != null){
            setupHashMap = (HashMap<String, String>) setupData;
        }
    }

    //lets users click an arrow to go to the next textbox
    /*
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
    }*/

    private void rightSelected(){
        leftOrRight = "right";
        GenUtils.selectedButtonState(this, rightButton);
        GenUtils.defaultButtonState(this, leftButton);
        GenUtils.defaultButtonState(this, localStorageResetButton);
    }

    private void leftSelected(){
        leftOrRight = "left";
        GenUtils.selectedButtonState(this, rightButton);
        GenUtils.defaultButtonState(this, leftButton);
        GenUtils.defaultButtonState(this, localStorageResetButton);
    }

    private void disable (BootstrapButton button) {
        button.setBackgroundColor(GenUtils.getAColor(this, (R.color.savedefault)));
        button.setTextColor(GenUtils.getAColor(this, R.color.savetextdefault));
        button.setEnabled(false);
    }

    private void enable (BootstrapButton button) {
        button.setBackgroundColor(GenUtils.getAColor(this, R.color.genutils_green));
        button.setTextColor(GenUtils.getAColor(this, R.color.light));
        button.setEnabled(true);
    }

    public void rightClick (View view) {
        if (!leftOrRight.equals("right")) {
            rightSelected();
            enable(saveButton);
            if (!isFirstTime)
                cancelButton.setEnabled(true);
        }
    }

    public void leftClick (View view) {
        if (!leftOrRight.equals("left")) {
            leftSelected();
            enable(saveButton);
            if (!isFirstTime)
                cancelButton.setEnabled(true);
        }
    }

    public void localStorageResetClick (View view) {
        if (!isLocalStorageClicked) {
            isLocalStorageClicked = true;
            GenUtils.selectedButtonState(this, localStorageResetButton);
            isFirstTime = true;
            disable(cancelButton);
        } else {
            isLocalStorageClicked = false;
            GenUtils.defaultButtonState(this, localStorageResetButton);
            if (!isFirstTime)
                cancelButton.setEnabled(true);
        }
    }

    public void saveClick (View view) {
        if (isLocalStorageClicked) {
            //clear everything (including values from other screens)
            leftOrRight = "Left";
            leftSelected();
            disable(saveButton);
            setupHashMap.clear();
            setupHashMap.put("NoShow","0");
            setupHashMap.put("AllianceColor","");
            setupHashMap.put("LeftOrRight","left");
            Toast.makeText(this, "All variables successfully reset.", Toast.LENGTH_SHORT).show();
        }
        else {
            //save values and go to the setup screen (MainActivity)
            setupHashMap.put("NoShow","0");
            setupHashMap.put("AllianceColor","");
            setupHashMap.put("LeftOrRight",leftOrRight);
            Intent intent = new Intent(this, PregameActivity.class);
            intent.putExtra("setupHashMap", setupHashMap);
            startActivity(intent);
        }
    }

    public void cancelClick (View view) {
        Serializable setupData = getIntent().getSerializableExtra("setupHashMap");
        if(setupData != null){
            setupHashMap = (HashMap<String, String>) setupData;
        }
        Intent intent = new Intent(this, PregameActivity.class);
        intent.putExtra("setupHashMap", setupHashMap);
        startActivity(intent);
    }
}