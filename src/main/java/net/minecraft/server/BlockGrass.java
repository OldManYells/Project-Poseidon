package net.minecraft.server;

import com.legacyminecraft.poseidon.block.GrassSpreadBehaviour;

import java.util.Random;

// CraftBukkit start
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.BlockFadeEvent;
//CraftBukkit end

public class BlockGrass extends Block {
    private final GrassSpreadBehaviour grassSpreadService = GrassSpreadBehaviour.getInstance();

    protected BlockGrass(int i) {
        super(i, Material.GRASS);
        this.textureId = 3;
        this.a(true);
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (!world.isStatic) {
            int lightAbove = world.getLightLevel(i, j + 1, k);
            int aboveTypeId = world.getTypeId(i, j + 1, k);
            if (grassSpreadService.shouldAttemptFade(lightAbove, Block.q[aboveTypeId])) {
                if (!grassSpreadService.shouldPassFadeRandomGate(random)) {
                    return;
                }

                // CraftBukkit start
                org.bukkit.World bworld = world.getWorld();
                org.bukkit.block.BlockState blockState = bworld.getBlockAt(i, j, k).getState();
                blockState.setTypeId(Block.DIRT.id);

                BlockFadeEvent event = new BlockFadeEvent(blockState.getBlock(), blockState);
                world.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled()) {
                    blockState.update(true);
                }
                // CraftBukkit end
            } else if (grassSpreadService.shouldAttemptSpread(lightAbove)) {
                int l = grassSpreadService.resolveSpreadTargetX(i, random);
                int i1 = grassSpreadService.resolveSpreadTargetY(j, random);
                int j1 = grassSpreadService.resolveSpreadTargetZ(k, random);
                int k1 = world.getTypeId(l, i1 + 1, j1);

                if (grassSpreadService.canSpreadTo(
                        world.getTypeId(l, i1, j1),
                        Block.DIRT.id,
                        world.getLightLevel(l, i1 + 1, j1),
                        4,
                        Block.q[k1],
                        2
                )) {
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

    public int a(int i, Random random) {
        return Block.DIRT.a(0, random);
    }
}
