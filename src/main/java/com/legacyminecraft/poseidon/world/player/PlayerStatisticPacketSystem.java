package com.legacyminecraft.poseidon.world.player;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet200Statistic;
import net.minecraft.server.Statistic;

import java.util.ArrayList;
import java.util.List;

/**
 * Canonical statistic packet batching for legacy player wrappers.
 */
public final class PlayerStatisticPacketSystem {
    private static final int MAX_PACKET_AMOUNT = 100;
    private static final PlayerStatisticPacketSystem INSTANCE = new PlayerStatisticPacketSystem();

    private PlayerStatisticPacketSystem() {
    }

    public static PlayerStatisticPacketSystem getInstance() {
        return INSTANCE;
    }

    public void sendStatisticPackets(EntityPlayer player, Statistic statistic, int amount) {
        if (!shouldSendStatistic(statistic)) {
            return;
        }

        List<Integer> packetAmounts = splitIntoPacketAmounts(amount, MAX_PACKET_AMOUNT);
        for (int i = 0; i < packetAmounts.size(); ++i) {
            player.netServerHandler.sendPacket(new Packet200Statistic(statistic.e, packetAmounts.get(i).intValue()));
        }
    }

    public boolean shouldSendStatistic(Statistic statistic) {
        return statistic != null && !statistic.g;
    }

    public List<Integer> splitIntoPacketAmounts(int amount, int maxPacketAmount) {
        List<Integer> packetAmounts = new ArrayList<Integer>();
        int remaining = amount;
        while (remaining > maxPacketAmount) {
            packetAmounts.add(Integer.valueOf(maxPacketAmount));
            remaining -= maxPacketAmount;
        }
        packetAmounts.add(Integer.valueOf(remaining));
        return packetAmounts;
    }
}
