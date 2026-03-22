package org.bukkit.craftbukkit.inventory;

import com.legacyminecraft.poseidon.compat.bukkit.PlayerInventoryBridgeBehaviour;
import net.minecraft.server.InventoryPlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CraftInventoryPlayer extends CraftInventory implements PlayerInventory {
    private static final PlayerInventoryBridgeBehaviour PLAYER_INVENTORY_BRIDGE_BEHAVIOUR =
            PlayerInventoryBridgeBehaviour.getInstance();

    public CraftInventoryPlayer(net.minecraft.server.InventoryPlayer inventory) {
        super(inventory);
    }

    public InventoryPlayer getInventory() {
        return (InventoryPlayer) inventory;
    }

    public int getSize() {
        return PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.inventorySizeWithoutArmor(super.getSize());
    }

    public ItemStack getItemInHand() {
        return PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.getItemInHand(getInventory());
    }

    public void setItemInHand(ItemStack stack) {
        setItem(getHeldItemSlot(), stack);
    }

    public int getHeldItemSlot() {
        return PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.getHeldItemSlot(getInventory());
    }

    public ItemStack getHelmet() {
        return getItem(PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.armorSlotIndex(getSize(), 3));
    }

    public ItemStack getChestplate() {
        return getItem(PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.armorSlotIndex(getSize(), 2));
    }

    public ItemStack getLeggings() {
        return getItem(PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.armorSlotIndex(getSize(), 1));
    }

    public ItemStack getBoots() {
        return getItem(PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.armorSlotIndex(getSize(), 0));
    }

    public void setHelmet(ItemStack helmet) {
        setItem(PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.armorSlotIndex(getSize(), 3), helmet);
    }

    public void setChestplate(ItemStack chestplate) {
        setItem(PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.armorSlotIndex(getSize(), 2), chestplate);
    }

    public void setLeggings(ItemStack leggings) {
        setItem(PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.armorSlotIndex(getSize(), 1), leggings);
    }

    public void setBoots(ItemStack boots) {
        setItem(PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.armorSlotIndex(getSize(), 0), boots);
    }

    public CraftItemStack[] getArmorContents() {
        return PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.toArmorContents(getInventory().getArmorContents());
    }

    public void setArmorContents(ItemStack[] items) {
        PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.applyArmorContents(
                items,
                getSize(),
                new PlayerInventoryBridgeBehaviour.SlotMutator() {
                    public void clear(int slot) {
                        CraftInventoryPlayer.this.clear(slot);
                    }

                    public void set(int slot, ItemStack item) {
                        CraftInventoryPlayer.this.setItem(slot, item);
                    }
                }
        );
    }
}
