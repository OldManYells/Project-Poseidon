package net.minecraft.server;

import com.legacyminecraft.poseidon.block.ChestStateBehaviour;

import java.util.Random;

public class BlockChest extends BlockContainer {

    private Random a = new Random();
    private static final ChestStateBehaviour CHEST_STATE_SERVICE = ChestStateBehaviour.getInstance();

    protected BlockChest(int i) {
        super(i, Material.WOOD);
        this.textureId = 26;
    }

    public int a(int i) {
        return CHEST_STATE_SERVICE.resolveTextureBySide(i, this.textureId);
    }

    public boolean canPlace(World world, int i, int j, int k) {
        ChestStateBehaviour.TypeIdQuery query = new ChestStateBehaviour.TypeIdQuery() {
            public int getTypeId(int x, int y, int z) {
                return world.getTypeId(x, y, z);
            }
        };
        int l = CHEST_STATE_SERVICE.countAdjacentChests(query, i, j, k, this.id);

        return CHEST_STATE_SERVICE.canPlace(
                l,
                this.g(world, i - 1, j, k),
                this.g(world, i + 1, j, k),
                this.g(world, i, j, k - 1),
                this.g(world, i, j, k + 1)
        );
    }

    private boolean g(World world, int i, int j, int k) {
        return world.getTypeId(i, j, k) != this.id ? false : (world.getTypeId(i - 1, j, k) == this.id ? true : (world.getTypeId(i + 1, j, k) == this.id ? true : (world.getTypeId(i, j, k - 1) == this.id ? true : world.getTypeId(i, j, k + 1) == this.id)));
    }

    public void remove(World world, int i, int j, int k) {
        TileEntityChest tileentitychest = (TileEntityChest) world.getTileEntity(i, j, k);

        for (int l = 0; l < tileentitychest.getSize(); ++l) {
            ItemStack itemstack = tileentitychest.getItem(l);

            if (itemstack != null) {
                float f = CHEST_STATE_SERVICE.resolveDropOffset(this.a);
                float f1 = CHEST_STATE_SERVICE.resolveDropOffset(this.a);
                float f2 = CHEST_STATE_SERVICE.resolveDropOffset(this.a);

                while (itemstack.count > 0) {
                    int i1 = CHEST_STATE_SERVICE.resolveDropStackChunk(this.a, itemstack.count);

                    itemstack.count -= i1;
                    EntityItem entityitem = new EntityItem(world, (double) ((float) i + f), (double) ((float) j + f1), (double) ((float) k + f2), new ItemStack(itemstack.id, i1, itemstack.getData()));
                    float f3 = 0.05F;

                    entityitem.motX = CHEST_STATE_SERVICE.resolveDropHorizontalMotion(this.a, f3);
                    entityitem.motY = CHEST_STATE_SERVICE.resolveDropVerticalMotion(this.a, f3, 0.2D);
                    entityitem.motZ = CHEST_STATE_SERVICE.resolveDropHorizontalMotion(this.a, f3);
                    world.addEntity(entityitem);
                }
                tileentitychest.setItem(l, null);
            }
        }

        super.remove(world, i, j, k);
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        IInventory object = (TileEntityChest) world.getTileEntity(i, j, k);

        if (CHEST_STATE_SERVICE.shouldBlockAccessFromTop(
                world.e(i, j + 1, k),
                world.getTypeId(i - 1, j, k) == this.id && world.e(i - 1, j + 1, k),
                world.getTypeId(i + 1, j, k) == this.id && world.e(i + 1, j + 1, k),
                world.getTypeId(i, j, k - 1) == this.id && world.e(i, j + 1, k - 1),
                world.getTypeId(i, j, k + 1) == this.id && world.e(i, j + 1, k + 1)
        )) {
            return true;
        } else {
            object = CHEST_STATE_SERVICE.composeChestInventory(new ChestStateBehaviour.ChestAccess() {
                public int getTypeId(int x, int y, int z) {
                    return world.getTypeId(x, y, z);
                }

                public TileEntityChest getChest(int x, int y, int z) {
                    return (TileEntityChest) world.getTileEntity(x, y, z);
                }
            }, i, j, k, this.id, object);

            if (world.isStatic) {
                return true;
            } else {
                entityhuman.a(object);
                return true;
            }
        }
    }

    protected TileEntity a_() {
        return new TileEntityChest();
    }
}
