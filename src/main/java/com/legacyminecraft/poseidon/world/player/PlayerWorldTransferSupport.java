package com.legacyminecraft.poseidon.world.player;

import com.legacyminecraft.compat.bukkit.PortalTravelAgentBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.WorldDimensionBridgeBehaviour;

/**
 * Canonical world-transfer helpers for respawn and portal movement flows.
 */
public final class PlayerWorldTransferSupport {
    private static final PlayerWorldTransferSupport INSTANCE = new PlayerWorldTransferSupport();
    private static final WorldDimensionBridgeBehaviour WORLD_DIMENSION_BRIDGE = WorldDimensionBridgeBehaviour.getInstance();
    private static final PortalTravelAgentBridgeBehaviour PORTAL_TRAVEL_AGENT_BRIDGE = PortalTravelAgentBridgeBehaviour.getInstance();

    private PlayerWorldTransferSupport() {
    }

    public static PlayerWorldTransferSupport getInstance() {
        return INSTANCE;
    }

    public RespawnResolution resolveRespawnLocation(
            MinecraftServer minecraftServer,
            Server bukkitServer,
            EntityPlayer entityPlayer,
            int targetDimension,
            Location requestedLocation
    ) {
        ChunkCoordinates bedCoordinates = entityPlayer.getBed();
        com.legacyminecraft.compat.bukkit.World sourceWorld = entityPlayer.getBukkitEntity().getWorld();
        Location location = requestedLocation;

        if (location == null) {
            boolean isBedSpawn = false;
            com.legacyminecraft.compat.bukkit.World spawnWorld = minecraftServer.server.getWorld(entityPlayer.spawnWorld);
            WorldServer spawnWorldHandle = WORLD_DIMENSION_BRIDGE.resolveWorldHandle(spawnWorld, null);
            if (spawnWorld != null && spawnWorldHandle != null && bedCoordinates != null) {
                ChunkCoordinates bedSpawn = EntityHuman.getBed(spawnWorldHandle, bedCoordinates);
                if (bedSpawn != null) {
                    isBedSpawn = true;
                    location = new Location(spawnWorld, bedSpawn.x + 0.5, bedSpawn.y, bedSpawn.z + 0.5);
                } else {
                    entityPlayer.netServerHandler.sendPacket(new Packet70Bed(0));
                }
            }

            if (location == null) {
                com.legacyminecraft.compat.bukkit.World primaryWorld = (com.legacyminecraft.compat.bukkit.World) minecraftServer.server.getWorlds().get(0);
                WorldServer primaryWorldHandle = WORLD_DIMENSION_BRIDGE.resolveWorldHandle(primaryWorld, null);
                if (primaryWorldHandle != null) {
                    ChunkCoordinates worldSpawn = primaryWorldHandle.getSpawn();
                    float yaw = primaryWorldHandle.worldData.getYaw();
                    float pitch = primaryWorldHandle.worldData.getPitch();
                    location = new Location(primaryWorld, worldSpawn.x + 0.5, worldSpawn.y, worldSpawn.z + 0.5, yaw, pitch);
                } else {
                    location = primaryWorld.getSpawnLocation();
                }
            }

            Player respawnPlayer = (Player) entityPlayer.getBukkitEntity();
            PlayerRespawnEvent respawnEvent = new PlayerRespawnEvent(respawnPlayer, location, isBedSpawn);
            bukkitServer.getPluginManager().callEvent(respawnEvent);

            location = respawnEvent.getRespawnLocation();
            entityPlayer.health = 20;
            entityPlayer.fireTicks = 0;
            entityPlayer.fallDistance = 0;
        } else {
            location.setWorld(minecraftServer.getWorldServer(targetDimension).getWorld());
        }

        WorldServer worldserver = WORLD_DIMENSION_BRIDGE.resolveWorldHandle(location.getWorld(), minecraftServer.getWorldServer(targetDimension));
        entityPlayer.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        return new RespawnResolution(entityPlayer, sourceWorld, location, worldserver);
    }

    public PortalResolution resolvePortalDestination(MinecraftServer minecraftServer, EntityPlayer entityPlayer) {
        int dimension = entityPlayer.dimension;
        WorldServer fromWorld = minecraftServer.getWorldServer(dimension);
        WorldServer toWorld = null;
        if (dimension < 10) {
            int toDimension = dimension == -1 ? 0 : -1;
            for (WorldServer world : minecraftServer.worlds) {
                if (world.dimension == toDimension) {
                    toWorld = world;
                }
            }
        }
        double blockRatio = dimension == -1 ? 8 : 0.125;

        Location fromLocation = new Location(fromWorld.getWorld(), entityPlayer.locX, entityPlayer.locY, entityPlayer.locZ, entityPlayer.yaw, entityPlayer.pitch);
        Location toLocation = toWorld == null ? null : new Location(toWorld.getWorld(), (entityPlayer.locX * blockRatio), entityPlayer.locY, (entityPlayer.locZ * blockRatio), entityPlayer.yaw, entityPlayer.pitch);

        TravelAgent travelAgent = PORTAL_TRAVEL_AGENT_BRIDGE.createTravelAgent();
        PlayerPortalEvent event = new PlayerPortalEvent((Player) entityPlayer.getBukkitEntity(), fromLocation, toLocation, travelAgent);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled() || event.getTo() == null) {
            return null;
        }

        Location finalLocation = event.getTo();
        if (event.useTravelAgent()) {
            finalLocation = event.getPortalTravelAgent().findOrCreate(finalLocation);
        }
        WorldServer destinationWorld = WORLD_DIMENSION_BRIDGE.resolveWorldHandle(finalLocation.getWorld(), toWorld);
        if (destinationWorld == null) {
            return null;
        }
        return new PortalResolution(destinationWorld.dimension, finalLocation);
    }

    public static final class RespawnResolution {
        private final EntityPlayer entityPlayer;
        private final com.legacyminecraft.compat.bukkit.World sourceWorld;
        private final Location respawnLocation;
        private final WorldServer destinationWorld;

        RespawnResolution(EntityPlayer entityPlayer, com.legacyminecraft.compat.bukkit.World sourceWorld, Location respawnLocation, WorldServer destinationWorld) {
            this.entityPlayer = entityPlayer;
            this.sourceWorld = sourceWorld;
            this.respawnLocation = respawnLocation;
            this.destinationWorld = destinationWorld;
        }

        public EntityPlayer getEntityPlayer() {
            return entityPlayer;
        }

        public com.legacyminecraft.compat.bukkit.World getSourceWorld() {
            return sourceWorld;
        }

        public Location getRespawnLocation() {
            return respawnLocation;
        }

        public WorldServer getDestinationWorld() {
            return destinationWorld;
        }

        /**
         * @deprecated Use {@link #getEntityPlayer()}.
         */
        @Deprecated
        public EntityPlayer getEntityplayer() {
            return getEntityPlayer();
        }

        /**
         * @deprecated Use {@link #getSourceWorld()}.
         */
        @Deprecated
        public com.legacyminecraft.compat.bukkit.World getFromWorld() {
            return getSourceWorld();
        }

        /**
         * @deprecated Use {@link #getRespawnLocation()}.
         */
        @Deprecated
        public Location getLocation() {
            return getRespawnLocation();
        }

        /**
         * @deprecated Use {@link #getDestinationWorld()}.
         */
        @Deprecated
        public WorldServer getWorldserver() {
            return getDestinationWorld();
        }
    }

    public static final class PortalResolution {
        private final int destinationDimension;
        private final Location location;

        PortalResolution(int destinationDimension, Location location) {
            this.destinationDimension = destinationDimension;
            this.location = location;
        }

        public int getDestinationDimension() {
            return destinationDimension;
        }

        public Location getLocation() {
            return location;
        }
    }
}
