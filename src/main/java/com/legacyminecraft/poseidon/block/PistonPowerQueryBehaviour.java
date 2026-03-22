package com.legacyminecraft.poseidon.block;

/**
 * Canonical piston power-query policy service.
 */
public final class PistonPowerQueryBehaviour {
    private static final PistonPowerQueryBehaviour INSTANCE = new PistonPowerQueryBehaviour();

    private PistonPowerQueryBehaviour() {
    }

    public static PistonPowerQueryBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isIndirectlyPoweredExceptFacing(IndirectPowerQuery query, int x, int y, int z, int excludedFacing) {
        if (excludedFacing != 0 && query.isBlockFaceIndirectlyPowered(x, y - 1, z, 0)) {
            return true;
        }

        if (excludedFacing != 1 && query.isBlockFaceIndirectlyPowered(x, y + 1, z, 1)) {
            return true;
        }

        if (excludedFacing != 2 && query.isBlockFaceIndirectlyPowered(x, y, z - 1, 2)) {
            return true;
        }

        if (excludedFacing != 3 && query.isBlockFaceIndirectlyPowered(x, y, z + 1, 3)) {
            return true;
        }

        if (excludedFacing != 5 && query.isBlockFaceIndirectlyPowered(x + 1, y, z, 5)) {
            return true;
        }

        if (excludedFacing != 4 && query.isBlockFaceIndirectlyPowered(x - 1, y, z, 4)) {
            return true;
        }

        return query.isBlockFaceIndirectlyPowered(x, y, z, 0)
                || query.isBlockFaceIndirectlyPowered(x, y + 2, z, 1)
                || query.isBlockFaceIndirectlyPowered(x, y + 1, z - 1, 2)
                || query.isBlockFaceIndirectlyPowered(x, y + 1, z + 1, 3)
                || query.isBlockFaceIndirectlyPowered(x - 1, y + 1, z, 4)
                || query.isBlockFaceIndirectlyPowered(x + 1, y + 1, z, 5);
    }

    public interface IndirectPowerQuery {
        boolean isBlockFaceIndirectlyPowered(int x, int y, int z, int face);
    }
}
