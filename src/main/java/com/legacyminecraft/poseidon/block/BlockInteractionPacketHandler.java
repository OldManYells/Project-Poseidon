package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.compat.bukkit.PlayerInteractEventBridgeBehaviour;
import net.minecraft.server.ChunkCoordinates;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Packet103SetSlot;
import net.minecraft.server.Packet14BlockDig;
import net.minecraft.server.Packet15Place;
import net.minecraft.server.Packet53BlockChange;
import net.minecraft.server.Slot;
import net.minecraft.server.WorldServer;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.logging.Logger;

/**
 * Canonical handler for block interaction packets.
 */
public final class BlockInteractionPacketHandler {
    private static final BlockInteractionPacketHandler INSTANCE = new BlockInteractionPacketHandler();
    private final PlayerInteractEventBridgeBehaviour playerInteractEventBridge = PlayerInteractEventBridgeBehaviour.getInstance();

    private BlockInteractionPacketHandler() {
    }

    public static BlockInteractionPacketHandler getInstance() {
        return INSTANCE;
    }

    public void handleBlockDig(
            Server server,
            MinecraftServer minecraftServer,
            EntityPlayer player,
            Packet14BlockDig digPacket,
            BlockInteractionSessionState state,
            Logger logger,
            int currentTick
    ) {
        if (player.dead) {
            return;
        }

        WorldServer worldserver = minecraftServer.getWorldServer(player.dimension);

        if (digPacket.e == 4) {
            if (state.getLastDropTick() != currentTick) {
                state.setDropCount(0);
                state.setLastDropTick(currentTick);
            } else {
                state.setDropCount(state.getDropCount() + 1);
                if (state.getDropCount() >= 20) {
                    logger.warning(player.name + " dropped their items too quickly!");
                    player.netServerHandler.disconnect("You dropped your items too quickly (Hacking?)");
                }
            }
            player.F();
            return;
        }

        boolean flag = worldserver.weirdIsOpCache = worldserver.dimension != 0 || minecraftServer.serverConfigurationManager.isOp(player.name);
        boolean requiresDistanceCheck = requiresDigDistanceCheck(digPacket.e);

        int blockX = digPacket.a;
        int blockY = digPacket.b;
        int blockZ = digPacket.c;

        if (requiresDistanceCheck) {
            double deltaX = player.locX - (blockX + 0.5D);
            double deltaY = player.locY - (blockY + 0.5D);
            double deltaZ = player.locZ - (blockZ + 0.5D);
            double distanceSquared = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;

            if (distanceSquared > 36.0D) {
                return;
            }
        }

        ChunkCoordinates spawnCoordinates = worldserver.getSpawn();
        int deltaSpawnX = (int) MathHelper.abs((float) (blockX - spawnCoordinates.x));
        int deltaSpawnZ = (int) MathHelper.abs((float) (blockZ - spawnCoordinates.z));

        if (deltaSpawnX > deltaSpawnZ) {
            deltaSpawnZ = deltaSpawnX;
        }

        if (digPacket.e == 0) {
            if (deltaSpawnZ < server.getSpawnRadius() && !flag) {
                player.netServerHandler.sendPacket(new Packet53BlockChange(blockX, blockY, blockZ, worldserver));
            } else {
                player.itemInWorldManager.dig(blockX, blockY, blockZ, digPacket.face);
            }
        } else if (digPacket.e == 2) {
            player.itemInWorldManager.a(blockX, blockY, blockZ);
            if (worldserver.getTypeId(blockX, blockY, blockZ) != 0) {
                player.netServerHandler.sendPacket(new Packet53BlockChange(blockX, blockY, blockZ, worldserver));
            }
        } else if (digPacket.e == 3) {
            double deltaX = player.locX - (blockX + 0.5D);
            double deltaY = player.locY - (blockY + 0.5D);
            double deltaZ = player.locZ - (blockZ + 0.5D);
            double distanceSquared = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;

            if (distanceSquared < 256.0D) {
                player.netServerHandler.sendPacket(new Packet53BlockChange(blockX, blockY, blockZ, worldserver));
            }
        }

        worldserver.weirdIsOpCache = false;
    }

    public void handleBlockPlace(
            MinecraftServer minecraftServer,
            EntityPlayer player,
            Packet15Place packet15place,
            BlockInteractionSessionState state,
            int placeDistanceSquared
    ) {
        WorldServer worldserver = minecraftServer.getWorldServer(player.dimension);
        if (player.dead) {
            return;
        }

        if (packet15place.face == 255) {
            if (isDuplicateRightClickPacket(packet15place, state)) {
                state.setLastPacketTimestamp(null);
                return;
            }
        } else {
            state.setLastMaterial(packet15place.itemstack == null ? -1 : packet15place.itemstack.id);
            state.setLastPacketTimestamp(packet15place.timestamp);
        }

        boolean always = false;

        ItemStack itemstack = player.inventory.getItemInHand();
        boolean flag = worldserver.weirdIsOpCache = worldserver.dimension != 0 || minecraftServer.serverConfigurationManager.isOp(player.name);

        if (packet15place.face == 255) {
            if (itemstack == null) {
                return;
            }

            int itemstackAmount = itemstack.count;
            PlayerInteractEvent event = playerInteractEventBridge.callPlayerInteract(player, Action.RIGHT_CLICK_AIR, itemstack);
            if (event.useItemInHand() != Event.Result.DENY) {
                player.itemInWorldManager.useItem(player, player.world, itemstack);
            }

            always = (itemstack.count != itemstackAmount);
        } else {
            int i = packet15place.a;
            int j = packet15place.b;
            int k = packet15place.c;
            int l = packet15place.face;
            ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
            int i1 = (int) MathHelper.abs((float) (i - chunkcoordinates.x));
            int j1 = (int) MathHelper.abs((float) (k - chunkcoordinates.z));

            if (i1 > j1) {
                j1 = i1;
            }

            Location eyeLoc = ((Player) player.getBukkitEntity()).getEyeLocation();
            if (!isWithinPlaceDistanceSquared(eyeLoc, i, j, k, placeDistanceSquared)) {
                return;
            }

            // Spawn protection is handled in ItemBlock/CraftEventFactory, matching legacy behavior.
            flag = true;
            if (j1 > 16 || flag) {
                player.itemInWorldManager.interact(player, worldserver, itemstack, i, j, k, l);
            }

            player.netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, worldserver));
            if (l == 0) {
                --j;
            }

            if (l == 1) {
                ++j;
            }

            if (l == 2) {
                --k;
            }

            if (l == 3) {
                ++k;
            }

            if (l == 4) {
                --i;
            }

            if (l == 5) {
                ++i;
            }

            player.netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, worldserver));
        }

        itemstack = player.inventory.getItemInHand();
        if (itemstack != null && itemstack.count == 0) {
            player.inventory.items[player.inventory.itemInHandIndex] = null;
        }

        player.h = true;
        player.inventory.items[player.inventory.itemInHandIndex] = ItemStack.b(player.inventory.items[player.inventory.itemInHandIndex]);
        Slot slot = player.activeContainer.a(player.inventory, player.inventory.itemInHandIndex);

        player.activeContainer.a();
        player.h = false;
        if (!ItemStack.equals(player.inventory.getItemInHand(), packet15place.itemstack) || always) {
            player.netServerHandler.sendPacket(new Packet103SetSlot(player.activeContainer.windowId, slot.a, player.inventory.getItemInHand()));
        }

        worldserver.weirdIsOpCache = false;
    }

    public boolean requiresDigDistanceCheck(int digStatus) {
        return digStatus == 0 || digStatus == 2;
    }

    public boolean isDuplicateRightClickPacket(Packet15Place packet15place, BlockInteractionSessionState state) {
        return packet15place.itemstack != null
                && packet15place.itemstack.id == state.getLastMaterial()
                && state.getLastPacketTimestamp() != null
                && packet15place.timestamp - state.getLastPacketTimestamp() < 100;
    }

    public boolean isWithinPlaceDistanceSquared(Location eyeLoc, int x, int y, int z, int maxDistanceSquared) {
        return Math.pow(eyeLoc.getX() - x, 2)
                + Math.pow(eyeLoc.getY() - y, 2)
                + Math.pow(eyeLoc.getZ() - z, 2) <= maxDistanceSquared;
    }
}
