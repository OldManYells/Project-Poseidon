package net.minecraft.server;

import com.legacyminecraft.poseidon.block.TrapdoorStateBehaviour;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockTrapdoor extends Block {
    private final TrapdoorStateBehaviour trapdoorStateService = TrapdoorStateBehaviour.getInstance();

    protected BlockTrapdoor(int i, Material material) {
        super(i, material);
        this.textureId = 84;
        if (material == Material.ORE) {
            ++this.textureId;
        }

        float f = 0.5F;
        float f1 = 1.0F;

        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
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
        this.c(iblockaccess.getData(i, j, k));
    }

    public void c(int i) {
        TrapdoorStateBehaviour.Bounds bounds = trapdoorStateService.resolveBounds(i);
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

            world.setData(i, j, k, trapdoorStateService.toggleOpenBit(l));
            world.a(entityhuman, 1003, i, j, k, 0);
            return true;
        }
    }

    public void a(World world, int i, int j, int k, boolean flag) {
        int l = world.getData(i, j, k);
        if (trapdoorStateService.shouldToggleOpenState(l, flag)) {
            world.setData(i, j, k, trapdoorStateService.toggleOpenBit(l));
            world.a((EntityHuman) null, 1003, i, j, k, 0);
        }
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (!world.isStatic) {
            int i1 = world.getData(i, j, k);
            TrapdoorStateBehaviour.AttachmentOffset attachmentOffset = trapdoorStateService.resolveAttachmentOffset(i1);
            if (!this.supportQuery(world).isBlockSolid(i + attachmentOffset.getX(), j + attachmentOffset.getY(), k + attachmentOffset.getZ())) {
                world.setTypeId(i, j, k, 0);
                this.g(world, i, j, k, i1);
            }

            // CraftBukkit start
            if (l > 0 && Block.byId[l] != null && Block.byId[l].isPowerSource()) {
                org.bukkit.World bworld = world.getWorld();
                org.bukkit.block.Block block = bworld.getBlockAt(i, j, k);

                int power = block.getBlockPower();
                int oldPower = (world.getData(i, j, k) & 4) > 0 ? 15 : 0;

                if (oldPower == 0 ^ power == 0) {
                    BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, oldPower, power);
                    world.getServer().getPluginManager().callEvent(eventRedstone);

                    this.a(world, i, j, k, eventRedstone.getNewCurrent() > 0);
                }
                // CraftBukkit end
            }
        }
    }

    public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
        this.a(world, i, j, k);
        return super.a(world, i, j, k, vec3d, vec3d1);
    }

    public void postPlace(World world, int i, int j, int k, int l) {
        world.setData(i, j, k, trapdoorStateService.resolvePostPlaceData(l));
        doPhysics(world, i, j, k, Block.REDSTONE_WIRE.id); // CraftBukkit
    }

    public boolean canPlace(World world, int i, int j, int k, int l) {
        return trapdoorStateService.canPlaceOnSide(this.supportQuery(world), i, j, k, l);
    }

    public static boolean d(int i) {
        return TrapdoorStateBehaviour.getInstance().isOpen(i);
    }

    private TrapdoorStateBehaviour.SupportQuery supportQuery(final World world) {
        return new TrapdoorStateBehaviour.SupportQuery() {
            public boolean isBlockSolid(int x, int y, int z) {
                return world.e(x, y, z);
            }
        };
    }
}
