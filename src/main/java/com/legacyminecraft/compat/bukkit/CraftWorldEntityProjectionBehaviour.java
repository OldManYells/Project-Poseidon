package com.legacyminecraft.compat.bukkit;


import java.util.ArrayList;
import java.util.List;

/**
 * Canonical behavior for CraftWorld entity-list projection to Bukkit API views.
 */
public final class CraftWorldEntityProjectionBehaviour {
    private static final CraftWorldEntityProjectionBehaviour INSTANCE = new CraftWorldEntityProjectionBehaviour();
    private static final NmsEntityProjectionBridgeBehaviour NMS_ENTITY_PROJECTION_BRIDGE_BEHAVIOUR =
            NmsEntityProjectionBridgeBehaviour.getInstance();

    private CraftWorldEntityProjectionBehaviour() {
    }

    public static CraftWorldEntityProjectionBehaviour getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> toEntities(List<?> worldEntityList) {
        List<T> entities = new ArrayList<T>();
        for (Object value : worldEntityList) {
            Object bukkitEntity = resolveBukkitEntity(value);
            if (bukkitEntity != null) {
                entities.add((T) bukkitEntity);
            }
        }
        return entities;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> toLivingEntities(List<?> worldEntityList) {
        List<T> livingEntities = new ArrayList<T>();
        for (Object value : worldEntityList) {
            Object bukkitEntity = resolveBukkitEntity(value);
            if (implementsSimpleName(bukkitEntity, "LivingEntity")) {
                livingEntities.add((T) bukkitEntity);
            }
        }
        return livingEntities;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> toPlayers(List<?> worldEntityList) {
        List<T> players = new ArrayList<T>();
        for (Object value : worldEntityList) {
            Object bukkitEntity = resolveBukkitEntity(value);
            if (implementsSimpleName(bukkitEntity, "Player")) {
                players.add((T) bukkitEntity);
            }
        }
        return players;
    }

    private static Object resolveBukkitEntity(Object value) {
        Object bridged = NMS_ENTITY_PROJECTION_BRIDGE_BEHAVIOUR.resolveBukkitEntity(value);
        if (bridged != null) {
            return bridged;
        }
        if (value == null) {
            return null;
        }
        try {
            return value.getClass().getMethod("getBukkitEntity").invoke(value);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private static boolean implementsSimpleName(Object value, String interfaceSimpleName) {
        if (value == null) {
            return false;
        }
        Class<?> type = value.getClass();
        while (type != null) {
            Class<?>[] interfaces = type.getInterfaces();
            for (int index = 0; index < interfaces.length; index++) {
                if (interfaceSimpleName.equals(interfaces[index].getSimpleName())) {
                    return true;
                }
            }
            type = type.getSuperclass();
        }
        return false;
    }
}
