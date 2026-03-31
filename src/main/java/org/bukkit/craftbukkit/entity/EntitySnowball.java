package org.bukkit.craftbukkit.entity;

import net.minecraft.server.*;
import org.bukkit.craftbukkit.item.Item;
import org.bukkit.craftbukkit.item.ItemStack;
import org.bukkit.craftbukkit.world.World;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.AxisAlignedBB;
import org.bukkit.util.Vec3D;

import java.util.List;

// CraftBukkit start
// CraftBukkit end

public class EntitySnowball extends Entity {

    private int tileX = -1;
    private int tileY = -1;
    private int tileZ = -1;
    private int inBlockId = 0;
    private boolean inGround = false;
    public int shake = 0;
    public EntityLiving shooter; // CraftBukkit - private -> public
    private int ticksInGround;
    private int ticksInAir = 0;

    public EntitySnowball(World world) {
        super(world);
        this.b(0.25F, 0.25F);
    }

    protected void b() {}

    public EntitySnowball(World world, EntityLiving entityliving) {
        super(world);
        this.shooter = entityliving;
        this.b(0.25F, 0.25F);
        this.setPositionRotation(entityliving.locX, entityliving.locY + (double) entityliving.t(), entityliving.locZ, entityliving.yaw, entityliving.pitch);
        this.locX -= (double) (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F);
        this.locY -= 0.10000000149011612D;
        this.locZ -= (double) (MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F);
        this.setPosition(this.locX, this.locY, this.locZ);
        this.height = 0.0F;
        float f = 0.4F;

        this.motX = (double) (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
        this.motZ = (double) (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
        this.motY = (double) (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F) * f);
        this.setProjectileVelocity(this.motX, this.motY, this.motZ, 1.5F, 1.0F);
    }

    public EntitySnowball(World world, double d0, double d1, double d2) {
        super(world);
        this.ticksInGround = 0;
        this.b(0.25F, 0.25F);
        this.setPosition(d0, d1, d2);
        this.height = 0.0F;
    }

    public void setProjectileVelocity(double velX, double velY, double velZ, float speed, float spread) {
        float f2 = MathHelper.a(velX * velX + velY * velY + velZ * velZ);

        velX /= (double) f2;
        velY /= (double) f2;
        velZ /= (double) f2;
        velX += this.random.nextGaussian() * 0.007499999832361937D * (double) spread;
        velY += this.random.nextGaussian() * 0.007499999832361937D * (double) spread;
        velZ += this.random.nextGaussian() * 0.007499999832361937D * (double) spread;
        velX *= (double) speed;
        velY *= (double) speed;
        velZ *= (double) speed;
        this.motX = velX;
        this.motY = velY;
        this.motZ = velZ;
        float f3 = MathHelper.a(velX * velX + velZ * velZ);

        this.lastYaw = this.yaw = (float) (Math.atan2(velX, velZ) * 180.0D / 3.1415927410125732D);
        this.lastPitch = this.pitch = (float) (Math.atan2(velY, (double) f3) * 180.0D / 3.1415927410125732D);
        this.ticksInGround = 0;
    }

    public void m_() {
        this.bo = this.locX;
        this.bp = this.locY;
        this.bq = this.locZ;
        super.m_();
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

        if (!this.world.isStatic) {
            Entity entity = null;
            List list = this.world.b((Entity) this, this.boundingBox.a(this.motX, this.motY, this.motZ).b(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;

            for (int j = 0; j < list.size(); ++j) {
                Entity entity1 = (Entity) list.get(j);

                if (entity1.l_() && (entity1 != this.shooter || this.ticksInAir >= 5)) {
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
        }

        if (movingobjectposition != null) {
            // CraftBukkit start
            ProjectileHitEvent phe = new ProjectileHitEvent((Projectile) this.getBukkitEntity());
            this.world.getServer().getPluginManager().callEvent(phe);

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
                        // this function returns if the snowball should stick in or not, i.e. !bounce
                        stick = movingobjectposition.entity.damageEntity(this, event.getDamage());
                    }
                } else {
                    stick = movingobjectposition.entity.damageEntity(this.shooter, 0);
                }
                if (stick) {
                    ;
                }
            }
            // CraftBukkit end

            for (int k = 0; k < 8; ++k) {
                this.world.a("snowballpoof", this.locX, this.locY, this.locZ, 0.0D, 0.0D, 0.0D);
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
        float f2 = 0.99F;
        float f3 = 0.03F;

        if (this.ad()) {
            for (int l = 0; l < 4; ++l) {
                float f4 = 0.25F;

                this.world.a("bubble", this.locX - this.motX * (double) f4, this.locY - this.motY * (double) f4, this.locZ - this.motZ * (double) f4, this.motX, this.motY, this.motZ);
            }

            f2 = 0.8F;
        }

        this.motX *= (double) f2;
        this.motY *= (double) f2;
        this.motZ *= (double) f2;
        this.motY -= (double) f3;
        this.setPosition(this.locX, this.locY, this.locZ);
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.setShort("xTile", (short) this.tileX);
        nbttagcompound.setShort("yTile", (short) this.tileY);
        nbttagcompound.setShort("zTile", (short) this.tileZ);
        nbttagcompound.setByte("inTile", (byte) this.inBlockId);
        nbttagcompound.setByte("shake", (byte) this.shake);
        nbttagcompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.tileX = nbttagcompound.getShort("xTile");
        this.tileY = nbttagcompound.getShort("yTile");
        this.tileZ = nbttagcompound.getShort("zTile");
        this.inBlockId = nbttagcompound.getByte("inTile") & 255;
        this.shake = nbttagcompound.getByte("shake") & 255;
        this.inGround = nbttagcompound.getByte("inGround") == 1;
    }

    public void b(EntityHuman entityhuman) {
        if (this.inGround && this.shooter == entityhuman && this.shake <= 0 && entityhuman.inventory.pickup(new ItemStack(Item.ARROW, 1))) {
            this.world.makeSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityhuman.receive(this, 1);
            this.die();
        }
    }
}
