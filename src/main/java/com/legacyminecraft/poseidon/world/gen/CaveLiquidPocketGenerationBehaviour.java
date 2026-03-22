package com.legacyminecraft.poseidon.world.gen;

import net.minecraft.server.Block;
import net.minecraft.server.World;

import java.util.Random;

public final class CaveLiquidPocketGenerationBehaviour {
    private static final CaveLiquidPocketGenerationBehaviour INSTANCE = new CaveLiquidPocketGenerationBehaviour();

    private CaveLiquidPocketGenerationBehaviour() {
    }

    public static CaveLiquidPocketGenerationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean generateInStone(World world, Random random, int i, int j, int k, int liquidBlockId) {
        if (world.getTypeId(i, j + 1, k) != Block.STONE.id) {
            return false;
        }
        if (world.getTypeId(i, j - 1, k) != Block.STONE.id) {
            return false;
        }
        if (world.getTypeId(i, j, k) != 0 && world.getTypeId(i, j, k) != Block.STONE.id) {
            return false;
        }

        int surroundingSolid = 0;
        if (world.getTypeId(i - 1, j, k) == Block.STONE.id) {
            ++surroundingSolid;
        }
        if (world.getTypeId(i + 1, j, k) == Block.STONE.id) {
            ++surroundingSolid;
        }
        if (world.getTypeId(i, j, k - 1) == Block.STONE.id) {
            ++surroundingSolid;
        }
        if (world.getTypeId(i, j, k + 1) == Block.STONE.id) {
            ++surroundingSolid;
        }

        int surroundingAir = 0;
        if (world.isEmpty(i - 1, j, k)) {
            ++surroundingAir;
        }
        if (world.isEmpty(i + 1, j, k)) {
            ++surroundingAir;
        }
        if (world.isEmpty(i, j, k - 1)) {
            ++surroundingAir;
        }
        if (world.isEmpty(i, j, k + 1)) {
            ++surroundingAir;
        }

        if (surroundingSolid == 3 && surroundingAir == 1) {
            triggerLiquidPlacement(world, random, i, j, k, liquidBlockId);
        }
        return true;
    }

    public boolean generateInNetherrack(World world, Random random, int i, int j, int k, int liquidBlockId) {
        if (world.getTypeId(i, j + 1, k) != Block.NETHERRACK.id) {
            return false;
        }
        if (world.getTypeId(i, j, k) != 0 && world.getTypeId(i, j, k) != Block.NETHERRACK.id) {
            return false;
        }

        int surroundingSolid = 0;
        if (world.getTypeId(i - 1, j, k) == Block.NETHERRACK.id) {
            ++surroundingSolid;
        }
        if (world.getTypeId(i + 1, j, k) == Block.NETHERRACK.id) {
            ++surroundingSolid;
        }
        if (world.getTypeId(i, j, k - 1) == Block.NETHERRACK.id) {
            ++surroundingSolid;
        }
        if (world.getTypeId(i, j, k + 1) == Block.NETHERRACK.id) {
            ++surroundingSolid;
        }
        if (world.getTypeId(i, j - 1, k) == Block.NETHERRACK.id) {
            ++surroundingSolid;
        }

        int surroundingAir = 0;
        if (world.isEmpty(i - 1, j, k)) {
            ++surroundingAir;
        }
        if (world.isEmpty(i + 1, j, k)) {
            ++surroundingAir;
        }
        if (world.isEmpty(i, j, k - 1)) {
            ++surroundingAir;
        }
        if (world.isEmpty(i, j, k + 1)) {
            ++surroundingAir;
        }
        if (world.isEmpty(i, j - 1, k)) {
            ++surroundingAir;
        }

        if (surroundingSolid == 4 && surroundingAir == 1) {
            triggerLiquidPlacement(world, random, i, j, k, liquidBlockId);
        }
        return true;
    }

    private void triggerLiquidPlacement(World world, Random random, int i, int j, int k, int liquidBlockId) {
        world.setTypeId(i, j, k, liquidBlockId);
        world.a = true;
        Block.byId[liquidBlockId].a(world, i, j, k, random);
        world.a = false;
    }
}
