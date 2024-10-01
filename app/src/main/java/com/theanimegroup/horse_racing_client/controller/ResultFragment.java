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
import com.theanimegroup.horse_racing_client.utils.DataUtils;

import java.util.List;

public class ResultFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_layout, container, false);
        TextView winnerTextView = view.findViewById(R.id.tvWinner);
        TextView winningsTextView = view.findViewById(R.id.tvWinnings);
        TextView backTextView = view.findViewById(R.id.tvBack);
        int winHorse = DataUtils.getInstance().getWinHorse();
        Double result = DataUtils.getInstance().getTotalBet();
        winnerTextView.setText(String.format("Winner Horse: Horse %d", winHorse + 1));
        winningsTextView.setText("You won: $" + result.toString());
        backTextView.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        return view;
    }
}


