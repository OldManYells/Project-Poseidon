package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer reload settings-apply wrapper glue.
 */
public final class CraftServerReloadSettingsBehaviour {
    private static final CraftServerReloadSettingsBehaviour INSTANCE =
            new CraftServerReloadSettingsBehaviour();

    private CraftServerReloadSettingsBehaviour() {
    }

    public static CraftServerReloadSettingsBehaviour getInstance() {
        return INSTANCE;
    }

    public void applySettings(MinecraftServer console, PropertyManager config) {
        boolean animals = config.getBoolean("spawn-animals", console.spawnAnimals);
        boolean monsters = config.getBoolean("spawn-monsters", console.worlds.get(0).spawnMonsters > 0);

        console.onlineMode = config.getBoolean("online-mode", console.onlineMode);
        console.spawnAnimals = config.getBoolean("spawn-animals", console.spawnAnimals);
        console.pvpMode = config.getBoolean("pvp", console.pvpMode);
        console.allowFlight = config.getBoolean("allow-flight", console.allowFlight);

        for (WorldServer world : console.worlds) {
            world.spawnMonsters = monsters ? 1 : 0;
            world.setSpawnFlags(monsters, animals);
        }
    }
}
