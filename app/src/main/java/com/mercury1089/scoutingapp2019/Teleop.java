package com.mercury1089.scoutingapp2019;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mercury1089.scoutingapp2019.utils.GenUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import at.markushi.ui.CircleButton;

import static com.mercury1089.scoutingapp2019.utils.GenUtils.getAColor;

public class Teleop extends AppCompatActivity {
    private int CSPR2Counter = 0;
    private int CSPR1Counter = 0;
    private int CSPR3Counter = 0;

    private TextView CSPF1;
    private TextView CSPF2;
    private TextView CSCF1;
    private TextView CSCF2;
    private TextView CSPL1;
    private TextView CSPL2;
    private TextView CSPL3;
    private TextView CSCL1;
    private TextView CSCL2;
    private TextView CSCL3;
    private TextView CSCR1;
    private TextView CSCR2;
    private TextView CSCR3;
    private TextView CSPR1;
    private TextView CSPR2;
    private TextView CSPR3;

    //CARGO SHIP
    //panel variables
    CircleButton CargoShipPanelFront1;
    CircleButton CargoShipPanelFront2;
    CircleButton CargoShipPanelLeft1;
    CircleButton CargoShipPanelLeft2;
    CircleButton CargoShipPanelLeft3;
    CircleButton CargoShipPanelRight1;
    CircleButton CargoShipPanelRight2;
    CircleButton CargoShipPanelRight3;

    //cargo variables
    CircleButton CargoShipCargoFront1;
    CircleButton CargoShipCargoFront2;
    CircleButton CargoShipCargoLeft1;
    CircleButton CargoShipCargoLeft2;
    CircleButton CargoShipCargoLeft3;
    CircleButton CargoShipCargoRight1;
    CircleButton CargoShipCargoRight2;
    CircleButton CargoShipCargoRight3;

    //displayed counters

    //cargo ship counters
    private int CSPF1Counter = 0;
    private int CSPF2Counter = 0;
    private int CSCF1Counter = 0;
    private int CSCF2Counter = 0;
    private int CSPL1Counter = 0;
    private int CSPL2Counter = 0;
    private int CSPL3Counter = 0;
    private int CSCL1Counter = 0;
    private int CSCL2Counter = 0;
    private int CSCL3Counter = 0;
    private int CSCR1Counter = 0;
    private int CSCR2Counter = 0;
    private int CSCR3Counter = 0;
    private HashMap<String, String> setupHashMap;
    private HashMap<String, String> scoreHashMap;

    //bootstrap buttons
    BootstrapButton SetupButton;
    BootstrapButton SandstormButton;
    BootstrapButton TeleopButton;
    BootstrapButton ClimbButton;

    //other variables
    Button UndoButton;
    ConstraintLayout constraintLayout;
    String UNDO;
    Switch FellOverSwitch;
    int eventCounter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (setupHashMap.get("LeftOrRight").equals("Left"))
            setContentView(R.layout.activity_teleop);
        else
            setContentView(R.layout.activity_teleop_right);
        //initializers
        CargoShipPanelFront1 = findViewById(R.id.CargoShipPanelFront1);
        CargoShipPanelFront2 = findViewById(R.id.CargoShipPanelFront2);
        CargoShipPanelLeft1 = findViewById(R.id.CargoShipPanelLeft1);
        CargoShipPanelLeft2 = findViewById(R.id.CargoShipPanelLeft2);
        CargoShipPanelLeft3 = findViewById(R.id.CargoShipPanelLeft3);
        CargoShipPanelRight1 = findViewById(R.id.CargoShipPanelRight1);
        CargoShipPanelRight2 = findViewById(R.id.CargoShipPanelRight2);
        CargoShipPanelRight3 = findViewById(R.id.CargoShipPanelRight3);
        CargoShipCargoFront1 = findViewById(R.id.CargoShipCargoFront1);
        CargoShipCargoFront2 = findViewById(R.id.CargoShipCargoFront2);
        CargoShipCargoLeft1 = findViewById(R.id.CargoShipCargoLeft1);
        CargoShipCargoLeft2 = findViewById(R.id.CargoShipCargoLeft2);
        CargoShipCargoLeft3 = findViewById(R.id.CargoShipCargoLeft3);
        CargoShipCargoRight1 = findViewById(R.id.CargoShipCargoRight1);
        CargoShipCargoRight2 = findViewById(R.id.CargoShipCargoRight2);
        CargoShipCargoRight3 = findViewById(R.id.CargoShipCargoRight3);
        CSPF1 = findViewById(R.id.CSPF1);
        CSPF2 = findViewById(R.id.CSPF2);
        CSCF1 = findViewById(R.id.CSCF1);
        CSCF2 = findViewById(R.id.CSCF2);
        CSPL1 = findViewById(R.id.CSPL1);
        CSPL2 = findViewById(R.id.CSPL2);
        CSPL3 = findViewById(R.id.CSPL3);
        CSCL1 = findViewById(R.id.CSCL1);
        CSCL2 = findViewById(R.id.CSCL2);
        CSCL3 = findViewById(R.id.CSCL3);
        CSCR1 = findViewById(R.id.CSCR1);
        CSCR2 = findViewById(R.id.CSCR2);
        CSCR3 = findViewById(R.id.CSCR3);
        CSPR1 = findViewById(R.id.CSPR1);
        CSPR2 = findViewById(R.id.CSPR2);
        CSPR3 = findViewById(R.id.CSPR3);
        SetupButton = findViewById(R.id.SetupButton);
        SandstormButton = findViewById(R.id.SandstormButton);
        TeleopButton = findViewById(R.id.TeleopButton);
        ClimbButton = findViewById(R.id.ClimbButton);
        setupHashMap = new HashMap<>();
        scoreHashMap = new HashMap<>();
        UndoButton = findViewById(R.id.UndoButton);
        constraintLayout = findViewById(R.id.layout);
        FellOverSwitch = findViewById(R.id.FellOverSwitch);
        //make Sandstorm button look active
        selectedButtonColors(TeleopButton);
        //make other buttons look default
        defaultButtonState(SetupButton);
        defaultButtonState(SandstormButton);
        defaultButtonState(ClimbButton);
        //disable scoring diagram
        disableScoringDiagram('A');
        UndoButton.setEnabled(false);
        Serializable setupData = getIntent().getSerializableExtra("setupHashMap");
        setupHashMap = (HashMap<String, String>)setupData;

        String fellOver = getIntent().getStringExtra("fellOver");
        if (fellOver != null && fellOver.equals("True")) {
            UNDO = "FellOver";
            UndoButton.setEnabled(true);
            setupHashMap.put("FellOver",String.valueOf(1));
            FellOverSwitch.setChecked(true);

            disableScoringDiagram('A');


                for (int i = 0; i < constraintLayout.getChildCount(); i++) {
                    if ((constraintLayout.getChildAt(i) instanceof TextView) && (constraintLayout.getChildAt(i).getTag() != null)) {
                        if (constraintLayout.getChildAt(i).getTag().toString().length() > 9) {
                            String tag = constraintLayout.getChildAt(i).getTag().toString();
                            if (tag.equals("label")) {
                                setTextToColor((TextView) constraintLayout.getChildAt(i), "grey");
                            }
                        }
                    }
                }
            }




            Serializable scoreData = getIntent().getSerializableExtra("scoreHashMap");

            if (scoreData != null) {
                scoreHashMap = (HashMap<String, String>) scoreData;
                Object keySet[] = scoreHashMap.keySet().toArray();
                String tag;
                for (int i = 0; i < keySet.length; i++) {
                    tag = String.valueOf(keySet[i]);
                    char arr[] = tag.toCharArray();
                    String hashVal;

                    if (tag.toCharArray()[1] == 'S') {
                        if (arr[2] == 'P')
                        {
                            if (arr[3] == 'L') {
                                if (arr[4] == '1') {
                                    hashVal = scoreHashMap.get("CSPL1");
                                    CSPL1.setText(hashVal);
                                    if (Integer.parseInt(hashVal) > 0) {
                                        CargoShipPanelLeft1.setColor(GenUtils.YELLOW);
                                        CSPL1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                                    }
                                    else {
                                        //panel disabled colors
                                        //panel text set
                                    }
                                }
                                else if (arr[4] == '2') {
                                    hashVal = scoreHashMap.get("CSPL2");
                                    CSPL2.setText(hashVal);
                                    if (Integer.parseInt(hashVal) > 0) {
                                        CargoShipPanelLeft2.setColor(GenUtils.YELLOW);
                                        CSPL2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                                    }
                                    else {
                                        //panel disabled colors
                                        //panel text set
                                    }
                                }
                                else if (arr[4] == '3') {
                                    hashVal = scoreHashMap.get("CSPL3");
                                    CSPL3.setText(hashVal);
                                    if (Integer.parseInt(hashVal) > 0) {
                                        CargoShipPanelLeft3.setColor(GenUtils.YELLOW);
                                        CSPL3.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                                    }
                                    else {
                                        //panel disabled colors
                                        //panel text set
                                    }
                                }
                            }
                            else if (arr[3] == 'R') {
                                if (arr[4] == '1') {
                                    hashVal = scoreHashMap.get("CSPR1");
                                    CSPR1.setText(hashVal);
                                    if (Integer.parseInt(hashVal) > 0) {
                                        CargoShipPanelRight1.setColor(GenUtils.YELLOW);
                                        CSPR1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                                    }
                                    else {
                                        //panel disabled colors
                                        //panel text set
                                    }
                                }
                                else if (arr[4] == '2') {
                                    hashVal = scoreHashMap.get("CSPR2");
                                    CSPR2.setText(hashVal);
                                    if (Integer.parseInt(hashVal) > 0) {
                                        CargoShipPanelRight2.setColor(GenUtils.YELLOW);
                                        CSPR2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                                    }
                                    else {
                                        //panel disabled colors
                                        //panel text set
                                    }
                                }
                                else if (arr[4] == '3') {
                                    hashVal = scoreHashMap.get("CSPR3");
                                    CSPR3.setText(hashVal);
                                    if (Integer.parseInt(hashVal) > 0) {
                                        CargoShipPanelRight3.setColor(GenUtils.YELLOW);
                                        CSPR3.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                                    }
                                    else {
                                        //panel disabled colors
                                        //panel text set
                                    }
                                }
                            }
                            else if (arr[3] == 'F') {
                                if (arr[4] == '1') {
                                    hashVal = scoreHashMap.get("CSPF1");
                                    CSPF1.setText(hashVal);
                                    if (Integer.parseInt(hashVal) > 0) {
                                        CargoShipPanelFront1.setColor(GenUtils.YELLOW);
                                        CSPF1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                                    }
                                    else {
                                        //panel disabled colors
                                        //panel text set
                                    }
                                }
                                else if (arr[4] == '2') {
                                    hashVal = scoreHashMap.get("CSPF2");
                                    CSPF2.setText(hashVal);
                                    if (Integer.parseInt(hashVal) > 0) {
                                        CargoShipPanelFront2.setColor(GenUtils.YELLOW);
                                        CSPF2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                                    }
                                    else {
                                        //panel disabled colors
                                        //panel text set
                                    }
                                }
                            }
                        }
                        else if (arr[2] == 'C')
                        {
                            if (arr[3] == 'L') {
                                if (arr[4] == '1') {
                                    hashVal = scoreHashMap.get("CSCL1");
                                    CSPL1.setText(hashVal);
                                    if (Integer.parseInt(hashVal) > 0) {
                                        CargoShipCargoLeft1.setColor(GenUtils.ORANGE);
                                        //panel text set
                                    }
                                    else {
                                        //panel disabled colors
                                        //panel text set
                                    }
                                }
                                else if (arr[4] == '2') {
                                    hashVal = scoreHashMap.get("CSCL2");
                                    CSPL2.setText(hashVal);
                                    if (Integer.parseInt(hashVal) > 0) {
                                        CargoShipCargoLeft2.setColor(GenUtils.ORANGE);
                                        //panel text set
                                    }
                                    else {
                                        //panel disabled colors
                                        //panel text set
                                    }
                                }
                                else if (arr[4] == '3') {
                                    hashVal = scoreHashMap.get("CSCL3");
                                    CSPL3.setText(hashVal);
                                    if (Integer.parseInt(hashVal) > 0) {
                                        CargoShipCargoLeft3.setColor(GenUtils.ORANGE);
                                        //panel text set
                                    }
                                    else {
                                        //panel disabled colors
                                        //panel text set
                                    }
                                }
                            }
                            else if (arr[3] == 'R') {
                                if (arr[4] == '1') {
                                    hashVal = scoreHashMap.get("CSCR1");
                                    CSPR1.setText(hashVal);
                                    if (Integer.parseInt(hashVal) > 0) {
                                        CargoShipCargoRight1.setColor(GenUtils.ORANGE);
                                        //panel text set
                                    } else {
                                        //panel disabled colors
                                        //panel text set
                                    }
                                } else if (arr[4] == '2') {
                                    hashVal = scoreHashMap.get("CSCL2");
                                    CSPL2.setText(hashVal);
                                    if (Integer.parseInt(hashVal) > 0) {
                                        CargoShipCargoRight2.setColor(GenUtils.ORANGE);
                                        //panel text set
                                    } else {
                                        //panel disabled colors
                                        //panel text set
                                    }
                                } else if (arr[4] == '3') {
                                    hashVal = scoreHashMap.get("CSCL3");
                                    CSPL3.setText(hashVal);
                                    if (Integer.parseInt(hashVal) > 0) {
                                        CargoShipCargoRight3.setColor(GenUtils.ORANGE);
                                        //panel text set
                                    } else {
                                        //panel disabled colors
                                        //panel text set
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else {
                scoreHashMap = new HashMap<>();
            }

            //setting buttons to default state
            SetupButton.setEnabled(false);
            defaultButtonState(SetupButton);

            SandstormButton.setEnabled(false);
            defaultButtonState(SandstormButton);

            selectedButtonColors(TeleopButton);
            defaultButtonState(ClimbButton);




            FellOverSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                boolean wasPanel = false;
                boolean wasCargo = false;
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                    String color;
                    if (isChecked) {
                        if (UNDO != null) {
                            if (UNDO.equals("Panel"))
                                wasPanel = true;
                            else if (UNDO.equals("Cargo"))
                                wasCargo = true;
                        }
                        UNDO = "FellOver";
                        UndoButton.setEnabled(true);
                        setupHashMap.put("FellOver",String.valueOf(1));

                        disableScoringDiagram('A');


                        color = "grey";

                    } else {
                        setupHashMap.put("FellOver",String.valueOf(0));
                        if (wasPanel) {
                            UNDO = "Panel";
                            UndoButton.setEnabled(true);
                            enableScoringDiagram('P');
                            disableScoringDiagram('C');
                        }
                        if (wasCargo) {
                            UNDO = "Cargo";
                            UndoButton.setEnabled(true);
                            enableScoringDiagram('C');
                            disableScoringDiagram('P');
                        }
                        color = "white";
                    }

                    for (int i = 0; i < constraintLayout.getChildCount(); i++) {
                        if ((constraintLayout.getChildAt(i) instanceof TextView) && (constraintLayout.getChildAt(i).getTag() != null)) {
                            if (constraintLayout.getChildAt(i).getTag().toString().length() > 9) {
                                String tag = constraintLayout.getChildAt(i).getTag().toString();
                                if (tag.equals("label")) {
                                    setTextToColor((TextView) constraintLayout.getChildAt(i), color);
                                }
                            }
                        }
                    }
                }

            });
        }
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

        //call methods
        private void defaultButtonState (BootstrapButton button) {
            button.setBackgroundColor(getAColor(Teleop.this, R.color.light));
            button.setTextColor(getAColor(Teleop.this, R.color.grey));
        }
        public void selectedButtonColors(BootstrapButton button) {
            button.setBackgroundColor(getAColor(Teleop.this, R.color.orange));
            button.setTextColor(getAColor(Teleop.this, R.color.light));
        }
        private void setTextToColor (TextView textView, String color) {
            if (color.equals("grey"))
                textView.setTextColor(getAColor(Teleop.this, R.color.grey));
            else if (color.equals("white"))
                textView.setTextColor(getAColor(Teleop.this, R.color.light));
        }


        private void disableScoringDiagram (char c) {
            switch (c) {
                case 'A':
                case 'P':
                    CargoShipPanelFront1.setEnabled(false);
                    CargoShipPanelFront2.setEnabled(false);
                    CargoShipPanelLeft1.setEnabled(false);
                    CargoShipPanelLeft2.setEnabled(false);
                    CargoShipPanelLeft3.setEnabled(false);
                    CargoShipPanelRight1.setEnabled(false);
                    CargoShipPanelRight2.setEnabled(false);
                    CargoShipPanelRight3.setEnabled(false);
                    CSPF1.setEnabled(false);
                    CSPF2.setEnabled(false);

                    CSPL1.setEnabled(false);
                    CSPL2.setEnabled(false);
                    CSPL3.setEnabled(false);

                    CSPR1.setEnabled(false);
                    CSPR2.setEnabled(false);
                    CSPR3.setEnabled(false);
                    if (CSPF1Counter > 0) {
                        CargoShipPanelFront1.setColor(GenUtils.YELLOW);
                        CSPF1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    else {
                        CargoShipPanelFront1.setColor(GenUtils.BLACK);
                        CSPF1.setTextColor(getAColor(Teleop.this, R.color.light));
                    }

                    if (CSPF2Counter > 0) {
                        CargoShipPanelFront2.setColor(GenUtils.YELLOW);
                        CSPF2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    else {
                        CargoShipPanelFront2.setColor(GenUtils.BLACK);
                        CSPF2.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    if (CSPL1Counter > 0) {
                        CargoShipPanelLeft1.setColor(GenUtils.YELLOW);
                        CSPL1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    else {
                        CargoShipPanelLeft1.setColor(GenUtils.BLACK);
                        CSPL1.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    if (CSPL2Counter > 0) {
                        CargoShipPanelLeft2.setColor(GenUtils.YELLOW);
                        CSPL2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    else {
                        CargoShipPanelLeft2.setColor(GenUtils.BLACK);
                        CSPL2.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    if (CSPL3Counter > 0) {
                        CargoShipPanelLeft3.setColor(GenUtils.YELLOW);
                        CSPL3.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    else {
                        CargoShipPanelLeft3.setColor(GenUtils.BLACK);
                        CSPL3.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    if (CSPR1Counter > 0) {
                        CargoShipPanelRight1.setColor(GenUtils.YELLOW);
                        CSPR1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    else {
                        CargoShipPanelRight1.setColor(GenUtils.BLACK);
                        CSPR1.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    if (CSPR2Counter > 0) {
                        CargoShipPanelRight2.setColor(GenUtils.YELLOW);
                        CSPR2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    else {
                        CargoShipPanelRight2.setColor(GenUtils.BLACK);
                        CSPR2.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    if (CSPR3Counter > 0) {
                        CargoShipPanelRight3.setColor(GenUtils.YELLOW);
                        CSPR3.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    else {
                        CargoShipPanelRight3.setColor(GenUtils.BLACK);
                        CSPR3.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    if (c == 'P')
                        break;
                case 'C':
                    CargoShipCargoFront1.setEnabled(false);
                    CargoShipCargoFront2.setEnabled(false);
                    CargoShipCargoLeft1.setEnabled(false);
                    CargoShipCargoLeft2.setEnabled(false);
                    CargoShipCargoLeft3.setEnabled(false);
                    CargoShipCargoRight1.setEnabled(false);
                    CargoShipCargoRight2.setEnabled(false);
                    CargoShipCargoRight3.setEnabled(false);

                    CSCF1.setEnabled(false);
                    CSCF2.setEnabled(false);

                    CSCL1.setEnabled(false);
                    CSCL2.setEnabled(false);
                    CSCL3.setEnabled(false);
                    CSCR1.setEnabled(false);
                    CSCR2.setEnabled(false);
                    CSCR3.setEnabled(false);

                    if (CSCF1Counter > 0) {
                        CargoShipCargoFront1.setColor(GenUtils.GREEN);
                        CSCF1.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    else {
                        CargoShipCargoFront1.setColor(GenUtils.BLACK);
                        CSCF1.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    if (CSCF2Counter > 0) {
                        CargoShipCargoFront2.setColor(GenUtils.GREEN);
                        CSCF2.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    else {
                        CargoShipCargoFront2.setColor(GenUtils.BLACK);
                        CSCF2.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    if (CSCL1Counter > 0) {
                        CargoShipCargoLeft1.setColor(GenUtils.GREEN);
                        CSCL1.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    else {
                        CargoShipCargoLeft1.setColor(GenUtils.BLACK);
                        CSCL1.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    if (CSCL2Counter > 0) {
                        CargoShipCargoLeft2.setColor(GenUtils.GREEN);
                        CSCL2.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    else {
                        CargoShipCargoLeft2.setColor(GenUtils.BLACK);
                        CSCL2.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    if (CSCL3Counter > 0) {
                        CargoShipCargoLeft3.setColor(GenUtils.GREEN);
                        CSCL3.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    else {
                        CargoShipCargoLeft3.setColor(GenUtils.BLACK);
                        CSCL3.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    if (CSCR1Counter > 0) {
                        CargoShipCargoRight1.setColor(GenUtils.GREEN);
                        CSCR1.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    else {
                        CargoShipCargoRight1.setColor(GenUtils.BLACK);
                        CSCR1.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    if (CSCR2Counter > 0) {
                        CargoShipCargoRight2.setColor(GenUtils.GREEN);
                        CSCR2.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    else {
                        CargoShipCargoRight2.setColor(GenUtils.BLACK);
                        CSCR2.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    if (CSCR3Counter > 0) {
                        CargoShipCargoRight3.setColor(GenUtils.GREEN);
                        CSCR3.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    else {
                        CargoShipCargoRight3.setColor(GenUtils.BLACK);
                        CSCR3.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
            }
        }

        private void enableScoringDiagram (char c) {
            switch (c) {
                case 'A':
                case 'P':
                    CargoShipPanelFront1.setEnabled(true);
                    CargoShipPanelFront2.setEnabled(true);
                    CargoShipPanelLeft1.setEnabled(true);
                    CargoShipPanelLeft2.setEnabled(true);
                    CargoShipPanelLeft3.setEnabled(true);
                    CargoShipPanelRight1.setEnabled(true);
                    CargoShipPanelRight2.setEnabled(true);
                    CargoShipPanelRight3.setEnabled(true);
                    CSPF1.setEnabled(true);
                    CSPF2.setEnabled(true);

                    CSPL1.setEnabled(true);
                    CSPL2.setEnabled(true);
                    CSPL3.setEnabled(true);

                    CSPR1.setEnabled(true);
                    CSPR2.setEnabled(true);
                    CSPR3.setEnabled(true);

                    if (CSPF1Counter > 0) {
                        CargoShipPanelFront1.setColor(GenUtils.YELLOW);
                        CSPF1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    else {
                        CargoShipPanelFront1.setColor(GenUtils.LIGHT_YELLOW);
                        CSPF1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    if (CSPF2Counter > 0) {
                        CargoShipPanelFront2.setColor(GenUtils.YELLOW);
                        CSPF2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    else {
                        CargoShipPanelFront2.setColor(GenUtils.LIGHT_YELLOW);
                        CSPF2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    if (CSPL1Counter > 0) {
                        CargoShipPanelLeft1.setColor(GenUtils.YELLOW);
                        CSPL1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    else {
                        CargoShipPanelLeft1.setColor(GenUtils.LIGHT_YELLOW);
                        CSPL1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    if (CSPL2Counter > 0) {
                        CargoShipPanelLeft2.setColor(GenUtils.YELLOW);
                        CSPL2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    else {
                        CargoShipPanelLeft2.setColor(GenUtils.LIGHT_YELLOW);
                        CSPL2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    if (CSPL3Counter > 0) {
                        CargoShipPanelLeft3.setColor(GenUtils.YELLOW);
                        CSPL3.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    else {
                        CargoShipPanelLeft3.setColor(GenUtils.LIGHT_YELLOW);
                        CSPL3.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    if (CSPR1Counter > 0) {
                        CargoShipPanelRight1.setColor(GenUtils.YELLOW);
                        CSPR1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    else {
                        CargoShipPanelRight1.setColor(GenUtils.LIGHT_YELLOW);
                        CSPR1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    if (CSPR2Counter > 0) {
                        CargoShipPanelRight2.setColor(GenUtils.YELLOW);
                        CSPR2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    else {
                        CargoShipPanelRight2.setColor(GenUtils.LIGHT_YELLOW);
                        CSPR2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    if (CSPR3Counter > 0) {
                        CargoShipPanelRight3.setColor(GenUtils.YELLOW);
                        CSPR3.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    else {
                        CargoShipPanelRight3.setColor(GenUtils.LIGHT_YELLOW);
                        CSPR3.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    if (c == 'P')
                        break;
                case 'C':
                    CargoShipCargoFront1.setEnabled(true);
                    CargoShipCargoFront2.setEnabled(true);
                    CargoShipCargoLeft1.setEnabled(true);
                    CargoShipCargoLeft2.setEnabled(true);
                    CargoShipCargoLeft3.setEnabled(true);
                    CargoShipCargoRight1.setEnabled(true);
                    CargoShipCargoRight2.setEnabled(true);
                    CargoShipCargoRight3.setEnabled(true);

                    CSCF1.setEnabled(true);
                    CSCF2.setEnabled(true);

                    CSCL1.setEnabled(true);
                    CSCL2.setEnabled(true);
                    CSCL3.setEnabled(true);
                    CSCR1.setEnabled(true);
                    CSCR2.setEnabled(true);
                    CSCR3.setEnabled(true);

                    if (CSCF1Counter > 0) {
                        CargoShipCargoFront1.setColor(GenUtils.ORANGE);
                        CSCF1.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    else {
                        CargoShipCargoFront1.setColor(Color.rgb(221, 172, 107));
                        CSCF1.setTextColor(getAColor(Teleop.this, R.color.defaultdisabled));
                    }
                    if (CSCF2Counter > 0) {
                        CargoShipCargoFront2.setColor(GenUtils.ORANGE);
                        CSCF2.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    else {
                        CargoShipCargoFront2.setColor(Color.rgb(221, 172, 107));
                        CSCF2.setTextColor(getAColor(Teleop.this, R.color.defaultdisabled));
                    }
                    if (CSCL1Counter > 0) {
                        CargoShipCargoLeft1.setColor(GenUtils.ORANGE);
                        CSCL1.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    else {
                        CargoShipCargoLeft1.setColor(Color.rgb(221, 172, 107));
                        CSCL1.setTextColor(getAColor(Teleop.this, R.color.defaultdisabled));
                    }
                    if (CSCL2Counter > 0) {
                        CargoShipCargoLeft2.setColor(GenUtils.ORANGE);
                        CSCL2.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    else {
                        CargoShipCargoLeft2.setColor(Color.rgb(221, 172, 107));
                        CSCL2.setTextColor(getAColor(Teleop.this, R.color.defaultdisabled));
                    }
                    if (CSCL3Counter > 0) {
                        CargoShipCargoLeft3.setColor(GenUtils.ORANGE);
                        CSCL3.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    else {
                        CargoShipCargoLeft3.setColor(Color.rgb(221, 172, 107));
                        CSCL3.setTextColor(getAColor(Teleop.this, R.color.defaultdisabled));
                    }
                    if (CSCR1Counter > 0) {
                        CargoShipCargoRight1.setColor(GenUtils.ORANGE);
                        CSCR1.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    else {
                        CargoShipCargoRight1.setColor(Color.rgb(221, 172, 107));
                        CSCR1.setTextColor(getAColor(Teleop.this, R.color.defaultdisabled));
                    }
                    if (CSCR2Counter > 0) {
                        CargoShipCargoRight2.setColor(GenUtils.ORANGE);
                        CSCR2.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    else {
                        CargoShipCargoRight2.setColor(Color.rgb(221, 172, 107));
                        CSCR2.setTextColor(getAColor(Teleop.this, R.color.defaultdisabled));
                    }
                    if (CSCR3Counter > 0) {
                        CargoShipCargoRight3.setColor(GenUtils.ORANGE);
                        CSCR3.setTextColor(getAColor(Teleop.this, R.color.light));
                    }
                    else {
                        CargoShipCargoRight3.setColor(Color.rgb(221, 172, 107));
                        CSCR3.setTextColor(getAColor(Teleop.this, R.color.defaultdisabled));
                    }
            }
        }



        //click methods
        public void setupClick (View view) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("setupHashMap", setupHashMap);

            for (int i = 0; i < constraintLayout.getChildCount(); i++) {
                if (constraintLayout.getChildAt(i) instanceof TextView) {
                    TextView textView = (TextView) constraintLayout.getChildAt(i);
                    if (textView.getTag() != null) {
                        if (!textView.getTag().equals("") && !textView.getTag().equals("label")) {
                            if (!textView.getText().toString().equals("P") && !textView.getText().toString().equals("C")) {
                                scoreHashMap.put(textView.getTag().toString(), textView.getText().toString());
                            }
                        }
                    }
                }
            }

            intent.putExtra("scoreHashMap", scoreHashMap);
            startActivity(intent);
        }

    public void sandstormClick (View view) {
        Intent intent = new Intent(this, Sandstorm.class);
        intent.putExtra("setupHashMap", setupHashMap);

        for (int i = 0; i < constraintLayout.getChildCount(); i++) {
            if (constraintLayout.getChildAt(i) instanceof TextView) {
                TextView textView = (TextView) constraintLayout.getChildAt(i);
                if (textView.getTag() != null) {
                    if (!textView.getTag().equals("") && !textView.getTag().equals("label")) {
                        if (!textView.getText().toString().equals("P") && !textView.getText().toString().equals("C")) {
                            scoreHashMap.put(textView.getTag().toString(), textView.getText().toString());
                        }
                    }
                }
            }
        }

        intent.putExtra("scoreHashMap", scoreHashMap);
        startActivity(intent);
    }

        public void climbClick (View view) {
            Intent intent = new Intent(this, Climb.class);
            intent.putExtra("setupHashMap", setupHashMap);

            for (int i = 0; i < constraintLayout.getChildCount(); i++) {
                if (constraintLayout.getChildAt(i) instanceof TextView) {
                    TextView textView = (TextView) constraintLayout.getChildAt(i);
                    if (textView.getTag() != null) {
                        if (!textView.getTag().equals("") && !textView.getTag().equals("label")) {
                            if (!textView.getText().toString().equals("P") && !textView.getText().toString().equals("C")) {
                                scoreHashMap.put(textView.getTag().toString(), textView.getText().toString());
                            }
                        }
                    }
                }
            }

            intent.putExtra("scoreHashMap", scoreHashMap);
            startActivity(intent);
        }

        //cargo ship onClicks
        public void CSPF1CounterClick (View view) {
            CSPF1Counter++;
            eventCounter++;
            CargoShipPanelFront1.setColor(GenUtils.YELLOW);
            scoreHashMap.put(CSPF1.getTag().toString(), String.valueOf(CSPF1Counter));
            UNDO = "CSPF1";
            CSPF1.setText(String.valueOf(CSPF1Counter));
            CSPF1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
            disableScoringDiagram('A');
            UndoButton.setEnabled(true);
        }
        public void CSPF2CounterClick (View view) {
            CSPF2Counter++;
            eventCounter++;
            CargoShipPanelFront2.setColor(GenUtils.YELLOW);
            scoreHashMap.put(CSPF2.getTag().toString(), String.valueOf(CSPF2Counter));
            UNDO = "CSPF2";
            CSPF2.setText(String.valueOf(CSPF2Counter));
            CSPF2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
            disableScoringDiagram('A');
            UndoButton.setEnabled(true);
        }
        public void CSCF1CounterClick (View view) {
            CSCF1Counter++;
            eventCounter++;
            CargoShipCargoFront1.setColor(GenUtils.ORANGE);
            scoreHashMap.put(CSCF1.getTag().toString(), String.valueOf(CSCF1Counter));
            UNDO = "CSCF1";
            CSCF1.setText(String.valueOf(CSCF1Counter));
            disableScoringDiagram('A');
            UndoButton.setEnabled(true);
        }
        public void CSCF2CounterClick (View view) {
            CSCF2Counter++;
            eventCounter++;
            scoreHashMap.put(CSCF2.getTag().toString(), String.valueOf(CSCF2Counter));
            CargoShipCargoFront2.setColor(GenUtils.ORANGE);
            UNDO = "CSCF2";
            CSCF2.setText(String.valueOf(CSCF2Counter));
            disableScoringDiagram('A');
            UndoButton.setEnabled(true);
        }
        public void CSPL1CounterClick (View view) {
            CSPL1Counter++;
            eventCounter++;
            scoreHashMap.put(CSPL1.getTag().toString(), String.valueOf(CSPL1Counter));
            CargoShipPanelLeft1.setColor(GenUtils.YELLOW);
            UNDO = "CSPL1";
            CSPL1.setText(String.valueOf(CSPL1Counter));
            CSPL1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
            disableScoringDiagram('A');
            UndoButton.setEnabled(true);
        }
        public void CSPL2CounterClick (View view) {
            CSPL2Counter++;
            eventCounter++;
            scoreHashMap.put(CSPL2.getTag().toString(), String.valueOf(CSPL2Counter));
            CargoShipPanelLeft2.setColor(GenUtils.YELLOW);
            UNDO = "CSPL2";
            CSPL2.setText(String.valueOf(CSPL2Counter));
            CSPL2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
            disableScoringDiagram('A');
            UndoButton.setEnabled(true);
        }
        public void CSPL3CounterClick (View view) {
            CSPL3Counter++;
            eventCounter++;
            scoreHashMap.put(CSPL3.getTag().toString(), String.valueOf(CSPL3Counter));
            CargoShipPanelLeft3.setColor(GenUtils.YELLOW);
            UNDO = "CSPL3";
            CSPL3.setText(String.valueOf(CSPL3Counter));
            CSPL3.setTextColor(getAColor(Teleop.this, R.color.textdefault));
            disableScoringDiagram('A');
            UndoButton.setEnabled(true);
        }
        public void CSCL1CounterClick (View view) {
            CSCL1Counter++;
            eventCounter++;
            scoreHashMap.put(CSCL1.getTag().toString(), String.valueOf(CSCL1Counter));
            CargoShipCargoLeft1.setColor(GenUtils.ORANGE);
            UNDO = "CSCL1";
            CSCL1.setText(String.valueOf(CSCL1Counter));
            disableScoringDiagram('A');
            UndoButton.setEnabled(true);
        }
        public void CSCL2CounterClick (View view) {
            CSCL2Counter++;
            eventCounter++;
            scoreHashMap.put(CSCL2.getTag().toString(), String.valueOf(CSCL2Counter));
            CargoShipCargoLeft2.setColor(GenUtils.ORANGE);
            UNDO = "CSCL2";
            CSCL2.setText(String.valueOf(CSCL2Counter));
            disableScoringDiagram('A');
            UndoButton.setEnabled(true);
        }
        public void CSCL3CounterClick (View view) {
            CSCL3Counter++;
            eventCounter++;
            scoreHashMap.put(CSCL3.getTag().toString(), String.valueOf(CSCL3Counter));
            CargoShipCargoLeft3.setColor(GenUtils.ORANGE);
            UNDO = "CSCL3";
            CSCL3.setText(String.valueOf(CSCL3Counter));
            disableScoringDiagram('A');
            UndoButton.setEnabled(true);
        }
        public void CSCR1CounterClick (View view) {
            CSCR1Counter++;
            eventCounter++;
            scoreHashMap.put(CSCR1.getTag().toString(), String.valueOf(CSCR1Counter));
            CargoShipCargoRight1.setColor(GenUtils.ORANGE);
            UNDO = "CSCR1";
            CSCR1.setText(String.valueOf(CSCR1Counter));
            disableScoringDiagram('A');
            UndoButton.setEnabled(true);
        }
        public void CSCR2CounterClick (View view) {
            CSCR2Counter++;
            eventCounter++;
            scoreHashMap.put(CSCR2.getTag().toString(), String.valueOf(CSCR2Counter));
            CargoShipCargoRight2.setColor(GenUtils.ORANGE);
            UNDO = "CSCR2";
            CSCR2.setText(String.valueOf(CSCR2Counter));
            disableScoringDiagram('A');
            UndoButton.setEnabled(true);
        }
        public void CSCR3CounterClick (View view) {
            CSCR3Counter++;
            eventCounter++;
            scoreHashMap.put(CSCR3.getTag().toString(), String.valueOf(CSCR3Counter));
            CargoShipCargoRight3.setColor(GenUtils.ORANGE);
            UNDO = "CSCR3";
            CSCR3.setText(String.valueOf(CSCR3Counter));
            disableScoringDiagram('A');
            UndoButton.setEnabled(true);
        }
        public void CSPR1CounterClick (View view) {
            CSPR1Counter++;
            eventCounter++;
            scoreHashMap.put(CSPR1.getTag().toString(), String.valueOf(CSPR1Counter));
            CargoShipPanelRight1.setColor(GenUtils.YELLOW);
            UNDO = "CSPR1";
            CSPR1.setText(String.valueOf(CSPR1Counter));
            CSPR1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
            disableScoringDiagram('A');
            UndoButton.setEnabled(true);
        }
        public void CSPR2CounterClick (View view) {
            CSPR2Counter++;
            eventCounter++;
            scoreHashMap.put(CSPR2.getTag().toString(), String.valueOf(CSPR2Counter));
            CargoShipPanelRight2.setColor(GenUtils.YELLOW);
            UNDO = "CSPR2";
            CSPR2.setText(String.valueOf(CSPR2Counter));
            CSPR2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
            disableScoringDiagram('A');
            UndoButton.setEnabled(true);
        }
        public void CSPR3CounterClick (View view) {
            CSPR3Counter++;
            eventCounter++;
            scoreHashMap.put(CSPR3.getTag().toString(), String.valueOf(CSPR3Counter));
            CargoShipPanelRight3.setColor(GenUtils.YELLOW);
            UNDO = "CSPR3";
            CSPR3.setText(String.valueOf(CSPR3Counter));
            CSPR3.setTextColor(getAColor(Teleop.this, R.color.textdefault));
            disableScoringDiagram('A');
            UndoButton.setEnabled(true);
        }

        //undo button
        public void UndoClick (View view) {
            UndoButton.setEnabled(false);
            switch (UNDO) {
                case "FellOver":
                    setupHashMap.put("FellOver",String.valueOf(0));
                    FellOverSwitch.setChecked(!FellOverSwitch.isChecked());
                    break;
                //undo for circle buttons aka locations
                case "CSPF1":
                    CSPF1Counter--;
                    CSPF1.setText(String.valueOf(CSPF1Counter));
                    enableScoringDiagram('P');

                    if (CSPF1Counter == 0) {
                        CargoShipPanelFront1.setColor(GenUtils.LIGHT_YELLOW);
                        CSPF1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    break;
                case "CSPF2":
                    CSPF2Counter--;
                    CSPF2.setText(String.valueOf(CSPF2Counter));
                    enableScoringDiagram('P');

                    if (CSPF2Counter == 0) {
                        CargoShipPanelFront2.setColor(GenUtils.LIGHT_YELLOW);
                        CSPF2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    break;
                case "CSCF1":
                    CSCF1Counter--;
                    CSCF1.setText(String.valueOf(CSCF1Counter));
                    enableScoringDiagram('C');

                    if (CSCF1Counter == 0) {
                        CargoShipCargoFront1.setColor(Color.rgb(221, 172, 107));
                        CSCF1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    break;
                case "CSCF2":
                    CSCF2Counter--;
                    CSCF2.setText(String.valueOf(CSCF2Counter));
                    enableScoringDiagram('C');

                    if (CSCF2Counter == 0) {
                        CargoShipCargoFront2.setColor(Color.rgb(221, 172, 107));
                        CSCF2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    break;
                case "CSPL1":
                    CSPL1Counter--;
                    CSPL1.setText(String.valueOf(CSPL1Counter));
                    enableScoringDiagram('P');

                    if (CSPL1Counter == 0) {
                        CargoShipPanelLeft1.setColor(GenUtils.LIGHT_YELLOW);
                        CSPL1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    break;
                case "CSPL2":
                    CSPL2Counter--;
                    CSPL2.setText(String.valueOf(CSPL2Counter));
                    enableScoringDiagram('P');

                    if (CSPL2Counter == 0) {
                        CargoShipPanelLeft2.setColor(GenUtils.LIGHT_YELLOW);
                        CSPL2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    break;
                case "CSPL3":
                    CSPL3Counter--;
                    CSPL3.setText(String.valueOf(CSPL3Counter));
                    enableScoringDiagram('P');

                    if (CSPL3Counter == 0) {
                        CargoShipPanelLeft3.setColor(GenUtils.LIGHT_YELLOW);
                        CSPL3.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    break;
                case "CSCL1":
                    CSCL1Counter--;
                    CSCL1.setText(String.valueOf(CSCL1Counter));
                    enableScoringDiagram('C');

                    if (CSCL1Counter == 0) {
                        CargoShipCargoLeft1.setColor(Color.rgb(221, 172, 107));
                        CSCL1.setTextColor(getAColor(Teleop.this, R.color.defaultdisabled));
                    }
                    break;
                case "CSCL2":
                    CSCL2Counter--;
                    CSCL2.setText(String.valueOf(CSCL2Counter));
                    enableScoringDiagram('C');

                    if (CSCL2Counter == 0) {
                        CargoShipCargoLeft2.setColor(Color.rgb(221, 172, 107));
                        CSCL2.setTextColor(getAColor(Teleop.this, R.color.defaultdisabled));
                    }
                    break;
                case "CSCL3":
                    CSCL3Counter--;
                    CSCL3.setText(String.valueOf(CSCL3Counter));
                    enableScoringDiagram('C');

                    if (CSCL3Counter == 0) {
                        CargoShipCargoLeft3.setColor(Color.rgb(221, 172, 107));
                        CSCL3.setTextColor(getAColor(Teleop.this, R.color.defaultdisabled));
                    }
                    break;
                case "CSCR1":
                    CSCR1Counter--;
                    CSCR1.setText(String.valueOf(CSCR1Counter));
                    enableScoringDiagram('C');

                    if (CSCR1Counter == 0) {
                        CargoShipCargoRight1.setColor(Color.rgb(221, 172, 107));
                        CSCR1.setTextColor(getAColor(Teleop.this, R.color.defaultdisabled));
                    }
                    break;
                case "CSCR2":
                    CSCR2Counter--;
                    CSCR2.setText(String.valueOf(CSCR2Counter));
                    enableScoringDiagram('C');

                    if (CSCR2Counter == 0) {
                        CargoShipCargoRight2.setColor(Color.rgb(221, 172, 107));
                        CSCR2.setTextColor(getAColor(Teleop.this, R.color.defaultdisabled));
                    }
                    break;
                case "CSCR3":
                    CSCR3Counter--;
                    CSCR3.setText(String.valueOf(CSCR3Counter));
                    enableScoringDiagram('C');

                    if (CSCR3Counter == 0) {
                        CargoShipCargoRight3.setColor(Color.rgb(221, 172, 107));
                        CSCR3.setTextColor(getAColor(Teleop.this, R.color.defaultdisabled));
                    }
                    break;
                case "CSPR1":
                    CSCR1Counter--;
                    CSPR1.setText(String.valueOf(CSPR1Counter));
                    enableScoringDiagram('P');

                    if (CSCR1Counter == 0) {
                        CargoShipCargoRight1.setColor(GenUtils.LIGHT_YELLOW);
                        CSCR1.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    break;
                case "CSPR2":
                    CSPR2Counter--;
                    CSPR2.setText(String.valueOf(CSPR2Counter));
                    enableScoringDiagram('P');

                    if (CSPR2Counter == 0) {
                        CargoShipPanelRight2.setColor(GenUtils.LIGHT_YELLOW);
                        CSPR2.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    break;
                case "CSPR3":
                    CSPR3Counter--;
                    CSPR3.setText(String.valueOf(CSPR3Counter));
                    enableScoringDiagram('P');

                    if (CSPR3Counter == 0) {
                        CargoShipPanelRight3.setColor(GenUtils.LIGHT_YELLOW);
                        CSPR3.setTextColor(getAColor(Teleop.this, R.color.textdefault));
                    }
                    break;
            }
        }
}
