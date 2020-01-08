package com.mercury1089.scoutingapp2019;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mercury1089.scoutingapp2019.utils.GenUtils;
import java.io.Serializable;
import java.util.HashMap;
import androidx.fragment.app.Fragment;

public class Settings extends Fragment {

    private BootstrapButton leftButton;
    private BootstrapButton rightButton;
    private BootstrapButton localStorageResetButton;
    private BootstrapButton saveButton;
    private boolean isLocalStorageClicked;
    private boolean isFirstTime;
    private String leftOrRight;
    private BootstrapButton cancelButton;

    private HashMap<String, String> setupHashMap;

    public static Settings newInstance() {
        Settings fragment = new Settings();
        return fragment;
    }

    private MainActivity context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = (MainActivity) getActivity();
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    public void onStart(Bundle savedInstanceState) {
        super.onStart();

        //setting variables to screen elements for changing their properties
        leftButton = context.findViewById(R.id.FieldSideLeft);
        rightButton = context.findViewById(R.id.FieldSideRight);
        localStorageResetButton = context.findViewById(R.id.LocalStorageResetButton);
        saveButton = context.findViewById(R.id.SaveButton);
        cancelButton = context.findViewById(R.id.CancelButton);

        //default values
        isLocalStorageClicked = false;
        isFirstTime = false;
        leftOrRight = "left";
        leftSelected();
        String mainLeftOrRight = "";

        //create hashmap for data transfer between screens
        setupHashMap = context.setupHashMap;
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
        GenUtils.selectedButtonState(context, rightButton);
        GenUtils.defaultButtonState(context, leftButton);
        GenUtils.defaultButtonState(context, localStorageResetButton);
    }

    private void leftSelected(){
        leftOrRight = "left";
        GenUtils.selectedButtonState(context, rightButton);
        GenUtils.defaultButtonState(context, leftButton);
        GenUtils.defaultButtonState(context, localStorageResetButton);
    }

    private void disable (BootstrapButton button) {
        button.setBackgroundColor(GenUtils.getAColor(context, (R.color.savedefault)));
        button.setTextColor(GenUtils.getAColor(context, R.color.savetextdefault));
        button.setEnabled(false);
    }

    private void enable (BootstrapButton button) {
        button.setBackgroundColor(GenUtils.getAColor(context, R.color.genutils_green));
        button.setTextColor(GenUtils.getAColor(context, R.color.light));
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
            GenUtils.selectedButtonState(context, localStorageResetButton);
            isFirstTime = true;
            disable(cancelButton);
        } else {
            isLocalStorageClicked = false;
            GenUtils.defaultButtonState(context, localStorageResetButton);
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
            Toast.makeText(context, "All variables successfully reset.", Toast.LENGTH_SHORT).show();
        }
        else {
            //save values and go to the setup screen (MainActivity)
            setupHashMap.put("NoShow","0");
            setupHashMap.put("AllianceColor","");
            setupHashMap.put("LeftOrRight",leftOrRight);
            context.setupHashMap = setupHashMap;
            getFragmentManager().beginTransaction().remove(this);
            getFragmentManager().beginTransaction().commit();
        }
    }

    public void cancelClick (View view) {
        getFragmentManager().beginTransaction().remove(this);
        getFragmentManager().beginTransaction().commit();
    }
}