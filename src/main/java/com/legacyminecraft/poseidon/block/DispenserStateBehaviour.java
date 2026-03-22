package com.legacyminecraft.poseidon.block;

import java.util.Random;

/**
 * Canonical facing, scheduling, and dispense/drop math policy for legacy dispenser wrappers.
 */
public final class DispenserStateBehaviour {
    private static final DispenserStateBehaviour INSTANCE = new DispenserStateBehaviour();

    private DispenserStateBehaviour() {
    }

    public static DispenserStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveTickRate() {
        return 4;
    }

    public int resolveDroppedBlockId(int dispenserBlockId) {
        return dispenserBlockId;
    }

    public int resolveDefaultFacing(boolean northOpaque, boolean southOpaque, boolean westOpaque, boolean eastOpaque) {
        int data = 3;

        if (northOpaque && !southOpaque) {
            data = 3;
        }
        if (southOpaque && !northOpaque) {
            data = 2;
        }
        if (westOpaque && !eastOpaque) {
            data = 5;
        }
        if (eastOpaque && !westOpaque) {
            data = 4;
        }

        return data;
    }

    public int resolveTextureBySide(int side, int textureId) {
        if (side == 1 || side == 0) {
            return textureId + 17;
        }
        if (side == 3) {
            return textureId + 1;
        }
        return textureId;
    }

    public Facing resolveDispenseFacing(int data) {
        if (data == 3) {
            return new Facing(0, 1);
        }
        if (data == 2) {
            return new Facing(0, -1);
        }
        if (data == 5) {
            return new Facing(1, 0);
        }
        return new Facing(-1, 0);
    }

    public double resolveDispenseOriginX(int x, int offsetX) {
        return (double) x + (double) offsetX * 0.6D + 0.5D;
    }

    public double resolveDispenseOriginY(int y) {
        return (double) y + 0.5D;
    }

    public double resolveDispenseOriginZ(int z, int offsetZ) {
        return (double) z + (double) offsetZ * 0.6D + 0.5D;
    }

    public double resolveDispenseBaseSpeed(Random random) {
        return random.nextDouble() * 0.1D + 0.2D;
    }

    public double resolveDispenseVelocityComponent(int direction, double baseSpeed, double gaussian) {
        return (double) direction * baseSpeed + gaussian * 0.007499999832361937D * 6.0D;
    }

    public double resolveDispenseVerticalVelocity(double baseVelocity, double gaussian) {
        return baseVelocity + gaussian * 0.007499999832361937D * 6.0D;
    }

    public int resolveDispenseEventData(int offsetX, int offsetZ) {
        return offsetX + 1 + (offsetZ + 1) * 3;
    }

    public boolean shouldScheduleDispense(boolean sourceIsPowerSource, boolean poweredSelf, boolean poweredAbove) {
        return sourceIsPowerSource && (poweredSelf || poweredAbove);
    }

    public boolean shouldDispenseNow(boolean poweredSelf, boolean poweredAbove) {
        return poweredSelf || poweredAbove;
    }

    public int resolvePostPlaceData(float yawDegrees) {
        int rotationIndex = ((int) Math.floor((double) (yawDegrees * 4.0F / 360.0F) + 0.5D)) & 3;
        if (rotationIndex == 0) {
            return 2;
        }
        if (rotationIndex == 1) {
            return 5;
        }
        if (rotationIndex == 2) {
            return 3;
        }
        return 4;
    }

    public float resolveDropOffset(Random random) {
        return random.nextFloat() * 0.8F + 0.1F;
    }

    public int resolveDropStackChunk(Random random, int remainingCount) {
        int amount = random.nextInt(21) + 10;
        return Math.min(amount, remainingCount);
    }

    public double resolveDropMotion(Random random, boolean vertical) {
        float randomScale = 0.05F;
        double motion = random.nextGaussian() * (double) randomScale;
        if (vertical) {
            motion += 0.2F;
        }
        return motion;
    }

    public static final class Facing {
        public final int offsetX;
        public final int offsetZ;

        public Facing(int offsetX, int offsetZ) {
            this.offsetX = offsetX;
            this.offsetZ = offsetZ;
        }
    }
}
