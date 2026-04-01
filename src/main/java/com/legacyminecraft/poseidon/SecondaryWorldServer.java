package com.legacyminecraft.poseidon;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.core.*;

import org.bukkit.generator.ChunkGenerator;

public class SecondaryWorldServer extends WorldServer {
    // CraftBukkit start
    public SecondaryWorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i, long j, WorldServer worldserver, org.bukkit.World.Environment env, ChunkGenerator gen) {
        super(minecraftserver, idatamanager, s, i, j, env, gen);
        // CraftBukkit end
        this.worldMaps = worldserver.worldMaps;
    }
}
