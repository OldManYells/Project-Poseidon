package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet28EntityVelocity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;

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
        boolean cancelled = false;

        if (tracker instanceof EntityPlayer) {
            Player player = (Player) tracker.getBukkitEntity();
            Vector velocity = player.getVelocity();

            PlayerVelocityEvent event = new PlayerVelocityEvent(player, velocity);
            tracker.world.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                cancelled = true;
            } else if (!velocity.equals(event.getVelocity())) {
                player.setVelocity(velocity);
            }
        }

        if (shouldBroadcastVelocityPacket(cancelled)) {
            dispatchService.sendToTrackedPlayersAndSelf(trackedPlayers, tracker, new Packet28EntityVelocity(tracker));
        }
        tracker.velocityChanged = false;
    }

    public boolean shouldBroadcastVelocityPacket(boolean cancelled) {
        return !cancelled;
    }
}
