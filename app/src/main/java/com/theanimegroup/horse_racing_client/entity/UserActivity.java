package com.theanimegroup.horse_racing_client.entity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.horse_racing_client.R;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    private ArrayList<User> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);


        itemList = new ArrayList<>();
        itemList.add(new User("johnDoe", "password1", "John Doe", R.drawable.figure1, 100));
        itemList.add(new User("janeDoe", "password2", "Jane Doe", R.drawable.figure2, 200));
        itemList.add(new User("mikeSmith", "password3", "Mike Smith", R.drawable.figure3, 150));
        itemList.add(new User("susanLee", "password4", "Susan Lee", R.drawable.figure4, 180));
        itemList.add(new User("tomBrown", "password5", "Tom Brown", R.drawable.figure5, 220));

    }

}
