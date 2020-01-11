package com.mercury1089.scoutingapp2019;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


public class Auton extends Fragment {
    //HashMaps for sending QR data between screens
    private HashMap<String, String> setupHashMap;
    private HashMap<String, String> autonHashMap;

    //other variables
    private static Timer timer = new Timer();
    private boolean firstTime = true;
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

        //Waiting for layout --> constraintLayout = context.findViewById(R.id.layout);
        //Waiting for layout --> fellOverSwitch = context.findViewById(R.id.FellOverSwitch);

        //initialize hash maps and fill in default data
        setupHashMap = context.setupHashMap;
        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.AUTON);
        autonHashMap = HashMapManager.getAutonHashMap();

        //switch to the next screen with data after 15 seconds
        TimerTask switchToTeleop = new TimerTask() {
            @Override
            public void run() {
                Handler handler = new Handler(context.getMainLooper());
                Runnable switchToTeleop = new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Switching to Teleop...", Toast.LENGTH_SHORT).show();
                        TabLayout tabs = context.findViewById(R.id.tabs);
                        ViewPager viewPager = context.findViewById(R.id.view_pager);
                        viewPager.setCurrentItem(1);
                        tabs.setupWithViewPager(viewPager);
                    }
                };
                handler.post(switchToTeleop);
            }
        };
        if(firstTime) {
            timer.schedule(switchToTeleop, 5000);
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
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
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
        timer.purge();
        timer = new Timer();
    }
}
