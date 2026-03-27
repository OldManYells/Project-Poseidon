package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftWorld explosion orchestration.
 */
public final class CraftWorldExplosionBehaviour {
    private static final CraftWorldExplosionBehaviour INSTANCE = new CraftWorldExplosionBehaviour();

    private CraftWorldExplosionBehaviour() {
    }

    public static CraftWorldExplosionBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean createExplosion(
            WorldServer worldServer,
            double x,
            double y,
            double z,
            float power,
            boolean setFire,
            EntityDamageEvent.DamageCause damageCause
    ) {
        return !worldServer.createExplosion(null, x, y, z, power, setFire, damageCause).wasCanceled;
    }
}
