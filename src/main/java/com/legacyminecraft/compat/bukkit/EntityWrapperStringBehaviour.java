package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for simple CraftEntity wrapper string rendering.
 */
public final class EntityWrapperStringBehaviour {
    private static final EntityWrapperStringBehaviour INSTANCE = new EntityWrapperStringBehaviour();

    private EntityWrapperStringBehaviour() {
    }

    public static EntityWrapperStringBehaviour getInstance() {
        return INSTANCE;
    }

    public String toString(String wrapperName) {
        return wrapperName;
    }

    public String toString(String wrapperName, Entity passenger) {
        return wrapperName + "{passenger=" + passenger + '}';
    }
}
