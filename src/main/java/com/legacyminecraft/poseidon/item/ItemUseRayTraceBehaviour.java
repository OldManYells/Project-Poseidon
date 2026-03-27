package com.legacyminecraft.poseidon.item;


public final class ItemUseRayTraceBehaviour {
    private static final ItemUseRayTraceBehaviour INSTANCE = new ItemUseRayTraceBehaviour();

    private ItemUseRayTraceBehaviour() {
    }

    public static ItemUseRayTraceBehaviour getInstance() {
        return INSTANCE;
    }

    public MovingObjectPosition rayTraceFromPlayer(World world, EntityHuman entityhuman, boolean includeFluids) {
        float partialTicks = 1.0F;
        float pitch = entityhuman.lastPitch + (entityhuman.pitch - entityhuman.lastPitch) * partialTicks;
        float yaw = entityhuman.lastYaw + (entityhuman.yaw - entityhuman.lastYaw) * partialTicks;
        double x = entityhuman.lastX + (entityhuman.locX - entityhuman.lastX) * (double) partialTicks;
        double y = entityhuman.lastY + (entityhuman.locY - entityhuman.lastY) * (double) partialTicks + 1.62D - (double) entityhuman.height;
        double z = entityhuman.lastZ + (entityhuman.locZ - entityhuman.lastZ) * (double) partialTicks;
        Vec3D start = Vec3D.create(x, y, z);
        float yawCos = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
        float yawSin = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
        float pitchCos = -MathHelper.cos(-pitch * 0.017453292F);
        float pitchSin = MathHelper.sin(-pitch * 0.017453292F);
        float lookX = yawSin * pitchCos;
        float lookZ = yawCos * pitchCos;
        double distance = 5.0D;
        Vec3D end = start.add((double) lookX * distance, (double) pitchSin * distance, (double) lookZ * distance);
        return world.rayTrace(start, end, includeFluids);
    }
}
