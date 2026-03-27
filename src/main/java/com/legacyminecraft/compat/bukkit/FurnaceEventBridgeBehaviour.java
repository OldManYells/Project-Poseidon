package com.legacyminecraft.compat.bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Canonical bridge for Furnace burn/smelt Bukkit event orchestration.
 */
public final class FurnaceEventBridgeBehaviour {
    private static final FurnaceEventBridgeBehaviour INSTANCE = new FurnaceEventBridgeBehaviour();

    private FurnaceEventBridgeBehaviour() {
    }

    public static FurnaceEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public BurnDecision fireBurnEvent(Object world, int x, int y, int z, Object fuelStack, int defaultBurnTime) {
        FurnaceBurnEvent burnEvent = new FurnaceBurnEvent(
                toCompatBlock(invoke(invoke(world, "getWorld"), "getBlockAt", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z))),
                toCompatItemStack(fuelStack),
                defaultBurnTime
        );
        invoke(invoke(invoke(world, "getServer"), "getPluginManager"), "callEvent", burnEvent);
        return new BurnDecision(burnEvent.isCancelled(), burnEvent.getBurnTime(), burnEvent.isBurning());
    }

    public SmeltDecision fireSmeltEvent(Object world, int x, int y, int z, Object sourceStack, Object proposedResult) {
        FurnaceSmeltEvent smeltEvent = new FurnaceSmeltEvent(
                toCompatBlock(invoke(invoke(world, "getWorld"), "getBlockAt", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z))),
                toCompatItemStack(sourceStack),
                proposedResult == null ? null : toCompatItemStackCopy(proposedResult)
        );
        invoke(invoke(invoke(world, "getServer"), "getPluginManager"), "callEvent", smeltEvent);

        if (smeltEvent.isCancelled()) {
            return SmeltDecision.cancelled();
        }

        return SmeltDecision.accepted(toNmsItemStack(smeltEvent.getResult()));
    }

    public static final class BurnDecision {
        private final boolean cancelled;
        private final int burnTime;
        private final boolean burning;

        public BurnDecision(boolean cancelled, int burnTime, boolean burning) {
            this.cancelled = cancelled;
            this.burnTime = burnTime;
            this.burning = burning;
        }

        public boolean isCancelled() {
            return cancelled;
        }

        public int getBurnTime() {
            return burnTime;
        }

        public boolean isBurning() {
            return burning;
        }
    }

    public static final class SmeltDecision {
        private final boolean cancelled;
        private final Object result;

        private SmeltDecision(boolean cancelled, Object result) {
            this.cancelled = cancelled;
            this.result = result;
        }

        public static SmeltDecision cancelled() {
            return new SmeltDecision(true, null);
        }

        public static SmeltDecision accepted(Object result) {
            return new SmeltDecision(false, result);
        }

        public boolean isCancelled() {
            return cancelled;
        }

        public Object getResult() {
            return result;
        }
    }

    private static com.legacyminecraft.compat.bukkit.inventory.ItemStack toCompatItemStack(Object stack) {
        if (stack == null) {
            return null;
        }

        return new com.legacyminecraft.compat.bukkit.inventory.ItemStack(
                stackId(stack),
                stackCount(stack),
                stackDamage(stack)
        );
    }

    private static com.legacyminecraft.compat.bukkit.inventory.ItemStack toCompatItemStackCopy(Object stack) {
        return stack == null ? null : toCompatItemStack(invoke(stack, "cloneItemStack"));
    }

    private static Object toNmsItemStack(com.legacyminecraft.compat.bukkit.inventory.ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }

        try {
            Class<?> nmsItemStackClass = Class.forName("net.minecraft.server.ItemStack");
            return nmsItemStackClass.getConstructor(int.class, int.class, int.class)
                    .newInstance(itemStack.getTypeId(), itemStack.getAmount(), itemStack.getDurability());
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to project Bukkit stack to NMS stack", exception);
        }
    }

    private static Block toCompatBlock(Object block) {
        if (block == null) {
            return null;
        }

        if (block instanceof Block) {
            return (Block) block;
        }

        return new Block();
    }

    private static int stackId(Object stack) {
        return readIntField(stack, "id");
    }

    private static int stackCount(Object stack) {
        return readIntField(stack, "count");
    }

    private static int stackDamage(Object stack) {
        return readIntField(stack, "damage");
    }

    private static int readIntField(Object stack, String fieldName) {
        try {
            Field field = stack.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return ((Number) field.get(stack)).intValue();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read stack field: " + fieldName, exception);
        }
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
