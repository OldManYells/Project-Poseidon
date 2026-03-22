package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.TntPrimedBehaviour;
// CraftBukkit start
// CraftBukkit end

public class EntityTNTPrimed extends Entity {
    private static final TntPrimedBehaviour TNT_PRIMED_BEHAVIOUR = TntPrimedBehaviour.getInstance();

    public int fuseTicks;
    public float yield = 4; // CraftBukkit
    public boolean isIncendiary = false; // CraftBukkit

    public EntityTNTPrimed(World world) {
        super(world);
        this.fuseTicks = 0;
        this.aI = true;
        this.b(0.98F, 0.98F);
        this.height = this.width / 2.0F;
    }

    public EntityTNTPrimed(World world, double d0, double d1, double d2) {
        this(world);
        this.setPosition(d0, d1, d2);
        float f = (float) (Math.random() * 3.1415927410125732D * 2.0D);
        TNT_PRIMED_BEHAVIOUR.initializeSpawnMotion(this, f);
        this.lastX = d0;
        this.lastY = d1;
        this.lastZ = d2;
    }

    protected void b() {}

    protected boolean n() {
        return false;
    }

    public boolean l_() {
        return !this.dead;
    }

    public void m_() {
        TNT_PRIMED_BEHAVIOUR.tickPreMove(this);
        this.move(this.motX, this.motY, this.motZ);
        TNT_PRIMED_BEHAVIOUR.tickPostMove(this);

        TntPrimedBehaviour.FuseTickResult fuseTickResult = TNT_PRIMED_BEHAVIOUR.tickFuse(this.fuseTicks);
        this.fuseTicks = fuseTickResult.fuseTicks;
        if (fuseTickResult.explodeNow) {
            if (!this.world.isStatic) {
                // CraftBukkit start - Need to reverse the order of the explosion and the entity death so we have a location for the event.
                this.explode();
                this.die();
                // CraftBukkit end
            } else {
                this.die();
            }
        } else {
            TNT_PRIMED_BEHAVIOUR.spawnFuseSmoke(this);
        }
    }

    private void explode() {
        TNT_PRIMED_BEHAVIOUR.explode(this);
    }

    protected void b(NBTTagCompound nbttagcompound) {
        TNT_PRIMED_BEHAVIOUR.writeFuseNbt(nbttagcompound, this.fuseTicks);
    }

    protected void a(NBTTagCompound nbttagcompound) {
        this.fuseTicks = TNT_PRIMED_BEHAVIOUR.readFuseNbt(nbttagcompound);
    }
}
