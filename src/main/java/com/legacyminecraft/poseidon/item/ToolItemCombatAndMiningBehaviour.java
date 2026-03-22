package com.legacyminecraft.poseidon.item;

import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.ItemStack;

public final class ToolItemCombatAndMiningBehaviour {
    private static final ToolItemCombatAndMiningBehaviour INSTANCE = new ToolItemCombatAndMiningBehaviour();

    private ToolItemCombatAndMiningBehaviour() {
    }

    public static ToolItemCombatAndMiningBehaviour getInstance() {
        return INSTANCE;
    }

    public float resolveDestroySpeed(Block[] effectiveBlocks, float effectiveSpeed, Block block) {
        for (int i = 0; i < effectiveBlocks.length; ++i) {
            if (effectiveBlocks[i] == block) {
                return effectiveSpeed;
            }
        }
        return 1.0F;
    }

    public boolean damageOnEntityHit(ItemStack itemstack, EntityLiving damager, int amount) {
        itemstack.damage(amount, damager);
        return true;
    }

    public boolean damageOnBlockBreak(ItemStack itemstack, EntityLiving user, int amount) {
        itemstack.damage(amount, user);
        return true;
    }

    public int resolveToolAttackDamage(int baseAttackOffset, EnumToolMaterial material) {
        return baseAttackOffset + material.c();
    }

    public int resolveAttackDamageAgainstEntity(int attackDamage, Entity entity) {
        return attackDamage;
    }
}
