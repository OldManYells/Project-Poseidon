package net.minecraft.server;

import com.legacyminecraft.poseidon.block.TntStateBehaviour;

import java.util.Random;

public class BlockTNT extends Block {
    private final TntStateBehaviour tntStateService = TntStateBehaviour.getInstance();

    public BlockTNT(int i, int j) {
        super(i, j, Material.TNT);
    }

    public int a(int i) {
        return tntStateService.resolveTextureBySide(i, this.textureId);
    }

    public void c(World world, int i, int j, int k) {
        super.c(world, i, j, k);
        if (tntStateService.shouldPrimeOnPlacement(world.isBlockIndirectlyPowered(i, j, k))) {
            this.postBreak(world, i, j, k, 1);
            world.setTypeId(i, j, k, 0);
        }
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        boolean neighborIsPowerSource = l > 0 && Block.byId[l].isPowerSource();
        if (tntStateService.shouldPrimeOnPhysics(l, neighborIsPowerSource, world.isBlockIndirectlyPowered(i, j, k))) {
            this.postBreak(world, i, j, k, 1);
            world.setTypeId(i, j, k, 0);
        }
    }

    public int a(Random random) {
        return 0;
    }

    public void d(World world, int i, int j, int k) {
        EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(
                world,
                tntStateService.resolveCenteredSpawnCoordinate(i),
                tntStateService.resolveCenteredSpawnCoordinate(j),
                tntStateService.resolveCenteredSpawnCoordinate(k)
        );

        entitytntprimed.fuseTicks = tntStateService.resolveDispensedFuseTicks(world.random, entitytntprimed.fuseTicks);
        world.addEntity(entitytntprimed);
    }

    public void postBreak(World world, int i, int j, int k, int l) {
        if (!world.isStatic) {
            if (tntStateService.shouldDropAsItem(l)) {
                this.a(world, i, j, k, new ItemStack(Block.TNT.id, 1, 0));
            } else {
                EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(
                        world,
                        tntStateService.resolveCenteredSpawnCoordinate(i),
                        tntStateService.resolveCenteredSpawnCoordinate(j),
                        tntStateService.resolveCenteredSpawnCoordinate(k)
                );

                world.addEntity(entitytntprimed);
                world.makeSound(entitytntprimed, "random.fuse", 1.0F, 1.0F);
            }
        }
    }

    public void b(World world, int i, int j, int k, EntityHuman entityhuman) {
        if (entityhuman.G() != null && tntStateService.shouldMarkIgnitedFromHeldItem(entityhuman.G().id, Item.FLINT_AND_STEEL.id)) {
            world.setRawData(i, j, k, 1);
        }

        super.b(world, i, j, k, entityhuman);
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        return super.interact(world, i, j, k, entityhuman);
    }
}
