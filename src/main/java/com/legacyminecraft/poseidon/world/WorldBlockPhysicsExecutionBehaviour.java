package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.compat.bukkit.WorldBlockPhysicsEventBridgeBehaviour;

/**
 * Canonical behaviour for world block-physics execution flow.
 */
public final class WorldBlockPhysicsExecutionBehaviour {
    private static final WorldBlockPhysicsExecutionBehaviour INSTANCE = new WorldBlockPhysicsExecutionBehaviour();

    private WorldBlockPhysicsExecutionBehaviour() {
    }

    public static WorldBlockPhysicsExecutionBehaviour getInstance() {
        return INSTANCE;
    }

    public void applyNeighbourPhysics(
            World world,
            boolean suppressPhysics,
            boolean isStatic,
            int x,
            int y,
            int z,
            int sourceTypeId,
            WorldBlockPhysicsEventBridgeBehaviour eventBridgeBehaviour
    ) {
        if (suppressPhysics || isStatic) {
            return;
        }

        Block block = Block.byId[world.getTypeId(x, y, z)];
        if (block == null) {
            return;
        }

        if (eventBridgeBehaviour.shouldCancelPhysics(world, x, y, z, sourceTypeId)) {
            return;
        }

        block.doPhysics(world, x, y, z, sourceTypeId);
    }
}
