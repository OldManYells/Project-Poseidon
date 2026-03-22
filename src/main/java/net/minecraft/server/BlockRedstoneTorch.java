package net.minecraft.server;

import com.legacyminecraft.poseidon.block.RedstoneTorchStateBehaviour;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockRedstoneTorch extends BlockTorch {

    private boolean isOn = false;
    private static List b = new ArrayList();
    private final RedstoneTorchStateBehaviour redstoneTorchStateService = RedstoneTorchStateBehaviour.getInstance();

    public int a(int i, int j) {
        return i == 1 ? Block.REDSTONE_WIRE.a(i, j) : super.a(i, j);
    }

    private boolean a(World world, int i, int j, int k, boolean flag) {
        return redstoneTorchStateService.recordAndCheckBurnout(b, i, j, k, world.getTime(), flag, 8);
    }

    protected BlockRedstoneTorch(int i, int j, boolean flag) {
        super(i, j);
        this.isOn = flag;
        this.a(true);
    }

    public int c() {
        return 2;
    }

    public void c(World world, int i, int j, int k) {
        if (world.getData(i, j, k) == 0) {
            super.c(world, i, j, k);
        }

        if (this.isOn) {
            redstoneTorchStateService.notifyAdjacentBlocks(
                    new RedstoneTorchStateBehaviour.NeighborNotifier() {
                        @Override
                        public void applyPhysics(int x, int y, int z, int blockId) {
                            world.applyPhysics(x, y, z, blockId);
                        }
                    },
                    i,
                    j,
                    k,
                    this.id
            );
        }
    }

    public void remove(World world, int i, int j, int k) {
        if (this.isOn) {
            redstoneTorchStateService.notifyAdjacentBlocks(
                    new RedstoneTorchStateBehaviour.NeighborNotifier() {
                        @Override
                        public void applyPhysics(int x, int y, int z, int blockId) {
                            world.applyPhysics(x, y, z, blockId);
                        }
                    },
                    i,
                    j,
                    k,
                    this.id
            );
        }
    }

    public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        int i1 = iblockaccess.getData(i, j, k);
        return redstoneTorchStateService.canProvidePower(this.isOn, i1, l);
    }

    private boolean g(World world, int i, int j, int k) {
        int l = world.getData(i, j, k);
        return redstoneTorchStateService.isReceivingIndirectPower(
                l,
                i,
                j,
                k,
                new RedstoneTorchStateBehaviour.IndirectPowerQuery() {
                    @Override
                    public boolean isBlockFaceIndirectlyPowered(int x, int y, int z, int face) {
                        return world.isBlockFaceIndirectlyPowered(x, y, z, face);
                    }
                }
        );
    }

    public void a(World world, int i, int j, int k, Random random) {
        boolean flag = this.g(world, i, j, k);

        redstoneTorchStateService.purgeExpiredUpdates(b, world.getTime(), 100L);

        // CraftBukkit start
        org.bukkit.plugin.PluginManager manager = world.getServer().getPluginManager();
        org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
        int oldCurrent = this.isOn ? 15 : 0;

        BlockRedstoneEvent event = new BlockRedstoneEvent(block, oldCurrent, oldCurrent);
        // CraftBukkit end

        if (this.isOn) {
            if (flag) {
                // CraftBukkit start
                if (oldCurrent != 0) {
                    event.setNewCurrent(0);
                    manager.callEvent(event);
                    if (event.getNewCurrent() != 0) {
                        return;
                    }
                }
                // CraftBukkit end

                world.setTypeIdAndData(i, j, k, Block.REDSTONE_TORCH_OFF.id, world.getData(i, j, k));
                if (this.a(world, i, j, k, true)) {
                    world.makeSound((double) ((float) i + 0.5F), (double) ((float) j + 0.5F), (double) ((float) k + 0.5F), "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

                    for (int l = 0; l < 5; ++l) {
                        double d0 = (double) i + random.nextDouble() * 0.6D + 0.2D;
                        double d1 = (double) j + random.nextDouble() * 0.6D + 0.2D;
                        double d2 = (double) k + random.nextDouble() * 0.6D + 0.2D;

                        world.a("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        } else if (!flag && !this.a(world, i, j, k, false)) {
            // CraftBukkit start
            if (oldCurrent != 15) {
                event.setNewCurrent(15);
                manager.callEvent(event);
                if (event.getNewCurrent() != 15) {
                    return;
                }
            }
            // CraftBukkit end

            world.setTypeIdAndData(i, j, k, Block.REDSTONE_TORCH_ON.id, world.getData(i, j, k));
        }
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        super.doPhysics(world, i, j, k, l);
        world.c(i, j, k, this.id, this.c());
    }

    public boolean d(World world, int i, int j, int k, int l) {
        return l == 0 ? this.a(world, i, j, k, l) : false;
    }

    public int a(int i, Random random) {
        return Block.REDSTONE_TORCH_ON.id;
    }

    public boolean isPowerSource() {
        return true;
    }
}
