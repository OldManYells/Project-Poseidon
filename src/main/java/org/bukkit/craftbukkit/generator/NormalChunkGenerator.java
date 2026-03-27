package org.bukkit.craftbukkit.generator;

import com.legacyminecraft.compat.bukkit.NormalChunkGeneratorBehaviour;
import net.minecraft.server.Chunk;
import net.minecraft.server.IChunkProvider;
import net.minecraft.server.IProgressUpdate;
import net.minecraft.server.World;
import org.bukkit.generator.BlockPopulator;

import java.util.List;
import java.util.Random;

public class NormalChunkGenerator extends InternalChunkGenerator {
    private static final NormalChunkGeneratorBehaviour NORMAL_CHUNK_GENERATOR_BEHAVIOUR =
            NormalChunkGeneratorBehaviour.getInstance();

    private final IChunkProvider provider;

    public NormalChunkGenerator(World world, long seed) {
        provider = NORMAL_CHUNK_GENERATOR_BEHAVIOUR.resolveChunkProvider(world);
    }

    public byte[] generate(org.bukkit.World world, Random random, int x, int z) {
        return NORMAL_CHUNK_GENERATOR_BEHAVIOUR.generateUnsupported();
    }

    public boolean canSpawn(org.bukkit.World world, int x, int z) {
        return NORMAL_CHUNK_GENERATOR_BEHAVIOUR.canSpawn(world, x, z);
    }

    public List<BlockPopulator> getDefaultPopulators(org.bukkit.World world) {
        return NORMAL_CHUNK_GENERATOR_BEHAVIOUR.getDefaultPopulators(world);
    }

    public boolean isChunkLoaded(int i, int i1) {
        return NORMAL_CHUNK_GENERATOR_BEHAVIOUR.isChunkLoaded(provider, i, i1);
    }

    public Chunk getOrCreateChunk(int i, int i1) {
        return NORMAL_CHUNK_GENERATOR_BEHAVIOUR.getOrCreateChunk(provider, i, i1);
    }

    public Chunk getChunkAt(int i, int i1) {
        return NORMAL_CHUNK_GENERATOR_BEHAVIOUR.getChunkAt(provider, i, i1);
    }

    public void getChunkAt(IChunkProvider icp, int i, int i1) {
        NORMAL_CHUNK_GENERATOR_BEHAVIOUR.getChunkAt(provider, icp, i, i1);
    }

    public boolean saveChunks(boolean bln, IProgressUpdate ipu) {
        return NORMAL_CHUNK_GENERATOR_BEHAVIOUR.saveChunks(provider, bln, ipu);
    }

    public boolean unloadChunks() {
        return NORMAL_CHUNK_GENERATOR_BEHAVIOUR.unloadChunks(provider);
    }

    public boolean canSave() {
        return NORMAL_CHUNK_GENERATOR_BEHAVIOUR.canSave(provider);
    }
}
