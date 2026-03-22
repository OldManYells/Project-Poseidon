package com.legacyminecraft.poseidon.world.player;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EntityTracker;
import net.minecraft.server.Packet22Collect;

/**
 * Canonical collect-packet emission rules for EntityPlayer receive flow.
 */
public final class PlayerCollectionPacketSystem {
    private static final PlayerCollectionPacketSystem INSTANCE = new PlayerCollectionPacketSystem();

    private PlayerCollectionPacketSystem() {
    }

    public static PlayerCollectionPacketSystem getInstance() {
        return INSTANCE;
    }

    public void sendCollectPacketIfNeeded(EntityPlayer player, Entity entity) {
        if (!shouldBroadcastCollectPacket(entity.dead, entity instanceof EntityItem, entity instanceof EntityArrow)) {
            return;
        }

        EntityTracker tracker = player.b.getTracker(player.dimension);
        tracker.a(entity, new Packet22Collect(entity.id, player.id));
    }

    public void refreshActiveContainer(EntityPlayer player) {
        player.activeContainer.a();
    }

    public boolean shouldBroadcastCollectPacket(boolean entityDead, boolean entityItem, boolean entityArrow) {
        return !entityDead && (entityItem || entityArrow);
    }
}
