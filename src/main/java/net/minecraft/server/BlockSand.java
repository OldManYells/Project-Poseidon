package net.minecraft.server;

import com.legacyminecraft.poseidon.block.FallingBlockBehaviour;
import com.legacyminecraft.poseidon.PoseidonConfig;

import java.util.Random;

public class BlockSand extends Block {

    public static boolean instaFall = false;
    private static final FallingBlockBehaviour FALLING_BLOCK_SERVICE = FallingBlockBehaviour.getInstance();

    public BlockSand(int i, int j) {
        super(i, j, Material.SAND);
    }

    public void c(World world, int i, int j, int k) {
        world.c(i, j, k, this.id, FALLING_BLOCK_SERVICE.updateDelayTicks());
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        world.c(i, j, k, this.id, FALLING_BLOCK_SERVICE.updateDelayTicks());
    }

    public void a(World world, int i, int j, int k, Random random) {
        this.g(world, i, j, k);
    }

    private void g(World world, int i, int j, int k) {
        if (FALLING_BLOCK_SERVICE.shouldAttemptFall(c_(world, i, j - 1, k), j)) {
            int b0 = FALLING_BLOCK_SERVICE.chunkCheckRadius();

            if (FALLING_BLOCK_SERVICE.shouldSpawnFallingEntity(instaFall, world.a(i - b0, j - b0, k - b0, i + b0, j + b0, k + b0))) {
                if (FALLING_BLOCK_SERVICE.shouldApplyDupingFix(
                        PoseidonConfig.getInstance().getConfigBoolean("world.settings.pistons.sand-gravel-duping-fix.enabled", true)
                )) {
                    world.setTypeId(i, j, k, 0);
                }
                EntityFallingSand entityfallingsand = new EntityFallingSand(world, i + 0.5D, j + 0.5D, k + 0.5D, this.id);

                world.addEntity(entityfallingsand);
            } else {
                world.setTypeId(i, j, k, 0);
                int settledY = FALLING_BLOCK_SERVICE.resolveSettledY(new FallingBlockBehaviour.FallThroughQuery() {
                    public boolean canFallThrough(int x, int y, int z) {
                        return c_(world, x, y, z);
                    }
                }, i, j, k);

                if (FALLING_BLOCK_SERVICE.canSettleAt(settledY)) {
                    world.setTypeId(i, settledY, k, this.id);
                }
            }
        }
    }

    public int c() {
        return FALLING_BLOCK_SERVICE.updateDelayTicks();
    }

    public static boolean c_(World world, int i, int j, int k) {
        int l = world.getTypeId(i, j, k);
        Block block = l >= 0 && l < Block.byId.length ? Block.byId[l] : null;
        Material material = block == null ? null : block.material;
        return FALLING_BLOCK_SERVICE.canFallThrough(l, Block.FIRE.id, material);
    }
}
