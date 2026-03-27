package com.legacyminecraft.compat.bukkit;


import java.util.UUID;

/**
 * Canonical Bukkit-compat behaviour for entity world binding fallback.
 */
public final class EntityWorldBindingBehaviour {
    private static final EntityWorldBindingBehaviour INSTANCE = new EntityWorldBindingBehaviour();
    private static final WorldHandleBridgeBehaviour WORLD_HANDLE_BRIDGE_BEHAVIOUR =
            WorldHandleBridgeBehaviour.getInstance();
    private static final ServerWrapperProjectionBridgeBehaviour SERVER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR =
            ServerWrapperProjectionBridgeBehaviour.getInstance();

    private EntityWorldBindingBehaviour() {
    }

    public static EntityWorldBindingBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldUseFallbackWorld(com.legacyminecraft.compat.bukkit.World world) {
        return world == null;
    }

    public com.legacyminecraft.compat.bukkit.World resolveFallbackWorld() {
        return WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveWorldServerHandle(Bukkit.getServer().getWorlds().get(0));
    }

    public com.legacyminecraft.compat.bukkit.World resolveWorld(com.legacyminecraft.compat.bukkit.World world) {
        return world;
    }

    public World resolvePlayerWorld(Server server, NBTTagCompound entityTag, EntityPlayer entityPlayer) {
        World bukkitWorld;
        String worldName = entityTag.getString("World");

        if (entityTag.hasKey("WorldUUIDMost") && entityTag.hasKey("WorldUUIDLeast")) {
            UUID worldUuid = new UUID(entityTag.getLong("WorldUUIDMost"), entityTag.getLong("WorldUUIDLeast"));
            bukkitWorld = server.getWorld(worldUuid);
        } else {
            bukkitWorld = server.getWorld(worldName);
        }

        if (bukkitWorld == null) {
            CraftServer craftServer = SERVER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftServer(server);
            if (craftServer == null) {
                throw new IllegalStateException("Expected CraftServer for entity world resolution");
            }
            bukkitWorld = (World) (Object) craftServer.getServer().getWorldServer(entityPlayer.dimension).getWorld();
        }

        return bukkitWorld;
    }
}
