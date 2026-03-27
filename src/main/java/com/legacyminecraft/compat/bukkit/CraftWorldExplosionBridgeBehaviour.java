package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftWorld double-coordinate explosion overload forwarding.
 */
public final class CraftWorldExplosionBridgeBehaviour {
    private static final CraftWorldExplosionBridgeBehaviour INSTANCE = new CraftWorldExplosionBridgeBehaviour();

    private CraftWorldExplosionBridgeBehaviour() {
    }

    public static CraftWorldExplosionBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean createExplosion(CraftWorld craftWorld, double x, double y, double z, float power) {
        return craftWorld.createExplosion(x, y, z, power, false);
    }

    public boolean createExplosion(CraftWorld craftWorld, double x, double y, double z, float power, boolean setFire) {
        return craftWorld.createExplosion(x, y, z, power, setFire, EntityDamageEvent.DamageCause.PLUGIN_EXPLOSION);
    }
}
