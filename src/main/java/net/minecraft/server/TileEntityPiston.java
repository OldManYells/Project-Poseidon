package net.minecraft.server;

import com.legacyminecraft.poseidon.block.PistonEntityPushBehaviour;
import com.legacyminecraft.poseidon.block.PistonLifecycleGateBehaviour;
import com.legacyminecraft.poseidon.block.PistonTileNbtBehaviour;
import com.legacyminecraft.poseidon.block.PistonProgressInterpolationBehaviour;
import com.legacyminecraft.poseidon.block.PistonTickProgressionBehaviour;
import com.legacyminecraft.poseidon.block.PistonTileFinalizationBehaviour;
import com.legacyminecraft.poseidon.block.PistonTileTickOrchestrationBehaviour;

import java.util.ArrayList;
import java.util.List;

public class TileEntityPiston extends TileEntity {
    private static final PistonEntityPushBehaviour PISTON_ENTITY_PUSH_BEHAVIOUR = PistonEntityPushBehaviour.getInstance();
    private static final PistonLifecycleGateBehaviour PISTON_LIFECYCLE_GATE_BEHAVIOUR = PistonLifecycleGateBehaviour.getInstance();
    private static final PistonTileNbtBehaviour PISTON_TILE_NBT_BEHAVIOUR = PistonTileNbtBehaviour.getInstance();
    private static final PistonProgressInterpolationBehaviour PISTON_PROGRESS_INTERPOLATION_BEHAVIOUR = PistonProgressInterpolationBehaviour.getInstance();
    private static final PistonTickProgressionBehaviour PISTON_TICK_PROGRESSION_BEHAVIOUR = PistonTickProgressionBehaviour.getInstance();
    private static final PistonTileFinalizationBehaviour PISTON_TILE_FINALIZATION_BEHAVIOUR = PistonTileFinalizationBehaviour.getInstance();
    private static final PistonTileTickOrchestrationBehaviour PISTON_TILE_TICK_ORCHESTRATION_BEHAVIOUR = PistonTileTickOrchestrationBehaviour.getInstance();

    private int a;
    private int b;
    private int c;
    private boolean i;
    private boolean j;
    private float k;
    private float l;
    private static List m = new ArrayList();

    public TileEntityPiston() {}

    public TileEntityPiston(int i, int j, int k, boolean flag, boolean flag1) {
        this.a = i;
        this.b = j;
        this.c = k;
        this.i = flag;
        this.j = flag1;
    }

    public int a() {
        return this.a;
    }

    public int e() {
        return this.b;
    }

    public boolean c() {
        return this.i;
    }

    public int d() {
        return this.c;
    }

    public float a(float f) {
        return PISTON_PROGRESS_INTERPOLATION_BEHAVIOUR.interpolate(this.l, this.k, f);
    }

    private void a(float f, float f1) {
        PISTON_ENTITY_PUSH_BEHAVIOUR.moveCollidingEntities(this.world, this.x, this.y, this.z, this.a, this.c, this.i, f, f1, m);
    }

    public void k() {
        PistonTileTickOrchestrationBehaviour.FinalizationDecision finalizationDecision =
                PISTON_TILE_TICK_ORCHESTRATION_BEHAVIOUR.resolveImmediateFinalization(this.l, PISTON_LIFECYCLE_GATE_BEHAVIOUR);
        if (finalizationDecision.shouldFinalize()) {
            this.l = this.k = finalizationDecision.getCompletionProgress();
            PISTON_TILE_FINALIZATION_BEHAVIOUR.finalizeMovingTile(this.world, this.x, this.y, this.z, this.a, this.b, new Runnable() {
                public void run() {
                    h();
                }
            });
        }
    }

    public void g_() {
        // CraftBukkit
        if (PISTON_LIFECYCLE_GATE_BEHAVIOUR.shouldSkipTick(this.world)) return;
        PistonTileTickOrchestrationBehaviour.TickDecision tickDecision = PISTON_TILE_TICK_ORCHESTRATION_BEHAVIOUR.resolveTickProgression(
                this.l,
                this.k,
                this.i,
                PISTON_LIFECYCLE_GATE_BEHAVIOUR,
                PISTON_TICK_PROGRESSION_BEHAVIOUR
        );
        this.l = tickDecision.getPreviousProgress();
        this.k = tickDecision.getCurrentProgress();

        if (tickDecision.shouldPushEntities()) {
            this.a(tickDecision.getPushProgress(), tickDecision.getPushDelta());
        }

        if (tickDecision.shouldFinalize()) {
            PISTON_TILE_FINALIZATION_BEHAVIOUR.finalizeMovingTile(this.world, this.x, this.y, this.z, this.a, this.b, new Runnable() {
                public void run() {
                    h();
                }
            });
        }
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        PistonTileNbtBehaviour.PistonTileState state = PISTON_TILE_NBT_BEHAVIOUR.readState(nbttagcompound);
        this.a = state.getMovedBlockId();
        this.b = state.getMovedBlockData();
        this.c = state.getFacing();
        this.l = this.k = state.getProgress();
        this.i = state.isExtending();
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        PISTON_TILE_NBT_BEHAVIOUR.writeState(nbttagcompound, this.a, this.b, this.c, this.l, this.i);
    }
}
