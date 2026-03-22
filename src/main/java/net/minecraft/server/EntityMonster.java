package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.MonsterCoreBehaviour;
// CraftBukkit start
// CraftBukkit end

public class EntityMonster extends EntityCreature implements IMonster {
    private static final MonsterCoreBehaviour MONSTER_CORE_BEHAVIOUR = MonsterCoreBehaviour.getInstance();

    protected int damage = 2;

    public EntityMonster(World world) {
        super(world);
        this.health = 20;
    }

    public void v() {
        this.ay = MONSTER_CORE_BEHAVIOUR.updateSunlightPenalty(this.c(1.0F), this.ay);

        super.v();
    }

    public void m_() {
        super.m_();
        if (MONSTER_CORE_BEHAVIOUR.shouldDespawnWhenMonstersDisabled(this.world.isStatic, this.world.spawnMonsters)) {
            this.die();
        }
    }

    protected Entity findTarget() {
        return MONSTER_CORE_BEHAVIOUR.findTarget(this);
    }

    public boolean damageEntity(Entity entity, int i) {
        if (!super.damageEntity(entity, i)) {
            return false;
        }

        return MONSTER_CORE_BEHAVIOUR.onDamaged(this, entity);
    }

    protected void a(Entity entity, float f) {
        MONSTER_CORE_BEHAVIOUR.attackTarget(this, entity, f, this.damage);
    }

    protected float a(int i, int j, int k) {
        return MONSTER_CORE_BEHAVIOUR.resolvePathWeight(this.world.n(i, j, k));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
    }

    public boolean d() {
        int i = MathHelper.floor(this.locX);
        int j = MathHelper.floor(this.boundingBox.b);
        int k = MathHelper.floor(this.locZ);

        if (this.world.a(EnumSkyBlock.SKY, i, j, k) > this.random.nextInt(32)) {
            return false;
        } else {
            int l = this.world.getLightLevel(i, j, k);

            if (this.world.u()) {
                int i1 = this.world.f;

                this.world.f = 10;
                l = this.world.getLightLevel(i, j, k);
                this.world.f = i1;
            }

            return l <= this.random.nextInt(8) && super.d();
        }
    }
}
