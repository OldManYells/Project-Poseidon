package com.legacyminecraft.compat.bukkit;

import java.io.File;

/**
 * Canonical compat WorldServer scaffold.
 */
public class WorldServer extends World {
    public int dimension;
    public boolean weirdIsOpCache;
    public final EntityTracker tracker = new EntityTracker();
    public WorldData worldData = new WorldData("world");
    public WorldProvider worldProvider = new WorldProvider();
    private final ChunkCoordinates spawn = new ChunkCoordinates(0, 64, 0);
    private long seed;
    private long time;
    public boolean pvpMode;
    public boolean allowAnimals = true;
    public boolean allowMonsters = true;
    public boolean keepSpawnInMemory = true;
    public boolean canSave = true;
    public final ChunkProviderServer chunkProviderServer =
            new ChunkProviderServer(this, null, null);

    public WorldServer() {
    }

    public WorldServer(
            MinecraftServer server,
            ServerNBTManager serverNbtManager,
            String worldName,
            int dimension,
            long seed,
            Environment environment,
            ChunkGenerator chunkGenerator
    ) {
        super(worldName, seed);
        this.dimension = dimension;
        this.seed = seed;
        this.worldData = new WorldData(worldName);
    }

    public void checkSleepStatus() {
    }

    @Override
    public CraftWorld getWorld() {
        return new CraftWorld();
    }

    @Override
    public CraftServer getServer() {
        return new CraftServer();
    }

    public boolean isLoaded(int x, int y, int z) {
        return true;
    }

    public TileEntity getTileEntity(int x, int y, int z) {
        return new TileEntitySign();
    }

    public void notify(int x, int y, int z) {
    }

    public ChunkCoordinates getSpawn() {
        return spawn;
    }

    public int getTypeId(int x, int y, int z) {
        return 0;
    }

    public int getHighestBlockYAt(int x, int z) {
        return 0;
    }

    public Entity getEntity(int id) {
        Entity entity = new Entity();
        entity.id = id;
        return entity;
    }

    public PathEntity findPath(Entity source, Entity target, float range) {
        return new PathEntity();
    }

    public long getSeed() {
        return seed;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setSpawnFlags(boolean allowMonsters, boolean allowAnimals) {
        this.allowMonsters = allowMonsters;
        this.allowAnimals = allowAnimals;
    }

    public Chunk getChunkAt(int x, int z) {
        return new Chunk(this, new byte[0], x, z);
    }

    public void addEntity(Entity entity) {
    }

    public void addEntity(Entity entity, CreatureSpawnEvent.SpawnReason reason) {
    }

    public void strikeLightning(EntityWeatherStorm lightning) {
    }

    public ExplosionResult createExplosion(
            Entity source,
            double x,
            double y,
            double z,
            float power,
            boolean setFire,
            EntityDamageEvent.DamageCause damageCause
    ) {
        return new ExplosionResult(false);
    }

    public void save(boolean flush, Object progressUpdate) {
    }

    public boolean v() {
        return false;
    }

    public static final class ExplosionResult {
        public final boolean wasCanceled;

        public ExplosionResult(boolean wasCanceled) {
            this.wasCanceled = wasCanceled;
        }
    }

    public static final class WorldData {
        public final String name;
        private float yaw;
        private float pitch;
        private boolean storm;
        private int weatherDuration;
        private boolean thundering;
        private int thunderDuration;
        private int spawnX;
        private int spawnY = 64;
        private int spawnZ;

        public WorldData(String name) {
            this.name = name;
        }

        public float getYaw() {
            return yaw;
        }

        public float getPitch() {
            return pitch;
        }

        public boolean hasStorm() {
            return storm;
        }

        public void setStorm(boolean storm) {
            this.storm = storm;
        }

        public int getWeatherDuration() {
            return weatherDuration;
        }

        public void setWeatherDuration(int weatherDuration) {
            this.weatherDuration = weatherDuration;
        }

        public boolean isThundering() {
            return thundering;
        }

        public void setThundering(boolean thundering) {
            this.thundering = thundering;
        }

        public int getThunderDuration() {
            return thunderDuration;
        }

        public void setThunderDuration(int thunderDuration) {
            this.thunderDuration = thunderDuration;
        }

        public void setSpawn(int x, int y, int z, float yaw, float pitch) {
            this.spawnX = x;
            this.spawnY = y;
            this.spawnZ = z;
            this.yaw = yaw;
            this.pitch = pitch;
        }
    }
}
