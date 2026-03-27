package org.bukkit.craftbukkit.generator;

import com.legacyminecraft.compat.bukkit.CustomChunkGeneratorBehaviour;
import net.minecraft.server.*;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.List;
import java.util.Random;

public class CustomChunkGenerator extends InternalChunkGenerator {
    private static final CustomChunkGeneratorBehaviour CUSTOM_CHUNK_GENERATOR_BEHAVIOUR =
            CustomChunkGeneratorBehaviour.getInstance();

    private final ChunkGenerator generator;
    private final WorldServer world;
    private final Random random;

    public CustomChunkGenerator(World world, long seed, ChunkGenerator generator) {
        this.world = (WorldServer) world;
        this.generator = generator;
        this.random = CUSTOM_CHUNK_GENERATOR_BEHAVIOUR.createRandom(seed);
    }

    public boolean isChunkLoaded(int x, int z) {
        return CUSTOM_CHUNK_GENERATOR_BEHAVIOUR.isChunkLoaded();
    }

    public Chunk getOrCreateChunk(int x, int z) {
        return CUSTOM_CHUNK_GENERATOR_BEHAVIOUR.getChunkAt(world, generator, random, x, z);
    }

    public void getChunkAt(IChunkProvider icp, int i, int i1) {
        CUSTOM_CHUNK_GENERATOR_BEHAVIOUR.getChunkAt(icp, i, i1);
    }

    public boolean saveChunks(boolean bln, IProgressUpdate ipu) {
        return CUSTOM_CHUNK_GENERATOR_BEHAVIOUR.saveChunks(bln, ipu);
    }

    public boolean unloadChunks() {
        return CUSTOM_CHUNK_GENERATOR_BEHAVIOUR.unloadChunks();
    }

    public boolean canSave() {
        return CUSTOM_CHUNK_GENERATOR_BEHAVIOUR.canSave();
    }

    public byte[] generate(org.bukkit.World world, Random random, int x, int z) {
        return CUSTOM_CHUNK_GENERATOR_BEHAVIOUR.generate(world, random, x, z, generator);
    }

    public Chunk getChunkAt(int x, int z) {
        return CUSTOM_CHUNK_GENERATOR_BEHAVIOUR.getChunkAt(this.world, generator, random, x, z);
    }

    public boolean canSpawn(org.bukkit.World world, int x, int z) {
        return CUSTOM_CHUNK_GENERATOR_BEHAVIOUR.canSpawn(world, x, z, generator);
    }

    public List<BlockPopulator> getDefaultPopulators(org.bukkit.World world) {
        return CUSTOM_CHUNK_GENERATOR_BEHAVIOUR.getDefaultPopulators(world, generator);
    }
}
