package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.OverworldCaveCarvingBehaviour;

public class MapGenCaves extends MapGenBase {
    private static final OverworldCaveCarvingBehaviour OVERWORLD_CAVE_CARVING_BEHAVIOUR = OverworldCaveCarvingBehaviour.getInstance();

    public MapGenCaves() {}

    protected void a(int i, int j, byte[] abyte, double d0, double d1, double d2) {
        OVERWORLD_CAVE_CARVING_BEHAVIOUR.generateLargeCaveNode(i, j, abyte, d0, d1, d2, i, j, this.a, this.b);
    }

    protected void a(int i, int j, byte[] abyte, double d0, double d1, double d2, float f, float f1, float f2, int k, int l, double d3) {
        OVERWORLD_CAVE_CARVING_BEHAVIOUR.generateCaveNode(i, j, abyte, d0, d1, d2, f, f1, f2, k, l, d3, this.a, this.b);
    }

    protected void a(World world, int i, int j, int k, int l, byte[] abyte) {
        OVERWORLD_CAVE_CARVING_BEHAVIOUR.generateChunkCaves(world, i, j, k, l, abyte, this.a, this.b);
    }
}
