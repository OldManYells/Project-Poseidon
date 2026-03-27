package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftEntity context/access passthroughs.
 */
public final class EntityContextAccessBridgeBehaviour {
    private static final EntityContextAccessBridgeBehaviour INSTANCE = new EntityContextAccessBridgeBehaviour();

    private EntityContextAccessBridgeBehaviour() {
    }

    public static EntityContextAccessBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public Entity resolveHandle(Entity handle) {
        return handle;
    }

    public Server resolveServer(CraftServer craftServer) {
        return craftServer;
    }
}
