package com.legacyminecraft.poseidon.network;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet10Flying;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Canonical dispatcher for PlayerMoveEvent construction and outcome resolution.
 */
public final class PlayerMoveEventDispatchSystem {
    private static final PlayerMoveEventDispatchSystem INSTANCE = new PlayerMoveEventDispatchSystem();
    private final PlayerMoveEventPolicy playerMoveEventPolicy = PlayerMoveEventPolicy.getInstance();

    private PlayerMoveEventDispatchSystem() {
    }

    public static PlayerMoveEventDispatchSystem getInstance() {
        return INSTANCE;
    }

    public MoveEventResult processMoveEvent(
            Server server,
            EntityPlayer entityPlayer,
            Player player,
            Packet10Flying packet10flying,
            MovementEventState state,
            boolean checkMovement
    ) {
        Location from = new Location(player.getWorld(), state.getLastPosX(), state.getLastPosY(), state.getLastPosZ(), state.getLastYaw(), state.getLastPitch());
        Location to = player.getLocation().clone();

        if (playerMoveEventPolicy.shouldApplyPositionFromPacket(packet10flying.h, packet10flying.y, packet10flying.stance)) {
            to.setX(packet10flying.x);
            to.setY(packet10flying.y);
            to.setZ(packet10flying.z);
        }

        if (packet10flying.hasLook) {
            to.setYaw(packet10flying.yaw);
            to.setPitch(packet10flying.pitch);
        }

        boolean significantMoveEventDelta = playerMoveEventPolicy.hasSignificantMoveEventDelta(
                state.getLastPosX(),
                state.getLastPosY(),
                state.getLastPosZ(),
                state.getLastYaw(),
                state.getLastPitch(),
                to
        );
        if (!playerMoveEventPolicy.shouldProcessMoveEvent(significantMoveEventDelta, checkMovement, entityPlayer.dead)) {
            return MoveEventResult.continueProcessing();
        }

        state.setLastPosX(to.getX());
        state.setLastPosY(to.getY());
        state.setLastPosZ(to.getZ());
        state.setLastYaw(to.getYaw());
        state.setLastPitch(to.getPitch());

        if (!playerMoveEventPolicy.hasInitializedMoveFromLocation(from)) {
            return MoveEventResult.continueProcessing();
        }

        PlayerMoveEvent event = new PlayerMoveEvent(player, from, to);
        server.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return MoveEventResult.cancelAndRollback(from);
        }

        if (!to.equals(event.getTo())) {
            return MoveEventResult.teleportToEventDestination(event.getTo());
        }

        if (playerMoveEventPolicy.shouldAbortAfterPluginTeleport(from, player.getLocation(), state.isJustTeleported())) {
            state.setJustTeleported(false);
            return MoveEventResult.abortAfterPluginTeleport();
        }

        return MoveEventResult.continueProcessing();
    }

    public static final class MovementEventState {
        private double lastPosX;
        private double lastPosY;
        private double lastPosZ;
        private float lastYaw;
        private float lastPitch;
        private boolean justTeleported;

        public MovementEventState(
                double lastPosX,
                double lastPosY,
                double lastPosZ,
                float lastYaw,
                float lastPitch,
                boolean justTeleported
        ) {
            this.lastPosX = lastPosX;
            this.lastPosY = lastPosY;
            this.lastPosZ = lastPosZ;
            this.lastYaw = lastYaw;
            this.lastPitch = lastPitch;
            this.justTeleported = justTeleported;
        }

        public double getLastPosX() {
            return lastPosX;
        }

        public void setLastPosX(double lastPosX) {
            this.lastPosX = lastPosX;
        }

        public double getLastPosY() {
            return lastPosY;
        }

        public void setLastPosY(double lastPosY) {
            this.lastPosY = lastPosY;
        }

        public double getLastPosZ() {
            return lastPosZ;
        }

        public void setLastPosZ(double lastPosZ) {
            this.lastPosZ = lastPosZ;
        }

        public float getLastYaw() {
            return lastYaw;
        }

        public void setLastYaw(float lastYaw) {
            this.lastYaw = lastYaw;
        }

        public float getLastPitch() {
            return lastPitch;
        }

        public void setLastPitch(float lastPitch) {
            this.lastPitch = lastPitch;
        }

        public boolean isJustTeleported() {
            return justTeleported;
        }

        public void setJustTeleported(boolean justTeleported) {
            this.justTeleported = justTeleported;
        }
    }

    public static final class MoveEventResult {
        public enum Action {
            CONTINUE,
            CANCEL_AND_ROLLBACK,
            TELEPORT_TO_EVENT_DESTINATION,
            ABORT_AFTER_PLUGIN_TELEPORT
        }

        private final Action action;
        private final Location rollbackLocation;
        private final Location teleportDestination;

        private MoveEventResult(Action action, Location rollbackLocation, Location teleportDestination) {
            this.action = action;
            this.rollbackLocation = rollbackLocation;
            this.teleportDestination = teleportDestination;
        }

        public static MoveEventResult continueProcessing() {
            return new MoveEventResult(Action.CONTINUE, null, null);
        }

        public static MoveEventResult cancelAndRollback(Location rollbackLocation) {
            return new MoveEventResult(Action.CANCEL_AND_ROLLBACK, rollbackLocation, null);
        }

        public static MoveEventResult teleportToEventDestination(Location teleportDestination) {
            return new MoveEventResult(Action.TELEPORT_TO_EVENT_DESTINATION, null, teleportDestination);
        }

        public static MoveEventResult abortAfterPluginTeleport() {
            return new MoveEventResult(Action.ABORT_AFTER_PLUGIN_TELEPORT, null, null);
        }

        public Action getAction() {
            return action;
        }

        public Location getRollbackLocation() {
            return rollbackLocation;
        }

        public Location getTeleportDestination() {
            return teleportDestination;
        }
    }
}
