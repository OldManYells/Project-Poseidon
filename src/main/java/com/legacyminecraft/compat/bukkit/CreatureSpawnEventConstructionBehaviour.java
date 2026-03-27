package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftEventFactory creature-spawn event construction.
 */
public final class CreatureSpawnEventConstructionBehaviour {
    private static final CreatureSpawnEventConstructionBehaviour INSTANCE =
            new CreatureSpawnEventConstructionBehaviour();

    private CreatureSpawnEventConstructionBehaviour() {
    }

    public static CreatureSpawnEventConstructionBehaviour getInstance() {
        return INSTANCE;
    }

    public CreatureSpawnEvent createCreatureSpawnEvent(
            com.legacyminecraft.compat.bukkit.Entity entity,
            CreatureType type,
            CreatureSpawnEvent.SpawnReason reason
    ) {
        return new CreatureSpawnEvent(entity, type, entity.getLocation(), reason);
    }
}
