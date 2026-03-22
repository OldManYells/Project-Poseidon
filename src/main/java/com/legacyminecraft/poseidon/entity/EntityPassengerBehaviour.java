package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.Entity;

/**
 * Canonical behaviour for entity passenger-propagation rules.
 */
public final class EntityPassengerBehaviour {
    private static final EntityPassengerBehaviour INSTANCE = new EntityPassengerBehaviour();

    private EntityPassengerBehaviour() {
    }

    public static EntityPassengerBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldPropagateFallDistance(Entity passenger) {
        return passenger != null;
    }
}
