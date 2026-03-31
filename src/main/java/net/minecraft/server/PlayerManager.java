package net.minecraft.server;

import org.bukkit.craftbukkit.entity.EntityPlayer;
import org.bukkit.craftbukkit.server.ChunkCoordIntPair;
import org.bukkit.craftbukkit.world.WorldServer;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {

    public List managedPlayers = new ArrayList();
    private PlayerList playerInstances = new PlayerList();
    private List dirtyInstances = new ArrayList();
    private MinecraftServer server;
    private int dimension;
    private int viewRadius;
    private final int[][] spiralDirections = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

    public PlayerManager(MinecraftServer server, int dimension, int viewRadius) {
        if (viewRadius > 15) {
            throw new IllegalArgumentException("Too big view radius!");
        } else if (viewRadius < 3) {
            throw new IllegalArgumentException("Too small view radius!");
        } else {
            this.viewRadius = viewRadius;
            this.server = server;
            this.dimension = dimension;
        }
    }

    public WorldServer getWorldServer() {
        return this.server.getWorldServer(this.dimension);
    }

    public void flush() {
        for (int index = 0; index < this.dirtyInstances.size(); ++index) {
            ((PlayerInstance) this.dirtyInstances.get(index)).sendChanges();
        }

        this.dirtyInstances.clear();
    }

    private PlayerInstance getPlayerInstance(int chunkX, int chunkZ, boolean createIfMissing) {
        long chunkKey = (long) chunkX + 2147483647L | (long) chunkZ + 2147483647L << 32;
        PlayerInstance playerInstance = (PlayerInstance) this.playerInstances.get(chunkKey);

        if (playerInstance == null && createIfMissing) {
            playerInstance = new PlayerInstance(this, chunkX, chunkZ);
            this.playerInstances.put(chunkKey, playerInstance);
        }

        return playerInstance;
    }

    public void flagDirty(int x, int y, int z) {
        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        PlayerInstance playerInstance = this.getPlayerInstance(chunkX, chunkZ, false);

        if (playerInstance != null) {
            playerInstance.flagDirty(x & 15, y, z & 15);
        }
    }

    public void addPlayer(EntityPlayer player) {
        int chunkX = (int) player.locX >> 4;
        int chunkZ = (int) player.locZ >> 4;

        player.d = (int) player.locX;
        player.e = player.locZ;
        int directionIndex = 0;
        int radius = this.viewRadius;
        int offsetX = 0;
        int offsetZ = 0;

        this.getPlayerInstance(chunkX, chunkZ, true).addPlayer(player);

        int ring;
        for (ring = 1; ring <= radius * 2; ++ring) {
            for (int side = 0; side < 2; ++side) {
                int[] direction = this.spiralDirections[directionIndex++ % 4];

                for (int step = 0; step < ring; ++step) {
                    offsetX += direction[0];
                    offsetZ += direction[1];
                    this.getPlayerInstance(chunkX + offsetX, chunkZ + offsetZ, true).addPlayer(player);
                }
            }
        }

        directionIndex %= 4;

        for (ring = 0; ring < radius * 2; ++ring) {
            offsetX += this.spiralDirections[directionIndex][0];
            offsetZ += this.spiralDirections[directionIndex][1];
            this.getPlayerInstance(chunkX + offsetX, chunkZ + offsetZ, true).addPlayer(player);
        }

        this.managedPlayers.add(player);
    }

    public void removePlayer(EntityPlayer player) {
        int previousChunkX = (int) player.d >> 4;
        int previousChunkZ = (int) player.e >> 4;

        for (int chunkX = previousChunkX - this.viewRadius; chunkX <= previousChunkX + this.viewRadius; ++chunkX) {
            for (int chunkZ = previousChunkZ - this.viewRadius; chunkZ <= previousChunkZ + this.viewRadius; ++chunkZ) {
                PlayerInstance playerInstance = this.getPlayerInstance(chunkX, chunkZ, false);

                if (playerInstance != null) {
                    playerInstance.removePlayer(player);
                }
            }
        }

        this.managedPlayers.remove(player);
    }

    private boolean isWithinViewRadius(int chunkX, int chunkZ, int centerX, int centerZ) {
        int deltaX = chunkX - centerX;
        int deltaZ = chunkZ - centerZ;
        return deltaX >= -this.viewRadius && deltaX <= this.viewRadius ? deltaZ >= -this.viewRadius && deltaZ <= this.viewRadius : false;
    }

    public void movePlayer(EntityPlayer player) {
        int currentChunkX = (int) player.locX >> 4;
        int currentChunkZ = (int) player.locZ >> 4;
        double deltaX = player.d - player.locX;
        double deltaZ = player.e - player.locZ;
        double distanceSquared = deltaX * deltaX + deltaZ * deltaZ;

        if (distanceSquared >= 64.0D) {
            int previousChunkX = (int) player.d >> 4;
            int previousChunkZ = (int) player.e >> 4;
            int movedChunkX = currentChunkX - previousChunkX;
            int movedChunkZ = currentChunkZ - previousChunkZ;

            if (movedChunkX != 0 || movedChunkZ != 0) {
                for (int chunkX = currentChunkX - this.viewRadius; chunkX <= currentChunkX + this.viewRadius; ++chunkX) {
                    for (int chunkZ = currentChunkZ - this.viewRadius; chunkZ <= currentChunkZ + this.viewRadius; ++chunkZ) {
                        if (!this.isWithinViewRadius(chunkX, chunkZ, previousChunkX, previousChunkZ)) {
                            this.getPlayerInstance(chunkX, chunkZ, true).addPlayer(player);
                        }

                        if (!this.isWithinViewRadius(chunkX - movedChunkX, chunkZ - movedChunkZ, currentChunkX, currentChunkZ)) {
                            PlayerInstance playerInstance = this.getPlayerInstance(chunkX - movedChunkX, chunkZ - movedChunkZ, false);
                            if (playerInstance != null) {
                                playerInstance.removePlayer(player);
                            }
                        }
                    }
                }

                player.d = (int) player.locX;
                player.e = player.locZ;

                if (movedChunkX > 1 || movedChunkX < -1 || movedChunkZ > 1 || movedChunkZ < -1) {
                    final int x = currentChunkX;
                    final int z = currentChunkZ;
                    List chunksToSend = player.chunkCoordIntPairQueue;

                    java.util.Collections.sort(chunksToSend, new java.util.Comparator() {
                        public int compare(Object first, Object second) {
                            ChunkCoordIntPair a = (ChunkCoordIntPair) first;
                            ChunkCoordIntPair b = (ChunkCoordIntPair) second;
                            return Math.max(Math.abs(a.x - x), Math.abs(a.z - z)) - Math.max(Math.abs(b.x - x), Math.abs(b.z - z));
                        }
                    });
                }
            }
        }
    }

    public boolean isPlayerWatchingChunk(EntityPlayer player, int chunkX, int chunkZ) {
        PlayerInstance playerInstance = this.getPlayerInstance(chunkX, chunkZ, false);
        return playerInstance == null ? false : PlayerInstance.getPlayers(playerInstance).contains(player) && !player.chunkCoordIntPairQueue.contains(PlayerInstance.getLocation(playerInstance));
    }

    public int getFurthestViewableBlock() {
        return this.viewRadius * 16 - 16;
    }

    static PlayerList getPlayerInstances(PlayerManager playerManager) {
        return playerManager.playerInstances;
    }

    static List getDirtyInstances(PlayerManager playerManager) {
        return playerManager.dirtyInstances;
    }

    @Deprecated
    public WorldServer a() {
        return this.getWorldServer();
    }

    @Deprecated
    private PlayerInstance a(int chunkX, int chunkZ, boolean createIfMissing) {
        return this.getPlayerInstance(chunkX, chunkZ, createIfMissing);
    }

    @Deprecated
    private boolean a(int chunkX, int chunkZ, int centerX, int centerZ) {
        return this.isWithinViewRadius(chunkX, chunkZ, centerX, centerZ);
    }

    @Deprecated
    public boolean a(EntityPlayer player, int chunkX, int chunkZ) {
        return this.isPlayerWatchingChunk(player, chunkX, chunkZ);
    }

    @Deprecated
    static PlayerList a(PlayerManager playerManager) {
        return getPlayerInstances(playerManager);
    }

    @Deprecated
    static List b(PlayerManager playerManager) {
        return getDirtyInstances(playerManager);
    }
}
