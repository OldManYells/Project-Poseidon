package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftBukkit entity wrapper identity strings.
 */
public final class CraftEntityIdentityBehaviour {
    private static final CraftEntityIdentityBehaviour INSTANCE = new CraftEntityIdentityBehaviour();

    private CraftEntityIdentityBehaviour() {
    }

    public static CraftEntityIdentityBehaviour getInstance() {
        return INSTANCE;
    }

    public String toString(CraftEntity craftEntity) {
        return craftEntity.getClass().getSimpleName();
    }
}
