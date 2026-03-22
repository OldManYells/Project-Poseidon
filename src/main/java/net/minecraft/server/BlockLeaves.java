package net.minecraft.server;

import com.legacyminecraft.poseidon.block.LeavesDecayBehaviour;
import org.bukkit.event.block.LeavesDecayEvent;

import java.util.Random;

public class BlockLeaves extends BlockLeavesBase {

    private int c;
    int[] a;
    private static final LeavesDecayBehaviour LEAVES_DECAY_SERVICE = LeavesDecayBehaviour.getInstance();

    protected BlockLeaves(int i, int j) {
        super(i, j, Material.LEAVES, false);
        this.c = j;
        this.a(true);
    }

    public void remove(World world, int i, int j, int k) {
        LEAVES_DECAY_SERVICE.markNearbyLeavesForDecay(new LeavesDecayBehaviour.RemoveQuery() {
            public boolean isAreaLoaded(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
                return world.a(minX, minY, minZ, maxX, maxY, maxZ);
            }

            public int getTypeId(int x, int y, int z) {
                return world.getTypeId(x, y, z);
            }

            public int getData(int x, int y, int z) {
                return world.getData(x, y, z);
            }

            public void setRawData(int x, int y, int z, int data) {
                world.setRawData(x, y, z, data);
            }
        }, i, j, k, Block.LEAVES.id);
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (!world.isStatic) {
            int l = world.getData(i, j, k);

            LeavesDecayBehaviour.DecayResult result = LEAVES_DECAY_SERVICE.evaluateDecayTick(new LeavesDecayBehaviour.DecayQuery() {
                public boolean isAreaLoaded(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
                    return world.a(minX, minY, minZ, maxX, maxY, maxZ);
                }

                public int getTypeId(int x, int y, int z) {
                    return world.getTypeId(x, y, z);
                }
            }, i, j, k, l, this.a, Block.LEAVES.id, Block.LOG.id);
            this.a = result.scratch;

            if (result.clearDecayBit) {
                world.setRawData(i, j, k, LEAVES_DECAY_SERVICE.clearDecayBit(l));
            } else if (result.decayNow) {
                this.g(world, i, j, k);
            }
        }
    }

    private void g(World world, int i, int j, int k) {
        // CraftBukkit start
        LeavesDecayEvent event = new LeavesDecayEvent(world.getWorld().getBlockAt(i, j, k));
        world.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) return;
        // CraftBukkit end

        this.g(world, i, j, k, world.getData(i, j, k));
        world.setTypeId(i, j, k, 0);
    }

    public int a(Random random) {
        return LEAVES_DECAY_SERVICE.resolveSaplingDropCount(random);
    }

    public int a(int i, Random random) {
        return LEAVES_DECAY_SERVICE.resolveSaplingDropItemId(Block.SAPLING.id);
    }

    public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
        int heldItemId = entityhuman.G() == null ? 0 : entityhuman.G().id;
        if (LEAVES_DECAY_SERVICE.shouldUseShearHarvest(world.isStatic, heldItemId, Item.SHEARS.id)) {
            entityhuman.a(StatisticList.C[this.id], 1);
            this.a(world, i, j, k, new ItemStack(Block.LEAVES.id, 1, l & 3));
        } else {
            super.a(world, entityhuman, i, j, k, l);
        }
    }

    protected int a_(int i) {
        return LEAVES_DECAY_SERVICE.stripVariantData(i);
    }

    public boolean a() {
        return LEAVES_DECAY_SERVICE.isOpaqueCube(this.b);
    }

    public int a(int i, int j) {
        return LEAVES_DECAY_SERVICE.resolveTextureByVariantData(j, this.textureId);
    }

    public void b(World world, int i, int j, int k, Entity entity) {
        super.b(world, i, j, k, entity);
    }
}
