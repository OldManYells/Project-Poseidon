package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.NetherCaveCarvingBehaviour;

public class MapGenCavesHell extends MapGenBase {
    private static final NetherCaveCarvingBehaviour NETHER_CAVE_CARVING_BEHAVIOUR = NetherCaveCarvingBehaviour.getInstance();

    public MapGenCavesHell() {}

    protected void a(int i, int j, byte[] abyte, double d0, double d1, double d2) {
        NETHER_CAVE_CARVING_BEHAVIOUR.generateLargeCaveNode(i, j, abyte, d0, d1, d2, i, j, this.a, this.b);
    }

    protected void a(int i, int j, byte[] abyte, double d0, double d1, double d2, float f, float f1, float f2, int k, int l, double d3) {
        NETHER_CAVE_CARVING_BEHAVIOUR.generateCaveNode(i, j, abyte, d0, d1, d2, f, f1, f2, k, l, d3, this.a, this.b);
    }

    protected void a(World world, int i, int j, int k, int l, byte[] abyte) {
        NETHER_CAVE_CARVING_BEHAVIOUR.generateChunkCaves(world, i, j, k, l, abyte, this.a, this.b);
    }
}
