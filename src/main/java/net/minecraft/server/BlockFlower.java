package net.minecraft.server;

import com.legacyminecraft.poseidon.block.FlowerStateBehaviour;

import java.util.Random;

public class BlockFlower extends Block {
    private final FlowerStateBehaviour flowerStateService = FlowerStateBehaviour.getInstance();

    protected BlockFlower(int i, int j) {
        super(i, Material.PLANT);
        this.textureId = j;
        this.a(true);
        float f = 0.2F;

        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return flowerStateService.canPlace(super.canPlace(world, i, j, k), this.c(world.getTypeId(i, j - 1, k)));
    }

    protected boolean c(int i) {
        return flowerStateService.canPlantOn(i, Block.GRASS.id, Block.DIRT.id, Block.SOIL.id);
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        super.doPhysics(world, i, j, k, l);
        this.g(world, i, j, k);
    }

    public void a(World world, int i, int j, int k, Random random) {
        this.g(world, i, j, k);
    }

    protected final void g(World world, int i, int j, int k) {
        if (flowerStateService.shouldDropForInvalidPlacement(this.f(world, i, j, k))) {
            this.g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
        }
    }

    public boolean f(World world, int i, int j, int k) {
        return flowerStateService.canStay(
                world.k(i, j, k),
                world.isChunkLoaded(i, j, k),
                this.c(world.getTypeId(i, j - 1, k))
        );
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return null;
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }
}
