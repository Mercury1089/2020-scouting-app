package com.mercury1089.scoutingapp2019;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import androidx.constraintlayout.widget.ConstraintLayout;
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

    Activity context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        //Inflate the layout for this fragment
        if (setupHashMap.get("LeftOrRight").equals("Left"))
            return inflater.inflate(R.layout.activity_sandstorm, container, false);
        else
            return inflater.inflate(R.layout.activity_sandstorm_right, container, false);
    }

    public void onStart(){
        super.onStart();

        //linking variables to XML elements on the screen
        SetupButton = context.findViewById(R.id.SetupButton);
        SandstormButton = context.findViewById(R.id.SandstormButton);
        TeleopButton = context.findViewById(R.id.TeleopButton);
        ClimbButton = context.findViewById(R.id.ClimbButton);

        HABLineSwitch = context.findViewById(R.id.CrossedHABLineSwitch);
        FellOverSwitch = context.findViewById(R.id.FellOverSwitch);

        CargoShipPanelFront1 = context.findViewById(R.id.CargoShipPanelFront1);
        CargoShipPanelFront2 = context.findViewById(R.id.CargoShipPanelFront2);
        CargoShipPanelLeft1 = context.findViewById(R.id.CargoShipPanelLeft1);
        CargoShipPanelLeft2 = context.findViewById(R.id.CargoShipPanelLeft2);
        CargoShipPanelLeft3 = context.findViewById(R.id.CargoShipPanelLeft3);
        CargoShipPanelRight1 = context.findViewById(R.id.CargoShipPanelRight1);
        CargoShipPanelRight2 = context.findViewById(R.id.CargoShipPanelRight2);
        CargoShipPanelRight3 = context.findViewById(R.id.CargoShipPanelRight3);
        CargoShipCargoFront1 = context.findViewById(R.id.CargoShipCargoFront1);
        CargoShipCargoFront2 = context.findViewById(R.id.CargoShipCargoFront2);
        CargoShipCargoLeft1 = context.findViewById(R.id.CargoShipCargoLeft1);
        CargoShipCargoLeft2 = context.findViewById(R.id.CargoShipCargoLeft2);
        CargoShipCargoLeft3 = context.findViewById(R.id.CargoShipCargoLeft3);
        CargoShipCargoRight1 = context.findViewById(R.id.CargoShipCargoRight1);
        CargoShipCargoRight2 = context.findViewById(R.id.CargoShipCargoRight2);
        CargoShipCargoRight3 = context.findViewById(R.id.CargoShipCargoRight3);

        TextView CSPF1_Text = context.findViewById(R.id.CSPF1);
        TextView CSPF2_Text = context.findViewById(R.id.CSPF2);
        TextView CSCF1_Text = context.findViewById(R.id.CSCF1);
        TextView CSCF2_Text = context.findViewById(R.id.CSCF2);
        TextView CSPL1_Text = context.findViewById(R.id.CSPL1);
        TextView CSPL2_Text = context.findViewById(R.id.CSPL2);
        TextView CSPL3_Text = context.findViewById(R.id.CSPL3);
        TextView CSCL1_Text = context.findViewById(R.id.CSCL1);
        TextView CSCL2_Text = context.findViewById(R.id.CSCL2);
        TextView CSCL3_Text = context.findViewById(R.id.CSCL3);
        TextView CSCR1_Text = context.findViewById(R.id.CSCR1);
        TextView CSCR2_Text = context.findViewById(R.id.CSCR2);
        TextView CSCR3_Text = context.findViewById(R.id.CSCR3);
        TextView CSPR1_Text = context.findViewById(R.id.CSPR1);
        TextView CSPR2_Text = context.findViewById(R.id.CSPR2);
        TextView CSPR3_Text = context.findViewById(R.id.CSPR3);

        // grouping screen elements from the scoring map based on location
        LocationGroup CSPF1 = new LocationGroup("CSPF1", context, CSPF1_Text, CargoShipPanelFront1, CSPF1Counter);
        LocationGroup CSPF2 = new LocationGroup("CSPF2", context, CSPF2_Text, CargoShipPanelFront2, CSPF2Counter);
        LocationGroup CSCF1 = new LocationGroup("CSCF1", context, CSCF1_Text, CargoShipCargoFront1, CSCF1Counter);
        LocationGroup CSCF2 = new LocationGroup("CSCF2", context, CSCF2_Text, CargoShipCargoFront2, CSCF2Counter);
        LocationGroup CSPL1= new LocationGroup("CSPL1", context, CSPL1_Text, CargoShipPanelLeft1, CSPL1Counter);
        LocationGroup CSPL2 = new LocationGroup("CSPL2", context, CSPL2_Text, CargoShipPanelLeft2, CSPL2Counter);
        LocationGroup CSPL3 = new LocationGroup("CSPL3", context, CSPL3_Text, CargoShipPanelLeft3, CSPL3Counter);
        LocationGroup CSPR1= new LocationGroup("CSPR1", context, CSPR1_Text, CargoShipPanelRight1, CSPR1Counter);
        LocationGroup CSPR2 = new LocationGroup("CSPR2", context, CSPR2_Text, CargoShipPanelRight2, CSPR2Counter);
        LocationGroup CSPR3 = new LocationGroup("CSPR3", context, CSPR3_Text, CargoShipPanelRight3, CSPR3Counter);
        LocationGroup CSCL1= new LocationGroup("CSCL1", context, CSCL1_Text, CargoShipCargoLeft1, CSCL1Counter);
        LocationGroup CSCL2 = new LocationGroup("CSCL2", context, CSCL2_Text, CargoShipCargoLeft2, CSCL2Counter);
        LocationGroup CSCL3 = new LocationGroup("CSCL3", context, CSCL3_Text, CargoShipCargoLeft3, CSCL3Counter);
        LocationGroup CSCR1= new LocationGroup("CSCR1", context, CSCR1_Text, CargoShipCargoRight1, CSCR1Counter);
        LocationGroup CSCR2 = new LocationGroup("CSCR2", context, CSCR2_Text, CargoShipCargoRight2, CSCR2Counter);
        LocationGroup CSCR3 = new LocationGroup("CSCR3", context, CSCR3_Text, CargoShipCargoRight3, CSCR3Counter);

        setupHashMap = new HashMap<>();
        scoreHashMap = new HashMap<>();

        timer = new Timer();
        UndoButton = context.findViewById(R.id.UndoButton);
        constraintLayout = context.findViewById(R.id.layout);

        //disable scoring diagram
        GenUtils.disableScoringDiagram('A');
        UndoButton.setEnabled(false);

        //initialize hash maps and fill in default data
        final Serializable setupData = context.getIntent().getSerializableExtra("setupHashMap");
        setupHashMap = (HashMap<String, String>)setupData;
        setupHashMap.put("FellOver",String.valueOf(0));
        setupHashMap.put("HABLine",String.valueOf(0));


        //switch to the next screen with data after 15 seconds
        TimerTask switchToTeleop = new TimerTask() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
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

                        Intent intent = new Intent(context, Teleop.class);
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

        Serializable scoreData = context.getIntent().getSerializableExtra("scoreHashMap");
        if (scoreData != null) { //show any values entered before coming to this screen
            constraintLayout.setBackgroundColor(GenUtils.getAColor(context,R.color.genutils_red));
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
        GenUtils.defaultButtonState(context, SetupButton);
        GenUtils.selectedButtonState(context, SandstormButton);
        GenUtils.defaultButtonState(context, TeleopButton);
        GenUtils.defaultButtonState(context, ClimbButton);


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

        SetupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("setupHashMap", setupHashMap);
                timer.cancel();
                startActivity(intent);
            }
        });

        TeleopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Teleop.class);
                if (FellOverSwitch.isChecked())
                    intent.putExtra("fellOver","True");
                else
                    intent.putExtra("fellOver","");
                intent.putExtra("setupHashMap", setupHashMap);
                intent.putExtra("scoreHashMap", scoreHashMap);
                timer.cancel();
                startActivity(intent);
            }
        });

        ClimbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Climb.class);
                intent.putExtra("setupHashMap", setupHashMap);
                intent.putExtra("scoreHashMap", scoreHashMap);
                timer.cancel();
                startActivity(intent);
            }
        });

        CargoShipPanelFront1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UNDO = "CSPF1";
                cargoShipClick(UNDO);
            }
        });

        CargoShipPanelFront2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UNDO = "CSPF2";
                cargoShipClick(UNDO);
            }
        });

        CargoShipPanelLeft1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UNDO = "CSPL1";
                cargoShipClick(UNDO);
            }
        });

        CargoShipPanelLeft2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UNDO = "CSPL2";
                cargoShipClick(UNDO);
            }
        });

        CargoShipPanelLeft3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UNDO = "CSPL3";
                cargoShipClick(UNDO);
            }
        });

        CargoShipPanelRight1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UNDO = "CSPR1";
                cargoShipClick(UNDO);
            }
        });

        CargoShipPanelRight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UNDO = "CSPR2";
                cargoShipClick(UNDO);
            }
        });

        CargoShipPanelRight3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UNDO = "CSPR3";
                cargoShipClick(UNDO);
            }
        });

        CargoShipCargoFront1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UNDO = "CSCF1";
                cargoShipClick(UNDO);
            }
        });

        CargoShipCargoFront2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UNDO = "CSCF2";
                cargoShipClick(UNDO);
            }
        });

        CargoShipCargoLeft1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UNDO = "CSCL1";
                cargoShipClick(UNDO);
            }
        });

        CargoShipCargoLeft2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UNDO = "CSCL2";
                cargoShipClick(UNDO);
            }
        });

        CargoShipCargoLeft3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UNDO = "CSCL3";
                cargoShipClick(UNDO);
            }
        });

        CargoShipCargoRight1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UNDO = "CSCR1";
                cargoShipClick(UNDO);
            }
        });

        CargoShipCargoRight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UNDO = "CSCR2";
                cargoShipClick(UNDO);
            }
        });

        CargoShipCargoRight3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UNDO = "CSCR3";
                cargoShipClick(UNDO);
            }
        });

        UndoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
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
}
