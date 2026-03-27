package com.legacyminecraft.poseidon.inventory;


import java.lang.reflect.Method;

public final class SlotInteractionBehaviour {
    private static final SlotInteractionBehaviour INSTANCE = new SlotInteractionBehaviour();

    private SlotInteractionBehaviour() {
    }

    public static SlotInteractionBehaviour getInstance() {
        return INSTANCE;
    }

    public void onSet() {
        // Base slot behaviour only marks inventory dirty via the wrapper callback.
    }

    public boolean isAllowed(Object itemstack) {
        return true;
    }

    public Object getItem(Object inventory, int index) {
        return invoke(inventory, "getItem", index);
    }

    public boolean hasItem(Object itemstack) {
        return itemstack != null;
    }

    public void setItem(Object inventory, int index, Object itemstack) {
        invoke(inventory, "setItem", index, itemstack);
    }

    public void onInventoryChanged(Object inventory) {
        invoke(inventory, "update");
    }

    public int getMaxStackSize(Object inventory) {
        Object value = invoke(inventory, "getMaxStackSize");
        return value == null ? 64 : ((Number) value).intValue();
    }

    public Object splitStack(Object inventory, int index, int amount) {
        return invoke(inventory, "splitStack", index, amount);
    }

    public boolean matchesInventorySlot(Object expectedInventory, int expectedIndex, Object inventory, int index) {
        return inventory == expectedInventory && index == expectedIndex;
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
