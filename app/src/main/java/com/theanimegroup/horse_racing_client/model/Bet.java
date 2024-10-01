package com.theanimegroup.horse_racing_client.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Bet {
    private int horseId;
    private double bet;
    private String username;
}
