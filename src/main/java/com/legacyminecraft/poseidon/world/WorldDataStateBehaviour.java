package com.legacyminecraft.poseidon.world;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.WorldData;

import java.util.List;

/**
 * Canonical world metadata state/NBT behaviour for legacy WorldData wrappers.
 */
public final class WorldDataStateBehaviour {
    private static final WorldDataStateBehaviour INSTANCE = new WorldDataStateBehaviour();

    private WorldDataStateBehaviour() {
    }

    public static WorldDataStateBehaviour getInstance() {
        return INSTANCE;
    }

    public void readFromTag(WorldData worldData, NBTTagCompound tag) {
        worldData.poseidonSetSeed(tag.getLong("RandomSeed"));
        worldData.setSpawn(
                tag.e("SpawnX"),
                tag.e("SpawnY"),
                tag.e("SpawnZ"),
                tag.g("SpawnYaw"),
                tag.g("SpawnPitch")
        );
        worldData.a(tag.getLong("Time"));
        worldData.poseidonSetLastPlayed(tag.getLong("LastPlayed"));
        worldData.b(tag.getLong("SizeOnDisk"));
        worldData.a(tag.getString("LevelName"));
        worldData.a(tag.e("version"));
        worldData.setWeatherDuration(tag.e("rainTime"));
        worldData.setStorm(tag.m("raining"));
        worldData.setThunderDuration(tag.e("thunderTime"));
        worldData.setThundering(tag.m("thundering"));
        if (tag.hasKey("Player")) {
            worldData.poseidonSetCachedPlayerData(tag.k("Player"));
        }
    }

    public NBTTagCompound createSaveTag(WorldData worldData) {
        return buildDataTag(worldData, worldData.poseidonGetCachedPlayerData());
    }

    public NBTTagCompound createSaveTagWithFirstPlayer(WorldData worldData, List players) {
        NBTTagCompound playerTag = serializeFirstPlayer(players);
        return buildDataTag(worldData, playerTag);
    }

    public NBTTagCompound buildDataTag(WorldData worldData, NBTTagCompound playerTag) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setLong("RandomSeed", worldData.getSeed());
        tag.a("SpawnX", worldData.c());
        tag.a("SpawnY", worldData.d());
        tag.a("SpawnZ", worldData.e());
        tag.a("SpawnYaw", worldData.getYaw());
        tag.a("SpawnPitch", worldData.getPitch());
        tag.setLong("Time", worldData.f());
        tag.setLong("SizeOnDisk", worldData.g());
        tag.setLong("LastPlayed", System.currentTimeMillis());
        tag.setString("LevelName", worldData.name);
        tag.a("version", worldData.i());
        tag.a("rainTime", worldData.getWeatherDuration());
        tag.a("raining", worldData.hasStorm());
        tag.a("thunderTime", worldData.getThunderDuration());
        tag.a("thundering", worldData.isThundering());
        if (playerTag != null) {
            tag.a("Player", playerTag);
        }
        return tag;
    }

    public NBTTagCompound serializeFirstPlayer(List players) {
        if (players == null || players.isEmpty()) {
            return null;
        }

        EntityHuman firstPlayer = (EntityHuman) players.get(0);
        NBTTagCompound playerTag = new NBTTagCompound();
        firstPlayer.d(playerTag);
        return playerTag;
    }
}
