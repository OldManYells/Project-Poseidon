package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local living entity scaffold.
 */
public class EntityLiving extends Entity {
    public Entity target;
    public int health = 20;
    public int attackTicks;
    public int maxNoDamageTicks;
    public int lastDamage;
    public int noDamageTicks;

    public void setPosition(double x, double y, double z) {
        this.locX = x;
        this.locY = y;
        this.locZ = z;
    }

    public void setPositionRotation(double x, double y, double z, float yaw, float pitch) {
        setPosition(x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public boolean d() {
        return true;
    }

    public int l() {
        return 4;
    }

    public void mount(Entity entity) {
        this.vehicle = entity;
    }

    public void Q() {
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public boolean e(Entity target) {
        return true;
    }

    @Override
    public boolean damageEntity(Entity source, int damage) {
        this.health -= damage;
        return true;
    }

    public void b(int amount, Object reason) {
        this.health += amount;
    }
}
