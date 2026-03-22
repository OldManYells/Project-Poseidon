package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Packet131;
import net.minecraft.server.Packet6SpawnPosition;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.craftbukkit.map.RenderData;

import java.util.ArrayList;
import java.util.List;

/**
 * Canonical behaviour for CraftPlayer compass/map packet construction.
 */
public final class PlayerMapPacketBehaviour {
    private static final PlayerMapPacketBehaviour INSTANCE = new PlayerMapPacketBehaviour();
    private static final int MAP_WIDTH = 128;
    private static final int MAP_HEIGHT = 128;
    private static final int MAP_PACKET_LENGTH = 131;

    private PlayerMapPacketBehaviour() {
    }

    public static PlayerMapPacketBehaviour getInstance() {
        return INSTANCE;
    }

    public Packet6SpawnPosition createCompassPacket(Location location) {
        return new Packet6SpawnPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public List<Packet131> createMapPackets(short mapId, RenderData renderData) {
        List<Packet131> packets = new ArrayList<Packet131>(MAP_WIDTH);

        for (int x = 0; x < MAP_WIDTH; ++x) {
            byte[] bytes = new byte[MAP_PACKET_LENGTH];
            bytes[1] = (byte) x;
            for (int y = 0; y < MAP_HEIGHT; ++y) {
                bytes[y + 3] = renderData.buffer[y * MAP_WIDTH + x];
            }
            packets.add(new Packet131((short) Material.MAP.getId(), mapId, bytes));
        }

        return packets;
    }
}
