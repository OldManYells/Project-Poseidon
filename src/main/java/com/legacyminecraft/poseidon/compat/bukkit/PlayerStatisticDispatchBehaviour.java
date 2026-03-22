package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.NetServerHandler;
import net.minecraft.server.Packet200Statistic;
import org.bukkit.Material;
import org.bukkit.Statistic;

/**
 * Canonical behaviour for CraftPlayer statistic validation/id resolution and packet dispatch.
 */
public final class PlayerStatisticDispatchBehaviour {
    private static final PlayerStatisticDispatchBehaviour INSTANCE = new PlayerStatisticDispatchBehaviour();

    private PlayerStatisticDispatchBehaviour() {
    }

    public static PlayerStatisticDispatchBehaviour getInstance() {
        return INSTANCE;
    }

    public void validateSubStatisticRequest(Statistic statistic, Material material) {
        if (!statistic.isSubstatistic()) {
            throw new IllegalArgumentException("Given statistic is not a substatistic");
        }
        if (statistic.isBlock() != material.isBlock()) {
            throw new IllegalArgumentException("Given material is not valid for this substatistic");
        }
    }

    public int resolveStatisticId(Statistic statistic, Material material) {
        int materialId = material.getId();
        if (!material.isBlock()) {
            materialId -= 255;
        }
        return statistic.getId() + materialId;
    }

    public void sendStatistic(NetServerHandler netServerHandler, int statisticId, int amount) {
        int remainingAmount = amount;
        while (remainingAmount > Byte.MAX_VALUE) {
            netServerHandler.sendPacket(new Packet200Statistic(statisticId, Byte.MAX_VALUE));
            remainingAmount -= Byte.MAX_VALUE;
        }
        netServerHandler.sendPacket(new Packet200Statistic(statisticId, remainingAmount));
    }
}
