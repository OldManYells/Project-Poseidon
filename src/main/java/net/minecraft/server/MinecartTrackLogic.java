package net.minecraft.server;

import com.legacyminecraft.poseidon.block.MinecartTrackConnectionLayoutBehaviour;
import com.legacyminecraft.poseidon.block.MinecartTrackDataWriteBehaviour;
import com.legacyminecraft.poseidon.block.MinecartTrackPropagationBehaviour;
import com.legacyminecraft.poseidon.block.MinecartTrackShapeSelectionBehaviour;
import com.legacyminecraft.poseidon.block.MinecartTrackStateUpdateBehaviour;

import java.util.ArrayList;
import java.util.List;

class MinecartTrackLogic {
    private static final MinecartTrackConnectionLayoutBehaviour MINECART_TRACK_CONNECTION_LAYOUT_BEHAVIOUR = MinecartTrackConnectionLayoutBehaviour.getInstance();
    private static final MinecartTrackDataWriteBehaviour MINECART_TRACK_DATA_WRITE_BEHAVIOUR = MinecartTrackDataWriteBehaviour.getInstance();
    private static final MinecartTrackPropagationBehaviour MINECART_TRACK_PROPAGATION_BEHAVIOUR = MinecartTrackPropagationBehaviour.getInstance();
    private static final MinecartTrackShapeSelectionBehaviour MINECART_TRACK_SHAPE_SELECTION_BEHAVIOUR = MinecartTrackShapeSelectionBehaviour.getInstance();
    private static final MinecartTrackStateUpdateBehaviour MINECART_TRACK_STATE_UPDATE_BEHAVIOUR = MinecartTrackStateUpdateBehaviour.getInstance();

    private World b;
    private int c;
    private int d;
    private int e;
    private final boolean f;
    private List g;

    final BlockMinecartTrack a;

    public MinecartTrackLogic(BlockMinecartTrack blockminecarttrack, World world, int i, int j, int k) {
        this.a = blockminecarttrack;
        this.g = new ArrayList();
        this.b = world;
        this.c = i;
        this.d = j;
        this.e = k;
        int l = world.getTypeId(i, j, k);
        int i1 = world.getData(i, j, k);

        if (BlockMinecartTrack.a((BlockMinecartTrack) Block.byId[l])) {
            this.f = true;
            i1 &= -9;
        } else {
            this.f = false;
        }

        this.a(i1);
    }

    private void a(int i) {
        MINECART_TRACK_CONNECTION_LAYOUT_BEHAVIOUR.populateConnections(this.g, i, this.c, this.d, this.e);
    }

    private void a() {
        MINECART_TRACK_CONNECTION_LAYOUT_BEHAVIOUR.pruneDisconnectedConnections(this.g, new MinecartTrackConnectionLayoutBehaviour.ConnectionResolver() {
            public ChunkPosition resolve(ChunkPosition connection) {
                MinecartTrackLogic resolvedLogic = MinecartTrackLogic.this.a(connection);

                if (resolvedLogic != null && resolvedLogic.b(MinecartTrackLogic.this)) {
                    return new ChunkPosition(resolvedLogic.c, resolvedLogic.d, resolvedLogic.e);
                }

                return null;
            }
        });
    }

    private boolean a(int i, int j, int k) {
        return MINECART_TRACK_CONNECTION_LAYOUT_BEHAVIOUR.hasAdjacentTrack(this.b, i, j, k);
    }

    private MinecartTrackLogic a(ChunkPosition chunkposition) {
        Integer trackY = MINECART_TRACK_CONNECTION_LAYOUT_BEHAVIOUR.resolveTrackY(this.b, chunkposition.x, chunkposition.y, chunkposition.z);

        return trackY == null ? null : new MinecartTrackLogic(this.a, this.b, chunkposition.x, trackY.intValue(), chunkposition.z);
    }

    private boolean b(MinecartTrackLogic minecarttracklogic) {
        return MINECART_TRACK_CONNECTION_LAYOUT_BEHAVIOUR.containsConnectionAt(this.g, minecarttracklogic.c, minecarttracklogic.e);
    }

    private boolean b(int i, int j, int k) {
        return MINECART_TRACK_CONNECTION_LAYOUT_BEHAVIOUR.containsConnectionAt(this.g, i, k);
    }

    private int b() {
        return MINECART_TRACK_CONNECTION_LAYOUT_BEHAVIOUR.countAdjacentTracks(this.b, this.c, this.d, this.e);
    }

    private boolean c(MinecartTrackLogic minecarttracklogic) {
        return MINECART_TRACK_CONNECTION_LAYOUT_BEHAVIOUR.canAcceptConnection(this.b(minecarttracklogic), this.g.size());
    }

    private void d(MinecartTrackLogic minecarttracklogic) {
        MINECART_TRACK_PROPAGATION_BEHAVIOUR.appendConnection(this.g, minecarttracklogic.c, minecarttracklogic.d, minecarttracklogic.e);
        byte b0 = MINECART_TRACK_PROPAGATION_BEHAVIOUR.resolvePropagationShape(new MinecartTrackPropagationBehaviour.ConnectionMembershipLookup() {
            public boolean hasConnection(int x, int y, int z) {
                return MinecartTrackLogic.this.b(x, y, z);
            }
        }, this.c, this.d, this.e, this.f, MINECART_TRACK_SHAPE_SELECTION_BEHAVIOUR, this.b);
        int composedData = MINECART_TRACK_DATA_WRITE_BEHAVIOUR.composeStoredData(this.b, this.c, this.d, this.e, this.f, b0);
        MINECART_TRACK_DATA_WRITE_BEHAVIOUR.writeData(this.b, this.c, this.d, this.e, composedData);
    }

    private boolean c(int i, int j, int k) {
        MinecartTrackLogic minecarttracklogic = this.a(new ChunkPosition(i, j, k));

        if (minecarttracklogic == null) {
            return false;
        } else {
            minecarttracklogic.a();
            return minecarttracklogic.c(this);
        }
    }

    public void a(boolean flag, boolean flag1) {
        byte b0 = MINECART_TRACK_PROPAGATION_BEHAVIOUR.resolvePlacementShape(new MinecartTrackPropagationBehaviour.ConnectionProbe() {
            public boolean isConnected(int x, int y, int z) {
                return MinecartTrackLogic.this.c(x, y, z);
            }
        }, this.c, this.d, this.e, this.f, flag, MINECART_TRACK_SHAPE_SELECTION_BEHAVIOUR, this.b);

        this.a(b0);
        int composedData = MINECART_TRACK_DATA_WRITE_BEHAVIOUR.composeStoredData(this.b, this.c, this.d, this.e, this.f, b0);
        MINECART_TRACK_STATE_UPDATE_BEHAVIOUR.writeAndPropagate(
                flag1,
                this.b,
                this.c,
                this.d,
                this.e,
                composedData,
                MINECART_TRACK_DATA_WRITE_BEHAVIOUR,
                MINECART_TRACK_PROPAGATION_BEHAVIOUR,
                this.g,
                new MinecartTrackStateUpdateBehaviour.ConnectionUpdate() {
                    public void propagate(ChunkPosition connection) {
                        MinecartTrackLogic minecarttracklogic = MinecartTrackLogic.this.a(connection);

                        if (minecarttracklogic != null) {
                            minecarttracklogic.a();
                            if (minecarttracklogic.c(MinecartTrackLogic.this)) {
                                minecarttracklogic.d(MinecartTrackLogic.this);
                            }
                        }
                    }
                }
        );
    }

    static int a(MinecartTrackLogic minecarttracklogic) {
        return minecarttracklogic.b();
    }
}
