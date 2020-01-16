package com.mercury1089.scoutingapp2019;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mercury1089.scoutingapp2019.utils.GenUtils;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class Auton extends Fragment {
    //HashMaps for sending QR data between screens
    private LinkedHashMap<String, String> setupHashMap;
    private LinkedHashMap<String, String> autonHashMap;

    //RadioButtons
    private RadioButton pickedUpIncrementButton;
    private RadioButton pickedUpDecrementButton;
    private RadioButton droppedIncrementButton;
    private RadioButton droppedDecrementButton;
    private RadioButton scoredButton   ;
    private RadioButton missedButton;
    private RadioButton crossedLineSwitch;
    private RadioButton fellOverSwitch;

    //TextViews
    private TextView secondsRemaining;
    private TextView pickedUpCounter;
    private TextView droppedCounter;
    private TextView scoredCounter;
    private TextView missedCounter;

    //other variables
    private static Timer timer = new Timer();
    private boolean firstTime = true;
    private int totalScored;
    private int totalMissed;

    public static Auton newInstance() {
        Auton fragment = new Auton();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    MatchActivity context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = (MatchActivity) getActivity();
        return inflater.inflate(R.layout.fragment_auton, container, false);
    }

    public void onStart(){
        super.onStart();

        //linking variables to XML elements on the screen
        pickedUpIncrementButton = getView().findViewById(R.id.PickedUpButton);
        pickedUpDecrementButton = getView().findViewById(R.id.NotPickedUpButton);
        pickedUpCounter = getView().findViewById(R.id.PickedUpCounter);

        droppedIncrementButton = getView().findViewById(R.id.DroppedButton);
        droppedDecrementButton = getView().findViewById(R.id.NotDroppedButton);
        droppedCounter = getView().findViewById(R.id.DroppedCounter);

        scoredButton = getView().findViewById(R.id.ScoredButton);
        scoredCounter = getView().findViewById(R.id.ScoredCounter);
        missedButton = getView().findViewById(R.id.MissedButton);
        missedCounter = getView().findViewById(R.id.MissedCounter);

        crossedLineSwitch = getView().findViewById(R.id.CrossedLineSwitch);
        fellOverSwitch = getView().findViewById(R.id.FellOverSwitch);

        //get HashMap data (fill with defaults if empty or null)
        setupHashMap = context.setupHashMap;
        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.AUTON);
        autonHashMap = HashMapManager.getAutonHashMap();

        //fill in counters with data
        pickedUpCounter.setText(autonHashMap.get("NumberPickedUp"));
        droppedCounter.setText(autonHashMap.get("NumberDropped"));
        totalScored = Integer.parseInt(autonHashMap.get("InnerPortScored")) + Integer.parseInt(autonHashMap.get("OuterPortScored")) + Integer.parseInt(autonHashMap.get("LowerPortScored"));
        scoredCounter.setText(GenUtils.padLeftZeros(Integer.toString(totalScored), 3));
        totalMissed = Integer.parseInt(autonHashMap.get("UpperMissed")) + Integer.parseInt(autonHashMap.get("LowerPortMissed"));
        missedCounter.setText(GenUtils.padLeftZeros(Integer.toString(totalScored), 3));

        //switch to the next screen with data after 15 seconds
        TimerTask switchToTeleop = new TimerTask() {
            @Override
            public void run() {
                Handler handler = new Handler(context.getMainLooper());
                Runnable switchToTeleop = new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "End Auton", Toast.LENGTH_SHORT).show();
                    }
                };
                handler.post(switchToTeleop);
            }
        };
        if(firstTime) {
            timer.schedule(switchToTeleop, 15000);
            firstTime = false;
        }

        //set listeners for buttons and fill the hashmap with data
        /*
        fellOverSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setupHashMap.put("FellOver",String.valueOf(1));
                } else {
                    setupHashMap.put("FellOver",String.valueOf(0));
                }
            }
        });*/

        scoredButton.setOnClickListener(new BootstrapButton.OnClickListener() {
            public void onClick(View view){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_scored, null);

                int width = 325;
                int height = 310;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
                popupWindow.showAsDropDown(scoredButton);

                // Bootstrap Buttons
                BootstrapButton saveButton = popupView.findViewById(R.id.SaveButton);
                BootstrapButton cancelButton = popupView.findViewById(R.id.CancelButton);
                BootstrapButton innerIncrement = popupView.findViewById(R.id.InnerIncrement);
                BootstrapButton innerDecrement = popupView.findViewById(R.id.InnerDecrement);
                BootstrapButton outerIncrement = popupView.findViewById(R.id.OuterIncrement);
                BootstrapButton outerDecrement = popupView.findViewById(R.id.OuterDecrement);
                BootstrapButton lowerIncrement = popupView.findViewById(R.id.LowerIncrement);
                BootstrapButton lowerDecrement = popupView.findViewById(R.id.LowerDecrement);

                // Counter TextBoxes
                TextView innerScore = popupView.findViewById(R.id.InnerScore);
                TextView outerScore = popupView.findViewById(R.id.OuterScore);
                TextView lowerScore = popupView.findViewById(R.id.LowerScore);

                // Temp variables
                innerScore.setText(GenUtils.padLeftZeros(autonHashMap.get("InnerPortScored"), 3));
                outerScore.setText(GenUtils.padLeftZeros(autonHashMap.get("OuterPortScored"), 3));
                lowerScore.setText(GenUtils.padLeftZeros(autonHashMap.get("LowerPortScored"), 3));

                innerIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int innerPortNum = Integer.parseInt((String)innerScore.getText());
                        innerPortNum += 1;
                        innerScore.setText(GenUtils.padLeftZeros(Integer.toString(innerPortNum), 3));
                    }
                });

                innerDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int innerPortNum = Integer.parseInt((String)innerScore.getText());
                        if(innerPortNum > 0) {
                            innerPortNum -= 1;
                            innerScore.setText(GenUtils.padLeftZeros(Integer.toString(innerPortNum), 3));
                        }
                    }
                });

                outerIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int outerPortNum = Integer.parseInt((String)outerScore.getText());
                        outerPortNum += 1;
                        outerScore.setText(GenUtils.padLeftZeros(Integer.toString(outerPortNum), 3));
                    }
                });

                outerDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int outerPortNum = Integer.parseInt((String)outerScore.getText());
                        if(outerPortNum > 0) {
                            outerPortNum -= 1;
                            outerScore.setText(GenUtils.padLeftZeros(Integer.toString(outerPortNum), 3));
                        }
                    }
                });

                lowerIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int lowerPortNum = Integer.parseInt((String)lowerScore.getText());
                        lowerPortNum += 1;
                        lowerScore.setText(GenUtils.padLeftZeros(Integer.toString(lowerPortNum), 3));
                    }
                });

                lowerDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int lowerPortNum = Integer.parseInt((String)lowerScore.getText());
                        if(lowerPortNum > 0) {
                            lowerPortNum -= 1;
                            lowerScore.setText(GenUtils.padLeftZeros(Integer.toString(lowerPortNum), 3));
                        }
                    }
                });

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        autonHashMap.put("InnerPortScored", (String)innerScore.getText());
                        autonHashMap.put("OuterPortScored", (String)outerScore.getText());
                        autonHashMap.put("LowerPortScored", (String)lowerScore.getText());
                        popupWindow.dismiss();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

        missedButton.setOnClickListener(new BootstrapButton.OnClickListener() {
            public void onClick(View view){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_missed, null);

                int width = 325;
                int height = 310;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
                popupWindow.showAsDropDown(missedButton);

                // Bootstrap Buttons
                BootstrapButton saveButton = popupView.findViewById(R.id.SaveButton);
                BootstrapButton cancelButton = popupView.findViewById(R.id.CancelButton);
                BootstrapButton innerIncrement = popupView.findViewById(R.id.InnerIncrement);
                BootstrapButton innerDecrement = popupView.findViewById(R.id.InnerDecrement);
                BootstrapButton outerIncrement = popupView.findViewById(R.id.OuterIncrement);
                BootstrapButton outerDecrement = popupView.findViewById(R.id.OuterDecrement);
                BootstrapButton lowerIncrement = popupView.findViewById(R.id.LowerIncrement);
                BootstrapButton lowerDecrement = popupView.findViewById(R.id.LowerDecrement);

                // Counter TextBoxes
                TextView innerScore = popupView.findViewById(R.id.InnerScore);
                TextView outerScore = popupView.findViewById(R.id.OuterScore);
                TextView lowerScore = popupView.findViewById(R.id.LowerScore);

                // Temp variables
                innerScore.setText(GenUtils.padLeftZeros(autonHashMap.get("InnerPortMissed"), 3));
                outerScore.setText(GenUtils.padLeftZeros(autonHashMap.get("OuterPortMissed"), 3));
                lowerScore.setText(GenUtils.padLeftZeros(autonHashMap.get("LowerPortMissed"), 3));

                innerIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int innerPortNum = Integer.parseInt((String)innerScore.getText());
                        innerPortNum += 1;
                        innerScore.setText(GenUtils.padLeftZeros(Integer.toString(innerPortNum), 3));
                    }
                });

                innerDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int innerPortNum = Integer.parseInt((String)innerScore.getText());
                        if(innerPortNum > 0) {
                            innerPortNum -= 1;
                            innerScore.setText(GenUtils.padLeftZeros(Integer.toString(innerPortNum), 3));
                        }
                    }
                });

                outerIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int outerPortNum = Integer.parseInt((String)outerScore.getText());
                        outerPortNum += 1;
                        outerScore.setText(GenUtils.padLeftZeros(Integer.toString(outerPortNum), 3));
                    }
                });

                outerDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int outerPortNum = Integer.parseInt((String)outerScore.getText());
                        if(outerPortNum > 0) {
                            outerPortNum -= 1;
                            outerScore.setText(GenUtils.padLeftZeros(Integer.toString(outerPortNum), 3));
                        }
                    }
                });

                lowerIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int lowerPortNum = Integer.parseInt((String)lowerScore.getText());
                        lowerPortNum += 1;
                        lowerScore.setText(GenUtils.padLeftZeros(Integer.toString(lowerPortNum), 3));
                    }
                });

                lowerDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int lowerPortNum = Integer.parseInt((String)lowerScore.getText());
                        if(lowerPortNum > 0) {
                            lowerPortNum -= 1;
                            lowerScore.setText(GenUtils.padLeftZeros(Integer.toString(lowerPortNum), 3));
                        }
                    }
                });

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        autonHashMap.put("InnerPortMissed", (String)innerScore.getText());
                        autonHashMap.put("OuterPortMissed", (String)outerScore.getText());
                        autonHashMap.put("LowerPortMissed", (String)lowerScore.getText());
                        popupWindow.dismiss();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (isVisibleToUser) {
                setupHashMap = context.setupHashMap;
                autonHashMap = HashMapManager.getAutonHashMap();
                //set all objects in the fragment to their values from the HashMaps
            } else {
                context.setupHashMap = setupHashMap;
                HashMapManager.putAutonHashMap(autonHashMap);
                timer.cancel();
                timer = new Timer();
            }
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        context.setupHashMap = setupHashMap;
        HashMapManager.putAutonHashMap(autonHashMap);
        timer.cancel();
        timer = new Timer();
    }
}
