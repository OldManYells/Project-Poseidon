package org.bukkit.craftbukkit.entity;

import net.minecraft.server.*;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

// CraftBukkit start
// CraftBukkit end

public class EntityFireball extends Entity {

    private int tileX = -1;
    private int tileY = -1;
    private int tileZ = -1;
    private int inBlockId = 0;
    private boolean inGround = false;
    public int shake = 0;
    public EntityLiving shooter;
    private int ticksInGround;
    private int ticksInAir = 0;
    public double directionX;
    public double directionY;
    public double directionZ;

    public float yield = 1; // CraftBukkit
    public boolean isIncendiary = true; // CraftBukkit

    public EntityFireball(World world) {
        super(world);
        this.b(1.0F, 1.0F);
    }

    protected void b() {}

    public EntityFireball(World world, EntityLiving entityliving, double d0, double d1, double d2) {
        super(world);
        this.shooter = entityliving;
        this.b(1.0F, 1.0F);
        this.setPositionRotation(entityliving.locX, entityliving.locY, entityliving.locZ, entityliving.yaw, entityliving.pitch);
        this.setPosition(this.locX, this.locY, this.locZ);
        this.height = 0.0F;
        this.motX = this.motY = this.motZ = 0.0D;
        // CraftBukkit start (added setDirection method)
        this.setDirection(d0, d1, d2);
    }

    public void setDirection(double d0, double d1, double d2) {
        d0 += this.random.nextGaussian() * 0.4D;
        d1 += this.random.nextGaussian() * 0.4D;
        d2 += this.random.nextGaussian() * 0.4D;
        double d3 = (double) MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2);

        this.directionX = d0 / d3 * 0.1D;
        this.directionY = d1 / d3 * 0.1D;
        this.directionZ = d2 / d3 * 0.1D;
    }

    public void m_() {
        super.m_();
        this.fireTicks = 10;
        if (this.shake > 0) {
            --this.shake;
        }

        if (this.inGround) {
            int i = this.world.getTypeId(this.tileX, this.tileY, this.tileZ);

            if (i == this.inBlockId) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.die();
                }

                return;
            }

            this.inGround = false;
            this.motX *= (double) (this.random.nextFloat() * 0.2F);
            this.motY *= (double) (this.random.nextFloat() * 0.2F);
            this.motZ *= (double) (this.random.nextFloat() * 0.2F);
            this.ticksInGround = 0;
            this.ticksInAir = 0;
        } else {
            ++this.ticksInAir;
        }

        Vec3D vec3d = Vec3D.create(this.locX, this.locY, this.locZ);
        Vec3D vec3d1 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
        MovingObjectPosition movingobjectposition = this.world.a(vec3d, vec3d1);

        vec3d = Vec3D.create(this.locX, this.locY, this.locZ);
        vec3d1 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
        if (movingobjectposition != null) {
            vec3d1 = Vec3D.create(movingobjectposition.f.a, movingobjectposition.f.b, movingobjectposition.f.c);
        }

        Entity entity = null;
        List list = this.world.b((Entity) this, this.boundingBox.a(this.motX, this.motY, this.motZ).b(1.0D, 1.0D, 1.0D));
        double d0 = 0.0D;

        for (int j = 0; j < list.size(); ++j) {
            Entity entity1 = (Entity) list.get(j);

            if (entity1.l_() && (entity1 != this.shooter || this.ticksInAir >= 25)) {
                float f = 0.3F;
                AxisAlignedBB axisalignedbb = entity1.boundingBox.b((double) f, (double) f, (double) f);
                MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);

                if (movingobjectposition1 != null) {
                    double d1 = vec3d.a(movingobjectposition1.f);

                    if (d1 < d0 || d0 == 0.0D) {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }

        if (entity != null) {
            movingobjectposition = new MovingObjectPosition(entity);
        }

        if (movingobjectposition != null) {
            // CraftBukkit start
            ProjectileHitEvent phe = new ProjectileHitEvent((Projectile) this.getBukkitEntity());
            this.world.getServer().getPluginManager().callEvent(phe);
            // CraftBukkit end
            if (!this.world.isStatic) {
                // CraftBukkit start
                if (movingobjectposition.entity != null) {
                    boolean stick;
                    if (movingobjectposition.entity instanceof EntityLiving) {
                        org.bukkit.entity.Entity damagee = movingobjectposition.entity.getBukkitEntity();
                        Projectile projectile = (Projectile) this.getBukkitEntity();

                        // TODO @see EntityArrow#162
                        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(projectile, damagee, EntityDamageEvent.DamageCause.PROJECTILE, 0);
                        this.world.getServer().getPluginManager().callEvent(event);

                        this.shooter = (projectile.getShooter() == null) ? null : ((CraftLivingEntity) projectile.getShooter()).getHandle();

                        if (event.isCancelled()) {
                            stick = !projectile.doesBounce();
                        } else {
                            // this function returns if the fireball should stick in or not, i.e. !bounce
                            stick = movingobjectposition.entity.damageEntity(this, event.getDamage());
                        }
                    } else {
                        stick = movingobjectposition.entity.damageEntity(this.shooter, 0);
                    }
                    if (stick) {
                        ;
                    }
                }

                ExplosionPrimeEvent event = new ExplosionPrimeEvent((Explosive) CraftEntity.getEntity(this.world.getServer(), this));
                this.world.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled()) {
                    // give 'this' instead of (Entity) null so we know what causes the damage
                    this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire());
                }
                // CraftBukkit end
            }

            this.die();
        }

        this.locX += this.motX;
        this.locY += this.motY;
        this.locZ += this.motZ;
        float f1 = MathHelper.a(this.motX * this.motX + this.motZ * this.motZ);

        this.yaw = (float) (Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D);

        for (this.pitch = (float) (Math.atan2(this.motY, (double) f1) * 180.0D / 3.1415927410125732D); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {
            ;
        }

        while (this.pitch - this.lastPitch >= 180.0F) {
            this.lastPitch += 360.0F;
        }

        while (this.yaw - this.lastYaw < -180.0F) {
            this.lastYaw -= 360.0F;
        }

        while (this.yaw - this.lastYaw >= 180.0F) {
            this.lastYaw += 360.0F;
        }

        this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
        this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
        float f2 = 0.95F;

        if (this.ad()) {
            for (int k = 0; k < 4; ++k) {
                float f3 = 0.25F;

                this.world.a("bubble", this.locX - this.motX * (double) f3, this.locY - this.motY * (double) f3, this.locZ - this.motZ * (double) f3, this.motX, this.motY, this.motZ);
            }

            f2 = 0.8F;
        }

        this.motX += this.directionX;
        this.motY += this.directionY;
        this.motZ += this.directionZ;
        this.motX *= (double) f2;
        this.motY *= (double) f2;
        this.motZ *= (double) f2;
        this.world.a("smoke", this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D);
        this.setPosition(this.locX, this.locY, this.locZ);
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("xTile", (short) this.tileX);
        nbttagcompound.a("yTile", (short) this.tileY);
        nbttagcompound.a("zTile", (short) this.tileZ);
        nbttagcompound.a("inTile", (byte) this.inBlockId);
        nbttagcompound.a("shake", (byte) this.shake);
        nbttagcompound.a("inGround", (byte) (this.inGround ? 1 : 0));
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.tileX = nbttagcompound.d("xTile");
        this.tileY = nbttagcompound.d("yTile");
        this.tileZ = nbttagcompound.d("zTile");
        this.inBlockId = nbttagcompound.c("inTile") & 255;
        this.shake = nbttagcompound.c("shake") & 255;
        this.inGround = nbttagcompound.c("inGround") == 1;
    }

    public boolean l_() {
        return true;
    }

    public boolean damageEntity(Entity entity, int i) {
        this.af();
        if (entity != null) {
            Vec3D vec3d = entity.Z();

            if (vec3d != null) {
                this.motX = vec3d.a;
                this.motY = vec3d.b;
                this.motZ = vec3d.c;
                this.directionX = this.motX * 0.1D;
                this.directionY = this.motY * 0.1D;
                this.directionZ = this.motZ * 0.1D;
            }

            return true;
        } else {
            return false;
        }
    }
}
