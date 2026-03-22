package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.Block;
import net.minecraft.server.MathHelper;
import net.minecraft.server.World;

public final class EntityAnimalBehaviour {
    private static final EntityAnimalBehaviour INSTANCE = new EntityAnimalBehaviour();

    private EntityAnimalBehaviour() {
    }

    public static EntityAnimalBehaviour getInstance() {
        return INSTANCE;
    }

    public float resolvePathWeight(World world, int x, int y, int z, float ambientValue) {
        return world.getTypeId(x, y - 1, z) == Block.GRASS.id ? 10.0F : ambientValue - 0.5F;
    }

    public boolean canSpawn(World world, double locX, double boundingBoxMinY, double locZ, boolean superCanSpawn) {
        int blockX = MathHelper.floor(locX);
        int blockY = MathHelper.floor(boundingBoxMinY);
        int blockZ = MathHelper.floor(locZ);
        return world.getTypeId(blockX, blockY - 1, blockZ) == Block.GRASS.id && world.k(blockX, blockY, blockZ) > 8 && superCanSpawn;
    }

    public int getAmbientSoundInterval() {
        return 120;
    }
}
