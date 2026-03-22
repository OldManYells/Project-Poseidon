package net.minecraft.server;

import com.legacyminecraft.poseidon.block.ButtonPlacementAndPowerBehaviour;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.Random;

public class BlockButton extends Block {
    private final ButtonPlacementAndPowerBehaviour buttonPlacementAndPowerService = ButtonPlacementAndPowerBehaviour.getInstance();

    protected BlockButton(int i, int j) {
        super(i, j, Material.ORIENTABLE);
        this.a(true);
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return null;
    }

    public int c() {
        return 20;
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public boolean canPlace(World world, int i, int j, int k, int l) {
        return buttonPlacementAndPowerService.canPlaceOnSide(this.supportQuery(world), i, j, k, l);
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return buttonPlacementAndPowerService.canPlace(this.supportQuery(world), i, j, k);
    }

    public void postPlace(World world, int i, int j, int k, int l) {
        world.setData(i, j, k, buttonPlacementAndPowerService.resolvePostPlaceData(
                this.supportQuery(world),
                i,
                j,
                k,
                l,
                world.getData(i, j, k)
        ));
    }

    private int g(World world, int i, int j, int k) {
        return buttonPlacementAndPowerService.resolveFallbackFacing(this.supportQuery(world), i, j, k);
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (this.h(world, i, j, k)) {
            if (buttonPlacementAndPowerService.isAttachedSupportMissing(
                    this.supportQuery(world),
                    i,
                    j,
                    k,
                    world.getData(i, j, k)
            )) {
                this.g(world, i, j, k, world.getData(i, j, k));
                world.setTypeId(i, j, k, 0);
            }
        }
    }

    private boolean h(World world, int i, int j, int k) {
        if (!this.canPlace(world, i, j, k)) {
            this.g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
            return false;
        } else {
            return true;
        }
    }

    public void a(IBlockAccess iblockaccess, int i, int j, int k) {
        ButtonPlacementAndPowerBehaviour.Bounds bounds =
                buttonPlacementAndPowerService.resolveBounds(iblockaccess.getData(i, j, k));
        if (bounds != null) {
            this.a(
                    bounds.getMinX(),
                    bounds.getMinY(),
                    bounds.getMinZ(),
                    bounds.getMaxX(),
                    bounds.getMaxY(),
                    bounds.getMaxZ()
            );
        }
    }

    public void b(World world, int i, int j, int k, EntityHuman entityhuman) {
        this.interact(world, i, j, k, entityhuman);
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        int l = world.getData(i, j, k);
        int i1 = buttonPlacementAndPowerService.extractFacing(l);
        int j1 = buttonPlacementAndPowerService.calculateTogglePowerBit(l);

        if (j1 == 0) {
            return true;
        } else {
            // CraftBukkit start
            org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            int old = (j1 != 8) ? 1 : 0;
            int current = (j1 == 8) ? 1 : 0;

            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
            world.getServer().getPluginManager().callEvent(eventRedstone);

            if ((eventRedstone.getNewCurrent() > 0) != (j1 == 8)) {
                return true;
            }
            // CraftBukkit end

            world.setData(i, j, k, buttonPlacementAndPowerService.composeData(i1, j1));
            world.b(i, j, k, i, j, k);
            world.makeSound((double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D, "random.click", 0.3F, 0.6F);
            world.applyPhysics(i, j, k, this.id);
            this.applyPhysicsToAttachedBlock(world, i, j, k, i1);

            world.c(i, j, k, this.id, this.c());
            return true;
        }
    }

    public void remove(World world, int i, int j, int k) {
        int l = world.getData(i, j, k);

        if ((l & 8) > 0) {
            world.applyPhysics(i, j, k, this.id);
            this.applyPhysicsToAttachedBlock(world, i, j, k, buttonPlacementAndPowerService.extractFacing(l));
        }

        super.remove(world, i, j, k);
    }

    public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return buttonPlacementAndPowerService.isPressed(iblockaccess.getData(i, j, k));
    }

    public boolean d(World world, int i, int j, int k, int l) {
        return buttonPlacementAndPowerService.isPoweringSide(world.getData(i, j, k), l);
    }

    public boolean isPowerSource() {
        return true;
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (!world.isStatic) {
            int l = world.getData(i, j, k);

            if ((l & 8) != 0) {
                // CraftBukkit start
                org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);

                BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 1, 0);
                world.getServer().getPluginManager().callEvent(eventRedstone);

                if (eventRedstone.getNewCurrent() > 0) return;
                // CraftBukkit end

                int i1 = buttonPlacementAndPowerService.extractFacing(l);
                world.setData(i, j, k, buttonPlacementAndPowerService.composeData(i1, 0));
                world.applyPhysics(i, j, k, this.id);
                this.applyPhysicsToAttachedBlock(world, i, j, k, i1);

                world.makeSound((double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D, "random.click", 0.3F, 0.5F);
                world.b(i, j, k, i, j, k);
            }
        }
    }

    private void applyPhysicsToAttachedBlock(World world, int x, int y, int z, int facing) {
        ButtonPlacementAndPowerBehaviour.NeighborOffset neighborOffset =
                buttonPlacementAndPowerService.resolveAttachmentOffset(facing);
        world.applyPhysics(
                x + neighborOffset.getX(),
                y + neighborOffset.getY(),
                z + neighborOffset.getZ(),
                this.id
        );
    }

    private ButtonPlacementAndPowerBehaviour.SupportQuery supportQuery(final World world) {
        return new ButtonPlacementAndPowerBehaviour.SupportQuery() {
            public boolean isBlockSolid(int x, int y, int z) {
                return world.e(x, y, z);
            }
        };
    }
}
