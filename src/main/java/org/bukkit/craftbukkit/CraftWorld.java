package org.bukkit.craftbukkit;

import com.google.common.collect.MapMaker;
import com.legacyminecraft.poseidon.compat.bukkit.BiomeConversionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldChunkLifecycleBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldChunkLoadBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldChunkPostLoadBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldChunkRegenerationBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldChunkUnloadBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldEnvironmentUpdateBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldEffectBroadcastBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldExplosionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldEntityProjectionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldEntitySpawnBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldGenericEntitySpawnBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldLoadedChunkBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldItemDropBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldSaveBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldSpawnChunkRetentionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldSpawnUpdateBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldTimeUpdateBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldTreeGenerationBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftWorldWeatherUpdateBehaviour;
import net.minecraft.server.*;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class CraftWorld implements World {
    private static final BiomeConversionBehaviour BIOME_CONVERSION_BEHAVIOUR =
            BiomeConversionBehaviour.getInstance();
    private static final CraftWorldChunkLifecycleBehaviour CRAFT_WORLD_CHUNK_LIFECYCLE_BEHAVIOUR =
            CraftWorldChunkLifecycleBehaviour.getInstance();
    private static final CraftWorldChunkLoadBehaviour CRAFT_WORLD_CHUNK_LOAD_BEHAVIOUR =
            CraftWorldChunkLoadBehaviour.getInstance();
    private static final CraftWorldChunkPostLoadBehaviour CRAFT_WORLD_CHUNK_POST_LOAD_BEHAVIOUR =
            CraftWorldChunkPostLoadBehaviour.getInstance();
    private static final CraftWorldChunkRegenerationBehaviour CRAFT_WORLD_CHUNK_REGENERATION_BEHAVIOUR =
            CraftWorldChunkRegenerationBehaviour.getInstance();
    private static final CraftWorldChunkUnloadBehaviour CRAFT_WORLD_CHUNK_UNLOAD_BEHAVIOUR =
            CraftWorldChunkUnloadBehaviour.getInstance();
    private static final CraftWorldEnvironmentUpdateBehaviour CRAFT_WORLD_ENVIRONMENT_UPDATE_BEHAVIOUR =
            CraftWorldEnvironmentUpdateBehaviour.getInstance();
    private static final CraftWorldEffectBroadcastBehaviour CRAFT_WORLD_EFFECT_BROADCAST_BEHAVIOUR =
            CraftWorldEffectBroadcastBehaviour.getInstance();
    private static final CraftWorldExplosionBehaviour CRAFT_WORLD_EXPLOSION_BEHAVIOUR =
            CraftWorldExplosionBehaviour.getInstance();
    private static final CraftWorldEntityProjectionBehaviour CRAFT_WORLD_ENTITY_PROJECTION_BEHAVIOUR =
            CraftWorldEntityProjectionBehaviour.getInstance();
    private static final CraftWorldEntitySpawnBehaviour CRAFT_WORLD_ENTITY_SPAWN_BEHAVIOUR =
            CraftWorldEntitySpawnBehaviour.getInstance();
    private static final CraftWorldGenericEntitySpawnBehaviour CRAFT_WORLD_GENERIC_ENTITY_SPAWN_BEHAVIOUR =
            CraftWorldGenericEntitySpawnBehaviour.getInstance();
    private static final CraftWorldLoadedChunkBehaviour CRAFT_WORLD_LOADED_CHUNK_BEHAVIOUR =
            CraftWorldLoadedChunkBehaviour.getInstance();
    private static final CraftWorldItemDropBehaviour CRAFT_WORLD_ITEM_DROP_BEHAVIOUR =
            CraftWorldItemDropBehaviour.getInstance();
    private static final CraftWorldSaveBehaviour CRAFT_WORLD_SAVE_BEHAVIOUR =
            CraftWorldSaveBehaviour.getInstance();
    private static final CraftWorldSpawnUpdateBehaviour CRAFT_WORLD_SPAWN_UPDATE_BEHAVIOUR =
            CraftWorldSpawnUpdateBehaviour.getInstance();
    private static final CraftWorldTimeUpdateBehaviour CRAFT_WORLD_TIME_UPDATE_BEHAVIOUR =
            CraftWorldTimeUpdateBehaviour.getInstance();
    private static final CraftWorldSpawnChunkRetentionBehaviour CRAFT_WORLD_SPAWN_CHUNK_RETENTION_BEHAVIOUR =
            CraftWorldSpawnChunkRetentionBehaviour.getInstance();
    private static final CraftWorldTreeGenerationBehaviour CRAFT_WORLD_TREE_GENERATION_BEHAVIOUR =
            CraftWorldTreeGenerationBehaviour.getInstance();
    private static final CraftWorldWeatherUpdateBehaviour CRAFT_WORLD_WEATHER_UPDATE_BEHAVIOUR =
            CraftWorldWeatherUpdateBehaviour.getInstance();
    private final WorldServer world;
    private Environment environment;
    private final CraftServer server = (CraftServer)Bukkit.getServer();
//    private ConcurrentMap<Integer, CraftChunk> unloadedChunks = new MapMaker().weakValues().makeMap();
    private final ChunkGenerator generator;
    private final List<BlockPopulator> populators = new ArrayList<BlockPopulator>();

    private static final Random rand = new Random();

    public CraftWorld(WorldServer world, ChunkGenerator gen, Environment env) {
        this.world = world;
        this.generator = gen;

        environment = env;
    }

//    public void preserveChunk(CraftChunk chunk) {
//        chunk.breakLink();
//        unloadedChunks.put((chunk.getX() << 16) + chunk.getZ(), chunk);
//    }
//
//    public Chunk popPreservedChunk(int x, int z) {
//        return unloadedChunks.remove((x << 16) + z);
//    }

    public Block getBlockAt(int x, int y, int z) {
        return getChunkAt(x >> 4, z >> 4).getBlock(x & 0xF, y & 0x7F, z & 0xF);
    }

    public int getBlockTypeIdAt(int x, int y, int z) {
        return world.getTypeId(x, y, z);
    }

    public int getHighestBlockYAt(int x, int z) {
        return world.getHighestBlockYAt(x, z);
    }

    public Location getSpawnLocation() {
        ChunkCoordinates spawn = world.getSpawn();
        float yaw = world.worldData.getYaw(); // Poseidon
        float pitch = world.worldData.getPitch(); // Poseidon
        return new Location(this, spawn.x, spawn.y, spawn.z, yaw, pitch);
    }

    public boolean setSpawnLocation(int x, int y, int z) {
        return setSpawnLocation(x, y, z, 0f, 0f); // Poseidon - moved to overloaded method
    }

    // Poseidon start
    public boolean setSpawnLocation(int x, int y, int z, float yaw, float pitch) {
        return CRAFT_WORLD_SPAWN_UPDATE_BEHAVIOUR.setSpawnLocation(this, server, world, x, y, z, yaw, pitch);
    }

    // Poseidon end

    public Chunk getChunkAt(int x, int z) {
        return this.world.chunkProviderServer.getChunkAt(x, z).bukkitChunk;
    }

    public Chunk getChunkAt(Block block) {
        return getChunkAt(block.getX() >> 4, block.getZ() >> 4);
    }

    public boolean isChunkLoaded(int x, int z) {
        return world.chunkProviderServer.isChunkLoaded(x, z);
    }

    public Chunk[] getLoadedChunks() {
        Object[] loadedChunkValues = world.chunkProviderServer.chunks.values().toArray();
        return CRAFT_WORLD_LOADED_CHUNK_BEHAVIOUR.toBukkitChunks(loadedChunkValues);
    }

    public void loadChunk(int x, int z) {
        loadChunk(x, z, true);
    }

    public boolean unloadChunk(Chunk chunk) {
        return unloadChunk(chunk.getX(), chunk.getZ());
    }

    public boolean unloadChunk(int x, int z) {
        return unloadChunk(x, z, true);
    }

    public boolean unloadChunk(int x, int z, boolean save) {
        return unloadChunk(x, z, save, false);
    }

    public boolean unloadChunkRequest(int x, int z) {
        return unloadChunkRequest(x, z, true);
    }

    public boolean unloadChunkRequest(int x, int z, boolean safe) {
        if (safe && isChunkInUse(x, z)) {
            return false;
        }

        CRAFT_WORLD_CHUNK_UNLOAD_BEHAVIOUR.queueUnload(world.chunkProviderServer, x, z);

        return true;
    }

    public boolean unloadChunk(int x, int z, boolean save, boolean safe) {
        if (safe && isChunkInUse(x, z)) {
            return false;
        }

        return CRAFT_WORLD_CHUNK_UNLOAD_BEHAVIOUR.unloadChunk(world.chunkProviderServer, x, z, save);
    }

    public boolean regenerateChunk(int x, int z) {
        unloadChunk(x, z, false, false);
        net.minecraft.server.Chunk chunk = CRAFT_WORLD_CHUNK_REGENERATION_BEHAVIOUR
                .resolveRegeneratedChunk(world.chunkProviderServer, x, z);

        chunkLoadPostProcess(chunk, x, z);

        refreshChunk(x, z);

        return chunk != null;
    }

    public boolean refreshChunk(int x, int z) {
        if (!isChunkLoaded(x, z)) {
            return false;
        }
        CRAFT_WORLD_CHUNK_LIFECYCLE_BEHAVIOUR.notifyChunkRefresh(world, x, z);

        return true;
    }


    public boolean isChunkInUse(int x, int z) {
        return CRAFT_WORLD_CHUNK_LIFECYCLE_BEHAVIOUR.isChunkInUse(
                server.getOnlinePlayers(),
                world.chunkProviderServer.world.getWorld(),
                x,
                z,
                256
        );
    }

    public boolean loadChunk(int x, int z, boolean generate) {
        if (generate) {
            // Use the default variant of loadChunk when generate == true.
            return world.chunkProviderServer.getChunkAt(x, z) != null;
        }

        net.minecraft.server.Chunk chunk = CRAFT_WORLD_CHUNK_LOAD_BEHAVIOUR
                .loadChunkIfPresentOrDisk(world.chunkProviderServer, x, z);
        if (chunk != null && !world.chunkProviderServer.chunks.containsKey(x, z)) {
            chunkLoadPostProcess(chunk, x, z);
        }
        return chunk != null;
    }

    @SuppressWarnings("unchecked")
    private void chunkLoadPostProcess(net.minecraft.server.Chunk chunk, int x, int z) {
        CRAFT_WORLD_CHUNK_POST_LOAD_BEHAVIOUR.postProcessLoadedChunk(world.chunkProviderServer, chunk, x, z);
    }

    public boolean isChunkLoaded(Chunk chunk) {
        return isChunkLoaded(chunk.getX(), chunk.getZ());
    }

    public void loadChunk(Chunk chunk) {
        loadChunk(chunk.getX(), chunk.getZ());
        ((CraftChunk) getChunkAt(chunk.getX(), chunk.getZ())).getHandle().bukkitChunk = chunk;
    }

    public WorldServer getHandle() {
        return world;
    }

    public org.bukkit.entity.Item dropItem(Location loc, ItemStack item) {
        net.minecraft.server.ItemStack stack = CRAFT_WORLD_ITEM_DROP_BEHAVIOUR.toNativeItemStack(item);
        EntityItem entity = new EntityItem(world, loc.getX(), loc.getY(), loc.getZ(), stack);
        entity.pickupDelay = 10;
        world.addEntity(entity);
        // TODO this is inconsistent with how Entity.getBukkitEntity() works.
        // However, this entity is not at the moment backed by a server entity class so it may be left.
        return new CraftItem(world.getServer(), entity);
    }

    public org.bukkit.entity.Item dropItemNaturally(Location loc, ItemStack item) {
        return dropItem(CRAFT_WORLD_ITEM_DROP_BEHAVIOUR.toNaturalDropLocation(loc, world.random), item);
    }

    public Arrow spawnArrow(Location loc, Vector velocity, float speed, float spread) {
        return CRAFT_WORLD_ENTITY_SPAWN_BEHAVIOUR.spawnArrow(world, loc, velocity, speed, spread);
    }

    public LivingEntity spawnCreature(Location loc, CreatureType creatureType) {
        return CRAFT_WORLD_ENTITY_SPAWN_BEHAVIOUR.spawnCreature(world, server, loc, creatureType);
    }

    public LightningStrike strikeLightning(Location loc) {
        return CRAFT_WORLD_ENTITY_SPAWN_BEHAVIOUR.strikeLightning(world, server, loc, false);
    }

    public LightningStrike strikeLightningEffect(Location loc) {
        return CRAFT_WORLD_ENTITY_SPAWN_BEHAVIOUR.strikeLightning(world, server, loc, true);
    }

    public boolean generateTree(Location loc, TreeType type) {
        return generateTree(loc, type, world);
    }

    public boolean generateTree(Location loc, TreeType type, BlockChangeDelegate delegate) {
        return CRAFT_WORLD_TREE_GENERATION_BEHAVIOUR.generateTree(loc, type, delegate, rand);
    }

    public TileEntity getTileEntityAt(final int x, final int y, final int z) {
        return world.getTileEntity(x, y, z);
    }

    public String getName() {
        return world.worldData.name;
    }

    @Deprecated
    public long getId() {
        return world.worldData.getSeed();
    }

    public UUID getUID() {
        return world.getUUID();
    }

    @Override
    public String toString() {
        return "CraftWorld{name=" + getName() + '}';
    }

    public long getTime() {
        long time = getFullTime() % 24000;
        if (time < 0) time += 24000;
        return time;
    }

    public void setTime(long time) {
        long margin = (time - getFullTime()) % 24000;
        if (margin < 0) margin += 24000;
        setFullTime(getFullTime() + margin);
    }

    public long getFullTime() {
        return world.getTime();
    }

    public void setFullTime(long time) {
        CRAFT_WORLD_TIME_UPDATE_BEHAVIOUR.setFullTime(world, getPlayers(), time);
    }

    public boolean createExplosion(double x, double y, double z, float power) {
        return createExplosion(x, y, z, power, false);
    }

    public boolean createExplosion(double x, double y, double z, float power, boolean setFire) {
        return createExplosion(x, y, z, power, setFire, EntityDamageEvent.DamageCause.PLUGIN_EXPLOSION);
    }

    public boolean createExplosion(double x, double y, double z, float power, boolean setFire, EntityDamageEvent.DamageCause customDamageCause){
        return CRAFT_WORLD_EXPLOSION_BEHAVIOUR.createExplosion(world, x, y, z, power, setFire, customDamageCause);
    }

    public boolean createExplosion(Location loc, float power) {
        return createExplosion(loc, power, false);
    }

    public boolean createExplosion(Location loc, float power, boolean setFire) {
        return createExplosion(loc.getX(), loc.getY(), loc.getZ(), power, setFire);
    }

    public boolean createExplosion(Location loc, float power, boolean setFire, EntityDamageEvent.DamageCause customDamageCause){
        return createExplosion(loc.getX(), loc.getY(), loc.getZ(), power, setFire, customDamageCause);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment env) {
        environment = CRAFT_WORLD_ENVIRONMENT_UPDATE_BEHAVIOUR.setEnvironment(world, environment, env);
    }

    public Block getBlockAt(Location location) {
        return getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public int getBlockTypeIdAt(Location location) {
        return getBlockTypeIdAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public int getHighestBlockYAt(Location location) {
        return getHighestBlockYAt(location.getBlockX(), location.getBlockZ());
    }

    public Chunk getChunkAt(Location location) {
        return getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4);
    }

    public ChunkGenerator getGenerator() {
        return generator;
    }

    public List<BlockPopulator> getPopulators() {
        return populators;
    }

    public Block getHighestBlockAt(int x, int z) {
        return getBlockAt(x, getHighestBlockYAt(x, z), z);
    }

    public Block getHighestBlockAt(Location location) {
        return getHighestBlockAt(location.getBlockX(), location.getBlockZ());
    }

    public Biome getBiome(int x, int z) {
        BiomeBase base = getHandle().getWorldChunkManager().getBiome(x, z);
        return BIOME_CONVERSION_BEHAVIOUR.biomeBaseToBiome(base);
    }

    public double getTemperature(int x, int z) {
        return getHandle().getWorldChunkManager().a((double[])null, x, z, 1, 1)[0];
    }

    public double getHumidity(int x, int z) {
        return getHandle().getWorldChunkManager().getHumidity(x, z);
    }

    public List<Entity> getEntities() {
        return CRAFT_WORLD_ENTITY_PROJECTION_BEHAVIOUR.toEntities(world.entityList);
    }

    public List<LivingEntity> getLivingEntities() {
        return CRAFT_WORLD_ENTITY_PROJECTION_BEHAVIOUR.toLivingEntities(world.entityList);
    }

    public List<Player> getPlayers() {
        return CRAFT_WORLD_ENTITY_PROJECTION_BEHAVIOUR.toPlayers(world.entityList);
    }

    public void save() {
        CRAFT_WORLD_SAVE_BEHAVIOUR.save(world);
    }

    public boolean isAutoSave() {
        return !world.canSave;
    }

    public void setAutoSave(boolean value) {
        world.canSave = !value;
    }

    public boolean hasStorm() {
        return world.worldData.hasStorm();
    }

    public void setStorm(boolean hasStorm) {
        CRAFT_WORLD_WEATHER_UPDATE_BEHAVIOUR.setStorm(this, world.getServer(), world, hasStorm, rand);
    }

    public int getWeatherDuration() {
        return world.worldData.getWeatherDuration();
    }

    public void setWeatherDuration(int duration) {
        world.worldData.setWeatherDuration(duration);
    }

    public boolean isThundering() {
        return world.worldData.isThundering();
    }

    public void setThundering(boolean thundering) {
        CRAFT_WORLD_WEATHER_UPDATE_BEHAVIOUR.setThundering(this, world.getServer(), world, thundering, rand);
    }

    public int getThunderDuration() {
        return world.worldData.getThunderDuration();
    }

    public void setThunderDuration(int duration) {
        world.worldData.setThunderDuration(duration);
    }

    public long getSeed() {
        return world.worldData.getSeed();
    }

    public boolean getPVP() {
        return world.pvpMode;
    }

    public void setPVP(boolean pvp) {
        world.pvpMode = pvp;
    }

    public void playEffect(Player player, Effect effect, int data) {
        playEffect(player.getLocation(), effect, data, 0);
    }

    public void playEffect(Location location, Effect effect, int data) {
        playEffect(location, effect, data, 64);
    }

    public void playEffect(Location location, Effect effect, int data, int radius) {
        CRAFT_WORLD_EFFECT_BROADCAST_BEHAVIOUR.playEffect(location, effect, data, radius, getPlayers());
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T spawn(Location location, Class<T> clazz) throws IllegalArgumentException {
        return CRAFT_WORLD_GENERIC_ENTITY_SPAWN_BEHAVIOUR.spawn(world, location, clazz);
    }

    public ChunkSnapshot getEmptyChunkSnapshot(int x, int z, boolean includeBiome, boolean includeBiomeTempRain) {
        return CraftChunk.getEmptyChunkSnapshot(x, z, this, includeBiome, includeBiomeTempRain);
    }

    public void setSpawnFlags(boolean allowMonsters, boolean allowAnimals) {
        world.setSpawnFlags(allowMonsters, allowAnimals);
    }

    public boolean getAllowAnimals() {
        return world.allowAnimals;
    }

    public boolean getAllowMonsters() {
        return world.allowMonsters;
    }

    public int getMaxHeight() {
        return 128;
    }

    public boolean getKeepSpawnInMemory() {
        return world.keepSpawnInMemory;
    }

    public void setKeepSpawnInMemory(boolean keepLoaded) {
        CRAFT_WORLD_SPAWN_CHUNK_RETENTION_BEHAVIOUR.setKeepSpawnInMemory(this, world, keepLoaded);
    }
}
