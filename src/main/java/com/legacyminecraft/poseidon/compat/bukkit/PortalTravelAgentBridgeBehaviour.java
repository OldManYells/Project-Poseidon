package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.TravelAgent;

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
        return new org.bukkit.craftbukkit.PortalTravelAgent();
    }
}
