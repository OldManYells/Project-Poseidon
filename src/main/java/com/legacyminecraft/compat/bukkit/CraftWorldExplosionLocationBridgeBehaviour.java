package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftWorld location-based explosion overload forwarding.
 */
public final class CraftWorldExplosionLocationBridgeBehaviour {
    private static final CraftWorldExplosionLocationBridgeBehaviour INSTANCE =
            new CraftWorldExplosionLocationBridgeBehaviour();

    private CraftWorldExplosionLocationBridgeBehaviour() {
    }

    public static CraftWorldExplosionLocationBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean createExplosion(CraftWorld craftWorld, Location location, float power) {
        return craftWorld.createExplosion(location, power, false);
    }

    public boolean createExplosion(CraftWorld craftWorld, Location location, float power, boolean setFire) {
        return craftWorld.createExplosion(location.getX(), location.getY(), location.getZ(), power, setFire);
    }

    public boolean createExplosion(
            CraftWorld craftWorld,
            Location location,
            float power,
            boolean setFire,
            EntityDamageEvent.DamageCause customDamageCause
    ) {
        return craftWorld.createExplosion(
                location.getX(),
                location.getY(),
                location.getZ(),
                power,
                setFire,
                customDamageCause
        );
    }
}
