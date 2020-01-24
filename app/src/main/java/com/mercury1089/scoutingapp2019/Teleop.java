package com.mercury1089.scoutingapp2019;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mercury1089.scoutingapp2019.utils.GenUtils;
import java.util.LinkedHashMap;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Teleop extends Fragment {
    //HashMaps for sending QR data between screens
    private LinkedHashMap<String, String> setupHashMap;
    private LinkedHashMap<String, String> teleopHashMap;

    //RadioButtons
    private ImageButton pickedUpIncrementButton;
    private ImageButton pickedUpDecrementButton;
    private ImageButton droppedIncrementButton;
    private ImageButton droppedDecrementButton;
    private Button scoredButton;
    private Button missedButton;

    //Switches
    private Switch stageTwoButton;
    private Switch stageThreeButton;
    private Switch fellOverSwitch;

    //TextViews
    private TextView pickedUpCounter;
    private TextView droppedCounter;
    private TextView scoredCounter;
    private TextView missedCounter;
    private TextView stageTwoID;
    private TextView stageThreeID;

    //other variables
    private ConstraintLayout constraintLayout;
    private int totalScored;
    private int totalMissed;

    public static Teleop newInstance() {
        Teleop fragment = new Teleop();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private MatchActivity context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = (MatchActivity) getActivity();
        return inflater.inflate(R.layout.fragment_teleop, container, false);
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

        stageTwoButton = getView().findViewById(R.id.Stage2Switch);
        stageTwoID = getView().findViewById(R.id.IDStage2);
        stageThreeButton = getView().findViewById(R.id.Stage3Switch);
        stageThreeID = getView().findViewById(R.id.IDStage3);

        fellOverSwitch = getView().findViewById(R.id.FellOverSwitch);

        //set listeners for buttons
        pickedUpIncrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                int currentCount = Integer.parseInt((String)pickedUpCounter.getText());
                currentCount++;
                teleopHashMap.put("NumberPickedUp", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        pickedUpDecrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                int currentCount = Integer.parseInt((String)pickedUpCounter.getText());
                if(currentCount > 0)
                    currentCount--;
                teleopHashMap.put("NumberPickedUp", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        droppedIncrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                int currentCount = Integer.parseInt((String)droppedCounter.getText());
                currentCount++;
                teleopHashMap.put("NumberDropped", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        droppedDecrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                int currentCount = Integer.parseInt((String)droppedCounter.getText());
                if(currentCount > 0)
                    currentCount--;
                teleopHashMap.put("NumberDropped", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        scoredButton.setOnClickListener(new BootstrapButton.OnClickListener() {
            public void onClick(View view){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_scored, null);

                int width = (int)getResources().getDimension(R.dimen.scoring_popup_width);
                int height = (int)getResources().getDimension(R.dimen.scoring_popup_height);

                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
                popupWindow.showAsDropDown(scoredButton);

                // Bootstrap Buttons
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
                upperScore.setText(GenUtils.padLeftZeros(teleopHashMap.get("UpperPortScored"), 3));
                lowerScore.setText(GenUtils.padLeftZeros(teleopHashMap.get("LowerPortScored"), 3));

                checkToDisable(upperDecrement, upperScore, "upper");
                checkToDisable(lowerDecrement, lowerScore, "lower");

                upperIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int upperPortNum = Integer.parseInt((String)upperScore.getText());
                        upperPortNum += 1;
                        upperScore.setText(GenUtils.padLeftZeros(Integer.toString(upperPortNum), 3));
                        checkToDisable(upperDecrement, upperScore, "upper");
                    }
                });

                upperDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int upperPortNum = Integer.parseInt((String)upperScore.getText());
                        if(upperPortNum > 0) {
                            upperPortNum -= 1;
                            upperScore.setText(GenUtils.padLeftZeros(Integer.toString(upperPortNum), 3));
                            checkToDisable(upperDecrement, upperScore, "upper");
                        }
                    }
                });

                lowerIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int lowerPortNum = Integer.parseInt((String)lowerScore.getText());
                        lowerPortNum += 1;
                        lowerScore.setText(GenUtils.padLeftZeros(Integer.toString(lowerPortNum), 3));
                        checkToDisable(lowerDecrement, lowerScore, "lower");
                    }
                });

                lowerDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int lowerPortNum = Integer.parseInt((String)lowerScore.getText());
                        if(lowerPortNum > 0) {
                            lowerPortNum -= 1;
                            lowerScore.setText(GenUtils.padLeftZeros(Integer.toString(lowerPortNum), 3));
                            checkToDisable(lowerDecrement, lowerScore, "lower");
                        }
                    }
                });

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        teleopHashMap.put("UpperPortScored", (String)upperScore.getText());
                        // teleopHashMap.put("OuterPortScored", (String)outerScore.getText());
                        teleopHashMap.put("LowerPortScored", (String)lowerScore.getText());
                        totalScored = Integer.parseInt((String)upperScore.getText()) + Integer.parseInt((String)lowerScore.getText());
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

            private void checkToDisable(Button button, TextView text, String name){
                if(name.toLowerCase().equals("upper")) {
                    if (Integer.parseInt((String)text.getText()) == 0)
                        button.setEnabled(false);
                    else
                        button.setEnabled(true);
                } else {
                    if (Integer.parseInt((String)text.getText()) == 0)
                        button.setEnabled(false);
                    else
                        button.setEnabled(true);
                }
            }
        });

        missedButton.setOnClickListener(new BootstrapButton.OnClickListener() {
            public void onClick(View view){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_scored, null);

                int width = (int)getResources().getDimension(R.dimen.scoring_popup_width);
                int height = (int)getResources().getDimension(R.dimen.scoring_popup_height);

                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
                popupWindow.showAsDropDown(missedButton);

                // Bootstrap Buttons
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
                upperScore.setText(GenUtils.padLeftZeros(teleopHashMap.get("UpperPortMissed"), 3));
                lowerScore.setText(GenUtils.padLeftZeros(teleopHashMap.get("LowerPortMissed"), 3));

                checkToDisable(upperDecrement, upperScore, "upper");
                checkToDisable(lowerDecrement, lowerScore, "lower");

                upperIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int upperPortNum = Integer.parseInt((String)upperScore.getText());
                        upperPortNum += 1;
                        upperScore.setText(GenUtils.padLeftZeros(Integer.toString(upperPortNum), 3));
                        checkToDisable(upperDecrement, upperScore, "upper");
                    }
                });

                upperDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int upperPortNum = Integer.parseInt((String)upperScore.getText());
                        if(upperPortNum > 0) {
                            upperPortNum -= 1;
                            upperScore.setText(GenUtils.padLeftZeros(Integer.toString(upperPortNum), 3));
                            checkToDisable(upperDecrement, upperScore, "upper");
                        }
                    }
                });

                lowerIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int lowerPortNum = Integer.parseInt((String)lowerScore.getText());
                        lowerPortNum += 1;
                        lowerScore.setText(GenUtils.padLeftZeros(Integer.toString(lowerPortNum), 3));
                        checkToDisable(lowerDecrement, lowerScore, "lower");
                    }
                });

                lowerDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int lowerPortNum = Integer.parseInt((String)lowerScore.getText());
                        if(lowerPortNum > 0) {
                            lowerPortNum -= 1;
                            lowerScore.setText(GenUtils.padLeftZeros(Integer.toString(lowerPortNum), 3));
                            checkToDisable(lowerDecrement, lowerScore, "lower");
                        }
                    }
                });

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        teleopHashMap.put("UpperPortMissed", (String)upperScore.getText());
                        teleopHashMap.put("LowerPortMissed", (String)lowerScore.getText());
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

            private void checkToDisable(Button button, TextView text, String name){
                if(name.toLowerCase().equals("upper")) {
                    if (Integer.parseInt((String)text.getText()) == 0)
                        button.setEnabled(false);
                    else
                        button.setEnabled(true);
                } else {
                    if (Integer.parseInt((String)text.getText()) == 0)
                        button.setEnabled(false);
                    else
                        button.setEnabled(true);
                }
            }
        });

        stageTwoButton.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                teleopHashMap.put("StageTwo", isChecked ? "1" : "0");
                updateXMLObjects();
            }
        });

        stageThreeButton.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                teleopHashMap.put("StageThree", isChecked ? "1" : "0");
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
        pickedUpCounter.setEnabled(enable);

        droppedIncrementButton.setEnabled(enable);
        droppedDecrementButton.setEnabled(enable);
        droppedCounter.setEnabled(enable);

        scoredButton.setEnabled(enable);
        scoredCounter.setEnabled(enable);

        missedButton.setEnabled(enable);
        missedCounter.setEnabled(enable);

        stageTwoButton.setEnabled(enable);
        stageTwoID.setEnabled(enable);

        stageThreeButton.setEnabled(enable);
        stageThreeID.setEnabled(enable);
    }

    private void updateXMLObjects(){
        scoredCounter.setText(GenUtils.padLeftZeros(Integer.toString(totalScored), 3));
        missedCounter.setText(GenUtils.padLeftZeros(Integer.toString(totalMissed), 3));
        pickedUpCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("NumberPickedUp"), 3));
        droppedCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("NumberDropped"), 3));
        stageTwoButton.setChecked(teleopHashMap.get("StageTwo").equals("1"));
        stageThreeButton.setChecked(teleopHashMap.get("StageThree").equals("1"));

        if(setupHashMap.get("FellOver").equals("1")) {
            fellOverSwitch.setChecked(true);
            allButtonsEnabledState(false);
        } else {
            fellOverSwitch.setChecked(false);
            allButtonsEnabledState(true);
        }

        if(Integer.parseInt((String)pickedUpCounter.getText()) == 0)
            pickedUpDecrementButton.setEnabled(false);
        else
            pickedUpDecrementButton.setEnabled(true);
        if(Integer.parseInt((String)droppedCounter.getText()) == 0)
            pickedUpDecrementButton.setEnabled(false);
        else
            pickedUpDecrementButton.setEnabled(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming visible, then...
            if (isVisibleToUser) {
                setupHashMap = HashMapManager.getSetupHashMap();
                teleopHashMap = HashMapManager.getTeleopHashMap();
                updateXMLObjects();
                //set all objects in the fragment to their values from the HashMaps
            } else {
                HashMapManager.putSetupHashMap(setupHashMap);
                HashMapManager.putTeleopHashMap(teleopHashMap);
            }
        }
    }
}
