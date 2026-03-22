package org.bukkit.craftbukkit;

import com.legacyminecraft.poseidon.compat.bukkit.PortalCreationBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.PortalSearchBehaviour;
import net.minecraft.server.Block;
import net.minecraft.server.WorldServer;
import org.bukkit.Location;
import org.bukkit.TravelAgent;

import java.util.Random;

public class PortalTravelAgent implements TravelAgent {
    private static final PortalCreationBehaviour PORTAL_CREATION_BEHAVIOUR = PortalCreationBehaviour.getInstance();
    private static final PortalSearchBehaviour PORTAL_SEARCH_BEHAVIOUR = PortalSearchBehaviour.getInstance();

    private Random random = new Random();

    private int searchRadius = 128;
    private int creationRadius = 14; // 16 -> 14
    private boolean canCreatePortal = true;

    public PortalTravelAgent() { }

    public Location findOrCreate(Location location) {
        WorldServer worldServer = ((CraftWorld) location.getWorld()).getHandle();
        worldServer.chunkProviderServer.forceChunkLoad = true;
        // Attempt to find a Portal.
        Location resultLocation = this.findPortal(location);
        // If a Portal cannot be found we will attempt to create one.
        if (resultLocation == null) {
            // Attempt to create a portal, return if it was successful or not.
            if (this.canCreatePortal && this.createPortal(location)) {
                // Now find that portals location.
                resultLocation = this.findPortal(location);
            } else {
                // Fallback onto the original location.
                resultLocation = location;
            }
        }
        worldServer.chunkProviderServer.forceChunkLoad = false;
        // Return our resulting portal location.
        return resultLocation;
    }

    public Location findPortal(Location location) {
        net.minecraft.server.World world = ((CraftWorld) location.getWorld()).getHandle();
        PortalSearchBehaviour.SearchResult searchResult = PORTAL_SEARCH_BEHAVIOUR.findNearestPortal(
                world,
                location.getBlockX(),
                location.getBlockZ(),
                location.getX(),
                location.getY(),
                location.getZ(),
                this.searchRadius,
                Block.PORTAL.id
        );
        if (!searchResult.isFound()) {
            return null;
        }

        return new Location(
                location.getWorld(),
                searchResult.getPortalCenterX(),
                searchResult.getPortalCenterY(),
                searchResult.getPortalCenterZ(),
                location.getYaw(),
                location.getPitch()
        );
    }

    public boolean createPortal(Location location) {
        return PORTAL_CREATION_BEHAVIOUR.createPortal(
                (CraftWorld) location.getWorld(),
                location,
                this.random,
                this.creationRadius
        );
    }




    public TravelAgent setSearchRadius(int radius) {
        this.searchRadius = radius;
        return this;
    }

    public int getSearchRadius() {
        return this.searchRadius;
    }

    public TravelAgent setCreationRadius(int radius) {
        this.creationRadius = radius < 2 ? 0 : radius - 2;
        return this;
    }

    public int getCreationRadius() {
        return this.creationRadius;
    }

    public boolean getCanCreatePortal() {
        return this.canCreatePortal;
    }

    public void setCanCreatePortal(boolean create) {
        this.canCreatePortal = create;
    }
}
