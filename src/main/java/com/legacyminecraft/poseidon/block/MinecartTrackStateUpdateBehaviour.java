package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.world.ChunkPosition;
import com.legacyminecraft.poseidon.world.World;

import java.util.List;

/**
 * Canonical behaviour for minecart track state-write and neighbor propagation orchestration.
 */
public final class MinecartTrackStateUpdateBehaviour {
    private static final MinecartTrackStateUpdateBehaviour INSTANCE = new MinecartTrackStateUpdateBehaviour();

    private MinecartTrackStateUpdateBehaviour() {
    }

    public static MinecartTrackStateUpdateBehaviour getInstance() {
        return INSTANCE;
    }

    public void writeAndPropagate(boolean forceWrite,
                                  World world,
                                  int x,
                                  int y,
                                  int z,
                                  int composedData,
                                  MinecartTrackDataWriteBehaviour dataWriteBehaviour,
                                  MinecartTrackPropagationBehaviour propagationBehaviour,
                                  List connections,
                                  ConnectionUpdate connectionUpdate) {
        if (!dataWriteBehaviour.shouldWriteData(forceWrite, world, x, y, z, composedData)) {
            return;
        }

        dataWriteBehaviour.writeData(world, x, y, z, composedData);
        propagationBehaviour.forEachConnection(connections, new MinecartTrackPropagationBehaviour.ConnectionVisitor() {
            public void visit(ChunkPosition connection) {
                connectionUpdate.propagate(connection);
            }
        });
    }

    public interface ConnectionUpdate {
        void propagate(ChunkPosition connection);
    }
}
