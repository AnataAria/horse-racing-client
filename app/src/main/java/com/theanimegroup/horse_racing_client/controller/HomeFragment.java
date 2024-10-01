package com.theanimegroup.horse_racing_client.controller;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.theanimegroup.horse_racing_client.R;
import com.theanimegroup.horse_racing_client.model.Bet;
import com.theanimegroup.horse_racing_client.service.BetService;
import com.theanimegroup.horse_racing_client.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {

    private CheckBox horse1, horse2, horse3;
    private EditText horse1Text, horse2Text, horse3Text;
    private SeekBar sbHorse1, sbHorse2, sbHorse3;
    private EditText betAmountEditText;
    private TextView cashTextView;
    private TextView balanceText;
    private int winner = -1;
    private final List<Handler> handlers = new ArrayList<>();
    private final List<Runnable> runnables = new ArrayList<>();
    private final List<Boolean> finished = new ArrayList<>();
    private double userBalance = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);
        // Init objects
        horse1 = view.findViewById(R.id.horse1);
        horse2 = view.findViewById(R.id.horse2);
        horse3 = view.findViewById(R.id.horse3);
        horse1Text = view.findViewById(R.id.horse1Text);
        horse2Text = view.findViewById(R.id.horse2Text);
        horse3Text = view.findViewById(R.id.horse3Text);
        sbHorse1 = view.findViewById(R.id.seekBar);
        sbHorse2 = view.findViewById(R.id.seekBar2);
        sbHorse3 = view.findViewById(R.id.seekBar3);

        sbHorse1.setEnabled(false);
        sbHorse2.setEnabled(false);
        sbHorse3.setEnabled(false);

        betAmountEditText = view.findViewById(R.id.betAmount);
        balanceText = view.findViewById(R.id.editTextNumber3);
        cashTextView = view.findViewById(R.id.tvCash);
        TextView goTextView = view.findViewById(R.id.tvGo);

        // Disable edit texts initially
        horse1Text.setEnabled(false);
        horse2Text.setEnabled(false);
        horse3Text.setEnabled(false);

        // Add event listeners
        initEventClickChange();
        initEventBetChange(horse1Text, 1);
        initEventBetChange(horse2Text, 2);
        initEventBetChange(horse3Text, 3);

        initRaceHandlers();
        cashTextView.setOnClickListener(v -> {
            CashFragment cashFragment = new CashFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .add(R.id.container, cashFragment)
                    .addToBackStack(null)
                    .commit();
        });

        goTextView.setOnClickListener(v -> {
            if (BetService.getInstance().validateBet()) {
                double betAmount = Double.parseDouble(betAmountEditText.getText().toString());
                DataUtils.getInstance().setTotalBet(betAmount);
                if (DataUtils.getInstance().getCurrentUser().getCash() < betAmount) {
                    Toast.makeText(getActivity(), "Insufficient balance!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    startRace();
                }
            } else {
                Toast.makeText(getActivity(), "Please select a horse and enter a valid bet amount", Toast.LENGTH_SHORT).show();
            }
        });
        updateBetPrice ();
        balanceText.setText(String.valueOf(DataUtils.getInstance().getCurrentUser().getCash()));
        return view;
    }

    private void initEventBetChange(EditText editText, int horseId) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (((CheckBox) editText.getTag()).isChecked()) {
                    String newData = s.toString();

                    if (!newData.isEmpty()) {
                        double betAmount = Double.parseDouble(editText.getText().toString());
                        Bet bet = Bet.builder()
                                .horseId(horseId)
                                .username(DataUtils.getInstance().getCurrentUser().getUsername())
                                .bet(betAmount)
                                .build();
                        BetService.getInstance().addBet(bet);
                        balanceText.setText(String.valueOf(DataUtils.getInstance().getCurrentUser().getCash()));
                    }
                }
                updateBetPrice ();
            }
        });
        editText.setTag(getCorrespondingCheckBox(editText));
    }

    private void initEventClickChange() {
        horse1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            handleHorseSelection(isChecked, horse1Text, 1);
            updateBetPrice ();
        });

        horse2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            handleHorseSelection(isChecked, horse2Text, 2);
            updateBetPrice ();
        });

        horse3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            handleHorseSelection(isChecked, horse3Text, 3);
            updateBetPrice ();
        });
    }

    private void handleHorseSelection(boolean isChecked, EditText horseText, int horseId) {
        if (isChecked) {
            horseText.setEnabled(true);
            Bet temp = Bet.builder()
                    .horseId(horseId)
                    .bet(0)
                    .build();
            BetService.getInstance().addBet(temp);
        } else {
            BetService.getInstance().betReset();
            horseText.setEnabled(false);
            horseText.setText("");
        }
    }

    private CheckBox getCorrespondingCheckBox(EditText editText) {
        if (editText == horse1Text) {
            return horse1;
        } else if (editText == horse2Text) {
            return horse2;
        } else if (editText == horse3Text) {
            return horse3;
        }
        return null;
    }


    @Override
    public void onResume() {
        super.onResume();
        updateBetPrice();
    }

    private void updateBetPrice () {
        betAmountEditText.setText(BetService.getInstance().totalBet().toString());
    }

    private void startRace() {
        stopRace();
        resetRace();
        for (int i = 0; i < handlers.size(); i++) {
            handlers.get(i).post(runnables.get(i));
        }
    }

    private void initRaceHandlers() {
        SeekBar[] seekBars = {sbHorse1, sbHorse2, sbHorse3};

        for (int i = 0; i < seekBars.length; i++) {
            int finalI = i;
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                int progress = 0;

                @Override
                public void run() {
                    Random random = new Random();
                    int increment = random.nextInt(6);  // Random step 0-15
                    progress += increment;
                    seekBars[finalI].setProgress(progress);

                    if (progress < 100) {
                        handler.postDelayed(this, 100);
                    } else {
                        finished.set(finalI, true);
                        if (winner == -1) {
                            winner = finalI;
                        }
                        if (!finished.contains(false)) {
                            announceWinner();
                        }
                    }
                }
            };
            handlers.add(handler);
            runnables.add(runnable);
            finished.add(false);
        }
    }

    private void announceWinner() {

        double winnings = BetService.getInstance().calculateBet(winner);

        userBalance += winnings;

        requireActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .add(R.id.container, new ResultFragment())
                .addToBackStack(null)
                .commit();
        resetRace();

    }

    private void resetRace() {
        winner = -1;
        sbHorse1.setProgress(0);
        sbHorse2.setProgress(0);
        sbHorse3.setProgress(0);

        stopRace();
    }

    private void stopRace() {
        for (Handler handler : handlers) {
            handler.removeCallbacksAndMessages(null);
        }

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}