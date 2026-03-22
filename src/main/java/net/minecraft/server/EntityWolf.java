package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.WolfStateBehaviour;
import com.legacyminecraft.poseidon.entity.WolfFollowOwnerBehaviour;
import com.legacyminecraft.poseidon.entity.WolfCombatInteractionBehaviour;
import com.legacyminecraft.poseidon.entity.WolfAiBehaviour;

// CraftBukkit start
// CraftBukkit end

public class EntityWolf extends EntityAnimal {
    private static final WolfStateBehaviour WOLF_STATE_BEHAVIOUR = WolfStateBehaviour.getInstance();
    private static final WolfFollowOwnerBehaviour WOLF_FOLLOW_OWNER_BEHAVIOUR = WolfFollowOwnerBehaviour.getInstance();
    private static final WolfCombatInteractionBehaviour WOLF_COMBAT_INTERACTION_BEHAVIOUR = WolfCombatInteractionBehaviour.getInstance();
    private static final WolfAiBehaviour WOLF_AI_BEHAVIOUR = WolfAiBehaviour.getInstance();

    private boolean a = false;
    private float b;
    private float c;
    private boolean f;
    private boolean g;
    private float h;
    private float i;

    public EntityWolf(World world) {
        super(world);
        this.texture = "/mob/wolf.png";
        this.b(0.8F, 0.8F);
        this.aE = 1.1F;
        this.health = 8;
    }

    protected void b() {
        super.b();
        this.datawatcher.a(WOLF_STATE_BEHAVIOUR.getFlagsWatcherIndex(), Byte.valueOf(WOLF_STATE_BEHAVIOUR.createInitialFlags()));
        this.datawatcher.a(WOLF_STATE_BEHAVIOUR.getOwnerWatcherIndex(), WOLF_STATE_BEHAVIOUR.createInitialOwnerName());
        this.datawatcher.a(WOLF_STATE_BEHAVIOUR.getHealthWatcherIndex(), Integer.valueOf(WOLF_STATE_BEHAVIOUR.createInitialHealthValue(this.health)));
    }

    protected boolean n() {
        return false;
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Angry", this.isAngry());
        nbttagcompound.a("Sitting", this.isSitting());
        nbttagcompound.setString("Owner", WOLF_STATE_BEHAVIOUR.toStoredOwnerName(this.getOwnerName()));
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setAngry(nbttagcompound.m("Angry"));
        this.setSitting(nbttagcompound.m("Sitting"));
        String s = nbttagcompound.getString("Owner");

        if (s.length() > 0) {
            this.setOwnerName(s);
            this.setTamed(true);
        }
    }

    protected boolean h_() {
        return WOLF_STATE_BEHAVIOUR.shouldStayHostileToPlayers(this.isTamed());
    }

    protected String g() {
        return WOLF_STATE_BEHAVIOUR.resolveAmbientSound(this.isAngry(), this.random.nextInt(3), this.isTamed(), this.datawatcher.b(WOLF_STATE_BEHAVIOUR.getHealthWatcherIndex()));
    }

    protected String h() {
        return WOLF_STATE_BEHAVIOUR.getHurtSound();
    }

    protected String i() {
        return WOLF_STATE_BEHAVIOUR.getDeathSound();
    }

    protected float k() {
        return WOLF_STATE_BEHAVIOUR.getSoundVolume();
    }

    protected int j() {
        return WOLF_STATE_BEHAVIOUR.getDropItemId();
    }

    protected void c_() {
        super.c_();
        WOLF_AI_BEHAVIOUR.tickAi(this, this.e, WOLF_STATE_BEHAVIOUR.getHealthWatcherIndex(), WOLF_FOLLOW_OWNER_BEHAVIOUR);
    }

    public void v() {
        super.v();
        this.a = false;
        if (this.V() && !this.C() && !this.isAngry()) {
            Entity entity = this.W();

            ItemStack itemInHand = entity instanceof EntityHuman ? ((EntityHuman) entity).inventory.getItemInHand() : null;
            this.a = WOLF_STATE_BEHAVIOUR.shouldBegForItem(this.V(), this.C(), this.isAngry(), entity, this.isTamed(), itemInHand, this.datawatcher.b(WOLF_STATE_BEHAVIOUR.getHealthWatcherIndex()));
        }

        if (!this.Y && this.f && !this.g && !this.C() && this.onGround) {
            this.g = true;
            this.h = 0.0F;
            this.i = 0.0F;
            this.world.a(this, (byte) 8);
        }
    }

    public void m_() {
        super.m_();
        this.c = this.b;
        if (this.a) {
            this.b += (1.0F - this.b) * 0.4F;
        } else {
            this.b += (0.0F - this.b) * 0.4F;
        }

        if (this.a) {
            this.aF = WOLF_STATE_BEHAVIOUR.updateTailAngleTimer(true, this.b);
        }

        if (this.ac()) {
            this.f = true;
            this.g = false;
            this.h = 0.0F;
            this.i = 0.0F;
        } else if ((this.f || this.g) && this.g) {
            if (this.h == 0.0F) {
                this.world.makeSound(this, "mob.wolf.shake", this.k(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }

            this.i = this.h;
            this.h += 0.05F;
            if (this.i >= 2.0F) {
                this.f = false;
                this.g = false;
                this.i = 0.0F;
                this.h = 0.0F;
            }

            if (this.h > 0.4F) {
                float f = (float) this.boundingBox.b;
                int i = (int) (MathHelper.sin((this.h - 0.4F) * 3.1415927F) * 7.0F);

                for (int j = 0; j < i; ++j) {
                    float f1 = (this.random.nextFloat() * 2.0F - 1.0F) * this.length * 0.5F;
                    float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.length * 0.5F;

                    this.world.a("splash", this.locX + (double) f1, (double) (f + 0.8F), this.locZ + (double) f2, this.motX, this.motY, this.motZ);
                }
            }
        }
    }

    public float t() {
        return this.width * 0.8F;
    }

    protected int u() {
        return this.isSitting() ? 20 : super.u();
    }

    private void c(Entity entity, float f) {
        WOLF_FOLLOW_OWNER_BEHAVIOUR.followOwnerOrTeleport(this, entity, f);
    }

    protected boolean w() {
        return WOLF_FOLLOW_OWNER_BEHAVIOUR.shouldPausePathing(this.isSitting(), this.g);
    }

    public boolean damageEntity(Entity entity, int i) {
        this.setSitting(false);
        i = WOLF_COMBAT_INTERACTION_BEHAVIOUR.adjustIncomingDamage(entity, i);

        if (!super.damageEntity((Entity) entity, i)) {
            return false;
        }

        WOLF_COMBAT_INTERACTION_BEHAVIOUR.onDamaged(this, entity);
        return true;
    }

    protected Entity findTarget() {
        return WOLF_COMBAT_INTERACTION_BEHAVIOUR.findTarget(this);
    }

    protected void a(Entity entity, float f) {
        WOLF_COMBAT_INTERACTION_BEHAVIOUR.attackTarget(this, entity, f, this.random);
    }

    public boolean a(EntityHuman entityhuman) {
        return WOLF_COMBAT_INTERACTION_BEHAVIOUR.interact(this, entityhuman, this.datawatcher.b(WOLF_STATE_BEHAVIOUR.getHealthWatcherIndex()), WOLF_STATE_BEHAVIOUR.getHealthWatcherIndex(), this.random);
    }

    void a(boolean flag) {
        WOLF_COMBAT_INTERACTION_BEHAVIOUR.spawnTameEffectParticles(this.world, this, flag, this.random);
    }

    public void poseidonShowTameEffect(boolean success) {
        this.a(success);
    }

    public void poseidonClearMovementIntent() {
        this.aC = false;
    }

    public void poseidonSyncHealthWatcher(int healthWatcherIndex) {
        this.datawatcher.watch(healthWatcherIndex, Integer.valueOf(this.health));
    }

    public int l() {
        return 8;
    }

    public String getOwnerName() {
        return this.datawatcher.c(WOLF_STATE_BEHAVIOUR.getOwnerWatcherIndex());
    }

    public void setOwnerName(String s) {
        this.datawatcher.watch(WOLF_STATE_BEHAVIOUR.getOwnerWatcherIndex(), s);
    }

    public boolean isSitting() {
        return WOLF_STATE_BEHAVIOUR.isSitting(this.datawatcher.a(WOLF_STATE_BEHAVIOUR.getFlagsWatcherIndex()));
    }

    public void setSitting(boolean flag) {
        byte flags = this.datawatcher.a(WOLF_STATE_BEHAVIOUR.getFlagsWatcherIndex());
        this.datawatcher.watch(WOLF_STATE_BEHAVIOUR.getFlagsWatcherIndex(), Byte.valueOf(WOLF_STATE_BEHAVIOUR.withSitting(flags, flag)));
    }

    public boolean isAngry() {
        return WOLF_STATE_BEHAVIOUR.isAngry(this.datawatcher.a(WOLF_STATE_BEHAVIOUR.getFlagsWatcherIndex()));
    }

    public void setAngry(boolean flag) {
        byte flags = this.datawatcher.a(WOLF_STATE_BEHAVIOUR.getFlagsWatcherIndex());
        this.datawatcher.watch(WOLF_STATE_BEHAVIOUR.getFlagsWatcherIndex(), Byte.valueOf(WOLF_STATE_BEHAVIOUR.withAngry(flags, flag)));
    }

    public boolean isTamed() {
        return WOLF_STATE_BEHAVIOUR.isTamed(this.datawatcher.a(WOLF_STATE_BEHAVIOUR.getFlagsWatcherIndex()));
    }

    public void setTamed(boolean flag) {
        byte flags = this.datawatcher.a(WOLF_STATE_BEHAVIOUR.getFlagsWatcherIndex());
        this.datawatcher.watch(WOLF_STATE_BEHAVIOUR.getFlagsWatcherIndex(), Byte.valueOf(WOLF_STATE_BEHAVIOUR.withTamed(flags, flag)));
    }
}
