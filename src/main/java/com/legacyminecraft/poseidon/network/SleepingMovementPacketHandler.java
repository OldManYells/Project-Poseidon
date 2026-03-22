package com.legacyminecraft.poseidon.network;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.WorldServer;

/**
 * Canonical helper for movement packet handling while the player is sleeping.
 */
public final class SleepingMovementPacketHandler {
    private static final SleepingMovementPacketHandler INSTANCE = new SleepingMovementPacketHandler();

    private SleepingMovementPacketHandler() {
    }

    public static SleepingMovementPacketHandler getInstance() {
        return INSTANCE;
    }

    public boolean handleSleepingMovement(
            EntityPlayer player,
            WorldServer worldServer,
            double lockedX,
            double lockedY,
            double lockedZ
    ) {
        if (!shouldHandleSleepingMovement(player.isSleeping())) {
            return false;
        }

        player.a(true);
        player.setLocation(lockedX, lockedY, lockedZ, player.yaw, player.pitch);
        worldServer.playerJoinedWorld(player);
        return true;
    }

    public boolean shouldHandleSleepingMovement(boolean sleeping) {
        return sleeping;
    }
}
