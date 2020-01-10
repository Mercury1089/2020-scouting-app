package com.mercury1089.scoutingapp2019;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mercury1089.scoutingapp2019.utils.GenUtils;
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
        setContentView(R.layout.activity_settings);

        //assigning variables to their equivalent screen elements
        leftButton = findViewById(R.id.FieldSideLeft);
        rightButton = findViewById(R.id.FieldSideRight);
        localStorageResetButton = findViewById(R.id.LocalStorageResetButton);
        saveButton = findViewById(R.id.SaveButton);
        cancelButton = findViewById(R.id.CancelButton);

        //get global setupHashMap
        SetupData.checkNullOrEmpty(SetupData.HASH.SETUP);
        setupHashMap = SetupData.getSetupHashMap();
    }

    @Override
    protected void onStart() {
        super.onStart();
        leftSelected();
        GenUtils.disabledButtonState(this, saveButton);
        cancelButton.setEnabled(true);
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
            GenUtils.disabledButtonState(this, cancelButton);
        } else {
            isLocalStorageClicked = false;
            GenUtils.defaultButtonState(this, localStorageResetButton);
            if (!isFirstTime)
                cancelButton.setEnabled(true);
        }
    }

    public void saveClick (View view) {
        if (isLocalStorageClicked) {
            //clear the setupHashMap and set it back to the default values
            SetupData.setDefaultValues(SetupData.HASH.SETUP);
            setupHashMap = SetupData.getSetupHashMap();
            leftOrRight = "Left";
            isLocalStorageClicked = false;

            leftSelected();
            GenUtils.disabledButtonState(this, saveButton);
            cancelButton.setEnabled(true);

            Toast.makeText(this, "All variables successfully reset.", Toast.LENGTH_SHORT).show();
        }
        else {
            //save values and return to the PregameActivity
            setupHashMap.put("LeftOrRight",leftOrRight);
            SetupData.putSetupHashMap(setupHashMap);

            Intent intent = new Intent(this, PregameActivity.class);
            startActivity(intent);
        }
    }

    public void cancelClick (View view) {
        Intent intent = new Intent(this, PregameActivity.class);
        startActivity(intent);
    }
}