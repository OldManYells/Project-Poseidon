package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.InventoryPlayer;
import net.minecraft.server.Packet16BlockItemSwitch;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemHeldEvent;

/**
 * Canonical handler for hotbar item selection packets.
 */
public final class HotbarSelectionBehaviour {
    private static final HotbarSelectionBehaviour INSTANCE = new HotbarSelectionBehaviour();

    private HotbarSelectionBehaviour() {
    }

    public static HotbarSelectionBehaviour getInstance() {
        return INSTANCE;
    }

    public SwitchResult handleSwitch(Server server, EntityPlayer player, Packet16BlockItemSwitch packet16blockitemswitch) {
        if (player.dead) {
            return SwitchResult.IGNORED_PLAYER_DEAD;
        }

        if (!isValidSelectionIndex(packet16blockitemswitch.itemInHandIndex)) {
            return SwitchResult.INVALID_SELECTION;
        }

        PlayerItemHeldEvent event = new PlayerItemHeldEvent(
                (Player) player.getBukkitEntity(),
                player.inventory.itemInHandIndex,
                packet16blockitemswitch.itemInHandIndex
        );
        server.getPluginManager().callEvent(event);

        player.inventory.itemInHandIndex = packet16blockitemswitch.itemInHandIndex;
        return SwitchResult.APPLIED;
    }

    public boolean isValidSelectionIndex(int index) {
        return index >= 0 && index <= InventoryPlayer.e();
    }

    public enum SwitchResult {
        APPLIED,
        INVALID_SELECTION,
        IGNORED_PLAYER_DEAD
    }
}
