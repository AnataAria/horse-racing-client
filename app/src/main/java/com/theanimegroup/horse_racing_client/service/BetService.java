package com.theanimegroup.horse_racing_client.service;

import com.theanimegroup.horse_racing_client.model.Bet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BetService {
    private static BetService instance;
    public Map<Integer, Bet> userBet;
    private BetService () {
        userBet = new HashMap<>();
    }

    public static BetService getInstance() {
        if(instance == null) instance = new BetService();
        return instance;
    }

    public void addBet(Bet bet) {
        Bet temp = getBet(bet.getHorseId());
        if (temp == null) {
            userBet.put(bet.getHorseId(), bet);
        }
        updateBet(bet);
    }
    public void betReset() {
        userBet.clear();
    }
    public void updateBet(Bet bet) {
        userBet.replace(bet.getHorseId(), bet);
    }
    public Bet getBet(Integer horseId) {
        return userBet.get(horseId);
    }

    public Double calculateBet(List<Integer> winningHorse) {
        double total = 0;
        for (Integer item: winningHorse) {
            Bet temp = userBet.get(item);
            if (temp != null) {
                total += temp.getBet();
            }
        }
        return total;
    }

    public Double totalBet() {
        double total = 0;
        for (Bet item: userBet.values()) {
            total += item.getBet();
        }
        return total;
    }

    public boolean validateBet() {
        for(Bet item: userBet.values()) {
            if(item.getBet() <= 1) {
                return false;
            }
        }
        return true;
    }
}
