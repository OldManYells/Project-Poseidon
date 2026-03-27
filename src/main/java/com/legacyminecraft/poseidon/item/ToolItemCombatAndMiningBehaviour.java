package com.legacyminecraft.poseidon.item;


public final class ToolItemCombatAndMiningBehaviour {
    private static final ToolItemCombatAndMiningBehaviour INSTANCE = new ToolItemCombatAndMiningBehaviour();

    private ToolItemCombatAndMiningBehaviour() {
    }

    public static ToolItemCombatAndMiningBehaviour getInstance() {
        return INSTANCE;
    }

    public float resolveDestroySpeed(Object[] effectiveBlocks, float effectiveSpeed, Object block) {
        for (int i = 0; i < effectiveBlocks.length; ++i) {
            if (effectiveBlocks[i] == block) {
                return effectiveSpeed;
            }
        }
        return 1.0F;
    }

    public boolean damageOnEntityHit(Object itemstack, Object damager, int amount) {
        invokeDamage(itemstack, amount, damager);
        return true;
    }

    public boolean damageOnBlockBreak(Object itemstack, Object user, int amount) {
        invokeDamage(itemstack, amount, user);
        return true;
    }

    public int resolveToolAttackDamage(int baseAttackOffset, Object material) {
        return baseAttackOffset + readMaterialAttackBonus(material);
    }

    public int resolveAttackDamageAgainstEntity(int attackDamage, Object entity) {
        return attackDamage;
    }

    private void invokeDamage(Object itemstack, int amount, Object user) {
        if (itemstack == null) {
            return;
        }
        try {
            java.lang.reflect.Method[] methods = itemstack.getClass().getMethods();
            for (int i = 0; i < methods.length; ++i) {
                java.lang.reflect.Method method = methods[i];
                if (!"damage".equals(method.getName())) {
                    continue;
                }
                Class[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 2 && parameterTypes[0] == Integer.TYPE) {
                    method.invoke(itemstack, Integer.valueOf(amount), user);
                    return;
                }
            }
        } catch (ReflectiveOperationException ignored) {
            // no-op in lean migration scaffold when legacy signature differs
        }
    }

    private int readMaterialAttackBonus(Object material) {
        if (material == null) {
            return 0;
        }
        try {
            Object value = material.getClass().getMethod("c").invoke(material);
            return value instanceof Number ? ((Number) value).intValue() : 0;
        } catch (ReflectiveOperationException ignored) {
            return 0;
        }
    }
}
