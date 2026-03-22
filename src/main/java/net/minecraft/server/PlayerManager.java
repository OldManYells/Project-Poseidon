package net.minecraft.server;

import com.legacyminecraft.poseidon.world.player.PlayerViewRangeBehaviour;
import com.legacyminecraft.poseidon.world.player.PlayerChunkMovementBehaviour;
import com.legacyminecraft.poseidon.world.player.PlayerChunkCoordinateBehaviour;
import com.legacyminecraft.poseidon.world.player.PlayerChunkIterationBehaviour;
import com.legacyminecraft.poseidon.world.player.PlayerChunkSpiralLoadBehaviour;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private static final PlayerChunkIterationBehaviour PLAYER_CHUNK_ITERATION_BEHAVIOUR = PlayerChunkIterationBehaviour.getInstance();
    private static final PlayerChunkSpiralLoadBehaviour PLAYER_CHUNK_SPIRAL_LOAD_BEHAVIOUR = PlayerChunkSpiralLoadBehaviour.getInstance();
    private static final PlayerChunkCoordinateBehaviour PLAYER_CHUNK_COORDINATE_BEHAVIOUR = PlayerChunkCoordinateBehaviour.getInstance();
    private static final PlayerChunkMovementBehaviour PLAYER_CHUNK_MOVEMENT_BEHAVIOUR = PlayerChunkMovementBehaviour.getInstance();
    private static final PlayerViewRangeBehaviour PLAYER_VIEW_RANGE_BEHAVIOUR = PlayerViewRangeBehaviour.getInstance();

    public List managedPlayers = new ArrayList();
    private PlayerList b = new PlayerList();
    private List c = new ArrayList();
    private MinecraftServer server;
    private int e;
    private int f;

    public PlayerManager(MinecraftServer minecraftserver, int i, int j) {
        PLAYER_VIEW_RANGE_BEHAVIOUR.validateViewRadius(j);
        this.f = j;
        this.server = minecraftserver;
        this.e = i;
    }

    public WorldServer a() {
        return this.server.getWorldServer(this.e);
    }

    public void flush() {
        for (int i = 0; i < this.c.size(); ++i) {
            ((PlayerInstance) this.c.get(i)).a();
        }

        this.c.clear();
    }

    private PlayerInstance a(int i, int j, boolean flag) {
        long k = (long) i + 2147483647L | (long) j + 2147483647L << 32;
        PlayerInstance playerinstance = (PlayerInstance) this.b.a(k);

        if (playerinstance == null && flag) {
            playerinstance = new PlayerInstance(this, i, j);
            this.b.a(k, playerinstance);
        }

        return playerinstance;
    }

    public void flagDirty(int i, int j, int k) {
        int l = PLAYER_CHUNK_COORDINATE_BEHAVIOUR.chunkFromBlock(i);
        int i1 = PLAYER_CHUNK_COORDINATE_BEHAVIOUR.chunkFromBlock(k);
        PlayerInstance playerinstance = this.a(l, i1, false);

        if (playerinstance != null) {
            playerinstance.a(PLAYER_CHUNK_COORDINATE_BEHAVIOUR.localBlockInChunk(i), j, PLAYER_CHUNK_COORDINATE_BEHAVIOUR.localBlockInChunk(k));
        }
    }

    public void addPlayer(EntityPlayer entityplayer) {
        int i = PLAYER_CHUNK_COORDINATE_BEHAVIOUR.chunkFromWorldPosition(entityplayer.locX);
        int j = PLAYER_CHUNK_COORDINATE_BEHAVIOUR.chunkFromWorldPosition(entityplayer.locZ);

        entityplayer.d = entityplayer.locX;
        entityplayer.e = entityplayer.locZ;
        PLAYER_CHUNK_SPIRAL_LOAD_BEHAVIOUR.forEachSpiralChunk(i, j, this.f, new PlayerChunkSpiralLoadBehaviour.ChunkConsumer() {
            public void accept(int chunkX, int chunkZ) {
                a(chunkX, chunkZ, true).a(entityplayer);
            }
        });

        this.managedPlayers.add(entityplayer);
    }

    public void removePlayer(EntityPlayer entityplayer) {
        int i = PLAYER_CHUNK_COORDINATE_BEHAVIOUR.chunkFromWorldPosition(entityplayer.d);
        int j = PLAYER_CHUNK_COORDINATE_BEHAVIOUR.chunkFromWorldPosition(entityplayer.e);

        PLAYER_CHUNK_ITERATION_BEHAVIOUR.forEachChunkInViewRange(i, j, this.f, new PlayerChunkIterationBehaviour.ChunkConsumer() {
            public void accept(int chunkX, int chunkZ) {
                PlayerInstance playerinstance = a(chunkX, chunkZ, false);

                if (playerinstance != null) {
                    playerinstance.b(entityplayer);
                }
            }
        });

        this.managedPlayers.remove(entityplayer);
    }

    private boolean a(int i, int j, int k, int l) {
        return PLAYER_VIEW_RANGE_BEHAVIOUR.isWithinViewRange(i, j, k, l, this.f);
    }

    public void movePlayer(EntityPlayer entityplayer) {
        int i = PLAYER_CHUNK_MOVEMENT_BEHAVIOUR.chunkCoordinate(entityplayer.locX);
        int j = PLAYER_CHUNK_MOVEMENT_BEHAVIOUR.chunkCoordinate(entityplayer.locZ);
        double d2 = PLAYER_CHUNK_MOVEMENT_BEHAVIOUR.squaredMovement(entityplayer.d, entityplayer.e, entityplayer.locX, entityplayer.locZ);

        if (PLAYER_CHUNK_MOVEMENT_BEHAVIOUR.shouldProcessChunkMovement(d2)) {
            int k = PLAYER_CHUNK_MOVEMENT_BEHAVIOUR.chunkCoordinate(entityplayer.d);
            int l = PLAYER_CHUNK_MOVEMENT_BEHAVIOUR.chunkCoordinate(entityplayer.e);
            int i1 = i - k;
            int j1 = j - l;

            if (i1 != 0 || j1 != 0) {
                PLAYER_CHUNK_ITERATION_BEHAVIOUR.forEachChunkInViewRange(i, j, this.f, new PlayerChunkIterationBehaviour.ChunkConsumer() {
                    public void accept(int chunkX, int chunkZ) {
                        if (!a(chunkX, chunkZ, k, l)) {
                            a(chunkX, chunkZ, true).a(entityplayer);
                        }

                        if (!a(chunkX - i1, chunkZ - j1, i, j)) {
                            PlayerInstance playerinstance = a(chunkX - i1, chunkZ - j1, false);

                            if (playerinstance != null) {
                                playerinstance.b(entityplayer);
                            }
                        }
                    }
                });

                entityplayer.d = entityplayer.locX;
                entityplayer.e = entityplayer.locZ;

                // CraftBukkit start - send nearest chunks first
                if (i1 > 1 || i1 < -1 || j1 > 1 || j1 < -1) {
                    final int x = i;
                    final int z = j;
                    List<ChunkCoordIntPair> chunksToSend = entityplayer.chunkCoordIntPairQueue;

                    java.util.Collections.sort(chunksToSend, new java.util.Comparator<ChunkCoordIntPair>() {
                        public int compare(ChunkCoordIntPair a, ChunkCoordIntPair b) {
                            return Math.max(Math.abs(a.x - x), Math.abs(a.z - z)) - Math.max(Math.abs(b.x - x), Math.abs(b.z - z));
                        }
                    });
                }
                // CraftBukkit end
            }
        }
    }
    
    // Poseidon
    public boolean a(EntityPlayer entityplayer, int i, int j) {
        PlayerInstance playerchunk = this.a(i, j, false);

        return playerchunk == null ? false : PlayerInstance.b(playerchunk).contains(entityplayer) && !entityplayer.chunkCoordIntPairQueue.contains(PlayerInstance.a(playerchunk));
    }

    public int getFurthestViewableBlock() {
        return PLAYER_VIEW_RANGE_BEHAVIOUR.furthestViewableBlock(this.f);
    }

    static PlayerList a(PlayerManager playermanager) {
        return playermanager.b;
    }

    static List b(PlayerManager playermanager) {
        return playermanager.c;
    }
}
