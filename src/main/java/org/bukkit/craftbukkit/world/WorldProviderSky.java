package org.bukkit.craftbukkit.world;


import org.bukkit.craftbukkit.server.ChunkProviderSky;
import net.minecraft.server.CraftBlock;
import net.minecraft.server.IChunkProvider;
import org.bukkit.generator.BiomeBase;

public class WorldProviderSky extends WorldProvider {

    public WorldProviderSky() {}

    public void a() {
        this.b = new WorldChunkManagerHell(BiomeBase.SKY, 0.5D, 0.0D);
        this.dimension = 1;
    }

    public IChunkProvider getChunkProvider() {
        return new ChunkProviderSky(this.a, this.a.getSeed());
    }

    public float a(long i, float f) {
        return 0.0F;
    }

    public boolean canSpawn(int i, int j) {
        int k = this.a.a(i, j);

        return k == 0 ? false : CraftBlock.byId[k].material.isSolid();
    }
}
