package net.minecraft.server;

import com.legacyminecraft.poseidon.block.FurnaceStateBehaviour;

import java.util.Random;

public class BlockFurnace extends BlockContainer {

    private Random a = new Random();
    private final boolean b;
    private static boolean c = false;
    private static final FurnaceStateBehaviour FURNACE_STATE_SERVICE = FurnaceStateBehaviour.getInstance();

    protected BlockFurnace(int i, boolean flag) {
        super(i, Material.STONE);
        this.b = flag;
        this.textureId = 45;
    }

    public int a(int i, Random random) {
        return FURNACE_STATE_SERVICE.resolveDropItemId(Block.FURNACE.id);
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

            world.setData(i, j, k, FURNACE_STATE_SERVICE.resolveDefaultFacing(
                    Block.o[l],
                    Block.o[i1],
                    Block.o[j1],
                    Block.o[k1]
            ));
        }
    }

    public int a(int i) {
        return FURNACE_STATE_SERVICE.resolveTextureBySide(i, this.textureId);
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        if (world.isStatic) {
            return true;
        } else {
            TileEntityFurnace tileentityfurnace = (TileEntityFurnace) world.getTileEntity(i, j, k);

            entityhuman.a(tileentityfurnace);
            return true;
        }
    }

    public static void a(boolean flag, World world, int i, int j, int k) {
        int l = world.getData(i, j, k);
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity == null) return; // CraftBukkit

        c = true;
        world.setTypeId(
                i,
                j,
                k,
                FURNACE_STATE_SERVICE.resolveBlockIdForBurningState(flag, Block.BURNING_FURNACE.id, Block.FURNACE.id)
        );

        c = false;
        world.setData(i, j, k, l);
        tileentity.j();
        world.setTileEntity(i, j, k, tileentity);
    }

    protected TileEntity a_() {
        return new TileEntityFurnace();
    }

    public void postPlace(World world, int i, int j, int k, EntityLiving entityliving) {
        world.setData(i, j, k, FURNACE_STATE_SERVICE.resolvePlacementFacingFromYaw(entityliving.yaw));
    }

    public void remove(World world, int i, int j, int k) {
        if (!c) {
            TileEntityFurnace tileentityfurnace = (TileEntityFurnace) world.getTileEntity(i, j, k);

            if (tileentityfurnace == null) return; // CraftBukkit
            for (int l = 0; l < tileentityfurnace.getSize(); ++l) {
                ItemStack itemstack = tileentityfurnace.getItem(l);

                if (itemstack != null) {
                    float f = FURNACE_STATE_SERVICE.resolveDropOffset(this.a);
                    float f1 = FURNACE_STATE_SERVICE.resolveDropOffset(this.a);
                    float f2 = FURNACE_STATE_SERVICE.resolveDropOffset(this.a);

                    while (itemstack.count > 0) {
                        int i1 = FURNACE_STATE_SERVICE.resolveDropStackChunk(this.a, itemstack.count);

                        itemstack.count -= i1;
                        EntityItem entityitem = new EntityItem(world, (double) ((float) i + f), (double) ((float) j + f1), (double) ((float) k + f2), new ItemStack(itemstack.id, i1, itemstack.getData()));
                        float f3 = 0.05F;

                        entityitem.motX = FURNACE_STATE_SERVICE.resolveDropHorizontalMotion(this.a, f3);
                        entityitem.motY = FURNACE_STATE_SERVICE.resolveDropVerticalMotion(this.a, f3, 0.2D);
                        entityitem.motZ = FURNACE_STATE_SERVICE.resolveDropHorizontalMotion(this.a, f3);
                        world.addEntity(entityitem);
                    }
                    tileentityfurnace.setItem(l, null);
                }
            }
        }

        super.remove(world, i, j, k);
    }
}
