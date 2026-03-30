package org.bukkit.craftbukkit.server;

import net.minecraft.server.IDataManager;
import net.minecraft.server.MinecraftServer;
import org.bukkit.craftbukkit.world.WorldServer;
import org.bukkit.generator.ChunkGenerator;

public class SecondaryWorldServer extends WorldServer {
    // CraftBukkit start
    public SecondaryWorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i, long j, WorldServer worldserver, org.bukkit.World.Environment env, ChunkGenerator gen) {
        super(minecraftserver, idatamanager, s, i, j, env, gen);
        // CraftBukkit end
        this.worldMaps = worldserver.worldMaps;
    }
}
