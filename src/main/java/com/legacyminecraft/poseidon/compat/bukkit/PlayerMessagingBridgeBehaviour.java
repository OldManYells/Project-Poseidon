package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.NetServerHandler;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet3Chat;
import org.bukkit.entity.Player;

/**
 * Canonical behaviour for CraftPlayer messaging/kick/send-packet bridge policy.
 */
public final class PlayerMessagingBridgeBehaviour {
    private static final PlayerMessagingBridgeBehaviour INSTANCE = new PlayerMessagingBridgeBehaviour();

    private PlayerMessagingBridgeBehaviour() {
    }

    public static PlayerMessagingBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public void sendRawMessage(NetServerHandler netServerHandler, String message, String playerName) {
        try {
            netServerHandler.sendPacket(new Packet3Chat(message));
        } catch (NullPointerException exception) {
            System.out.println("[Poseidon] Exception thrown when attempting to send packet to "
                    + playerName + ". Does this player exist, or are they a phantom?????");
            exception.printStackTrace();
        }
    }

    public void kickIfOnline(boolean online, NetServerHandler netServerHandler, String message) {
        if (online && !netServerHandler.disconnected) {
            netServerHandler.disconnect(message == null ? "" : message);
        }
    }

    public void sendPacketIfOnline(Player player, NetServerHandler netServerHandler, Packet packet) {
        if (player.isOnline()) {
            netServerHandler.sendPacket(packet);
        }
    }
}
