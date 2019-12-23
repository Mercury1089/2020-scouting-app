package com.mercury1089.scoutingapp2019;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mercury1089.scoutingapp2019.utils.GenUtils;
import com.mercury1089.scoutingapp2019.utils.LocationGroup;
import com.mercury1089.scoutingapp2019.utils.LocationGroupList;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import at.markushi.ui.CircleButton;



public class Sandstorm extends MainActivity {
    //CARGO SHIP
    //panel location buttons
    private CircleButton CargoShipPanelFront1;
    private CircleButton CargoShipPanelFront2;
    private CircleButton CargoShipPanelLeft1;
    private CircleButton CargoShipPanelLeft2;
    private CircleButton CargoShipPanelLeft3;
    private CircleButton CargoShipPanelRight1;
    private CircleButton CargoShipPanelRight2;
    private CircleButton CargoShipPanelRight3;

    //cargo location buttons
    private CircleButton CargoShipCargoFront1;
    private CircleButton CargoShipCargoFront2;
    private CircleButton CargoShipCargoLeft1;
    private CircleButton CargoShipCargoLeft2;
    private CircleButton CargoShipCargoLeft3;
    private CircleButton CargoShipCargoRight1;
    private CircleButton CargoShipCargoRight2;
    private CircleButton CargoShipCargoRight3;

    //cargo ship score counters
    private int CSPF1Counter = 0;
    private int CSPF2Counter = 0;
    private int CSCF1Counter = 0;
    private int CSCF2Counter = 0;
    private int CSPL1Counter = 0;
    private int CSPL2Counter = 0;
    private int CSPL3Counter = 0;
    private int CSPR2Counter = 0;
    private int CSPR1Counter = 0;
    private int CSPR3Counter = 0;
    private int CSCL1Counter = 0;
    private int CSCL2Counter = 0;
    private int CSCL3Counter = 0;
    private int CSCR1Counter = 0;
    private int CSCR2Counter = 0;
    private int CSCR3Counter = 0;

    //hashmaps for sending QR data between screens
    private HashMap<String, String> setupHashMap;
    private HashMap<String, String> scoreHashMap;

    //navigation buttons
    private BootstrapButton SetupButton;
    private BootstrapButton SandstormButton;
    private BootstrapButton TeleopButton;
    private BootstrapButton ClimbButton;

    //other variables
    private Timer timer;
    private Button UndoButton;
    private ConstraintLayout constraintLayout;
    private String UNDO;
    private Switch FellOverSwitch;
    private Switch HABLineSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (setupHashMap.get("LeftOrRight").equals("Left"))
            setContentView(R.layout.activity_sandstorm);
        else
            setContentView(R.layout.activity_sandstorm_right);

        //linking variables to XML elements on the screen
        SetupButton = findViewById(R.id.SetupButton);
        SandstormButton = findViewById(R.id.SandstormButton);
        TeleopButton = findViewById(R.id.TeleopButton);
        ClimbButton = findViewById(R.id.ClimbButton);

        HABLineSwitch = findViewById(R.id.CrossedHABLineSwitch);
        FellOverSwitch = findViewById(R.id.FellOverSwitch);

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

        TextView CSPF1_Text = findViewById(R.id.CSPF1);
        TextView CSPF2_Text = findViewById(R.id.CSPF2);
        TextView CSCF1_Text = findViewById(R.id.CSCF1);
        TextView CSCF2_Text = findViewById(R.id.CSCF2);
        TextView CSPL1_Text = findViewById(R.id.CSPL1);
        TextView CSPL2_Text = findViewById(R.id.CSPL2);
        TextView CSPL3_Text = findViewById(R.id.CSPL3);
        TextView CSCL1_Text = findViewById(R.id.CSCL1);
        TextView CSCL2_Text = findViewById(R.id.CSCL2);
        TextView CSCL3_Text = findViewById(R.id.CSCL3);
        TextView CSCR1_Text = findViewById(R.id.CSCR1);
        TextView CSCR2_Text = findViewById(R.id.CSCR2);
        TextView CSCR3_Text = findViewById(R.id.CSCR3);
        TextView CSPR1_Text = findViewById(R.id.CSPR1);
        TextView CSPR2_Text = findViewById(R.id.CSPR2);
        TextView CSPR3_Text = findViewById(R.id.CSPR3);

        // grouping screen elements from the scoring map based on location
        LocationGroup CSPF1 = new LocationGroup("CSPF1", Sandstorm.this, CSPF1_Text, CargoShipPanelFront1, CSPF1Counter);
        LocationGroup CSPF2 = new LocationGroup("CSPF2", Sandstorm.this, CSPF2_Text, CargoShipPanelFront2, CSPF2Counter);
        LocationGroup CSCF1 = new LocationGroup("CSCF1", Sandstorm.this, CSCF1_Text, CargoShipCargoFront1, CSCF1Counter);
        LocationGroup CSCF2 = new LocationGroup("CSCF2", Sandstorm.this, CSCF2_Text, CargoShipCargoFront2, CSCF2Counter);
        LocationGroup CSPL1= new LocationGroup("CSPL1", Sandstorm.this, CSPL1_Text, CargoShipPanelLeft1, CSPL1Counter);
        LocationGroup CSPL2 = new LocationGroup("CSPL2", Sandstorm.this, CSPL2_Text, CargoShipPanelLeft2, CSPL2Counter);
        LocationGroup CSPL3 = new LocationGroup("CSPL3", Sandstorm.this, CSPL3_Text, CargoShipPanelLeft3, CSPL3Counter);
        LocationGroup CSPR1= new LocationGroup("CSPR1", Sandstorm.this, CSPR1_Text, CargoShipPanelRight1, CSPR1Counter);
        LocationGroup CSPR2 = new LocationGroup("CSPR2", Sandstorm.this, CSPR2_Text, CargoShipPanelRight2, CSPR2Counter);
        LocationGroup CSPR3 = new LocationGroup("CSPR3", Sandstorm.this, CSPR3_Text, CargoShipPanelRight3, CSPR3Counter);
        LocationGroup CSCL1= new LocationGroup("CSCL1", Sandstorm.this, CSCL1_Text, CargoShipCargoLeft1, CSCL1Counter);
        LocationGroup CSCL2 = new LocationGroup("CSCL2", Sandstorm.this, CSCL2_Text, CargoShipCargoLeft2, CSCL2Counter);
        LocationGroup CSCL3 = new LocationGroup("CSCL3", Sandstorm.this, CSCL3_Text, CargoShipCargoLeft3, CSCL3Counter);
        LocationGroup CSCR1= new LocationGroup("CSCR1", Sandstorm.this, CSCR1_Text, CargoShipCargoRight1, CSCR1Counter);
        LocationGroup CSCR2 = new LocationGroup("CSCR2", Sandstorm.this, CSCR2_Text, CargoShipCargoRight2, CSCR2Counter);
        LocationGroup CSCR3 = new LocationGroup("CSCR3", Sandstorm.this, CSCR3_Text, CargoShipCargoRight3, CSCR3Counter);

        setupHashMap = new HashMap<>();
        scoreHashMap = new HashMap<>();

        timer = new Timer();
        UndoButton = findViewById(R.id.UndoButton);
        constraintLayout = findViewById(R.id.layout);

        //disable scoring diagram
        GenUtils.disableScoringDiagram('A');
        UndoButton.setEnabled(false);

        //initialize hash maps and fill in default data
        final Serializable setupData = getIntent().getSerializableExtra("setupHashMap");
        setupHashMap = (HashMap<String, String>)setupData;
        setupHashMap.put("FellOver",String.valueOf(0));
        setupHashMap.put("HABLine",String.valueOf(0));


        //switch to the next screen with data after 15 seconds
        TimerTask switchToTeleop = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /*Template for iterating through screen elements
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
                        }*/
                        for (LocationGroup lg : LocationGroupList.list.values())
                            if (lg.getCounter() > 0)
                                scoreHashMap.put(lg.getCounterText().getTag().toString(), lg.getCounterText().getText().toString());

                        Intent intent = new Intent(Sandstorm.this, Teleop.class);
                        intent.putExtra("setupHashMap", setupHashMap);
                        intent.putExtra("scoreHashMap", scoreHashMap);
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
        if (scoreData != null) { //show any values entered before coming to this screen
            constraintLayout.setBackgroundColor(GenUtils.getAColor(Sandstorm.this,R.color.genutils_red));
            scoreHashMap = (HashMap<String, String>) scoreData;
            Object keySet[] = scoreHashMap.keySet().toArray();
            String tag;
            for (int i = 0; i < keySet.length; i++) {
                tag = String.valueOf(keySet[i]);
                char arr[] = tag.toCharArray();
                String hashVal;

                if (tag.toCharArray()[1] == 'S') {
                    if (arr[2] == 'P') {
                        if (arr[3] == 'L') {
                            if (arr[4] == '1') {
                                hashVal = scoreHashMap.get("CSPL1");
                                CSPL1.setCounterText(hashVal);
                                if (Integer.parseInt(hashVal) > 0)
                                    CSPL1.selectLocation();
                            } else if (arr[4] == '2') {
                                hashVal = scoreHashMap.get("CSPL2");
                                CSPL2.setCounterText(hashVal);
                                if (Integer.parseInt(hashVal) > 0)
                                    CSPL2.selectLocation();
                            } else if (arr[4] == '3') {
                                hashVal = scoreHashMap.get("CSPL3");
                                CSPL3.setCounterText(hashVal);
                                if (Integer.parseInt(hashVal) > 0)
                                    CSPL3.selectLocation();
                            }
                        } else if (arr[3] == 'R') {
                            if (arr[4] == '1') {
                                hashVal = scoreHashMap.get("CSPR1");
                                CSPR1.setCounterText(hashVal);
                                if (Integer.parseInt(hashVal) > 0)
                                    CSPR1.selectLocation();
                            } else if (arr[4] == '2') {
                                hashVal = scoreHashMap.get("CSPR2");
                                CSPR2.setCounterText(hashVal);
                                if (Integer.parseInt(hashVal) > 0)
                                    CSPR2.selectLocation();
                            } else if (arr[4] == '3') {
                                hashVal = scoreHashMap.get("CSPR3");
                                CSPR3.setCounterText(hashVal);
                                if (Integer.parseInt(hashVal) > 0)
                                    CSPR3.selectLocation();
                            }
                        } else if (arr[3] == 'F') {
                            if (arr[4] == '1') {
                                hashVal = scoreHashMap.get("CSPF1");
                                CSPF1.setCounterText(hashVal);
                                if (Integer.parseInt(hashVal) > 0)
                                    CSPF1.selectLocation();
                            } else if (arr[4] == '2') {
                                hashVal = scoreHashMap.get("CSPF2");
                                CSPF2.setCounterText(hashVal);
                                if (Integer.parseInt(hashVal) > 0)
                                    CSPF2.selectLocation();
                            }
                        }
                    } else if (arr[2] == 'C') {
                        if (arr[3] == 'L') {
                            if (arr[4] == '1') {
                                hashVal = scoreHashMap.get("CSCL1");
                                CSCL1.setCounterText(hashVal);
                                if (Integer.parseInt(hashVal) > 0)
                                    CSCL1.selectLocation();
                            } else if (arr[4] == '2') {
                                hashVal = scoreHashMap.get("CSCL2");
                                CSCL2.setCounterText(hashVal);
                                if (Integer.parseInt(hashVal) > 0)
                                    CSCL2.selectLocation();
                            } else if (arr[4] == '3') {
                                hashVal = scoreHashMap.get("CSCL3");
                                CSCL3.setCounterText(hashVal);
                                if (Integer.parseInt(hashVal) > 0)
                                    CSCL3.selectLocation();
                            }
                        } else if (arr[3] == 'R') {
                            if (arr[4] == '1') {
                                hashVal = scoreHashMap.get("CSCR1");
                                CSCR1.setCounterText(hashVal);
                                if (Integer.parseInt(hashVal) > 0)
                                    CSCR1.selectLocation();
                            } else if (arr[4] == '2') {
                                hashVal = scoreHashMap.get("CSCR2");
                                CSCR2.setCounterText(hashVal);
                                if (Integer.parseInt(hashVal) > 0)
                                    CSCR2.selectLocation();
                            } else if (arr[4] == '3') {
                                hashVal = scoreHashMap.get("CSCR3");
                                CSCR3.setCounterText(hashVal);
                                if (Integer.parseInt(hashVal) > 0)
                                    CSCR3.selectLocation();
                            }
                        }  else if (arr[3] == 'F') {
                            if (arr[4] == '1') {
                                hashVal = scoreHashMap.get("CSCF1");
                                CSCF1.setCounterText(hashVal);
                                if (Integer.parseInt(hashVal) > 0)
                                    CSCF1.selectLocation();
                            } else if (arr[4] == '2') {
                                hashVal = scoreHashMap.get("CSCF2");
                                CSCF2.setCounterText(hashVal);
                                if (Integer.parseInt(hashVal) > 0)
                                    CSCF2.selectLocation();
                            }
                        }
                    }
                }
            }
        } else
            scoreHashMap = new HashMap<>();


        //making only Sandstorm Button look active from top toggle
        GenUtils.defaultButtonState(Sandstorm.this, SetupButton);
        GenUtils.selectedButtonState(Sandstorm.this, SandstormButton);
        GenUtils.defaultButtonState(Sandstorm.this, TeleopButton);
        GenUtils.defaultButtonState(Sandstorm.this, ClimbButton);


        //set listeners for buttons and fill the hashmap with data
        HABLineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UNDO = "HAB";
                    UndoButton.setEnabled(true);
                    setupHashMap.put("HABLine",String.valueOf(1));
                } else
                    setupHashMap.put("HABLine",String.valueOf(0));
            }
        });


        FellOverSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UNDO = "FellOver";
                    UndoButton.setEnabled(true);
                    setupHashMap.put("FellOver",String.valueOf(1));
                    HABLineSwitch.setEnabled(false);
                    GenUtils.disableScoringDiagram('A');
                } else {
                    setupHashMap.put("FellOver",String.valueOf(0));
                    HABLineSwitch.setEnabled(true);
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
    private void cargoShipClick(String name) {
        LocationGroup lg = LocationGroupList.list.get(name);
        lg.increaseCounter();
        lg.enableLocation();
        scoreHashMap.put(lg.getCounterText().getTag().toString(), String.valueOf(lg.getCounter()));
        GenUtils.disableScoringDiagram('A');
        UndoButton.setEnabled(true);
        HABLineSwitch.setChecked(true);
    }

    private void cargoShipUndo(String name) {
        LocationGroup lg = LocationGroup.list.get(name);
        lg.decreaseCounter();
        GenUtils.enableScoringDiagram(name.charAt(2));
    }


    //button click methods
    public void setupClick (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("setupHashMap", setupHashMap);
        timer.cancel();
        startActivity(intent);
    }
    public void teleopClick (View view) {
        Intent intent = new Intent(this, Teleop.class);
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


    //cargo ship location click methods
    public void CSPF1CounterClick (View view) {
        UNDO = "CSPF1";
        cargoShipClick(UNDO);
    }
    public void CSPF2CounterClick (View view) {
        UNDO = "CSPF2";
        cargoShipClick(UNDO);
    }
    public void CSCF1CounterClick (View view) {
        UNDO = "CSCF1";
        cargoShipClick(UNDO);
    }
    public void CSCF2CounterClick (View view) {
        UNDO = "CSCF2";
        cargoShipClick(UNDO);
    }
    public void CSPL1CounterClick (View view) {
        UNDO = "CSPL1";
        cargoShipClick(UNDO);
    }
    public void CSPL2CounterClick (View view) {
        UNDO = "CSPL2";
        cargoShipClick(UNDO);
    }
    public void CSPL3CounterClick (View view) {
        UNDO = "CSPL3";
        cargoShipClick(UNDO);
    }
    public void CSCL1CounterClick (View view) {
        UNDO = "CSCL1";
        cargoShipClick(UNDO);
    }
    public void CSCL2CounterClick (View view) {
        UNDO = "CSCL2";
        cargoShipClick(UNDO);
    }
    public void CSCL3CounterClick (View view) {
        UNDO = "CSCL3";
        cargoShipClick(UNDO);
    }
    public void CSCR1CounterClick (View view) {
        UNDO = "CSCR1";
        cargoShipClick(UNDO);
    }
    public void CSCR2CounterClick (View view) {
        UNDO = "CSCR2";
        cargoShipClick(UNDO);
    }
    public void CSCR3CounterClick (View view) {
        UNDO = "CSCR3";
        cargoShipClick(UNDO);
    }
    public void CSPR1CounterClick (View view) {
        UNDO = "CSPR1";
        cargoShipClick(UNDO);
    }
    public void CSPR2CounterClick (View view) {
        UNDO = "CSPR2";
        cargoShipClick(UNDO);
    }
    public void CSPR3CounterClick (View view) {
        UNDO = "CSPR3";
        cargoShipClick(UNDO);
    }

    //undo button click method
    public void UndoClick (View view) {
        UndoButton.setEnabled(false);
        switch (UNDO) {
            case "FellOver":
                setupHashMap.put("FellOver", String.valueOf(0));
                HABLineSwitch.setEnabled(true);
                FellOverSwitch.setChecked(!FellOverSwitch.isChecked());
                break;
            case "HAB":
                setupHashMap.put("HABLine", String.valueOf(0));
                HABLineSwitch.setChecked(!HABLineSwitch.isChecked());
                break;
            //undo for cargo ship locations
            case "CSPF1":
                cargoShipUndo(UNDO);
                break;
            case "CSPF2":
                cargoShipUndo(UNDO);
                break;
            case "CSCF1":
                cargoShipUndo(UNDO);
                break;
            case "CSCF2":
                cargoShipUndo(UNDO);
                break;
            case "CSPL1":
                cargoShipUndo(UNDO);
                break;
            case "CSPL2":
                cargoShipUndo(UNDO);
                break;
            case "CSPL3":
                cargoShipUndo(UNDO);
                break;
            case "CSCL1":
                cargoShipUndo(UNDO);
                break;
            case "CSCL2":
                cargoShipUndo(UNDO);
                break;
            case "CSCL3":
                cargoShipUndo(UNDO);
                break;
            case "CSCR1":
                cargoShipUndo(UNDO);
                break;
            case "CSCR2":
                cargoShipUndo(UNDO);
                break;
            case "CSCR3":
                cargoShipUndo(UNDO);
                break;
            case "CSPR1":
                cargoShipUndo(UNDO);
                break;
            case "CSPR2":
                cargoShipUndo(UNDO);
                break;
            case "CSPR3":
                cargoShipUndo(UNDO);
        }
    }
}