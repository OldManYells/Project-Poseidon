package com.legacyminecraft.poseidon.block;

/**
 * Canonical placement/power/delay policy for legacy diode wrappers.
 */
public final class DiodeStateBehaviour {
    private static final DiodeStateBehaviour INSTANCE = new DiodeStateBehaviour();

    private DiodeStateBehaviour() {
    }

    public static DiodeStateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canPlaceOnSupport(boolean hasSupportBelow, boolean superCanPlace) {
        return hasSupportBelow && superCanPlace;
    }

    public boolean canRemainOnSupport(boolean hasSupportBelow, boolean superCanRemain) {
        return hasSupportBelow && superCanRemain;
    }

    public boolean shouldTurnOffOnTick(boolean isLit, boolean inputPowered) {
        return isLit && !inputPowered;
    }

    public boolean shouldTurnOnOnTick(boolean isLit) {
        return !isLit;
    }

    public boolean shouldScheduleRecheckAfterTurnOn(boolean inputPowered) {
        return !inputPowered;
    }

    public int resolveTickDelayFromData(int data, int[] delayTable) {
        int delayIndex = (data & 12) >> 2;
        return delayTable[delayIndex] * 2;
    }

    public int resolveTextureBySideAndLit(int side, boolean isLit) {
        if (side == 0) {
            return isLit ? 99 : 115;
        }
        if (side == 1) {
            return isLit ? 147 : 131;
        }
        return 5;
    }

    public boolean isPoweringSide(boolean isLit, int orientation, int side) {
        if (!isLit) {
            return false;
        }
        return orientation == 0 && side == 3
                || orientation == 1 && side == 4
                || orientation == 2 && side == 2
                || orientation == 3 && side == 5;
    }

    public boolean shouldDropForInvalidSupport(boolean canRemainPlaced) {
        return !canRemainPlaced;
    }

    public boolean shouldScheduleStateCheck(boolean isLit, boolean inputPowered) {
        return isLit && !inputPowered || !isLit && inputPowered;
    }

    public boolean isInputPowered(InputPowerQuery query, int x, int y, int z, int data, int redstoneWireBlockId) {
        int orientation = data & 3;

        switch (orientation) {
            case 0:
                return isPoweredFrom(query, x, y, z + 1, 3, redstoneWireBlockId);
            case 1:
                return isPoweredFrom(query, x - 1, y, z, 4, redstoneWireBlockId);
            case 2:
                return isPoweredFrom(query, x, y, z - 1, 2, redstoneWireBlockId);
            case 3:
                return isPoweredFrom(query, x + 1, y, z, 5, redstoneWireBlockId);
            default:
                return false;
        }
    }

    private boolean isPoweredFrom(InputPowerQuery query, int x, int y, int z, int face, int redstoneWireBlockId) {
        return query.isBlockFaceIndirectlyPowered(x, y, z, face)
                || query.getTypeId(x, y, z) == redstoneWireBlockId && query.getData(x, y, z) > 0;
    }

    public int cycleDelayBits(int data) {
        int delayBits = (data & 12) >> 2;
        delayBits = delayBits + 1 << 2 & 12;
        return delayBits | data & 3;
    }

    public int resolvePlacementDataFromYaw(float yaw) {
        return (((int) Math.floor((double) (yaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
    }

    public boolean shouldScheduleImmediateTickOnPlace(boolean inputPowered) {
        return inputPowered;
    }

    public void applyNeighborPhysics(NeighborPhysicsApplier applier, int x, int y, int z, int blockId) {
        applier.applyPhysics(x + 1, y, z, blockId);
        applier.applyPhysics(x - 1, y, z, blockId);
        applier.applyPhysics(x, y, z + 1, blockId);
        applier.applyPhysics(x, y, z - 1, blockId);
        applier.applyPhysics(x, y - 1, z, blockId);
        applier.applyPhysics(x, y + 1, z, blockId);
    }

    public int resolveDropItemId(int diodeItemId) {
        return diodeItemId;
    }

    public interface InputPowerQuery {
        boolean isBlockFaceIndirectlyPowered(int x, int y, int z, int face);

        int getTypeId(int x, int y, int z);

        int getData(int x, int y, int z);
    }

    public interface NeighborPhysicsApplier {
        void applyPhysics(int x, int y, int z, int blockId);
    }
}
