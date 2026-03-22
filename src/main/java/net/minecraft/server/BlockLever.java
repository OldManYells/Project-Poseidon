package net.minecraft.server;

import com.legacyminecraft.poseidon.block.LeverPlacementAndPowerBehaviour;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockLever extends Block {
    private final LeverPlacementAndPowerBehaviour leverPlacementAndPowerService = LeverPlacementAndPowerBehaviour.getInstance();

    protected BlockLever(int i, int j) {
        super(i, j, Material.ORIENTABLE);
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return null;
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public boolean canPlace(World world, int i, int j, int k, int l) {
        return leverPlacementAndPowerService.canPlaceOnSide(this.supportQuery(world), i, j, k, l);
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return leverPlacementAndPowerService.canPlace(this.supportQuery(world), i, j, k);
    }

    public void postPlace(World world, int i, int j, int k, int l) {
        int resolvedData = leverPlacementAndPowerService.resolvePostPlaceData(
                this.supportQuery(world),
                this.randomSource(world),
                i,
                j,
                k,
                l,
                world.getData(i, j, k)
        );
        if (resolvedData == -1) {
            this.g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
        } else {
            world.setData(i, j, k, resolvedData);
        }
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (this.g(world, i, j, k)) {
            if (leverPlacementAndPowerService.isAttachedSupportMissing(
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

    private boolean g(World world, int i, int j, int k) {
        if (!this.canPlace(world, i, j, k)) {
            this.g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
            return false;
        } else {
            return true;
        }
    }

    public void a(IBlockAccess iblockaccess, int i, int j, int k) {
        LeverPlacementAndPowerBehaviour.Bounds bounds =
                leverPlacementAndPowerService.resolveBounds(iblockaccess.getData(i, j, k));
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
        if (world.isStatic) {
            return true;
        } else {
            int l = world.getData(i, j, k);
            int i1 = leverPlacementAndPowerService.extractOrientation(l);
            int j1 = leverPlacementAndPowerService.calculateTogglePowerBit(l);

            // CraftBukkit start - Interact Lever
            org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            int old = (j1 != 8) ? 1 : 0;
            int current = (j1 == 8) ? 1 : 0;

            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
            world.getServer().getPluginManager().callEvent(eventRedstone);

            if ((eventRedstone.getNewCurrent() > 0) != (j1 == 8)) {
                return true;
            }
            // CraftBukkit end

            world.setData(i, j, k, leverPlacementAndPowerService.composeData(i1, j1));
            world.b(i, j, k, i, j, k);
            world.makeSound((double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D, "random.click", 0.3F, j1 > 0 ? 0.6F : 0.5F);
            world.applyPhysics(i, j, k, this.id);
            this.applyPhysicsToAttachedBlock(world, i, j, k, i1);

            return true;
        }
    }

    public void remove(World world, int i, int j, int k) {
        int l = world.getData(i, j, k);

        if (leverPlacementAndPowerService.isPowered(l)) {
            world.applyPhysics(i, j, k, this.id);
            this.applyPhysicsToAttachedBlock(world, i, j, k, leverPlacementAndPowerService.extractOrientation(l));
        }

        super.remove(world, i, j, k);
    }

    public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return leverPlacementAndPowerService.isPowered(iblockaccess.getData(i, j, k));
    }

    public boolean d(World world, int i, int j, int k, int l) {
        return leverPlacementAndPowerService.isPoweringSide(world.getData(i, j, k), l);
    }

    public boolean isPowerSource() {
        return true;
    }

    private void applyPhysicsToAttachedBlock(World world, int x, int y, int z, int orientation) {
        LeverPlacementAndPowerBehaviour.NeighborOffset neighborOffset =
                leverPlacementAndPowerService.resolveAttachmentOffset(orientation);
        world.applyPhysics(
                x + neighborOffset.getX(),
                y + neighborOffset.getY(),
                z + neighborOffset.getZ(),
                this.id
        );
    }

    private LeverPlacementAndPowerBehaviour.SupportQuery supportQuery(final World world) {
        return new LeverPlacementAndPowerBehaviour.SupportQuery() {
            public boolean isBlockSolid(int x, int y, int z) {
                return world.e(x, y, z);
            }
        };
    }

    private LeverPlacementAndPowerBehaviour.RandomSource randomSource(final World world) {
        return new LeverPlacementAndPowerBehaviour.RandomSource() {
            public int nextInt(int bound) {
                return world.random.nextInt(bound);
            }
        };
    }
}
