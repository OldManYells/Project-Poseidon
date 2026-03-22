package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Block;
import net.minecraft.server.EnumSkyBlock;
import net.minecraft.server.World;

/**
 * Canonical behaviour for world light-update gating and target-value policy.
 */
public final class WorldLightUpdateBehaviour {
    private static final WorldLightUpdateBehaviour INSTANCE = new WorldLightUpdateBehaviour();

    private WorldLightUpdateBehaviour() {
    }

    public static WorldLightUpdateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldProcessUpdate(boolean skyLightDisabled, EnumSkyBlock lightLayer) {
        return !skyLightDisabled || lightLayer != EnumSkyBlock.SKY;
    }

    public int resolveTargetLightValue(World world, EnumSkyBlock lightLayer, int x, int y, int z, int requestedValue) {
        int targetValue = requestedValue;

        if (lightLayer == EnumSkyBlock.SKY) {
            if (world.m(x, y, z)) {
                targetValue = 15;
            }
        } else if (lightLayer == EnumSkyBlock.BLOCK) {
            int typeId = world.getTypeId(x, y, z);
            if (Block.s[typeId] > targetValue) {
                targetValue = Block.s[typeId];
            }
        }

        return targetValue;
    }

    public boolean shouldPropagateUpdate(int currentLightValue, int targetLightValue) {
        return currentLightValue != targetLightValue;
    }
}
