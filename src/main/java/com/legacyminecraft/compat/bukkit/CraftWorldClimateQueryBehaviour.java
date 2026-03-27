package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftWorld climate query lookups.
 */
public final class CraftWorldClimateQueryBehaviour {
    private static final CraftWorldClimateQueryBehaviour INSTANCE = new CraftWorldClimateQueryBehaviour();

    private CraftWorldClimateQueryBehaviour() {
    }

    public static CraftWorldClimateQueryBehaviour getInstance() {
        return INSTANCE;
    }

    public double getTemperature(WorldServer worldServer, int x, int z) {
        return worldServer.getWorldChunkManager().a((double[]) null, x, z, 1, 1)[0];
    }

    public double getHumidity(WorldServer worldServer, int x, int z) {
        return worldServer.getWorldChunkManager().getHumidity(x, z);
    }
}
