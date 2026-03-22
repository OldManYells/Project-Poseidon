package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.craftbukkit.entity.CraftPlayer;

/**
 * Canonical behaviour for CraftPlayer equality/hash identity policy.
 */
public final class PlayerIdentityBridgeBehaviour {
    private static final PlayerIdentityBridgeBehaviour INSTANCE = new PlayerIdentityBridgeBehaviour();

    private PlayerIdentityBridgeBehaviour() {
    }

    public static PlayerIdentityBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean equalsByName(CraftPlayer self, Object otherObject) {
        if (otherObject == null) {
            return false;
        }
        if (self.getClass() != otherObject.getClass()) {
            return false;
        }

        CraftPlayer other = (CraftPlayer) otherObject;
        String selfName = self.getName();
        String otherName = other.getName();
        if (selfName == null) {
            return otherName == null;
        }
        return selfName.equals(otherName);
    }

    public int hashByName(String playerName) {
        int hash = 5;
        hash = 97 * hash + (playerName != null ? playerName.hashCode() : 0);
        return hash;
    }
}
