package com.legacyminecraft.compat.bukkit;


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

    public int getRemainingAir(Object livingEntity) {
        return ((Number) BridgeReflection.getField(livingEntity, "airTicks")).intValue();
    }

    public void setRemainingAir(Object livingEntity, int ticks) {
        BridgeReflection.setField(livingEntity, "airTicks", ticks);
    }

    public int getMaximumAir(Object livingEntity) {
        return ((Number) BridgeReflection.getField(livingEntity, "maxAirTicks")).intValue();
    }

    public void setMaximumAir(Object livingEntity, int ticks) {
        BridgeReflection.setField(livingEntity, "maxAirTicks", ticks);
    }

    public void applyDamage(Object livingEntity, int amount) {
        BridgeReflection.invoke(livingEntity, "damageEntity", null, amount);
    }

    public void applyDamage(Object livingEntity, Object source, int amount) {
        BridgeReflection.invoke(livingEntity, "damageEntity", source, amount);
    }

    public int getMaximumNoDamageTicks(Object livingEntity) {
        return ((Number) BridgeReflection.getField(livingEntity, "maxNoDamageTicks")).intValue();
    }

    public void setMaximumNoDamageTicks(Object livingEntity, int ticks) {
        BridgeReflection.setField(livingEntity, "maxNoDamageTicks", ticks);
    }

    public int getLastDamage(Object livingEntity) {
        return ((Number) BridgeReflection.getField(livingEntity, "lastDamage")).intValue();
    }

    public void setLastDamage(Object livingEntity, int damage) {
        BridgeReflection.setField(livingEntity, "lastDamage", damage);
    }

    public int getNoDamageTicks(Object livingEntity) {
        return ((Number) BridgeReflection.getField(livingEntity, "noDamageTicks")).intValue();
    }

    public void setNoDamageTicks(Object livingEntity, int ticks) {
        BridgeReflection.setField(livingEntity, "noDamageTicks", ticks);
    }
}
