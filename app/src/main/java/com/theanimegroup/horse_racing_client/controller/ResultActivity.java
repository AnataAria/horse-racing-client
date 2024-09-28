package com.theanimegroup.horse_racing_client.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.horse_racing_client.R;

import java.util.Random;

public class ResultActivity extends AppCompatActivity {

    private TextView winnerTextView, winningsTextView, backTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);

        winnerTextView = findViewById(R.id.tvWinner);
        winningsTextView = findViewById(R.id.tvWinnings);
        backTextView = findViewById(R.id.tvBack);

        double betAmount = getIntent().getDoubleExtra("betAmount", 0);
        int selectedHorse = getIntent().getIntExtra("selectedHorse", 1);

        // Simulating a winning horse
        int winningHorse = new Random().nextInt(3) + 1; // 1 to 3
        winnerTextView.setText("Winner Horse: Horse " + winningHorse);

        if (selectedHorse == winningHorse) {
            double winnings = betAmount * 2; // Double the bet amount
            winningsTextView.setText("You won: $" + winnings);
        } else {
            winningsTextView.setText("You lost: $" + betAmount);
        }

        backTextView.setOnClickListener(v -> {
            // Return to HomeActivity
            finish();
        });
    }
}


