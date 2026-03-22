package com.legacyminecraft.poseidon.block;

import net.minecraft.server.AxisAlignedBB;

import java.util.List;

/**
 * Canonical minecart-detection and redstone-power policy for legacy detector-rail wrappers.
 */
public final class DetectorRailStateBehaviour {
    private static final DetectorRailStateBehaviour INSTANCE = new DetectorRailStateBehaviour();

    private DetectorRailStateBehaviour() {
    }

    public static DetectorRailStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int updateDelayTicks() {
        return 20;
    }

    public boolean isPowered(int data) {
        return (data & 8) != 0;
    }

    public int setPowered(int data) {
        return data | 8;
    }

    public int clearPowered(int data) {
        return data & 7;
    }

    public boolean shouldEvaluateOnEntityCollision(boolean worldIsStatic, int data) {
        return !worldIsStatic && !isPowered(data);
    }

    public boolean shouldEvaluateOnTick(boolean worldIsStatic, int data) {
        return !worldIsStatic && isPowered(data);
    }

    public boolean isTopSidePowered(int data, int side) {
        return isPowered(data) && side == 1;
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

    public boolean hasMinecart(List entities) {
        return entities.size() > 0;
    }

    public boolean shouldFireRedstoneTransitionEvent(boolean previousPowered, boolean detectedNow) {
        return previousPowered != detectedNow;
    }

    public int toRedstoneCurrent(boolean powered) {
        return powered ? 1 : 0;
    }

    public boolean shouldPowerOn(boolean previousPowered, boolean detectedNow) {
        return detectedNow && !previousPowered;
    }

    public boolean shouldPowerOff(boolean previousPowered, boolean detectedNow) {
        return !detectedNow && previousPowered;
    }

    public boolean shouldScheduleRecheck(boolean detectedNow) {
        return detectedNow;
    }
}
