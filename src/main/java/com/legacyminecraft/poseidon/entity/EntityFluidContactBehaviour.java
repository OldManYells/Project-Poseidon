package com.legacyminecraft.poseidon.entity;


/**
 * Canonical behaviour for entity fluid-contact bounding-box probes.
 */
public final class EntityFluidContactBehaviour {
    private static final EntityFluidContactBehaviour INSTANCE = new EntityFluidContactBehaviour();

    private EntityFluidContactBehaviour() {
    }

    public static EntityFluidContactBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isInWater(World world, AxisAlignedBB boundingBox, Entity entity) {
        AxisAlignedBB probeBox = boundingBox.b(0.0D, -0.4000000059604645D, 0.0D).shrink(0.0010D, 0.0010D, 0.0010D);
        return world.a(probeBox, Material.WATER, entity);
    }

    public boolean isInLava(World world, AxisAlignedBB boundingBox) {
        AxisAlignedBB probeBox = boundingBox.b(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D);
        return world.a(probeBox, Material.LAVA);
    }

    public boolean isWet(World world, double locX, double locY, double locZ, boolean isInWater) {
        return isInWater || world.s(MathHelper.floor(locX), MathHelper.floor(locY), MathHelper.floor(locZ));
    }

    public boolean isSubmergedInMaterial(
            World world,
            double locX,
            double locY,
            double locZ,
            float eyeOffset,
            Material material
    ) {
        double probeY = locY + (double) eyeOffset;
        int blockX = MathHelper.floor(locX);
        int blockY = MathHelper.d((float) MathHelper.floor(probeY));
        int blockZ = MathHelper.floor(locZ);
        int blockTypeId = world.getTypeId(blockX, blockY, blockZ);

        if (blockTypeId == 0 || Block.byId[blockTypeId].material != material) {
            return false;
        }

        float fluidHeight = BlockFluids.c(world.getData(blockX, blockY, blockZ)) - 0.11111111F;
        float fluidSurfaceY = (float) (blockY + 1) - fluidHeight;
        return probeY < (double) fluidSurfaceY;
    }
}
