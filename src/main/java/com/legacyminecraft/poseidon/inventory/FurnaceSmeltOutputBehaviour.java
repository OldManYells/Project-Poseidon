package com.legacyminecraft.poseidon.inventory;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Canonical furnace output merge and input-consumption behaviour.
 */
public final class FurnaceSmeltOutputBehaviour {
    private static final FurnaceSmeltOutputBehaviour INSTANCE = new FurnaceSmeltOutputBehaviour();

    private FurnaceSmeltOutputBehaviour() {
    }

    public static FurnaceSmeltOutputBehaviour getInstance() {
        return INSTANCE;
    }

    public void applySmeltResult(Object[] items, int outputSlot, Object resultStack) {
        if (items[outputSlot] == null) {
            items[outputSlot] = cloneStack(resultStack);
        } else if (stackId(items[outputSlot]) == stackId(resultStack)
                && stackDamage(items[outputSlot]) == stackDamage(resultStack)) {
            setCount(items[outputSlot], stackCount(items[outputSlot]) + stackCount(resultStack));
        }
    }

    public void consumeInput(Object[] items, int inputSlot) {
        setCount(items[inputSlot], stackCount(items[inputSlot]) - 1);
        if (stackCount(items[inputSlot]) <= 0) {
            items[inputSlot] = null;
        }
    }

    private static Object cloneStack(Object stack) {
        try {
            Method method = stack.getClass().getMethod("cloneItemStack");
            return method.invoke(stack);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to clone stack", exception);
        }
    }

    private static int stackId(Object stack) {
        try {
            Field field = stack.getClass().getDeclaredField("id");
            field.setAccessible(true);
            return ((Number) field.get(stack)).intValue();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read stack id", exception);
        }
    }

    private static int stackCount(Object stack) {
        try {
            Field field = stack.getClass().getDeclaredField("count");
            field.setAccessible(true);
            return ((Number) field.get(stack)).intValue();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read stack count", exception);
        }
    }

    private static int stackDamage(Object stack) {
        try {
            Field field = stack.getClass().getDeclaredField("damage");
            field.setAccessible(true);
            return ((Number) field.get(stack)).intValue();
        } catch (Exception exception) {
            return 0;
        }
    }

    private static void setCount(Object stack, int value) {
        try {
            Field field = stack.getClass().getDeclaredField("count");
            field.setAccessible(true);
            field.set(stack, Integer.valueOf(value));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to write stack count", exception);
        }
    }
}
