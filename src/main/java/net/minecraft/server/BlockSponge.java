package net.minecraft.server;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.block.SpongePhysicsBehaviour;

public class BlockSponge extends Block {
    private final SpongePhysicsBehaviour spongePhysicsService = SpongePhysicsBehaviour.getInstance();

    protected BlockSponge(int i) {
        super(i, Material.SPONGE);
        this.textureId = 48;
    }

    public void remove(World world, int i, int j, int k) {
        int radius = spongePhysicsService.removalRadius();
        SpongePhysicsBehaviour.PhysicsWorld physicsWorld = new SpongePhysicsBehaviour.PhysicsWorld() {
            public int getTypeId(int x, int y, int z) {
                return world.getTypeId(x, y, z);
            }

            public void applyPhysics(int x, int y, int z, int typeId) {
                world.applyPhysics(x, y, z, typeId);
            }
        };

        if (spongePhysicsService.shouldUseOptimizedRemoval(PoseidonConfig.getInstance().getConfigBoolean("fix.optimize-sponges.enabled", true))) {
            spongePhysicsService.applyOptimizedRemoval(
                    physicsWorld,
                    i,
                    j,
                    k,
                    radius,
                    0,
                    127,
                    Block.WATER.id,
                    Block.STATIONARY_WATER.id
            );
            return;
        }

        spongePhysicsService.applyLegacyRemoval(physicsWorld, i, j, k, radius);
    }
}
