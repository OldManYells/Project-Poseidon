package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.World;

public final class EntityWaterAnimalBehaviour {
    private static final EntityWaterAnimalBehaviour INSTANCE = new EntityWaterAnimalBehaviour();

    private EntityWaterAnimalBehaviour() {
    }

    public static EntityWaterAnimalBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public boolean canSpawn(World world, AxisAlignedBB boundingBox) {
        return world.containsEntity(boundingBox);
    }

    public int getAmbientSoundInterval() {
        return 120;
    }
}
