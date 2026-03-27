package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.world.ChunkPosition;
import com.legacyminecraft.poseidon.world.World;

import java.util.List;

/**
 * Canonical behaviour for minecart track placement/propagation orchestration.
 */
public final class MinecartTrackPropagationBehaviour {
    private static final MinecartTrackPropagationBehaviour INSTANCE = new MinecartTrackPropagationBehaviour();

    private MinecartTrackPropagationBehaviour() {
    }

    public static MinecartTrackPropagationBehaviour getInstance() {
        return INSTANCE;
    }

    public void appendConnection(List connections, int connectedTrackX, int connectedTrackY, int connectedTrackZ) {
        connections.add(new ChunkPosition(connectedTrackX, connectedTrackY, connectedTrackZ));
    }

    public byte resolvePropagationShape(ConnectionMembershipLookup membershipLookup,
                                        int trackX,
                                        int trackY,
                                        int trackZ,
                                        boolean poweredRail,
                                        MinecartTrackShapeSelectionBehaviour shapeSelectionBehaviour,
                                        World world) {
        boolean hasNorth = membershipLookup.hasConnection(trackX, trackY, trackZ - 1);
        boolean hasSouth = membershipLookup.hasConnection(trackX, trackY, trackZ + 1);
        boolean hasWest = membershipLookup.hasConnection(trackX - 1, trackY, trackZ);
        boolean hasEast = membershipLookup.hasConnection(trackX + 1, trackY, trackZ);
        byte shape = shapeSelectionBehaviour.resolvePropagationShape(hasNorth, hasSouth, hasWest, hasEast, poweredRail);
        return shapeSelectionBehaviour.applySlopeAdjustments(world, trackX, trackY, trackZ, shape);
    }

    public byte resolvePlacementShape(ConnectionProbe connectionProbe,
                                      int trackX,
                                      int trackY,
                                      int trackZ,
                                      boolean poweredRail,
                                      boolean preferSouthEastTurns,
                                      MinecartTrackShapeSelectionBehaviour shapeSelectionBehaviour,
                                      World world) {
        boolean hasNorth = connectionProbe.isConnected(trackX, trackY, trackZ - 1);
        boolean hasSouth = connectionProbe.isConnected(trackX, trackY, trackZ + 1);
        boolean hasWest = connectionProbe.isConnected(trackX - 1, trackY, trackZ);
        boolean hasEast = connectionProbe.isConnected(trackX + 1, trackY, trackZ);
        byte shape = shapeSelectionBehaviour.resolvePlacementShape(hasNorth, hasSouth, hasWest, hasEast, poweredRail, preferSouthEastTurns);
        return shapeSelectionBehaviour.applySlopeAdjustments(world, trackX, trackY, trackZ, shape);
    }

    public void forEachConnection(List connections, ConnectionVisitor connectionVisitor) {
        for (int index = 0; index < connections.size(); ++index) {
            connectionVisitor.visit((ChunkPosition) connections.get(index));
        }
    }

    public interface ConnectionMembershipLookup {
        boolean hasConnection(int x, int y, int z);
    }

    public interface ConnectionProbe {
        boolean isConnected(int x, int y, int z);
    }

    public interface ConnectionVisitor {
        void visit(ChunkPosition connection);
    }
}
