package com.legacyminecraft.poseidon.inventory;

/**
 * Canonical armor and equipment lifecycle operations for legacy InventoryPlayer wrappers.
 */
public final class PlayerInventoryEquipmentBehaviour {
    private static final PlayerInventoryEquipmentBehaviour INSTANCE = new PlayerInventoryEquipmentBehaviour();

    private PlayerInventoryEquipmentBehaviour() {
    }

    public static PlayerInventoryEquipmentBehaviour getInstance() {
        return INSTANCE;
    }

    public int calculateArmorValue(Object[] armor, ArmorStatsResolver resolver) {
        int armorPoints = 0;
        int remainingDurability = 0;
        int maxDurability = 0;

        for (int i = 0; i < armor.length; ++i) {
            Object stack = armor[i];
            if (stack != null && resolver.isArmor(stack)) {
                int stackMaxDurability = resolver.getMaxDurability(stack);
                int stackDamage = resolver.getCurrentDamage(stack);
                int stackRemaining = stackMaxDurability - stackDamage;

                remainingDurability += stackRemaining;
                maxDurability += stackMaxDurability;
                armorPoints += resolver.getArmorReduction(stack);
            }
        }

        if (maxDurability == 0) {
            return 0;
        }
        return (armorPoints - 1) * remainingDurability / maxDurability + 1;
    }

    public void damageArmor(Object[] armor, int amount, ArmorDamageCallbacks callbacks) {
        for (int i = 0; i < armor.length; ++i) {
            Object stack = armor[i];
            if (stack != null && callbacks.isArmor(stack)) {
                callbacks.damage(stack, amount);
                int count = ((Number) getField(stack, "count")).intValue();
                if (count == 0) {
                    callbacks.onBroken(stack);
                    armor[i] = null;
                }
            }
        }
    }

    public void dropAll(Object[] items, Object[] armor, DropSink dropSink) {
        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null) {
                dropSink.drop(items[i]);
                items[i] = null;
            }
        }

        for (int i = 0; i < armor.length; ++i) {
            if (armor[i] != null) {
                dropSink.drop(armor[i]);
                armor[i] = null;
            }
        }
    }

    public boolean contains(Object[] armor, Object[] items, Object target) {
        for (int i = 0; i < armor.length; ++i) {
            if (armor[i] != null && (Boolean) invoke(armor[i], "c", target)) {
                return true;
            }
        }
        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null && (Boolean) invoke(items[i], "c", target)) {
                return true;
            }
        }
        return false;
    }

    private Object getField(Object target, String fieldName) {
        try {
            java.lang.reflect.Field field = target.getClass().getField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read field: " + fieldName, exception);
        }
    }

    private Object invoke(Object target, String methodName, Object arg) {
        try {
            java.lang.reflect.Method[] methods = target.getClass().getMethods();
            for (java.lang.reflect.Method method : methods) {
                if (method.getName().equals(methodName) && method.getParameterTypes().length == 1) {
                    method.setAccessible(true);
                    return method.invoke(target, arg);
                }
            }
            throw new IllegalStateException("Method not found: " + methodName);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to invoke method: " + methodName, exception);
        }
    }

    public interface ArmorStatsResolver {
        boolean isArmor(Object stack);

        int getMaxDurability(Object stack);

        int getCurrentDamage(Object stack);

        int getArmorReduction(Object stack);
    }

    public interface ArmorDamageCallbacks {
        boolean isArmor(Object stack);

        void damage(Object stack, int amount);

        void onBroken(Object stack);
    }

    public interface DropSink {
        void drop(Object stack);
    }
}
