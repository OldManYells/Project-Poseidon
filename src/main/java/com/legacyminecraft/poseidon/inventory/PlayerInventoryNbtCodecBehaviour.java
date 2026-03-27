package com.legacyminecraft.poseidon.inventory;

import com.legacyminecraft.poseidon.network.NetworkCompatGatewayRegistry;

import java.lang.reflect.Method;

/**
 * Canonical NBT serialization and deserialization for player inventory arrays.
 */
public final class PlayerInventoryNbtCodecBehaviour {
    private static final PlayerInventoryNbtCodecBehaviour INSTANCE = new PlayerInventoryNbtCodecBehaviour();
    private static final int ARMOR_SLOT_OFFSET = 100;

    private PlayerInventoryNbtCodecBehaviour() {
    }

    public static PlayerInventoryNbtCodecBehaviour getInstance() {
        return INSTANCE;
    }

    public Object writeInventory(Object nbtTagList, Object[] items, Object[] armor) {
        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null) {
                Object entry = newNbtCompound();
                invoke(entry, "a", "Slot", (byte) i);
                invoke(items[i], "a", entry);
                invoke(nbtTagList, "a", entry);
            }
        }

        for (int i = 0; i < armor.length; ++i) {
            if (armor[i] != null) {
                Object entry = newNbtCompound();
                invoke(entry, "a", "Slot", (byte) (i + ARMOR_SLOT_OFFSET));
                invoke(armor[i], "a", entry);
                invoke(nbtTagList, "a", entry);
            }
        }
        return nbtTagList;
    }

    public InventoryState readInventory(Object nbtTagList, int itemSlots, int armorSlots) {
        Object[] items = NetworkCompatGatewayRegistry.gateway().createItemStackArray(itemSlots);
        Object[] armor = NetworkCompatGatewayRegistry.gateway().createItemStackArray(armorSlots);

        int size = ((Number) invoke(nbtTagList, "c")).intValue();
        for (int i = 0; i < size; ++i) {
            Object entry = invoke(nbtTagList, "a", i);
            int slot = ((Number) invoke(entry, "c", "Slot")).intValue() & 255;
            Object itemStack = newItemStackFromNbt(entry);

            if (invoke(itemStack, "getItem") != null) {
                if (slot >= 0 && slot < items.length) {
                    items[slot] = itemStack;
                }
                if (slot >= ARMOR_SLOT_OFFSET && slot < armor.length + ARMOR_SLOT_OFFSET) {
                    armor[slot - ARMOR_SLOT_OFFSET] = itemStack;
                }
            }
        }
        return new InventoryState(items, armor);
    }

    public static final class InventoryState {
        private final Object[] items;
        private final Object[] armor;

        public InventoryState(Object[] items, Object[] armor) {
            this.items = items;
            this.armor = armor;
        }

        public Object[] getItems() {
            return items;
        }

        public Object[] getArmor() {
            return armor;
        }
    }

    private static Object newNbtCompound() {
        return NetworkCompatGatewayRegistry.gateway().createNbtTagCompound();
    }

    private static Object newItemStackFromNbt(Object nbtCompound) {
        return NetworkCompatGatewayRegistry.gateway().createItemStackFromNbt(nbtCompound);
    }

    private static Object invoke(Object target, String methodName, Object... args) {
        try {
            Method matched = null;
            for (Method method : target.getClass().getMethods()) {
                if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                    matched = method;
                    break;
                }
            }
            if (matched == null) {
                for (Method method : target.getClass().getDeclaredMethods()) {
                    if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                        matched = method;
                        break;
                    }
                }
            }
            if (matched == null) {
                throw new IllegalStateException("Method not found: " + methodName);
            }
            matched.setAccessible(true);
            return matched.invoke(target, args);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to invoke method: " + methodName, exception);
        }
    }
}
