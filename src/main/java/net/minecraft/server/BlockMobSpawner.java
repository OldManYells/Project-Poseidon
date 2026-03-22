package net.minecraft.server;

import com.legacyminecraft.poseidon.block.MobSpawnerStateBehaviour;

import java.util.Random;

public class BlockMobSpawner extends BlockContainer {
    private static final MobSpawnerStateBehaviour MOB_SPAWNER_STATE_SERVICE = MobSpawnerStateBehaviour.getInstance();

    protected BlockMobSpawner(int i, int j) {
        super(i, j, Material.STONE);
    }

    protected TileEntity a_() {
        return MOB_SPAWNER_STATE_SERVICE.createTileEntity();
    }

    public int a(int i, Random random) {
        return MOB_SPAWNER_STATE_SERVICE.resolveDropItemId();
    }

    public int a(Random random) {
        return MOB_SPAWNER_STATE_SERVICE.resolveDropCount();
    }

    public boolean a() {
        return MOB_SPAWNER_STATE_SERVICE.isOpaqueCube();
    }
}
