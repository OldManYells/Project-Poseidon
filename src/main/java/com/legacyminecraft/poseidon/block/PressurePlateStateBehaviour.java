package com.legacyminecraft.poseidon.block;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EnumMobType;
import net.minecraft.server.World;

import java.util.List;

/**
 * Canonical pressure-plate state, detection, and shape policy service.
 */
public final class PressurePlateStateBehaviour {
    private static final PressurePlateStateBehaviour INSTANCE = new PressurePlateStateBehaviour();

    private PressurePlateStateBehaviour() {
    }

    public static PressurePlateStateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean hasSupportBelow(SupportQuery supportQuery, int x, int y, int z) {
        return supportQuery.isBlockSolid(x, y - 1, z);
    }

    public boolean shouldDropWithoutSupport(SupportQuery supportQuery, int x, int y, int z) {
        return !hasSupportBelow(supportQuery, x, y, z);
    }

    public boolean shouldReevaluateFromScheduledTick(int blockData) {
        return blockData != 0;
    }

    public boolean shouldReevaluateFromEntityTouch(int blockData) {
        return blockData != 1;
    }

    public AxisAlignedBB createDetectionBox(int x, int y, int z, float inset) {
        return AxisAlignedBB.b(
                (double) ((float) x + inset),
                (double) y,
                (double) ((float) z + inset),
                (double) ((float) (x + 1) - inset),
                (double) y + 0.25D,
                (double) ((float) (z + 1) - inset)
        );
    }

    public List collectEntities(World world, EnumMobType triggerType, AxisAlignedBB detectionBox) {
        if (triggerType == EnumMobType.EVERYTHING) {
            return world.b((Entity) null, detectionBox);
        }

        if (triggerType == EnumMobType.MOBS) {
            return world.a(EntityLiving.class, detectionBox);
        }

        if (triggerType == EnumMobType.PLAYERS) {
            return world.a(EntityHuman.class, detectionBox);
        }

        return java.util.Collections.EMPTY_LIST;
    }

    public boolean hasTriggeringEntities(List entities) {
        return entities != null && entities.size() > 0;
    }

    public int toLegacyData(boolean powered) {
        return powered ? 1 : 0;
    }

    public boolean isPowered(int blockData) {
        return blockData == 1;
    }

    public boolean isPoweringSide(int blockData, int side) {
        return blockData != 0 && side == 1;
    }

    public Bounds resolveVisualBounds(int blockData) {
        float min = 0.0625F;
        return isPowered(blockData)
                ? new Bounds(min, 0.0F, min, 1.0F - min, 0.03125F, 1.0F - min)
                : new Bounds(min, 0.0F, min, 1.0F - min, 0.0625F, 1.0F - min);
    }

    public interface SupportQuery {
        boolean isBlockSolid(int x, int y, int z);
    }

    public static final class Bounds {
        private final float minX;
        private final float minY;
        private final float minZ;
        private final float maxX;
        private final float maxY;
        private final float maxZ;

        public Bounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
        }

        public float getMinX() {
            return minX;
        }

        public float getMinY() {
            return minY;
        }

        public float getMinZ() {
            return minZ;
        }

        public float getMaxX() {
            return maxX;
        }

        public float getMaxY() {
            return maxY;
        }

        public float getMaxZ() {
            return maxZ;
        }
    }
}
