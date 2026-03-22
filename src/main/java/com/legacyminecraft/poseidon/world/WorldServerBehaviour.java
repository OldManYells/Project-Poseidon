package com.legacyminecraft.poseidon.world;

/**
 * Canonical behaviour for world-server access and transition policies.
 */
public final class WorldServerBehaviour {
    private static final WorldServerBehaviour INSTANCE = new WorldServerBehaviour();

    private WorldServerBehaviour() {
    }

    public static WorldServerBehaviour getInstance() {
        return INSTANCE;
    }

    public int maxSpawnAxisDistance(int spawnX, int spawnZ, int blockX, int blockZ) {
        int deltaX = Math.abs(blockX - spawnX);
        int deltaZ = Math.abs(blockZ - spawnZ);
        return Math.max(deltaX, deltaZ);
    }

    public boolean canBypassSpawnProtection(int maxAxisDistance, int spawnProtectionRadius, boolean isOperator) {
        return maxAxisDistance > spawnProtectionRadius || isOperator;
    }

    public boolean canBypassSpawnProtection(
            int spawnX,
            int spawnZ,
            int blockX,
            int blockZ,
            int spawnProtectionRadius,
            boolean isOperator
    ) {
        int maxAxisDistance = this.maxSpawnAxisDistance(spawnX, spawnZ, blockX, blockZ);
        return this.canBypassSpawnProtection(maxAxisDistance, spawnProtectionRadius, isOperator);
    }

    public boolean shouldBroadcastWeatherStateChange(boolean previousRaining, boolean currentRaining) {
        return previousRaining != currentRaining;
    }

    public int weatherStatePacketType(boolean wasRaining) {
        return wasRaining ? 2 : 1;
    }
}
