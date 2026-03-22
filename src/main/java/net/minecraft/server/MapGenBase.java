package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.MapGenerationSeedingBehaviour;

import java.util.Random;

public class MapGenBase {
    private static final MapGenerationSeedingBehaviour MAP_GENERATION_SEEDING_BEHAVIOUR = MapGenerationSeedingBehaviour.getInstance();

    protected int a = 8;
    protected Random b = new Random();

    public MapGenBase() {}

    public void a(IChunkProvider ichunkprovider, World world, int i, int j, byte[] abyte) {
        MAP_GENERATION_SEEDING_BEHAVIOUR.run(this.a, this.b, world, i, j, abyte, new MapGenerationSeedingBehaviour.GenerationCallback() {
            public void generate(World callbackWorld, int chunkX, int chunkZ, int originX, int originZ, byte[] blockData) {
                MapGenBase.this.a(callbackWorld, chunkX, chunkZ, originX, originZ, blockData);
            }
        });
    }

    protected void a(World world, int i, int j, int k, int l, byte[] abyte) {}
}
