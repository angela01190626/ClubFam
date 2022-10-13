package com.example.clubfam.ui.contactdevelopers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clubfam.MainActivity;
import com.example.clubfam.R;

public class ContactDevelopersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_developers);

        final Button saveButtonC = findViewById(R.id.saveButtonC);
        final Button cancelButtonC = findViewById(R.id.cancelButtonC);

        saveButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(ContactDevelopersActivity.this, MainActivity.class);
                startActivity(MainIntent);
            }
            // TODO: add feeedback
        });

        cancelButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(ContactDevelopersActivity.this, MainActivity.class);
                startActivity(MainIntent);
            }
        });
    }
}
