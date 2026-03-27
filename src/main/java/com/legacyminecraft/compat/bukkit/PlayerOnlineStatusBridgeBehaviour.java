package com.legacyminecraft.compat.bukkit;


import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

/**
 * Canonical behaviour for CraftPlayer online-state and address lookup bridging.
 */
public final class PlayerOnlineStatusBridgeBehaviour {
    private static final PlayerOnlineStatusBridgeBehaviour INSTANCE = new PlayerOnlineStatusBridgeBehaviour();

    private PlayerOnlineStatusBridgeBehaviour() {
    }

    public static PlayerOnlineStatusBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isOnline(List onlinePlayers, String playerName) {
        for (Object onlinePlayer : onlinePlayers) {
            String name = BridgeReflection.cast(BridgeReflection.getField(onlinePlayer, "name"));
            if (name != null && name.equalsIgnoreCase(playerName)) {
                return true;
            }
        }
        return false;
    }

    public InetSocketAddress resolveAddress(Object netServerHandler) {
        Object networkManager = BridgeReflection.getField(netServerHandler, "networkManager");
        SocketAddress address = BridgeReflection.cast(BridgeReflection.invoke(networkManager, "getSocketAddress"));
        if (address instanceof InetSocketAddress) {
            return (InetSocketAddress) address;
        }
        return null;
    }
}
