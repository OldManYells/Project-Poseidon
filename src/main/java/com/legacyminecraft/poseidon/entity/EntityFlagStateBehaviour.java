package com.legacyminecraft.poseidon.entity;

/**
 * Canonical behaviour for packed entity flag-bit state operations.
 */
public final class EntityFlagStateBehaviour {
    private static final EntityFlagStateBehaviour INSTANCE = new EntityFlagStateBehaviour();
    private static final int SNEAKING_FLAG_INDEX = 1;

    private EntityFlagStateBehaviour() {
    }

    public static EntityFlagStateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isFlagSet(byte flags, int flagIndex) {
        return (flags & 1 << flagIndex) != 0;
    }

    public byte setFlag(byte flags, int flagIndex, boolean enabled) {
        if (enabled) {
            return (byte) (flags | 1 << flagIndex);
        }

        return (byte) (flags & ~(1 << flagIndex));
    }

    public boolean isSneaking(byte flags) {
        return this.isFlagSet(flags, SNEAKING_FLAG_INDEX);
    }

    public byte withSneaking(byte flags, boolean sneaking) {
        return this.setFlag(flags, SNEAKING_FLAG_INDEX, sneaking);
    }
}
