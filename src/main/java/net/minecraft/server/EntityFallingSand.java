package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.FallingSandBehaviour;

public class EntityFallingSand extends Entity {
    private static final FallingSandBehaviour FALLING_SAND_BEHAVIOUR = FallingSandBehaviour.getInstance();

    public int a;
    public int b = 0;

    public EntityFallingSand(World world) {
        super(world);
    }

    public EntityFallingSand(World world, double d0, double d1, double d2, int i) {
        super(world);
        FALLING_SAND_BEHAVIOUR.initializeSpawn(this, d0, d1, d2, i);
    }

    protected boolean n() {
        return false;
    }

    protected void b() {}

    public boolean l_() {
        return !this.dead;
    }

    public void m_() {
        if (FALLING_SAND_BEHAVIOUR.shouldDieForMissingBlock(this.a)) {
            this.die();
        } else {
            FALLING_SAND_BEHAVIOUR.tickPreMove(this);
            this.move(this.motX, this.motY, this.motZ);
            FALLING_SAND_BEHAVIOUR.tickPostMove(this);
            FallingSandBehaviour.BlockPos blockPos = FALLING_SAND_BEHAVIOUR.resolveBlockPos(this.locX, this.locY, this.locZ);
            int i = blockPos.x;
            int j = blockPos.y;
            int k = blockPos.z;

            FALLING_SAND_BEHAVIOUR.clearSourceBlockIfMatching(this, i, j, k);

            if (this.onGround) {
                FallingSandBehaviour.GroundImpactResult impactResult = FALLING_SAND_BEHAVIOUR.handleGroundImpact(this, i, j, k);
                this.die();
                if (impactResult.shouldDropItem) {
                    this.b(this.a, 1);
                }
            } else if (FALLING_SAND_BEHAVIOUR.shouldDropForTimeout(this.b, this.world.isStatic)) {
                this.b(this.a, 1);
                this.die();
            }
        }
    }

    protected void b(NBTTagCompound nbttagcompound) {
        FALLING_SAND_BEHAVIOUR.writeTileNbt(nbttagcompound, this.a);
    }

    protected void a(NBTTagCompound nbttagcompound) {
        this.a = FALLING_SAND_BEHAVIOUR.readTileNbt(nbttagcompound);
    }

    public void poseidonInitializeBounds() {
        this.b(0.98F, 0.98F);
        this.height = this.width / 2.0F;
    }
}
