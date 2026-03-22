package net.minecraft.server;

import com.legacyminecraft.poseidon.block.FireSpreadBehaviour;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.material.MaterialData;

import java.util.Random;

// CraftBukkit start
// CraftBukkit end

public class BlockFire extends Block {

    private int[] a = new int[256];
    private int[] b = new int[256];
    private static final FireSpreadBehaviour FIRE_SPREAD_POLICY_SERVICE = FireSpreadBehaviour.getInstance();

    protected BlockFire(int i, int j) {
        super(i, j, Material.FIRE);
        this.a(true);
    }

    public void h() {
        this.a(Block.WOOD.id, 5, 20);
        this.a(Block.FENCE.id, 5, 20);
        this.a(Block.WOOD_STAIRS.id, 5, 20);
        this.a(Block.LOG.id, 5, 5);
        this.a(Block.LEAVES.id, 30, 60);
        this.a(Block.BOOKSHELF.id, 30, 20);
        this.a(Block.TNT.id, 15, 100);
        this.a(Block.LONG_GRASS.id, 60, 100);
        this.a(Block.WOOL.id, 30, 60);
    }

    private void a(int i, int j, int k) {
        FIRE_SPREAD_POLICY_SERVICE.setFlammability(this.a, this.b, i, j, k);
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

    public int a(Random random) {
        return FIRE_SPREAD_POLICY_SERVICE.resolveDropCount();
    }

    public int c() {
        return FIRE_SPREAD_POLICY_SERVICE.resolveTickRate();
    }

    public void a(World world, int i, int j, int k, Random random) {
        boolean flag = FIRE_SPREAD_POLICY_SERVICE.isEternalBase(world.getTypeId(i, j - 1, k), Block.NETHERRACK.id);
        if (FIRE_SPREAD_POLICY_SERVICE.shouldRemoveForInvalidPlacement(this.canPlace(world, i, j, k))) {
            world.setTypeId(i, j, k, 0);
        }

        boolean rainedOnSelfOrNeighbors = world.s(i, j, k) || world.s(i - 1, j, k) || world.s(i + 1, j, k) || world.s(i, j, k - 1) || world.s(i, j, k + 1);
        if (FIRE_SPREAD_POLICY_SERVICE.shouldRemoveForRain(flag, world.v(), rainedOnSelfOrNeighbors)) {
            world.setTypeId(i, j, k, 0);
        } else {
            int l = world.getData(i, j, k);

            int nextAge = FIRE_SPREAD_POLICY_SERVICE.nextFireAge(l, random.nextInt(3));
            if (nextAge != l) {
                world.setRawData(i, j, k, nextAge);
                l = nextAge;
            }

            world.c(i, j, k, this.id, this.c());
            boolean hasBurnableNeighbor = this.g(world, i, j, k);
            if (FIRE_SPREAD_POLICY_SERVICE.shouldRemoveWithoutSupport(flag, hasBurnableNeighbor, world.e(i, j - 1, k), l)) {
                world.setTypeId(i, j, k, 0);
            } else if (FIRE_SPREAD_POLICY_SERVICE.shouldRemoveAtMaxAge(flag, this.b(world, i, j - 1, k), l, random.nextInt(4))) {
                world.setTypeId(i, j, k, 0);
            } else {
                this.a(world, i + 1, j, k, 300, random, l);
                this.a(world, i - 1, j, k, 300, random, l);
                this.a(world, i, j - 1, k, 250, random, l);
                this.a(world, i, j + 1, k, 250, random, l);
                this.a(world, i, j, k - 1, 300, random, l);
                this.a(world, i, j, k + 1, 300, random, l);

                // CraftBukkit start - Call to stop spread of fire.
                org.bukkit.Server server = world.getServer();
                org.bukkit.World bworld = world.getWorld();

                IgniteCause igniteCause = BlockIgniteEvent.IgniteCause.SPREAD;
                org.bukkit.block.Block fromBlock = bworld.getBlockAt(i, j, k);
                // CraftBukkit end

                for (int i1 = i - 1; i1 <= i + 1; ++i1) {
                    for (int j1 = k - 1; j1 <= k + 1; ++j1) {
                        for (int k1 = j - 1; k1 <= j + 4; ++k1) {
                            if (i1 != i || k1 != j || j1 != k) {
                                int l1 = FIRE_SPREAD_POLICY_SERVICE.resolveVerticalSpreadBound(j, k1, 100);

                                int i2 = this.h(world, i1, k1, j1);

                                if (i2 > 0) {
                                    int j2 = FIRE_SPREAD_POLICY_SERVICE.resolveSpreadChanceFromNeighbor(i2, l);
                                    boolean weatherBlockedTarget = world.v() && world.s(i1, k1, j1);
                                    boolean weatherBlockedAdjacent = world.s(i1 - 1, k1, k) || world.s(i1 + 1, k1, j1) || world.s(i1, k1, j1 - 1) || world.s(i1, k1, j1 + 1);

                                    if (FIRE_SPREAD_POLICY_SERVICE.shouldSpreadToAir(j2, random.nextInt(l1), weatherBlockedTarget, weatherBlockedAdjacent)) {
                                        int k2 = FIRE_SPREAD_POLICY_SERVICE.nextSpreadAge(l, random.nextInt(5));
                                        // CraftBukkit start - Call to stop spread of fire.
                                        org.bukkit.block.Block block = bworld.getBlockAt(i1, k1, j1);

                                        if (block.getTypeId() != Block.FIRE.id) {
                                            BlockIgniteEvent event = new BlockIgniteEvent(block, igniteCause, null);
                                            server.getPluginManager().callEvent(event);

                                            if (event.isCancelled()) {
                                                continue;
                                            }

                                            org.bukkit.block.BlockState blockState = bworld.getBlockAt(i1, k1, j1).getState();
                                            blockState.setTypeId(this.id);
                                            blockState.setData(new MaterialData(this.id, (byte) k2));

                                            BlockSpreadEvent spreadEvent = new BlockSpreadEvent(blockState.getBlock(), fromBlock, blockState);
                                            server.getPluginManager().callEvent(spreadEvent);

                                            if (!spreadEvent.isCancelled()) {
                                                blockState.update(true);
                                            }
                                        }
                                        // CraftBukkit end
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void a(World world, int i, int j, int k, int l, Random random, int i1) {
        int j1 = FIRE_SPREAD_POLICY_SERVICE.resolveNeighborBurnOdds(this.b, world.getTypeId(i, j, k));

        if (FIRE_SPREAD_POLICY_SERVICE.shouldAttemptNeighborBurn(random.nextInt(l), j1)) {
            boolean flag = world.getTypeId(i, j, k) == Block.TNT.id;
            // CraftBukkit start
            org.bukkit.block.Block theBlock = world.getWorld().getBlockAt(i, j, k);

            BlockBurnEvent event = new BlockBurnEvent(theBlock);
            world.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return;
            }
            // CraftBukkit end

            if (FIRE_SPREAD_POLICY_SERVICE.shouldIgniteBurnedBlock(random.nextInt(i1 + 10), world.s(i, j, k))) {
                int k1 = FIRE_SPREAD_POLICY_SERVICE.nextSpreadAge(i1, random.nextInt(5));
                world.setTypeIdAndData(i, j, k, this.id, k1);
            } else {
                world.setTypeId(i, j, k, 0);
            }

            if (flag) {
                Block.TNT.postBreak(world, i, j, k, 1);
            }
        }
    }

    private boolean g(World world, int i, int j, int k) {
        return FIRE_SPREAD_POLICY_SERVICE.hasBurnableNeighbor(new FireSpreadBehaviour.BurnableQuery() {
            public boolean isBurnable(int x, int y, int z) {
                return BlockFire.this.b(world, x, y, z);
            }
        }, i, j, k);
    }

    private int h(World world, int i, int j, int k) {
        return FIRE_SPREAD_POLICY_SERVICE.resolveNeighborEncouragement(new FireSpreadBehaviour.MaxEncouragementQuery() {
            public boolean isEmpty(int x, int y, int z) {
                return world.isEmpty(x, y, z);
            }

            public int encouragementAt(int x, int y, int z) {
                return FIRE_SPREAD_POLICY_SERVICE.resolveEncouragement(BlockFire.this.a, world.getTypeId(x, y, z));
            }
        }, i, j, k);
    }

    public boolean k_() {
        return false;
    }

    public boolean b(IBlockAccess iblockaccess, int i, int j, int k) {
        return FIRE_SPREAD_POLICY_SERVICE.isBurnable(FIRE_SPREAD_POLICY_SERVICE.resolveEncouragement(this.a, iblockaccess.getTypeId(i, j, k)));
    }

    public int f(World world, int i, int j, int k, int l) {
        int i1 = FIRE_SPREAD_POLICY_SERVICE.resolveEncouragement(this.a, world.getTypeId(i, j, k));
        return FIRE_SPREAD_POLICY_SERVICE.maxEncouragement(l, i1);
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return FIRE_SPREAD_POLICY_SERVICE.canPlace(world.e(i, j - 1, k), this.g(world, i, j, k));
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (FIRE_SPREAD_POLICY_SERVICE.shouldDropOnPhysics(world.e(i, j - 1, k), this.g(world, i, j, k))) {
            world.setTypeId(i, j, k, 0);
        }
    }

    public void c(World world, int i, int j, int k) {
        if (!FIRE_SPREAD_POLICY_SERVICE.shouldTryPortalCreation(world.getTypeId(i, j - 1, k), Block.OBSIDIAN.id) || !Block.PORTAL.a_(world, i, j, k)) {
            if (FIRE_SPREAD_POLICY_SERVICE.shouldDropOnPhysics(world.e(i, j - 1, k), this.g(world, i, j, k))) {
                world.setTypeId(i, j, k, 0);
            } else {
                world.c(i, j, k, this.id, this.c());
            }
        }
    }
}
