package com.legacyminecraft.poseidon.world;


import java.util.List;

/**
 * Canonical behaviour for world entity-removal lifecycle policy.
 */
public final class WorldEntityRemovalBehaviour {
    private static final WorldEntityRemovalBehaviour INSTANCE = new WorldEntityRemovalBehaviour();

    private WorldEntityRemovalBehaviour() {
    }

    public static WorldEntityRemovalBehaviour getInstance() {
        return INSTANCE;
    }

    public void detachMounts(Entity entity) {
        if (entity.passenger != null) {
            entity.passenger.mount((Entity) null);
        }

        if (entity.vehicle != null) {
            entity.mount((Entity) null);
        }
    }

    public boolean markDeadAndWasPlayer(Entity entity) {
        entity.die();
        return isEntityHuman(entity);
    }

    public boolean isPlayer(Entity entity) {
        return isEntityHuman(entity);
    }

    public void addPlayer(List players, Entity entity) {
        if (isEntityHuman(entity)) {
            players.add(entity);
        }
    }

    public void removePlayer(List players, Entity entity) {
        if (isEntityHuman(entity)) {
            players.remove(entity);
        }
    }

    private static boolean isEntityHuman(Object entity) {
        if (entity == null) {
            return false;
        }
        Class<?> current = entity.getClass();
        while (current != null) {
            if ("EntityHuman".equals(current.getSimpleName())) {
                return true;
            }
            current = current.getSuperclass();
        }
        return false;
    }
}
