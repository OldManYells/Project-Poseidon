package com.legacyminecraft.poseidon.world.player;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet39AttachEntity;

/**
 * Canonical attachment/mount synchronization for player wrapper flows.
 */
public final class PlayerAttachmentSyncSystem {
    private static final PlayerAttachmentSyncSystem INSTANCE = new PlayerAttachmentSyncSystem();

    private PlayerAttachmentSyncSystem() {
    }

    public static PlayerAttachmentSyncSystem getInstance() {
        return INSTANCE;
    }

    public void syncPassengerAttachment(EntityPlayer player) {
        if (!shouldSyncAttachment(player.netServerHandler != null)) {
            return;
        }
        player.netServerHandler.sendPacket(new Packet39AttachEntity(player, player.vehicle));
        player.netServerHandler.a(player.locX, player.locY, player.locZ, player.yaw, player.pitch);
    }

    public void restoreSessionState(EntityPlayer player) {
        if (player.vehicle != null) {
            player.mount(player.vehicle);
        }

        if (player.passenger != null) {
            player.passenger.mount(player);
        }

        if (player.sleeping) {
            player.a(true, false, false);
        }
    }

    public boolean shouldSyncAttachment(boolean hasNetServerHandler) {
        return hasNetServerHandler;
    }
}
