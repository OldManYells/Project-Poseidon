package com.legacyminecraft.poseidon.block;

import net.minecraft.server.ChunkPosition;
import net.minecraft.server.World;

import java.util.List;

/**
 * Canonical behaviour for minecart track connection layout and neighbor checks.
 */
public final class MinecartTrackConnectionLayoutBehaviour {
    private static final MinecartTrackConnectionLayoutBehaviour INSTANCE = new MinecartTrackConnectionLayoutBehaviour();

    private MinecartTrackConnectionLayoutBehaviour() {
    }

    public static MinecartTrackConnectionLayoutBehaviour getInstance() {
        return INSTANCE;
    }

    public void populateConnections(List connections, int trackData, int x, int y, int z) {
        connections.clear();
        if (trackData == 0) {
            connections.add(new ChunkPosition(x, y, z - 1));
            connections.add(new ChunkPosition(x, y, z + 1));
        } else if (trackData == 1) {
            connections.add(new ChunkPosition(x - 1, y, z));
            connections.add(new ChunkPosition(x + 1, y, z));
        } else if (trackData == 2) {
            connections.add(new ChunkPosition(x - 1, y, z));
            connections.add(new ChunkPosition(x + 1, y + 1, z));
        } else if (trackData == 3) {
            connections.add(new ChunkPosition(x - 1, y + 1, z));
            connections.add(new ChunkPosition(x + 1, y, z));
        } else if (trackData == 4) {
            connections.add(new ChunkPosition(x, y + 1, z - 1));
            connections.add(new ChunkPosition(x, y, z + 1));
        } else if (trackData == 5) {
            connections.add(new ChunkPosition(x, y, z - 1));
            connections.add(new ChunkPosition(x, y + 1, z + 1));
        } else if (trackData == 6) {
            connections.add(new ChunkPosition(x + 1, y, z));
            connections.add(new ChunkPosition(x, y, z + 1));
        } else if (trackData == 7) {
            connections.add(new ChunkPosition(x - 1, y, z));
            connections.add(new ChunkPosition(x, y, z + 1));
        } else if (trackData == 8) {
            connections.add(new ChunkPosition(x - 1, y, z));
            connections.add(new ChunkPosition(x, y, z - 1));
        } else if (trackData == 9) {
            connections.add(new ChunkPosition(x + 1, y, z));
            connections.add(new ChunkPosition(x, y, z - 1));
        }
    }

    public boolean hasAdjacentTrack(World world, int x, int y, int z) {
        return net.minecraft.server.BlockMinecartTrack.g(world, x, y, z)
                || net.minecraft.server.BlockMinecartTrack.g(world, x, y + 1, z)
                || net.minecraft.server.BlockMinecartTrack.g(world, x, y - 1, z);
    }

    public int countAdjacentTracks(World world, int x, int y, int z) {
        int adjacentCount = 0;

        if (hasAdjacentTrack(world, x, y, z - 1)) {
            ++adjacentCount;
        }

        if (hasAdjacentTrack(world, x, y, z + 1)) {
            ++adjacentCount;
        }

        if (hasAdjacentTrack(world, x - 1, y, z)) {
            ++adjacentCount;
        }

        if (hasAdjacentTrack(world, x + 1, y, z)) {
            ++adjacentCount;
        }

        return adjacentCount;
    }

    public Integer resolveTrackY(World world, int x, int y, int z) {
        if (net.minecraft.server.BlockMinecartTrack.g(world, x, y, z)) {
            return Integer.valueOf(y);
        }

        if (net.minecraft.server.BlockMinecartTrack.g(world, x, y + 1, z)) {
            return Integer.valueOf(y + 1);
        }

        if (net.minecraft.server.BlockMinecartTrack.g(world, x, y - 1, z)) {
            return Integer.valueOf(y - 1);
        }

        return null;
    }

    public boolean containsConnectionAt(List connections, int x, int z) {
        for (int index = 0; index < connections.size(); ++index) {
            ChunkPosition connection = (ChunkPosition) connections.get(index);

            if (connection.x == x && connection.z == z) {
                return true;
            }
        }

        return false;
    }

    public boolean canAcceptConnection(boolean alreadyConnected, int connectionCount) {
        if (alreadyConnected) {
            return true;
        }

        return connectionCount != 2;
    }

    public void pruneDisconnectedConnections(List connections, ConnectionResolver connectionResolver) {
        for (int index = 0; index < connections.size(); ++index) {
            ChunkPosition connection = (ChunkPosition) connections.get(index);
            ChunkPosition resolvedConnection = connectionResolver.resolve(connection);

            if (resolvedConnection != null) {
                connections.set(index, resolvedConnection);
            } else {
                connections.remove(index--);
            }
        }
    }

    public interface ConnectionResolver {
        ChunkPosition resolve(ChunkPosition connection);
    }
}
