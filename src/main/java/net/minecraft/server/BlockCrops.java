package net.minecraft.server;

import com.legacyminecraft.poseidon.block.CropGrowthBehaviour;

import java.util.Random;

public class BlockCrops extends BlockFlower {
    private final CropGrowthBehaviour cropGrowthService = CropGrowthBehaviour.getInstance();

    protected BlockCrops(int i, int j) {
        super(i, j);
        this.textureId = j;
        this.a(true);
        float f = 0.5F;

        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
    }

    protected boolean c(int i) {
        return cropGrowthService.canPlaceOn(i, Block.SOIL.id);
    }

    public void a(World world, int i, int j, int k, Random random) {
        super.a(world, i, j, k, random);
        int l = world.getData(i, j, k);
        if (cropGrowthService.canAttemptGrowth(world.getLightLevel(i, j + 1, k), l)) {
            float f = this.h(world, i, j, k);
            if (cropGrowthService.shouldIncreaseGrowthStage(random, f)) {
                world.setData(i, j, k, cropGrowthService.resolveNextGrowthStage(l));
            }
        }
    }

    public void d_(World world, int i, int j, int k) {
        world.setData(i, j, k, cropGrowthService.matureGrowthStage());
    }

    private float h(World world, int i, int j, int k) {
        return cropGrowthService.computeGrowthFactor(new CropGrowthBehaviour.GrowthQuery() {
            public int getTypeId(int x, int y, int z) {
                return world.getTypeId(x, y, z);
            }

            public int getData(int x, int y, int z) {
                return world.getData(x, y, z);
            }
        }, i, j, k, this.id, Block.SOIL.id);
    }

    public int a(int i, int j) {
        return cropGrowthService.resolveTextureByGrowthStage(this.textureId, j);
    }

    public void dropNaturally(World world, int i, int j, int k, int l, float f) {
        super.dropNaturally(world, i, j, k, l, f);
        if (!world.isStatic) {
            for (int i1 = 0; i1 < cropGrowthService.seedDropAttempts(); ++i1) {
                if (cropGrowthService.shouldDropSeed(world.random, l)) {
                    float f1 = 0.7F;
                    float f2 = (float) cropGrowthService.resolveDropOffset(world.random, f1);
                    float f3 = (float) cropGrowthService.resolveDropOffset(world.random, f1);
                    float f4 = (float) cropGrowthService.resolveDropOffset(world.random, f1);
                    EntityItem entityitem = new EntityItem(world, (double) ((float) i + f2), (double) ((float) j + f3), (double) ((float) k + f4), new ItemStack(Item.SEEDS));

                    entityitem.pickupDelay = 10;
                    world.addEntity(entityitem);
                }
            }
        }
    }

    public int a(int i, Random random) {
        return cropGrowthService.resolveDropItemIdByGrowthStage(i, cropGrowthService.matureGrowthStage(), Item.WHEAT.id, -1);
    }

    public int a(Random random) {
        return cropGrowthService.resolveDropCount();
    }
}
