package com.legacyminecraft.poseidon.inventory;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Canonical inventory/container interaction logic extracted from legacy NMS wrappers.
 */
public final class ContainerInteractionBehaviour {
    private static final ContainerInteractionBehaviour INSTANCE = new ContainerInteractionBehaviour();

    private ContainerInteractionBehaviour() {
    }

    public static ContainerInteractionBehaviour getInstance() {
        return INSTANCE;
    }

    public void addSlot(List slots, List cachedItems, Object slot) {
        setField(slot, "a", Integer.valueOf(slots.size()));
        slots.add(slot);
        cachedItems.add(null);
    }

    public void addListener(Object container, List listeners, List slots, List cachedItems, Object listener) {
        if (listeners.contains(listener)) {
            throw new IllegalArgumentException("Listener already listening");
        }

        listeners.add(listener);
        invoke(listener, "a", container, collectSlotItems(slots));
        broadcastChanges(container, slots, cachedItems, listeners);
    }

    public List collectSlotItems(List slots) {
        ArrayList arraylist = new ArrayList();

        for (int i = 0; i < slots.size(); ++i) {
            arraylist.add(invoke(slots.get(i), "getItem"));
        }

        return arraylist;
    }

    public void broadcastChanges(Object container, List slots, List cachedItems, List listeners) {
        for (int i = 0; i < slots.size(); ++i) {
            Object itemstack = invoke(slots.get(i), "getItem");
            Object itemstack1 = cachedItems.get(i);

            if (!stackEquals(itemstack1, itemstack)) {
                itemstack1 = itemstack == null ? null : invoke(itemstack, "cloneItemStack");
                cachedItems.set(i, itemstack1);

                for (int j = 0; j < listeners.size(); ++j) {
                    invoke(listeners.get(j), "a", container, Integer.valueOf(i), itemstack1);
                }
            }
        }
    }

    public Object findSlot(List slots, Object inventory, int slotIndex) {
        for (int j = 0; j < slots.size(); ++j) {
            Object slot = slots.get(j);

            if (matchesInventorySlot(slot, inventory, slotIndex)) {
                return slot;
            }
        }

        return null;
    }

    public Object getSlot(List slots, int index) {
        return slots.get(index);
    }

    public Object getSlotItem(List slots, int index) {
        Object slot = slots.get(index);
        return slot != null ? invoke(slot, "getItem") : null;
    }

    public Object clickSlot(Object container, List slots, int i, int j, boolean flag, Object entityhuman) {
        Object itemstack = null;

        if (j == 0 || j == 1) {
            Object inventoryplayer = getField(entityhuman, "inventory");

            if (i == -999) {
                if (invoke(inventoryplayer, "j") != null && i == -999) {
                    if (j == 0) {
                        invoke(entityhuman, "b", invoke(inventoryplayer, "j"));
                        invoke(inventoryplayer, "b", new Object[] { null });
                    }

                    if (j == 1) {
                        Object carried = invoke(inventoryplayer, "j");
                        invoke(entityhuman, "b", invoke(carried, "a", Integer.valueOf(1)));
                        if (stackCount(invoke(inventoryplayer, "j")) == 0) {
                            invoke(inventoryplayer, "b", new Object[] { null });
                        }
                    }
                }
            } else {
                int k;

                if (flag) {
                    Object itemstack1 = invoke(container, "a", Integer.valueOf(i));

                    if (itemstack1 != null) {
                        int l = stackCount(itemstack1);

                        itemstack = invoke(itemstack1, "cloneItemStack");
                        Object slot = slots.get(i);

                        if (slot != null && invoke(slot, "getItem") != null) {
                            k = stackCount(invoke(slot, "getItem"));
                            if (k < l) {
                                invoke(container, "a", Integer.valueOf(i), Integer.valueOf(j), Boolean.TRUE, entityhuman);
                            }
                        }
                    }
                } else {
                    Object slot1 = slots.get(i);

                    if (slot1 != null) {
                        invoke(slot1, "c");
                        Object itemstack2 = invoke(slot1, "getItem");
                        Object itemstack3 = invoke(inventoryplayer, "j");

                        if (itemstack2 != null) {
                            itemstack = invoke(itemstack2, "cloneItemStack");
                        }

                        if (itemstack2 == null) {
                            if (itemstack3 != null && (Boolean) invoke(slot1, "isAllowed", itemstack3)) {
                                k = j == 0 ? stackCount(itemstack3) : 1;
                                if (k > ((Number) invoke(slot1, "d")).intValue()) {
                                    k = ((Number) invoke(slot1, "d")).intValue();
                                }

                                invoke(slot1, "c", invoke(itemstack3, "a", Integer.valueOf(k)));
                                if (stackCount(itemstack3) == 0) {
                                    invoke(inventoryplayer, "b", new Object[] { null });
                                }
                            }
                        } else if (itemstack3 == null) {
                            k = j == 0 ? stackCount(itemstack2) : (stackCount(itemstack2) + 1) / 2;
                            Object itemstack4 = invoke(slot1, "a", Integer.valueOf(k));

                            invoke(inventoryplayer, "b", itemstack4);
                            if (stackCount(itemstack2) == 0) {
                                invoke(slot1, "c", new Object[] { null });
                            }

                            invoke(slot1, "a", invoke(inventoryplayer, "j"));
                        } else if ((Boolean) invoke(slot1, "isAllowed", itemstack3)) {
                            if (stackId(itemstack2) == stackId(itemstack3) && (!usesData(itemstack2) || getData(itemstack2) == getData(itemstack3))) {
                                k = j == 0 ? stackCount(itemstack3) : 1;
                                if (k > ((Number) invoke(slot1, "d")).intValue() - stackCount(itemstack2)) {
                                    k = ((Number) invoke(slot1, "d")).intValue() - stackCount(itemstack2);
                                }

                                if (k > getMaxStackSize(itemstack3) - stackCount(itemstack2)) {
                                    k = getMaxStackSize(itemstack3) - stackCount(itemstack2);
                                }

                                invoke(itemstack3, "a", Integer.valueOf(k));
                                if (stackCount(itemstack3) == 0) {
                                    invoke(inventoryplayer, "b", new Object[] { null });
                                }

                                setStackCount(itemstack2, stackCount(itemstack2) + k);
                            } else if (stackCount(itemstack3) <= ((Number) invoke(slot1, "d")).intValue()) {
                                invoke(slot1, "c", itemstack3);
                                invoke(inventoryplayer, "b", itemstack2);
                            }
                        } else if (stackId(itemstack2) == stackId(itemstack3) && getMaxStackSize(itemstack3) > 1 && (!usesData(itemstack2) || getData(itemstack2) == getData(itemstack3))) {
                            k = stackCount(itemstack2);
                            if (k > 0 && k + stackCount(itemstack3) <= getMaxStackSize(itemstack3)) {
                                setStackCount(itemstack3, stackCount(itemstack3) + k);
                                invoke(itemstack2, "a", Integer.valueOf(k));
                                if (stackCount(itemstack2) == 0) {
                                    invoke(slot1, "c", new Object[] { null });
                                }

                                invoke(slot1, "a", invoke(inventoryplayer, "j"));
                            }
                        }
                    }
                }
            }
        }

        return itemstack;
    }

    public void dropCarriedItem(Object entityhuman) {
        Object inventoryplayer = getField(entityhuman, "inventory");

        if (invoke(inventoryplayer, "j") != null) {
            invoke(entityhuman, "b", invoke(inventoryplayer, "j"));
            invoke(inventoryplayer, "b", new Object[] { null });
        }
    }

    public boolean canInteract(Set blockedPlayers, Object entityhuman) {
        return !blockedPlayers.contains(entityhuman);
    }

    public void updateInteractionState(Set blockedPlayers, Object entityhuman, boolean flag) {
        if (flag) {
            blockedPlayers.remove(entityhuman);
        } else {
            blockedPlayers.add(entityhuman);
        }
    }

    public void mergeItemStack(List slots, Object itemstack, int i, int j, boolean flag) {
        int k = i;

        if (flag) {
            k = j - 1;
        }

        Object slot;
        Object itemstack1;

        if ((Boolean) invoke(itemstack, "isStackable")) {
            while (stackCount(itemstack) > 0 && (!flag && k < j || flag && k >= i)) {
                slot = slots.get(k);
                itemstack1 = invoke(slot, "getItem");
                if (itemstack1 != null && stackId(itemstack1) == stackId(itemstack) && (!usesData(itemstack) || getData(itemstack) == getData(itemstack1))) {
                    int l = stackCount(itemstack1) + stackCount(itemstack);

                    if (l <= getMaxStackSize(itemstack)) {
                        setStackCount(itemstack, 0);
                        setStackCount(itemstack1, l);
                        invoke(slot, "c");
                    } else if (stackCount(itemstack1) < getMaxStackSize(itemstack)) {
                        setStackCount(itemstack, stackCount(itemstack) - (getMaxStackSize(itemstack) - stackCount(itemstack1)));
                        setStackCount(itemstack1, getMaxStackSize(itemstack));
                        invoke(slot, "c");
                    }
                }

                if (flag) {
                    --k;
                } else {
                    ++k;
                }
            }
        }

        if (stackCount(itemstack) > 0) {
            if (flag) {
                k = j - 1;
            } else {
                k = i;
            }

            while (!flag && k < j || flag && k >= i) {
                slot = slots.get(k);
                itemstack1 = invoke(slot, "getItem");
                if (itemstack1 == null) {
                    invoke(slot, "c", invoke(itemstack, "cloneItemStack"));
                    invoke(slot, "c");
                    setStackCount(itemstack, 0);
                    break;
                }

                if (flag) {
                    --k;
                } else {
                    ++k;
                }
            }
        }
    }

    private static boolean stackEquals(Object left, Object right) {
        if (left == right) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return stackId(left) == stackId(right)
                && stackCount(left) == stackCount(right)
                && getData(left) == getData(right);
    }

    private static int stackId(Object stack) {
        return ((Number) getField(stack, "id")).intValue();
    }

    private static int stackCount(Object stack) {
        return ((Number) getField(stack, "count")).intValue();
    }

    private static void setStackCount(Object stack, int count) {
        setField(stack, "count", Integer.valueOf(count));
    }

    private static boolean usesData(Object stack) {
        Object value = invoke(stack, "usesData");
        return value instanceof Boolean && (Boolean) value;
    }

    private static int getData(Object stack) {
        Object value = invoke(stack, "getData");
        return value == null ? 0 : ((Number) value).intValue();
    }

    private static int getMaxStackSize(Object stack) {
        Object value = invoke(stack, "getMaxStackSize");
        return value == null ? 64 : ((Number) value).intValue();
    }

    private static boolean matchesInventorySlot(Object slot, Object inventory, int index) {
        Object value = invoke(slot, "a", inventory, Integer.valueOf(index));
        return value instanceof Boolean && (Boolean) value;
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

    private static Object getField(Object target, String fieldName) {
        if (target == null) {
            return null;
        }

        Class<?> type = target.getClass();
        while (type != null) {
            try {
                Field field = type.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            } catch (NoSuchFieldException ignored) {
                type = type.getSuperclass();
            } catch (Exception exception) {
                throw new IllegalStateException("Unable to read field: " + fieldName, exception);
            }
        }

        throw new IllegalStateException("Field not found: " + fieldName);
    }

    private static void setField(Object target, String fieldName, Object value) {
        if (target == null) {
            return;
        }

        Class<?> type = target.getClass();
        while (type != null) {
            try {
                Field field = type.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target, value);
                return;
            } catch (NoSuchFieldException ignored) {
                type = type.getSuperclass();
            } catch (Exception exception) {
                throw new IllegalStateException("Unable to write field: " + fieldName, exception);
            }
        }

        throw new IllegalStateException("Field not found: " + fieldName);
    }
}
