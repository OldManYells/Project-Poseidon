package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityPlayer;

/**
 * Canonical policy helpers for entity interaction packet admission and mode decoding.
 */
public final class EntityInteractionPolicy {
    private static final EntityInteractionPolicy INSTANCE = new EntityInteractionPolicy();

    private EntityInteractionPolicy() {
    }

    public static EntityInteractionPolicy getInstance() {
        return INSTANCE;
    }

    public boolean canProcessInteraction(EntityPlayer player, Entity target) {
        return target != null && player.e(target) && player.g(target) < 36.0D;
    }

    public EntityInteractionMode resolveInteractionMode(int rawAction) {
        return EntityInteractionMode.fromRawAction(rawAction);
    }

    public boolean shouldCancelStorageMinecartInteraction(boolean playerInsideVehicle, boolean targetIsStorageMinecart) {
        return playerInsideVehicle && targetIsStorageMinecart;
    }
}
