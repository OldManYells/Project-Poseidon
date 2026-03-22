package net.minecraft.server;

import com.legacyminecraft.poseidon.block.FrozenBlockMeltBehaviour;
import com.legacyminecraft.poseidon.block.SnowLayerStateBehaviour;

import java.util.Random;

public class BlockSnowBlock extends Block {
    private final SnowLayerStateBehaviour snowLayerStateService = SnowLayerStateBehaviour.getInstance();
    private final FrozenBlockMeltBehaviour frozenBlockMeltService = FrozenBlockMeltBehaviour.getInstance();

    protected BlockSnowBlock(int i, int j) {
        super(i, j, Material.SNOW_BLOCK);
        this.a(true);
    }

    public int a(int i, Random random) {
        return snowLayerStateService.resolveDropItemId(Item.SNOW_BALL.id);
    }

    public int a(Random random) {
        return frozenBlockMeltService.resolveSnowBlockDropCount();
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (frozenBlockMeltService.shouldMelt(world.a(EnumSkyBlock.BLOCK, i, j, k), frozenBlockMeltService.snowMeltThreshold())) {
            this.g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
        }
    }
}
