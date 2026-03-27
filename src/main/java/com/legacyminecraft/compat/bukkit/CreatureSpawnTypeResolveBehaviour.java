package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftEventFactory creature spawn-type resolution.
 */
public final class CreatureSpawnTypeResolveBehaviour {
    private static final CreatureSpawnTypeResolveBehaviour INSTANCE = new CreatureSpawnTypeResolveBehaviour();

    private CreatureSpawnTypeResolveBehaviour() {
    }

    public static CreatureSpawnTypeResolveBehaviour getInstance() {
        return INSTANCE;
    }

    public CreatureType resolve(EntityLiving entityLiving) {
        if (entityLiving instanceof EntityChicken) {
            return CreatureType.CHICKEN;
        } else if (entityLiving instanceof EntityCow) {
            return CreatureType.COW;
        } else if (entityLiving instanceof EntityCreeper) {
            return CreatureType.CREEPER;
        } else if (entityLiving instanceof EntityGhast) {
            return CreatureType.GHAST;
        } else if (entityLiving instanceof EntityGiantZombie) {
            return CreatureType.GIANT;
        } else if (entityLiving instanceof EntityWolf) {
            return CreatureType.WOLF;
        } else if (entityLiving instanceof EntityPig) {
            return CreatureType.PIG;
        } else if (entityLiving instanceof EntityPigZombie) {
            return CreatureType.PIG_ZOMBIE;
        } else if (entityLiving instanceof EntitySheep) {
            return CreatureType.SHEEP;
        } else if (entityLiving instanceof EntitySkeleton) {
            return CreatureType.SKELETON;
        } else if (entityLiving instanceof EntitySlime) {
            return CreatureType.SLIME;
        } else if (entityLiving instanceof EntitySpider) {
            return CreatureType.SPIDER;
        } else if (entityLiving instanceof EntitySquid) {
            return CreatureType.SQUID;
        } else if (entityLiving instanceof EntityZombie) {
            return CreatureType.ZOMBIE;
        } else if (entityLiving instanceof EntityMonster) {
            return CreatureType.MONSTER;
        }
        return null;
    }
}
