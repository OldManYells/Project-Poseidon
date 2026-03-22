package com.legacyminecraft.poseidon.block;

import net.minecraft.server.TileEntity;
import net.minecraft.server.TileEntityMobSpawner;

/**
 * Canonical tile/drop/opacity policy for legacy mob-spawner wrappers.
 */
public final class MobSpawnerStateBehaviour {
    private static final MobSpawnerStateBehaviour INSTANCE = new MobSpawnerStateBehaviour();

    private MobSpawnerStateBehaviour() {
    }

    public static MobSpawnerStateBehaviour getInstance() {
        return INSTANCE;
    }

    public TileEntity createTileEntity() {
        return new TileEntityMobSpawner();
    }

    public int resolveDropItemId() {
        return 0;
    }

    public int resolveDropCount() {
        return 0;
    }

    public boolean isOpaqueCube() {
        return false;
    }
}
