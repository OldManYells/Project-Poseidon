package com.legacyminecraft.poseidon.block;

/**
 * Canonical power recomputation and connection policy for legacy redstone wire wrappers.
 */
public final class RedstoneWireStateBehaviour {
    private static final RedstoneWireStateBehaviour INSTANCE = new RedstoneWireStateBehaviour();

    private RedstoneWireStateBehaviour() {
    }

    public static RedstoneWireStateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canPlace(boolean hasSolidBelow) {
        return hasSolidBelow;
    }

    public int computeTargetPower(PowerComputationQuery query, int x, int y, int z, int fromX, int fromY, int fromZ, boolean indirectlyPowered) {
        int targetPower = 0;

        if (indirectlyPowered) {
            return 15;
        }

        for (int direction = 0; direction < 4; ++direction) {
            int nx = x;
            int nz = z;
            if (direction == 0) {
                nx = x - 1;
            }
            if (direction == 1) {
                nx = x + 1;
            }
            if (direction == 2) {
                nz = z - 1;
            }
            if (direction == 3) {
                nz = z + 1;
            }

            if (nx != fromX || y != fromY || nz != fromZ) {
                targetPower = query.getPowerAt(nx, y, nz, targetPower);
            }

            if (query.isSolidTop(nx, y, nz) && !query.isSolidTop(x, y + 1, z)) {
                if (nx != fromX || y + 1 != fromY || nz != fromZ) {
                    targetPower = query.getPowerAt(nx, y + 1, nz, targetPower);
                }
            } else if (!query.isSolidTop(nx, y, nz) && (nx != fromX || y - 1 != fromY || nz != fromZ)) {
                targetPower = query.getPowerAt(nx, y - 1, nz, targetPower);
            }
        }

        if (targetPower > 0) {
            --targetPower;
        } else {
            targetPower = 0;
        }

        return targetPower;
    }

    public int decayPower(int power) {
        return power > 0 ? power - 1 : 0;
    }

    public boolean shouldPropagateNeighbor(int neighborPower, int decayedCurrentPower) {
        return neighborPower >= 0 && neighborPower != decayedCurrentPower;
    }

    public void forEachPropagationTarget(PropagationQuery query, int x, int y, int z, PositionConsumer consumer) {
        for (int direction = 0; direction < 4; ++direction) {
            int nx = x;
            int nz = z;
            int probeY = y - 1;

            if (direction == 0) {
                nx = x - 1;
            }
            if (direction == 1) {
                nx = x + 1;
            }
            if (direction == 2) {
                nz = z - 1;
            }
            if (direction == 3) {
                nz = z + 1;
            }

            if (query.isSolidTop(nx, y, nz)) {
                probeY += 2;
            }

            int directNeighbor = query.getPowerAt(nx, y, nz, -1);
            int decayed = decayPower(query.currentPower(x, y, z));
            if (shouldPropagateNeighbor(directNeighbor, decayed)) {
                consumer.accept(nx, y, nz);
            }

            int verticalNeighbor = query.getPowerAt(nx, probeY, nz, -1);
            decayed = decayPower(query.currentPower(x, y, z));
            if (shouldPropagateNeighbor(verticalNeighbor, decayed)) {
                consumer.accept(nx, probeY, nz);
            }
        }
    }

    public void forEachTransitionPhysicsPosition(int x, int y, int z, PositionConsumer consumer) {
        consumer.accept(x, y, z);
        consumer.accept(x - 1, y, z);
        consumer.accept(x + 1, y, z);
        consumer.accept(x, y - 1, z);
        consumer.accept(x, y + 1, z);
        consumer.accept(x, y, z - 1);
        consumer.accept(x, y, z + 1);
    }

    public void forEachExtendedNeighborPosition(SolidQuery query, int x, int y, int z, PositionConsumer consumer) {
        consumer.accept(x - 1, y, z);
        consumer.accept(x + 1, y, z);
        consumer.accept(x, y, z - 1);
        consumer.accept(x, y, z + 1);

        consumer.accept(x - 1, query.isSolidTop(x - 1, y, z) ? y + 1 : y - 1, z);
        consumer.accept(x + 1, query.isSolidTop(x + 1, y, z) ? y + 1 : y - 1, z);
        consumer.accept(x, query.isSolidTop(x, y, z - 1) ? y + 1 : y - 1, z - 1);
        consumer.accept(x, query.isSolidTop(x, y, z + 1) ? y + 1 : y - 1, z + 1);
    }

    public boolean isProvidingPower(PowerConnectionQuery query, boolean powerEnabled, int data, int side, int redstoneWireId, int diodeOffId, int diodeOnId, int[] diodeFacingByData) {
        if (!powerEnabled) {
            return false;
        }
        if (data == 0) {
            return false;
        }
        if (side == 1) {
            return true;
        }

        boolean west = isWireOrPowerSourceConnection(query, query.typeIdAt(query.originX(), query.originY(), query.originZ(), -1, 0), query, query.originX() - 1, query.originY(), query.originZ(), 1, redstoneWireId, diodeOffId, diodeOnId, diodeFacingByData)
                || !query.isSolidTop(query.originX() - 1, query.originY(), query.originZ()) && isWireOrPowerSourceConnection(query, query.typeIdAt(query.originX(), query.originY(), query.originZ(), -1, -1), query, query.originX() - 1, query.originY() - 1, query.originZ(), -1, redstoneWireId, diodeOffId, diodeOnId, diodeFacingByData);
        boolean east = isWireOrPowerSourceConnection(query, query.typeIdAt(query.originX(), query.originY(), query.originZ(), 1, 0), query, query.originX() + 1, query.originY(), query.originZ(), 3, redstoneWireId, diodeOffId, diodeOnId, diodeFacingByData)
                || !query.isSolidTop(query.originX() + 1, query.originY(), query.originZ()) && isWireOrPowerSourceConnection(query, query.typeIdAt(query.originX(), query.originY(), query.originZ(), 1, -1), query, query.originX() + 1, query.originY() - 1, query.originZ(), -1, redstoneWireId, diodeOffId, diodeOnId, diodeFacingByData);
        boolean north = isWireOrPowerSourceConnection(query, query.typeIdAt(query.originX(), query.originY(), query.originZ(), 0, -1), query, query.originX(), query.originY(), query.originZ() - 1, 2, redstoneWireId, diodeOffId, diodeOnId, diodeFacingByData)
                || !query.isSolidTop(query.originX(), query.originY(), query.originZ() - 1) && isWireOrPowerSourceConnection(query, query.typeIdAt(query.originX(), query.originY(), query.originZ(), 0, -2), query, query.originX(), query.originY() - 1, query.originZ() - 1, -1, redstoneWireId, diodeOffId, diodeOnId, diodeFacingByData);
        boolean south = isWireOrPowerSourceConnection(query, query.typeIdAt(query.originX(), query.originY(), query.originZ(), 0, 1), query, query.originX(), query.originY(), query.originZ() + 1, 0, redstoneWireId, diodeOffId, diodeOnId, diodeFacingByData)
                || !query.isSolidTop(query.originX(), query.originY(), query.originZ() + 1) && isWireOrPowerSourceConnection(query, query.typeIdAt(query.originX(), query.originY(), query.originZ(), 0, 0), query, query.originX(), query.originY() - 1, query.originZ() + 1, -1, redstoneWireId, diodeOffId, diodeOnId, diodeFacingByData);

        if (!query.isSolidTop(query.originX(), query.originY() + 1, query.originZ())) {
            if (query.isSolidTop(query.originX() - 1, query.originY(), query.originZ()) && isWireOrPowerSourceConnection(query, query.typeIdAt(query.originX(), query.originY(), query.originZ(), -1, 1), query, query.originX() - 1, query.originY() + 1, query.originZ(), -1, redstoneWireId, diodeOffId, diodeOnId, diodeFacingByData)) {
                west = true;
            }
            if (query.isSolidTop(query.originX() + 1, query.originY(), query.originZ()) && isWireOrPowerSourceConnection(query, query.typeIdAt(query.originX(), query.originY(), query.originZ(), 1, 1), query, query.originX() + 1, query.originY() + 1, query.originZ(), -1, redstoneWireId, diodeOffId, diodeOnId, diodeFacingByData)) {
                east = true;
            }
            if (query.isSolidTop(query.originX(), query.originY(), query.originZ() - 1) && isWireOrPowerSourceConnection(query, query.typeIdAt(query.originX(), query.originY(), query.originZ(), 0, -1), query, query.originX(), query.originY() + 1, query.originZ() - 1, -1, redstoneWireId, diodeOffId, diodeOnId, diodeFacingByData)) {
                north = true;
            }
            if (query.isSolidTop(query.originX(), query.originY(), query.originZ() + 1) && isWireOrPowerSourceConnection(query, query.typeIdAt(query.originX(), query.originY(), query.originZ(), 0, 1), query, query.originX(), query.originY() + 1, query.originZ() + 1, -1, redstoneWireId, diodeOffId, diodeOnId, diodeFacingByData)) {
                south = true;
            }
        }

        return !north && !east && !west && !south && side >= 2 && side <= 5
                || side == 2 && north && !west && !east
                || side == 3 && south && !west && !east
                || side == 4 && west && !north && !south
                || side == 5 && east && !north && !south;
    }

    public boolean isWireOrPowerSourceConnection(StaticConnectionQuery query, int x, int y, int z, int side, int redstoneWireId, int diodeOffId, int diodeOnId, int[] diodeFacingByData) {
        int typeId = query.typeIdAt(x, y, z);
        if (typeId == redstoneWireId) {
            return true;
        }
        if (typeId == 0) {
            return false;
        }
        if (query.isPowerSource(typeId)) {
            return true;
        }
        if (typeId != diodeOffId && typeId != diodeOnId) {
            return false;
        }
        int data = query.dataAt(x, y, z);
        return side == diodeFacingByData[data & 3];
    }

    private boolean isWireOrPowerSourceConnection(PowerConnectionQuery fullQuery, int ignoredTypeId, StaticConnectionQuery query, int x, int y, int z, int side, int redstoneWireId, int diodeOffId, int diodeOnId, int[] diodeFacingByData) {
        return isWireOrPowerSourceConnection(query, x, y, z, side, redstoneWireId, diodeOffId, diodeOnId, diodeFacingByData);
    }

    public interface PowerComputationQuery {
        int getPowerAt(int x, int y, int z, int currentMax);

        boolean isSolidTop(int x, int y, int z);
    }

    public interface PropagationQuery {
        int getPowerAt(int x, int y, int z, int currentMax);

        int currentPower(int x, int y, int z);

        boolean isSolidTop(int x, int y, int z);
    }

    public interface SolidQuery {
        boolean isSolidTop(int x, int y, int z);
    }

    public interface StaticConnectionQuery {
        int typeIdAt(int x, int y, int z);

        int dataAt(int x, int y, int z);

        boolean isPowerSource(int typeId);
    }

    public interface PowerConnectionQuery extends StaticConnectionQuery {
        boolean isSolidTop(int x, int y, int z);

        int originX();

        int originY();

        int originZ();

        default int typeIdAt(int originX, int originY, int originZ, int offsetX, int offsetZ) {
            return typeIdAt(originX + offsetX, originY, originZ + offsetZ);
        }
    }

    public interface PositionConsumer {
        void accept(int x, int y, int z);
    }
}
