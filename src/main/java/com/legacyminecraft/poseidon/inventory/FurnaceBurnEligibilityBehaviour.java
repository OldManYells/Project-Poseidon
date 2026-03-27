package com.legacyminecraft.poseidon.inventory;

import com.legacyminecraft.poseidon.network.NetworkCompatGatewayRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Canonical furnace smelt eligibility behaviour.
 */
public final class FurnaceBurnEligibilityBehaviour {
    private static final FurnaceBurnEligibilityBehaviour INSTANCE = new FurnaceBurnEligibilityBehaviour();

    private FurnaceBurnEligibilityBehaviour() {
    }

    public static FurnaceBurnEligibilityBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canBurn(Object inputStack, Object outputStack, int maxInventoryStackSize) {
        if (inputStack == null) {
            return false;
        }

        Object smeltingResult = lookupRecipe(stackItemId(inputStack));
        if (smeltingResult == null) {
            return false;
        }

        if (outputStack == null) {
            return true;
        }

        if (!materialsMatch(outputStack, smeltingResult)) {
            return false;
        }

        if (stackCount(outputStack) + stackCount(smeltingResult) <= maxInventoryStackSize
                && stackCount(outputStack) < stackMaxStackSize(outputStack)) {
            return true;
        }

        return stackCount(outputStack) + stackCount(smeltingResult) <= stackMaxStackSize(smeltingResult);
    }

    private static Object lookupRecipe(int itemId) {
        return NetworkCompatGatewayRegistry.gateway().getFurnaceRecipeResult(itemId);
    }

    private static boolean materialsMatch(Object left, Object right) {
        try {
            Method method = left.getClass().getMethod("doMaterialsMatch", left.getClass());
            Object result = method.invoke(left, right);
            if (result instanceof Boolean) {
                return (Boolean) result;
            }
        } catch (Exception ignored) {
        }

        return stackItemId(left) == stackItemId(right) && getData(left) == getData(right);
    }

    private static int stackItemId(Object stack) {
        try {
            Object item = stack.getClass().getMethod("getItem").invoke(stack);
            if (item == null) {
                return -1;
            }
            Field idField = item.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            return ((Number) idField.get(item)).intValue();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read stack item id", exception);
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

    private static int stackMaxStackSize(Object stack) {
        try {
            Object value = stack.getClass().getMethod("getMaxStackSize").invoke(stack);
            return value == null ? 64 : ((Number) value).intValue();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read stack max size", exception);
        }
    }

    private static int getData(Object stack) {
        try {
            Object value = stack.getClass().getMethod("getData").invoke(stack);
            return value == null ? 0 : ((Number) value).intValue();
        } catch (Exception exception) {
            return 0;
        }
    }
}
