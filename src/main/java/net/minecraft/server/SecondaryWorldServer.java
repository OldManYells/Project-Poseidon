package net.minecraft.server;

import com.legacyminecraft.poseidon.world.SharedWorldMapLinkBehaviour;
import org.bukkit.generator.ChunkGenerator;

public class SecondaryWorldServer extends WorldServer {
    private static final SharedWorldMapLinkBehaviour SHARED_WORLD_MAP_LINK_BEHAVIOUR = SharedWorldMapLinkBehaviour.getInstance();

    // CraftBukkit start
    public SecondaryWorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i, long j, WorldServer worldserver, org.bukkit.World.Environment env, ChunkGenerator gen) {
        super(minecraftserver, idatamanager, s, i, j, env, gen);
        // CraftBukkit end
        SHARED_WORLD_MAP_LINK_BEHAVIOUR.linkSharedMaps(this, worldserver);
    }
}
