package net.minecraft.server;

import com.legacyminecraft.poseidon.block.PistonMovingBlockBehaviour;

import java.util.Random;

public class BlockPistonMoving extends BlockContainer {
    private final PistonMovingBlockBehaviour pistonMovingBlockService = PistonMovingBlockBehaviour.getInstance();

    public BlockPistonMoving(int i) {
        super(i, Material.PISTON);
        this.c(-1.0F);
    }

    protected TileEntity a_() {
        return null;
    }

    public void c(World world, int i, int j, int k) {}

    public void remove(World world, int i, int j, int k) {
        if (!pistonMovingBlockService.handleRemove(world, i, j, k)) {
            super.remove(world, i, j, k);
        }
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return false;
    }

    public boolean canPlace(World world, int i, int j, int k, int l) {
        return false;
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        if (pistonMovingBlockService.shouldClearOrphanMovingBlock(world, i, j, k)) {
            world.setTypeId(i, j, k, 0);
            return true;
        } else {
            return false;
        }
    }

    public int a(int i, Random random) {
        return 0;
    }

    public void dropNaturally(World world, int i, int j, int k, int l, float f) {
        pistonMovingBlockService.dropMovedBlockNaturally(world, i, j, k, this.b(world, i, j, k));
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (!world.isStatic && world.getTileEntity(i, j, k) == null) {
            ;
        }
    }

    public static TileEntity a(int i, int j, int k, boolean flag, boolean flag1) {
        return (TileEntity) PistonMovingBlockBehaviour.getInstance().createMovingTileEntity(i, j, k, flag, flag1);
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        TileEntityPiston tileentitypiston = this.b(world, i, j, k);

        if (tileentitypiston == null) {
            return null;
        } else {
            return this.a(
                    world,
                    i,
                    j,
                    k,
                    tileentitypiston.a(),
                    pistonMovingBlockService.resolveRenderProgress(tileentitypiston),
                    tileentitypiston.d()
            );
        }
    }

    public void a(IBlockAccess iblockaccess, int i, int j, int k) {
        TileEntityPiston tileentitypiston = this.b(iblockaccess, i, j, k);

        if (tileentitypiston != null) {
            Block block = Block.byId[tileentitypiston.a()];

            if (block == null || block == this) {
                return;
            }

            block.a(iblockaccess, i, j, k);
            PistonMovingBlockBehaviour.Bounds bounds = pistonMovingBlockService.resolveShiftedOutlineBounds(
                    block,
                    pistonMovingBlockService.resolveRenderProgress(tileentitypiston),
                    tileentitypiston.d()
            );
            this.minX = bounds.getMinX();
            this.minY = bounds.getMinY();
            this.minZ = bounds.getMinZ();
            this.maxX = bounds.getMaxX();
            this.maxY = bounds.getMaxY();
            this.maxZ = bounds.getMaxZ();
        }
    }

    public AxisAlignedBB a(World world, int i, int j, int k, int l, float f, int i1) {
        return pistonMovingBlockService.resolveShiftedCollisionBox(world, i, j, k, l, f, i1, this.id);
    }

    private TileEntityPiston b(IBlockAccess iblockaccess, int i, int j, int k) {
        return pistonMovingBlockService.extractPistonTileEntity(iblockaccess.getTileEntity(i, j, k));
    }
}
