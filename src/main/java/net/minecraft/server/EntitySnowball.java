package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.ArrowStateBehaviour;
import com.legacyminecraft.poseidon.entity.ThrowableProjectileStateBehaviour;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

// CraftBukkit start
// CraftBukkit end

public class EntitySnowball extends Entity {
    private static final ThrowableProjectileStateBehaviour THROWABLE_PROJECTILE_STATE_BEHAVIOUR = ThrowableProjectileStateBehaviour.getInstance();

    private int b = -1;
    private int c = -1;
    private int d = -1;
    private int e = 0;
    private boolean f = false;
    public int a = 0;
    public EntityLiving shooter; // CraftBukkit - private -> public
    private int h;
    private int i = 0;

    public EntitySnowball(World world) {
        super(world);
        this.b(0.25F, 0.25F);
    }

    protected void b() {}

    public EntitySnowball(World world, EntityLiving entityliving) {
        super(world);
        this.shooter = entityliving;
        this.b(0.25F, 0.25F);
        ArrowStateBehaviour.ShooterLaunchState launchState = THROWABLE_PROJECTILE_STATE_BEHAVIOUR.createShooterLaunchState(
                entityliving.locX,
                entityliving.locY,
                entityliving.locZ,
                entityliving.yaw,
                entityliving.pitch,
                entityliving.t(),
                0.4F
        );
        this.setPositionRotation(launchState.spawnX, launchState.spawnY, launchState.spawnZ, entityliving.yaw, entityliving.pitch);
        this.locX = launchState.spawnX;
        this.locY = launchState.spawnY;
        this.locZ = launchState.spawnZ;
        this.setPosition(this.locX, this.locY, this.locZ);
        this.height = 0.0F;
        this.motX = launchState.baseMotionX;
        this.motY = launchState.baseMotionY;
        this.motZ = launchState.baseMotionZ;
        this.a(this.motX, this.motY, this.motZ, 1.5F, 1.0F);
    }

    public EntitySnowball(World world, double d0, double d1, double d2) {
        super(world);
        this.h = 0;
        this.b(0.25F, 0.25F);
        this.setPosition(d0, d1, d2);
        this.height = 0.0F;
    }

    public void a(double d0, double d1, double d2, float f, float f1) {
        ArrowStateBehaviour.HeadingState heading = THROWABLE_PROJECTILE_STATE_BEHAVIOUR.computeHeading(
                d0,
                d1,
                d2,
                f,
                f1,
                this.random.nextGaussian(),
                this.random.nextGaussian(),
                this.random.nextGaussian()
        );
        this.motX = heading.motionX;
        this.motY = heading.motionY;
        this.motZ = heading.motionZ;
        this.lastYaw = this.yaw = heading.yaw;
        this.lastPitch = this.pitch = heading.pitch;
        this.h = 0;
    }

    public void m_() {
        this.bo = this.locX;
        this.bp = this.locY;
        this.bq = this.locZ;
        super.m_();
        if (this.a > 0) {
            --this.a;
        }

        if (this.f) {
            int i = this.world.getTypeId(this.b, this.c, this.d);

            if (i == this.e) {
                ++this.h;
                if (this.h == 1200) {
                    this.die();
                }

                return;
            }

            this.f = false;
            this.motX *= (double) (this.random.nextFloat() * 0.2F);
            this.motY *= (double) (this.random.nextFloat() * 0.2F);
            this.motZ *= (double) (this.random.nextFloat() * 0.2F);
            this.h = 0;
            this.i = 0;
        } else {
            ++this.i;
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

                if (entity1.l_() && (entity1 != this.shooter || this.i >= 5)) {
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
        THROWABLE_PROJECTILE_STATE_BEHAVIOUR.writePersistedState(nbttagcompound, this.b, this.c, this.d, this.e, this.a, this.f);
    }

    public void a(NBTTagCompound nbttagcompound) {
        ThrowableProjectileStateBehaviour.LoadedState loadedState = THROWABLE_PROJECTILE_STATE_BEHAVIOUR.readPersistedState(nbttagcompound);
        this.b = loadedState.tileX;
        this.c = loadedState.tileY;
        this.d = loadedState.tileZ;
        this.e = loadedState.inTile;
        this.a = loadedState.shake;
        this.f = loadedState.inGround;
    }

    public void b(EntityHuman entityhuman) {
        if (this.f && this.shooter == entityhuman && this.a <= 0 && entityhuman.inventory.pickup(new ItemStack(Item.ARROW, 1))) {
            this.world.makeSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityhuman.receive(this, 1);
            this.die();
        }
    }
}
