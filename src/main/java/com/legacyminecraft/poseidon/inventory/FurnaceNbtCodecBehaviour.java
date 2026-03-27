package com.legacyminecraft.poseidon.inventory;

import com.legacyminecraft.poseidon.network.NetworkCompatGatewayRegistry;
import com.legacyminecraft.poseidon.nbt.NBTBase;

import java.lang.reflect.Array;
import java.lang.reflect.Method;


/**
 * Canonical NBT codec behaviour for furnace inventory and timers.
 */
public final class FurnaceNbtCodecBehaviour {
    private static final FurnaceNbtCodecBehaviour INSTANCE = new FurnaceNbtCodecBehaviour();

    private FurnaceNbtCodecBehaviour() {
    }

    public static FurnaceNbtCodecBehaviour getInstance() {
        return INSTANCE;
    }

    public FurnaceNbtState readState(Object nbt, int inventorySize) {
        Object itemList = invoke(nbt, "l", "Items");
        Object items = Array.newInstance(itemStackClass(), inventorySize);

        for (int index = 0; index < ((Number) invoke(itemList, "c")).intValue(); ++index) {
            Object itemNbt = invoke(itemList, "a", Integer.valueOf(index));
            byte slotIndex = ((Number) invoke(itemNbt, "c", "Slot")).byteValue();

            if (slotIndex >= 0 && slotIndex < Array.getLength(items)) {
                Array.set(items, slotIndex, newItemStack(itemNbt));
            }
        }

        int burnTime = ((Number) invoke(nbt, "d", "BurnTime")).intValue();
        int cookTime = ((Number) invoke(nbt, "d", "CookTime")).intValue();
        return new FurnaceNbtState(items, burnTime, cookTime);
    }

    public void writeState(Object nbt, Object[] items, int burnTime, int cookTime) {
        invoke(nbt, "a", "BurnTime", Short.valueOf((short) burnTime));
        invoke(nbt, "a", "CookTime", Short.valueOf((short) cookTime));

        Object itemList = newItemList();
        for (int slotIndex = 0; slotIndex < items.length; ++slotIndex) {
            if (items[slotIndex] == null) {
                continue;
            }

            Object itemNbt = newItemNbt();
            invoke(itemNbt, "a", "Slot", Byte.valueOf((byte) slotIndex));
            invoke(items[slotIndex], "a", itemNbt);
            invoke(itemList, "a", (NBTBase) itemNbt);
        }

        invoke(nbt, "a", "Items", (NBTBase) itemList);
    }

    public static final class FurnaceNbtState {
        private final Object items;
        private final int burnTime;
        private final int cookTime;

        public FurnaceNbtState(Object items, int burnTime, int cookTime) {
            this.items = items;
            this.burnTime = burnTime;
            this.cookTime = cookTime;
        }

        public Object getItems() {
            return items;
        }

        public int getBurnTime() {
            return burnTime;
        }

        public int getCookTime() {
            return cookTime;
        }
    }

    private static Object newItemList() {
        return NetworkCompatGatewayRegistry.gateway().createNbtTagList();
    }

    private static Object newItemNbt() {
        return NetworkCompatGatewayRegistry.gateway().createNbtTagCompound();
    }

    private static Class<?> itemStackClass() {
        return NetworkCompatGatewayRegistry.gateway().createItemStackArray(0).getClass().getComponentType();
    }

    private static Object newItemStack(Object itemNbt) {
        return NetworkCompatGatewayRegistry.gateway().createItemStackFromNbt(itemNbt);
    }

    private static Object invoke(Object target, String name, Object... args) {
        if (target == null) {
            return null;
        }

        Class<?> type = target.getClass();
        while (type != null) {
            Method[] methods = type.getDeclaredMethods();
            for (Method method : methods) {
                if (!method.getName().equals(name) || method.getParameterTypes().length != args.length) {
                    continue;
                }
                try {
                    method.setAccessible(true);
                    return method.invoke(target, args);
                } catch (Exception ignored) {
                }
            }
            type = type.getSuperclass();
        }

        throw new IllegalStateException("Method not found: " + name);
    }
}
