package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer world-populator registration wrapper glue.
 */
public final class CraftServerWorldPopulatorRegistrationBehaviour {
    private static final CraftServerWorldPopulatorRegistrationBehaviour INSTANCE =
            new CraftServerWorldPopulatorRegistrationBehaviour();

    private CraftServerWorldPopulatorRegistrationBehaviour() {
    }

    public static CraftServerWorldPopulatorRegistrationBehaviour getInstance() {
        return INSTANCE;
    }

    public void registerPopulators(WorldServer internal, ChunkGenerator generator) {
        if (generator != null) {
            internal.getWorld().getPopulators().addAll(generator.getDefaultPopulators(internal.getWorld()));
        }
    }
}
