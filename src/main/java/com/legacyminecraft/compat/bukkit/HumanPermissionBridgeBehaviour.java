package com.legacyminecraft.compat.bukkit;


import java.util.Set;

/**
 * Canonical behaviour for CraftHumanEntity permission and operator state delegation.
 */
public final class HumanPermissionBridgeBehaviour {
    private static final HumanPermissionBridgeBehaviour INSTANCE = new HumanPermissionBridgeBehaviour();

    private HumanPermissionBridgeBehaviour() {
    }

    public static HumanPermissionBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isPermissionSet(Object permissible, String name) {
        return (Boolean) BridgeReflection.invoke(permissible, "isPermissionSet", name);
    }

    public boolean isPermissionSet(Object permissible, Object permission) {
        return (Boolean) BridgeReflection.invoke(permissible, "isPermissionSet", permission);
    }

    public boolean hasPermission(Object permissible, String name) {
        return (Boolean) BridgeReflection.invoke(permissible, "hasPermission", name);
    }

    public boolean hasPermission(Object permissible, Object permission) {
        return (Boolean) BridgeReflection.invoke(permissible, "hasPermission", permission);
    }

    public <T> T addAttachment(Object permissible, Object plugin, String name, boolean value) {
        return BridgeReflection.cast(BridgeReflection.invoke(permissible, "addAttachment", plugin, name, value));
    }

    public <T> T addAttachment(Object permissible, Object plugin) {
        return BridgeReflection.cast(BridgeReflection.invoke(permissible, "addAttachment", plugin));
    }

    public <T> T addAttachment(Object permissible, Object plugin, String name,
                                              boolean value, int ticks) {
        return BridgeReflection.cast(BridgeReflection.invoke(permissible, "addAttachment", plugin, name, value, ticks));
    }

    public <T> T addAttachment(Object permissible, Object plugin, int ticks) {
        return BridgeReflection.cast(BridgeReflection.invoke(permissible, "addAttachment", plugin, ticks));
    }

    public void removeAttachment(Object permissible, Object attachment) {
        BridgeReflection.invoke(permissible, "removeAttachment", attachment);
    }

    public void recalculatePermissions(Object permissible) {
        BridgeReflection.invoke(permissible, "recalculatePermissions");
    }

    public <T> Set<T> getEffectivePermissions(Object permissible) {
        return BridgeReflection.cast(BridgeReflection.invoke(permissible, "getEffectivePermissions"));
    }

    public boolean applyOperatorState(Object permissible, boolean operatorState) {
        BridgeReflection.invoke(permissible, "recalculatePermissions");
        return operatorState;
    }
}
