package net.minecraft.server;

import com.legacyminecraft.poseidon.world.player.PlayerChunkDirtyBlockBehaviour;
import com.legacyminecraft.poseidon.world.player.PlayerChunkDirtyFlushBehaviour;
import com.legacyminecraft.poseidon.world.player.PlayerChunkMembershipBehaviour;
import com.legacyminecraft.poseidon.world.player.PlayerChunkPacketDispatchBehaviour;
import com.legacyminecraft.poseidon.world.player.PlayerChunkTileEntityPacketBehaviour;

import java.util.ArrayList;
import java.util.List;

class PlayerInstance {
    private static final PlayerChunkDirtyBlockBehaviour PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR = PlayerChunkDirtyBlockBehaviour.getInstance();
    private static final PlayerChunkDirtyFlushBehaviour PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR = PlayerChunkDirtyFlushBehaviour.getInstance();
    private static final PlayerChunkMembershipBehaviour PLAYER_CHUNK_MEMBERSHIP_BEHAVIOUR = PlayerChunkMembershipBehaviour.getInstance();
    private static final PlayerChunkPacketDispatchBehaviour PLAYER_CHUNK_PACKET_DISPATCH_BEHAVIOUR = PlayerChunkPacketDispatchBehaviour.getInstance();
    private static final PlayerChunkTileEntityPacketBehaviour PLAYER_CHUNK_TILE_ENTITY_PACKET_BEHAVIOUR = PlayerChunkTileEntityPacketBehaviour.getInstance();

    private List b;
    private int chunkX;
    private int chunkZ;
    private ChunkCoordIntPair location;
    private short[] dirtyBlocks;
    private int dirtyCount;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;

    final PlayerManager playerManager;

    public PlayerInstance(PlayerManager playermanager, int i, int j) {
        this.playerManager = playermanager;
        this.b = new ArrayList();
        this.dirtyBlocks = new short[10];
        this.dirtyCount = 0;
        this.chunkX = i;
        this.chunkZ = j;
        this.location = new ChunkCoordIntPair(i, j);
        playermanager.a().chunkProviderServer.getChunkAt(i, j);
    }

    public void a(EntityPlayer entityplayer) {
        if (this.b.contains(entityplayer)) {
            throw new IllegalStateException("Failed to add player. " + entityplayer + " already is in chunk " + this.chunkX + ", " + this.chunkZ);
        } else {
            // CraftBukkit start
            boolean chunkSubscriptionAdded = entityplayer.playerChunkCoordIntPairs.add(this.location);
            if (PLAYER_CHUNK_MEMBERSHIP_BEHAVIOUR.shouldSendPreChunkLoad(chunkSubscriptionAdded)) {
                entityplayer.netServerHandler.sendPacket(new Packet50PreChunk(this.location.x, this.location.z, true));
            }
            // CraftBukkit end

            this.b.add(entityplayer);
            entityplayer.chunkCoordIntPairQueue.add(this.location);
        }
    }

    public void b(EntityPlayer entityplayer) {
        if (this.b.contains(entityplayer)) {
            this.b.remove(entityplayer);
            if (PLAYER_CHUNK_MEMBERSHIP_BEHAVIOUR.isChunkNowEmpty(this.b.size())) {
                long i = PLAYER_CHUNK_MEMBERSHIP_BEHAVIOUR.chunkKey(this.chunkX, this.chunkZ);

                PlayerManager.a(this.playerManager).b(i);
                if (PLAYER_CHUNK_MEMBERSHIP_BEHAVIOUR.shouldUntrackDirtyInstance(this.dirtyCount)) {
                    PlayerManager.b(this.playerManager).remove(this);
                }

                this.playerManager.a().chunkProviderServer.queueUnload(this.chunkX, this.chunkZ);
            }

            entityplayer.chunkCoordIntPairQueue.remove(this.location);
            // CraftBukkit - contains -> remove -- TODO VERIFY!!!!
            boolean chunkSubscriptionRemoved = entityplayer.playerChunkCoordIntPairs.remove(this.location);
            if (PLAYER_CHUNK_MEMBERSHIP_BEHAVIOUR.shouldSendPreChunkUnload(chunkSubscriptionRemoved)) {
                entityplayer.netServerHandler.sendPacket(new Packet50PreChunk(this.chunkX, this.chunkZ, false));
            }
        }
    }

    public void a(int i, int j, int k) {
        if (this.dirtyCount == 0) {
            PlayerManager.b(this.playerManager).add(this);
            this.h = this.i = i;
            this.j = this.k = j;
            this.l = this.m = k;
        }

        this.h = PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.min(this.h, i);
        this.i = PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.max(this.i, i);
        this.j = PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.min(this.j, j);
        this.k = PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.max(this.k, j);
        this.l = PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.min(this.l, k);
        this.m = PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.max(this.m, k);

        if (this.dirtyCount < 10) {
            short encodedDirtyBlock = PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.encodeDirtyBlock(i, j, k);
            if (PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.containsDirtyBlock(this.dirtyBlocks, this.dirtyCount, encodedDirtyBlock)) {
                return;
            }

            this.dirtyCount = PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.appendDirtyBlock(this.dirtyBlocks, this.dirtyCount, encodedDirtyBlock);
        }
    }

    public void sendAll(Packet packet) {
        PLAYER_CHUNK_PACKET_DISPATCH_BEHAVIOUR.sendToSubscribedPlayers(this.b, this.location, packet);
    }

    public void a() {
        WorldServer worldserver = this.playerManager.a();

        if (this.dirtyCount != 0) {
            int i;
            int j;
            int k;

            if (PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.isSingleBlockUpdate(this.dirtyCount)) {
                i = PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.worldCoordinate(this.chunkX, this.h);
                j = this.j;
                k = PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.worldCoordinate(this.chunkZ, this.l);
                this.sendAll(new Packet53BlockChange(i, j, k, worldserver));
                if (Block.isTileEntity[worldserver.getTypeId(i, j, k)]) {
                    this.sendTileEntity(worldserver.getTileEntity(i, j, k));
                }
            } else {
                int l;

                if (PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.isFullChunkSectionUpdate(this.dirtyCount)) {
                    this.j = PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.alignSectionMinY(this.j);
                    this.k = PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.alignSectionMaxY(this.k);
                    i = PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.worldCoordinate(this.chunkX, this.h);
                    j = this.j;
                    k = PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.worldCoordinate(this.chunkZ, this.l);
                    l = PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.sectionWidth(this.h, this.i);
                    int i1 = PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.sectionHeight(this.j, this.k);
                    int j1 = PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.sectionDepth(this.l, this.m);

                    this.sendAll(new Packet51MapChunk(i, j, k, l, i1, j1, worldserver));
                    List list = worldserver.getTileEntities(i, j, k, i + l, j + i1, k + j1);

                    for (int k1 = 0; k1 < list.size(); ++k1) {
                        this.sendTileEntity((TileEntity) list.get(k1));
                    }
                } else {
                    this.sendAll(new Packet52MultiBlockChange(this.chunkX, this.chunkZ, this.dirtyBlocks, this.dirtyCount, worldserver));

                    for (i = 0; i < this.dirtyCount; ++i) {
                        // CraftBukkit start - Fixes TileEntity updates occurring upon a multi-block change; dirtyCount -> dirtyBlocks[i]
                        short encodedDirtyBlock = this.dirtyBlocks[i];
                        j = this.chunkX * 16 + PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.decodeLocalX(encodedDirtyBlock);
                        k = PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.decodeY(encodedDirtyBlock);
                        l = this.chunkZ * 16 + PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.decodeLocalZ(encodedDirtyBlock);
                        // CraftBukkit end

                        if (Block.isTileEntity[worldserver.getTypeId(j, k, l)]) {
                            // System.out.println("Sending!"); // CraftBukkit
                            this.sendTileEntity(worldserver.getTileEntity(j, k, l));
                        }
                    }
                }
            }

            this.dirtyCount = 0;
        }
    }

    private void sendTileEntity(TileEntity tileentity) {
        Packet packet = (Packet) PLAYER_CHUNK_TILE_ENTITY_PACKET_BEHAVIOUR.extractUpdatePacket(tileentity);
        if (packet != null) {
            this.sendAll(packet);
        }
    }
    
    // Poseidon
    static ChunkCoordIntPair a(PlayerInstance playerchunk) {
        return playerchunk.location;
    }

    static List b(PlayerInstance playerchunk) {
        return playerchunk.b;
    }
}
