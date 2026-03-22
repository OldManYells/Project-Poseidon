package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.DataWatcher;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet28EntityVelocity;
import net.minecraft.server.Packet31RelEntityMove;
import net.minecraft.server.Packet32EntityLook;
import net.minecraft.server.Packet33RelEntityMoveLook;
import net.minecraft.server.Packet34EntityTeleport;
import net.minecraft.server.Packet40EntityMetadata;

import java.util.Set;

/**
 * Canonical processor for per-tick entity tracker movement packet generation/dispatch.
 */
public final class EntityTrackingMovementProcessor {
    private static final EntityTrackingMovementProcessor INSTANCE = new EntityTrackingMovementProcessor();
    private final EntityTrackingUpdatePolicy trackingUpdatePolicy = EntityTrackingUpdatePolicy.getInstance();

    private EntityTrackingMovementProcessor() {
    }

    public static EntityTrackingMovementProcessor getInstance() {
        return INSTANCE;
    }

    public void processTrackingFrame(
            Entity tracker,
            EntityTrackingState state,
            boolean isMoving,
            Set trackedPlayers,
            EntityTrackingDispatchSystem dispatchService,
            Runnable refreshTrackedPlayersForTeleport
    ) {
        EntityTrackingUpdatePolicy.EncodedEntityState encodedState = trackingUpdatePolicy.encode(tracker);
        int newEncodedPosX = encodedState.getX();
        int newEncodedPosY = encodedState.getY();
        int newEncodedPosZ = encodedState.getZ();
        int newEncodedRotationYaw = encodedState.getYaw();
        int newEncodedRotationPitch = encodedState.getPitch();

        int encodedDiffX = newEncodedPosX - state.getEncodedX();
        int encodedDiffY = newEncodedPosY - state.getEncodedY();
        int encodedDiffZ = newEncodedPosZ - state.getEncodedZ();

        boolean needsPositionUpdate = trackingUpdatePolicy.needsPositionUpdate(tracker, encodedDiffX, encodedDiffY, encodedDiffZ);
        boolean needsRotationUpdate = trackingUpdatePolicy.needsRotationUpdate(
                newEncodedRotationYaw,
                newEncodedRotationPitch,
                state.getEncodedYaw(),
                state.getEncodedPitch()
        );

        if (needsPositionUpdate) {
            state.setEncodedX(newEncodedPosX);
            state.setEncodedY(newEncodedPosY);
            state.setEncodedZ(newEncodedPosZ);
        }

        if (needsRotationUpdate) {
            state.setEncodedYaw(newEncodedRotationYaw);
            state.setEncodedPitch(newEncodedRotationPitch);
        }

        Packet movementPacket = buildMovementPacket(
                tracker,
                state,
                encodedDiffX,
                encodedDiffY,
                encodedDiffZ,
                newEncodedPosX,
                newEncodedPosY,
                newEncodedPosZ,
                newEncodedRotationYaw,
                newEncodedRotationPitch,
                needsPositionUpdate,
                needsRotationUpdate,
                refreshTrackedPlayersForTeleport
        );

        if (isMoving && trackingUpdatePolicy.shouldSendVelocityUpdate(tracker, state.getMotionX(), state.getMotionY(), state.getMotionZ())) {
            state.setMotionX(tracker.motX);
            state.setMotionY(tracker.motY);
            state.setMotionZ(tracker.motZ);
            dispatchService.sendToTrackedPlayers(
                    trackedPlayers,
                    new Packet28EntityVelocity(tracker.id, state.getMotionX(), state.getMotionY(), state.getMotionZ())
            );
        }

        if (movementPacket != null) {
            dispatchService.sendToTrackedPlayers(trackedPlayers, movementPacket);
        }

        DataWatcher datawatcher = tracker.aa();
        if (datawatcher.a()) {
            dispatchService.sendToTrackedPlayersAndSelf(trackedPlayers, tracker, new Packet40EntityMetadata(tracker.id, datawatcher));
        }

        tracker.airBorne = false;
    }

    private Packet buildMovementPacket(
            Entity tracker,
            EntityTrackingState state,
            int encodedDiffX,
            int encodedDiffY,
            int encodedDiffZ,
            int newEncodedPosX,
            int newEncodedPosY,
            int newEncodedPosZ,
            int newEncodedRotationYaw,
            int newEncodedRotationPitch,
            boolean needsPositionUpdate,
            boolean needsRotationUpdate,
            Runnable refreshTrackedPlayersForTeleport
    ) {
        if (trackingUpdatePolicy.canUseRelativePacket(encodedDiffX, encodedDiffY, encodedDiffZ, state.getTeleportCounter())) {
            if (needsPositionUpdate && needsRotationUpdate) {
                return new Packet33RelEntityMoveLook(
                        tracker.id,
                        (byte) encodedDiffX,
                        (byte) encodedDiffY,
                        (byte) encodedDiffZ,
                        (byte) newEncodedRotationYaw,
                        (byte) newEncodedRotationPitch
                );
            } else if (needsPositionUpdate) {
                return new Packet31RelEntityMove(tracker.id, (byte) encodedDiffX, (byte) encodedDiffY, (byte) encodedDiffZ);
            } else if (needsRotationUpdate) {
                return new Packet32EntityLook(tracker.id, (byte) newEncodedRotationYaw, (byte) newEncodedRotationPitch);
            }
            return null;
        }

        state.setTeleportCounter(0);
        if (shouldRefreshViewerListBeforeTeleport(tracker) && refreshTrackedPlayersForTeleport != null) {
            refreshTrackedPlayersForTeleport.run();
        }
        return new Packet34EntityTeleport(
                tracker.id,
                newEncodedPosX,
                newEncodedPosY,
                newEncodedPosZ,
                (byte) newEncodedRotationYaw,
                (byte) newEncodedRotationPitch
        );
    }

    public boolean shouldRefreshViewerListBeforeTeleport(Entity tracker) {
        return tracker instanceof EntityPlayer;
    }
}
