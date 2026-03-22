package net.minecraft.server;

import com.legacyminecraft.poseidon.world.chunk.ChunkNbtLayoutBehaviour;

import java.io.*;

public class ChunkRegionLoader implements IChunkLoader {
    private static final ChunkNbtLayoutBehaviour CHUNK_NBT_LAYOUT_BEHAVIOUR = ChunkNbtLayoutBehaviour.getInstance();

    private final File a;

    public ChunkRegionLoader(File file1) {
        this.a = file1;
    }

    public Chunk a(World world, int i, int j) throws IOException {
        DataInputStream datainputstream = RegionFileCache.c(this.a, i, j);

        if (datainputstream != null) {
            NBTTagCompound nbttagcompound = CompressedStreamTools.a((DataInput) datainputstream);

            if (!CHUNK_NBT_LAYOUT_BEHAVIOUR.hasLevelData(nbttagcompound)) {
                System.out.println("Chunk file at " + i + "," + j + " is missing level data, skipping");
                return null;
            } else if (!CHUNK_NBT_LAYOUT_BEHAVIOUR.hasBlockData(nbttagcompound.k("Level"))) {
                System.out.println("Chunk file at " + i + "," + j + " is missing block data, skipping");
                return null;
            } else {
                NBTTagCompound levelTag = nbttagcompound.k("Level");
                Chunk chunk = CHUNK_NBT_LAYOUT_BEHAVIOUR.loadChunk(world, levelTag);

                if (!CHUNK_NBT_LAYOUT_BEHAVIOUR.isExpectedChunkLocation(chunk, i, j)) {
                    System.out.println("Chunk file at " + i + "," + j + " is in the wrong location; relocating. (Expected " + i + ", " + j + ", got " + chunk.x + ", " + chunk.z + ")");
                    CHUNK_NBT_LAYOUT_BEHAVIOUR.overwriteChunkCoordinates(levelTag, i, j);
                    chunk = CHUNK_NBT_LAYOUT_BEHAVIOUR.loadChunk(world, levelTag);
                }

                chunk.h();
                return chunk;
            }
        } else {
            return null;
        }
    }

    public void a(World world, Chunk chunk) {
        world.k();

        try {
            DataOutputStream dataoutputstream = RegionFileCache.d(this.a, chunk.x, chunk.z);
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();

            nbttagcompound.a("Level", (NBTBase) nbttagcompound1);
            ChunkLoader.a(chunk, world, nbttagcompound1);
            CompressedStreamTools.a(nbttagcompound, (DataOutput) dataoutputstream);
            dataoutputstream.close();
            WorldData worlddata = world.q();

            worlddata.b(worlddata.g() + (long) RegionFileCache.b(this.a, chunk.x, chunk.z));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void b(World world, Chunk chunk) {}

    public void a() {}

    public void b() {}
}
