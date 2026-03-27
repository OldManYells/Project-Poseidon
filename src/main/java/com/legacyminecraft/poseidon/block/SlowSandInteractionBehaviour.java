package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.entity.Entity;
import com.legacyminecraft.poseidon.world.AxisAlignedBB;

/**
 * Canonical collision and movement slowdown policy for legacy slow-sand wrappers.
 */
public final class SlowSandInteractionBehaviour {
    private static final SlowSandInteractionBehaviour INSTANCE = new SlowSandInteractionBehaviour();

    private SlowSandInteractionBehaviour() {
    }

    public static SlowSandInteractionBehaviour getInstance() {
        return INSTANCE;
    }

    public AxisAlignedBB resolveCollisionBox(int x, int y, int z, float topInset) {
        return AxisAlignedBB.b(
                (double) x,
                (double) y,
                (double) z,
                (double) (x + 1),
                (double) ((float) (y + 1) - topInset),
                (double) (z + 1)
        );
    }

    public double slowdownFactor() {
        return 0.4D;
    }

    public void applyHorizontalSlowdown(Entity entity, double factor) {
        entity.motX *= factor;
        entity.motZ *= factor;
    }
}
