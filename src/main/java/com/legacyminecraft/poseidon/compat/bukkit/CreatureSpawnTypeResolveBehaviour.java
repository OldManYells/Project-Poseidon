package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityChicken;
import net.minecraft.server.EntityCow;
import net.minecraft.server.EntityCreeper;
import net.minecraft.server.EntityGhast;
import net.minecraft.server.EntityGiantZombie;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityMonster;
import net.minecraft.server.EntityPig;
import net.minecraft.server.EntityPigZombie;
import net.minecraft.server.EntitySheep;
import net.minecraft.server.EntitySkeleton;
import net.minecraft.server.EntitySlime;
import net.minecraft.server.EntitySpider;
import net.minecraft.server.EntitySquid;
import net.minecraft.server.EntityWolf;
import net.minecraft.server.EntityZombie;
import org.bukkit.entity.CreatureType;

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
