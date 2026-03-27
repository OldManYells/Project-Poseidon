package com.legacyminecraft.poseidon.world.types;


/**
 * Canonical contract for world creature spawn categories.
 */
public interface CreatureTypeContract {
    Class getBaseClass();

    int getMaxCount();

    Material getSpawnMaterial();

    boolean isPeaceful();
}
