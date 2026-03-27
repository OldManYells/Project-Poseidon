package com.legacyminecraft.compat.bukkit;


/**
 * Canonical Bukkit bridge behaviour for creating CraftBukkit portal travel agents.
 */
public final class PortalTravelAgentBridgeBehaviour {
    private static final PortalTravelAgentBridgeBehaviour INSTANCE = new PortalTravelAgentBridgeBehaviour();

    private PortalTravelAgentBridgeBehaviour() {
    }

    public static PortalTravelAgentBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public TravelAgent createTravelAgent() {
        return new com.legacyminecraft.compat.bukkit.craftbukkit.PortalTravelAgent();
    }
}
