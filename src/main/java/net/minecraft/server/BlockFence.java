package net.minecraft.server;

import com.legacyminecraft.poseidon.block.FenceCollisionBehaviour;

public class BlockFence extends Block {
    private final FenceCollisionBehaviour fenceCollisionService = FenceCollisionBehaviour.getInstance();
    private final boolean modernFencingBounding;

    public BlockFence(int i, int j) {
        super(i, j, Material.WOOD);
        modernFencingBounding = fenceCollisionService.useModernFenceBoundingBoxes();
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return fenceCollisionService.canPlaceOnTop(
                world.getTypeId(i, j - 1, k),
                world.getMaterial(i, j - 1, k),
                this.id,
                super.canPlace(world, i, j, k)
        );
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return fenceCollisionService.resolveCollisionBox(world, i, j, k, this.id, modernFencingBounding);
    }

    public boolean b(IBlockAccess iblockaccess, int i, int j, int k) {
        return fenceCollisionService.isConnectable(iblockaccess, i, j, k, this.id);
    }


    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }
}
