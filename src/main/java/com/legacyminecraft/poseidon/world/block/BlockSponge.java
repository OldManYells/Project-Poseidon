package com.legacyminecraft.poseidon.world.block;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.world.block.material.Material;
import com.legacyminecraft.poseidon.world.core.World;

public class BlockSponge extends Block {
    public BlockSponge(int i) {
        super(i, Material.SPONGE);
        this.textureId = 48;
    }

    public void remove(World world, int i, int j, int k) {
        byte radius = 2;

        if (PoseidonConfig.getInstance().getConfigBoolean("fix.optimize-sponges.enabled", true)) {
            this.optimizedRemove(world, i, j, k, radius);
            return;
        }

        for (int x = i - radius; x <= i + radius; ++x) {
            for (int y = j - radius; y <= j + radius; ++y) {
                for (int z = k - radius; z <= k + radius; ++z) {
                    world.applyPhysics(x, y, z, world.getTypeId(x, y, z));
                }
            }
        }
    }

    private void optimizedRemove(World world, int i, int j, int k, byte radius) {
        for (int x = i - radius; x <= i + radius; ++x) {
            for (int y = j - radius; y <= j + radius; ++y) {
                if (y > 127 || y < 0) continue;

                for (int z = k - radius; z <= k + radius; ++z) {
                    int type = world.getTypeId(x, y, z);
                    if ((type != Block.WATER.id && type != Block.STATIONARY_WATER.id)) continue;

                    world.applyPhysics(x, y, z, type);
                }
            }
        }
    }
}
