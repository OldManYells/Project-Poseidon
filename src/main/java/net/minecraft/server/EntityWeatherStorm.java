package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.LightningStormLifecycleBehaviour;
import org.bukkit.craftbukkit.CraftWorld;

// CraftBukkit start
// CraftBukkit end

public class EntityWeatherStorm extends EntityWeather {
    private static final LightningStormLifecycleBehaviour LIGHTNING_STORM_LIFECYCLE_BEHAVIOUR = LightningStormLifecycleBehaviour.getInstance();

    private int lifeTicks;
    public long a = 0L;
    private int c;

    // CraftBukkit start
    private CraftWorld cworld;
    public boolean isEffect = false;

    public EntityWeatherStorm(World world, double d0, double d1, double d2) {
        this(world, d0, d1, d2, false);
    }

    public EntityWeatherStorm(World world, double d0, double d1, double d2, boolean isEffect) {
        // CraftBukkit end

        super(world);

        // CraftBukkit start
        this.isEffect = isEffect;
        this.cworld = world.getWorld();
        // CraftBukkit end

        LIGHTNING_STORM_LIFECYCLE_BEHAVIOUR.initialize(this, world, d0, d1, d2, isEffect, this.cworld);
    }

    public void m_() {
        super.m_();
        LIGHTNING_STORM_LIFECYCLE_BEHAVIOUR.tick(this);
    }

    protected void b() {}

    protected void a(NBTTagCompound nbttagcompound) {
        LIGHTNING_STORM_LIFECYCLE_BEHAVIOUR.readFromNbt(this, nbttagcompound);
    }

    protected void b(NBTTagCompound nbttagcompound) {
        LIGHTNING_STORM_LIFECYCLE_BEHAVIOUR.writeToNbt(this, nbttagcompound);
    }

    public int poseidonGetLifeTicks() {
        return this.lifeTicks;
    }

    public void poseidonSetLifeTicks(int lifeTicks) {
        this.lifeTicks = lifeTicks;
    }

    public int poseidonGetFlashCount() {
        return this.c;
    }

    public void poseidonSetFlashCount(int flashCount) {
        this.c = flashCount;
    }

    public CraftWorld poseidonGetCraftWorld() {
        return this.cworld;
    }

    public long poseidonNextRandomLong() {
        return this.random.nextLong();
    }

    public int poseidonNextRandomInt(int bound) {
        return this.random.nextInt(bound);
    }

    public float poseidonNextRandomFloat() {
        return this.random.nextFloat();
    }
}
