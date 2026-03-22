package net.minecraft.server;

import com.legacyminecraft.poseidon.block.MushroomStateBehaviour;
import org.bukkit.event.block.BlockSpreadEvent;

import java.util.Random;

public class BlockMushroom extends BlockFlower {
    private final MushroomStateBehaviour mushroomStateService = MushroomStateBehaviour.getInstance();

    protected BlockMushroom(int i, int j) {
        super(i, j);
        float f = 0.2F;

        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.a(true);
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (mushroomStateService.shouldAttemptSpread(random)) {
            int l = i + mushroomStateService.resolveHorizontalOffset(random);
            int i1 = j + mushroomStateService.resolveVerticalOffset(random);
            int j1 = k + mushroomStateService.resolveHorizontalOffset(random);

            if (world.isEmpty(l, i1, j1) && this.f(world, l, i1, j1)) {
                int k1 = i + mushroomStateService.resolveHorizontalOffset(random);

                k1 = k + mushroomStateService.resolveHorizontalOffset(random);
                if (world.isEmpty(l, i1, j1) && this.f(world, l, i1, j1)) {
                    // CraftBukkit start
                    org.bukkit.World bworld = world.getWorld();
                    org.bukkit.block.BlockState blockState = bworld.getBlockAt(l, i1, j1).getState();
                    blockState.setTypeId(this.id);

                    BlockSpreadEvent event = new BlockSpreadEvent(blockState.getBlock(), bworld.getBlockAt(i, j, k), blockState);
                    world.getServer().getPluginManager().callEvent(event);

                    if (!event.isCancelled()) {
                        blockState.update(true);
                    }
                    // CraftBukkit end
                }
            }
        }
    }

    protected boolean c(int i) {
        return mushroomStateService.canPlantOn(Block.o[i]);
    }

    public boolean f(World world, int i, int j, int k) {
        return mushroomStateService.canStay(
                j,
                128,
                world.k(i, j, k),
                13,
                this.c(world.getTypeId(i, j - 1, k))
        );
    }
}
