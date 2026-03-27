package com.legacyminecraft.poseidon.inventory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Canonical storage and slot-routing operations for legacy InventoryPlayer wrappers.
 */
public final class PlayerInventoryStorageBehaviour {
    private static final PlayerInventoryStorageBehaviour INSTANCE = new PlayerInventoryStorageBehaviour();
    private static final int HOTBAR_SIZE = 9;

    private PlayerInventoryStorageBehaviour() {
    }

    public static PlayerInventoryStorageBehaviour getInstance() {
        return INSTANCE;
    }

    public int hotbarSize() {
        return HOTBAR_SIZE;
    }

    public Object getItemInHand(Object[] items, int itemInHandIndex) {
        return itemInHandIndex < HOTBAR_SIZE && itemInHandIndex >= 0 ? items[itemInHandIndex] : null;
    }

    public int findSlotByItemId(Object[] items, int itemId) {
        for (int i = 0; i < items.length; ++i) {
            Object stack = items[i];
            if (stack != null && intField(stack, "id") == itemId) {
                return i;
            }
        }
        return -1;
    }

    public int findFirstPartial(Object[] items, Object itemStack, int maxStackSize) {
        for (int i = 0; i < items.length; ++i) {
            Object stack = items[i];
            if (stack != null
                    && intField(stack, "id") == intField(itemStack, "id")
                    && boolCall(stack, "isStackable")
                    && intField(stack, "count") < intCall(stack, "getMaxStackSize")
                    && intField(stack, "count") < maxStackSize
                    && (!boolCall(stack, "usesData") || intCall(stack, "getData") == intCall(itemStack, "getData"))) {
                return i;
            }
        }
        return -1;
    }

    public int firstEmptySlot(Object[] items) {
        for (int i = 0; i < items.length; ++i) {
            if (items[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public int canHold(Object[] items, Object itemStack, int maxStackSize) {
        int remains = intField(itemStack, "count");
        for (int i = 0; i < items.length; ++i) {
            Object stack = items[i];
            if (stack == null) {
                return intField(itemStack, "count");
            }

            if (intField(stack, "id") == intField(itemStack, "id")
                    && boolCall(stack, "isStackable")
                    && intField(stack, "count") < intCall(stack, "getMaxStackSize")
                    && intField(stack, "count") < maxStackSize
                    && (!boolCall(stack, "usesData") || intCall(stack, "getData") == intCall(itemStack, "getData"))) {
                int stackCap = Math.min(intCall(stack, "getMaxStackSize"), maxStackSize);
                remains -= stackCap - intField(stack, "count");
            }
            if (remains <= 0) {
                return intField(itemStack, "count");
            }
        }
        return intField(itemStack, "count") - remains;
    }

    public boolean consumeByItemId(Object[] items, int itemId) {
        int index = findSlotByItemId(items, itemId);
        if (index < 0) {
            return false;
        }

        int count = intField(items[index], "count") - 1;
        setIntField(items[index], "count", count);
        if (count <= 0) {
            items[index] = null;
        }
        return true;
    }

    public boolean pickup(Object[] items, Object itemStack, int maxStackSize) {
        int previousCount;
        if (boolCall(itemStack, "f")) {
            int emptySlot = firstEmptySlot(items);
            if (emptySlot >= 0) {
                items[emptySlot] = staticCall(itemStack.getClass(), "b", itemStack);
                setIntField(items[emptySlot], "b", 5);
                setIntField(itemStack, "count", 0);
                return true;
            }
            return false;
        }

        do {
            previousCount = intField(itemStack, "count");
            setIntField(itemStack, "count", storePartial(items, itemStack, maxStackSize));
        } while (intField(itemStack, "count") > 0 && intField(itemStack, "count") < previousCount);

        return intField(itemStack, "count") < previousCount;
    }

    private int storePartial(Object[] items, Object itemStack, int maxStackSize) {
        int itemId = intField(itemStack, "id");
        int remaining = intField(itemStack, "count");
        int slotIndex = findFirstPartial(items, itemStack, maxStackSize);
        if (slotIndex < 0) {
            slotIndex = firstEmptySlot(items);
        }
        if (slotIndex < 0) {
            return remaining;
        }

        if (items[slotIndex] == null) {
            items[slotIndex] = newItemStack(itemStack.getClass(), itemId, 0, intCall(itemStack, "getData"));
        }

        int toMove = remaining;
        toMove = Math.min(toMove, intCall(items[slotIndex], "getMaxStackSize") - intField(items[slotIndex], "count"));
        toMove = Math.min(toMove, maxStackSize - intField(items[slotIndex], "count"));
        if (toMove == 0) {
            return remaining;
        }

        remaining -= toMove;
        setIntField(items[slotIndex], "count", intField(items[slotIndex], "count") + toMove);
        setIntField(items[slotIndex], "b", 5);
        return remaining;
    }

    public Object splitCombined(Object[] items, Object[] armor, int index, int amount) {
        Object[] target = items;
        int resolvedIndex = index;
        if (resolvedIndex >= items.length) {
            target = armor;
            resolvedIndex -= items.length;
        }

        if (target[resolvedIndex] == null) {
            return null;
        }

        if (intField(target[resolvedIndex], "count") <= amount) {
            Object extracted = target[resolvedIndex];
            target[resolvedIndex] = null;
            return extracted;
        }

        Object extracted = call(target[resolvedIndex], "a", amount);
        if (intField(target[resolvedIndex], "count") == 0) {
            target[resolvedIndex] = null;
        }
        return extracted;
    }

    public void setCombined(Object[] items, Object[] armor, int index, Object itemStack) {
        Object[] target = items;
        int resolvedIndex = index;
        if (resolvedIndex >= target.length) {
            resolvedIndex -= target.length;
            target = armor;
        }
        target[resolvedIndex] = itemStack;
    }

    public int combinedSize(Object[] items, Object[] armor) {
        return items.length + armor.length;
    }

    public Object getCombined(Object[] items, Object[] armor, int index) {
        Object[] target = items;
        int resolvedIndex = index;
        if (resolvedIndex >= target.length) {
            resolvedIndex -= target.length;
            target = armor;
        }
        return target[resolvedIndex];
    }

    private static int intField(Object target, String name) {
        return ((Number) getField(target, name)).intValue();
    }

    private static void setIntField(Object target, String name, int value) {
        try {
            Field field = resolveField(target.getClass(), name);
            field.setAccessible(true);
            field.setInt(target, value);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to write field: " + name, exception);
        }
    }

    private static Object getField(Object target, String name) {
        try {
            Field field = resolveField(target.getClass(), name);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read field: " + name, exception);
        }
    }

    private static Field resolveField(Class<?> type, String name) throws NoSuchFieldException {
        Class<?> current = type;
        while (current != null) {
            try {
                return current.getDeclaredField(name);
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass();
            }
        }
        throw new NoSuchFieldException(name);
    }

    private static boolean boolCall(Object target, String method) {
        return (Boolean) call(target, method);
    }

    private static int intCall(Object target, String method, Object... args) {
        return ((Number) call(target, method, args)).intValue();
    }

    private static Object staticCall(Class<?> type, String method, Object arg) {
        try {
            Method resolved = null;
            for (Method candidate : type.getMethods()) {
                if (candidate.getName().equals(method) && candidate.getParameterTypes().length == 1) {
                    resolved = candidate;
                    break;
                }
            }
            if (resolved == null) {
                throw new IllegalStateException("Static method not found: " + method);
            }
            resolved.setAccessible(true);
            return resolved.invoke(null, arg);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to invoke static method: " + method, exception);
        }
    }

    private static Object call(Object target, String method, Object... args) {
        try {
            Method resolved = null;
            for (Method candidate : target.getClass().getMethods()) {
                if (candidate.getName().equals(method) && candidate.getParameterTypes().length == args.length) {
                    resolved = candidate;
                    break;
                }
            }
            if (resolved == null) {
                for (Method candidate : target.getClass().getDeclaredMethods()) {
                    if (candidate.getName().equals(method) && candidate.getParameterTypes().length == args.length) {
                        resolved = candidate;
                        break;
                    }
                }
            }
            if (resolved == null) {
                throw new IllegalStateException("Method not found: " + method);
            }
            resolved.setAccessible(true);
            return resolved.invoke(target, args);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to invoke method: " + method, exception);
        }
    }

    private static Object newItemStack(Class<?> stackClass, int id, int count, int data) {
        try {
            Constructor<?> constructor = stackClass.getConstructor(int.class, int.class, int.class);
            return constructor.newInstance(id, count, data);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to construct item stack", exception);
        }
    }
}
