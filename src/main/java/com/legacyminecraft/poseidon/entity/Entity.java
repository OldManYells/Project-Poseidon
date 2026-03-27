package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local alias used by migrated entity systems.
 */
public class Entity extends com.legacyminecraft.compat.bukkit.Entity {
    public int bH;
    public int bJ;
    public boolean bf;
    public boolean onGround;
    public float width = 0.6F;
    public float height = 1.8F;
    public float length = 0.6F;
    public float K;
    public double lastX;
    public World world;
    public Entity vehicle;
    public Entity passenger;
    public boolean airBorne;

    public void a(EntityWeatherStorm storm) {
    }

    public void e(NBTTagCompound nbt) {
    }

    public void setPosition(double x, double y, double z) {
        this.locX = x;
        this.locY = y;
        this.locZ = z;
    }

    public void mount(Entity entity) {
        this.vehicle = entity;
    }

    public void f() {
    }

    public void b(double x, double y, double z) {
        this.motX += x;
        this.motY += y;
        this.motZ += z;
    }

    public float t() {
        return this.height;
    }

    public EntityItem a(ItemStack stack, float offset) {
        return new EntityItem(world, locX, locY + (double) offset, locZ, stack);
    }

    public boolean damageEntity(Entity source, int damage) {
        return true;
    }

    public float I() {
        return 0.0F;
    }

    public double g(Entity other) {
        if (other == null) {
            return Double.MAX_VALUE;
        }
        double dx = other.locX - this.locX;
        double dy = other.locY - this.locY;
        double dz = other.locZ - this.locZ;
        return dx * dx + dy * dy + dz * dz;
    }

    public Vec3D Z() {
        return null;
    }
}
