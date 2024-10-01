package com.theanimegroup.horse_racing_client.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.theanimegroup.horse_racing_client.R;
import com.theanimegroup.horse_racing_client.model.Account;
import com.theanimegroup.horse_racing_client.utils.DataUtils;

public class LoginFragment extends Fragment {

    private EditText usernameEditText;
    private TextView startTextView, tutorialTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout, container, false);

        usernameEditText = view.findViewById(R.id.editText);
        startTextView = view.findViewById(R.id.tvStart);
        tutorialTextView = view.findViewById(R.id.tvTurtorial);

        startTextView.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            if (!username.isEmpty()) {
                HomeFragment homeFragment = new HomeFragment();
                DataUtils.getInstance().setCurrentUser(Account.builder()
                                .cash(5000)
                                .username(username)
                        .build());
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.container, homeFragment)
                        .addToBackStack(null)
                        .commit();
            } else {
                Toast.makeText(getActivity(), "Please enter a username", Toast.LENGTH_SHORT).show();
            }
        });

        tutorialTextView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TutorialActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
