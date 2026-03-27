package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behaviour for CraftBukkit wrapper descriptor string rendering.
 */
public final class EntityWrapperDescriptionBehaviour {
    private static final EntityWrapperDescriptionBehaviour INSTANCE = new EntityWrapperDescriptionBehaviour();

    private EntityWrapperDescriptionBehaviour() {
    }

    public static EntityWrapperDescriptionBehaviour getInstance() {
        return INSTANCE;
    }

    public String craftEntityToString(int entityId) {
        return "CraftEntity{" + "id=" + entityId + '}';
    }

    public String craftLivingEntityToString(int entityId) {
        return "CraftLivingEntity{" + "id=" + entityId + '}';
    }

    public String craftPlayerToString(String playerName) {
        return "CraftPlayer{" + "name=" + playerName + '}';
    }

    public String craftStorageMinecartToString(Object inventory) {
        return "CraftStorageMinecart{" + "inventory=" + inventory + '}';
    }

    public String craftWolfToString(boolean angry, Object owner, boolean tamed, boolean sitting) {
        return "CraftWolf[anger=" + angry + ",owner=" + owner + ",tame=" + tamed + ",sitting=" + sitting + "]";
    }
}
