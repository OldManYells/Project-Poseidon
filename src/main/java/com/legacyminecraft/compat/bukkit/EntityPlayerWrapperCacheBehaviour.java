package com.legacyminecraft.compat.bukkit;


import java.util.Map;

/**
 * Canonical behaviour for CraftEntity player-wrapper cache resolution.
 */
public final class EntityPlayerWrapperCacheBehaviour {
    private static final EntityPlayerWrapperCacheBehaviour INSTANCE = new EntityPlayerWrapperCacheBehaviour();
    private static final ServerWrapperProjectionBridgeBehaviour SERVER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR =
            ServerWrapperProjectionBridgeBehaviour.getInstance();

    private EntityPlayerWrapperCacheBehaviour() {
    }

    public static EntityPlayerWrapperCacheBehaviour getInstance() {
        return INSTANCE;
    }

    public CraftPlayer resolvePlayer(Map<String, CraftPlayer> playerCache, EntityPlayer entityPlayer) {
        CraftPlayer wrapper = playerCache.get(entityPlayer.name);
        if (wrapper == null) {
            CraftServer craftServer = SERVER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftServer(Bukkit.getServer());
            if (craftServer == null) {
                throw new IllegalStateException("Expected CraftServer for CraftPlayer wrapper caching");
            }
            wrapper = new CraftPlayer(craftServer, entityPlayer);
            playerCache.put(entityPlayer.name, wrapper);
        } else {
            wrapper.setHandle(entityPlayer);
        }
        return wrapper;
    }
}
