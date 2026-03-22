package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.MinecartLifecycleBehaviour;
import com.legacyminecraft.poseidon.entity.MinecartCollisionBehaviour;
import com.legacyminecraft.poseidon.entity.MinecartNbtBehaviour;
import org.bukkit.Location;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.*;

import java.util.List;

// CraftBukkit start
// CraftBukkit end

public class EntityMinecart extends Entity implements IInventory {
    private static final MinecartLifecycleBehaviour MINECART_LIFECYCLE_BEHAVIOUR = MinecartLifecycleBehaviour.getInstance();
    private static final MinecartCollisionBehaviour MINECART_COLLISION_BEHAVIOUR = MinecartCollisionBehaviour.getInstance();
    private static final MinecartNbtBehaviour MINECART_NBT_BEHAVIOUR = MinecartNbtBehaviour.getInstance();

    private ItemStack[] items;
    public int damage;
    public int b;
    public int c;
    private boolean i;
    public int type;
    public int e;
    public double f;
    public double g;
    private static final int[][][] matrix = new int[][][] { { { 0, 0, -1}, { 0, 0, 1}}, { { -1, 0, 0}, { 1, 0, 0}}, { { -1, -1, 0}, { 1, 0, 0}}, { { -1, 0, 0}, { 1, -1, 0}}, { { 0, 0, -1}, { 0, -1, 1}}, { { 0, -1, -1}, { 0, 0, 1}}, { { 0, 0, 1}, { 1, 0, 0}}, { { 0, 0, 1}, { -1, 0, 0}}, { { 0, 0, -1}, { -1, 0, 0}}, { { 0, 0, -1}, { 1, 0, 0}}};
    private int k;
    private double l;
    private double m;
    private double n;
    private double o;
    private double p;

    // CraftBukkit start
    public boolean slowWhenEmpty = true;
    public double derailedX = 0.5;
    public double derailedY = 0.5;
    public double derailedZ = 0.5;
    public double flyingX = 0.95;
    public double flyingY = 0.95;
    public double flyingZ = 0.95;
    public double maxSpeed = 0.4D;

    public ItemStack[] getContents() {
        return this.items;
    }
    // CraftBukkit end

    public EntityMinecart(World world) {
        super(world);
        this.items = new ItemStack[27]; // CraftBukkit
        this.damage = 0;
        this.b = 0;
        this.c = 1;
        this.i = false;
        this.aI = true;
        this.b(0.98F, 0.7F);
        this.height = this.width / 2.0F;
    }

    protected boolean n() {
        return false;
    }

    protected void b() {}

    public AxisAlignedBB a_(Entity entity) {
        return entity.boundingBox;
    }

    public AxisAlignedBB e_() {
        return null;
    }

    public boolean d_() {
        return true;
    }

    public EntityMinecart(World world, double d0, double d1, double d2, int i) {
        this(world);
        this.setPosition(d0, d1 + (double) this.height, d2);
        this.motX = 0.0D;
        this.motY = 0.0D;
        this.motZ = 0.0D;
        this.lastX = d0;
        this.lastY = d1;
        this.lastZ = d2;
        this.type = i;

        this.world.getServer().getPluginManager().callEvent(new VehicleCreateEvent((Vehicle) this.getBukkitEntity())); // CraftBukkit
    }

    public double m() {
        return (double) this.width * 0.0D - 0.30000001192092896D;
    }

    public boolean damageEntity(Entity entity, int i) {
        return MINECART_LIFECYCLE_BEHAVIOUR.handleDamage(this, entity, i);
    }

    public boolean l_() {
        return !this.dead;
    }

    public void die() {
        MINECART_LIFECYCLE_BEHAVIOUR.dropInventoryContents(this);
        super.die();
    }

    public void m_() {
        // CraftBukkit start
        double prevX = this.locX;
        double prevY = this.locY;
        double prevZ = this.locZ;
        float prevYaw = this.yaw;
        float prevPitch = this.pitch;
        // CraftBukkit end

        if (this.b > 0) {
            --this.b;
        }

        if (this.damage > 0) {
            --this.damage;
        }

        double d0;

        if (this.world.isStatic && this.k > 0) {
            if (this.k > 0) {
                double d1 = this.locX + (this.l - this.locX) / (double) this.k;
                double d2 = this.locY + (this.m - this.locY) / (double) this.k;
                double d3 = this.locZ + (this.n - this.locZ) / (double) this.k;

                for (d0 = this.o - (double) this.yaw; d0 < -180.0D; d0 += 360.0D) {
                    ;
                }

                while (d0 >= 180.0D) {
                    d0 -= 360.0D;
                }

                this.yaw = (float) ((double) this.yaw + d0 / (double) this.k);
                this.pitch = (float) ((double) this.pitch + (this.p - (double) this.pitch) / (double) this.k);
                --this.k;
                this.setPosition(d1, d2, d3);
                this.c(this.yaw, this.pitch);
            } else {
                this.setPosition(this.locX, this.locY, this.locZ);
                this.c(this.yaw, this.pitch);
            }
        } else {
            this.lastX = this.locX;
            this.lastY = this.locY;
            this.lastZ = this.locZ;
            this.motY -= 0.03999999910593033D;
            int i = MathHelper.floor(this.locX);
            int j = MathHelper.floor(this.locY);
            int k = MathHelper.floor(this.locZ);

            if (BlockMinecartTrack.g(this.world, i, j - 1, k)) {
                --j;
            }

            // CraftBukkit
            double d4 = this.maxSpeed;
            boolean flag = false;

            d0 = 0.0078125D;
            int l = this.world.getTypeId(i, j, k);

            if (BlockMinecartTrack.c(l)) {
                Vec3D vec3d = this.h(this.locX, this.locY, this.locZ);
                int i1 = this.world.getData(i, j, k);

                this.locY = (double) j;
                boolean flag1 = false;
                boolean flag2 = false;

                if (l == Block.GOLDEN_RAIL.id) {
                    flag1 = (i1 & 8) != 0;
                    flag2 = !flag1;
                }

                if (((BlockMinecartTrack) Block.byId[l]).f()) {
                    i1 &= 7;
                }

                if (i1 >= 2 && i1 <= 5) {
                    this.locY = (double) (j + 1);
                }

                if (i1 == 2) {
                    this.motX -= d0;
                }

                if (i1 == 3) {
                    this.motX += d0;
                }

                if (i1 == 4) {
                    this.motZ += d0;
                }

                if (i1 == 5) {
                    this.motZ -= d0;
                }

                int[][] aint = matrix[i1];
                double d5 = (double) (aint[1][0] - aint[0][0]);
                double d6 = (double) (aint[1][2] - aint[0][2]);
                double d7 = Math.sqrt(d5 * d5 + d6 * d6);
                double d8 = this.motX * d5 + this.motZ * d6;

                if (d8 < 0.0D) {
                    d5 = -d5;
                    d6 = -d6;
                }

                double d9 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);

                this.motX = d9 * d5 / d7;
                this.motZ = d9 * d6 / d7;
                double d10;

                if (flag2) {
                    d10 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
                    if (d10 < 0.03D) {
                        this.motX *= 0.0D;
                        this.motY *= 0.0D;
                        this.motZ *= 0.0D;
                    } else {
                        this.motX *= 0.5D;
                        this.motY *= 0.0D;
                        this.motZ *= 0.5D;
                    }
                }

                d10 = 0.0D;
                double d11 = (double) i + 0.5D + (double) aint[0][0] * 0.5D;
                double d12 = (double) k + 0.5D + (double) aint[0][2] * 0.5D;
                double d13 = (double) i + 0.5D + (double) aint[1][0] * 0.5D;
                double d14 = (double) k + 0.5D + (double) aint[1][2] * 0.5D;

                d5 = d13 - d11;
                d6 = d14 - d12;
                double d15;
                double d16;
                double d17;

                if (d5 == 0.0D) {
                    this.locX = (double) i + 0.5D;
                    d10 = this.locZ - (double) k;
                } else if (d6 == 0.0D) {
                    this.locZ = (double) k + 0.5D;
                    d10 = this.locX - (double) i;
                } else {
                    d16 = this.locX - d11;
                    d15 = this.locZ - d12;
                    d17 = (d16 * d5 + d15 * d6) * 2.0D;
                    d10 = d17;
                }

                this.locX = d11 + d5 * d10;
                this.locZ = d12 + d6 * d10;
                this.setPosition(this.locX, this.locY + (double) this.height, this.locZ);
                d16 = this.motX;
                d15 = this.motZ;
                if (this.passenger != null) {
                    d16 *= 0.75D;
                    d15 *= 0.75D;
                }

                if (d16 < -d4) {
                    d16 = -d4;
                }

                if (d16 > d4) {
                    d16 = d4;
                }

                if (d15 < -d4) {
                    d15 = -d4;
                }

                if (d15 > d4) {
                    d15 = d4;
                }

                this.move(d16, 0.0D, d15);
                if (aint[0][1] != 0 && MathHelper.floor(this.locX) - i == aint[0][0] && MathHelper.floor(this.locZ) - k == aint[0][2]) {
                    this.setPosition(this.locX, this.locY + (double) aint[0][1], this.locZ);
                } else if (aint[1][1] != 0 && MathHelper.floor(this.locX) - i == aint[1][0] && MathHelper.floor(this.locZ) - k == aint[1][2]) {
                    this.setPosition(this.locX, this.locY + (double) aint[1][1], this.locZ);
                }

                // CraftBukkit
                if (this.passenger != null || !this.slowWhenEmpty) {
                    this.motX *= 0.996999979019165D;
                    this.motY *= 0.0D;
                    this.motZ *= 0.996999979019165D;
                } else {
                    if (this.type == 2) {
                        d17 = (double) MathHelper.a(this.f * this.f + this.g * this.g);
                        if (d17 > 0.01D) {
                            flag = true;
                            this.f /= d17;
                            this.g /= d17;
                            double d18 = 0.04D;

                            this.motX *= 0.800000011920929D;
                            this.motY *= 0.0D;
                            this.motZ *= 0.800000011920929D;
                            this.motX += this.f * d18;
                            this.motZ += this.g * d18;
                        } else {
                            this.motX *= 0.8999999761581421D;
                            this.motY *= 0.0D;
                            this.motZ *= 0.8999999761581421D;
                        }
                    }

                    this.motX *= 0.9599999785423279D;
                    this.motY *= 0.0D;
                    this.motZ *= 0.9599999785423279D;
                }

                Vec3D vec3d1 = this.h(this.locX, this.locY, this.locZ);

                if (vec3d1 != null && vec3d != null) {
                    double d19 = (vec3d.b - vec3d1.b) * 0.05D;

                    d9 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
                    if (d9 > 0.0D) {
                        this.motX = this.motX / d9 * (d9 + d19);
                        this.motZ = this.motZ / d9 * (d9 + d19);
                    }

                    this.setPosition(this.locX, vec3d1.b, this.locZ);
                }

                int j1 = MathHelper.floor(this.locX);
                int k1 = MathHelper.floor(this.locZ);

                if (j1 != i || k1 != k) {
                    d9 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
                    this.motX = d9 * (double) (j1 - i);
                    this.motZ = d9 * (double) (k1 - k);
                }

                double d20;

                if (this.type == 2) {
                    d20 = (double) MathHelper.a(this.f * this.f + this.g * this.g);
                    if (d20 > 0.01D && this.motX * this.motX + this.motZ * this.motZ > 0.0010D) {
                        this.f /= d20;
                        this.g /= d20;
                        if (this.f * this.motX + this.g * this.motZ < 0.0D) {
                            this.f = 0.0D;
                            this.g = 0.0D;
                        } else {
                            this.f = this.motX;
                            this.g = this.motZ;
                        }
                    }
                }

                if (flag1) {
                    d20 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
                    if (d20 > 0.01D) {
                        double d21 = 0.06D;

                        this.motX += this.motX / d20 * d21;
                        this.motZ += this.motZ / d20 * d21;
                    } else if (i1 == 1) {
                        if (this.world.e(i - 1, j, k)) {
                            this.motX = 0.02D;
                        } else if (this.world.e(i + 1, j, k)) {
                            this.motX = -0.02D;
                        }
                    } else if (i1 == 0) {
                        if (this.world.e(i, j, k - 1)) {
                            this.motZ = 0.02D;
                        } else if (this.world.e(i, j, k + 1)) {
                            this.motZ = -0.02D;
                        }
                    }
                }
            } else {
                if (this.motX < -d4) {
                    this.motX = -d4;
                }

                if (this.motX > d4) {
                    this.motX = d4;
                }

                if (this.motZ < -d4) {
                    this.motZ = -d4;
                }

                if (this.motZ > d4) {
                    this.motZ = d4;
                }

                if (this.onGround) {
                    // CraftBukkit start
                    this.motX *= this.derailedX;
                    this.motY *= this.derailedY;
                    this.motZ *= this.derailedZ;
                    // CraftBukkit start
                }

                this.move(this.motX, this.motY, this.motZ);
                if (!this.onGround) {
                    // CraftBukkit start
                    this.motX *= this.flyingX;
                    this.motY *= this.flyingY;
                    this.motZ *= this.flyingZ;
                    // CraftBukkit start
                }
            }

            this.pitch = 0.0F;
            double d22 = this.lastX - this.locX;
            double d23 = this.lastZ - this.locZ;

            if (d22 * d22 + d23 * d23 > 0.0010D) {
                this.yaw = (float) (Math.atan2(d23, d22) * 180.0D / 3.141592653589793D);
                if (this.i) {
                    this.yaw += 180.0F;
                }
            }

            double d24;

            for (d24 = (double) (this.yaw - this.lastYaw); d24 >= 180.0D; d24 -= 360.0D) {
                ;
            }

            while (d24 < -180.0D) {
                d24 += 360.0D;
            }

            if (d24 < -170.0D || d24 >= 170.0D) {
                this.yaw += 180.0F;
                this.i = !this.i;
            }

            this.c(this.yaw, this.pitch);

            // CraftBukkit start
            org.bukkit.World bworld = this.world.getWorld();
            Location from = new Location(bworld, prevX, prevY, prevZ, prevYaw, prevPitch);
            Location to = new Location(bworld, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            Vehicle vehicle = (Vehicle) this.getBukkitEntity();

            this.world.getServer().getPluginManager().callEvent(new VehicleUpdateEvent(vehicle));

            if (!from.equals(to)) {
                this.world.getServer().getPluginManager().callEvent(new VehicleMoveEvent(vehicle, from, to));
            }
            // CraftBukkit end

            List list = this.world.b((Entity) this, this.boundingBox.b(0.20000000298023224D, 0.0D, 0.20000000298023224D));

            if (list != null && list.size() > 0) {
                for (int l1 = 0; l1 < list.size(); ++l1) {
                    Entity entity = (Entity) list.get(l1);

                    if (entity != this.passenger && entity.d_() && entity instanceof EntityMinecart) {
                        entity.collide(this);
                    }
                }
            }

            if (this.passenger != null && this.passenger.dead) {
                this.passenger.vehicle = null; // CraftBukkit
                this.passenger = null;
            }

            if (flag && this.random.nextInt(4) == 0) {
                --this.e;
                if (this.e < 0) {
                    this.f = this.g = 0.0D;
                }

                this.world.a("largesmoke", this.locX, this.locY + 0.8D, this.locZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public Vec3D h(double d0, double d1, double d2) {
        int i = MathHelper.floor(d0);
        int j = MathHelper.floor(d1);
        int k = MathHelper.floor(d2);

        if (BlockMinecartTrack.g(this.world, i, j - 1, k)) {
            --j;
        }

        int l = this.world.getTypeId(i, j, k);

        if (BlockMinecartTrack.c(l)) {
            int i1 = this.world.getData(i, j, k);

            d1 = (double) j;
            if (((BlockMinecartTrack) Block.byId[l]).f()) {
                i1 &= 7;
            }

            if (i1 >= 2 && i1 <= 5) {
                d1 = (double) (j + 1);
            }

            int[][] aint = matrix[i1];
            double d3 = 0.0D;
            double d4 = (double) i + 0.5D + (double) aint[0][0] * 0.5D;
            double d5 = (double) j + 0.5D + (double) aint[0][1] * 0.5D;
            double d6 = (double) k + 0.5D + (double) aint[0][2] * 0.5D;
            double d7 = (double) i + 0.5D + (double) aint[1][0] * 0.5D;
            double d8 = (double) j + 0.5D + (double) aint[1][1] * 0.5D;
            double d9 = (double) k + 0.5D + (double) aint[1][2] * 0.5D;
            double d10 = d7 - d4;
            double d11 = (d8 - d5) * 2.0D;
            double d12 = d9 - d6;

            if (d10 == 0.0D) {
                d0 = (double) i + 0.5D;
                d3 = d2 - (double) k;
            } else if (d12 == 0.0D) {
                d2 = (double) k + 0.5D;
                d3 = d0 - (double) i;
            } else {
                double d13 = d0 - d4;
                double d14 = d2 - d6;
                double d15 = (d13 * d10 + d14 * d12) * 2.0D;

                d3 = d15;
            }

            d0 = d4 + d10 * d3;
            d1 = d5 + d11 * d3;
            d2 = d6 + d12 * d3;
            if (d11 < 0.0D) {
                ++d1;
            }

            if (d11 > 0.0D) {
                d1 += 0.5D;
            }

            return Vec3D.create(d0, d1, d2);
        } else {
            return null;
        }
    }

    protected void b(NBTTagCompound nbttagcompound) {
        MINECART_NBT_BEHAVIOUR.writeNbt(this, nbttagcompound);
    }

    protected void a(NBTTagCompound nbttagcompound) {
        MINECART_NBT_BEHAVIOUR.readNbt(this, nbttagcompound);
    }

    public void collide(Entity entity) {
        MINECART_COLLISION_BEHAVIOUR.handleCollision(this, entity);
    }

    public int getSize() {
        return 27;
    }

    public ItemStack getItem(int i) {
        return this.items[i];
    }

    public ItemStack splitStack(int i, int j) {
        if (this.items[i] != null) {
            ItemStack itemstack;

            if (this.items[i].count <= j) {
                itemstack = this.items[i];
                this.items[i] = null;
                return itemstack;
            } else {
                itemstack = this.items[i].a(j);
                if (this.items[i].count == 0) {
                    this.items[i] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    public void setItem(int i, ItemStack itemstack) {
        this.items[i] = itemstack;
        if (itemstack != null && itemstack.count > this.getMaxStackSize()) {
            itemstack.count = this.getMaxStackSize();
        }
    }

    public String getName() {
        return "Minecart";
    }

    public int getMaxStackSize() {
        return 64;
    }

    public void update() {}

    public boolean a(EntityHuman entityhuman) {
        return MINECART_LIFECYCLE_BEHAVIOUR.interact(this, entityhuman);
    }

    public boolean a_(EntityHuman entityhuman) {
        return this.dead ? false : entityhuman.g(this) <= 64.0D;
    }

    public double poseidonCollisionReductionFactor() {
        return (double) (1.0F - this.bu);
    }

    public void poseidonApplyCollisionPush(double x, double y, double z) {
        this.b(x, y, z);
    }

    public void poseidonMarkDamaged() {
        this.af();
    }

    public void poseidonDropEntityItem(int itemId, int count, float spread) {
        this.a(itemId, count, spread);
    }

    public float poseidonRandomFloat() {
        return this.random.nextFloat();
    }

    public int poseidonRandomInt(int bound) {
        return this.random.nextInt(bound);
    }

    public double poseidonRandomGaussian() {
        return this.random.nextGaussian();
    }
}
