package com.legacyminecraft.compat.bukkit;

public class Packet200Statistic extends Packet {
    public final int statisticId;
    public final int amount;

    public Packet200Statistic(int statisticId, int amount) {
        this.statisticId = statisticId;
        this.amount = amount;
    }
}
