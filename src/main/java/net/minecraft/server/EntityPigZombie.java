package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.PigZombieAggroBehaviour;

// CraftBukkit start
// CraftBukkit end

public class EntityPigZombie extends EntityZombie {
    private static final PigZombieAggroBehaviour PIG_ZOMBIE_AGGRO_BEHAVIOUR = PigZombieAggroBehaviour.getInstance();

    public int angerLevel = 0; // CraftBukkit - private -> public
    private int soundDelay = 0;
    private static final ItemStack f = new ItemStack(Item.GOLD_SWORD, 1);

    public EntityPigZombie(World world) {
        super(world);
        this.texture = "/mob/pigzombie.png";
        this.aE = 0.5F;
        this.damage = 5;
        this.fireProof = true;
    }

    public void m_() {
        PigZombieAggroBehaviour.TickState tickState = PIG_ZOMBIE_AGGRO_BEHAVIOUR.tick(this.target != null, this.soundDelay, this.random);
        this.aE = tickState.movementSpeed;
        this.soundDelay = tickState.soundDelay;
        if (tickState.playAngrySound) {
            this.world.makeSound(this, "mob.zombiepig.zpigangry", this.k() * 2.0F, tickState.angrySoundPitch);
        }

        super.m_();
    }

    public boolean d() {
        return PIG_ZOMBIE_AGGRO_BEHAVIOUR.canSpawn(
                this.world.spawnMonsters,
                this.world.containsEntity(this.boundingBox),
                this.world.getEntities(this, this.boundingBox).size(),
                this.world.c(this.boundingBox)
        );
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Anger", (short) this.angerLevel);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.angerLevel = nbttagcompound.d("Anger");
    }

    protected Entity findTarget() {
        return PIG_ZOMBIE_AGGRO_BEHAVIOUR.shouldFindTarget(this.angerLevel) ? super.findTarget() : null;
    }

    public void v() {
        super.v();
    }

    public boolean damageEntity(Entity entity, int i) {
        PIG_ZOMBIE_AGGRO_BEHAVIOUR.propagateAggroToNearbyPigZombies(this, entity, this.random);
        return super.damageEntity(entity, i);
    }

    private void d(Entity entity) {
        PIG_ZOMBIE_AGGRO_BEHAVIOUR.applyAggroTarget(this, entity, this.random);
    }

    protected String g() {
        return PIG_ZOMBIE_AGGRO_BEHAVIOUR.getAmbientSound();
    }

    protected String h() {
        return PIG_ZOMBIE_AGGRO_BEHAVIOUR.getHurtSound();
    }

    protected String i() {
        return PIG_ZOMBIE_AGGRO_BEHAVIOUR.getDeathSound();
    }

    protected int j() {
        return PIG_ZOMBIE_AGGRO_BEHAVIOUR.getDropItemId();
    }

    public void poseidonSetSoundDelay(int soundDelay) {
        this.soundDelay = soundDelay;
    }
}
