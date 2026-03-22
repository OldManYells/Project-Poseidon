package net.minecraft.server;

import com.legacyminecraft.poseidon.world.ChunkWindowAccessBehaviour;

public class ChunkCache implements IBlockAccess {
    private static final ChunkWindowAccessBehaviour CHUNK_WINDOW_ACCESS_BEHAVIOUR = ChunkWindowAccessBehaviour.getInstance();

    private int a;
    private int b;
    private Chunk[][] c;
    private World d;

    public ChunkCache(World world, int i, int j, int k, int l, int i1, int j1) {
        this.d = world;
        ChunkWindowAccessBehaviour.WindowState state = CHUNK_WINDOW_ACCESS_BEHAVIOUR.initialize(world, i, k, l, j1);
        this.a = state.minChunkX;
        this.b = state.minChunkZ;
        this.c = state.chunks;
    }

    public int getTypeId(int i, int j, int k) {
        return CHUNK_WINDOW_ACCESS_BEHAVIOUR.getTypeId(this.a, this.b, this.c, i, j, k);
    }

    public TileEntity getTileEntity(int i, int j, int k) {
        return CHUNK_WINDOW_ACCESS_BEHAVIOUR.getTileEntity(this.a, this.b, this.c, i, j, k);
    }

    public int getData(int i, int j, int k) {
        return CHUNK_WINDOW_ACCESS_BEHAVIOUR.getData(this.a, this.b, this.c, i, j, k);
    }

    public Material getMaterial(int i, int j, int k) {
        return CHUNK_WINDOW_ACCESS_BEHAVIOUR.getMaterial(this.getTypeId(i, j, k));
    }

    public boolean e(int i, int j, int k) {
        return CHUNK_WINDOW_ACCESS_BEHAVIOUR.isSolidRenderable(Block.byId[this.getTypeId(i, j, k)]);
    }
}
