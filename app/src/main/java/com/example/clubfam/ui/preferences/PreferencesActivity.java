package com.example.clubfam.ui.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clubfam.MainActivity;
import com.example.clubfam.R;

public class PreferencesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        final Button saveButton = findViewById(R.id.saveButton);
        final Button cancelButton = findViewById(R.id.cancelButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(PreferencesActivity.this, MainActivity.class);
                startActivity(MainIntent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(PreferencesActivity.this, MainActivity.class);
                startActivity(MainIntent);
            }
        });
    }
}
