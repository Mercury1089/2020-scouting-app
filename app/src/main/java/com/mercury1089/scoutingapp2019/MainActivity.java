package com.mercury1089.scoutingapp2019;

import com.mercury1089.scoutingapp2019.utils.GenUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import com.beardedhen.androidbootstrap.BootstrapButton;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //variables that store elements of the screen for the output variables
    private BootstrapButton settingsButton;
    private BootstrapButton pregameButton;

    public HashMap<String, String> setupHashMap;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    public void onStart() {
        super.onStart();

        //initializers
        settingsButton = findViewById(R.id.SettingsButton);
        pregameButton = findViewById(R.id.PregameButton);
        setupHashMap = new HashMap<>();

        //click methods
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.newInstance();
            }
        });

        pregameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pregame.newInstance();
            }
        });
    }
}