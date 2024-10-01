package com.theanimegroup.horse_racing_client.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HorseRowBet {
    private int rowId;
    private boolean isTick;
    private Bet bet;
}
