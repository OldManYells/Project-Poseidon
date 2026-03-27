package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat player-portal event scaffold.
 */
public class PlayerPortalEvent extends Event {
    private final Player player;
    private final Location from;
    private Location to;
    private final TravelAgent portalTravelAgent;
    private boolean useTravelAgent = true;

    public PlayerPortalEvent(Player player, Location from, Location to, TravelAgent portalTravelAgent) {
        this.player = player;
        this.from = from;
        this.to = to;
        this.portalTravelAgent = portalTravelAgent;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public TravelAgent getPortalTravelAgent() {
        return portalTravelAgent;
    }

    public boolean useTravelAgent() {
        return useTravelAgent;
    }

    public void setUseTravelAgent(boolean useTravelAgent) {
        this.useTravelAgent = useTravelAgent;
    }
}

