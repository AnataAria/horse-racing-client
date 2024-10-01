package com.theanimegroup.horse_racing_client.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.theanimegroup.horse_racing_client.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    private CheckBox horse1, horse2, horse3;
    private EditText betAmountEditText1, betAmountEditText2, betAmountEditText3;
    private TextView goTextView, money;
    private SeekBar sbHorse1, sbHorse2, sbHorse3;
    private double userBalance = 100.0;
    private int winner = -1;
    private final List<Handler> handlers = new ArrayList<>();
    private final List<Runnable> runnables = new ArrayList<>();
    private final List<Boolean> finished = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        horse1 = findViewById(R.id.horse1);
        horse2 = findViewById(R.id.horse2);
        horse3 = findViewById(R.id.horse3);

        betAmountEditText1 = findViewById(R.id.editTextNumber4);
        betAmountEditText2 = findViewById(R.id.editTextNumber5);
        betAmountEditText3 = findViewById(R.id.editTextNumber6);

        goTextView = findViewById(R.id.tvGo);
        money = findViewById(R.id.editTextNumber3);

        sbHorse1 = findViewById(R.id.seekBar);
        sbHorse2 = findViewById(R.id.seekBar2);
        sbHorse3 = findViewById(R.id.seekBar3);

        money.setText(String.valueOf(userBalance));

        // Initialize Handlers and Finished List
        initRaceHandlers();

        goTextView.setOnClickListener(v -> {
            double bet1 = getBetAmount(betAmountEditText1);
            double bet2 = getBetAmount(betAmountEditText2);
            double bet3 = getBetAmount(betAmountEditText3);

            if ((bet1 > 0 || bet2 > 0 || bet3 > 0) && (horse1.isChecked() || horse2.isChecked() || horse3.isChecked())) {
                double totalBet = bet1 + bet2 + bet3;
                if (userBalance >= totalBet) {
                    userBalance -= totalBet;
                    money.setText(String.format("$%.2f", userBalance));
                    startRace();
                } else {
                    Toast.makeText(HomeActivity.this, "Insufficient balance!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(HomeActivity.this, "Please select a horse and enter a valid bet amount.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private double getBetAmount(EditText betEditText) {
        String betText = betEditText.getText().toString();
        return betText.isEmpty() ? 0 : Double.parseDouble(betText);
    }

    // Start the race by running the Handlers for each horse
    private void startRace() {
        stopRace();
        resetRace();
        for (int i = 0; i < handlers.size(); i++) {
            handlers.get(i).post(runnables.get(i));
        }
    }

    // Initialize Handlers and Runnables for each horse
    private void initRaceHandlers() {
        SeekBar[] seekBars = {sbHorse1, sbHorse2, sbHorse3};

        for (int i = 0; i < seekBars.length; i++) {
            int finalI = i;
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                int progress = 0;

                @Override
                public void run() {
                    Random random = new Random();
                    int increment = random.nextInt(16);  // Random step 0-15
                    progress += increment;
                    seekBars[finalI].setProgress(progress);

                    if (progress < 100) {
                        handler.postDelayed(this, 1000);
                    } else {
                        finished.set(finalI, true);
                        if (winner == -1) {
                            winner = finalI;
                        }
                        if (!finished.contains(false)) {
                            announceWinner();
                        }
                    }
                }
            };
            handlers.add(handler);
            runnables.add(runnable);
            finished.add(false);
        }
    }

    // Pass result
    private void announceWinner() {
        double bet1 = getBetAmount(betAmountEditText1);
        double bet2 = getBetAmount(betAmountEditText2);
        double bet3 = getBetAmount(betAmountEditText3);

        double winnings = 0;
        if (winner == 0 && horse1.isChecked()) {
            winnings = bet1 * 2;
        } else if (winner == 1 && horse2.isChecked()) {
            winnings = bet2 * 2;
        } else if (winner == 2 && horse3.isChecked()) {
            winnings = bet3 * 2;
        }

        userBalance += winnings;
        money.setText(String.format("$%.2f", userBalance));

        Toast.makeText(HomeActivity.this, winnings > 0 ? "You won $" + winnings : "You lost your bet", Toast.LENGTH_LONG).show();

        // Pass result to ResultActivity
        Intent resultIntent = new Intent(HomeActivity.this, ResultActivity.class);
        resultIntent.putExtra("winner", "Horse " + (winner + 1));
        resultIntent.putExtra("winnings", winnings);
        resultIntent.putExtra("moneyLeft", userBalance);
        startActivity(resultIntent);
        resetRace();
    }

    // Stop any ongoing race by clearing all handlers
    private void stopRace() {
        for (Handler handler : handlers) {
            handler.removeCallbacksAndMessages(null); // Remove all callbacks and stop the handlers
        }

    }

    // Reset the race and UI elements
    private void resetRace() {
        winner = -1;
        finished.clear();
        sbHorse1.setProgress(0);
        sbHorse2.setProgress(0);
        sbHorse3.setProgress(0);

        betAmountEditText1.setText("");
        betAmountEditText2.setText("");
        betAmountEditText3.setText("");
        horse1.setChecked(false);
        horse2.setChecked(false);
        horse3.setChecked(false);

        // Force UI update to ensure the SeekBars visually reset
        sbHorse1.invalidate();
        sbHorse2.invalidate();
        sbHorse3.invalidate();

        for (int i = 0; i < 3; i++) {
            finished.add(false);
        }
        stopRace();
    }
}