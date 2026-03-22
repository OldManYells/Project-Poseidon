package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.PoseidonConfig;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.IBlockAccess;
import net.minecraft.server.Material;

/**
 * Canonical fence placement and collision policy service.
 */
public final class FenceCollisionBehaviour {
    private static final FenceCollisionBehaviour INSTANCE = new FenceCollisionBehaviour();

    private FenceCollisionBehaviour() {
    }

    public static FenceCollisionBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean useModernFenceBoundingBoxes() {
        return (boolean) PoseidonConfig.getInstance().getConfigOption("world-settings.use-modern-fence-bounding-boxes", false);
    }

    public boolean canPlaceOnTop(int blockBelowTypeId, Material blockBelowMaterial, int fenceBlockId, boolean fallbackCanPlace) {
        return blockBelowTypeId == fenceBlockId || blockBelowMaterial.isBuildable() && fallbackCanPlace;
    }

    public AxisAlignedBB resolveCollisionBox(IBlockAccess blockAccess, int x, int y, int z, int fenceBlockId, boolean modernBounding) {
        if (!modernBounding) {
            return AxisAlignedBB.b((double) x, (double) y, (double) z, (double) (x + 1), (double) ((float) y + 1.5F), (double) (z + 1));
        }

        boolean north = this.isConnectable(blockAccess, x, y, z - 1, fenceBlockId);
        boolean south = this.isConnectable(blockAccess, x, y, z + 1, fenceBlockId);
        boolean west = this.isConnectable(blockAccess, x - 1, y, z, fenceBlockId);
        boolean east = this.isConnectable(blockAccess, x + 1, y, z, fenceBlockId);
        float minX = 0.375F;
        float maxX = 0.625F;
        float minZ = 0.375F;
        float maxZ = 0.625F;

        if (north) {
            minZ = 0.0F;
        }

        if (south) {
            maxZ = 1.0F;
        }

        if (west) {
            minX = 0.0F;
        }

        if (east) {
            maxX = 1.0F;
        }

        return AxisAlignedBB.b(
                (double) ((float) x + minX),
                (double) y,
                (double) ((float) z + minZ),
                (double) ((float) x + maxX),
                (double) ((float) y + 1.5F),
                (double) ((float) z + maxZ)
        );
    }

    public boolean isConnectable(IBlockAccess blockAccess, int x, int y, int z, int fenceBlockId) {
        int typeId = blockAccess.getTypeId(x, y, z);
        if (typeId == fenceBlockId) {
            return true;
        }

        Block block = Block.byId[typeId];
        return block != null && block.material.h() && block.b() ? block.material != Material.PUMPKIN : false;
    }
}
