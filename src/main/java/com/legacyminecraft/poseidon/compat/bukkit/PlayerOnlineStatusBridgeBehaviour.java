package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.NetServerHandler;

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
            EntityPlayer entityPlayer = (EntityPlayer) onlinePlayer;
            if (entityPlayer.name.equalsIgnoreCase(playerName)) {
                return true;
            }
        }
        return false;
    }

    public InetSocketAddress resolveAddress(NetServerHandler netServerHandler) {
        SocketAddress address = netServerHandler.networkManager.getSocketAddress();
        if (address instanceof InetSocketAddress) {
            return (InetSocketAddress) address;
        }
        return null;
    }
}
