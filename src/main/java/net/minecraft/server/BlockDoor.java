package net.minecraft.server;

import com.legacyminecraft.poseidon.block.DoorStateBehaviour;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.Random;

public class BlockDoor extends Block {
    private final DoorStateBehaviour doorStateService = DoorStateBehaviour.getInstance();

    protected BlockDoor(int i, Material material) {
        super(i, material);
        this.textureId = 97;
        if (material == Material.ORE) {
            ++this.textureId;
        }

        float f = 0.5F;
        float f1 = 1.0F;

        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

    public int a(int i, int j) {
        return doorStateService.resolveTextureIndex(i, j, this.textureId);
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        this.a(world, i, j, k);
        return super.e(world, i, j, k);
    }

    public void a(IBlockAccess iblockaccess, int i, int j, int k) {
        this.c(this.d(iblockaccess.getData(i, j, k)));
    }

    public void c(int i) {
        DoorStateBehaviour.Bounds bounds = doorStateService.resolveBounds(i);
        this.a(
                bounds.getMinX(),
                bounds.getMinY(),
                bounds.getMinZ(),
                bounds.getMaxX(),
                bounds.getMaxY(),
                bounds.getMaxZ()
        );
    }

    public void b(World world, int i, int j, int k, EntityHuman entityhuman) {
        this.interact(world, i, j, k, entityhuman);
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        if (this.material == Material.ORE) {
            return true;
        } else {
            int l = world.getData(i, j, k);

            if (doorStateService.isUpperHalf(l)) {
                if (world.getTypeId(i, j - 1, k) == this.id) {
                    this.interact(world, i, j - 1, k, entityhuman);
                }

                return true;
            } else {
                if (world.getTypeId(i, j + 1, k) == this.id) {
                    world.setData(i, j + 1, k, doorStateService.resolveUpperDataFromLowerToggle(l));
                }

                world.setData(i, j, k, doorStateService.toggleOpenBit(l));
                world.b(i, j - 1, k, i, j, k);
                world.a(entityhuman, 1003, i, j, k, 0);
                return true;
            }
        }
    }

    public void setDoor(World world, int i, int j, int k, boolean flag) {
        int l = world.getData(i, j, k);

        if (doorStateService.isUpperHalf(l)) {
            if (world.getTypeId(i, j - 1, k) == this.id) {
                this.setDoor(world, i, j - 1, k, flag);
            }
        } else {
            boolean flag1 = doorStateService.isOpen(world.getData(i, j, k));

            if (flag1 != flag) {
                if (world.getTypeId(i, j + 1, k) == this.id) {
                    world.setData(i, j + 1, k, doorStateService.resolveUpperDataFromLowerToggle(l));
                }

                world.setData(i, j, k, doorStateService.toggleOpenBit(l));
                world.b(i, j - 1, k, i, j, k);
                world.a((EntityHuman) null, 1003, i, j, k, 0);
            }
        }
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        int i1 = world.getData(i, j, k);

        if (doorStateService.isUpperHalf(i1)) {
            if (doorStateService.shouldRemoveUpperHalf(world.getTypeId(i, j - 1, k), this.id)) {
                world.setTypeId(i, j, k, 0);
            }

            if (l > 0 && Block.byId[l].isPowerSource()) {
                this.doPhysics(world, i, j - 1, k, l);
            }
        } else {
            boolean flag = false;
            int aboveTypeId = world.getTypeId(i, j + 1, k);
            boolean hasSupportBelow = world.e(i, j - 1, k);

            if (doorStateService.shouldRemoveLowerHalf(aboveTypeId, hasSupportBelow, this.id)) {
                world.setTypeId(i, j, k, 0);
                flag = true;
                if (doorStateService.shouldAlsoRemoveUpperHalfWhenLowerRemoved(!hasSupportBelow, aboveTypeId, this.id)) {
                    world.setTypeId(i, j + 1, k, 0);
                }
            }

            if (flag) {
                if (!world.isStatic) {
                    this.g(world, i, j, k, i1);
                }
            } else if (l > 0 && Block.byId[l].isPowerSource()) {
                // CraftBukkit start
                org.bukkit.World bworld = world.getWorld();
                org.bukkit.block.Block block = bworld.getBlockAt(i, j, k);
                org.bukkit.block.Block blockTop = bworld.getBlockAt(i, j + 1, k);

                int power = block.getBlockPower();
                int powerTop = blockTop.getBlockPower();
                if (powerTop > power) power = powerTop;
                int oldPower = (world.getData(i, j, k) & 4) > 0 ? 15 : 0;

                if (oldPower == 0 ^ power == 0) {
                    BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, oldPower, power);
                    world.getServer().getPluginManager().callEvent(eventRedstone);

                    this.setDoor(world, i, j, k, eventRedstone.getNewCurrent() > 0);
                }
                // CraftBukkit end
            }
        }
    }

    public int a(int i, Random random) {
        return doorStateService.resolveDropItemId(i, this.material == Material.ORE, Item.IRON_DOOR.id, Item.WOOD_DOOR.id);
    }

    public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
        this.a(world, i, j, k);
        return super.a(world, i, j, k, vec3d, vec3d1);
    }

    public int d(int i) {
        return doorStateService.resolveBoundingOrientation(i);
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return doorStateService.canPlace(
                j,
                world.e(i, j - 1, k),
                super.canPlace(world, i, j, k),
                super.canPlace(world, i, j + 1, k)
        );
    }

    public static boolean e(int i) {
        return DoorStateBehaviour.isOpenStatic(i);
    }

    public int e() {
        return 1;
    }
}
