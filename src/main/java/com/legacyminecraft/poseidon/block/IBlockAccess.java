package com.legacyminecraft.poseidon.block;

/**
 * Minimal block-access abstraction used by fence and pressure-plate policy.
 */
public interface IBlockAccess {
    int getTypeId(int x, int y, int z);
}
