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

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private TextView startTextView, tutorialTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        usernameEditText = findViewById(R.id.editText);
        startTextView = findViewById(R.id.tvStart);
        tutorialTextView = findViewById(R.id.tvTurtorial);

        startTextView.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            if (!username.isEmpty()) {
                // Start HomeActivity with username
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            }
        });

        tutorialTextView.setOnClickListener(v -> {
            // Start TutorialActivity
            Intent intent = new Intent(LoginActivity.this, TutorialActivity.class);
            startActivity(intent);
        });
    }
}
