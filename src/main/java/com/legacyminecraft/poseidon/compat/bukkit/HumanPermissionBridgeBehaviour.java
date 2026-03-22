package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

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

    public boolean isPermissionSet(PermissibleBase permissible, String name) {
        return permissible.isPermissionSet(name);
    }

    public boolean isPermissionSet(PermissibleBase permissible, Permission permission) {
        return permissible.isPermissionSet(permission);
    }

    public boolean hasPermission(PermissibleBase permissible, String name) {
        return permissible.hasPermission(name);
    }

    public boolean hasPermission(PermissibleBase permissible, Permission permission) {
        return permissible.hasPermission(permission);
    }

    public PermissionAttachment addAttachment(PermissibleBase permissible, Plugin plugin, String name, boolean value) {
        return permissible.addAttachment(plugin, name, value);
    }

    public PermissionAttachment addAttachment(PermissibleBase permissible, Plugin plugin) {
        return permissible.addAttachment(plugin);
    }

    public PermissionAttachment addAttachment(PermissibleBase permissible, Plugin plugin, String name,
                                              boolean value, int ticks) {
        return permissible.addAttachment(plugin, name, value, ticks);
    }

    public PermissionAttachment addAttachment(PermissibleBase permissible, Plugin plugin, int ticks) {
        return permissible.addAttachment(plugin, ticks);
    }

    public void removeAttachment(PermissibleBase permissible, PermissionAttachment attachment) {
        permissible.removeAttachment(attachment);
    }

    public void recalculatePermissions(PermissibleBase permissible) {
        permissible.recalculatePermissions();
    }

    public Set<PermissionAttachmentInfo> getEffectivePermissions(PermissibleBase permissible) {
        return permissible.getEffectivePermissions();
    }

    public boolean applyOperatorState(PermissibleBase permissible, boolean operatorState) {
        permissible.recalculatePermissions();
        return operatorState;
    }
}
