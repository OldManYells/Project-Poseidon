package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for projecting generic values to NMS/Bukkit entity views.
 */
public final class NmsEntityProjectionBridgeBehaviour {
    private static final NmsEntityProjectionBridgeBehaviour INSTANCE = new NmsEntityProjectionBridgeBehaviour();

    private NmsEntityProjectionBridgeBehaviour() {
    }

    public static NmsEntityProjectionBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public net.minecraft.server.Entity resolveNmsEntity(Object value) {
        if (value instanceof net.minecraft.server.Entity) {
            return (net.minecraft.server.Entity) value;
        }
        return null;
    }

    public org.bukkit.entity.Entity resolveBukkitEntity(Object value) {
        net.minecraft.server.Entity nmsEntity = resolveNmsEntity(value);
        return nmsEntity == null ? null : nmsEntity.getBukkitEntity();
    }

    public org.bukkit.entity.Player resolveOnlinePlayer(Object value) {
        if (value instanceof net.minecraft.server.EntityPlayer) {
            return ((net.minecraft.server.EntityPlayer) value).netServerHandler.getPlayer();
        }
        return null;
    }
}
