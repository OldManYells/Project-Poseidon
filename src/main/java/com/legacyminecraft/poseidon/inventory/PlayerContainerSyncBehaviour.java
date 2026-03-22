package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.Container;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Packet103SetSlot;
import net.minecraft.server.Packet104WindowItems;
import net.minecraft.server.Packet105CraftProgressBar;

import java.util.List;

/**
 * Canonical container callback packet synchronization for EntityPlayer wrappers.
 */
public final class PlayerContainerSyncBehaviour {
    private static final PlayerContainerSyncBehaviour INSTANCE = new PlayerContainerSyncBehaviour();

    private PlayerContainerSyncBehaviour() {
    }

    public static PlayerContainerSyncBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldSendSlotUpdate(boolean resultSlot, boolean suppressWindowUpdates) {
        return !resultSlot && !suppressWindowUpdates;
    }

    public void sendSlotUpdate(EntityPlayer player, Container container, int slotIndex, ItemStack itemStack) {
        player.netServerHandler.sendPacket(new Packet103SetSlot(container.windowId, slotIndex, itemStack));
    }

    public void sendWindowItems(EntityPlayer player, Container container, List itemList) {
        player.netServerHandler.sendPacket(new Packet104WindowItems(container.windowId, itemList));
        sendCarriedItem(player);
    }

    public void sendProgressUpdate(EntityPlayer player, Container container, int progressBarId, int value) {
        player.netServerHandler.sendPacket(new Packet105CraftProgressBar(container.windowId, progressBarId, value));
    }

    private void sendCarriedItem(EntityPlayer player) {
        player.netServerHandler.sendPacket(new Packet103SetSlot(-1, -1, player.inventory.j()));
    }
}
