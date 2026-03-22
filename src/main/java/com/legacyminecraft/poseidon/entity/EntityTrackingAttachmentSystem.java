package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.Packet17;
import net.minecraft.server.Packet28EntityVelocity;
import net.minecraft.server.Packet39AttachEntity;
import net.minecraft.server.Packet40EntityMetadata;
import net.minecraft.server.Packet5EntityEquipment;

/**
 * Canonical helper for initial packet fan-out when a player starts tracking an entity.
 */
public final class EntityTrackingAttachmentSystem {
    private static final EntityTrackingAttachmentSystem INSTANCE = new EntityTrackingAttachmentSystem();

    private EntityTrackingAttachmentSystem() {
    }

    public static EntityTrackingAttachmentSystem getInstance() {
        return INSTANCE;
    }

    public MotionSnapshot sendInitialTrackingPackets(Entity tracker, EntityPlayer viewer, boolean isMoving) {
        if (!tracker.aa().getD()) {
            viewer.netServerHandler.sendPacket(new Packet40EntityMetadata(tracker.id, tracker.aa()));
        }

        MotionSnapshot motionSnapshot = new MotionSnapshot(tracker.motX, tracker.motY, tracker.motZ);
        if (shouldSendInitialVelocity(isMoving)) {
            viewer.netServerHandler.sendPacket(new Packet28EntityVelocity(tracker.id, tracker.motX, tracker.motY, tracker.motZ));
        }

        if (tracker.vehicle != null) {
            viewer.netServerHandler.sendPacket(new Packet39AttachEntity(tracker, tracker.vehicle));
        }

        if (tracker.passenger != null) {
            viewer.netServerHandler.sendPacket(new Packet39AttachEntity(tracker.passenger, tracker));
        }

        ItemStack[] equipment = tracker.getEquipment();
        if (equipment != null) {
            for (int i = 0; i < equipment.length; ++i) {
                viewer.netServerHandler.sendPacket(new Packet5EntityEquipment(tracker.id, i, equipment[i]));
            }
        }

        if (tracker instanceof EntityHuman) {
            EntityHuman entityhuman = (EntityHuman) tracker;
            if (entityhuman.isSleeping()) {
                viewer.netServerHandler.sendPacket(
                        new Packet17(
                                tracker,
                                0,
                                MathHelper.floor(tracker.locX),
                                MathHelper.floor(tracker.locY),
                                MathHelper.floor(tracker.locZ)
                        )
                );
            }
        }

        return motionSnapshot;
    }

    public boolean shouldSendInitialVelocity(boolean isMoving) {
        return isMoving;
    }

    public static final class MotionSnapshot {
        private final double motionX;
        private final double motionY;
        private final double motionZ;

        private MotionSnapshot(double motionX, double motionY, double motionZ) {
            this.motionX = motionX;
            this.motionY = motionY;
            this.motionZ = motionZ;
        }

        public double getMotionX() {
            return motionX;
        }

        public double getMotionY() {
            return motionY;
        }

        public double getMotionZ() {
            return motionZ;
        }
    }
}
