package com.theanimegroup.horse_racing_client.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.theanimegroup.horse_racing_client.R;

public class CashActivity extends AppCompatActivity {

    private EditText depositAmountEditText;
    private TextView saveTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_layout);

        depositAmountEditText = findViewById(R.id.depositAmount);
        saveTextView = findViewById(R.id.tvSave);

        saveTextView.setOnClickListener(v -> {
            double depositAmount = Double.parseDouble(depositAmountEditText.getText().toString());
            if (depositAmount > 0) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("depositAmount", depositAmount);
                setResult(RESULT_OK, resultIntent); // Return deposit amount to HomeActivity
                finish(); // Close CashActivity
            } else {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


