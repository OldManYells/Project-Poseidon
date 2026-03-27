package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftCreatureSpawner mob-type and delay state policy.
 */
public final class SpawnerStateBehaviour {
    private static final SpawnerStateBehaviour INSTANCE = new SpawnerStateBehaviour();

    private SpawnerStateBehaviour() {
    }

    public static SpawnerStateBehaviour getInstance() {
        return INSTANCE;
    }

    public CreatureType getCreatureType(TileEntityMobSpawner spawner) {
        return CreatureType.fromName(spawner.mobName);
    }

    public void setCreatureType(TileEntityMobSpawner spawner, CreatureType creatureType) {
        spawner.mobName = creatureType.getName();
    }

    public String getCreatureTypeId(TileEntityMobSpawner spawner) {
        return spawner.mobName;
    }

    public void setCreatureTypeId(TileEntityMobSpawner spawner, String creatureTypeId) {
        CreatureType type = CreatureType.fromName(creatureTypeId);
        if (type == null) {
            return;
        }
        spawner.mobName = type.getName();
    }

    public int getDelay(TileEntityMobSpawner spawner) {
        return spawner.spawnDelay;
    }

    public void setDelay(TileEntityMobSpawner spawner, int delay) {
        spawner.spawnDelay = delay;
    }
}
