package com.legacyminecraft.compat.bukkit;


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

    public void validateSubStatisticRequest(Object statistic, Object material) {
        boolean subStatistic = (Boolean) BridgeReflection.invoke(statistic, "isSubstatistic");
        if (!subStatistic) {
            throw new IllegalArgumentException("Given statistic is not a substatistic");
        }
        boolean statisticIsBlock = (Boolean) BridgeReflection.invoke(statistic, "isBlock");
        boolean materialIsBlock = (Boolean) BridgeReflection.invoke(material, "isBlock");
        if (statisticIsBlock != materialIsBlock) {
            throw new IllegalArgumentException("Given material is not valid for this substatistic");
        }
    }

    public int resolveStatisticId(Object statistic, Object material) {
        int materialId = ((Number) BridgeReflection.invoke(material, "getId")).intValue();
        boolean materialIsBlock = (Boolean) BridgeReflection.invoke(material, "isBlock");
        if (!materialIsBlock) {
            materialId -= 255;
        }
        int statisticId = ((Number) BridgeReflection.invoke(statistic, "getId")).intValue();
        return statisticId + materialId;
    }

    public void sendStatistic(Object netServerHandler, int statisticId, int amount) {
        int remainingAmount = amount;
        while (remainingAmount > Byte.MAX_VALUE) {
            BridgeReflection.invoke(netServerHandler, "sendPacket", new Packet200Statistic(statisticId, Byte.MAX_VALUE));
            remainingAmount -= Byte.MAX_VALUE;
        }
        BridgeReflection.invoke(netServerHandler, "sendPacket", new Packet200Statistic(statisticId, remainingAmount));
    }
}
