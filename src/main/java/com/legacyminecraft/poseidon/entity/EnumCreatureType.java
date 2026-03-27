package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local creature category policy.
 */
public enum EnumCreatureType {
    MONSTER(false, EntityMonster.class, 70, Material.AIR),
    CREATURE(true, EntityAnimal.class, 15, Material.AIR),
    WATER_CREATURE(true, EntityLiving.class, 5, Material.WATER);

    private final boolean peaceful;
    private final Class baseClass;
    private final int maxCount;
    private final Material spawnMaterial;

    EnumCreatureType(boolean peaceful, Class baseClass, int maxCount, Material spawnMaterial) {
        this.peaceful = peaceful;
        this.baseClass = baseClass;
        this.maxCount = maxCount;
        this.spawnMaterial = spawnMaterial;
    }

    public boolean isPeaceful() {
        return peaceful;
    }

    public Class getBaseClass() {
        return baseClass;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public Material getSpawnMaterial() {
        return spawnMaterial;
    }
}
