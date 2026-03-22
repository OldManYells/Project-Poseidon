package com.legacyminecraft.poseidon.network;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Canonical helper for teleport destination resolution and orientation sanitization.
 */
public final class PlayerTeleportCoordinator {
    private static final PlayerTeleportCoordinator INSTANCE = new PlayerTeleportCoordinator();

    private PlayerTeleportCoordinator() {
    }

    public static PlayerTeleportCoordinator getInstance() {
        return INSTANCE;
    }

    public Location resolveTeleportDestination(Server server, Player player, double x, double y, double z, float yaw, float pitch) {
        Location from = player.getLocation();
        Location to = new Location(player.getWorld(), x, y, z, yaw, pitch);
        PlayerTeleportEvent event = new PlayerTeleportEvent(player, from, to);
        server.getPluginManager().callEvent(event);

        from = event.getFrom();
        return event.isCancelled() ? from : event.getTo();
    }

    public TeleportPlan createTeleportPlan(Location destination) {
        double x = destination.getX();
        double y = destination.getY();
        double z = destination.getZ();
        float yaw = sanitizeRotationComponent(destination.getYaw());
        float pitch = sanitizeRotationComponent(destination.getPitch());
        return new TeleportPlan(x, y, z, yaw, pitch);
    }

    public float sanitizeRotationComponent(float value) {
        return Float.isNaN(value) ? 0.0F : value;
    }

    public static final class TeleportPlan {
        private final double x;
        private final double y;
        private final double z;
        private final float yaw;
        private final float pitch;

        private TeleportPlan(double x, double y, double z, float yaw, float pitch) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public float getYaw() {
            return yaw;
        }

        public float getPitch() {
            return pitch;
        }
    }
}
