package com.legacyminecraft.poseidon.item;

import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EnumToolMaterial;

public final class SwordItemBehaviour {
    private static final SwordItemBehaviour INSTANCE = new SwordItemBehaviour();

    private SwordItemBehaviour() {
    }

    public static SwordItemBehaviour getInstance() {
        return INSTANCE;
    }

    public float resolveDestroySpeed(Block block) {
        return block.id == Block.WEB.id ? 15.0F : 1.5F;
    }

    public int resolveAttackDamage(EnumToolMaterial material) {
        return 4 + material.c() * 2;
    }

    public int resolveAttackDamageAgainstEntity(int attackDamage, Entity entity) {
        return attackDamage;
    }

    public boolean canHarvest(Block block) {
        return block.id == Block.WEB.id;
    }
}
