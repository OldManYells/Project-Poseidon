package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.SpiderCombatBehaviour;
// CraftBukkit start
// CraftBukkit end

public class EntitySpider extends EntityMonster {
    private static final SpiderCombatBehaviour SPIDER_COMBAT_BEHAVIOUR = SpiderCombatBehaviour.getInstance();

    public EntitySpider(World world) {
        super(world);
        this.texture = "/mob/spider.png";
        this.b(1.4F, 0.9F);
        this.aE = 0.8F;
    }

    public double m() {
        return (double) this.width * 0.75D - 0.5D;
    }

    protected boolean n() {
        return false;
    }

    protected Entity findTarget() {
        return SPIDER_COMBAT_BEHAVIOUR.findTarget(this, this.c(1.0F));
    }

    protected String g() {
        return SPIDER_COMBAT_BEHAVIOUR.getAmbientSound();
    }

    protected String h() {
        return SPIDER_COMBAT_BEHAVIOUR.getHurtSound();
    }

    protected String i() {
        return SPIDER_COMBAT_BEHAVIOUR.getDeathSound();
    }

    protected void a(Entity entity, float f) {
        if (!SPIDER_COMBAT_BEHAVIOUR.handleAttack(this, entity, f, this.c(1.0F), this.random.nextInt(100), this.random.nextInt(10))) {
            super.a(entity, f);
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
    }

    protected int j() {
        return SPIDER_COMBAT_BEHAVIOUR.getDropItemId();
    }

    public boolean p() {
        return this.positionChanged;
    }
}
