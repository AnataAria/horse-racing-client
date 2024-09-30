package com.theanimegroup.horse_racing_client.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.theanimegroup.horse_racing_client.R;
import com.theanimegroup.horse_racing_client.entity.User;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private CheckBox horse1, horse2, horse3;
    private EditText betAmountEditText;
    private TextView cashTextView, goTextView;
    private double userBalance = 0; // Assuming starting balance is 0
    private String username;
    private EditText editTextNumber3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        horse1 = findViewById(R.id.horse1);
        horse2 = findViewById(R.id.horse2);
        horse3 = findViewById(R.id.horse3);
        betAmountEditText = findViewById(R.id.betAmount);
        cashTextView = findViewById(R.id.tvCash);
        goTextView = findViewById(R.id.tvGo);
        editTextNumber3 = findViewById(R.id.editTextNumber3); // Initialize your EditText
        username = getIntent().getStringExtra("username");
        userBalance = getIntent().getDoubleExtra("money", 0);
        editTextNumber3.setText(String.valueOf(userBalance));
        cashTextView.setOnClickListener(v -> {
            // Start CashActivity
            Intent intent = new Intent(HomeActivity.this, CashActivity.class);
            startActivityForResult(intent, 1); // Request code for cash activity
        });

        goTextView.setOnClickListener(v -> {
            double betAmount = Double.parseDouble(betAmountEditText.getText().toString());
            if (betAmount > 0 && (horse1.isChecked() || horse2.isChecked() || horse3.isChecked())) {
                // Validate the bet amount and selected horse
                int selectedHorse = getSelectedHorse();
                Intent intent = new Intent(HomeActivity.this, ResultActivity.class);
                intent.putExtra("betAmount", betAmount);
                intent.putExtra("selectedHorse", selectedHorse);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please select a horse and enter a valid bet amount", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getSelectedHorse() {
        if (horse1.isChecked()) return 1;
        if (horse2.isChecked()) return 2;
        return 3; // horse3 is checked
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            userBalance += data.getDoubleExtra("depositAmount", 0);
            editTextNumber3.setText(String.valueOf(userBalance)); // Update balance in EditText
        }
    }

}

