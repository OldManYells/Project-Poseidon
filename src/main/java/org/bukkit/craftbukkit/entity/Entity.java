package org.bukkit.craftbukkit.entity;

import net.minecraft.server.*;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.block.BlockFluids;
import org.bukkit.craftbukkit.item.ItemStack;
import org.bukkit.craftbukkit.world.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class Entity {

    public static Random SHARED_RANDOM = new Random() {
        private boolean locked = false;

        @Override
        public synchronized void setSeed(long seed) {
            if (!locked) {
                super.setSeed(seed);
                locked = true;
            }
        }
    };

    private static int entityCount = 0;

    public int id;
    public double aH;                  // renderDistanceWeight
    public boolean aI;                 // preventEntitySpawning
    public Entity passenger;
    public Entity vehicle;
    public World world;
    public double lastX;
    public double lastY;
    public double lastZ;
    public double locX;
    public double locY;
    public double locZ;
    public double motX;
    public double motY;
    public double motZ;
    public float yaw;
    public float pitch;
    public float lastYaw;
    public float lastPitch;
    public final AxisAlignedBB boundingBox;
    public boolean onGround;
    public boolean positionChanged;    // horizontal collision
    public boolean bc;                 // vertical collision
    public boolean bd;                 // collided
    public boolean velocityChanged;
    public boolean bf;
    public boolean bg;
    public boolean dead;
    public float height;               // yOffset
    public float length;               // width
    public float width;                // height
    public float bl;
    public float bm;
    public float fallDistance;
    private int nextStepDistance;
    public double bo;
    public double bp;
    public double bq;
    public float br;
    public float bs;                   // stepHeight
    public boolean bt;                 // noClip
    public float bu;                   // entityCollisionReduction
    protected Random random;
    public int ticksLived;
    public int maxFireTicks;
    public int fireTicks;
    public int maxAirTicks;
    protected boolean bA;              // inWater
    public int noDamageTicks;
    public int airTicks;
    private boolean justCreated;
    protected boolean fireProof;
    protected DataWatcher datawatcher;
    public float bF;
    private double ridingPitchDelta;
    public double e;                   // ridingYawDelta (kept for compatibility)
    public boolean bG;
    public int bH;
    public int bI;
    public int bJ;
    public boolean bK;
    public boolean airBorne;
    public UUID uniqueId = UUID.randomUUID();

    protected org.bukkit.entity.Entity bukkitEntity;

    public Entity(World world) {
        this.id = entityCount++;
        this.aH = 1.0D;
        this.aI = false;
        this.boundingBox = AxisAlignedBB.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        this.onGround = false;
        this.bd = false;
        this.velocityChanged = false;
        this.bg = true;
        this.dead = false;
        this.height = 0.0F;
        this.length = 0.6F;
        this.width = 1.8F;
        this.bl = 0.0F;
        this.bm = 0.0F;
        this.fallDistance = 0.0F;
        this.nextStepDistance = 1;
        this.br = 0.0F;
        this.bs = 0.0F;
        this.bt = false;
        this.bu = 0.0F;
        this.random = SHARED_RANDOM;
        this.ticksLived = 0;
        this.maxFireTicks = 1;
        this.fireTicks = 0;
        this.maxAirTicks = 300;
        this.bA = false;
        this.noDamageTicks = 0;
        this.airTicks = 300;
        this.justCreated = true;
        this.fireProof = false;
        this.datawatcher = new DataWatcher();
        this.bF = 0.0F;
        this.bG = false;
        this.world = world;
        this.setPosition(0.0D, 0.0D, 0.0D);
        this.datawatcher.a(0, Byte.valueOf((byte) 0));
        this.b(); // keep subclass hook name for compatibility
    }

    // ---------------------------------------------------------------------
    // Readable aliases for abstract subclass hooks
    // ---------------------------------------------------------------------

    protected final void initEntityData() {
        this.b();
    }

    protected final void readAdditionalSaveData(NBTTagCompound tag) {
        this.a(tag);
    }

    protected final void writeAdditionalSaveData(NBTTagCompound tag) {
        this.b(tag);
    }

    // Original subclass hooks kept for compatibility
    protected abstract void b();

    protected abstract void a(NBTTagCompound nbttagcompound);

    protected abstract void b(NBTTagCompound nbttagcompound);

    // ---------------------------------------------------------------------
    // Readable API
    // ---------------------------------------------------------------------

    public DataWatcher getDataWatcher() {
        return this.datawatcher;
    }

    public void die() {
        this.dead = true;
    }

    protected void setSize(float width, float height) {
        this.length = width;
        this.width = height;
    }

    protected void setRotation(float yaw, float pitch) {
        if (Float.isNaN(yaw)) {
            yaw = 0;
        }

        if (yaw == Float.POSITIVE_INFINITY || yaw == Float.NEGATIVE_INFINITY) {
            if (this instanceof EntityPlayer) {
                System.err.println(((CraftPlayer) this.getBukkitEntity()).getName() + " was caught trying to crash the server with an invalid yaw");
                ((CraftPlayer) this.getBukkitEntity()).kickPlayer("Nope");
            }
            yaw = 0;
        }

        if (Float.isNaN(pitch)) {
            pitch = 0;
        }

        if (pitch == Float.POSITIVE_INFINITY || pitch == Float.NEGATIVE_INFINITY) {
            if (this instanceof EntityPlayer) {
                System.err.println(((CraftPlayer) this.getBukkitEntity()).getName() + " was caught trying to crash the server with an invalid pitch");
                ((CraftPlayer) this.getBukkitEntity()).kickPlayer("Nope");
            }
            pitch = 0;
        }

        this.yaw = yaw % 360.0F;
        this.pitch = pitch % 360.0F;
    }

    public void setPosition(double x, double y, double z) {
        this.locX = x;
        this.locY = y;
        this.locZ = z;
        float halfWidth = this.length / 2.0F;
        float entityHeight = this.width;

        this.boundingBox.c(
                x - (double) halfWidth,
                y - (double) this.height + (double) this.br,
                z - (double) halfWidth,
                x + (double) halfWidth,
                y - (double) this.height + (double) this.br + (double) entityHeight,
                z + (double) halfWidth
        );
    }

    public void onUpdate() {
        this.onEntityUpdate();
    }

    public void onEntityUpdate() {
        if (this.vehicle != null && this.vehicle.dead) {
            this.vehicle = null;
        }

        ++this.ticksLived;
        this.bl = this.bm;
        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        this.lastPitch = this.pitch;
        this.lastYaw = this.yaw;

        if (this.handleWaterMovement()) {
            if (!this.bA && !this.justCreated) {
                float splashSpeed = MathHelper.a(this.motX * this.motX * 0.20000000298023224D + this.motY * this.motY + this.motZ * this.motZ * 0.20000000298023224D) * 0.2F;

                if (splashSpeed > 1.0F) {
                    splashSpeed = 1.0F;
                }

                this.world.makeSound(this, "random.splash", splashSpeed, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                float waterY = (float) MathHelper.floor(this.boundingBox.b);

                int i;
                float offsetX;
                float offsetZ;

                for (i = 0; (float) i < 1.0F + this.length * 20.0F; ++i) {
                    offsetX = (this.random.nextFloat() * 2.0F - 1.0F) * this.length;
                    offsetZ = (this.random.nextFloat() * 2.0F - 1.0F) * this.length;
                    this.world.a("bubble", this.locX + (double) offsetX, (double) (waterY + 1.0F), this.locZ + (double) offsetZ, this.motX, this.motY - (double) (this.random.nextFloat() * 0.2F), this.motZ);
                }

                for (i = 0; (float) i < 1.0F + this.length * 20.0F; ++i) {
                    offsetX = (this.random.nextFloat() * 2.0F - 1.0F) * this.length;
                    offsetZ = (this.random.nextFloat() * 2.0F - 1.0F) * this.length;
                    this.world.a("splash", this.locX + (double) offsetX, (double) (waterY + 1.0F), this.locZ + (double) offsetZ, this.motX, this.motY, this.motZ);
                }
            }

            this.fallDistance = 0.0F;
            this.bA = true;
            this.fireTicks = 0;
        } else {
            this.bA = false;
        }

        if (this.world.isStatic) {
            this.fireTicks = 0;
        } else if (this.fireTicks > 0) {
            if (this.fireProof) {
                this.fireTicks -= 4;
                if (this.fireTicks < 0) {
                    this.fireTicks = 0;
                }
            } else {
                if (this.fireTicks % 20 == 0) {
                    if (this instanceof EntityLiving) {
                        EntityDamageEvent event = new EntityDamageEvent(this.getBukkitEntity(), EntityDamageEvent.DamageCause.FIRE_TICK, 1);
                        this.world.getServer().getPluginManager().callEvent(event);

                        if (!event.isCancelled()) {
                            this.damageEntity((Entity) null, event.getDamage());
                        }
                    } else {
                        this.damageEntity((Entity) null, 1);
                    }
                }

                --this.fireTicks;
            }
        }

        if (this.handleLavaMovement()) {
            this.lavaHurt();
        }

        if (this.locY < -64.0D) {
            this.outOfWorld();
        }

        if (!this.world.isStatic) {
            this.setFlag(0, this.fireTicks > 0);
            this.setFlag(2, this.vehicle != null);
        }

        this.justCreated = false;
    }

    protected void lavaHurt() {
        if (!this.fireProof) {
            if (this instanceof EntityLiving) {
                org.bukkit.Server server = this.world.getServer();
                org.bukkit.block.Block damager = null;
                org.bukkit.entity.Entity damagee = this.getBukkitEntity();

                EntityDamageByBlockEvent event = new EntityDamageByBlockEvent(damager, damagee, EntityDamageEvent.DamageCause.LAVA, 4);
                server.getPluginManager().callEvent(event);

                if (!event.isCancelled()) {
                    this.damageEntity((Entity) null, event.getDamage());
                }

                if (this.fireTicks <= 0) {
                    EntityCombustEvent combustEvent = new EntityCombustEvent(damagee);
                    server.getPluginManager().callEvent(combustEvent);

                    if (!combustEvent.isCancelled()) {
                        this.fireTicks = 600;
                    }
                } else {
                    this.fireTicks = 600;
                }
                return;
            }

            this.damageEntity((Entity) null, 4);
            this.fireTicks = 600;
        }
    }

    protected void outOfWorld() {
        this.die();
    }

    public boolean isPositionClear(double dx, double dy, double dz) {
        AxisAlignedBB movedBox = this.boundingBox.c(dx, dy, dz);
        List list = this.world.getEntities(this, movedBox);

        return list.size() <= 0 && !this.world.c(movedBox);
    }

    public void move(double dx, double dy, double dz) {
        if (this.bt) {
            this.boundingBox.d(dx, dy, dz);
            this.locX = (this.boundingBox.a + this.boundingBox.d) / 2.0D;
            this.locY = this.boundingBox.b + (double) this.height - (double) this.br;
            this.locZ = (this.boundingBox.c + this.boundingBox.f) / 2.0D;
        } else {
            this.br *= 0.4F;
            double oldX = this.locX;
            double oldZ = this.locZ;

            if (this.bf) {
                this.bf = false;
                dx *= 0.25D;
                dy *= 0.05000000074505806D;
                dz *= 0.25D;
                this.motX = 0.0D;
                this.motY = 0.0D;
                this.motZ = 0.0D;
            }

            double originalDx = dx;
            double originalDy = dy;
            double originalDz = dz;
            AxisAlignedBB originalBox = this.boundingBox.clone();
            boolean sneakingEdgeCheck = this.onGround && this.isSneaking();

            if (sneakingEdgeCheck) {
                double step = 0.05D;

                for (; dx != 0.0D && this.world.getEntities(this, this.boundingBox.c(dx, -1.0D, 0.0D)).size() == 0; originalDx = dx) {
                    if (dx < step && dx >= -step) {
                        dx = 0.0D;
                    } else if (dx > 0.0D) {
                        dx -= step;
                    } else {
                        dx += step;
                    }
                }

                for (; dz != 0.0D && this.world.getEntities(this, this.boundingBox.c(0.0D, -1.0D, dz)).size() == 0; originalDz = dz) {
                    if (dz < step && dz >= -step) {
                        dz = 0.0D;
                    } else if (dz > 0.0D) {
                        dz -= step;
                    } else {
                        dz += step;
                    }
                }
            }

            List list = this.world.getEntities(this, this.boundingBox.a(dx, dy, dz));

            for (int i = 0; i < list.size(); ++i) {
                dy = ((AxisAlignedBB) list.get(i)).b(this.boundingBox, dy);
            }

            this.boundingBox.d(0.0D, dy, 0.0D);
            if (!this.bg && originalDy != dy) {
                dz = 0.0D;
                dy = 0.0D;
                dx = 0.0D;
            }

            boolean steppedOnGround = this.onGround || originalDy != dy && originalDy < 0.0D;

            for (int i = 0; i < list.size(); ++i) {
                dx = ((AxisAlignedBB) list.get(i)).a(this.boundingBox, dx);
            }

            this.boundingBox.d(dx, 0.0D, 0.0D);
            if (!this.bg && originalDx != dx) {
                dz = 0.0D;
                dy = 0.0D;
                dx = 0.0D;
            }

            for (int i = 0; i < list.size(); ++i) {
                dz = ((AxisAlignedBB) list.get(i)).c(this.boundingBox, dz);
            }

            this.boundingBox.d(0.0D, 0.0D, dz);
            if (!this.bg && originalDz != dz) {
                dz = 0.0D;
                dy = 0.0D;
                dx = 0.0D;
            }

            double oldClimbDx;
            double oldClimbDy;
            int i;

            if (this.bs > 0.0F && steppedOnGround && (sneakingEdgeCheck || this.br < 0.05F) && (originalDx != dx || originalDz != dz)) {
                oldClimbDx = dx;
                oldClimbDy = dy;
                double oldClimbDz = dz;

                dx = originalDx;
                dy = (double) this.bs;
                dz = originalDz;
                AxisAlignedBB stepBox = this.boundingBox.clone();

                this.boundingBox.b(originalBox);
                list = this.world.getEntities(this, this.boundingBox.a(originalDx, dy, originalDz));

                for (i = 0; i < list.size(); ++i) {
                    dy = ((AxisAlignedBB) list.get(i)).b(this.boundingBox, dy);
                }

                this.boundingBox.d(0.0D, dy, 0.0D);
                if (!this.bg && originalDy != dy) {
                    dz = 0.0D;
                    dy = 0.0D;
                    dx = 0.0D;
                }

                for (i = 0; i < list.size(); ++i) {
                    dx = ((AxisAlignedBB) list.get(i)).a(this.boundingBox, dx);
                }

                this.boundingBox.d(dx, 0.0D, 0.0D);
                if (!this.bg && originalDx != dx) {
                    dz = 0.0D;
                    dy = 0.0D;
                    dx = 0.0D;
                }

                for (i = 0; i < list.size(); ++i) {
                    dz = ((AxisAlignedBB) list.get(i)).c(this.boundingBox, dz);
                }

                this.boundingBox.d(0.0D, 0.0D, dz);
                if (!this.bg && originalDz != dz) {
                    dz = 0.0D;
                    dy = 0.0D;
                    dx = 0.0D;
                }

                if (!this.bg && originalDy != dy) {
                    dz = 0.0D;
                    dy = 0.0D;
                    dx = 0.0D;
                } else {
                    dy = (double) (-this.bs);

                    for (i = 0; i < list.size(); ++i) {
                        dy = ((AxisAlignedBB) list.get(i)).b(this.boundingBox, dy);
                    }

                    this.boundingBox.d(0.0D, dy, 0.0D);
                }

                if (oldClimbDx * oldClimbDx + oldClimbDz * oldClimbDz >= dx * dx + dz * dz) {
                    dx = oldClimbDx;
                    dy = oldClimbDy;
                    dz = oldClimbDz;
                    this.boundingBox.b(stepBox);
                } else {
                    double ySizeIncrease = this.boundingBox.b - (double) ((int) this.boundingBox.b);

                    if (ySizeIncrease > 0.0D) {
                        this.br = (float) ((double) this.br + ySizeIncrease + 0.01D);
                    }
                }
            }

            this.locX = (this.boundingBox.a + this.boundingBox.d) / 2.0D;
            this.locY = this.boundingBox.b + (double) this.height - (double) this.br;
            this.locZ = (this.boundingBox.c + this.boundingBox.f) / 2.0D;
            this.positionChanged = originalDx != dx || originalDz != dz;
            this.bc = originalDy != dy;
            this.onGround = originalDy != dy && originalDy < 0.0D;
            this.bd = this.positionChanged || this.bc;
            this.updateFallState(dy, this.onGround);

            if (originalDx != dx) {
                this.motX = 0.0D;
            }

            if (originalDy != dy) {
                this.motY = 0.0D;
            }

            if (originalDz != dz) {
                this.motZ = 0.0D;
            }

            oldClimbDx = this.locX - oldX;
            oldClimbDy = this.locZ - oldZ;

            int blockX;
            int blockY;
            int blockZ;
            int typeId;

            if (this.positionChanged && this.getBukkitEntity() instanceof Vehicle) {
                Vehicle vehicle = (Vehicle) this.getBukkitEntity();
                org.bukkit.block.Block block = this.world.getWorld().getBlockAt(
                        MathHelper.floor(this.locX),
                        MathHelper.floor(this.locY - 0.20000000298023224D - (double) this.height),
                        MathHelper.floor(this.locZ)
                );

                if (originalDx > dx) {
                    block = block.getRelative(BlockFace.SOUTH);
                } else if (originalDx < dx) {
                    block = block.getRelative(BlockFace.NORTH);
                } else if (originalDz > dz) {
                    block = block.getRelative(BlockFace.WEST);
                } else if (originalDz < dz) {
                    block = block.getRelative(BlockFace.EAST);
                }

                VehicleBlockCollisionEvent event = new VehicleBlockCollisionEvent(vehicle, block);
                this.world.getServer().getPluginManager().callEvent(event);
            }

            if (this.canTriggerWalking() && !sneakingEdgeCheck && this.vehicle == null) {
                this.bm = (float) ((double) this.bm + (double) MathHelper.a(oldClimbDx * oldClimbDx + oldClimbDy * oldClimbDy) * 0.6D);
                blockX = MathHelper.floor(this.locX);
                blockY = MathHelper.floor(this.locY - 0.20000000298023224D - (double) this.height);
                blockZ = MathHelper.floor(this.locZ);
                typeId = this.world.getTypeId(blockX, blockY, blockZ);

                if (this.world.getTypeId(blockX, blockY - 1, blockZ) == CraftBlock.FENCE.id) {
                    typeId = this.world.getTypeId(blockX, blockY - 1, blockZ);
                }

                if (this.bm > (float) this.nextStepDistance && typeId > 0) {
                    ++this.nextStepDistance;
                    StepSound stepSound = CraftBlock.byId[typeId].stepSound;

                    if (this.world.getTypeId(blockX, blockY + 1, blockZ) == CraftBlock.SNOW.id) {
                        stepSound = CraftBlock.SNOW.stepSound;
                        this.world.makeSound(this, stepSound.getName(), stepSound.getVolume1() * 0.15F, stepSound.getVolume2());
                    } else if (!CraftBlock.byId[typeId].material.isLiquid()) {
                        this.world.makeSound(this, stepSound.getName(), stepSound.getVolume1() * 0.15F, stepSound.getVolume2());
                    }

                    CraftBlock.byId[typeId].b(this.world, blockX, blockY, blockZ, this);
                }
            }

            blockX = MathHelper.floor(this.boundingBox.a + 0.0010D);
            blockY = MathHelper.floor(this.boundingBox.b + 0.0010D);
            blockZ = MathHelper.floor(this.boundingBox.c + 0.0010D);
            int maxX = MathHelper.floor(this.boundingBox.d - 0.0010D);
            int maxY = MathHelper.floor(this.boundingBox.e - 0.0010D);
            int maxZ = MathHelper.floor(this.boundingBox.f - 0.0010D);

            if (this.world.a(blockX, blockY, blockZ, maxX, maxY, maxZ)) {
                for (int x = blockX; x <= maxX; ++x) {
                    for (int y = blockY; y <= maxY; ++y) {
                        for (int z = blockZ; z <= maxZ; ++z) {
                            int id = this.world.getTypeId(x, y, z);

                            if (id > 0) {
                                CraftBlock.byId[id].a(this.world, x, y, z, this);
                            }
                        }
                    }
                }
            }

            boolean wet = this.isWet();

            if (this.world.d(this.boundingBox.shrink(0.0010D, 0.0010D, 0.0010D))) {
                this.dealFireDamage(1);

                if (!wet) {
                    ++this.fireTicks;
                    if (this.fireTicks <= 0) {
                        EntityCombustEvent event = new EntityCombustEvent(this.getBukkitEntity());
                        this.world.getServer().getPluginManager().callEvent(event);

                        if (!event.isCancelled()) {
                            this.fireTicks = 300;
                        }
                    } else {
                        this.fireTicks = 300;
                    }
                }
            } else if (this.fireTicks <= 0) {
                this.fireTicks = -this.maxFireTicks;
            }

            if (wet && this.fireTicks > 0) {
                this.world.makeSound(this, "random.fizz", 0.7F, 1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                this.fireTicks = -this.maxFireTicks;
            }
        }
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void updateFallState(double yMotion, boolean onGround) {
        if (onGround) {
            if (this.fallDistance > 0.0F) {
                this.fall(this.fallDistance);
                this.fallDistance = 0.0F;
            }
        } else if (yMotion < 0.0D) {
            this.fallDistance = (float) ((double) this.fallDistance - yMotion);
        }
    }

    public AxisAlignedBB getBoundingBox() {
        return null;
    }

    protected void dealFireDamage(int amount) {
        if (!this.fireProof) {
            if (this instanceof EntityLiving) {
                EntityDamageEvent event = new EntityDamageEvent(this.getBukkitEntity(), EntityDamageEvent.DamageCause.FIRE, amount);
                this.world.getServer().getPluginManager().callEvent(event);

                if (event.isCancelled()) {
                    return;
                }

                amount = event.getDamage();
            }

            this.damageEntity((Entity) null, amount);
        }
    }

    protected void fall(float distance) {
        if (this.passenger != null) {
            this.passenger.fall(distance);
        }
    }

    public boolean isWet() {
        return this.bA || this.world.s(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
    }

    public boolean isInWater() {
        return this.bA;
    }

    public boolean handleWaterMovement() {
        return this.world.a(
                this.boundingBox.b(0.0D, -0.4000000059604645D, 0.0D).shrink(0.0010D, 0.0010D, 0.0010D),
                Material.WATER,
                this
        );
    }

    public boolean isInsideOfMaterial(Material material) {
        double eyeY = this.locY + (double) this.getEyeHeight();
        int x = MathHelper.floor(this.locX);
        int y = MathHelper.d((float) MathHelper.floor(eyeY));
        int z = MathHelper.floor(this.locZ);
        int typeId = this.world.getTypeId(x, y, z);

        if (typeId != 0 && CraftBlock.byId[typeId].material == material) {
            float fluidHeight = BlockFluids.c(this.world.getData(x, y, z)) - 0.11111111F;
            float fluidTop = (float) (y + 1) - fluidHeight;
            return eyeY < (double) fluidTop;
        }

        return false;
    }

    public float getEyeHeight() {
        return 0.0F;
    }

    public boolean handleLavaMovement() {
        return this.world.a(this.boundingBox.b(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.LAVA);
    }

    public void moveFlying(float strafe, float forward, float friction) {
        float inputMagnitude = MathHelper.c(strafe * strafe + forward * forward);

        if (inputMagnitude >= 0.01F) {
            if (inputMagnitude < 1.0F) {
                inputMagnitude = 1.0F;
            }

            inputMagnitude = friction / inputMagnitude;
            strafe *= inputMagnitude;
            forward *= inputMagnitude;
            float yawSin = MathHelper.sin(this.yaw * 3.1415927F / 180.0F);
            float yawCos = MathHelper.cos(this.yaw * 3.1415927F / 180.0F);

            this.motX += (double) (strafe * yawCos - forward * yawSin);
            this.motZ += (double) (forward * yawCos + strafe * yawSin);
        }
    }

    public float getBrightness(float partialTick) {
        int x = MathHelper.floor(this.locX);
        double yOffset = (this.boundingBox.e - this.boundingBox.b) * 0.66D;
        int y = MathHelper.floor(this.locY - (double) this.height + yOffset);
        int z = MathHelper.floor(this.locZ);

        if (this.world.a(
                MathHelper.floor(this.boundingBox.a),
                MathHelper.floor(this.boundingBox.b),
                MathHelper.floor(this.boundingBox.c),
                MathHelper.floor(this.boundingBox.d),
                MathHelper.floor(this.boundingBox.e),
                MathHelper.floor(this.boundingBox.f)
        )) {
            float brightness = this.world.n(x, y, z);
            if (brightness < this.bF) {
                brightness = this.bF;
            }
            return brightness;
        }

        return this.bF;
    }

    public void spawnIn(World world) {
        if (world == null) {
            this.die();
            this.world = ((org.bukkit.craftbukkit.CraftWorld) Bukkit.getServer().getWorlds().get(0)).getHandle();
            return;
        }

        this.world = world;
    }

    public void setLocation(double x, double y, double z, float yaw, float pitch) {
        this.lastX = this.locX = x;
        this.lastY = this.locY = y;
        this.lastZ = this.locZ = z;
        this.lastYaw = this.yaw = yaw;
        this.lastPitch = this.pitch = pitch;
        this.br = 0.0F;
        double yawDelta = (double) (this.lastYaw - yaw);

        if (yawDelta < -180.0D) {
            this.lastYaw += 360.0F;
        }

        if (yawDelta >= 180.0D) {
            this.lastYaw -= 360.0F;
        }

        this.setPosition(this.locX, this.locY, this.locZ);
        this.setRotation(yaw, pitch);
    }

    public void setPositionRotation(double x, double y, double z, float yaw, float pitch) {
        this.bo = this.lastX = this.locX = x;
        this.bp = this.lastY = this.locY = y + (double) this.height;
        this.bq = this.lastZ = this.locZ = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.setPosition(this.locX, this.locY, this.locZ);
    }

    public float getDistanceToEntity(Entity entity) {
        float dx = (float) (this.locX - entity.locX);
        float dy = (float) (this.locY - entity.locY);
        float dz = (float) (this.locZ - entity.locZ);

        return MathHelper.c(dx * dx + dy * dy + dz * dz);
    }

    public double getDistanceSquared(double x, double y, double z) {
        double dx = this.locX - x;
        double dy = this.locY - y;
        double dz = this.locZ - z;

        return dx * dx + dy * dy + dz * dz;
    }

    public double getDistance(double x, double y, double z) {
        double dx = this.locX - x;
        double dy = this.locY - y;
        double dz = this.locZ - z;

        return (double) MathHelper.a(dx * dx + dy * dy + dz * dz);
    }

    public double getDistanceSquaredToEntity(Entity entity) {
        double dx = this.locX - entity.locX;
        double dy = this.locY - entity.locY;
        double dz = this.locZ - entity.locZ;

        return dx * dx + dy * dy + dz * dz;
    }

    public void onCollideWithPlayer(EntityHuman entityhuman) {}

    public void collide(Entity entity) {
        if (entity.passenger != this && entity.vehicle != this) {
            double dx = entity.locX - this.locX;
            double dz = entity.locZ - this.locZ;
            double distanceSq = MathHelper.a(dx, dz);

            if (distanceSq >= 0.009999999776482582D) {
                distanceSq = (double) MathHelper.a(distanceSq);
                dx /= distanceSq;
                dz /= distanceSq;
                double scale = 1.0D / distanceSq;

                if (scale > 1.0D) {
                    scale = 1.0D;
                }

                dx *= scale;
                dz *= scale;
                dx *= 0.05000000074505806D;
                dz *= 0.05000000074505806D;
                dx *= (double) (1.0F - this.bu);
                dz *= (double) (1.0F - this.bu);
                this.addVelocity(-dx, 0.0D, -dz);
                entity.addVelocity(dx, 0.0D, dz);
            }
        }
    }

    public void addVelocity(double x, double y, double z) {
        this.motX += x;
        this.motY += y;
        this.motZ += z;
        this.airBorne = true;
    }

    protected void markVelocityChanged() {
        this.velocityChanged = true;
    }

    public boolean damageEntity(Entity entity, int damage) {
        this.markVelocityChanged();
        return false;
    }

    public boolean canBeCollidedWith() {
        return false;
    }

    public boolean canBePushed() {
        return false;
    }

    public void c(Entity entity, int i) {}

    public boolean save(NBTTagCompound tag) {
        String saveId = this.getSaveId();

        if (!this.dead && saveId != null) {
            tag.setString("id", saveId);
            this.writeToNBT(tag);
            return true;
        }

        return false;
    }

    public void writeToNBT(NBTTagCompound tag) {
        tag.a("Pos", (NBTBase) this.newDoubleNBTList(new double[] { this.locX, this.locY + (double) this.br, this.locZ }));
        tag.a("Motion", (NBTBase) this.newDoubleNBTList(new double[] { this.motX, this.motY, this.motZ }));

        if (Float.isNaN(this.yaw)) {
            this.yaw = 0;
        }

        if (Float.isNaN(this.pitch)) {
            this.pitch = 0;
        }

        tag.a("Rotation", (NBTBase) this.newFloatNBTList(new float[] { this.yaw, this.pitch }));
        tag.a("FallDistance", this.fallDistance);
        tag.a("Fire", (short) this.fireTicks);
        tag.a("Air", (short) this.airTicks);
        tag.a("OnGround", this.onGround);
        tag.setLong("WorldUUIDLeast", this.world.getUUID().getLeastSignificantBits());
        tag.setLong("WorldUUIDMost", this.world.getUUID().getMostSignificantBits());
        tag.setLong("UUIDLeast", this.uniqueId.getLeastSignificantBits());
        tag.setLong("UUIDMost", this.uniqueId.getMostSignificantBits());
        this.writeAdditionalSaveData(tag);
    }

    public void readFromNBT(NBTTagCompound tag) {
        NBTTagList pos = tag.l("Pos");
        NBTTagList motion = tag.l("Motion");
        NBTTagList rotation = tag.l("Rotation");

        this.motX = ((NBTTagDouble) motion.a(0)).a;
        this.motY = ((NBTTagDouble) motion.a(1)).a;
        this.motZ = ((NBTTagDouble) motion.a(2)).a;

        this.lastX = this.bo = this.locX = ((NBTTagDouble) pos.a(0)).a;
        this.lastY = this.bp = this.locY = ((NBTTagDouble) pos.a(1)).a;
        this.lastZ = this.bq = this.locZ = ((NBTTagDouble) pos.a(2)).a;
        this.lastYaw = this.yaw = ((NBTTagFloat) rotation.a(0)).a;
        this.lastPitch = this.pitch = ((NBTTagFloat) rotation.a(1)).a;
        this.fallDistance = tag.g("FallDistance");
        this.fireTicks = tag.d("Fire");
        this.airTicks = tag.d("Air");
        this.onGround = tag.m("OnGround");
        this.setPosition(this.locX, this.locY, this.locZ);

        long least = tag.getLong("UUIDLeast");
        long most = tag.getLong("UUIDMost");

        if (least != 0L && most != 0L) {
            this.uniqueId = new UUID(most, least);
        }

        this.setRotation(this.yaw, this.pitch);
        this.readAdditionalSaveData(tag);

        if (!(this.getBukkitEntity() instanceof Vehicle)) {
            if (Math.abs(this.motX) > 10.0D) {
                this.motX = 0.0D;
            }

            if (Math.abs(this.motY) > 10.0D) {
                this.motY = 0.0D;
            }

            if (Math.abs(this.motZ) > 10.0D) {
                this.motZ = 0.0D;
            }
        }

        if (this instanceof EntityPlayer) {
            org.bukkit.Server server = Bukkit.getServer();
            org.bukkit.World bworld = null;
            String worldName = tag.getString("World");

            if (tag.hasKey("WorldUUIDMost") && tag.hasKey("WorldUUIDLeast")) {
                UUID uid = new UUID(tag.getLong("WorldUUIDMost"), tag.getLong("WorldUUIDLeast"));
                bworld = server.getWorld(uid);
            } else {
                bworld = server.getWorld(worldName);
            }

            if (bworld == null) {
                EntityPlayer entityPlayer = (EntityPlayer) this;
                bworld = ((org.bukkit.craftbukkit.CraftServer) server).getServer().getWorldServer(entityPlayer.dimension).getWorld();
            }

            this.spawnIn(bworld == null ? null : ((org.bukkit.craftbukkit.CraftWorld) bworld).getHandle());
        }
    }

    protected final String getSaveId() {
        return EntityTypes.b(this);
    }

    public NBTTagList newDoubleNBTList(double... values) {
        NBTTagList list = new NBTTagList();

        for (double value : values) {
            list.a((NBTBase) (new NBTTagDouble(value)));
        }

        return list;
    }

    protected NBTTagList newFloatNBTList(float... values) {
        NBTTagList list = new NBTTagList();

        for (float value : values) {
            list.a((NBTBase) (new NBTTagFloat(value)));
        }

        return list;
    }

    public EntityItem dropItem(int itemId, int count) {
        return this.dropItemWithOffset(itemId, count, 0.0F);
    }

    public EntityItem dropItemWithOffset(int itemId, int count, float yOffset) {
        return this.dropItemStack(new ItemStack(itemId, count, 0), yOffset);
    }

    public EntityItem dropItemStack(ItemStack itemstack, float yOffset) {
        EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY + (double) yOffset, this.locZ, itemstack);

        entityitem.pickupDelay = 10;
        this.world.addEntity(entityitem);
        return entityitem;
    }

    public boolean isAlive() {
        return !this.dead;
    }

    public boolean isInsideOpaqueBlock() {
        for (int i = 0; i < 8; ++i) {
            float xOffset = ((float) ((i >> 0) % 2) - 0.5F) * this.length * 0.9F;
            float yOffset = ((float) ((i >> 1) % 2) - 0.5F) * 0.1F;
            float zOffset = ((float) ((i >> 2) % 2) - 0.5F) * this.length * 0.9F;
            int x = MathHelper.floor(this.locX + (double) xOffset);
            int y = MathHelper.floor(this.locY + (double) this.getEyeHeight() + (double) yOffset);
            int z = MathHelper.floor(this.locZ + (double) zOffset);

            if (this.world.e(x, y, z)) {
                return true;
            }
        }

        return false;
    }

    public boolean interact(EntityHuman entityhuman) {
        return false;
    }

    public AxisAlignedBB getCollisionBox(Entity entity) {
        return null;
    }

    public void updateRidden() {
        if (this.vehicle.dead) {
            this.vehicle = null;
        } else {
            this.motX = 0.0D;
            this.motY = 0.0D;
            this.motZ = 0.0D;
            this.onUpdate();

            if (this.vehicle != null) {
                this.vehicle.updatePassengerPosition();
                this.e += (double) (this.vehicle.yaw - this.vehicle.lastYaw);

                for (this.ridingPitchDelta += (double) (this.vehicle.pitch - this.vehicle.lastPitch); this.e >= 180.0D; this.e -= 360.0D) {
                }

                while (this.e < -180.0D) {
                    this.e += 360.0D;
                }

                while (this.ridingPitchDelta >= 180.0D) {
                    this.ridingPitchDelta -= 360.0D;
                }

                while (this.ridingPitchDelta < -180.0D) {
                    this.ridingPitchDelta += 360.0D;
                }

                double yawDelta = this.e * 0.5D;
                double pitchDelta = this.ridingPitchDelta * 0.5D;
                float max = 10.0F;

                if (yawDelta > (double) max) {
                    yawDelta = (double) max;
                }

                if (yawDelta < (double) (-max)) {
                    yawDelta = (double) (-max);
                }

                if (pitchDelta > (double) max) {
                    pitchDelta = (double) max;
                }

                if (pitchDelta < (double) (-max)) {
                    pitchDelta = (double) (-max);
                }

                this.e -= yawDelta;
                this.ridingPitchDelta -= pitchDelta;
                this.yaw = (float) ((double) this.yaw + yawDelta);
                this.pitch = (float) ((double) this.pitch + pitchDelta);
            }
        }
    }

    public void updatePassengerPosition() {
        this.passenger.setPosition(this.locX, this.locY + this.getMountedYOffset() + this.passenger.getYOffset(), this.locZ);
    }

    public double getYOffset() {
        return (double) this.height;
    }

    public double getMountedYOffset() {
        return (double) this.width * 0.75D;
    }

    public void mount(Entity entity) {
        this.setPassengerOf(entity);
    }

    public org.bukkit.entity.Entity getBukkitEntity() {
        if (this.bukkitEntity == null) {
            this.bukkitEntity = org.bukkit.craftbukkit.entity.CraftEntity.getEntity(this.world.getServer(), this);
        }
        return this.bukkitEntity;
    }

    public void setPassengerOf(Entity entity) {
        this.ridingPitchDelta = 0.0D;
        this.e = 0.0D;

        if (entity == null) {
            if (this.vehicle != null) {
                if ((this.getBukkitEntity() instanceof LivingEntity) && (this.vehicle.getBukkitEntity() instanceof Vehicle)) {
                    VehicleExitEvent event = new VehicleExitEvent((Vehicle) this.vehicle.getBukkitEntity(), (LivingEntity) this.getBukkitEntity());
                    this.world.getServer().getPluginManager().callEvent(event);
                }

                this.setPositionRotation(this.vehicle.locX, this.vehicle.boundingBox.b + (double) this.vehicle.width, this.vehicle.locZ, this.yaw, this.pitch);
                this.vehicle.passenger = null;
            }

            this.vehicle = null;
        } else if (this.vehicle == entity) {
            if ((this.getBukkitEntity() instanceof LivingEntity) && (this.vehicle.getBukkitEntity() instanceof Vehicle)) {
                VehicleExitEvent event = new VehicleExitEvent((Vehicle) this.vehicle.getBukkitEntity(), (LivingEntity) this.getBukkitEntity());
                this.world.getServer().getPluginManager().callEvent(event);
            }

            this.vehicle.passenger = null;
            this.vehicle = null;
            this.setPositionRotation(entity.locX, entity.boundingBox.b + (double) entity.width, entity.locZ, this.yaw, this.pitch);
        } else {
            if (this.vehicle != null) {
                this.vehicle.passenger = null;
            }

            if (entity.passenger != null) {
                entity.passenger.vehicle = null;
            }

            this.vehicle = entity;
            entity.passenger = this;
        }
    }

    public Vec3D getLookVec() {
        return null;
    }

    public void P() {}

    public ItemStack[] getEquipment() {
        return null;
    }

    public boolean isSneaking() {
        return this.getFlag(1);
    }

    public void setSneak(boolean flag) {
        this.setFlag(1, flag);
    }

    protected boolean getFlag(int index) {
        return (this.datawatcher.a(0) & 1 << index) != 0;
    }

    protected void setFlag(int index, boolean value) {
        byte flags = this.datawatcher.a(0);

        if (value) {
            this.datawatcher.watch(0, Byte.valueOf((byte) (flags | 1 << index)));
        } else {
            this.datawatcher.watch(0, Byte.valueOf((byte) (flags & ~(1 << index))));
        }
    }

    public void onStruckByLightning(EntityWeatherStorm entityweatherstorm) {
        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(entityweatherstorm.getBukkitEntity(), this.getBukkitEntity(), EntityDamageEvent.DamageCause.LIGHTNING, 5);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        this.dealFireDamage(event.getDamage());

        ++this.fireTicks;
        if (this.fireTicks == 0) {
            this.fireTicks = 300;
        }
    }

    public void onKillEntity(EntityLiving entityliving) {}

    protected boolean pushOutOfBlocks(double x, double y, double z) {
        int blockX = MathHelper.floor(x);
        int blockY = MathHelper.floor(y);
        int blockZ = MathHelper.floor(z);
        double localX = x - (double) blockX;
        double localY = y - (double) blockY;
        double localZ = z - (double) blockZ;

        if (this.world.e(blockX, blockY, blockZ)) {
            boolean westFree = !this.world.e(blockX - 1, blockY, blockZ);
            boolean eastFree = !this.world.e(blockX + 1, blockY, blockZ);
            boolean downFree = !this.world.e(blockX, blockY - 1, blockZ);
            boolean upFree = !this.world.e(blockX, blockY + 1, blockZ);
            boolean northFree = !this.world.e(blockX, blockY, blockZ - 1);
            boolean southFree = !this.world.e(blockX, blockY, blockZ + 1);
            byte direction = -1;
            double minDistance = 9999.0D;

            if (westFree && localX < minDistance) {
                minDistance = localX;
                direction = 0;
            }

            if (eastFree && 1.0D - localX < minDistance) {
                minDistance = 1.0D - localX;
                direction = 1;
            }

            if (downFree && localY < minDistance) {
                minDistance = localY;
                direction = 2;
            }

            if (upFree && 1.0D - localY < minDistance) {
                minDistance = 1.0D - localY;
                direction = 3;
            }

            if (northFree && localZ < minDistance) {
                minDistance = localZ;
                direction = 4;
            }

            if (southFree && 1.0D - localZ < minDistance) {
                minDistance = 1.0D - localZ;
                direction = 5;
            }

            float push = this.random.nextFloat() * 0.2F + 0.1F;

            if (direction == 0) {
                this.motX = (double) (-push);
            }

            if (direction == 1) {
                this.motX = (double) push;
            }

            if (direction == 2) {
                this.motY = (double) (-push);
            }

            if (direction == 3) {
                this.motY = (double) push;
            }

            if (direction == 4) {
                this.motZ = (double) (-push);
            }

            if (direction == 5) {
                this.motZ = (double) push;
            }
        }

        return false;
    }


    public DataWatcher aa() {
        return this.getDataWatcher();
    }

    protected void b(float f, float f1) {
        this.setSize(f, f1);
    }

    protected void c(float f, float f1) {
        this.setRotation(f, f1);
    }

    public void m_() {
        this.onUpdate();
    }

    public void R() {
        this.onEntityUpdate();
    }

    protected void ab() {
        this.lavaHurt();
    }

    protected void Y() {
        this.outOfWorld();
    }

    public boolean d(double d0, double d1, double d2) {
        return this.isPositionClear(d0, d1, d2);
    }

    protected boolean n() {
        return this.canTriggerWalking();
    }

    protected void a(double d0, boolean flag) {
        this.updateFallState(d0, flag);
    }

    public AxisAlignedBB e_() {
        return this.getBoundingBox();
    }

    protected void burn(int i) {
        this.dealFireDamage(i);
    }

    protected void a(float f) {
        this.fall(f);
    }

    public boolean ac() {
        return this.isWet();
    }

    public boolean ad() {
        return this.isInWater();
    }

    public boolean f_() {
        return this.handleWaterMovement();
    }

    public boolean a(Material material) {
        return this.isInsideOfMaterial(material);
    }

    public float t() {
        return this.getEyeHeight();
    }

    public boolean ae() {
        return this.handleLavaMovement();
    }

    public void a(float f, float f1, float f2) {
        this.moveFlying(f, f1, f2);
    }

    public float c(float f) {
        return this.getBrightness(f);
    }

    public float f(Entity entity) {
        return this.getDistanceToEntity(entity);
    }

    public double e(double d0, double d1, double d2) {
        return this.getDistanceSquared(d0, d1, d2);
    }

    public double f(double d0, double d1, double d2) {
        return this.getDistance(d0, d1, d2);
    }

    public double g(Entity entity) {
        return this.getDistanceSquaredToEntity(entity);
    }

    public void b(EntityHuman entityhuman) {
        this.onCollideWithPlayer(entityhuman);
    }

    public void b(double d0, double d1, double d2) {
        this.addVelocity(d0, d1, d2);
    }

    protected void af() {
        this.markVelocityChanged();
    }

    public boolean l_() {
        return this.canBeCollidedWith();
    }

    public boolean d_() {
        return this.canBePushed();
    }

    public boolean c(NBTTagCompound nbttagcompound) {
        return this.save(nbttagcompound);
    }

    public void d(NBTTagCompound nbttagcompound) {
        this.writeToNBT(nbttagcompound);
    }

    public void e(NBTTagCompound nbttagcompound) {
        this.readFromNBT(nbttagcompound);
    }

    protected final String ag() {
        return this.getSaveId();
    }

    public NBTTagList a(double... adouble) {
        return this.newDoubleNBTList(adouble);
    }

    protected NBTTagList a(float... afloat) {
        return this.newFloatNBTList(afloat);
    }

    public EntityItem b(int i, int j) {
        return this.dropItem(i, j);
    }

    public EntityItem a(int i, int j, float f) {
        return this.dropItemWithOffset(i, j, f);
    }

    public EntityItem a(ItemStack itemstack, float f) {
        return this.dropItemStack(itemstack, f);
    }

    public boolean T() {
        return this.isAlive();
    }

    public boolean K() {
        return this.isInsideOpaqueBlock();
    }

    public boolean a(EntityHuman entityhuman) {
        return this.interact(entityhuman);
    }

    public AxisAlignedBB a_(Entity entity) {
        return this.getCollisionBox(entity);
    }

    public void E() {
        this.updateRidden();
    }

    public void f() {
        this.updatePassengerPosition();
    }

    public double I() {
        return this.getYOffset();
    }

    public double m() {
        return this.getMountedYOffset();
    }

    public Vec3D Z() {
        return this.getLookVec();
    }

    protected boolean d(int i) {
        return this.getFlag(i);
    }

    protected void a(int i, boolean flag) {
        this.setFlag(i, flag);
    }

    public void a(EntityWeatherStorm entityweatherstorm) {
        this.onStruckByLightning(entityweatherstorm);
    }

    public void a(EntityLiving entityliving) {
        this.onKillEntity(entityliving);
    }

    protected boolean g(double d0, double d1, double d2) {
        return this.pushOutOfBlocks(d0, d1, d2);
    }
}