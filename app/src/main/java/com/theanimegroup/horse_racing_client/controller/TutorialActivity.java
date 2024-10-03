package com.theanimegroup.horse_racing_client.controller;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.horse_racing_client.R;

public class TutorialActivity extends AppCompatActivity {

    private TextView closeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.turtorial_layout);
        closeTextView = findViewById(R.id.tvClose);
        closeTextView.setOnClickListener(v -> finish());
    }
}
