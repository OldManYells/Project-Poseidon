package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldChunkLifecycleWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");

    @Test
    public void craftWorldDelegatesChunkRefreshAndInUsePolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_WORLD_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BiomeConversionBehaviour"));
        Assert.assertTrue(text.contains("BIOME_CONVERSION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("biomeBaseToBiome(base)"));
        Assert.assertTrue(text.contains("CraftWorldChunkLifecycleBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_CHUNK_LIFECYCLE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CraftWorldChunkLoadBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_CHUNK_LOAD_BEHAVIOUR"));
        Assert.assertTrue(text.contains("loadChunkIfPresentOrDisk(world.chunkProviderServer, x, z)"));
        Assert.assertTrue(text.contains("CraftWorldChunkRegenerationBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_CHUNK_REGENERATION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("resolveRegeneratedChunk(world.chunkProviderServer, x, z)"));
        Assert.assertTrue(text.contains("CraftWorldEntitySpawnBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_ENTITY_SPAWN_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CraftWorldEntityProjectionBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_ENTITY_PROJECTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("toEntities(world.entityList)"));
        Assert.assertTrue(text.contains("toLivingEntities(world.entityList)"));
        Assert.assertTrue(text.contains("toPlayers(world.entityList)"));
        Assert.assertTrue(text.contains("CraftWorldGenericEntitySpawnBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_GENERIC_ENTITY_SPAWN_BEHAVIOUR"));
        Assert.assertTrue(text.contains("spawn(world, location, clazz)"));
        Assert.assertTrue(text.contains("spawnArrow(world, loc, velocity, speed, spread)"));
        Assert.assertTrue(text.contains("spawnCreature(world, server, loc, creatureType)"));
        Assert.assertTrue(text.contains("strikeLightning(world, server, loc, false)"));
        Assert.assertTrue(text.contains("strikeLightning(world, server, loc, true)"));
        Assert.assertTrue(text.contains("CraftWorldEffectBroadcastBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_EFFECT_BROADCAST_BEHAVIOUR"));
        Assert.assertTrue(text.contains("playEffect(location, effect, data, radius, getPlayers())"));
        Assert.assertTrue(text.contains("CraftWorldTreeGenerationBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_TREE_GENERATION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("generateTree(loc, type, delegate, rand)"));
        Assert.assertTrue(text.contains("CraftWorldChunkUnloadBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_CHUNK_UNLOAD_BEHAVIOUR"));
        Assert.assertTrue(text.contains("queueUnload(world.chunkProviderServer, x, z)"));
        Assert.assertTrue(text.contains("unloadChunk(world.chunkProviderServer, x, z, save)"));
        Assert.assertTrue(text.contains("CraftWorldEnvironmentUpdateBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_ENVIRONMENT_UPDATE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("setEnvironment(world, environment, env)"));
        Assert.assertTrue(text.contains("CraftWorldExplosionBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_EXPLOSION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("createExplosion(world, x, y, z, power, setFire, customDamageCause)"));
        Assert.assertTrue(text.contains("CraftWorldLoadedChunkBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_LOADED_CHUNK_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CraftWorldItemDropBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_ITEM_DROP_BEHAVIOUR"));
        Assert.assertTrue(text.contains("toNativeItemStack(item)"));
        Assert.assertTrue(text.contains("toNaturalDropLocation(loc, world.random)"));
        Assert.assertTrue(text.contains("CraftWorldSaveBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_SAVE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("save(world)"));
        Assert.assertTrue(text.contains("CraftWorldSpawnUpdateBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_SPAWN_UPDATE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("setSpawnLocation(this, server, world, x, y, z, yaw, pitch)"));
        Assert.assertTrue(text.contains("CraftWorldTimeUpdateBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_TIME_UPDATE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("setFullTime(world, getPlayers(), time)"));
        Assert.assertTrue(text.contains("CraftWorldSpawnChunkRetentionBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_SPAWN_CHUNK_RETENTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("setKeepSpawnInMemory(this, world, keepLoaded)"));
        Assert.assertTrue(text.contains("CraftWorldWeatherUpdateBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_WEATHER_UPDATE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("setStorm(this, world.getServer(), world, hasStorm, rand)"));
        Assert.assertTrue(text.contains("setThundering(this, world.getServer(), world, thundering, rand)"));
        Assert.assertTrue(text.contains("toBukkitChunks(loadedChunkValues)"));
        Assert.assertTrue(text.contains("notifyChunkRefresh(world, x, z)"));
        Assert.assertTrue(text.contains("isChunkInUse("));
        Assert.assertTrue(text.contains("server.getOnlinePlayers()"));
        Assert.assertFalse(text.contains("net.minecraft.server.Chunk chunk = (net.minecraft.server.Chunk) chunks[i];"));
        Assert.assertFalse(text.contains("craftChunks[i] = chunk.bukkitChunk;"));
        Assert.assertFalse(text.contains("item.getTypeId(),"));
        Assert.assertFalse(text.contains("item.getAmount(),"));
        Assert.assertFalse(text.contains("item.getDurability()"));
        Assert.assertFalse(text.contains("world.random.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D"));
        Assert.assertFalse(text.contains("SpawnChangeEvent event = new SpawnChangeEvent(this, previousLocation);"));
        Assert.assertFalse(text.contains("world.worldData.setSpawn(x, y, z, yaw, pitch);"));
        Assert.assertFalse(text.contains("WeatherChangeEvent weather = new WeatherChangeEvent((org.bukkit.World) this, hasStorm);"));
        Assert.assertFalse(text.contains("ThunderChangeEvent thunder = new ThunderChangeEvent((org.bukkit.World) this, thundering);"));
        Assert.assertFalse(text.contains("setWeatherDuration(rand.nextInt(12000) + 12000);"));
        Assert.assertFalse(text.contains("setThunderDuration(rand.nextInt(12000) + 3600);"));
        Assert.assertFalse(text.contains("boolean oldSave = world.canSave;"));
        Assert.assertFalse(text.contains("world.save(true, null);"));
        Assert.assertFalse(text.contains("world.chunkProviderServer.queueUnload(x, z);"));
        Assert.assertFalse(text.contains("world.worldProvider = WorldProvider.byDimension(environment.getId());"));
        Assert.assertFalse(text.contains("world.createExplosion(null, x, y, z, power, setFire, customDamageCause).wasCanceled ? false : true"));
        Assert.assertFalse(text.contains("chunk.removeEntities();"));
        Assert.assertFalse(text.contains("world.chunkProviderServer.saveChunk(chunk);"));
        Assert.assertFalse(text.contains("chunk = world.chunkProviderServer.emptyChunk;"));
        Assert.assertFalse(text.contains("chunk = world.chunkProviderServer.chunkProvider.getOrCreateChunk(x, z);"));
        Assert.assertFalse(text.contains("EntityArrow arrow = new EntityArrow(world);"));
        Assert.assertFalse(text.contains("EntityTypes.a(creatureType.getName(), world)"));
        Assert.assertFalse(text.contains("new EntityWeatherStorm(world, loc.getX(), loc.getY(), loc.getZ());"));
        Assert.assertFalse(text.contains("Packet61 packet = new Packet61(packetData, location.getBlockX(), location.getBlockY(), location.getBlockZ(), data);"));
        Assert.assertFalse(text.contains("distance = (int) player.getLocation().distance(location);"));
        Assert.assertFalse(text.contains("for (Object o: world.entityList)"));
        Assert.assertFalse(text.contains("for (Object o : world.entityList)"));
        Assert.assertFalse(text.contains("if (Boat.class.isAssignableFrom(clazz))"));
        Assert.assertFalse(text.contains("entity = new EntityBoat(world, x, y, z);"));
        Assert.assertFalse(text.contains("throw new IllegalArgumentException(\"Cannot spawn an entity for \" + clazz.getName());"));
        Assert.assertFalse(text.contains("if (base == BiomeBase.RAINFOREST)"));
        Assert.assertFalse(text.contains("case BIG_TREE:"));
        Assert.assertFalse(text.contains("new WorldGenBigTree().generate(delegate, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());"));
        Assert.assertFalse(text.contains("ChunkCoordinates chunkcoordinates = this.world.getSpawn();"));
        Assert.assertFalse(text.contains("for (int x = -12; x <= 12; x++)"));
        Assert.assertFalse(text.contains("for (int z = -12; z <= 12; z++)"));
        Assert.assertFalse(text.contains("cp.getHandle().netServerHandler.sendPacket(new Packet4UpdateTime(cp.getHandle().getPlayerTime()));"));
        Assert.assertFalse(text.contains("for (int xx = px; xx < (px + 16); xx++)"));
        Assert.assertFalse(text.contains("world.notify(xx, 0, pz);"));
        Assert.assertFalse(text.contains("for (Player player : players)"));
        Assert.assertFalse(text.contains("Math.abs(loc.getBlockX() - (x << 4)) <= 256"));
    }

    @Test
    public void craftWorldDelegatesChunkPostLoadOrchestrationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_WORLD_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CraftWorldChunkPostLoadBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_WORLD_CHUNK_POST_LOAD_BEHAVIOUR"));
        Assert.assertTrue(text.contains("postProcessLoadedChunk(world.chunkProviderServer, chunk, x, z)"));
        Assert.assertFalse(text.contains("chunk.loadNOP();"));
        Assert.assertFalse(text.contains("chunk.addEntities();"));
        Assert.assertFalse(text.contains("getChunkAt(world.chunkProviderServer, x - 1, z - 1);"));
    }
}
