package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Packet61;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Canonical behavior for CraftWorld effect packet broadcast orchestration.
 */
public final class CraftWorldEffectBroadcastBehaviour {
    private static final CraftWorldEffectBroadcastBehaviour INSTANCE = new CraftWorldEffectBroadcastBehaviour();

    private CraftWorldEffectBroadcastBehaviour() {
    }

    public static CraftWorldEffectBroadcastBehaviour getInstance() {
        return INSTANCE;
    }

    public void playEffect(Location location, Effect effect, int data, int radius, List<Player> players) {
        Packet61 packet = new Packet61(
                effect.getId(),
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                data
        );

        for (Player player : players) {
            int distance = (int) player.getLocation().distance(location);
            if (distance <= radius) {
                ((CraftPlayer) player).getHandle().netServerHandler.sendPacket(packet);
            }
        }
    }
}
