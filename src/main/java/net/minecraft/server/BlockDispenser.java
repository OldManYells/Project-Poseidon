package net.minecraft.server;

import com.legacyminecraft.poseidon.block.DispenserStateBehaviour;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;

import java.util.Random;

// CraftBukkit start
// CraftBukkit end

public class BlockDispenser extends BlockContainer {

    private Random a = new Random();
    private static final DispenserStateBehaviour DISPENSER_STATE_SERVICE = DispenserStateBehaviour.getInstance();

    protected BlockDispenser(int i) {
        super(i, Material.STONE);
        this.textureId = 45;
    }

    public int c() {
        return DISPENSER_STATE_SERVICE.resolveTickRate();
    }

    public int a(int i, Random random) {
        return DISPENSER_STATE_SERVICE.resolveDroppedBlockId(Block.DISPENSER.id);
    }

    public void c(World world, int i, int j, int k) {
        super.c(world, i, j, k);
        this.g(world, i, j, k);
    }

    private void g(World world, int i, int j, int k) {
        if (!world.isStatic) {
            int l = world.getTypeId(i, j, k - 1);
            int i1 = world.getTypeId(i, j, k + 1);
            int j1 = world.getTypeId(i - 1, j, k);
            int k1 = world.getTypeId(i + 1, j, k);
            world.setData(i, j, k, DISPENSER_STATE_SERVICE.resolveDefaultFacing(Block.o[l], Block.o[i1], Block.o[j1], Block.o[k1]));
        }
    }

    public int a(int i) {
        return DISPENSER_STATE_SERVICE.resolveTextureBySide(i, this.textureId);
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        if (world.isStatic) {
            return true;
        } else {
            TileEntityDispenser tileentitydispenser = (TileEntityDispenser) world.getTileEntity(i, j, k);

            entityhuman.a(tileentitydispenser);
            return true;
        }
    }

    // CraftBukkit - private -> public
    public void dispense(World world, int i, int j, int k, Random random) {
        int l = world.getData(i, j, k);
        DispenserStateBehaviour.Facing facing = DISPENSER_STATE_SERVICE.resolveDispenseFacing(l);
        int b0 = facing.offsetX;
        int b1 = facing.offsetZ;

        TileEntityDispenser tileentitydispenser = (TileEntityDispenser) world.getTileEntity(i, j, k);
        // CraftBukkit start
        int dispenseSlot = tileentitydispenser.findDispenseSlot();
        ItemStack itemstack = null;
        if (dispenseSlot > -1) {
            itemstack = tileentitydispenser.getContents()[dispenseSlot];

            // Copy item stack, because we want it to have 1 item
            itemstack = new ItemStack(itemstack.id, 1, itemstack.damage);
        }
        // CraftBukkit end

        double d0 = DISPENSER_STATE_SERVICE.resolveDispenseOriginX(i, b0);
        double d1 = DISPENSER_STATE_SERVICE.resolveDispenseOriginY(j);
        double d2 = DISPENSER_STATE_SERVICE.resolveDispenseOriginZ(k, b1);

        if (itemstack == null) {
            world.e(1001, i, j, k, 0);
        } else {
            // CraftBukkit start
            double d3 = DISPENSER_STATE_SERVICE.resolveDispenseBaseSpeed(random);
            double motX = DISPENSER_STATE_SERVICE.resolveDispenseVelocityComponent(b0, d3, random.nextGaussian());
            double motY = DISPENSER_STATE_SERVICE.resolveDispenseVerticalVelocity(0.20000000298023224D, random.nextGaussian());
            double motZ = DISPENSER_STATE_SERVICE.resolveDispenseVelocityComponent(b1, d3, random.nextGaussian());

            org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            org.bukkit.inventory.ItemStack bukkitItem = new CraftItemStack(itemstack).clone();

            BlockDispenseEvent event = new BlockDispenseEvent(block, bukkitItem, new Vector(motX, motY, motZ));
            world.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return;
            }

            // Actually remove the item
            tileentitydispenser.splitStack(dispenseSlot, 1);

            motX = event.getVelocity().getX();
            motY = event.getVelocity().getY();
            motZ = event.getVelocity().getZ();

            itemstack = new ItemStack(event.getItem().getTypeId(), event.getItem().getAmount(), event.getItem().getDurability());
            // CraftBukkit end

            if (itemstack.id == Item.ARROW.id) {
                EntityArrow entityarrow = new EntityArrow(world, d0, d1, d2);

                entityarrow.a((double) b0, 0.10000000149011612D, (double) b1, 1.1F, 6.0F);
                entityarrow.fromPlayer = true;
                world.addEntity(entityarrow);
                world.e(1002, i, j, k, 0);
            } else if (itemstack.id == Item.EGG.id) {
                EntityEgg entityegg = new EntityEgg(world, d0, d1, d2);

                entityegg.a((double) b0, 0.10000000149011612D, (double) b1, 1.1F, 6.0F);
                world.addEntity(entityegg);
                world.e(1002, i, j, k, 0);
            } else if (itemstack.id == Item.SNOW_BALL.id) {
                EntitySnowball entitysnowball = new EntitySnowball(world, d0, d1, d2);

                entitysnowball.a((double) b0, 0.10000000149011612D, (double) b1, 1.1F, 6.0F);
                world.addEntity(entitysnowball);
                world.e(1002, i, j, k, 0);
            } else {
                EntityItem entityitem = new EntityItem(world, d0, d1 - 0.3D, d2, itemstack);
                // CraftBukkit start
                // Base dispense speed and spread were moved earlier for event mutation support.
                entityitem.motX = motX;
                entityitem.motY = motY;
                entityitem.motZ = motZ;
                // CraftBukkit end
                world.addEntity(entityitem);
                world.e(1000, i, j, k, 0);
            }

            world.e(2000, i, j, k, DISPENSER_STATE_SERVICE.resolveDispenseEventData(b0, b1));
        }
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        boolean sourceIsPowerSource = l > 0 && Block.byId[l].isPowerSource();
        boolean poweredSelf = world.isBlockIndirectlyPowered(i, j, k);
        boolean poweredAbove = world.isBlockIndirectlyPowered(i, j + 1, k);

        if (DISPENSER_STATE_SERVICE.shouldScheduleDispense(sourceIsPowerSource, poweredSelf, poweredAbove)) {
            world.c(i, j, k, this.id, this.c());
        }
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (DISPENSER_STATE_SERVICE.shouldDispenseNow(world.isBlockIndirectlyPowered(i, j, k), world.isBlockIndirectlyPowered(i, j + 1, k))) {
            this.dispense(world, i, j, k, random);
        }
    }

    protected TileEntity a_() {
        return new TileEntityDispenser();
    }

    public void postPlace(World world, int i, int j, int k, EntityLiving entityliving) {
        world.setData(i, j, k, DISPENSER_STATE_SERVICE.resolvePostPlaceData(entityliving.yaw));
    }

    public void remove(World world, int i, int j, int k) {
        TileEntityDispenser tileentitydispenser = (TileEntityDispenser) world.getTileEntity(i, j, k);

        for (int l = 0; l < tileentitydispenser.getSize(); ++l) {
            ItemStack itemstack = tileentitydispenser.getItem(l);

            if (itemstack != null) {
                float f = DISPENSER_STATE_SERVICE.resolveDropOffset(this.a);
                float f1 = DISPENSER_STATE_SERVICE.resolveDropOffset(this.a);
                float f2 = DISPENSER_STATE_SERVICE.resolveDropOffset(this.a);

                while (itemstack.count > 0) {
                    int i1 = DISPENSER_STATE_SERVICE.resolveDropStackChunk(this.a, itemstack.count);

                    itemstack.count -= i1;
                    EntityItem entityitem = new EntityItem(world, (double) ((float) i + f), (double) ((float) j + f1), (double) ((float) k + f2), new ItemStack(itemstack.id, i1, itemstack.getData()));
                    entityitem.motX = DISPENSER_STATE_SERVICE.resolveDropMotion(this.a, false);
                    entityitem.motY = DISPENSER_STATE_SERVICE.resolveDropMotion(this.a, true);
                    entityitem.motZ = DISPENSER_STATE_SERVICE.resolveDropMotion(this.a, false);
                    world.addEntity(entityitem);
                }
                tileentitydispenser.setItem(l, null);
            }
        }

        super.remove(world, i, j, k);
    }
}
