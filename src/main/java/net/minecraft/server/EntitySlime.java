package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.SlimeStateBehaviour;

public class EntitySlime extends EntityLiving implements IMonster {
    private static final SlimeStateBehaviour SLIME_STATE_BEHAVIOUR = SlimeStateBehaviour.getInstance();

    public float a;
    public float b;
    private int size = 0;

    public EntitySlime(World world) {
        super(world);
        this.texture = "/mob/slime.png";
        int i = SLIME_STATE_BEHAVIOUR.createInitialPhysicalSize(this.random);

        this.height = 0.0F;
        this.size = SLIME_STATE_BEHAVIOUR.createInitialJumpDelay(this.random);
        this.setSize(i);
    }

    protected void b() {
        super.b();
        this.datawatcher.a(16, Byte.valueOf(SLIME_STATE_BEHAVIOUR.createInitialWatcherSize()));
    }

    public void setSize(int i) {
        SlimeStateBehaviour.SetSizeState setSizeState = SLIME_STATE_BEHAVIOUR.computeSetSizeState(i);
        this.datawatcher.watch(16, Byte.valueOf((byte) i));
        this.b(setSizeState.scaledWidth, setSizeState.scaledWidth);
        this.health = setSizeState.scaledHealth;
        this.setPosition(this.locX, this.locY, this.locZ);
    }

    public int getSize() {
        return this.datawatcher.a(16);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Size", this.getSize() - 1);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setSize(nbttagcompound.e("Size") + 1);
    }

    public void m_() {
        this.b = this.a;
        boolean flag = this.onGround;

        super.m_();
        this.a = SLIME_STATE_BEHAVIOUR.tickLandingSquish(this, this.a, flag, this.onGround, this.getSize(), this.random);
    }

    protected void c_() {
        this.U();
        EntityHuman entityhuman = this.world.findNearbyPlayer(this, 16.0D);

        if (entityhuman != null) {
            this.a(entityhuman, 10.0F, 20.0F);
        }

        if (this.onGround && this.size-- <= 0) {
            this.size = this.random.nextInt(20) + 10;
            if (entityhuman != null) {
                this.size /= 3;
            }

            this.aC = true;
            if (this.getSize() > 1) {
                this.world.makeSound(this, "mob.slime", this.k(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
            }

            this.a = 1.0F;
            this.az = 1.0F - this.random.nextFloat() * 2.0F;
            this.aA = (float) (1 * this.getSize());
        } else {
            this.aC = false;
            if (this.onGround) {
                this.az = this.aA = 0.0F;
            }
        }
    }

    public void die() {
        SLIME_STATE_BEHAVIOUR.splitOnDeath(this, this.getSize(), this.health, this.world.isStatic, this.random);
        super.die();
    }

    public void b(EntityHuman entityhuman) {
        SLIME_STATE_BEHAVIOUR.attackPlayerOnContact(this, entityhuman, this.getSize(), this.random);
    }

    protected String h() {
        return SLIME_STATE_BEHAVIOUR.getHurtSound();
    }

    protected String i() {
        return SLIME_STATE_BEHAVIOUR.getDeathSound();
    }

    protected int j() {
        return SLIME_STATE_BEHAVIOUR.getDropItemId(this.getSize());
    }

    public boolean d() {
        Chunk chunk = this.world.getChunkAtWorldCoords(MathHelper.floor(this.locX), MathHelper.floor(this.locZ));
        return SLIME_STATE_BEHAVIOUR.canSpawn(chunk, this.getSize(), this.world.spawnMonsters, this.random.nextInt(10), this.locY);
    }

    protected float k() {
        return SLIME_STATE_BEHAVIOUR.getSoundVolume();
    }
}
