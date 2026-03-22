package net.minecraft.server;

import com.legacyminecraft.poseidon.block.FarmlandStateBehaviour;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.entity.EntityInteractEvent;

import java.util.Random;

// CraftBukkit start
// CraftBukkit end

public class BlockSoil extends Block {
    private final FarmlandStateBehaviour farmlandStateService = FarmlandStateBehaviour.getInstance();

    protected BlockSoil(int i) {
        super(i, Material.EARTH);
        this.textureId = 87;
        this.a(true);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
        this.f(255);
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return farmlandStateService.resolveCollisionBox(i, j, k);
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public int a(int i, int j) {
        return farmlandStateService.resolveTextureBySideAndMoisture(i, j, this.textureId, 2);
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (farmlandStateService.shouldProcessMoistureTick(random)) {
            if (farmlandStateService.shouldDecayWithoutWaterAndRain(this.h(world, i, j, k), world.s(i, j + 1, k))) {
                int l = world.getData(i, j, k);

                if (l > 0) {
                    world.setData(i, j, k, farmlandStateService.resolveNextMoisture(l));
                } else if (farmlandStateService.shouldTurnToDirtWhenDry(l, this.g(world, i, j, k))) {
                    world.setTypeId(i, j, k, Block.DIRT.id);
                }
            } else {
                world.setData(i, j, k, farmlandStateService.hydratedMoisture());
            }
        }
    }

    public void b(World world, int i, int j, int k, Entity entity) {
        if (farmlandStateService.shouldTrampleToDirt(world.random)) {
            // CraftBukkit start - Interact Soil
            org.bukkit.event.Cancellable cancellable;
            if (entity instanceof EntityHuman) {
                cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman) entity, org.bukkit.event.block.Action.PHYSICAL, i, j, k, -1, null);
            } else {
                cancellable = new EntityInteractEvent(entity.getBukkitEntity(), world.getWorld().getBlockAt(i, j, k));
                world.getServer().getPluginManager().callEvent((EntityInteractEvent) cancellable);
            }

            if (cancellable.isCancelled()) {
                return;
            }
            // CraftBukkit end

            world.setTypeId(i, j, k, Block.DIRT.id);
        }
    }

    private boolean g(World world, int i, int j, int k) {
        return farmlandStateService.hasCropsAbove(new FarmlandStateBehaviour.TypeQuery() {
            public int getTypeId(int x, int y, int z) {
                return world.getTypeId(x, y, z);
            }
        }, i, j, k, Block.CROPS.id);
    }

    private boolean h(World world, int i, int j, int k) {
        return farmlandStateService.hasNearbyWater(new FarmlandStateBehaviour.MaterialQuery() {
            public Material getMaterial(int x, int y, int z) {
                return world.getMaterial(x, y, z);
            }
        }, i, j, k);
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        super.doPhysics(world, i, j, k, l);
        Material material = world.getMaterial(i, j + 1, k);

        if (farmlandStateService.shouldTurnToDirtForBlockAbove(material.isBuildable())) {
            world.setTypeId(i, j, k, Block.DIRT.id);
        }
    }

    public int a(int i, Random random) {
        return Block.DIRT.a(0, random);
    }
}
