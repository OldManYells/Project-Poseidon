package net.minecraft.server;

import com.legacyminecraft.poseidon.block.SaplingGrowthBehaviour;
import org.bukkit.BlockChangeDelegate;

import java.util.Random;

public class BlockSapling extends BlockFlower {
    private final SaplingGrowthBehaviour saplingGrowthService = SaplingGrowthBehaviour.getInstance();

    protected BlockSapling(int i, int j) {
        super(i, j);
        float f = 0.4F;

        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (!world.isStatic) {
            super.a(world, i, j, k, random);
            if (saplingGrowthService.shouldAttemptGrowth(world.getLightLevel(i, j + 1, k), random)) {
                int l = world.getData(i, j, k);

                if (!saplingGrowthService.isMarkedForGrowth(l)) {
                    world.setData(i, j, k, saplingGrowthService.markForGrowth(l));
                } else {
                    this.b(world, i, j, k, random);
                }
            }
        }
    }

    public int a(int i, int j) {
        int variant = saplingGrowthService.extractVariant(j);
        return saplingGrowthService.resolveTextureByVariant(variant, super.a(i, variant));
    }

    public void b(World world, int i, int j, int k, Random random) {
        int l = saplingGrowthService.extractVariant(world.getData(i, j, k));

        world.setRawTypeId(i, j, k, 0);

        // CraftBukkit start - fixes client updates on recently grown trees
        boolean grownTree;
        BlockChangeWithNotify delegate = new BlockChangeWithNotify(world);

        SaplingGrowthBehaviour.TreeGeneratorType generatorType = saplingGrowthService.resolveGeneratorType(l, random);
        if (generatorType == SaplingGrowthBehaviour.TreeGeneratorType.TAIGA) {
            grownTree = new WorldGenTaiga2().generate(delegate, random, i, j, k);
        } else if (generatorType == SaplingGrowthBehaviour.TreeGeneratorType.FOREST) {
            grownTree = new WorldGenForest().generate(delegate, random, i, j, k);
        } else if (generatorType == SaplingGrowthBehaviour.TreeGeneratorType.BIG_TREE) {
            grownTree = new WorldGenBigTree().generate(delegate, random, i, j, k);
        } else {
            grownTree = new WorldGenTrees().generate(delegate, random, i, j, k);
        }

        if (!grownTree) {
            world.setRawTypeIdAndData(i, j, k, this.id, l);
        }
        // CraftBukkit end
    }

    protected int a_(int i) {
        return saplingGrowthService.extractVariant(i);
    }

    // CraftBukkit start
    private class BlockChangeWithNotify implements BlockChangeDelegate {
        World world;

        BlockChangeWithNotify(World world) { this.world = world; }

        public boolean setRawTypeId(int x, int y, int z, int type) {
            return this.world.setTypeId(x, y, z, type);
        }

        public boolean setRawTypeIdAndData(int x, int y, int z, int type, int data) {
            return this.world.setTypeIdAndData(x, y, z, type, data);
        }

        public int getTypeId(int x, int y, int z) {
            return this.world.getTypeId(x, y, z);
        }
    }
    // CraftBukkit end
}
