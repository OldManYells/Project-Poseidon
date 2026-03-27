package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftWorld direct world-state accessors.
 */
public final class CraftWorldStateAccessBehaviour {
    private static final CraftWorldStateAccessBehaviour INSTANCE = new CraftWorldStateAccessBehaviour();

    private CraftWorldStateAccessBehaviour() {
    }

    public static CraftWorldStateAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean hasStorm(WorldServer worldServer) {
        return worldServer.worldData.hasStorm();
    }

    public int getWeatherDuration(WorldServer worldServer) {
        return worldServer.worldData.getWeatherDuration();
    }

    public void setWeatherDuration(WorldServer worldServer, int duration) {
        worldServer.worldData.setWeatherDuration(duration);
    }

    public boolean isThundering(WorldServer worldServer) {
        return worldServer.worldData.isThundering();
    }

    public int getThunderDuration(WorldServer worldServer) {
        return worldServer.worldData.getThunderDuration();
    }

    public void setThunderDuration(WorldServer worldServer, int duration) {
        worldServer.worldData.setThunderDuration(duration);
    }

    public boolean getPVP(WorldServer worldServer) {
        return worldServer.pvpMode;
    }

    public void setPVP(WorldServer worldServer, boolean pvp) {
        worldServer.pvpMode = pvp;
    }

    public void setSpawnFlags(WorldServer worldServer, boolean allowMonsters, boolean allowAnimals) {
        worldServer.setSpawnFlags(allowMonsters, allowAnimals);
    }

    public boolean getAllowAnimals(WorldServer worldServer) {
        return worldServer.allowAnimals;
    }

    public boolean getAllowMonsters(WorldServer worldServer) {
        return worldServer.allowMonsters;
    }

    public int getMaxHeight(WorldServer worldServer) {
        return 128;
    }

    public boolean getKeepSpawnInMemory(WorldServer worldServer) {
        return worldServer.keepSpawnInMemory;
    }
}
