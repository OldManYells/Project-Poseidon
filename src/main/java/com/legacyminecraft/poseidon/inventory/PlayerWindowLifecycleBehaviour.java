package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.ContainerChest;
import net.minecraft.server.ContainerDispenser;
import net.minecraft.server.ContainerFurnace;
import net.minecraft.server.ContainerWorkbench;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ICrafting;
import net.minecraft.server.IInventory;
import net.minecraft.server.Packet100OpenWindow;
import net.minecraft.server.Packet101CloseWindow;
import net.minecraft.server.Packet103SetSlot;
import net.minecraft.server.TileEntityDispenser;
import net.minecraft.server.TileEntityFurnace;
import org.bukkit.event.inventory.ChestOpenedEvent;

/**
 * Canonical player window/container lifecycle orchestration.
 */
public final class PlayerWindowLifecycleBehaviour {
    private static final PlayerWindowLifecycleBehaviour INSTANCE = new PlayerWindowLifecycleBehaviour();

    private PlayerWindowLifecycleBehaviour() {
    }

    public static PlayerWindowLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public int nextWindowId(int currentWindowId) {
        return currentWindowId % 100 + 1;
    }

    public void openWorkbenchWindow(EntityPlayer player, int windowId, int x, int y, int z) {
        player.netServerHandler.sendPacket(new Packet100OpenWindow(windowId, 1, "Crafting", 9));
        player.activeContainer = new ContainerWorkbench(player.inventory, player.world, x, y, z);
        player.activeContainer.windowId = windowId;
        player.activeContainer.a((ICrafting) player);
    }

    public boolean fireChestOpenedEvent(EntityPlayer player, IInventory inventory) {
        ChestOpenedEvent event = new ChestOpenedEvent((org.bukkit.entity.Player) player.getBukkitEntity(), inventory.getContents());
        player.world.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public void openChestWindow(EntityPlayer player, int windowId, IInventory inventory) {
        player.netServerHandler.sendPacket(new Packet100OpenWindow(windowId, 0, inventory.getName(), inventory.getSize()));
        player.activeContainer = new ContainerChest(player.inventory, inventory);
        player.activeContainer.windowId = windowId;
        player.activeContainer.a((ICrafting) player);
    }

    public void openFurnaceWindow(EntityPlayer player, int windowId, TileEntityFurnace furnace) {
        player.netServerHandler.sendPacket(new Packet100OpenWindow(windowId, 2, furnace.getName(), furnace.getSize()));
        player.activeContainer = new ContainerFurnace(player.inventory, furnace);
        player.activeContainer.windowId = windowId;
        player.activeContainer.a((ICrafting) player);
    }

    public void openDispenserWindow(EntityPlayer player, int windowId, TileEntityDispenser dispenser) {
        player.netServerHandler.sendPacket(new Packet100OpenWindow(windowId, 3, dispenser.getName(), dispenser.getSize()));
        player.activeContainer = new ContainerDispenser(player.inventory, dispenser);
        player.activeContainer.windowId = windowId;
        player.activeContainer.a((ICrafting) player);
    }

    public void closeActiveWindow(EntityPlayer player) {
        player.netServerHandler.sendPacket(new Packet101CloseWindow(player.activeContainer.windowId));
        resetActiveContainer(player);
    }

    public void sendCarriedItemIfNeeded(EntityPlayer player) {
        if (!shouldSendCarriedItemUpdate(player.h)) {
            return;
        }
        player.netServerHandler.sendPacket(new Packet103SetSlot(-1, -1, player.inventory.j()));
    }

    public void resetActiveContainer(EntityPlayer player) {
        player.activeContainer.a((EntityHuman) player);
        player.activeContainer = player.defaultContainer;
    }

    public boolean shouldSendCarriedItemUpdate(boolean suppressWindowUpdates) {
        return !suppressWindowUpdates;
    }
}
