package com.theanimegroup.horse_racing_client.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.theanimegroup.horse_racing_client.R;
import com.theanimegroup.horse_racing_client.service.BetService;

import java.util.List;
import java.util.Random;

public class ResultFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_layout, container, false);
        TextView winnerTextView = view.findViewById(R.id.tvWinner);
        TextView winningsTextView = view.findViewById(R.id.tvWinnings);
        TextView backTextView = view.findViewById(R.id.tvBack);
        Double result = BetService.getInstance().calculateBet(List.of(1));
        int winningHorse = new Random().nextInt(3) + 1; // 1 to 3
        winnerTextView.setText("Winner Horse: Horse " + winningHorse);
        winningsTextView.setText("You won: $" + result.toString());
        backTextView.setOnClickListener(v -> {
            HomeFragment homeFragment = new HomeFragment();
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        return view;
    }
}


