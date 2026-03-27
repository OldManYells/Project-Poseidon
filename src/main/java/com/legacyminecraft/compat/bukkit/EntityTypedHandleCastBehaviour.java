package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for typed NMS handle casting in CraftBukkit wrappers.
 */
public final class EntityTypedHandleCastBehaviour {
    private static final EntityTypedHandleCastBehaviour INSTANCE = new EntityTypedHandleCastBehaviour();

    private EntityTypedHandleCastBehaviour() {
    }

    public static EntityTypedHandleCastBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T castHandle(Object handle, Class<T> handleType) {
        return handleType.cast(handle);
    }
}
