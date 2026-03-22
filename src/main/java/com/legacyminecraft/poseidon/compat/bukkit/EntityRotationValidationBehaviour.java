package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityPlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;

/**
 * Canonical Bukkit-aware behaviour for validating entity yaw/pitch inputs.
 */
public final class EntityRotationValidationBehaviour {
    private static final EntityRotationValidationBehaviour INSTANCE = new EntityRotationValidationBehaviour();

    private EntityRotationValidationBehaviour() {
    }

    public static EntityRotationValidationBehaviour getInstance() {
        return INSTANCE;
    }

    public Rotation sanitizeRotation(Entity entity, float yaw, float pitch) {
        float sanitizedYaw = this.sanitizeYaw(entity, yaw);
        float sanitizedPitch = this.sanitizePitch(entity, pitch);
        return new Rotation(sanitizedYaw, sanitizedPitch);
    }

    private float sanitizeYaw(Entity entity, float yaw) {
        if (Float.isNaN(yaw)) {
            return 0.0F;
        }

        if (yaw == Float.POSITIVE_INFINITY || yaw == Float.NEGATIVE_INFINITY) {
            this.handleInvalidRotation(entity, "yaw");
            return 0.0F;
        }

        return yaw;
    }

    private float sanitizePitch(Entity entity, float pitch) {
        if (Float.isNaN(pitch)) {
            return 0.0F;
        }

        if (pitch == Float.POSITIVE_INFINITY || pitch == Float.NEGATIVE_INFINITY) {
            this.handleInvalidRotation(entity, "pitch");
            return 0.0F;
        }

        return pitch;
    }

    private void handleInvalidRotation(Entity entity, String axis) {
        if (entity instanceof EntityPlayer) {
            CraftPlayer craftPlayer = (CraftPlayer) entity.getBukkitEntity();
            System.err.println(craftPlayer.getName() + " was caught trying to crash the server with an invalid " + axis);
            craftPlayer.kickPlayer("Nope");
        }
    }

    public static final class Rotation {
        public final float yaw;
        public final float pitch;

        public Rotation(float yaw, float pitch) {
            this.yaw = yaw;
            this.pitch = pitch;
        }
    }
}
