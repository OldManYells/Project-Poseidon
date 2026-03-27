package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local fluid-block helper scaffold.
 */
public final class BlockFluids {
    private BlockFluids() {
    }

    public static float c(int fluidData) {
        return (fluidData + 1) / 9.0F;
    }
}
