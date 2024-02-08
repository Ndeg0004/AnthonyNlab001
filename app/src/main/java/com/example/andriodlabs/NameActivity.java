package com.example.andriodlabs;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_activity);

        TextView nameTextView = findViewById(R.id.textView);
        Button dontCallMeThatButton = findViewById(R.id.DontButton);
        Button thankYouButton = findViewById(R.id.youButton);

        // this gets the users name form the MainActivity
        String userName = getIntent().getStringExtra("name");
        String welcomeMessage = "Welcome " + userName + "!";
        nameTextView.setText(welcomeMessage);
        dontCallMeThatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }});
        thankYouButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1);
                finish();

            }});}}

