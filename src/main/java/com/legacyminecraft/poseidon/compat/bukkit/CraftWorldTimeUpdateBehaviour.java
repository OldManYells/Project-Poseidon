package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Packet4UpdateTime;
import net.minecraft.server.WorldServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Canonical behavior for CraftWorld full-time update and player sync packet fan-out.
 */
public final class CraftWorldTimeUpdateBehaviour {
    private static final CraftWorldTimeUpdateBehaviour INSTANCE = new CraftWorldTimeUpdateBehaviour();

    private CraftWorldTimeUpdateBehaviour() {
    }

    public static CraftWorldTimeUpdateBehaviour getInstance() {
        return INSTANCE;
    }

    public void setFullTime(WorldServer worldServer, List<Player> players, long time) {
        worldServer.setTime(time);
        for (Player player : players) {
            CraftPlayer craftPlayer = (CraftPlayer) player;
            craftPlayer.getHandle().netServerHandler
                    .sendPacket(new Packet4UpdateTime(craftPlayer.getHandle().getPlayerTime()));
        }
    }
}
