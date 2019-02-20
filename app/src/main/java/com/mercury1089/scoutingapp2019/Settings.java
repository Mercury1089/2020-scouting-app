package com.mercury1089.scoutingapp2019;



import android.content.Intent;



import android.os.Bundle;



import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;



import android.widget.Button;

import com.beardedhen.androidbootstrap.BootstrapButton;

public class Settings extends AppCompatActivity {

    private BootstrapButton fieldSideLeftButton;
    private BootstrapButton fieldSideRightButton;
    private Button localStorageResetButton;
    private Button saveButton;
    private boolean isLeft;
    private boolean isRight;
    private boolean isLocalStorageClicked;
    public boolean hasBeenSaved;
    private String leftOrRight;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        fieldSideLeftButton = findViewById(R.id.FieldSideLeft);
        fieldSideRightButton = findViewById(R.id.FieldSideRight);
        localStorageResetButton = findViewById(R.id.LocalStorageResetButton);
        saveButton = findViewById(R.id.SaveButton);
        isLeft = false;
        isRight = false;
        isLocalStorageClicked = false;
        hasBeenSaved = false;
        leftOrRight = "";
        leftDefault();
        rightDefault();

        isRight = false;
        isLeft = false;
    }

    public void rightClick (View view) {
        if (!isRight) {
            isRight = true;
            leftOrRight = "Right";
            fieldSideRightButton.setBackgroundColor(getResources().getColor(R.color.orange));
            fieldSideRightButton.setTextColor(getResources().getColor(R.color.light));
            leftDefault();
            localStorageResetDefault();
            saveButton.setEnabled(true);
        }
        else {
            isRight = false;
            leftOrRight = "";
            rightDefault();
            saveButton.setEnabled(false);
        }
    }



    public void leftClick (View view) {
        if (!isLeft) {
            isLeft = true;
            leftOrRight = "Left";
            rightDefault();
            fieldSideLeftButton.setBackgroundColor(getResources().getColor(R.color.orange));
            fieldSideLeftButton.setTextColor(getResources().getColor(R.color.light));
            saveButton.setEnabled(true);
            localStorageResetDefault();
        } else {
            isLeft = false;
            leftOrRight = "";
            leftDefault();
            saveButton.setEnabled(false);
        }
    }

    public void localStorageResetClick (View view) {
        if (!isLocalStorageClicked) {
            isLocalStorageClicked = true;
            localStorageResetButton.setBackgroundColor(getResources().getColor(R.color.orange));
            localStorageResetButton.setTextColor(getResources().getColor(R.color.light));
        } else
            localStorageResetDefault();
    }

    private void localStorageResetDefault () {
        isLocalStorageClicked = false;
        localStorageResetButton.setBackgroundColor(getResources().getColor(R.color.light));
        localStorageResetButton.setTextColor(getResources().getColor(R.color.grey));
    }

    private void rightDefault () {
        fieldSideRightButton.setBackgroundColor(getResources().getColor(R.color.light));
        fieldSideRightButton.setTextColor(getResources().getColor(R.color.grey));
        isRight = false;
    }

    private void leftDefault () {
        fieldSideLeftButton.setBackgroundColor(getResources().getColor(R.color.light));
        fieldSideLeftButton.setTextColor(getResources().getColor(R.color.grey));
        isLeft = false;
    }

    public void saveClick (View view) {
        hasBeenSaved = true;
        Intent intent = new Intent(Settings.this, MainActivity.class);
        intent.putExtra("LEFTORRIGHT", leftOrRight);
        startActivity(intent);
    }

    public void cancelClick (View view) {
        leftOrRight = "";
        Intent intent = new Intent(Settings.this,MainActivity.class);
        intent.putExtra("LEFTORRIGHT", leftOrRight);
        startActivity(intent);
    }


}