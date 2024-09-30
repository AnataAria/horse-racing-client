package com.theanimegroup.horse_racing_client.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.horse_racing_client.R;
import com.theanimegroup.horse_racing_client.entity.User;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private TextView startTextView, tutorialTextView;
    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        usernameEditText = findViewById(R.id.editText);
        startTextView = findViewById(R.id.tvStart);
        tutorialTextView = findViewById(R.id.tvTurtorial);

        initializeUserList();

        startTextView.setOnClickListener(v -> handleStartClick());

        tutorialTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, TutorialActivity.class);
            startActivity(intent);
        });
    }


    private void initializeUserList() {
        userList = new ArrayList<>();
        userList.add(new User("user1", "password1", "John Doe", R.drawable.figure1, 100));
        userList.add(new User("user2", "password2", "Jane Doe", R.drawable.figure2, 200));
        userList.add(new User("user3", "password3", "Mike Smith", R.drawable.figure3, 150));
        userList.add(new User("user4", "password4", "Susan Lee", R.drawable.figure4, 180));
        userList.add(new User("user5", "password5", "Tom Brown", R.drawable.figure5, 220));
    }


    private void handleStartClick() {
        String username = usernameEditText.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        User matchedUser = null;
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                matchedUser = user;
                break;
            }
        }

        if (matchedUser != null) {
            // Pass username and money to HomeActivity
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("username", matchedUser.getUsername());
            intent.putExtra("money", matchedUser.getMoney()); // Pass the user's money
            startActivity(intent);
        } else {
            Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show();
        }
    }

}
