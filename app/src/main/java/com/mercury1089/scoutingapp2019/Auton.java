package com.mercury1089.scoutingapp2019;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;
import androidx.fragment.app.Fragment;
import com.mercury1089.scoutingapp2019.utils.GenUtils;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class Auton extends Fragment {
    //HashMaps for sending QR data between screens
    private LinkedHashMap<String, String> setupHashMap;
    private LinkedHashMap<String, String> autonHashMap;

    //Buttons
    private ImageButton pickedUpIncrementButton;
    private ImageButton pickedUpDecrementButton;
    private ImageButton droppedIncrementButton;
    private ImageButton droppedDecrementButton;
    private Button scoredButton;
    private Button missedButton;

    //Switches
    private Switch crossedLineSwitch;
    private Switch fellOverSwitch;

    //TextViews
    private TextView secondsRemaining;
    private TextView pickedUpCounter;
    private TextView droppedCounter;
    private TextView scoredCounter;
    private TextView missedCounter;

    //ImageViews
    private ImageView topEdgeBar;
    private ImageView bottomEdgeBar;
    private ImageView leftEdgeBar;
    private ImageView rightEdgeBar;

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
        secondsRemaining = getView().findViewById(R.id.AutonSeconds);

        droppedIncrementButton = getView().findViewById(R.id.DroppedButton);
        droppedDecrementButton = getView().findViewById(R.id.NotDroppedButton);
        droppedCounter = getView().findViewById(R.id.DroppedCounter);

        scoredButton = getView().findViewById(R.id.ScoredButton);
        scoredCounter = getView().findViewById(R.id.ScoredCounter);
        missedButton = getView().findViewById(R.id.MissedButton);
        missedCounter = getView().findViewById(R.id.MissedCounter);

        crossedLineSwitch = getView().findViewById(R.id.CrossedLineSwitch);
        fellOverSwitch = getView().findViewById(R.id.FellOverSwitch);

        topEdgeBar = getView().findViewById(R.id.topEdgeBar);
        bottomEdgeBar = getView().findViewById(R.id.bottomEdgeBar);
        leftEdgeBar = getView().findViewById(R.id.leftEdgeBar);
        rightEdgeBar = getView().findViewById(R.id.rightEdgeBar);

        //get HashMap data (fill with defaults if empty or null)
        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.SETUP);
        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.AUTON);
        setupHashMap = HashMapManager.getSetupHashMap();
        autonHashMap = HashMapManager.getAutonHashMap();

        //fill in counters with data
        updateXMLObjects();

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

            new CountDownTimer(15000, 1000) {

                public void onTick(long millisUntilFinished) {
                    secondsRemaining.setText(GenUtils.padLeftZeros(" " + millisUntilFinished / 1000, 3));
                    if (millisUntilFinished / 1000 <= 3 && millisUntilFinished / 1000 > 0) {  //play the blinking animation

                        ObjectAnimator topEdgeLighterOn = ObjectAnimator.ofFloat(topEdgeBar, View.ALPHA, 1.0f, 0.0f);
                        ObjectAnimator topEdgeLighterOff = ObjectAnimator.ofFloat(topEdgeBar, View.ALPHA, 0.0f, 1.0f);

                        ObjectAnimator bottomEdgeLighterOn = ObjectAnimator.ofFloat(bottomEdgeBar, View.ALPHA, 1.0f, 0.0f);
                        ObjectAnimator bottomEdgeLighterOff = ObjectAnimator.ofFloat(bottomEdgeBar, View.ALPHA, 0.0f, 1.0f);

                        ObjectAnimator rightEdgeLighterOn = ObjectAnimator.ofFloat(rightEdgeBar, View.ALPHA, 1.0f, 0.0f);
                        ObjectAnimator rightEdgeLighterOff = ObjectAnimator.ofFloat(rightEdgeBar, View.ALPHA, 0.0f, 1.0f);

                        ObjectAnimator leftEdgeLighterOn = ObjectAnimator.ofFloat(leftEdgeBar, View.ALPHA, 1.0f, 0.0f);
                        ObjectAnimator leftEdgeLighterOff = ObjectAnimator.ofFloat(leftEdgeBar, View.ALPHA, 0.0f, 1.0f);

                        topEdgeLighterOn.setDuration(100);
                        topEdgeLighterOff.setDuration(300);

                        bottomEdgeLighterOn.setDuration(100);
                        bottomEdgeLighterOff.setDuration(300);

                        leftEdgeLighterOn.setDuration(100);
                        leftEdgeLighterOff.setDuration(300);

                        rightEdgeLighterOn.setDuration(100);
                        rightEdgeLighterOff.setDuration(300);

                        AnimatorSet animatorSet = new AnimatorSet();

                        animatorSet.playTogether(topEdgeLighterOn, bottomEdgeLighterOn, rightEdgeLighterOn, leftEdgeLighterOn);
                        animatorSet.start();

                        animatorSet.playTogether(topEdgeLighterOff, bottomEdgeLighterOff, leftEdgeLighterOff, rightEdgeLighterOff);
                        animatorSet.start();
                    }
                    else { //the bars need to remain lit up after the countdown
                        topEdgeBar.setAlpha(1);
                        bottomEdgeBar.setAlpha(1);
                        rightEdgeBar.setAlpha(1);
                        leftEdgeBar.setAlpha(1);
                    }
                }

                public void onFinish() { //sets the label to display a teleop error background and text
                    secondsRemaining.setText("000");
//                    teleopWarning.setTextColor(getResources().getColor(R.color.white));
//                    teleopWarning.setBackground(getResources().getDrawable(R.drawable.teleop_error));
//                    teleopWarning.setText("Switch to Teleop as soon as possible!");
                }
            }.start();
        }
        else {
            topEdgeBar.setAlpha(1);
            bottomEdgeBar.setAlpha(1);
            rightEdgeBar.setAlpha(1);
            leftEdgeBar.setAlpha(1);
        }

        //set listeners for buttons and fill the hashmap with data

        pickedUpIncrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                int currentCount = Integer.parseInt((String)pickedUpCounter.getText());
                currentCount++;
                autonHashMap.put("NumberPickedUp", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        pickedUpDecrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                int currentCount = Integer.parseInt((String)pickedUpCounter.getText());
                currentCount--;
                autonHashMap.put("NumberPickedUp", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        droppedIncrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                int currentCount = Integer.parseInt((String)droppedCounter.getText());
                currentCount++;
                autonHashMap.put("NumberDropped", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        droppedDecrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                int currentCount = Integer.parseInt((String)droppedCounter.getText());
                currentCount--;
                autonHashMap.put("NumberDropped", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        scoredButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_scored, null);

                int width = 325;
                int height = 320;

                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
                popupWindow.showAsDropDown(scoredButton);

                // Buttons
                Button doneButton = popupView.findViewById(R.id.DoneButton);
                Button cancelButton = popupView.findViewById(R.id.CancelButton);
                Button higherIncrement = popupView.findViewById(R.id.HigherIncrement);
                Button higherDecrement = popupView.findViewById(R.id.HigherDecrement);
                Button lowerIncrement = popupView.findViewById(R.id.LowerIncrement);
                Button lowerDecrement = popupView.findViewById(R.id.LowerDecrement);

                // Counter TextBoxes
                TextView higherScore = popupView.findViewById(R.id.HigherScore);
                TextView lowerScore = popupView.findViewById(R.id.LowerScore);

                // Temp variables
                higherScore.setText(GenUtils.padLeftZeros(autonHashMap.get("higherPortScored"), 3));
                lowerScore.setText(GenUtils.padLeftZeros(autonHashMap.get("LowerPortScored"), 3));

                higherIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int higherPortNum = Integer.parseInt((String)higherScore.getText());
                        higherPortNum += 1;
                        higherScore.setText(GenUtils.padLeftZeros(Integer.toString(higherPortNum), 3));
                    }
                });

                higherDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int higherPortNum = Integer.parseInt((String)higherScore.getText());
                        if(higherPortNum > 0) {
                            higherPortNum -= 1;
                            higherScore.setText(GenUtils.padLeftZeros(Integer.toString(higherPortNum), 3));
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

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        autonHashMap.put("higherPortScored", (String)higherScore.getText());
                        // autonHashMap.put("OuterPortScored", (String)outerScore.getText());
                        autonHashMap.put("LowerPortScored", (String)lowerScore.getText());
                        totalScored = Integer.parseInt((String)higherScore.getText()) + Integer.parseInt((String)lowerScore.getText());
                        updateXMLObjects();
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

        missedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_missed, null);

                int width = 325;
                int height = 275;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
                popupWindow.showAsDropDown(missedButton);

                // Buttons
                Button doneButton = popupView.findViewById(R.id.DoneButton);
                Button cancelButton = popupView.findViewById(R.id.CancelButton);
                Button upperIncrement = popupView.findViewById(R.id.UpperIncrement);
                Button upperDecrement = popupView.findViewById(R.id.UpperDecrement);
                Button lowerIncrement = popupView.findViewById(R.id.LowerIncrement);
                Button lowerDecrement = popupView.findViewById(R.id.LowerDecrement);

                // Counter TextBoxes
                TextView upperScore = popupView.findViewById(R.id.UpperScore);
                TextView lowerScore = popupView.findViewById(R.id.LowerScore);

                // Temp variables
                upperScore.setText(GenUtils.padLeftZeros(autonHashMap.get("UpperPortMissed"), 3));
                lowerScore.setText(GenUtils.padLeftZeros(autonHashMap.get("LowerPortMissed"), 3));

                upperIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int upperPortNum = Integer.parseInt((String)upperScore.getText());
                        upperPortNum += 1;
                        upperScore.setText(GenUtils.padLeftZeros(Integer.toString(upperPortNum), 3));
                    }
                });

                upperDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int upperPortNum = Integer.parseInt((String)upperScore.getText());
                        if(upperPortNum > 0) {
                            upperPortNum -= 1;
                            upperScore.setText(GenUtils.padLeftZeros(Integer.toString(upperPortNum), 3));
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

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        autonHashMap.put("UpperPortMissed", (String)upperScore.getText());
                        autonHashMap.put("LowerPortMissed", (String)lowerScore.getText());
                        totalMissed = Integer.parseInt((String)upperScore.getText()) + Integer.parseInt((String)lowerScore.getText());
                        updateXMLObjects();
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

        crossedLineSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                autonHashMap.put("CrossedInitiationLine", isChecked ? "1" : "0");
                updateXMLObjects();
            }
        });

        fellOverSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setupHashMap.put("FellOver", isChecked ? "1" : "0");
                updateXMLObjects();
            }
        });
    }

    private void allButtonsEnabledState(boolean enable){
        pickedUpIncrementButton.setEnabled(enable);
        pickedUpDecrementButton.setEnabled(enable);
        droppedIncrementButton.setEnabled(enable);
        droppedDecrementButton.setEnabled(enable);
        scoredButton.setEnabled(enable);
        missedButton.setEnabled(enable);
        crossedLineSwitch.setEnabled(enable);
    }

    private void updateXMLObjects(){
        scoredCounter.setText(GenUtils.padLeftZeros(Integer.toString(totalScored), 3));
        missedCounter.setText(GenUtils.padLeftZeros(Integer.toString(totalMissed), 3));
        pickedUpCounter.setText(GenUtils.padLeftZeros(autonHashMap.get("NumberPickedUp"), 3));
        droppedCounter.setText(GenUtils.padLeftZeros(autonHashMap.get("NumberDropped"), 3));
        crossedLineSwitch.setChecked(autonHashMap.get("CrossedInitiationLine") == "1");
        if(setupHashMap.get("FellOver") == "1") {
            fellOverSwitch.setChecked(true);
            allButtonsEnabledState(false);
        } else {
            fellOverSwitch.setChecked(false);
            allButtonsEnabledState(true);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming visible, then...
            if (isVisibleToUser) {
                setupHashMap = HashMapManager.getSetupHashMap();
                autonHashMap = HashMapManager.getAutonHashMap();
                updateXMLObjects();
                //set all objects in the fragment to their values from the HashMaps
            } else {
                HashMapManager.putSetupHashMap(setupHashMap);
                HashMapManager.putAutonHashMap(autonHashMap);
                timer.cancel();
                timer = new Timer();
            }
        }
    }
}
