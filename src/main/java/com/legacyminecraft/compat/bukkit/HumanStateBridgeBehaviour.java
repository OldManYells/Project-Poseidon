package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftHumanEntity low-level state and identity bridge access.
 */
public final class HumanStateBridgeBehaviour {
    private static final HumanStateBridgeBehaviour INSTANCE = new HumanStateBridgeBehaviour();

    private HumanStateBridgeBehaviour() {
    }

    public static HumanStateBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public String getName(Object entity) {
        return BridgeReflection.cast(BridgeReflection.getField(entity, "name"));
    }

    public boolean isSleeping(Object entity) {
        return (Boolean) BridgeReflection.getField(entity, "sleeping");
    }

    public int getSleepTicks(Object entity) {
        return ((Number) BridgeReflection.getField(entity, "sleepTicks")).intValue();
    }

    public boolean isOperator(boolean opFlag) {
        return opFlag;
    }

    public String toString(int entityId, String name) {
        return "CraftHumanEntity{" + "id=" + entityId + "name=" + name + '}';
    }
}
