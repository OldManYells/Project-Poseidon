package com.legacyminecraft.poseidon.entity;


import com.legacyminecraft.compat.bukkit.Packet28EntityVelocity;

import java.util.Set;

/**
 * Canonical processor for velocity-changed event emission and packet broadcast.
 */
public final class EntityVelocityChangeProcessor {
    private static final EntityVelocityChangeProcessor INSTANCE = new EntityVelocityChangeProcessor();

    private EntityVelocityChangeProcessor() {
    }

    public static EntityVelocityChangeProcessor getInstance() {
        return INSTANCE;
    }

    public void processVelocityChanged(Entity tracker, Set trackedPlayers, EntityTrackingDispatchSystem dispatchService) {
        if (shouldBroadcastVelocityPacket(false)) {
            dispatchService.sendToTrackedPlayersAndSelf(
                    trackedPlayers,
                    tracker,
                    new Packet28EntityVelocity(tracker.id, tracker.motX, tracker.motY, tracker.motZ)
            );
        }
        tracker.velocityChanged = false;
    }

    public boolean shouldBroadcastVelocityPacket(boolean cancelled) {
        return !cancelled;
    }
}
