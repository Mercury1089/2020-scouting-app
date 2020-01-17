package com.mercury1089.scoutingapp2019;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //assigning variables to their equivalent screen elements
        Button localStorageResetButton = findViewById(R.id.LocalStorageResetButton);
        Button backButton = findViewById(R.id.BackButton);

        localStorageResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMapManager.setDefaultValues(HashMapManager.HASH.SETUP);
                HashMapManager.setDefaultValues(HashMapManager.HASH.AUTON);
                HashMapManager.setDefaultValues(HashMapManager.HASH.TELEOP);
                HashMapManager.setDefaultValues(HashMapManager.HASH.CLIMB);
                Toast.makeText(SettingsActivity.this, "All variables successfully reset.", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, PregameActivity.class);
                startActivity(intent);
            }
        });
    }
}