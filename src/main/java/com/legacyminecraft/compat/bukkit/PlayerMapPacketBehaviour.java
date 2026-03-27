package com.legacyminecraft.compat.bukkit;


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

    public List<Object> createMapPackets(short mapId, Object renderData) {
        List<Object> packets = new ArrayList<Object>(MAP_WIDTH);
        byte[] buffer = BridgeReflection.cast(BridgeReflection.getField(renderData, "buffer"));

        for (int x = 0; x < MAP_WIDTH; ++x) {
            byte[] bytes = new byte[MAP_PACKET_LENGTH];
            bytes[1] = (byte) x;
            for (int y = 0; y < MAP_HEIGHT; ++y) {
                bytes[y + 3] = buffer[y * MAP_WIDTH + x];
            }
            packets.add(new Packet131((short) Material.MAP.getId(), mapId, bytes));
        }

        return packets;
    }
}
