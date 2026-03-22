package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.InventoryPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * Canonical behaviour for CraftInventoryPlayer hand/armor bridge policy.
 */
public final class PlayerInventoryBridgeBehaviour {
    private static final PlayerInventoryBridgeBehaviour INSTANCE = new PlayerInventoryBridgeBehaviour();

    private PlayerInventoryBridgeBehaviour() {
    }

    public static PlayerInventoryBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public int inventorySizeWithoutArmor(int rawSize) {
        return rawSize - 4;
    }

    public ItemStack getItemInHand(InventoryPlayer inventoryPlayer) {
        return new CraftItemStack(inventoryPlayer.getItemInHand());
    }

    public int getHeldItemSlot(InventoryPlayer inventoryPlayer) {
        return inventoryPlayer.itemInHandIndex;
    }

    public int armorSlotIndex(int inventorySizeWithoutArmor, int armorOffsetFromBoots) {
        return inventorySizeWithoutArmor + armorOffsetFromBoots;
    }

    public CraftItemStack[] toArmorContents(net.minecraft.server.ItemStack[] armorContents) {
        CraftItemStack[] result = new CraftItemStack[armorContents.length];
        for (int index = 0; index < armorContents.length; index++) {
            result[index] = new CraftItemStack(armorContents[index]);
        }
        return result;
    }

    public void applyArmorContents(ItemStack[] items, int startSlot, SlotMutator slotMutator) {
        ItemStack[] input = items == null ? new ItemStack[4] : items;
        int currentSlot = startSlot;
        for (ItemStack item : input) {
            if (item == null || item.getTypeId() == 0) {
                slotMutator.clear(currentSlot++);
            } else {
                slotMutator.set(currentSlot++, item);
            }
        }
    }

    public interface SlotMutator {
        void clear(int slot);

        void set(int slot, ItemStack item);
    }
}
