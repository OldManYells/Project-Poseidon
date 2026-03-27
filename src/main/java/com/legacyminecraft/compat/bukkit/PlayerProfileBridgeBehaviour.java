package com.legacyminecraft.compat.bukkit;

import com.projectposeidon.ConnectionType;

import java.util.UUID;

/**
 * Canonical behavior for CraftPlayer profile/connection bridge access.
 */
public final class PlayerProfileBridgeBehaviour {
    private static final PlayerProfileBridgeBehaviour INSTANCE = new PlayerProfileBridgeBehaviour();

    private PlayerProfileBridgeBehaviour() {
    }

    public static PlayerProfileBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public String getDisplayName(Object player) {
        return BridgeReflection.cast(BridgeReflection.getField(player, "displayName"));
    }

    public void setDisplayName(Object player, String displayName) {
        BridgeReflection.setField(player, "displayName", displayName);
    }

    public UUID getUniqueId(Object player) {
        return BridgeReflection.cast(BridgeReflection.getField(player, "playerUUID"));
    }

    public <T> T getCompassTarget(Object player) {
        return BridgeReflection.cast(BridgeReflection.getField(player, "compassTarget"));
    }

    public ConnectionType getConnectionType(Object player) {
        Object netServerHandler = BridgeReflection.getField(player, "netServerHandler");
        return BridgeReflection.cast(BridgeReflection.invoke(netServerHandler, "getConnectionType"));
    }

    public com.legacyminecraft.poseidon.api.network.ConnectionType getCanonicalConnectionType(Object player) {
        Object netServerHandler = BridgeReflection.getField(player, "netServerHandler");
        return BridgeReflection.cast(BridgeReflection.invoke(netServerHandler, "getCanonicalConnectionType"));
    }

    public interface TimeResetCallbacks {
        void setPlayerTime(long time, boolean relative);
    }

    public void resetPlayerTime(TimeResetCallbacks callbacks) {
        callbacks.setPlayerTime(0, true);
    }
}
