package com.theanimegroup.horse_racing_client.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.theanimegroup.horse_racing_client.R;

public class ResultActivity extends AppCompatActivity {

    private TextView tvWinner, tvWinnings, tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);

        tvWinner = findViewById(R.id.tvWinner);
        tvWinnings = findViewById(R.id.tvWinnings);
        tvBack = findViewById(R.id.tvBack);

        // Get data
        Intent intent = getIntent();
        String winner = intent.getStringExtra("winner");
        double winnings = intent.getDoubleExtra("winnings", 0);
        double moneyLeft = intent.getDoubleExtra("moneyLeft", 0);

        // Display
        tvWinner.setText(winner);
        tvWinnings.setText(String.format("$%.2f", winnings));

        // Back
        tvBack.setOnClickListener(v -> finish());
    }
}


