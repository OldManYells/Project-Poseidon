package com.legacyminecraft.compat.bukkit.craftbukkit;

import com.legacyminecraft.compat.bukkit.Location;
import com.legacyminecraft.compat.bukkit.TravelAgent;

/**
 * Canonical compat craftbukkit portal travel-agent scaffold.
 */
public class PortalTravelAgent implements TravelAgent {
    @Override
    public Location findOrCreate(Location location) {
        return location;
    }
}

