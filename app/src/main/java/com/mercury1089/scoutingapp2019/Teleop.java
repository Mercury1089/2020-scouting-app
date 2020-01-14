package com.mercury1089.scoutingapp2019;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import java.util.HashMap;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;


public class Teleop extends Fragment {
    //HashMaps for sending QR data between screens
    private HashMap<String, String> setupHashMap;
    private HashMap<String, String> teleopHashMap;

    //other variables
    private ConstraintLayout constraintLayout;
    private Switch fellOverSwitch;

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

        //Waiting for layout --> fellOverSwitch = context.findViewById(R.id.FellOverSwitch);
        setupHashMap = context.setupHashMap;
        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.TELEOP);
        teleopHashMap = HashMapManager.getTeleopHashMap();

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
