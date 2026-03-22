package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;

/**
 * Canonical behaviour for CraftLivingEntity damage and air/no-damage state bridges.
 */
public final class LivingEntityDamageStateBehaviour {
    private static final LivingEntityDamageStateBehaviour INSTANCE = new LivingEntityDamageStateBehaviour();

    private LivingEntityDamageStateBehaviour() {
    }

    public static LivingEntityDamageStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int getRemainingAir(EntityLiving livingEntity) {
        return livingEntity.airTicks;
    }

    public void setRemainingAir(EntityLiving livingEntity, int ticks) {
        livingEntity.airTicks = ticks;
    }

    public int getMaximumAir(EntityLiving livingEntity) {
        return livingEntity.maxAirTicks;
    }

    public void setMaximumAir(EntityLiving livingEntity, int ticks) {
        livingEntity.maxAirTicks = ticks;
    }

    public void applyDamage(EntityLiving livingEntity, int amount) {
        livingEntity.damageEntity((Entity) null, amount);
    }

    public void applyDamage(EntityLiving livingEntity, Entity source, int amount) {
        livingEntity.damageEntity(source, amount);
    }

    public int getMaximumNoDamageTicks(EntityLiving livingEntity) {
        return livingEntity.maxNoDamageTicks;
    }

    public void setMaximumNoDamageTicks(EntityLiving livingEntity, int ticks) {
        livingEntity.maxNoDamageTicks = ticks;
    }

    public int getLastDamage(EntityLiving livingEntity) {
        return livingEntity.lastDamage;
    }

    public void setLastDamage(EntityLiving livingEntity, int damage) {
        livingEntity.lastDamage = damage;
    }

    public int getNoDamageTicks(EntityLiving livingEntity) {
        return livingEntity.noDamageTicks;
    }

    public void setNoDamageTicks(EntityLiving livingEntity, int ticks) {
        livingEntity.noDamageTicks = ticks;
    }
}
