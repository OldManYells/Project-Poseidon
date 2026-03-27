package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for projecting Bukkit entities/players to CraftPlayer wrappers.
 */
public final class PlayerWrapperProjectionBridgeBehaviour {
    private static final PlayerWrapperProjectionBridgeBehaviour INSTANCE = new PlayerWrapperProjectionBridgeBehaviour();

    private PlayerWrapperProjectionBridgeBehaviour() {
    }

    public static PlayerWrapperProjectionBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public CraftPlayer requireCraftPlayer(Entity entity) {
        return (CraftPlayer) entity;
    }

    public CraftPlayer requireCraftPlayer(Player player) {
        return (CraftPlayer) player;
    }
}
