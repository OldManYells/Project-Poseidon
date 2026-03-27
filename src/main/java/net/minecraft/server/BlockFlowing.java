package net.minecraft.server;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.block.FlowingFluidPropagationBehaviour;
import com.legacyminecraft.poseidon.world.WorldFeatureConfigPolicy;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.Random;

// CraftBukkit start
// CraftBukkit end

public class BlockFlowing extends BlockFluids {

    int a = 0;
    boolean[] b = new boolean[4];
    int[] c = new int[4];
    private static final FlowingFluidPropagationBehaviour FLOWING_FLUID_PROPAGATION_SERVICE = FlowingFluidPropagationBehaviour.getInstance();
    private static final WorldFeatureConfigPolicy WORLD_FEATURE_CONFIG_POLICY = WorldFeatureConfigPolicy.getInstance();

    protected BlockFlowing(int i, Material material) {
        super(i, material);
    }

    private void i(World world, int i, int j, int k) {
        int l = world.getData(i, j, k);
        FLOWING_FLUID_PROPAGATION_SERVICE.convertToStillBlock(new FlowingFluidPropagationBehaviour.ConvertToStillSink() {
            public void setRawTypeIdAndData(int x, int y, int z, int typeId, int data) {
                world.setRawTypeIdAndData(x, y, z, typeId, data);
            }

            public void markNeighborsDirty(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
                world.b(minX, minY, minZ, maxX, maxY, maxZ);
            }

            public void notifyBlock(int x, int y, int z) {
                world.notify(x, y, z);
            }
        }, i, j, k, this.id, l);
    }

    public void a(World world, int i, int j, int k, Random random) {
        // CraftBukkit start
        org.bukkit.World bworld = world.getWorld();
        org.bukkit.Server server = world.getServer();
        org.bukkit.block.Block source = bworld == null ? null : bworld.getBlockAt(i, j, k);
        // CraftBukkit end

        int l = this.g(world, i, j, k);
        int b0 = FLOWING_FLUID_PROPAGATION_SERVICE.resolveFlowIncreaseStep(this.material == Material.LAVA, world.worldProvider.d);

        boolean flag = true;
        int i1;

        if (l > 0) {
            FlowingFluidPropagationBehaviour.LevelComputation levelComputation = FLOWING_FLUID_PROPAGATION_SERVICE.computeNextLevel(new FlowingFluidPropagationBehaviour.LevelUpdateQuery() {
                public int fluidLevel(int x, int y, int z) {
                    return BlockFlowing.this.g(world, x, y, z);
                }

                public boolean belowIsBuildable(int x, int y, int z) {
                    return world.getMaterial(x, y, z).isBuildable();
                }

                public boolean belowIsSameMaterial(int x, int y, int z) {
                    return world.getMaterial(x, y, z) == BlockFlowing.this.material;
                }

                public int currentData(int x, int y, int z) {
                    return world.getData(x, y, z);
                }
            }, i, j, k, l, b0, this.material == Material.WATER);
            this.a = levelComputation.sourceCount;
            i1 = levelComputation.nextLevel;

            if (this.material == Material.LAVA && l < 8 && i1 < 8 && i1 > l && random.nextInt(4) != 0) {
                // Poseidon start - Fix flowing lava not disappearing
                boolean fixFlowingLava = PoseidonConfig.getInstance().getConfigBoolean(
                        WORLD_FEATURE_CONFIG_POLICY.flowingLavaFixEnabledKey(),
                        WORLD_FEATURE_CONFIG_POLICY.flowingLavaFixEnabledDefault()
                );
                FlowingFluidPropagationBehaviour.LavaSlowdownResult lavaSlowdownResult = FLOWING_FLUID_PROPAGATION_SERVICE.applyLavaSlowdown(true, l, i1, 1, fixFlowingLava);
                i1 = lavaSlowdownResult.adjustedNextLevel;
                // Poseidon end
                flag = lavaSlowdownResult.shouldConvertToStill;
            }

            if (i1 != l) {
                l = i1;
                if (i1 < 0) {
                    world.setTypeId(i, j, k, 0);
                } else {
                    world.setData(i, j, k, i1);
                    world.c(i, j, k, this.id, this.c());
                    world.applyPhysics(i, j, k, this.id);
                }
            } else if (flag) {
                this.i(world, i, j, k);
            }
        } else {
            this.i(world, i, j, k);
        }

        if (this.l(world, i, j - 1, k)) {
            // CraftBukkit start - send "down" to the server
            BlockFromToEvent event = new BlockFromToEvent(source, BlockFace.DOWN);
            if (server != null) {
                server.getPluginManager().callEvent(event);
            }

            if (!event.isCancelled()) {
                world.setTypeIdAndData(i, j - 1, k, this.id, FLOWING_FLUID_PROPAGATION_SERVICE.resolveDownwardFlowLevel(l));
            }
            // CraftBukkit end
        } else if (l >= 0 && (l == 0 || this.k(world, i, j - 1, k))) {
            boolean[] aboolean = this.j(world, i, j, k);

            i1 = FLOWING_FLUID_PROPAGATION_SERVICE.resolveSideFlowLevel(l, b0);
            if (i1 < 0) {
                return;
            }

            // CraftBukkit start - all four cardinal directions. Do not change the order!
            BlockFace[] faces = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };
            int index = 0;

            for (BlockFace currentFace: faces) {
                if (aboolean[index]) {
                    BlockFromToEvent event = new BlockFromToEvent(source, currentFace);

                    if (server != null) {
                        server.getPluginManager().callEvent(event);
                    }

                    if (!event.isCancelled()) {
                        this.flow(world, i + currentFace.getModX(), j, k + currentFace.getModZ(), i1);
                    }
                }
                index++;
            }
            // CraftBukkit end
        }
    }

    private void flow(World world, int i, int j, int k, int l) {
        if (this.l(world, i, j, k)) {
            int i1 = world.getTypeId(i, j, k);

            if (i1 > 0) {
                if (this.material == Material.LAVA) {
                    this.h(world, i, j, k);
                } else {
                    Block.byId[i1].g(world, i, j, k, world.getData(i, j, k));
                }
            }

            world.setTypeIdAndData(i, j, k, this.id, l);
        }
    }

    private int b(World world, int i, int j, int k, int l, int i1) {
        return FLOWING_FLUID_PROPAGATION_SERVICE.computeSlopeDistance(new FlowingFluidPropagationBehaviour.SlopeQuery() {
            public boolean isBlocked(int x, int y, int z) {
                return BlockFlowing.this.k(world, x, y, z);
            }

            public boolean isSameMaterialLevelZero(int x, int y, int z) {
                return world.getMaterial(x, y, z) == BlockFlowing.this.material && world.getData(x, y, z) == 0;
            }
        }, i, j, k, l, i1);
    }

    private boolean[] j(World world, int i, int j, int k) {
        return FLOWING_FLUID_PROPAGATION_SERVICE.resolveOptimalFlowDirections(new FlowingFluidPropagationBehaviour.DirectionQuery() {
            public boolean isBlocked(int x, int y, int z) {
                return BlockFlowing.this.k(world, x, y, z);
            }

            public boolean isSameMaterialLevelZero(int x, int y, int z) {
                return world.getMaterial(x, y, z) == BlockFlowing.this.material && world.getData(x, y, z) == 0;
            }
        }, i, j, k, this.c, this.b);
    }

    private boolean k(World world, int i, int j, int k) {
        int l = world.getTypeId(i, j, k);
        Material material = l == 0 ? Material.AIR : Block.byId[l].material;
        return FLOWING_FLUID_PROPAGATION_SERVICE.isBlockedType(l, material.isSolid(), Block.WOODEN_DOOR.id, Block.IRON_DOOR_BLOCK.id, Block.SIGN_POST.id, Block.LADDER.id, Block.SUGAR_CANE_BLOCK.id);
    }

    protected int f(World world, int i, int j, int k, int l) {
        FlowingFluidPropagationBehaviour.NeighborLevelUpdate update = FLOWING_FLUID_PROPAGATION_SERVICE.accumulateNeighbor(this.g(world, i, j, k), l);
        this.a += update.sourceCountIncrement;
        return update.minLevel;
    }

    private boolean l(World world, int i, int j, int k) {
        Material material = world.getMaterial(i, j, k);
        return FLOWING_FLUID_PROPAGATION_SERVICE.canFlowInto(material == this.material, material == Material.LAVA, this.k(world, i, j, k));
    }

    public void c(World world, int i, int j, int k) {
        super.c(world, i, j, k);
        if (world.getTypeId(i, j, k) == this.id) {
            world.c(i, j, k, this.id, this.c());
        }
    }
}
