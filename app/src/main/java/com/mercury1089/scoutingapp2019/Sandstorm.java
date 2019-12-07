package com.mercury1089.scoutingapp2019;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import at.markushi.ui.CircleButton;

public class Sandstorm extends MainActivity {
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
    private int totalPanels = 0;
    private int totalCargo = 0;
    private int droppedPanels = 0;
    private int droppedCargo = 0;
    private int missedCargo = 0;
    private int missedPanels = 0;

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

    //counter TextViews
    TextView PanelCounterText;
    TextView CargoCounterText;
    TextView DroppedCounterText;
    TextView MissedCounterText;

    //bootstrap buttons
    BootstrapButton SetupButton;
    BootstrapButton SandstormButton;
    BootstrapButton TeleopButton;
    BootstrapButton ClimbButton;
    BootstrapButton PanelButton;
    BootstrapButton CargoButton;
    BootstrapButton DroppedButton;
    BootstrapButton MissedButton;

    //text views
    TextView possessionTitle;
    TextView panelOrCargoDirections;
    TextView droppedDirection;

    TextView scoringTitle;
    TextView pOrCDirections;
    TextView missedDirections;

    //other variables
    private boolean isCargo = false;
    private boolean isPanel = false;
    private Timer timer;
    Button UndoButton;
    ConstraintLayout constraintLayout;
    String UNDO;
    Switch FellOverSwitch;
    Switch HABLineSwitch;
    int YELLOW = Color.rgb(248, 231, 28);
    int ORANGE = Color.rgb(255, 152, 0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandstorm);

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
        PanelCounterText = findViewById(R.id.PanelCounterText);
        CargoCounterText = findViewById(R.id.CargoCounterText);
        DroppedCounterText = findViewById(R.id.DroppedCounterText);
        MissedCounterText = findViewById(R.id.MissedCounterText);
        SetupButton = findViewById(R.id.SetupButton);
        SandstormButton = findViewById(R.id.SandstormButton);
        TeleopButton = findViewById(R.id.TeleopButton);
        ClimbButton = findViewById(R.id.ClimbButton);
        PanelButton = findViewById(R.id.SPanelButton);
        CargoButton = findViewById(R.id.SCargoButton);
        DroppedButton = findViewById(R.id.DroppedButton);
        MissedButton = findViewById(R.id.MissedButton);
        setupHashMap = new HashMap<>();
        scoreHashMap = new HashMap<>();
        timer = new Timer();
        UndoButton = findViewById(R.id.UndoButton);
        possessionTitle = findViewById(R.id.IDPossession);
        panelOrCargoDirections = findViewById(R.id.IDPanelOrCargoDirections);
        droppedDirection = findViewById(R.id.IDDroppedDirections);

        scoringTitle = findViewById(R.id.IDScoring);
        pOrCDirections = findViewById(R.id.IDScoringPOrCDirections);
        missedDirections = findViewById(R.id.IDScoringMissedDirections);

        constraintLayout = findViewById(R.id.layout);

        //make Sandstorm button look active
        selectedButtonColors(SandstormButton);

        //make other buttons look default
        //SetupButton.setEnabled(false);

        defaultButtonState(TeleopButton);
        defaultButtonState(ClimbButton);
        defaultButtonState(PanelButton);
        defaultButtonState(CargoButton);

        //setting counters to default
        PanelCounterText.setText("0");
        CargoCounterText.setText("0");
        DroppedCounterText.setText("0");
        MissedCounterText.setText("0");

        //disable scoring diagram
        disableScoringDiagram('A');

        UndoButton.setEnabled(false);

        final Serializable setupData = getIntent().getSerializableExtra("setupHashMap");
        setupHashMap = (HashMap<String, String>)setupData;

        setupHashMap.put("FellOver",String.valueOf(0));
        setupHashMap.put("HABLine",String.valueOf(0));




        TimerTask switchToTeleop = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
                        scoreHashMap.put("S,M,P,,,", missedPanels + "");
                        scoreHashMap.put("S,M,C,,,", missedCargo + "");
                        scoreHashMap.put("S,D,P,,,", droppedPanels + "");
                        scoreHashMap.put("S,D,C,,,", droppedCargo + "");



                        Intent intent = new Intent(Sandstorm.this, Teleop.class);
                        String POrC = " ";
                        if (isPanel)
                            POrC = "P";
                        else if (isCargo)
                            POrC = "C";
                        intent.putExtra("setupHashMap", setupHashMap);
                        intent.putExtra("scoreHashMap", scoreHashMap);
                        intent.putExtra("POrC", POrC);
                        if (FellOverSwitch.isChecked())
                            intent.putExtra("fellOver","True");
                        else
                            intent.putExtra("fellOver","");
                        startActivity(intent);
                    }
                });
            }
        };
        timer.schedule(switchToTeleop, 15000);

        Serializable scoreData = getIntent().getSerializableExtra("scoreHashMap");

        if (scoreData != null) {
            constraintLayout.setBackgroundColor(Color.rgb(91,24,24));
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
                                    CargoShipPanelLeft1.setColor(YELLOW);
                                    CSPL1.setTextColor(getResources().getColor(R.color.textdefault));
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
                                    CargoShipPanelLeft2.setColor(YELLOW);
                                    CSPL2.setTextColor(getResources().getColor(R.color.textdefault));
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
                                    CargoShipPanelLeft3.setColor(YELLOW);
                                    CSPL3.setTextColor(getResources().getColor(R.color.textdefault));
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
                                    CargoShipPanelRight1.setColor(YELLOW);
                                    CSPR1.setTextColor(getResources().getColor(R.color.textdefault));
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
                                    CargoShipPanelRight2.setColor(YELLOW);
                                    CSPR2.setTextColor(getResources().getColor(R.color.textdefault));
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
                                    CargoShipPanelRight3.setColor(YELLOW);
                                    CSPR3.setTextColor(getResources().getColor(R.color.textdefault));
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
                                    CargoShipPanelFront1.setColor(YELLOW);
                                    CSPF1.setTextColor(getResources().getColor(R.color.textdefault));
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
                                    CargoShipPanelFront2.setColor(YELLOW);
                                    CSPF2.setTextColor(getResources().getColor(R.color.textdefault));
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
                                    CargoShipCargoLeft1.setColor(ORANGE);
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
                                    CargoShipCargoLeft2.setColor(ORANGE);
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
                                    CargoShipCargoLeft3.setColor(ORANGE);
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
                                    CargoShipCargoRight1.setColor(ORANGE);
                                    //panel text set
                                } else {
                                    //panel disabled colors
                                    //panel text set
                                }
                            } else if (arr[4] == '2') {
                                hashVal = scoreHashMap.get("CSCL2");
                                CSPL2.setText(hashVal);
                                if (Integer.parseInt(hashVal) > 0) {
                                    CargoShipCargoRight2.setColor(ORANGE);
                                    //panel text set
                                } else {
                                    //panel disabled colors
                                    //panel text set
                                }
                            } else if (arr[4] == '3') {
                                hashVal = scoreHashMap.get("CSCL3");
                                CSPL3.setText(hashVal);
                                if (Integer.parseInt(hashVal) > 0) {
                                    CargoShipCargoRight3.setColor(ORANGE);
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

         PanelCounterText.bringToFront();
         CargoCounterText.bringToFront();
         DroppedCounterText.bringToFront();
         MissedCounterText.bringToFront();

        //setting buttons to default state
        defaultButtonState(SetupButton);
        selectedButtonColors(SandstormButton);
        defaultButtonState(TeleopButton);
        defaultButtonState(ClimbButton);

        CircleButton PanelCounterCircle = findViewById(R.id.PanelCounterCircle);
        CircleButton CargoCounterCircle = findViewById(R.id.CargoCounterCircle);
        final CircleButton DroppedCounterCircle = findViewById(R.id.DroppedCounterCircle);
        CircleButton MissedCounterCircle = findViewById(R.id.MissedCounterCircle);

        PanelCounterCircle.setEnabled(false);
        CargoCounterCircle.setEnabled(false);
        DroppedCounterCircle.setEnabled(false);
        MissedCounterCircle.setEnabled(false);

        possessionTitle.setTextColor(getResources().getColor(R.color.light));
        panelOrCargoDirections.setTextColor(getResources().getColor(R.color.light));
        droppedDirection.setTextColor(getResources().getColor(R.color.light));
        scoringTitle.setTextColor(getResources().getColor(R.color.light));
        pOrCDirections.setTextColor(getResources().getColor(R.color.light));
        missedDirections.setTextColor(getResources().getColor(R.color.light));

        MissedButton.setBackgroundColor(getResources().getColor(R.color.light));
        MissedButton.setTextColor(getResources().getColor(R.color.grey));
        MissedCounterText.setTextColor(getResources().getColor(R.color.light));
        MissedButton.setEnabled(true);
        MissedCounterText.setEnabled(true);

        DroppedButton.setBackgroundColor(getResources().getColor(R.color.light));
        DroppedButton.setTextColor(getResources().getColor(R.color.grey));
        DroppedCounterText.setTextColor(getResources().getColor(R.color.light));
        DroppedButton.setEnabled(true);
        DroppedCounterText.setEnabled(true);

        if (setupHashMap.get("StartingGameObject").equals("Panel")) {
            PanelButton.setEnabled(false);
            CargoButton.setEnabled(false);
            selectedButtonColors(PanelButton);
            defaultButtonState(CargoButton);
            totalPanels++;
            PanelCounterText.setText(String.valueOf(totalPanels));
            PanelCounterText.setEnabled(false);
            CargoCounterText.setEnabled(false);

            isPanel = true;
            isCargo = false;
            enableScoringDiagram('P');
        }
        else if (setupHashMap.get("StartingGameObject").equals("Cargo")) {
            PanelButton.setEnabled(false);
            CargoButton.setEnabled(false);
            selectedButtonColors(CargoButton);
            defaultButtonState(PanelButton);
            totalCargo++;
            CargoCounterText.setText(String.valueOf(totalCargo));
            PanelCounterText.setEnabled(false);
            CargoCounterText.setEnabled(false);
            isPanel = false;
            isCargo = true;
            enableScoringDiagram('C');
        }

        setupHashMap.put("HABLine",String.valueOf(0));
        HABLineSwitch = findViewById(R.id.CrossedHABLineSwitch);
        HABLineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UNDO = "HAB";
                    UndoButton.setEnabled(true);
                    setupHashMap.put("HABLine",String.valueOf(1));
                }
                else
                    setupHashMap.put("HABLine",String.valueOf(0));
            }
        });



        FellOverSwitch = findViewById(R.id.FellOverSwitch);
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
                    defaultButtonState(PanelButton);
                    defaultButtonState(CargoButton);
                    defaultButtonState(DroppedButton);
                    defaultButtonState(MissedButton);
                    HABLineSwitch.setEnabled(false);

                    PanelButton.setEnabled(false);
                    PanelCounterText.setEnabled(false);
                    CargoButton.setEnabled(false);
                    CargoCounterText.setEnabled(false);
                    DroppedButton.setEnabled(false);
                    DroppedCounterText.setEnabled(false);
                    MissedButton.setEnabled(false);
                    MissedCounterText.setEnabled(false);

                    disableScoringDiagram('A');


                    color = "grey";

                } else {
                    setupHashMap.put("FellOver",String.valueOf(0));
                    HABLineSwitch.setEnabled(true);
                    PanelButton.setEnabled(true);
                    PanelCounterText.setEnabled(true);
                    CargoButton.setEnabled(true);
                    CargoCounterText.setEnabled(true);
                    DroppedButton.setEnabled(false);
                    DroppedCounterText.setEnabled(false);
                    MissedButton.setEnabled(false);
                    MissedCounterText.setEnabled(false);
                    if (wasPanel && isPanel) {
                        UNDO = "Panel";
                        UndoButton.setEnabled(true);
                        selectedButtonColors(PanelButton);
                        defaultButtonState(CargoButton);
                        PanelCounterText.setText(String.valueOf(totalPanels));
                        PanelButton.setEnabled(false);
                        PanelCounterText.setEnabled(false);
                        CargoButton.setEnabled(false);
                        CargoCounterText.setEnabled(false);
                        DroppedButton.setEnabled(true);
                        DroppedCounterText.setEnabled(true);
                        MissedButton.setEnabled(true);
                        MissedCounterText.setEnabled(true);
                        enableScoringDiagram('P');
                        disableScoringDiagram('C');
                    }
                    if (wasCargo && isPanel) {
                        UNDO = "Cargo";
                        UndoButton.setEnabled(true);
                        selectedButtonColors(CargoButton);
                        defaultButtonState(PanelButton);
                        CargoCounterText.setText(String.valueOf(totalCargo));
                        PanelButton.setEnabled(false);
                        PanelCounterText.setEnabled(false);
                        CargoButton.setEnabled(false);
                        CargoCounterText.setEnabled(false);
                        DroppedButton.setEnabled(true);
                        DroppedCounterText.setEnabled(true);
                        MissedButton.setEnabled(true);
                        MissedCounterText.setEnabled(true);
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
        button.setBackgroundColor(getResources().getColor(R.color.light));
        button.setTextColor(getResources().getColor(R.color.grey));
        button.setRounded(true);
    }
    public void selectedButtonColors(BootstrapButton button) {
        button.setBackgroundColor(getResources().getColor(R.color.orange));
        button.setTextColor(getResources().getColor(R.color.light));
        button.setRounded(true);
    }
    private void setTextToColor (TextView textView, String color) {
        if (color.equals("grey"))
            textView.setTextColor(getResources().getColor(R.color.grey));
        else if (color.equals("white"))
            textView.setTextColor(getResources().getColor(R.color.light));
    }


    private void disableScoringDiagram (char c) {
        switch (c) {
            case 'A':
                isCargo = false;
                isPanel = false;
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
                    CargoShipPanelFront1.setColor(Color.rgb(248, 231, 28));
                    CSPF1.setTextColor(getResources().getColor(R.color.textdefault));
                }
                else {
                    CargoShipPanelFront1.setColor(Color.rgb(30, 30, 30));
                    CSPF1.setTextColor(getResources().getColor(R.color.light));
                }

                if (CSPF2Counter > 0) {
                    CargoShipPanelFront2.setColor(Color.rgb(248, 231, 28));
                    CSPF2.setTextColor(getResources().getColor(R.color.textdefault));
                }
                else {
                    CargoShipPanelFront2.setColor(Color.rgb(30, 30, 30));
                    CSPF2.setTextColor(getResources().getColor(R.color.light));
                }
                if (CSPL1Counter > 0) {
                    CargoShipPanelLeft1.setColor(Color.rgb(248, 231, 28));
                    CSPL1.setTextColor(getResources().getColor(R.color.textdefault));
                }
                else {
                    CargoShipPanelLeft1.setColor(Color.rgb(30, 30, 30));
                    CSPL1.setTextColor(getResources().getColor(R.color.light));
                }
                if (CSPL2Counter > 0) {
                    CargoShipPanelLeft2.setColor(Color.rgb(248, 231, 28));
                    CSPL2.setTextColor(getResources().getColor(R.color.textdefault));
                }
                else {
                    CargoShipPanelLeft2.setColor(Color.rgb(30, 30, 30));
                    CSPL2.setTextColor(getResources().getColor(R.color.light));
                }
                if (CSPL3Counter > 0) {
                    CargoShipPanelLeft3.setColor(Color.rgb(248, 231, 28));
                    CSPL3.setTextColor(getResources().getColor(R.color.textdefault));
                }
                else {
                    CargoShipPanelLeft3.setColor(Color.rgb(30, 30, 30));
                    CSPL3.setTextColor(getResources().getColor(R.color.textdefault));
                }
                if (CSPR1Counter > 0) {
                    CargoShipPanelRight1.setColor(Color.rgb(248, 231, 28));
                    CSPR1.setTextColor(getResources().getColor(R.color.textdefault));
                }
                else {
                    CargoShipPanelRight1.setColor(Color.rgb(30, 30, 30));
                    CSPR1.setTextColor(getResources().getColor(R.color.light));
                }
                if (CSPR2Counter > 0) {
                    CargoShipPanelRight2.setColor(Color.rgb(248, 231, 28));
                    CSPR2.setTextColor(getResources().getColor(R.color.textdefault));
                }
                else {
                    CargoShipPanelRight2.setColor(Color.rgb(30, 30, 30));
                    CSPR2.setTextColor(getResources().getColor(R.color.light));
                }
                if (CSPR3Counter > 0) {
                    CargoShipPanelRight3.setColor(Color.rgb(248, 231, 28));
                    CSPR3.setTextColor(getResources().getColor(R.color.textdefault));
                }
                else {
                    CargoShipPanelRight3.setColor(Color.rgb(30, 30, 30));
                    CSPR3.setTextColor(getResources().getColor(R.color.light));
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
                    CargoShipCargoFront1.setColor(Color.rgb(45, 192, 103));
                    CSCF1.setTextColor(getResources().getColor(R.color.light));
                }
                else {
                    CargoShipCargoFront1.setColor(Color.rgb(30, 30, 30));
                    CSCF1.setTextColor(getResources().getColor(R.color.light));
                }
                if (CSCF2Counter > 0) {
                    CargoShipCargoFront2.setColor(Color.rgb(45, 192, 103));
                    CSCF2.setTextColor(getResources().getColor(R.color.light));
                }
                else {
                    CargoShipCargoFront2.setColor(Color.rgb(30, 30, 30));
                    CSCF2.setTextColor(getResources().getColor(R.color.light));
                }
                if (CSCL1Counter > 0) {
                    CargoShipCargoLeft1.setColor(Color.rgb(45, 192, 103));
                    CSCL1.setTextColor(getResources().getColor(R.color.light));
                }
                else {
                    CargoShipCargoLeft1.setColor(Color.rgb(30, 30, 30));
                    CSCL1.setTextColor(getResources().getColor(R.color.light));
                }
                if (CSCL2Counter > 0) {
                    CargoShipCargoLeft2.setColor(Color.rgb(45, 192, 103));
                    CSCL2.setTextColor(getResources().getColor(R.color.light));
                }
                else {
                    CargoShipCargoLeft2.setColor(Color.rgb(30, 30, 30));
                    CSCL2.setTextColor(getResources().getColor(R.color.light));
                }
                if (CSCL3Counter > 0) {
                    CargoShipCargoLeft3.setColor(Color.rgb(45, 192, 103));
                    CSCL3.setTextColor(getResources().getColor(R.color.light));
                }
                else {
                    CargoShipCargoLeft3.setColor(Color.rgb(30, 30, 30));
                    CSCL3.setTextColor(getResources().getColor(R.color.light));
                }
                if (CSCR1Counter > 0) {
                    CargoShipCargoRight1.setColor(Color.rgb(45, 192, 103));
                    CSCR1.setTextColor(getResources().getColor(R.color.light));
                }
                else {
                    CargoShipCargoRight1.setColor(Color.rgb(30, 30, 30));
                    CSCR1.setTextColor(getResources().getColor(R.color.light));
                }
                if (CSCR2Counter > 0) {
                    CargoShipCargoRight2.setColor(Color.rgb(45, 192, 103));
                    CSCR2.setTextColor(getResources().getColor(R.color.light));
                }
                else {
                    CargoShipCargoRight2.setColor(Color.rgb(30, 30, 30));
                    CSCR2.setTextColor(getResources().getColor(R.color.light));
                }
                if (CSCR3Counter > 0) {
                    CargoShipCargoRight3.setColor(Color.rgb(45, 192, 103));
                    CSCR3.setTextColor(getResources().getColor(R.color.light));
                }
                else {
                    CargoShipCargoRight3.setColor(Color.rgb(30, 30, 30));
                    CSCR3.setTextColor(getResources().getColor(R.color.light));
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
                    CargoShipPanelFront1.setColor(Color.rgb(248, 231, 28));
                    CSPF1.setTextColor(getResources().getColor(R.color.textdefault));
                }
                else {
                    CargoShipPanelFront1.setColor(Color.rgb(255, 255, 217));
                    CSPF1.setTextColor(getResources().getColor(R.color.textdefault));
                }
                if (CSPF2Counter > 0) {
                    CargoShipPanelFront2.setColor(Color.rgb(248, 231, 28));
                    CSPF2.setTextColor(getResources().getColor(R.color.textdefault));
                }
                else {
                    CargoShipPanelFront2.setColor(Color.rgb(255, 255, 217));
                    CSPF2.setTextColor(getResources().getColor(R.color.textdefault));
                }
                if (CSPL1Counter > 0) {
                    CargoShipPanelLeft1.setColor(Color.rgb(248, 231, 28));
                    CSPL1.setTextColor(getResources().getColor(R.color.textdefault));
                }
                else {
                    CargoShipPanelLeft1.setColor(Color.rgb(255, 255, 217));
                    CSPL1.setTextColor(getResources().getColor(R.color.textdefault));
                }
                if (CSPL2Counter > 0) {
                    CargoShipPanelLeft2.setColor(Color.rgb(248, 231, 28));
                    CSPL2.setTextColor(getResources().getColor(R.color.textdefault));
                }
                else {
                    CargoShipPanelLeft2.setColor(Color.rgb(255, 255, 217));
                    CSPL2.setTextColor(getResources().getColor(R.color.textdefault));
                }
                if (CSPL3Counter > 0) {
                    CargoShipPanelLeft3.setColor(Color.rgb(248, 231, 28));
                    CSPL3.setTextColor(getResources().getColor(R.color.textdefault));
                }
                else {
                    CargoShipPanelLeft3.setColor(Color.rgb(255, 255, 217));
                    CSPL3.setTextColor(getResources().getColor(R.color.textdefault));
                }
                if (CSPR1Counter > 0) {
                    CargoShipPanelRight1.setColor(Color.rgb(248, 231, 28));
                    CSPR1.setTextColor(getResources().getColor(R.color.textdefault));
                }
                else {
                    CargoShipPanelRight1.setColor(Color.rgb(255, 255, 217));
                    CSPR1.setTextColor(getResources().getColor(R.color.textdefault));
                }
                if (CSPR2Counter > 0) {
                    CargoShipPanelRight2.setColor(Color.rgb(248, 231, 28));
                    CSPR2.setTextColor(getResources().getColor(R.color.textdefault));
                }
                else {
                    CargoShipPanelRight2.setColor(Color.rgb(255, 255, 217));
                    CSPR2.setTextColor(getResources().getColor(R.color.textdefault));
                }
                if (CSPR3Counter > 0) {
                    CargoShipPanelRight3.setColor(Color.rgb(248, 231, 28));
                    CSPR3.setTextColor(getResources().getColor(R.color.textdefault));
                }
                else {
                    CargoShipPanelRight3.setColor(Color.rgb(255, 255, 217));
                    CSPR3.setTextColor(getResources().getColor(R.color.textdefault));
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
                    CargoShipCargoFront1.setColor(Color.rgb(255, 152, 0));
                    CSCF1.setTextColor(getResources().getColor(R.color.light));
                }
                else {
                    CargoShipCargoFront1.setColor(Color.rgb(221, 172, 107));
                    CSCF1.setTextColor(getResources().getColor(R.color.defaultdisabled));
                }
                if (CSCF2Counter > 0) {
                    CargoShipCargoFront2.setColor(Color.rgb(255, 152, 0));
                    CSCF2.setTextColor(getResources().getColor(R.color.light));
                }
                else {
                    CargoShipCargoFront2.setColor(Color.rgb(221, 172, 107));
                    CSCF2.setTextColor(getResources().getColor(R.color.defaultdisabled));
                }
                if (CSCL1Counter > 0) {
                    CargoShipCargoLeft1.setColor(Color.rgb(255, 152, 0));
                    CSCL1.setTextColor(getResources().getColor(R.color.light));
                }
                else {
                    CargoShipCargoLeft1.setColor(Color.rgb(221, 172, 107));
                    CSCL1.setTextColor(getResources().getColor(R.color.defaultdisabled));
                }
                if (CSCL2Counter > 0) {
                    CargoShipCargoLeft2.setColor(Color.rgb(255, 152, 0));
                    CSCL2.setTextColor(getResources().getColor(R.color.light));
                }
                else {
                    CargoShipCargoLeft2.setColor(Color.rgb(221, 172, 107));
                    CSCL2.setTextColor(getResources().getColor(R.color.defaultdisabled));
                }
                if (CSCL3Counter > 0) {
                    CargoShipCargoLeft3.setColor(Color.rgb(255, 152, 0));
                    CSCL3.setTextColor(getResources().getColor(R.color.light));
                }
                else {
                    CargoShipCargoLeft3.setColor(Color.rgb(221, 172, 107));
                    CSCL3.setTextColor(getResources().getColor(R.color.defaultdisabled));
                }
                if (CSCR1Counter > 0) {
                    CargoShipCargoRight1.setColor(Color.rgb(255, 152, 0));
                    CSCR1.setTextColor(getResources().getColor(R.color.light));
                }
                else {
                    CargoShipCargoRight1.setColor(Color.rgb(221, 172, 107));
                    CSCR1.setTextColor(getResources().getColor(R.color.defaultdisabled));
                }
                if (CSCR2Counter > 0) {
                    CargoShipCargoRight2.setColor(Color.rgb(255, 152, 0));
                    CSCR2.setTextColor(getResources().getColor(R.color.light));
                }
                else {
                    CargoShipCargoRight2.setColor(Color.rgb(221, 172, 107));
                    CSCR2.setTextColor(getResources().getColor(R.color.defaultdisabled));
                }
                if (CSCR3Counter > 0) {
                    CargoShipCargoRight3.setColor(Color.rgb(255, 152, 0));
                    CSCR3.setTextColor(getResources().getColor(R.color.light));
                }
                else {
                    CargoShipCargoRight3.setColor(Color.rgb(221, 172, 107));
                    CSCR3.setTextColor(getResources().getColor(R.color.defaultdisabled));
                }
        }
    }



    //click methods
    public void setupClick (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("setupHashMap", setupHashMap);
        timer.cancel();
        startActivity(intent);
    }
    public void teleopClick (View view) {
        Intent intent = new Intent(this, Teleop.class);

        if (isPanel)
            intent.putExtra("POrC", "P");
        else if (isCargo)
            intent.putExtra("POrC", "C");

        if (FellOverSwitch.isChecked())
            intent.putExtra("fellOver","True");
        else
            intent.putExtra("fellOver","");
        intent.putExtra("setupHashMap", setupHashMap);
        intent.putExtra("scoreHashMap", scoreHashMap);
        timer.cancel();
        startActivity(intent);
    }

    public void climbClick (View view) {
        Intent intent = new Intent(this, Climb.class);
        intent.putExtra("setupHashMap", setupHashMap);
        intent.putExtra("scoreHashMap", scoreHashMap);
        timer.cancel();
        startActivity(intent);
    }

    //called when a hatch panel is scored
    public void panelCounterClick (View view) {
        UNDO = "Panel";
        UndoButton.setEnabled(true);
        selectedButtonColors(PanelButton);
        defaultButtonState(CargoButton);
        totalPanels++;
        PanelCounterText.setText(String.valueOf(totalPanels));
        PanelButton.setEnabled(false);
        PanelCounterText.setEnabled(false);
        CargoButton.setEnabled(false);
        CargoCounterText.setEnabled(false);
        DroppedButton.setEnabled(true);
        DroppedCounterText.setEnabled(true);
        MissedButton.setEnabled(true);
        MissedCounterText.setEnabled(true);
        isPanel = true;
        isCargo = false;
        enableScoringDiagram('P');
        disableScoringDiagram('C');
    }

    //called when a cargo is scored
    public void cargoCounterClick (View view) {
        UNDO = "Cargo";
        UndoButton.setEnabled(true);
        selectedButtonColors(CargoButton);
        defaultButtonState(PanelButton);
        totalCargo++;
        CargoCounterText.setText(String.valueOf(totalCargo));
        PanelButton.setEnabled(false);
        PanelCounterText.setEnabled(false);
        CargoButton.setEnabled(false);
        CargoCounterText.setEnabled(false);
        DroppedButton.setEnabled(true);
        DroppedCounterText.setEnabled(true);
        MissedButton.setEnabled(true);
        MissedCounterText.setEnabled(true);
        isPanel = false;
        isCargo = true;
        enableScoringDiagram('C');
        disableScoringDiagram('P');
    }

    public void droppedClick (View view) {
        UNDO = "Dropped";
        UndoButton.setEnabled(true);

        selectedButtonColors(DroppedButton);
        TimerTask changeToDefault = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        defaultButtonState(DroppedButton);
                    }
                });
            }
        };
        timer.schedule(changeToDefault, 500);

        if (isPanel) {
            droppedPanels++;
            defaultButtonState(PanelButton);
        }
        if (isCargo) {
            droppedCargo++;
            defaultButtonState(CargoButton);
        }

        int totalDropped = droppedPanels+droppedCargo;
        DroppedCounterText.setText(String.valueOf(totalDropped));
        DroppedButton.setBackgroundColor(getResources().getColor(R.color.orange));

        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        disableScoringDiagram('A');
    }
    public void missedClick (View view) {
        UNDO = "Missed";
        UndoButton.setEnabled(true);

        selectedButtonColors(MissedButton);
        TimerTask changeToDefault = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        defaultButtonState(MissedButton);
                    }
                });
            }
        };
        timer.schedule(changeToDefault, 500);

        if (isPanel){
            missedPanels++;
            defaultButtonState(PanelButton);
        }
        if (isCargo){
            missedCargo++;
            defaultButtonState(CargoButton);
        }
        int totalMissed = missedPanels+missedCargo;

        MissedButton.setBackgroundColor(getResources().getColor(R.color.orange));
        MissedCounterText.setText(String.valueOf(totalMissed));

        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        disableScoringDiagram('A');
    }

    //cargo ship onClicks
    public void CSPF1CounterClick (View view) {
        CSPF1Counter++;
        CargoShipPanelFront1.setColor(Color.rgb(248, 231, 28));
        scoreHashMap.put(CSPF1.getTag().toString(), String.valueOf(CSPF1Counter));
        UNDO = "CSPF1";
        CSPF1.setText(String.valueOf(CSPF1Counter));
        CSPF1.setTextColor(getResources().getColor(R.color.textdefault));
        disableScoringDiagram('A');
        defaultButtonState(PanelButton);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        UndoButton.setEnabled(true);
        defaultButtonState(CargoButton);
        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        HABLineSwitch.setChecked(true);
        isPanel = false;
        isCargo = false;
    }
    public void CSPF2CounterClick (View view) {
        CSPF2Counter++;
        CargoShipPanelFront2.setColor(Color.rgb(248, 231, 28));
        scoreHashMap.put(CSPF2.getTag().toString(), String.valueOf(CSPF2Counter));
        UNDO = "CSPF2";
        CSPF2.setText(String.valueOf(CSPF2Counter));
        CSPF2.setTextColor(getResources().getColor(R.color.textdefault));
        disableScoringDiagram('A');
        defaultButtonState(PanelButton);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        UndoButton.setEnabled(true);
        defaultButtonState(CargoButton);
        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        HABLineSwitch.setChecked(true);
        isPanel = false;
        isCargo = false;
    }
    public void CSCF1CounterClick (View view) {
        CSCF1Counter++;
        CargoShipCargoFront1.setColor(Color.argb(100, 255, 152, 0));
        scoreHashMap.put(CSCF1.getTag().toString(), String.valueOf(CSCF1Counter));
        UNDO = "CSCF1";
        CSCF1.setText(String.valueOf(CSCF1Counter));
        disableScoringDiagram('A');
        defaultButtonState(PanelButton);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        UndoButton.setEnabled(true);
        defaultButtonState(CargoButton);
        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        HABLineSwitch.setChecked(true);
        isPanel = false;
        isCargo = false;
    }
    public void CSCF2CounterClick (View view) {
        CSCF2Counter++;
        scoreHashMap.put(CSCF2.getTag().toString(), String.valueOf(CSCF2Counter));
        CargoShipCargoFront2.setColor(Color.argb(100, 255, 152, 0));
        UNDO = "CSCF2";
        CSCF2.setText(String.valueOf(CSCF2Counter));
        disableScoringDiagram('A');
        defaultButtonState(PanelButton);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        UndoButton.setEnabled(true);
        defaultButtonState(CargoButton);
        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        HABLineSwitch.setChecked(true);
        isPanel = false;
        isCargo = false;
    }
    public void CSPL1CounterClick (View view) {
        CSPL1Counter++;
        scoreHashMap.put(CSPL1.getTag().toString(), String.valueOf(CSPL1Counter));
        CargoShipPanelLeft1.setColor(Color.rgb(248, 231, 28));
        UNDO = "CSPL1";
        CSPL1.setText(String.valueOf(CSPL1Counter));
        CSPL1.setTextColor(getResources().getColor(R.color.textdefault));
        disableScoringDiagram('A');
        defaultButtonState(PanelButton);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        UndoButton.setEnabled(true);
        defaultButtonState(CargoButton);
        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        HABLineSwitch.setChecked(true);
        isPanel = false;
        isCargo = false;
    }
    public void CSPL2CounterClick (View view) {
        CSPL2Counter++;
        scoreHashMap.put(CSPL2.getTag().toString(), String.valueOf(CSPL2Counter));
        CargoShipPanelLeft2.setColor(Color.rgb(248, 231, 28));
        UNDO = "CSPL2";
        CSPL2.setText(String.valueOf(CSPL2Counter));
        CSPL2.setTextColor(getResources().getColor(R.color.textdefault));
        disableScoringDiagram('A');
        defaultButtonState(PanelButton);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        UndoButton.setEnabled(true);
        defaultButtonState(CargoButton);
        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        HABLineSwitch.setChecked(true);
        isPanel = false;
        isCargo = false;
    }
    public void CSPL3CounterClick (View view) {
        CSPL3Counter++;
        scoreHashMap.put(CSPL3.getTag().toString(), String.valueOf(CSPL3Counter));
        CargoShipPanelLeft3.setColor(Color.rgb(248, 231, 28));
        UNDO = "CSPL3";
        CSPL3.setText(String.valueOf(CSPL3Counter));
        CSPL3.setTextColor(getResources().getColor(R.color.textdefault));
        disableScoringDiagram('A');
        defaultButtonState(PanelButton);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        UndoButton.setEnabled(true);
        defaultButtonState(CargoButton);
        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        HABLineSwitch.setChecked(true);
        isPanel = false;
        isCargo = false;
    }
    public void CSCL1CounterClick (View view) {
        CSCL1Counter++;
        scoreHashMap.put(CSCL1.getTag().toString(), String.valueOf(CSCL1Counter));
        CargoShipCargoLeft1.setColor(Color.argb(100, 255, 152, 0));
        UNDO = "CSCL1";
        CSCL1.setText(String.valueOf(CSCL1Counter));
        disableScoringDiagram('A');
        defaultButtonState(PanelButton);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        UndoButton.setEnabled(true);
        defaultButtonState(CargoButton);
        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        HABLineSwitch.setChecked(true);
        isPanel = false;
        isCargo = false;
    }
    public void CSCL2CounterClick (View view) {
        CSCL2Counter++;
        scoreHashMap.put(CSCL2.getTag().toString(), String.valueOf(CSCL2Counter));
        CargoShipCargoLeft2.setColor(Color.argb(100, 255, 152, 0));
        UNDO = "CSCL2";
        CSCL2.setText(String.valueOf(CSCL2Counter));
        disableScoringDiagram('A');
        defaultButtonState(PanelButton);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        UndoButton.setEnabled(true);
        defaultButtonState(CargoButton);
        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        HABLineSwitch.setChecked(true);
        isPanel = false;
        isCargo = false;
    }
    public void CSCL3CounterClick (View view) {
        CSCL3Counter++;
        scoreHashMap.put(CSCL3.getTag().toString(), String.valueOf(CSCL3Counter));
        CargoShipCargoLeft3.setColor(Color.argb(100, 255, 152, 0));
        UNDO = "CSCL3";
        CSCL3.setText(String.valueOf(CSCL3Counter));
        disableScoringDiagram('A');
        defaultButtonState(PanelButton);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        UndoButton.setEnabled(true);
        defaultButtonState(CargoButton);
        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        HABLineSwitch.setChecked(true);
        isPanel = false;
        isCargo = false;
    }
    public void CSCR1CounterClick (View view) {
        CSCR1Counter++;
        scoreHashMap.put(CSCR1.getTag().toString(), String.valueOf(CSCR1Counter));
        CargoShipCargoRight1.setColor(Color.argb(100, 255, 152, 0));
        UNDO = "CSCR1";
        CSCR1.setText(String.valueOf(CSCR1Counter));
        disableScoringDiagram('A');
        defaultButtonState(PanelButton);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        UndoButton.setEnabled(true);
        defaultButtonState(CargoButton);
        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        HABLineSwitch.setChecked(true);
        isPanel = false;
        isCargo = false;
    }
    public void CSCR2CounterClick (View view) {
        CSCR2Counter++;
        scoreHashMap.put(CSCR2.getTag().toString(), String.valueOf(CSCR2Counter));
        CargoShipCargoRight2.setColor(Color.argb(100, 255, 152, 0));
        UNDO = "CSCR2";
        CSCR2.setText(String.valueOf(CSCR2Counter));
        disableScoringDiagram('A');
        defaultButtonState(PanelButton);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        UndoButton.setEnabled(true);
        defaultButtonState(CargoButton);
        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        HABLineSwitch.setChecked(true);
        isPanel = false;
        isCargo = false;
    }
    public void CSCR3CounterClick (View view) {
        CSCR3Counter++;
        scoreHashMap.put(CSCR3.getTag().toString(), String.valueOf(CSCR3Counter));
        CargoShipCargoRight3.setColor(Color.argb(100, 255, 152, 0));
        UNDO = "CSCR3";
        CSCR3.setText(String.valueOf(CSCR3Counter));
        disableScoringDiagram('A');
        defaultButtonState(PanelButton);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        UndoButton.setEnabled(true);
        defaultButtonState(CargoButton);
        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        HABLineSwitch.setChecked(true);
        isPanel = false;
        isCargo = false;
    }
    public void CSPR1CounterClick (View view) {
        CSPR1Counter++;
        scoreHashMap.put(CSPR1.getTag().toString(), String.valueOf(CSPR1Counter));
        CargoShipPanelRight1.setColor(Color.rgb(248, 231, 28));
        UNDO = "CSPR1";
        CSPR1.setText(String.valueOf(CSPR1Counter));
        CSPR1.setTextColor(getResources().getColor(R.color.textdefault));
        disableScoringDiagram('A');
        defaultButtonState(PanelButton);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        UndoButton.setEnabled(true);
        defaultButtonState(CargoButton);
        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        HABLineSwitch.setChecked(true);
        isPanel = false;
        isCargo = false;
    }
    public void CSPR2CounterClick (View view) {
        CSPR2Counter++;
        scoreHashMap.put(CSPR2.getTag().toString(), String.valueOf(CSPR2Counter));
        CargoShipPanelRight2.setColor(Color.rgb(248, 231, 28));
        UNDO = "CSPR2";
        CSPR2.setText(String.valueOf(CSPR2Counter));
        CSPR2.setTextColor(getResources().getColor(R.color.textdefault));
        disableScoringDiagram('A');
        defaultButtonState(PanelButton);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        UndoButton.setEnabled(true);
        defaultButtonState(CargoButton);
        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        HABLineSwitch.setChecked(true);
        isPanel = false;
        isCargo = false;
    }
    public void CSPR3CounterClick (View view) {
        CSPR3Counter++;
        scoreHashMap.put(CSPR3.getTag().toString(), String.valueOf(CSPR3Counter));
        CargoShipPanelRight3.setColor(Color.rgb(248, 231, 28));
        UNDO = "CSPR3";
        CSPR3.setText(String.valueOf(CSPR3Counter));
        CSPR3.setTextColor(getResources().getColor(R.color.textdefault));
        disableScoringDiagram('A');
        defaultButtonState(PanelButton);
        MissedButton.setEnabled(false);
        MissedCounterText.setEnabled(false);
        DroppedButton.setEnabled(false);
        DroppedCounterText.setEnabled(false);
        UndoButton.setEnabled(true);
        defaultButtonState(CargoButton);
        PanelButton.setEnabled(true);
        PanelCounterText.setEnabled(true);
        CargoButton.setEnabled(true);
        CargoCounterText.setEnabled(true);
        HABLineSwitch.setChecked(true);
        isPanel = false;
        isCargo = false;
    }

    //undo button
    public void UndoClick (View view) {
        UndoButton.setEnabled(false);
        switch (UNDO) {
            case "Panel":
                defaultButtonState(PanelButton);
                defaultButtonState(CargoButton);
                totalPanels--;
                PanelCounterText.setText(String.valueOf(totalPanels));
                PanelButton.setEnabled(true);
                PanelCounterText.setEnabled(true);
                CargoButton.setEnabled(true);
                CargoCounterText.setEnabled(true);
                DroppedButton.setEnabled(false);
                DroppedCounterText.setEnabled(false);
                MissedButton.setEnabled(false);
                MissedCounterText.setEnabled(false);
                isPanel = false;
                isCargo = false;
                disableScoringDiagram('A');
                break;
            case "Cargo":
                defaultButtonState(CargoButton);
                defaultButtonState(PanelButton);
                totalCargo--;
                CargoCounterText.setText(String.valueOf(totalCargo));
                PanelButton.setEnabled(true);
                PanelCounterText.setEnabled(true);
                CargoButton.setEnabled(true);
                CargoCounterText.setEnabled(true);
                DroppedButton.setEnabled(false);
                DroppedCounterText.setEnabled(false);
                MissedButton.setEnabled(false);
                MissedCounterText.setEnabled(false);
                isPanel = false;
                isCargo = false;
                disableScoringDiagram('A');
                break;
            case "Dropped":
                if (isPanel) {
                    droppedPanels--;
                    selectedButtonColors(PanelButton);
                    enableScoringDiagram('P');
                }
                if (isCargo) {
                    droppedCargo--;
                    selectedButtonColors(CargoButton);
                    enableScoringDiagram('C');
                }
                DroppedCounterText.setText(String.valueOf(droppedPanels + droppedCargo));

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                break;
            case "Missed":
                if (isPanel) {
                    missedPanels--;
                    selectedButtonColors(PanelButton);
                    enableScoringDiagram('P');
                }
                if (isCargo) {
                    missedCargo--;
                    selectedButtonColors(CargoButton);
                    enableScoringDiagram('C');
                }

                MissedCounterText.setText(String.valueOf(missedPanels + missedCargo));

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                break;
            case "FellOver":
                setupHashMap.put("FellOver", String.valueOf(0));
                HABLineSwitch.setEnabled(true);
                PanelButton.setEnabled(true);
                PanelCounterText.setEnabled(true);
                CargoButton.setEnabled(true);
                CargoCounterText.setEnabled(true);
                DroppedButton.setEnabled(false);
                DroppedCounterText.setEnabled(false);
                MissedButton.setEnabled(false);
                MissedCounterText.setEnabled(false);
                setTextToColor(possessionTitle, "white");
                setTextToColor(panelOrCargoDirections, "white");
                setTextToColor(droppedDirection, "white");
                setTextToColor(scoringTitle, "white");
                setTextToColor(pOrCDirections, "white");
                setTextToColor(missedDirections, "white");
                FellOverSwitch.setChecked(!FellOverSwitch.isChecked());
                break;
            case "HAB":
                setupHashMap.put("HABLine", String.valueOf(0));
                HABLineSwitch.setChecked(!HABLineSwitch.isChecked());
                break;
            //undo for circle buttons aka locations
            case "CSPF1":
                selectedButtonColors(PanelButton);
                defaultButtonState(CargoButton);

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                CSPF1Counter--;
                CSPF1.setText(String.valueOf(CSPF1Counter));
                enableScoringDiagram('P');

                if (CSPF1Counter == 0) {
                    CargoShipPanelFront1.setColor(Color.rgb(255, 255, 217));
                    CSPF1.setTextColor(getResources().getColor(R.color.textdefault));
                }
                break;
            case "CSPF2":
                selectedButtonColors(PanelButton);
                defaultButtonState(CargoButton);

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                CSPF2Counter--;
                CSPF2.setText(String.valueOf(CSPF2Counter));
                enableScoringDiagram('P');

                if (CSPF2Counter == 0) {
                    CargoShipPanelFront2.setColor(Color.rgb(255, 255, 217));
                    CSPF2.setTextColor(getResources().getColor(R.color.textdefault));
                }
                break;
            case "CSCF1":
                selectedButtonColors(CargoButton);
                defaultButtonState(PanelButton);

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                CSCF1Counter--;
                CSCF1.setText(String.valueOf(CSCF1Counter));
                enableScoringDiagram('C');

                if (CSCF1Counter == 0) {
                    CargoShipCargoFront1.setColor(Color.rgb(221, 172, 107));
                    CSCF1.setTextColor(getResources().getColor(R.color.textdefault));
                }
                break;
            case "CSCF2":
                selectedButtonColors(CargoButton);
                defaultButtonState(PanelButton);

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                CSCF2Counter--;
                CSCF2.setText(String.valueOf(CSCF2Counter));
                enableScoringDiagram('C');

                if (CSCF2Counter == 0) {
                    CargoShipCargoFront2.setColor(Color.rgb(221, 172, 107));
                    CSCF2.setTextColor(getResources().getColor(R.color.textdefault));
                }
                break;
            case "CSPL1":
                selectedButtonColors(PanelButton);
                defaultButtonState(CargoButton);

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                CSPL1Counter--;
                CSPL1.setText(String.valueOf(CSPL1Counter));
                enableScoringDiagram('P');

                if (CSPL1Counter == 0) {
                    CargoShipPanelLeft1.setColor(Color.rgb(255, 255, 217));
                    CSPL1.setTextColor(getResources().getColor(R.color.textdefault));
                }
                break;
            case "CSPL2":
                selectedButtonColors(PanelButton);
                defaultButtonState(CargoButton);

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                CSPL2Counter--;
                CSPL2.setText(String.valueOf(CSPL2Counter));
                enableScoringDiagram('P');

                if (CSPL2Counter == 0) {
                    CargoShipPanelLeft2.setColor(Color.rgb(255, 255, 217));
                    CSPL2.setTextColor(getResources().getColor(R.color.textdefault));
                }
                break;
            case "CSPL3":
                selectedButtonColors(PanelButton);
                defaultButtonState(CargoButton);

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                CSPL3Counter--;
                CSPL3.setText(String.valueOf(CSPL3Counter));
                enableScoringDiagram('P');

                if (CSPL3Counter == 0) {
                    CargoShipPanelLeft3.setColor(Color.rgb(255, 255, 217));
                    CSPL3.setTextColor(getResources().getColor(R.color.textdefault));
                }
                break;
            case "CSCL1":
                selectedButtonColors(CargoButton);
                defaultButtonState(PanelButton);

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                CSCL1Counter--;
                CSCL1.setText(String.valueOf(CSCL1Counter));
                enableScoringDiagram('C');

                if (CSCL1Counter == 0) {
                    CargoShipCargoLeft1.setColor(Color.rgb(221, 172, 107));
                    CSCL1.setTextColor(getResources().getColor(R.color.defaultdisabled));
                }
                break;
            case "CSCL2":
                selectedButtonColors(CargoButton);
                defaultButtonState(PanelButton);

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                CSCL2Counter--;
                CSCL2.setText(String.valueOf(CSCL2Counter));
                enableScoringDiagram('C');

                if (CSCL2Counter == 0) {
                    CargoShipCargoLeft2.setColor(Color.rgb(221, 172, 107));
                    CSCL2.setTextColor(getResources().getColor(R.color.defaultdisabled));
                }
                break;
            case "CSCL3":
                selectedButtonColors(CargoButton);
                defaultButtonState(PanelButton);

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                CSCL3Counter--;
                CSCL3.setText(String.valueOf(CSCL3Counter));
                enableScoringDiagram('C');

                if (CSCL3Counter == 0) {
                    CargoShipCargoLeft3.setColor(Color.rgb(221, 172, 107));
                    CSCL3.setTextColor(getResources().getColor(R.color.defaultdisabled));
                }
                break;
            case "CSCR1":
                selectedButtonColors(CargoButton);
                defaultButtonState(PanelButton);

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                CSCR1Counter--;
                CSCR1.setText(String.valueOf(CSCR1Counter));
                enableScoringDiagram('C');

                if (CSCR1Counter == 0) {
                    CargoShipCargoRight1.setColor(Color.rgb(221, 172, 107));
                    CSCR1.setTextColor(getResources().getColor(R.color.defaultdisabled));
                }
                break;
            case "CSCR2":
                selectedButtonColors(CargoButton);
                defaultButtonState(PanelButton);

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                CSCR2Counter--;
                CSCR2.setText(String.valueOf(CSCR2Counter));
                enableScoringDiagram('C');

                if (CSCR2Counter == 0) {
                    CargoShipCargoRight2.setColor(Color.rgb(221, 172, 107));
                    CSCR2.setTextColor(getResources().getColor(R.color.defaultdisabled));
                }
                break;
            case "CSCR3":
                selectedButtonColors(CargoButton);
                defaultButtonState(PanelButton);

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                CSCR3Counter--;
                CSCR3.setText(String.valueOf(CSCR3Counter));
                enableScoringDiagram('C');

                if (CSCR3Counter == 0) {
                    CargoShipCargoRight3.setColor(Color.rgb(221, 172, 107));
                    CSCR3.setTextColor(getResources().getColor(R.color.defaultdisabled));
                }
                break;
            case "CSPR1":
                selectedButtonColors(PanelButton);
                defaultButtonState(CargoButton);

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                CSCR1Counter--;
                CSPR1.setText(String.valueOf(CSPR1Counter));
                enableScoringDiagram('P');

                if (CSCR1Counter == 0) {
                    CargoShipCargoRight1.setColor(Color.rgb(255, 255, 217));
                    CSCR1.setTextColor(getResources().getColor(R.color.textdefault));
                }
                break;
            case "CSPR2":
                selectedButtonColors(PanelButton);
                defaultButtonState(CargoButton);

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                CSPR2Counter--;
                CSPR2.setText(String.valueOf(CSPR2Counter));
                enableScoringDiagram('P');

                if (CSPR2Counter == 0) {
                    CargoShipPanelRight2.setColor(Color.rgb(255, 255, 217));
                    CSPR2.setTextColor(getResources().getColor(R.color.textdefault));
                }
                break;
            case "CSPR3":
                selectedButtonColors(PanelButton);
                defaultButtonState(CargoButton);

                PanelButton.setEnabled(false);
                PanelCounterText.setEnabled(false);
                CargoButton.setEnabled(false);
                CargoCounterText.setEnabled(false);
                DroppedButton.setEnabled(true);
                DroppedCounterText.setEnabled(true);
                MissedButton.setEnabled(true);
                MissedCounterText.setEnabled(true);
                CSPR3Counter--;
                CSPR3.setText(String.valueOf(CSPR3Counter));
                enableScoringDiagram('P');

                if (CSPR3Counter == 0) {
                    CargoShipPanelRight3.setColor(Color.rgb(255, 255, 217));
                    CSPR3.setTextColor(getResources().getColor(R.color.textdefault));
                }
                break;
        }
    }
}
