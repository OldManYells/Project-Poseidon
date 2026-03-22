package net.minecraft.server;

import com.legacyminecraft.poseidon.block.CakeStateBehaviour;

import java.util.Random;

public class BlockCake extends Block {
    private final CakeStateBehaviour cakeStateService = CakeStateBehaviour.getInstance();

    protected BlockCake(int i, int j) {
        super(i, j, Material.CAKE);
        this.a(true);
    }

    public void a(IBlockAccess iblockaccess, int i, int j, int k) {
        CakeStateBehaviour.Bounds bounds = cakeStateService.resolveSelectionBounds(iblockaccess.getData(i, j, k));
        this.a(
                bounds.getMinX(),
                bounds.getMinY(),
                bounds.getMinZ(),
                bounds.getMaxX(),
                bounds.getMaxY(),
                bounds.getMaxZ()
        );
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return cakeStateService.resolveCollisionBox(i, j, k, world.getData(i, j, k));
    }

    public int a(int i, int j) {
        return cakeStateService.resolveTextureBySideAndBites(i, j, this.textureId);
    }

    public int a(int i) {
        return cakeStateService.resolveTextureBySide(i, this.textureId);
    }

    public boolean b() {
        return false;
    }

    public boolean a() {
        return false;
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        this.c(world, i, j, k, entityhuman);
        return true;
    }

    public void b(World world, int i, int j, int k, EntityHuman entityhuman) {
        this.c(world, i, j, k, entityhuman);
    }

    private void c(World world, int i, int j, int k, EntityHuman entityhuman) {
        if (cakeStateService.canEatAtHealth(entityhuman.health)) {
            entityhuman.b(cakeStateService.healAmountPerBite());
            int l = cakeStateService.incrementBites(world.getData(i, j, k));

            if (cakeStateService.isConsumed(l)) {
                world.setTypeId(i, j, k, 0);
            } else {
                world.setData(i, j, k, l);
                world.i(i, j, k);
            }
        }
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return cakeStateService.canRemainPlaced(super.canPlace(world, i, j, k), this.f(world, i, j, k));
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (!this.f(world, i, j, k)) {
            this.g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
        }
    }

    public boolean f(World world, int i, int j, int k) {
        return cakeStateService.hasBuildableSupportBelow(world.getMaterial(i, j - 1, k).isBuildable());
    }

    public int a(Random random) {
        return 0;
    }

    public int a(int i, Random random) {
        return 0;
    }
}
