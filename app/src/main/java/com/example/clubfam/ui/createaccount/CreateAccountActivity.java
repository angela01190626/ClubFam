package com.example.clubfam.ui.createaccount;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clubfam.R;
import com.example.clubfam.ui.userdata.UserDataActivity;

public class CreateAccountActivity extends AppCompatActivity {

    public static final String TEMP_INFO = "temp_info";

    EditText nameEditText;
    EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        EditText passwordEditText = findViewById(R.id.password);
        EditText passwordConfirmEditText = findViewById(R.id.passwordConfirm);
        Button createAccountButton = findViewById(R.id.createButton);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(CreateAccountActivity.this, UserDataActivity.class);
                startActivity(MainIntent);
                loadingProgressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    public void onStop() {
        super.onStop();

        // assign inputs to shared preferences
        SharedPreferences.Editor editor = getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE).edit();
        editor.putString("user_name", nameEditText.getText().toString());
        editor.putString("user_email", emailEditText.getText().toString());
        editor.apply();
        editor.commit();
    }


}
