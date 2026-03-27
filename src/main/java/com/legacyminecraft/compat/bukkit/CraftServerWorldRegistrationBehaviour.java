package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer world registration wrapper glue.
 */
public final class CraftServerWorldRegistrationBehaviour {
    private static final CraftServerWorldRegistrationBehaviour INSTANCE =
            new CraftServerWorldRegistrationBehaviour();

    private CraftServerWorldRegistrationBehaviour() {
    }

    public static CraftServerWorldRegistrationBehaviour getInstance() {
        return INSTANCE;
    }

    public void registerWorld(MinecraftServer console, WorldServer internal) {
        internal.worldMaps = console.worlds.get(0).worldMaps;
        internal.tracker = new EntityTracker(console, internal.dimension);
        internal.addIWorldAccess((IWorldAccess) new WorldManager(console, internal));
        internal.spawnMonsters = 1;
        internal.setSpawnFlags(true, true);
        console.worlds.add(internal);
    }
}
