package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.world.TileEntity;

/**
 * Canonical moving piston tile-entity scaffold.
 */
public class TileEntityPiston extends TileEntity {
    private final int movedBlockId;
    private final int movedBlockData;
    private final int facing;
    private final boolean extending;
    private final boolean renderHead;
    private float progress;

    public TileEntityPiston(int movedBlockId, int movedBlockData, int facing, boolean extending, boolean renderHead) {
        this.movedBlockId = movedBlockId;
        this.movedBlockData = movedBlockData;
        this.facing = facing;
        this.extending = extending;
        this.renderHead = renderHead;
        this.progress = 0.0F;
    }

    public int a() {
        return movedBlockId;
    }

    public int e() {
        return movedBlockData;
    }

    public int d() {
        return facing;
    }

    public boolean c() {
        return extending;
    }

    public boolean f() {
        return renderHead;
    }

    public float a(float tickDelta) {
        return progress;
    }

    public void k() {
        this.progress = 1.0F;
    }
}
