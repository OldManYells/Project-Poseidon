package com.legacyminecraft.poseidon.world;


/**
 * Canonical spawn-region preparation loop and progress policy.
 */
public final class SpawnRegionPreparationSystem {
    private static final long PROGRESS_INTERVAL_MILLIS = 1000L;
    private static final SpawnRegionPreparationSystem INSTANCE = new SpawnRegionPreparationSystem();

    private SpawnRegionPreparationSystem() {
    }

    public static SpawnRegionPreparationSystem getInstance() {
        return INSTANCE;
    }

    public long prepareSpawnRegion(
            WorldServer worldserver,
            int radius,
            long lastProgressTimestamp,
            RunningState runningState,
            ProgressReporter progressReporter
    ) {
        ChunkCoordinates spawn = worldserver.getSpawn();

        for (int offsetX = -radius; offsetX <= radius && runningState.isRunning(); offsetX += 16) {
            for (int offsetZ = -radius; offsetZ <= radius && runningState.isRunning(); offsetZ += 16) {
                long now = System.currentTimeMillis();
                lastProgressTimestamp = normalizeProgressTimestamp(now, lastProgressTimestamp);

                if (shouldReportProgress(now, lastProgressTimestamp, PROGRESS_INTERVAL_MILLIS)) {
                    progressReporter.report("Preparing spawn area", calculateProgressPercent(radius, offsetX, offsetZ));
                    lastProgressTimestamp = now;
                }

                worldserver.chunkProviderServer.getChunkAt(spawn.x + offsetX >> 4, spawn.z + offsetZ >> 4);
                while (worldserver.doLighting() && runningState.isRunning()) {
                    ;
                }
            }
        }

        return lastProgressTimestamp;
    }

    public long normalizeProgressTimestamp(long now, long lastProgressTimestamp) {
        return now < lastProgressTimestamp ? now : lastProgressTimestamp;
    }

    public boolean shouldReportProgress(long now, long lastProgressTimestamp, long intervalMillis) {
        return now > lastProgressTimestamp + intervalMillis;
    }

    public int calculateProgressPercent(int radius, int offsetX, int offsetZ) {
        int totalSteps = (radius * 2 + 1) * (radius * 2 + 1);
        int currentStep = (offsetX + radius) * (radius * 2 + 1) + offsetZ + 1;
        return currentStep * 100 / totalSteps;
    }

    public interface RunningState {
        boolean isRunning();
    }

    public interface ProgressReporter {
        void report(String task, int percent);
    }
}
