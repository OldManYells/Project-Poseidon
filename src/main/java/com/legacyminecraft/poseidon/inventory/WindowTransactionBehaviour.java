package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Packet102WindowClick;
import net.minecraft.server.Packet106Transaction;
import net.minecraft.server.Slot;

import java.util.ArrayList;
import java.util.Map;

/**
 * Canonical handler for container window click/transaction packet flows.
 */
public final class WindowTransactionBehaviour {
    private static final WindowTransactionBehaviour INSTANCE = new WindowTransactionBehaviour();

    private WindowTransactionBehaviour() {
    }

    public static WindowTransactionBehaviour getInstance() {
        return INSTANCE;
    }

    public void handleCloseWindow(EntityPlayer player) {
        if (player.dead) {
            return;
        }
        player.A();
    }

    public void handleWindowClick(EntityPlayer player, Packet102WindowClick packet102windowclick, Map pendingTransactions) {
        if (player.dead) {
            return;
        }

        if (player.activeContainer.windowId == packet102windowclick.a && player.activeContainer.c(player)) {
            ItemStack itemstack = player.activeContainer.a(packet102windowclick.b, packet102windowclick.c, packet102windowclick.f, player);

            if (ItemStack.equals(packet102windowclick.e, itemstack)) {
                player.netServerHandler.sendPacket(new Packet106Transaction(packet102windowclick.a, packet102windowclick.d, true));
                player.h = true;
                player.activeContainer.a();
                player.z();
                player.h = false;
            } else {
                pendingTransactions.put(Integer.valueOf(player.activeContainer.windowId), Short.valueOf(packet102windowclick.d));
                player.netServerHandler.sendPacket(new Packet106Transaction(packet102windowclick.a, packet102windowclick.d, false));
                player.activeContainer.a(player, false);
                ArrayList arraylist = new ArrayList();

                for (int i = 0; i < player.activeContainer.e.size(); ++i) {
                    arraylist.add(((Slot) player.activeContainer.e.get(i)).getItem());
                }

                player.a(player.activeContainer, arraylist);
            }
        }
    }

    public void handleTransactionConfirmation(EntityPlayer player, Packet106Transaction packet106transaction, Map pendingTransactions) {
        if (player.dead) {
            return;
        }

        Short oshort = (Short) pendingTransactions.get(Integer.valueOf(player.activeContainer.windowId));

        if (oshort != null && packet106transaction.b == oshort.shortValue() && player.activeContainer.windowId == packet106transaction.a && !player.activeContainer.c(player)) {
            player.activeContainer.a(player, true);
        }
    }
}
