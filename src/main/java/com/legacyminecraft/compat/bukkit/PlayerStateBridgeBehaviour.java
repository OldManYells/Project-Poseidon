package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftPlayer low-level state/session/time bridge operations.
 */
public final class PlayerStateBridgeBehaviour {
    private static final PlayerStateBridgeBehaviour INSTANCE = new PlayerStateBridgeBehaviour();

    private PlayerStateBridgeBehaviour() {
    }

    public static PlayerStateBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isPlayer() {
        return true;
    }

    public void setSneaking(Object player, boolean sneaking) {
        BridgeReflection.invoke(player, "setSneak", sneaking);
    }

    public boolean isSneaking(Object player) {
        return (Boolean) BridgeReflection.invoke(player, "isSneaking");
    }

    public void loadData(Object serverConfigurationManager, Object player) {
        Object playerFileData = BridgeReflection.getField(serverConfigurationManager, "playerFileData");
        BridgeReflection.invoke(playerFileData, "b", player);
    }

    public void saveData(Object serverConfigurationManager, Object player) {
        Object playerFileData = BridgeReflection.getField(serverConfigurationManager, "playerFileData");
        BridgeReflection.invoke(playerFileData, "a", player);
    }

    public void updateInventory(Object player) {
        Object activeContainer = BridgeReflection.getField(player, "activeContainer");
        BridgeReflection.invoke(player, "updateInventory", activeContainer);
    }

    public void setSleepingIgnored(Object player, boolean sleepingIgnored, Object world) {
        BridgeReflection.setField(player, "fauxSleeping", sleepingIgnored);
        BridgeReflection.invoke(world, "checkSleepStatus");
    }

    public boolean isSleepingIgnored(Object player) {
        return (Boolean) BridgeReflection.getField(player, "fauxSleeping");
    }

    public void setPlayerTime(Object player, long timeOffset, boolean relativeTime) {
        BridgeReflection.setField(player, "timeOffset", timeOffset);
        BridgeReflection.setField(player, "relativeTime", relativeTime);
    }

    public long getPlayerTimeOffset(Object player) {
        return ((Number) BridgeReflection.getField(player, "timeOffset")).longValue();
    }

    public long getPlayerTime(Object player) {
        return ((Number) BridgeReflection.invoke(player, "getPlayerTime")).longValue();
    }

    public boolean isPlayerTimeRelative(Object player) {
        return (Boolean) BridgeReflection.getField(player, "relativeTime");
    }

    public boolean hasReceivedKeepAlive(Object player) {
        Object netServerHandler = BridgeReflection.getField(player, "netServerHandler");
        return (Boolean) BridgeReflection.invoke(netServerHandler, "isReceivedKeepAlive");
    }

    public boolean isUsingReleaseToBeta(Object player) {
        Object netServerHandler = BridgeReflection.getField(player, "netServerHandler");
        return (Boolean) BridgeReflection.invoke(netServerHandler, "isUsingReleaseToBeta");
    }
}
