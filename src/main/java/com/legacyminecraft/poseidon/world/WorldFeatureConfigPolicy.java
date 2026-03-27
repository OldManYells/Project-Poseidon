package com.legacyminecraft.poseidon.world;

/**
 * Canonical ownership for world and safety feature config keys/defaults.
 */
public final class WorldFeatureConfigPolicy {
    private static final WorldFeatureConfigPolicy INSTANCE = new WorldFeatureConfigPolicy();

    private WorldFeatureConfigPolicy() {
    }

    public static WorldFeatureConfigPolicy getInstance() {
        return INSTANCE;
    }

    public String flowingLavaFixEnabledKey() {
        return "world.settings.flowing-lava-fix.enabled";
    }

    public boolean flowingLavaFixEnabledDefault() {
        return true;
    }

    public String pistonSandGravelDupingFixEnabledKey() {
        return "world.settings.pistons.sand-gravel-duping-fix.enabled";
    }

    public boolean pistonSandGravelDupingFixEnabledDefault() {
        return true;
    }

    public String pistonOtherFixesEnabledKey() {
        return "world.settings.pistons.other-fixes.enabled";
    }

    public boolean pistonOtherFixesEnabledDefault() {
        return true;
    }

    public String pistonTransmutationFixEnabledKey() {
        return "world.settings.pistons.transmutation-fix.enabled";
    }

    public boolean pistonTransmutationFixEnabledDefault() {
        return true;
    }

    public String optimizedExplosionsKey() {
        return "world-settings.optimized-explosions";
    }

    public boolean optimizedExplosionsDefault() {
        return false;
    }

    public String sendExplosionVelocityKey() {
        return "world-settings.send-explosion-velocity";
    }

    public boolean sendExplosionVelocityDefault() {
        return true;
    }

    public String randomizeSpawnKey() {
        return "world-settings.randomize-spawn";
    }

    public boolean randomizeSpawnDefault() {
        return true;
    }

    public String optimizeSpongeRemovalKey() {
        return "fix.optimize-sponges.enabled";
    }

    public boolean optimizeSpongeRemovalDefault() {
        return true;
    }

    public String mobSpawnerAreaLimitEnabledKey() {
        return "world.settings.mob-spawner-area-limit.enable";
    }

    public boolean mobSpawnerAreaLimitEnabledDefault() {
        return true;
    }

    public String mobSpawnerAreaLimitKey() {
        return "world.settings.mob-spawner-area-limit.limit";
    }

    public int mobSpawnerAreaLimitDefault() {
        return 150;
    }

    public String mobSpawnerChunkRadiusKey() {
        return "world.settings.mob-spawner-area-limit.chunk-radius";
    }

    public int mobSpawnerChunkRadiusDefault() {
        return 8;
    }

    public String regenerateCorruptChunksEnabledKey() {
        return "emergency.debug.regenerate-corrupt-chunks.enable";
    }

    public boolean regenerateCorruptChunksEnabledDefault() {
        return false;
    }
}
