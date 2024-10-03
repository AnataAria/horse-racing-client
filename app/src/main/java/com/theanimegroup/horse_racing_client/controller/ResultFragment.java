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
import com.theanimegroup.horse_racing_client.constant.AudioStage;
import com.theanimegroup.horse_racing_client.service.AudioMixer;
import com.theanimegroup.horse_racing_client.service.BetService;
import com.theanimegroup.horse_racing_client.utils.DataUtils;



public class ResultFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_layout, container, false);
        TextView winnerTextView = view.findViewById(R.id.tvWinner);
        TextView winningsTextView = view.findViewById(R.id.tvWinnings);
        TextView backTextView = view.findViewById(R.id.tvBack);
        int winHorse = DataUtils.getInstance().getWinHorse();
        Double result = BetService.getInstance().calculateBet(winHorse + 1);
        DataUtils.getInstance().getCurrentUser().setCash(result + DataUtils.getInstance().getCurrentUser().getCash());
        winnerTextView.setText(String.format("Winner Horse: Horse %d", winHorse + 1));
        if (result > 0) {
            String resultText = "You won: $" + result.toString();
            winningsTextView.setText(resultText);
            AudioMixer.getInstance().playAudio(AudioStage.WIN, getContext());
        } else {
            String resultText = "You lose $" + result.toString();
            winningsTextView.setText(resultText);
            AudioMixer.getInstance().playAudio(AudioStage.LOSE, getContext());
        }
        backTextView.setOnClickListener(v -> {
            FragmentManager manager = requireActivity().getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();
            manager.beginTransaction()
                    .replace(R.id.container, homeFragment).commit();
            manager.findFragmentById(R.id.container);
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DataUtils.getInstance().setWinHorse(-1);
        BetService.getInstance().betReset();
    }
}


