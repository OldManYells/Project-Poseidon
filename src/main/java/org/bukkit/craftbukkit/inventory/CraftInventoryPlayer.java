package org.bukkit.craftbukkit.inventory;

import com.legacyminecraft.compat.bukkit.PlayerInventoryBridgeBehaviour;
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
        return PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.getInventory(inventory);
    }

    public int getSize() {
        return PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.inventorySizeWithoutArmor(super.getSize());
    }

    public ItemStack getItemInHand() {
        return PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.getItemInHand(getInventory());
    }

    public void setItemInHand(ItemStack stack) {
        PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.setItemInHand(this, stack);
    }

    public int getHeldItemSlot() {
        return PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.getHeldItemSlot(getInventory());
    }

    public ItemStack getHelmet() {
        return PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.getHelmet(this);
    }

    public ItemStack getChestplate() {
        return PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.getChestplate(this);
    }

    public ItemStack getLeggings() {
        return PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.getLeggings(this);
    }

    public ItemStack getBoots() {
        return PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.getBoots(this);
    }

    public void setHelmet(ItemStack helmet) {
        PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.setHelmet(this, helmet);
    }

    public void setChestplate(ItemStack chestplate) {
        PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.setChestplate(this, chestplate);
    }

    public void setLeggings(ItemStack leggings) {
        PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.setLeggings(this, leggings);
    }

    public void setBoots(ItemStack boots) {
        PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.setBoots(this, boots);
    }

    public ItemStack[] getArmorContents() {
        Object[] projected = PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.toArmorContents(getInventory().getArmorContents());
        ItemStack[] result = new ItemStack[projected.length];
        for (int i = 0; i < projected.length; i++) {
            result[i] = (ItemStack) projected[i];
        }
        return result;
    }

    public void setArmorContents(ItemStack[] items) {
        PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.applyArmorContents(
                items,
                getSize(),
                new PlayerInventoryBridgeBehaviour.SlotMutator() {
                    public void clear(int slot) {
                        CraftInventoryPlayer.this.clear(slot);
                    }

                    public void set(int slot, Object item) {
                        CraftInventoryPlayer.this.setItem(slot, (ItemStack) item);
                    }
                }
        );
    }
}
