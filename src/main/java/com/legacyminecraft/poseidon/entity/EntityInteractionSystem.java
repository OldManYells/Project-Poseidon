package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Packet7UseEntity;
import net.minecraft.server.WorldServer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Canonical handler for entity interaction packet flow.
 */
public final class EntityInteractionSystem {
    private static final EntityInteractionSystem INSTANCE = new EntityInteractionSystem();
    private final EntityInteractionPolicy interactionPolicy = EntityInteractionPolicy.getInstance();

    private EntityInteractionSystem() {
    }

    public static EntityInteractionSystem getInstance() {
        return INSTANCE;
    }

    public void handleUseEntityPacket(MinecraftServer minecraftServer, Server server, EntityPlayer player, Packet7UseEntity packet7useentity) {
        if (player.dead) {
            return;
        }

        WorldServer worldserver = minecraftServer.getWorldServer(player.dimension);
        Entity entity = worldserver.getEntity(packet7useentity.target);
        ItemStack itemInHand = player.inventory.getItemInHand();

        if (interactionPolicy.canProcessInteraction(player, entity)) {
            EntityInteractionMode interactionMode = interactionPolicy.resolveInteractionMode(packet7useentity.c);
            if (interactionMode == EntityInteractionMode.INTERACT) {
                Player bukkitPlayer = (Player) player.getBukkitEntity();
                org.bukkit.entity.Entity bukkitEntity = entity.getBukkitEntity();

                if (interactionPolicy.shouldCancelStorageMinecartInteraction(bukkitPlayer.isInsideVehicle(), bukkitEntity instanceof StorageMinecart)) {
                    return;
                }

                PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(bukkitPlayer, bukkitEntity);
                server.getPluginManager().callEvent(event);

                if (event.isCancelled()) {
                    return;
                }

                player.c(entity);
                synchronizeInfiniteItemStack(player, itemInHand);
            } else if (interactionMode == EntityInteractionMode.ATTACK) {
                player.d(entity);
                synchronizeInfiniteItemStack(player, itemInHand);
            }
        }
    }

    public boolean shouldCancelStorageMinecartInteraction(boolean playerInsideVehicle, boolean targetIsStorageMinecart) {
        return interactionPolicy.shouldCancelStorageMinecartInteraction(playerInsideVehicle, targetIsStorageMinecart);
    }

    private void synchronizeInfiniteItemStack(EntityPlayer player, ItemStack itemInHand) {
        if (itemInHand != null && itemInHand.count <= -1) {
            player.updateInventory(player.activeContainer);
        }
    }
}
