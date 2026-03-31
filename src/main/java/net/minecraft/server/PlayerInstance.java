package net.minecraft.server;

import org.bukkit.craftbukkit.entity.EntityPlayer;
import org.bukkit.craftbukkit.network.*;
import org.bukkit.craftbukkit.server.ChunkCoordIntPair;
import org.bukkit.craftbukkit.world.WorldServer;

import java.util.ArrayList;
import java.util.List;

class PlayerInstance {

    private List players;
    private int chunkX;
    private int chunkZ;
    private ChunkCoordIntPair location;
    private short[] dirtyBlocks;
    private int dirtyCount;
    private int minDirtyX;
    private int maxDirtyX;
    private int minDirtyY;
    private int maxDirtyY;
    private int minDirtyZ;
    private int maxDirtyZ;

    final PlayerManager playerManager;

    public PlayerInstance(PlayerManager playerManager, int chunkX, int chunkZ) {
        this.playerManager = playerManager;
        this.players = new ArrayList();
        this.dirtyBlocks = new short[10];
        this.dirtyCount = 0;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.location = new ChunkCoordIntPair(chunkX, chunkZ);
        playerManager.getWorldServer().chunkProviderServer.getChunkAt(chunkX, chunkZ);
    }

    public void addPlayer(EntityPlayer player) {
        if (this.players.contains(player)) {
            throw new IllegalStateException("Failed to add player. " + player + " already is in chunk " + this.chunkX + ", " + this.chunkZ);
        }

        if (player.playerChunkCoordIntPairs.add(this.location)) {
            player.netServerHandler.sendPacket(new Packet50PreChunk(this.location.x, this.location.z, true));
        }

        this.players.add(player);
        player.chunkCoordIntPairQueue.add(this.location);
    }

    public void removePlayer(EntityPlayer player) {
        if (this.players.contains(player)) {
            this.players.remove(player);
            if (this.players.size() == 0) {
                long chunkKey = (long) this.chunkX + 2147483647L | (long) this.chunkZ + 2147483647L << 32;

                PlayerManager.getPlayerInstances(this.playerManager).remove(chunkKey);
                if (this.dirtyCount > 0) {
                    PlayerManager.getDirtyInstances(this.playerManager).remove(this);
                }

                this.playerManager.getWorldServer().chunkProviderServer.queueUnload(this.chunkX, this.chunkZ);
            }

            player.chunkCoordIntPairQueue.remove(this.location);
            if (player.playerChunkCoordIntPairs.remove(this.location)) {
                player.netServerHandler.sendPacket(new Packet50PreChunk(this.chunkX, this.chunkZ, false));
            }
        }
    }

    public void flagDirty(int x, int y, int z) {
        if (this.dirtyCount == 0) {
            PlayerManager.getDirtyInstances(this.playerManager).add(this);
            this.minDirtyX = this.maxDirtyX = x;
            this.minDirtyY = this.maxDirtyY = y;
            this.minDirtyZ = this.maxDirtyZ = z;
        }

        if (this.minDirtyX > x) {
            this.minDirtyX = x;
        }

        if (this.maxDirtyX < x) {
            this.maxDirtyX = x;
        }

        if (this.minDirtyY > y) {
            this.minDirtyY = y;
        }

        if (this.maxDirtyY < y) {
            this.maxDirtyY = y;
        }

        if (this.minDirtyZ > z) {
            this.minDirtyZ = z;
        }

        if (this.maxDirtyZ < z) {
            this.maxDirtyZ = z;
        }

        if (this.dirtyCount < 10) {
            short packed = (short) (x << 12 | z << 8 | y);

            for (int index = 0; index < this.dirtyCount; ++index) {
                if (this.dirtyBlocks[index] == packed) {
                    return;
                }
            }

            this.dirtyBlocks[this.dirtyCount++] = packed;
        }
    }

    public void sendAll(Packet packet) {
        for (int index = 0; index < this.players.size(); ++index) {
            EntityPlayer player = (EntityPlayer) this.players.get(index);

            if (player.playerChunkCoordIntPairs.contains(this.location)) {
                player.netServerHandler.sendPacket(packet);
            }
        }
    }

    public void sendChanges() {
        WorldServer worldServer = this.playerManager.getWorldServer();

        if (this.dirtyCount != 0) {
            int blockX;
            int blockY;
            int blockZ;

            if (this.dirtyCount == 1) {
                blockX = this.chunkX * 16 + this.minDirtyX;
                blockY = this.minDirtyY;
                blockZ = this.chunkZ * 16 + this.minDirtyZ;
                this.sendAll(new Packet53BlockChange(blockX, blockY, blockZ, worldServer));
                if (CraftBlock.isTileEntity[worldServer.getTypeId(blockX, blockY, blockZ)]) {
                    this.sendTileEntity(worldServer.getTileEntity(blockX, blockY, blockZ));
                }
            } else {
                int width;

                if (this.dirtyCount == 10) {
                    this.minDirtyY = this.minDirtyY / 2 * 2;
                    this.maxDirtyY = (this.maxDirtyY / 2 + 1) * 2;
                    blockX = this.minDirtyX + this.chunkX * 16;
                    blockY = this.minDirtyY;
                    blockZ = this.minDirtyZ + this.chunkZ * 16;
                    width = this.maxDirtyX - this.minDirtyX + 1;
                    int height = this.maxDirtyY - this.minDirtyY + 2;
                    int depth = this.maxDirtyZ - this.minDirtyZ + 1;

                    this.sendAll(new Packet51MapChunk(blockX, blockY, blockZ, width, height, depth, worldServer));
                    List tileEntities = worldServer.getTileEntities(blockX, blockY, blockZ, blockX + width, blockY + height, blockZ + depth);

                    for (int tileEntityIndex = 0; tileEntityIndex < tileEntities.size(); ++tileEntityIndex) {
                        this.sendTileEntity((TileEntity) tileEntities.get(tileEntityIndex));
                    }
                } else {
                    this.sendAll(new Packet52MultiBlockChange(this.chunkX, this.chunkZ, this.dirtyBlocks, this.dirtyCount, worldServer));

                    for (blockX = 0; blockX < this.dirtyCount; ++blockX) {
                        blockY = this.chunkX * 16 + (this.dirtyBlocks[blockX] >> 12 & 15);
                        blockZ = this.dirtyBlocks[blockX] & 255;
                        width = this.chunkZ * 16 + (this.dirtyBlocks[blockX] >> 8 & 15);

                        if (CraftBlock.isTileEntity[worldServer.getTypeId(blockY, blockZ, width)]) {
                            this.sendTileEntity(worldServer.getTileEntity(blockY, blockZ, width));
                        }
                    }
                }
            }

            this.dirtyCount = 0;
        }
    }

    private void sendTileEntity(TileEntity tileEntity) {
        if (tileEntity != null) {
            Packet packet = tileEntity.getUpdatePacket();
            if (packet != null) {
                this.sendAll(packet);
            }
        }
    }

    static ChunkCoordIntPair getLocation(PlayerInstance playerInstance) {
        return playerInstance.location;
    }

    static List getPlayers(PlayerInstance playerInstance) {
        return playerInstance.players;
    }

    @Deprecated
    public void a(EntityPlayer player) {
        this.addPlayer(player);
    }

    @Deprecated
    public void b(EntityPlayer player) {
        this.removePlayer(player);
    }

    @Deprecated
    public void a(int x, int y, int z) {
        this.flagDirty(x, y, z);
    }

    @Deprecated
    public void a() {
        this.sendChanges();
    }

    @Deprecated
    static ChunkCoordIntPair a(PlayerInstance playerInstance) {
        return getLocation(playerInstance);
    }

    @Deprecated
    static List b(PlayerInstance playerInstance) {
        return getPlayers(playerInstance);
    }
}
