package com.mercury1089.scoutingapp2019;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.mercury1089.scoutingapp2019.utils.GenUtils;
import java.util.HashMap;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;


public class Teleop extends Fragment {
    //hashmaps for sending QR data between screens
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

    Activity context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        return inflater.inflate(R.layout.fragment_teleop, container, false);
    }

    public void onStart(){
        super.onStart();

        //linking variables to XML elements on the screen

        //Waiting for layout --> fellOverSwitch = context.findViewById(R.id.FellOverSwitch);

        setupHashMap = new HashMap<>();
        //teleopHashMap = context.teleopHashMap;

        //Waiting for layout --> constraintLayout = context.findViewById(R.id.layout);
        //Waiting for layout --> fellOverSwitch = context.findViewById(R.id.FellOverSwitch);

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

        getView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(v == getView() && hasFocus){
                    //teleopHashMap = context.teleopHashMap;
                } else if(!hasFocus){
                    //context.teleopHashMap = teleopHashMap;
                }
            }
        });
    }
}
