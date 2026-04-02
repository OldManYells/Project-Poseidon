package com.legacyminecraft.poseidon.world.core;

import com.legacyminecraft.poseidon.MinecraftServer;
import com.legacyminecraft.poseidon.api.IDataManager;
import org.bukkit.generator.ChunkGenerator;

public class SecondaryWorldServer extends WorldServer {
    public SecondaryWorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i, long j, WorldServer worldserver, org.bukkit.World.Environment env, ChunkGenerator gen) {
        super(minecraftserver, idatamanager, s, i, j, env, gen);
        this.worldMaps = worldserver.worldMaps;
    }
}
