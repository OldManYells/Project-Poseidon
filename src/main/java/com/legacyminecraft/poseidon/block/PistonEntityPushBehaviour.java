package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.world.AxisAlignedBB;
import com.legacyminecraft.poseidon.world.Entity;
import com.legacyminecraft.poseidon.world.World;

import java.util.Iterator;
import java.util.List;

/**
 * Canonical behaviour for piston collision push motion against nearby entities.
 */
public final class PistonEntityPushBehaviour {
    private static final PistonEntityPushBehaviour INSTANCE = new PistonEntityPushBehaviour();

    private PistonEntityPushBehaviour() {
    }

    public static PistonEntityPushBehaviour getInstance() {
        return INSTANCE;
    }

    public void moveCollidingEntities(World world,
                                      int x,
                                      int y,
                                      int z,
                                      int movedBlockId,
                                      int facing,
                                      boolean extending,
                                      float progress,
                                      float deltaProgress,
                                      List reusableEntityList) {
        float adjustedProgress = progress;
        if (!extending) {
            --adjustedProgress;
        } else {
            adjustedProgress = 1.0F - adjustedProgress;
        }

        AxisAlignedBB movementBounds = Block.PISTON_MOVING.a(world, x, y, z, movedBlockId, adjustedProgress, facing);

        if (movementBounds == null) {
            return;
        }

        List collidingEntities = world.b((Entity) null, movementBounds);
        if (collidingEntities.isEmpty()) {
            return;
        }

        reusableEntityList.addAll(collidingEntities);
        Iterator iterator = reusableEntityList.iterator();

        while (iterator.hasNext()) {
            Entity entity = (Entity) iterator.next();
            entity.move((double) (deltaProgress * (float) PistonBlockTextures.b[facing]),
                    (double) (deltaProgress * (float) PistonBlockTextures.c[facing]),
                    (double) (deltaProgress * (float) PistonBlockTextures.d[facing]));
        }

        reusableEntityList.clear();
    }
}
