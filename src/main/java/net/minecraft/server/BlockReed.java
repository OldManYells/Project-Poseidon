package net.minecraft.server;

import com.legacyminecraft.poseidon.block.ColumnPlantGrowthBehaviour;
import com.legacyminecraft.poseidon.block.ReedStateBehaviour;

import java.util.Random;

public class BlockReed extends Block {
    private final ColumnPlantGrowthBehaviour columnPlantGrowthService = ColumnPlantGrowthBehaviour.getInstance();
    private final ReedStateBehaviour reedStateService = ReedStateBehaviour.getInstance();

    protected BlockReed(int i, int j) {
        super(i, Material.PLANT);
        this.textureId = j;
        float f = 0.375F;

        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
        this.a(true);
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (world.isEmpty(i, j + 1, k)) {
            int l = columnPlantGrowthService.countContiguousBelow(this.blockIdQuery(world), i, j, k, this.id);
            if (columnPlantGrowthService.shouldAttemptGrowth(true, l, 3)) {
                int i1 = world.getData(i, j, k);

                if (columnPlantGrowthService.shouldSpawnNewSegment(i1, 15)) {
                    world.setTypeId(i, j + 1, k, this.id);
                    world.setData(i, j, k, columnPlantGrowthService.nextGrowthData(i1, 15));
                } else {
                    world.setData(i, j, k, columnPlantGrowthService.nextGrowthData(i1, 15));
                }
            }
        }
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return reedStateService.canRemainPlaced(
                world.getTypeId(i, j - 1, k),
                this.id,
                Block.GRASS.id,
                Block.DIRT.id,
                world.getMaterial(i - 1, j - 1, k),
                world.getMaterial(i + 1, j - 1, k),
                world.getMaterial(i, j - 1, k - 1),
                world.getMaterial(i, j - 1, k + 1),
                Material.WATER
        );
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        this.g(world, i, j, k);
    }

    protected final void g(World world, int i, int j, int k) {
        if (!this.f(world, i, j, k)) {
            this.g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
        }
    }

    public boolean f(World world, int i, int j, int k) {
        return this.canPlace(world, i, j, k);
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return null;
    }

    public int a(int i, Random random) {
        return reedStateService.dropItemId(Item.SUGAR_CANE.id);
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }

    private ColumnPlantGrowthBehaviour.BlockIdQuery blockIdQuery(final World world) {
        return new ColumnPlantGrowthBehaviour.BlockIdQuery() {
            public int getTypeId(int x, int y, int z) {
                return world.getTypeId(x, y, z);
            }
        };
    }
}
