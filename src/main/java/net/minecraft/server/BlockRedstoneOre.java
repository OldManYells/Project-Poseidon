package net.minecraft.server;

import com.legacyminecraft.poseidon.block.RedstoneOreStateBehaviour;

import java.util.Random;

public class BlockRedstoneOre extends Block {

    private boolean a;
    private final RedstoneOreStateBehaviour redstoneOreStateService = RedstoneOreStateBehaviour.getInstance();

    public BlockRedstoneOre(int i, int j, boolean flag) {
        super(i, j, Material.STONE);
        if (flag) {
            this.a(true);
        }

        this.a = flag;
    }

    public int c() {
        return redstoneOreStateService.updateDelayTicks();
    }

    public void b(World world, int i, int j, int k, EntityHuman entityhuman) {
        this.g(world, i, j, k);
        super.b(world, i, j, k, entityhuman);
    }

    public void b(World world, int i, int j, int k, Entity entity) {
        this.g(world, i, j, k);
        super.b(world, i, j, k, entity);
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        this.g(world, i, j, k);
        return super.interact(world, i, j, k, entityhuman);
    }

    private void g(World world, int i, int j, int k) {
        this.h(world, i, j, k);
        if (redstoneOreStateService.shouldSwitchToGlowing(this.id, Block.REDSTONE_ORE.id)) {
            world.setTypeId(i, j, k, Block.GLOWING_REDSTONE_ORE.id);
        }
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (redstoneOreStateService.shouldRevertToNormal(this.id, Block.GLOWING_REDSTONE_ORE.id)) {
            world.setTypeId(i, j, k, Block.REDSTONE_ORE.id);
        }
    }

    public int a(int i, Random random) {
        return redstoneOreStateService.resolveDropItemId(Item.REDSTONE.id);
    }

    public int a(Random random) {
        return redstoneOreStateService.resolveDropCount(random);
    }

    private void h(World world, int i, int j, int k) {
        redstoneOreStateService.emitActivationParticles(
                new RedstoneOreStateBehaviour.ParticleEmitter() {
                    public void emit(String particle, double x, double y, double z) {
                        world.a(particle, x, y, z, 0.0D, 0.0D, 0.0D);
                    }
                },
                new RedstoneOreStateBehaviour.OcclusionQuery() {
                    public boolean isOccluding(int x, int y, int z) {
                        return world.p(x, y, z);
                    }
                },
                world.random,
                i,
                j,
                k
        );
    }
}
