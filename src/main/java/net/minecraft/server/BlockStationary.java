package net.minecraft.server;

import com.legacyminecraft.poseidon.block.StationaryFluidStateBehaviour;
import org.bukkit.event.block.BlockIgniteEvent;

import java.util.Random;

public class BlockStationary extends BlockFluids {
    private final StationaryFluidStateBehaviour stationaryFluidStateService = StationaryFluidStateBehaviour.getInstance();

    protected BlockStationary(int i, Material material) {
        super(i, material);
        this.a(false);
        if (material == Material.LAVA) {
            this.a(true);
        }
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        super.doPhysics(world, i, j, k, l);
        if (stationaryFluidStateService.shouldConvertToFlowing(world.getTypeId(i, j, k), this.id)) {
            this.i(world, i, j, k);
        }
    }

    private void i(World world, int i, int j, int k) {
        int l = world.getData(i, j, k);
        int flowingBlockId = stationaryFluidStateService.resolveFlowingBlockId(this.id);

        world.suppressPhysics = true;
        world.setRawTypeIdAndData(i, j, k, flowingBlockId, l);
        world.b(i, j, k, i, j, k);
        world.c(i, j, k, flowingBlockId, this.c());
        world.suppressPhysics = false;
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (stationaryFluidStateService.shouldAttemptLavaIgnition(this.material)) {
            int l = stationaryFluidStateService.resolveIgnitionAttempts(random);

            // CraftBukkit start - prevent lava putting something on fire.
            org.bukkit.World bworld = world.getWorld();
            BlockIgniteEvent.IgniteCause igniteCause = BlockIgniteEvent.IgniteCause.LAVA;
            // CraftBukkit end

            for (int i1 = 0; i1 < l; ++i1) {
                i += stationaryFluidStateService.resolveHorizontalOffset(random);
                ++j;
                k += stationaryFluidStateService.resolveHorizontalOffset(random);
                int j1 = world.getTypeId(i, j, k);

                if (stationaryFluidStateService.isAirBlock(j1)) {
                    if (stationaryFluidStateService.hasBurnableNeighbor(new StationaryFluidStateBehaviour.BurnableQuery() {
                        public boolean isBurnable(int x, int y, int z) {
                            return j(world, x, y, z);
                        }
                    }, i, j, k)) {
                        // CraftBukkit start - prevent lava putting something on fire.
                        org.bukkit.block.Block block = bworld.getBlockAt(i, j, k);

                        if (block.getTypeId() != Block.FIRE.id) {
                            BlockIgniteEvent event = new BlockIgniteEvent(block, igniteCause, null);
                            world.getServer().getPluginManager().callEvent(event);

                            if (event.isCancelled()) {
                                continue;
                            }
                        }
                        // CraftBukkit end

                        world.setTypeId(i, j, k, Block.FIRE.id);
                        return;
                    }
                } else if (stationaryFluidStateService.shouldStopAtSolid(Block.byId[j1].material.isSolid())) {
                    return;
                }
            }
        }
    }

    private boolean j(World world, int i, int j, int k) {
        return world.getMaterial(i, j, k).isBurnable();
    }
}
