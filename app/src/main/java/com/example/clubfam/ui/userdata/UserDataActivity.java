package com.example.clubfam.ui.userdata;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clubfam.MainActivity;
import com.example.clubfam.R;

public class UserDataActivity extends AppCompatActivity {

    private static final String TEMP_INFO="temp_info";

    EditText ageEditText;
    EditText majorEditText;
    EditText interestsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        ageEditText = findViewById(R.id.age);
        majorEditText = findViewById(R.id.major);
        interestsEditText = findViewById(R.id.interests);
        final Button addButton = findViewById(R.id.addButton);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(UserDataActivity.this, MainActivity.class);
                startActivity(MainIntent);
                loadingProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onStop() {
        super.onStop();

        // assign inputs to shared preferences
        SharedPreferences.Editor editor = getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE).edit();
        editor.putString("user_age", ageEditText.getText().toString());
        editor.putString("user_major", majorEditText.getText().toString());
        editor.putString("user_interests", interestsEditText.getText().toString());
        editor.apply();
        editor.commit();
    }
}
