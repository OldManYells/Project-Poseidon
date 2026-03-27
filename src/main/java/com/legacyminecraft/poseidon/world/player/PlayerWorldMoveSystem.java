package com.legacyminecraft.poseidon.world.player;


import java.util.List;

/**
 * Canonical player world-transfer orchestration for legacy manager wrappers.
 */
public final class PlayerWorldMoveSystem {
    private static final PlayerWorldMoveSystem INSTANCE = new PlayerWorldMoveSystem();

    private PlayerWorldMoveSystem() {
    }

    public static PlayerWorldMoveSystem getInstance() {
        return INSTANCE;
    }

    public EntityPlayer moveToWorld(
            MinecraftServer server,
            Server bukkitServer,
            List players,
            EntityPlayer entityPlayer,
            int targetDimension,
            Location targetLocation,
            PlayerLifecycleCoordinator playerLifecycleCoordinator,
            PlayerSessionSystem playerSessionSystem,
            PlayerWorldTransferSupport playerWorldTransferSupport,
            RespawnPacketPairSystem respawnPacketPairSystem
    ) {
        server.getTracker(entityPlayer.dimension).untrackPlayer(entityPlayer);
        playerLifecycleCoordinator.removePlayerFromCurrentManager(server, entityPlayer);
        players.remove(entityPlayer);
        server.getWorldServer(entityPlayer.dimension).removeEntity(entityPlayer);

        PlayerWorldTransferSupport.RespawnResolution respawnResolution =
                playerWorldTransferSupport.resolveRespawnLocation(server, bukkitServer, entityPlayer, targetDimension, targetLocation);
        EntityPlayer respawnedPlayer = respawnResolution.getEntityPlayer();
        com.legacyminecraft.compat.bukkit.World sourceWorld = respawnResolution.getSourceWorld();
        Location resolvedLocation = respawnResolution.getRespawnLocation();
        WorldServer destinationWorld = respawnResolution.getDestinationWorld();

        destinationWorld.chunkProviderServer.getChunkAt((int) respawnedPlayer.locX >> 4, (int) respawnedPlayer.locZ >> 4);

        while (destinationWorld.getEntities(respawnedPlayer, respawnedPlayer.boundingBox).size() != 0) {
            respawnedPlayer.setPosition(respawnedPlayer.locX, respawnedPlayer.locY + 1.0D, respawnedPlayer.locZ);
        }

        respawnPacketPairSystem.sendRespawnPacketPair(respawnedPlayer, destinationWorld);
        respawnedPlayer.spawnIn(destinationWorld);
        respawnedPlayer.dead = false;
        respawnedPlayer.netServerHandler.teleport(
                new Location(
                        destinationWorld.getWorld(),
                        respawnedPlayer.locX,
                        respawnedPlayer.locY,
                        respawnedPlayer.locZ,
                        respawnedPlayer.yaw,
                        respawnedPlayer.pitch
                )
        );

        playerSessionSystem.sendWorldState(respawnedPlayer, destinationWorld);
        playerLifecycleCoordinator.addPlayerToCurrentManager(server, respawnedPlayer);
        destinationWorld.addEntity(respawnedPlayer);
        players.add(respawnedPlayer);
        playerSessionSystem.refreshClient(respawnedPlayer);
        respawnedPlayer.x();
        if (sourceWorld != resolvedLocation.getWorld()) {
            com.legacyminecraft.compat.bukkit.event.player.PlayerChangedWorldEvent event =
                    new com.legacyminecraft.compat.bukkit.event.player.PlayerChangedWorldEvent((Player) respawnedPlayer.getBukkitEntity(), sourceWorld);
            bukkitServer.getPluginManager().callEvent(event);
        }

        return respawnedPlayer;
    }
}
