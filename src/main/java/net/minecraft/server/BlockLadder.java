package net.minecraft.server;

import com.legacyminecraft.poseidon.block.LadderPlacementAndBoundsBehaviour;

import java.util.Random;

public class BlockLadder extends Block {
    private final LadderPlacementAndBoundsBehaviour ladderPlacementAndBoundsService = LadderPlacementAndBoundsBehaviour.getInstance();

    protected BlockLadder(int i, int j) {
        super(i, j, Material.ORIENTABLE);
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        LadderPlacementAndBoundsBehaviour.Bounds bounds =
                ladderPlacementAndBoundsService.resolveBounds(world.getData(i, j, k), 0.125F);
        if (bounds != null) {
            this.a(
                    bounds.getMinX(),
                    bounds.getMinY(),
                    bounds.getMinZ(),
                    bounds.getMaxX(),
                    bounds.getMaxY(),
                    bounds.getMaxZ()
            );
        }

        return super.e(world, i, j, k);
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return ladderPlacementAndBoundsService.canPlace(this.supportQuery(world), i, j, k);
    }

    public void postPlace(World world, int i, int j, int k, int l) {
        world.setData(i, j, k, ladderPlacementAndBoundsService.resolvePostPlaceData(
                this.supportQuery(world),
                world.getData(i, j, k),
                l,
                i,
                j,
                k
        ));
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        int i1 = world.getData(i, j, k);
        if (!ladderPlacementAndBoundsService.hasValidAttachment(this.supportQuery(world), i, j, k, i1)) {
            this.g(world, i, j, k, i1);
            world.setTypeId(i, j, k, 0);
        }

        super.doPhysics(world, i, j, k, l);
    }

    public int a(Random random) {
        return 1;
    }

    private LadderPlacementAndBoundsBehaviour.SupportQuery supportQuery(final World world) {
        return new LadderPlacementAndBoundsBehaviour.SupportQuery() {
            public boolean isBlockSolid(int x, int y, int z) {
                return world.e(x, y, z);
            }
        };
    }
}
