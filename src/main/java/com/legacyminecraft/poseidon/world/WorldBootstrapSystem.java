package com.legacyminecraft.poseidon.world;


import java.io.File;
import java.util.logging.Logger;

/**
 * Canonical world bootstrap flow for legacy MinecraftServer wrappers.
 */
public final class WorldBootstrapSystem {
    private static final WorldBootstrapSystem INSTANCE = new WorldBootstrapSystem();

    private final LegacyDimensionFolderMigrationHandler legacyDimensionFolderMigrationHandler = LegacyDimensionFolderMigrationHandler.getInstance();
    private final SpawnRegionPreparationSystem spawnRegionPreparationSystem = SpawnRegionPreparationSystem.getInstance();

    private WorldBootstrapSystem() {
    }

    public static WorldBootstrapSystem getInstance() {
        return INSTANCE;
    }

    public void bootstrapWorlds(
            MinecraftServer server,
            Convertable convertable,
            String worldName,
            long seed,
            Logger logger,
            ProgressReporter progressReporter,
            CompletionHook completionHook
    ) {
        if (convertable.isConvertable(worldName)) {
            logger.info("Converting map!");
            convertable.convert(worldName, new ConvertProgressUpdater(server));
        }

        for (int j = 0; j < (server.propertyManager.getBoolean("allow-nether", true) ? 2 : 1); ++j) {
            WorldServer world;
            int dimension = j == 0 ? 0 : -1;
            String worldType = Environment.getEnvironment(dimension).toString().toLowerCase();
            String name = (dimension == 0) ? worldName : worldName + "_" + worldType;

            ChunkGenerator gen = server.server.getGenerator(name);

            if (j == 0) {
                world = new WorldServer(server, new ServerNBTManager(new File("."), worldName, true), worldName, dimension, seed, com.legacyminecraft.compat.bukkit.World.Environment.getEnvironment(dimension), gen);
            } else {
                String dim = "DIM-1";

                File newWorld = new File(new File(name), dim);
                File oldWorld = new File(new File(worldName), dim);
                legacyDimensionFolderMigrationHandler.migrateAndLog(logger, worldType, oldWorld, newWorld);

                world = new SecondaryWorldServer(server, new ServerNBTManager(new File("."), name, true), name, dimension, seed, server.worlds.get(0), com.legacyminecraft.compat.bukkit.World.Environment.getEnvironment(dimension), gen);
            }

            if (gen != null) {
                world.getWorld().getPopulators().addAll(gen.getDefaultPopulators(world.getWorld()));
            }

            server.server.getPluginManager().callEvent(new WorldInitEvent(world.getWorld()));

            world.tracker = new EntityTracker(server, dimension);
            world.addIWorldAccess(new WorldManager(server, world));
            world.spawnMonsters = server.propertyManager.getBoolean("spawn-monsters", true) ? 1 : 0;
            world.setSpawnFlags(server.propertyManager.getBoolean("spawn-monsters", true), server.spawnAnimals);
            server.worlds.add(world);
            server.serverConfigurationManager.setPlayerFileData(server.worlds.toArray(new WorldServer[0]));
        }

        short short1 = 196;
        long k = System.currentTimeMillis();
        SpawnRegionPreparationSystem.RunningState runningState = new SpawnRegionPreparationSystem.RunningState() {
            @Override
            public boolean isRunning() {
                return MinecraftServer.isRunning(server);
            }
        };

        for (int l = 0; l < server.worlds.size(); ++l) {
            WorldServer worldserver = server.worlds.get(l);
            logger.info("Preparing start region for level " + l + " (Seed: " + worldserver.getSeed() + ")");
            if (worldserver.getWorld().getKeepSpawnInMemory()) {
                k = spawnRegionPreparationSystem.prepareSpawnRegion(
                        worldserver,
                        short1,
                        k,
                        runningState,
                        progressReporter
                );
            }
        }

        for (World world : server.worlds) {
            server.server.getPluginManager().callEvent(new WorldLoadEvent(world.getWorld()));
        }

        completionHook.onBootstrapComplete();
    }

    public interface ProgressReporter extends SpawnRegionPreparationSystem.ProgressReporter {
    }

    public interface CompletionHook {
        void onBootstrapComplete();
    }
}
