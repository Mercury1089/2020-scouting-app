package com.mercury1089.scoutingapp2019;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    //BootstrapButtons
    Button scoredButton;
    Button missedButton;

    //other variables
    private ConstraintLayout constraintLayout;
    private Switch fellOverSwitch;
    private boolean secondTime = false;

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
        scoredButton = getView().findViewById(R.id.ScoredButton);
        missedButton = getView().findViewById(R.id.MissedButton);

        //Waiting for layout --> fellOverSwitch = context.findViewById(R.id.FellOverSwitch);
        setupHashMap = context.setupHashMap;
        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.TELEOP);
        teleopHashMap = HashMapManager.getTeleopHashMap();
        //constraintLayout = getView().findViewById(R.id.teleop);

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
                View popupView = inflater.inflate(R.layout.popup_scored_or_missed, null);

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
                innerScore.setText(GenUtils.padLeftZeros(teleopHashMap.get("InnerPortScored"), 3));
                outerScore.setText(GenUtils.padLeftZeros(teleopHashMap.get("OuterPortScored"), 3));
                lowerScore.setText(GenUtils.padLeftZeros(teleopHashMap.get("LowerPortScored"), 3));

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
                        teleopHashMap.put("InnerPortScored", (String)innerScore.getText());
                        teleopHashMap.put("OuterPortScored", (String)outerScore.getText());
                        teleopHashMap.put("LowerPortScored", (String)lowerScore.getText());
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
                View popupView = inflater.inflate(R.layout.popup_scored_or_missed, null);

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
                innerScore.setText(GenUtils.padLeftZeros(teleopHashMap.get("InnerPortMissed"), 3));
                outerScore.setText(GenUtils.padLeftZeros(teleopHashMap.get("OuterPortMissed"), 3));
                lowerScore.setText(GenUtils.padLeftZeros(teleopHashMap.get("LowerPortMissed"), 3));

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
                        teleopHashMap.put("InnerPortMissed", (String)innerScore.getText());
                        teleopHashMap.put("OuterPortMissed", (String)outerScore.getText());
                        teleopHashMap.put("LowerPortMissed", (String)lowerScore.getText());
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
                teleopHashMap = HashMapManager.getTeleopHashMap();
                //set all objects in the fragment to their values from the HashMaps
            } else {
                context.setupHashMap = setupHashMap;
                HashMapManager.putTeleopHashMap(teleopHashMap);
            }
        }
    }
}
