package com.mercury1089.scoutingapp2019;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TabHost;

import com.mercury1089.scoutingapp2019.utils.GenUtils;
import com.mercury1089.scoutingapp2019.utils.LocationGroup;
import com.mercury1089.scoutingapp2019.utils.LocationGroupList;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;


public class Auton extends Fragment {
    //hashmaps for sending QR data between screens
    private HashMap<String, String> setupHashMap;
    private HashMap<String, String> autonHashMap;

    //other variables
    private Timer timer;
    private ConstraintLayout constraintLayout;
    private Switch fellOverSwitch;

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

        //Waiting for layout --> fellOverSwitch = context.findViewById(R.id.FellOverSwitch);

        timer = new Timer();
        //Waiting for layout --> constraintLayout = context.findViewById(R.id.layout);
        //Waiting for layout --> fellOverSwitch = context.findViewById(R.id.FellOverSwitch);

        //initialize hash maps and fill in default data
        setupHashMap = context.setupHashMap;
        autonHashMap = context.autonHashMap;

        //switch to the next screen with data after 15 seconds
        TimerTask switchToTeleop = new TimerTask() {
            @Override
            public void run() {
                /*context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Template for iterating through screen elements
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
                        for (LocationGroup lg : LocationGroupList.list.values())
                            if (lg.getCounter() > 0)
                                autonHashMap.put(lg.getCounterText().getTag().toString(), lg.getCounterText().getText().toString());
                    }
                });*/
                TabHost host = (TabHost) context.findViewById(android.R.id.tabs);
                host.setCurrentTab(1);
            }
        };
        timer.schedule(switchToTeleop, 15000);

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
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                context.setupHashMap = setupHashMap;
                context.autonHashMap = autonHashMap;
            }
        }
    }
}
