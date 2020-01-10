package com.mercury1089.scoutingapp2019;

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


public class Endgame extends Fragment {
    //HashMaps for sending QR data between screens
    private HashMap<String, String> setupHashMap;
    private HashMap<String, String> endgameHashMap;

    //other variables
    private ConstraintLayout constraintLayout;
    private Switch fellOverSwitch;

    public static Endgame newInstance() {
        Endgame fragment = new Endgame();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private MatchActivity context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = (MatchActivity) getActivity();
        return inflater.inflate(R.layout.fragment_endgame, container, false);
    }

    public void onStart(){
        super.onStart();

        //linking variables to XML elements on the screen

        //Waiting for layout --> fellOverSwitch = context.findViewById(R.id.FellOverSwitch);
        setupHashMap = context.setupHashMap;
        endgameHashMap = context.endgameHashMap;

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
                context.endgameHashMap = endgameHashMap;
            }
        }
    }
}
