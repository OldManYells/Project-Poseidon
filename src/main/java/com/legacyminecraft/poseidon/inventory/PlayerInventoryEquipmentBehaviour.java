package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.ItemStack;

/**
 * Canonical armor and equipment lifecycle operations for legacy InventoryPlayer wrappers.
 */
public final class PlayerInventoryEquipmentBehaviour {
    private static final PlayerInventoryEquipmentBehaviour INSTANCE = new PlayerInventoryEquipmentBehaviour();

    private PlayerInventoryEquipmentBehaviour() {
    }

    public static PlayerInventoryEquipmentBehaviour getInstance() {
        return INSTANCE;
    }

    public int calculateArmorValue(ItemStack[] armor, ArmorStatsResolver resolver) {
        int armorPoints = 0;
        int remainingDurability = 0;
        int maxDurability = 0;

        for (int i = 0; i < armor.length; ++i) {
            ItemStack stack = armor[i];
            if (stack != null && resolver.isArmor(stack)) {
                int stackMaxDurability = resolver.getMaxDurability(stack);
                int stackDamage = resolver.getCurrentDamage(stack);
                int stackRemaining = stackMaxDurability - stackDamage;

                remainingDurability += stackRemaining;
                maxDurability += stackMaxDurability;
                armorPoints += resolver.getArmorReduction(stack);
            }
        }

        if (maxDurability == 0) {
            return 0;
        }
        return (armorPoints - 1) * remainingDurability / maxDurability + 1;
    }

    public void damageArmor(ItemStack[] armor, int amount, ArmorDamageCallbacks callbacks) {
        for (int i = 0; i < armor.length; ++i) {
            ItemStack stack = armor[i];
            if (stack != null && callbacks.isArmor(stack)) {
                callbacks.damage(stack, amount);
                if (stack.count == 0) {
                    callbacks.onBroken(stack);
                    armor[i] = null;
                }
            }
        }
    }

    public void dropAll(ItemStack[] items, ItemStack[] armor, DropSink dropSink) {
        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null) {
                dropSink.drop(items[i]);
                items[i] = null;
            }
        }

        for (int i = 0; i < armor.length; ++i) {
            if (armor[i] != null) {
                dropSink.drop(armor[i]);
                armor[i] = null;
            }
        }
    }

    public boolean contains(ItemStack[] armor, ItemStack[] items, ItemStack target) {
        for (int i = 0; i < armor.length; ++i) {
            if (armor[i] != null && armor[i].c(target)) {
                return true;
            }
        }
        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null && items[i].c(target)) {
                return true;
            }
        }
        return false;
    }

    public interface ArmorStatsResolver {
        boolean isArmor(ItemStack stack);

        int getMaxDurability(ItemStack stack);

        int getCurrentDamage(ItemStack stack);

        int getArmorReduction(ItemStack stack);
    }

    public interface ArmorDamageCallbacks {
        boolean isArmor(ItemStack stack);

        void damage(ItemStack stack, int amount);

        void onBroken(ItemStack stack);
    }

    public interface DropSink {
        void drop(ItemStack stack);
    }
}
