package com.legacyminecraft.poseidon.network;

import net.minecraft.server.Packet10Flying;
import org.bukkit.entity.Player;

/**
 * Canonical response handler for invalid numeric movement packets.
 */
public final class InvalidPositionResponseSystem {
    private static final InvalidPositionResponseSystem INSTANCE = new InvalidPositionResponseSystem();
    private final MovementPacketPolicy movementPacketPolicy = MovementPacketPolicy.getInstance();

    private InvalidPositionResponseSystem() {
    }

    public static InvalidPositionResponseSystem getInstance() {
        return INSTANCE;
    }

    public boolean handleInvalidPositionIfNeeded(Packet10Flying packet10flying, Player player, boolean disconnected) {
        if (!movementPacketPolicy.hasInvalidNumericPosition(packet10flying, player, disconnected)) {
            return false;
        }

        player.teleport(player.getWorld().getSpawnLocation());
        System.err.println(createInvalidPositionLogMessage(player.getName()));
        player.kickPlayer(getInvalidPositionKickMessage());
        return true;
    }

    public String createInvalidPositionLogMessage(String playerName) {
        return playerName + " was caught trying to crash the server with an invalid position.";
    }

    public String getInvalidPositionKickMessage() {
        return "Nope!";
    }
}
