package net.minecraft.server;

import com.legacyminecraft.poseidon.block.DecorationPlantStateBehaviour;

import java.util.Random;

public class BlockLongGrass extends BlockFlower {
    private final DecorationPlantStateBehaviour decorationPlantStateService = DecorationPlantStateBehaviour.getInstance();

    protected BlockLongGrass(int i, int j) {
        super(i, j);
        float f = 0.4F;

        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
    }

    public int a(int i, int j) {
        return decorationPlantStateService.longGrassTextureByData(j, this.textureId);
    }

    public int a(int i, Random random) {
        return decorationPlantStateService.longGrassDropItemId(random, Item.SEEDS.id);
    }
}
