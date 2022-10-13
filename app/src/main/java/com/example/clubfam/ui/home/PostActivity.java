package com.example.clubfam.ui.home;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.example.clubfam.MainActivity;
import com.example.clubfam.R;
import com.example.clubfam.helpers.*;

public class PostActivity extends AppCompatActivity{
    private static final int IMAGE_ID = 1;
    EditText editTextTitle;
    EditText editTextEventTimeLocation;
    EditText editTextBody;
    PostData postData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        final Button submitButton = findViewById(R.id.submitButton);
        final Button cancelButton = findViewById(R.id.cancelButton);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextEventTimeLocation = (EditText) findViewById(R.id.editTextEventTimeLocation);
        editTextBody = (EditText) findViewById(R.id.editTextBody);

        postData = new PostData(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bitmap image = ((BitmapDrawable) image1.getDrawable().getBitmap());
                //new UploadImage(image,)
                Intent MainIntent = new Intent(PostActivity.this, MainActivity.class);

                String title = editTextTitle.getText().toString();
                String body = editTextBody.getText().toString();
                String eventTimeLocation = editTextEventTimeLocation.getText().toString();

                postData.addPost(title, body, eventTimeLocation);

                startActivity(MainIntent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(PostActivity.this, MainActivity.class);
                startActivity(MainIntent);
            }
        });
    }
}
