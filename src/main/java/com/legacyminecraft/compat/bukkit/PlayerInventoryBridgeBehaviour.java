package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftInventoryPlayer hand/armor bridge policy.
 */
public final class PlayerInventoryBridgeBehaviour {
    private static final PlayerInventoryBridgeBehaviour INSTANCE = new PlayerInventoryBridgeBehaviour();
    private static final ItemStackProjectionBridgeBehaviour ITEM_STACK_PROJECTION_BRIDGE_BEHAVIOUR =
            ItemStackProjectionBridgeBehaviour.getInstance();

    private PlayerInventoryBridgeBehaviour() {
    }

    public static PlayerInventoryBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public int inventorySizeWithoutArmor(int rawSize) {
        return rawSize - 4;
    }

    public <T> T getInventory(Object inventory) {
        return BridgeReflection.cast(inventory);
    }

    public <T> T getItemInHand(Object inventoryPlayer) {
        return ITEM_STACK_PROJECTION_BRIDGE_BEHAVIOUR.toCraftItemStack(BridgeReflection.invoke(inventoryPlayer, "getItemInHand"));
    }

    public void setItemInHand(Object inventoryPlayerWrapper, Object stack) {
        Object inventory = BridgeReflection.invoke(inventoryPlayerWrapper, "getInventory");
        int heldItemSlot = getHeldItemSlot(inventory);
        BridgeReflection.invoke(inventoryPlayerWrapper, "setItem", heldItemSlot, stack);
    }

    public int getHeldItemSlot(Object inventoryPlayer) {
        return ((Number) BridgeReflection.getField(inventoryPlayer, "itemInHandIndex")).intValue();
    }

    public int armorSlotIndex(int inventorySizeWithoutArmor, int armorOffsetFromBoots) {
        return inventorySizeWithoutArmor + armorOffsetFromBoots;
    }

    public Object[] toArmorContents(Object[] armorContents) {
        Object[] result = new Object[armorContents.length];
        for (int index = 0; index < armorContents.length; index++) {
            result[index] = ITEM_STACK_PROJECTION_BRIDGE_BEHAVIOUR.toCraftItemStack(armorContents[index]);
        }
        return result;
    }

    public <T> T getHelmet(Object inventoryPlayerWrapper) {
        int size = ((Number) BridgeReflection.invoke(inventoryPlayerWrapper, "getSize")).intValue();
        return BridgeReflection.cast(BridgeReflection.invoke(inventoryPlayerWrapper, "getItem", armorSlotIndex(size, 3)));
    }

    public <T> T getChestplate(Object inventoryPlayerWrapper) {
        int size = ((Number) BridgeReflection.invoke(inventoryPlayerWrapper, "getSize")).intValue();
        return BridgeReflection.cast(BridgeReflection.invoke(inventoryPlayerWrapper, "getItem", armorSlotIndex(size, 2)));
    }

    public <T> T getLeggings(Object inventoryPlayerWrapper) {
        int size = ((Number) BridgeReflection.invoke(inventoryPlayerWrapper, "getSize")).intValue();
        return BridgeReflection.cast(BridgeReflection.invoke(inventoryPlayerWrapper, "getItem", armorSlotIndex(size, 1)));
    }

    public <T> T getBoots(Object inventoryPlayerWrapper) {
        int size = ((Number) BridgeReflection.invoke(inventoryPlayerWrapper, "getSize")).intValue();
        return BridgeReflection.cast(BridgeReflection.invoke(inventoryPlayerWrapper, "getItem", armorSlotIndex(size, 0)));
    }

    public void setHelmet(Object inventoryPlayerWrapper, Object helmet) {
        int size = ((Number) BridgeReflection.invoke(inventoryPlayerWrapper, "getSize")).intValue();
        BridgeReflection.invoke(inventoryPlayerWrapper, "setItem", armorSlotIndex(size, 3), helmet);
    }

    public void setChestplate(Object inventoryPlayerWrapper, Object chestplate) {
        int size = ((Number) BridgeReflection.invoke(inventoryPlayerWrapper, "getSize")).intValue();
        BridgeReflection.invoke(inventoryPlayerWrapper, "setItem", armorSlotIndex(size, 2), chestplate);
    }

    public void setLeggings(Object inventoryPlayerWrapper, Object leggings) {
        int size = ((Number) BridgeReflection.invoke(inventoryPlayerWrapper, "getSize")).intValue();
        BridgeReflection.invoke(inventoryPlayerWrapper, "setItem", armorSlotIndex(size, 1), leggings);
    }

    public void setBoots(Object inventoryPlayerWrapper, Object boots) {
        int size = ((Number) BridgeReflection.invoke(inventoryPlayerWrapper, "getSize")).intValue();
        BridgeReflection.invoke(inventoryPlayerWrapper, "setItem", armorSlotIndex(size, 0), boots);
    }

    public void applyArmorContents(Object[] items, int startSlot, SlotMutator slotMutator) {
        Object[] input = items == null ? new Object[4] : items;
        int currentSlot = startSlot;
        for (Object item : input) {
            if (item == null || ((Number) BridgeReflection.invoke(item, "getTypeId")).intValue() == 0) {
                slotMutator.clear(currentSlot++);
            } else {
                slotMutator.set(currentSlot++, item);
            }
        }
    }

    public interface SlotMutator {
        void clear(int slot);

        void set(int slot, Object item);
    }
}
