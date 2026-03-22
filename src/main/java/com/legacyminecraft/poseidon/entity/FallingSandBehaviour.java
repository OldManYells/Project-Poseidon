package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.BlockSand;
import net.minecraft.server.EntityFallingSand;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;

public final class FallingSandBehaviour {
    private static final FallingSandBehaviour INSTANCE = new FallingSandBehaviour();

    private FallingSandBehaviour() {
    }

    public static FallingSandBehaviour getInstance() {
        return INSTANCE;
    }

    public void initializeSpawn(EntityFallingSand fallingSand, double spawnX, double spawnY, double spawnZ, int blockId) {
        fallingSand.a = blockId;
        fallingSand.aI = true;
        fallingSand.poseidonInitializeBounds();
        fallingSand.setPosition(spawnX, spawnY, spawnZ);
        fallingSand.motX = 0.0D;
        fallingSand.motY = 0.0D;
        fallingSand.motZ = 0.0D;
        fallingSand.lastX = spawnX;
        fallingSand.lastY = spawnY;
        fallingSand.lastZ = spawnZ;
    }

    public boolean shouldDieForMissingBlock(int blockId) {
        return blockId == 0;
    }

    public void tickPreMove(EntityFallingSand fallingSand) {
        fallingSand.lastX = fallingSand.locX;
        fallingSand.lastY = fallingSand.locY;
        fallingSand.lastZ = fallingSand.locZ;
        ++fallingSand.b;
        fallingSand.motY -= 0.03999999910593033D;
    }

    public void tickPostMove(EntityFallingSand fallingSand) {
        fallingSand.motX *= 0.9800000190734863D;
        fallingSand.motY *= 0.9800000190734863D;
        fallingSand.motZ *= 0.9800000190734863D;
    }

    public BlockPos resolveBlockPos(double locX, double locY, double locZ) {
        return new BlockPos(MathHelper.floor(locX), MathHelper.floor(locY), MathHelper.floor(locZ));
    }

    public void clearSourceBlockIfMatching(EntityFallingSand fallingSand, int blockX, int blockY, int blockZ) {
        if (fallingSand.world.getTypeId(blockX, blockY, blockZ) == fallingSand.a) {
            fallingSand.world.setTypeId(blockX, blockY, blockZ, 0);
        }
    }

    public GroundImpactResult handleGroundImpact(EntityFallingSand fallingSand, int blockX, int blockY, int blockZ) {
        fallingSand.motX *= 0.699999988079071D;
        fallingSand.motZ *= 0.699999988079071D;
        fallingSand.motY *= -0.5D;

        boolean canPlace = fallingSand.world.a(fallingSand.a, blockX, blockY, blockZ, true, 1);
        boolean unsupportedBelow = BlockSand.c_(fallingSand.world, blockX, blockY - 1, blockZ);
        boolean placed = canPlace && !unsupportedBelow && fallingSand.world.setTypeId(blockX, blockY, blockZ, fallingSand.a);
        boolean shouldDropItem = !placed && !fallingSand.world.isStatic;
        return new GroundImpactResult(shouldDropItem);
    }

    public boolean shouldDropForTimeout(int ageTicks, boolean worldStatic) {
        return ageTicks > 100 && !worldStatic;
    }

    public void writeTileNbt(NBTTagCompound nbt, int tileId) {
        nbt.a("Tile", (byte) tileId);
    }

    public int readTileNbt(NBTTagCompound nbt) {
        return nbt.c("Tile") & 255;
    }

    public static final class BlockPos {
        public final int x;
        public final int y;
        public final int z;

        public BlockPos(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static final class GroundImpactResult {
        public final boolean shouldDropItem;

        public GroundImpactResult(boolean shouldDropItem) {
            this.shouldDropItem = shouldDropItem;
        }
    }
}
