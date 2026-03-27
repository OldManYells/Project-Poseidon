package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftPlayer equality/hash identity policy.
 */
public final class PlayerIdentityBridgeBehaviour {
    private static final PlayerIdentityBridgeBehaviour INSTANCE = new PlayerIdentityBridgeBehaviour();
    private static final PlayerWrapperProjectionBridgeBehaviour PLAYER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR =
            PlayerWrapperProjectionBridgeBehaviour.getInstance();

    private PlayerIdentityBridgeBehaviour() {
    }

    public static PlayerIdentityBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean equalsByName(Object self, Object otherObject) {
        if (otherObject == null) {
            return false;
        }
        if (self.getClass() != otherObject.getClass()) {
            return false;
        }

        String selfName = BridgeReflection.cast(BridgeReflection.invoke(self, "getName"));
        String otherName = BridgeReflection.cast(BridgeReflection.invoke(otherObject, "getName"));
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
