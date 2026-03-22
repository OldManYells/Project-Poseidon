package net.minecraft.server;

import com.legacyminecraft.poseidon.block.SlowSandInteractionBehaviour;

public class BlockSlowSand extends Block {
    private final SlowSandInteractionBehaviour slowSandInteractionService = SlowSandInteractionBehaviour.getInstance();

    public BlockSlowSand(int i, int j) {
        super(i, j, Material.SAND);
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return slowSandInteractionService.resolveCollisionBox(i, j, k, 0.125F);
    }

    public void a(World world, int i, int j, int k, Entity entity) {
        slowSandInteractionService.applyHorizontalSlowdown(entity, slowSandInteractionService.slowdownFactor());
    }
}
