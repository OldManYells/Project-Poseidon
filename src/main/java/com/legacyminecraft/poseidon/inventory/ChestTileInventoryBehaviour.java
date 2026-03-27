package com.legacyminecraft.poseidon.inventory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ChestTileInventoryBehaviour {
    private static final ChestTileInventoryBehaviour INSTANCE = new ChestTileInventoryBehaviour();
    private static final int DEFAULT_SIZE = 27;
    private static final int MAX_STACK = 64;

    private ChestTileInventoryBehaviour() {
    }

    public static ChestTileInventoryBehaviour getInstance() {
        return INSTANCE;
    }

    public Object[] createStorage() {
        return new Object[DEFAULT_SIZE];
    }

    public int getSize() {
        return DEFAULT_SIZE;
    }

    public Object getItem(Object[] items, int index) {
        return items[index];
    }

    public Object splitStack(Object[] items, int index, int amount) {
        if (items[index] != null) {
            Object split;
            int count = getCount(items[index]);
            if (count <= amount) {
                split = items[index];
                items[index] = null;
                return split;
            }
            split = invoke(items[index], "a", new Class<?>[]{int.class}, amount);
            if (getCount(items[index]) == 0) {
                items[index] = null;
            }
            return split;
        }
        return null;
    }

    public Object clampStackSize(Object itemstack) {
        if (itemstack != null && getCount(itemstack) > MAX_STACK) {
            setCount(itemstack, MAX_STACK);
        }
        return itemstack;
    }

    public String getName() {
        return "Chest";
    }

    public Object[] readItems(Object tag, Object[] items) {
        Object list = invoke(tag, "l", new Class<?>[]{String.class}, "Items");
        int listSize = ((Number) invoke(list, "c", new Class<?>[0])).intValue();
        for (int i = 0; i < listSize; ++i) {
            Object itemTag = invoke(list, "a", new Class<?>[]{int.class}, i);
            int slot = ((Number) invoke(itemTag, "c", new Class<?>[]{String.class}, "Slot")).intValue() & 255;
            if (slot >= 0 && slot < items.length) {
                try {
                    Constructor<?> constructor = items.getClass().getComponentType().getConstructor(itemTag.getClass());
                    items[slot] = constructor.newInstance(itemTag);
                } catch (Exception exception) {
                    throw new IllegalStateException("Failed to create item stack from tag", exception);
                }
            }
        }
        return items;
    }

    public void writeItems(Object tag, Object[] items) {
        Object existingList = invoke(tag, "l", new Class<?>[]{String.class}, "Items");
        Object list;
        try {
            list = existingList.getClass().getConstructor().newInstance();
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to create tag list", exception);
        }
        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null) {
                Object itemTag;
                try {
                    itemTag = tag.getClass().getConstructor().newInstance();
                } catch (Exception exception) {
                    throw new IllegalStateException("Failed to create item tag", exception);
                }
                invoke(itemTag, "a", new Class<?>[]{String.class, byte.class}, "Slot", (byte) i);
                invoke(items[i], "a", new Class<?>[]{itemTag.getClass()}, itemTag);
                invoke(list, "a", new Class<?>[]{itemTag.getClass().getSuperclass()}, itemTag);
            }
        }
        invoke(tag, "a", new Class<?>[]{String.class, list.getClass().getSuperclass()}, "Items", list);
    }

    public int getMaxStackSize() {
        return MAX_STACK;
    }

    public boolean canUse(Object world, int x, int y, int z, Object self, Object human) {
        Object tile = invoke(world, "getTileEntity", new Class<?>[]{int.class, int.class, int.class}, x, y, z);
        if (tile != self) {
            return false;
        }
        double distance = ((Number) invoke(
                human,
                "e",
                new Class<?>[]{double.class, double.class, double.class},
                (double) x + 0.5D,
                (double) y + 0.5D,
                (double) z + 0.5D
        )).doubleValue();
        return distance <= 64.0D;
    }

    private static Object invoke(Object target, String name, Class<?>[] parameterTypes, Object... args) {
        try {
            Method method = target.getClass().getMethod(name, parameterTypes);
            return method.invoke(target, args);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to invoke " + name + " on " + target.getClass().getName(), exception);
        }
    }

    private static int getCount(Object itemStack) {
        try {
            Field field = itemStack.getClass().getField("count");
            return field.getInt(itemStack);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to read count from item stack", exception);
        }
    }

    private static void setCount(Object itemStack, int count) {
        try {
            Field field = itemStack.getClass().getField("count");
            field.setInt(itemStack, count);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to write count to item stack", exception);
        }
    }
}
