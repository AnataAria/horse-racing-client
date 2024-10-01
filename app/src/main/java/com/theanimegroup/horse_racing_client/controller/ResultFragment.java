package com.theanimegroup.horse_racing_client.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
        Double result = BetService.getInstance().calculateBet(winHorse);
        DataUtils.getInstance().getCurrentUser().setCash(result + DataUtils.getInstance().getCurrentUser().getCash());
        String resultText = result.doubleValue() > 0.0 ? "You won: $" + result.toString() : "You lose $" + result.toString();
        winnerTextView.setText(String.format("Winner Horse: Horse %d", winHorse + 1));
        winningsTextView.setText(resultText);
        backTextView.setOnClickListener(v -> {
            FragmentManager manager = requireActivity().getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();
            manager.beginTransaction()
                    .replace(R.id.container, homeFragment).commit();
            manager.findFragmentById(R.id.container);
        });
        return view;
    }
}


