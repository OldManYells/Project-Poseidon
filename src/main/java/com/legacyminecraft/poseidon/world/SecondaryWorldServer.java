package com.legacyminecraft.poseidon.world;

/**
 * World-local secondary dimension server scaffold.
 */
public class SecondaryWorldServer extends WorldServer {
    public SecondaryWorldServer(
            MinecraftServer server,
            ServerNBTManager serverNbtManager,
            String worldName,
            int dimension,
            long seed,
            WorldServer parentWorld,
            com.legacyminecraft.compat.bukkit.Environment environment,
            ChunkGenerator chunkGenerator
    ) {
        super(server, serverNbtManager, worldName, dimension, seed, environment, chunkGenerator);
    }
}
