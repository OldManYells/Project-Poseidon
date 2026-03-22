package net.minecraft.server;

import com.legacyminecraft.poseidon.block.SlabStateBehaviour;

import java.util.Random;

public class BlockStep extends Block {

    public static final String[] a = new String[] { "stone", "sand", "wood", "cobble"};
    private boolean b;
    private final SlabStateBehaviour slabStateService = SlabStateBehaviour.getInstance();

    public BlockStep(int i, boolean flag) {
        super(i, 6, Material.STONE);
        this.b = flag;
        if (!flag) {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }

        this.f(255);
    }

    public int a(int i, int j) {
        return slabStateService.resolveTextureBySideAndVariant(i, j);
    }

    public int a(int i) {
        return this.a(i, 0);
    }

    public boolean a() {
        return this.b;
    }

    public void c(World world, int i, int j, int k) {
        if (this != Block.STEP) {
            super.c(world, i, j, k);
        }

        int l = world.getTypeId(i, j - 1, k);
        int i1 = world.getData(i, j, k);
        int j1 = world.getData(i, j - 1, k);

        if (slabStateService.shouldMergeWithBelow(l, i1, j1, STEP.id)) {
            world.setTypeId(i, j, k, 0);
            world.setTypeIdAndData(i, j - 1, k, Block.DOUBLE_STEP.id, i1);
        }
    }

    public int a(int i, Random random) {
        return slabStateService.resolveDroppedItemId(Block.STEP.id);
    }

    public int a(Random random) {
        return slabStateService.resolveDropCount(this.b);
    }

    protected int a_(int i) {
        return i;
    }

    public boolean b() {
        return this.b;
    }
}
