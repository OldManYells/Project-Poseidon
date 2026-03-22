package net.minecraft.server;

import com.legacyminecraft.poseidon.block.CactusStateBehaviour;
import com.legacyminecraft.poseidon.block.ColumnPlantGrowthBehaviour;

// CraftBukkit start

import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

// CraftBukkit end

public class BlockCactus extends Block {
    private final ColumnPlantGrowthBehaviour columnPlantGrowthService = ColumnPlantGrowthBehaviour.getInstance();
    private final CactusStateBehaviour cactusStateService = CactusStateBehaviour.getInstance();

    protected BlockCactus(int i, int j) {
        super(i, j, Material.CACTUS);
        this.a(true);
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (world.isEmpty(i, j + 1, k)) {
            int l = columnPlantGrowthService.countContiguousBelow(this.blockIdQuery(world), i, j, k, this.id);
            if (columnPlantGrowthService.shouldAttemptGrowth(true, l, 3)) {
                int i1 = world.getData(i, j, k);

                if (columnPlantGrowthService.shouldSpawnNewSegment(i1, 15)) {
                    world.setTypeId(i, j + 1, k, this.id);
                    world.setData(i, j, k, columnPlantGrowthService.nextGrowthData(i1, 15));
                } else {
                    world.setData(i, j, k, columnPlantGrowthService.nextGrowthData(i1, 15));
                }
            }
        }
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return cactusStateService.resolveCollisionBox(i, j, k);
    }

    public int a(int i) {
        return cactusStateService.resolveTextureBySide(i, this.textureId);
    }

    public boolean b() {
        return false;
    }

    public boolean a() {
        return false;
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return cactusStateService.canPlace(super.canPlace(world, i, j, k), this.f(world, i, j, k));
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (!this.f(world, i, j, k)) {
            this.g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
        }
    }

    public boolean f(World world, int i, int j, int k) {
        return cactusStateService.canRemainPlaced(this.supportQuery(world), i, j, k, Block.CACTUS.id, Block.SAND.id);
    }

    public void a(World world, int i, int j, int k, Entity entity) {
        // CraftBukkit start - ENTITY_DAMAGEBY_BLOCK event
        if (entity instanceof EntityLiving) {
            org.bukkit.block.Block damager = world.getWorld().getBlockAt(i, j, k);
            org.bukkit.entity.Entity damagee = (entity == null) ? null : entity.getBukkitEntity();

            EntityDamageByBlockEvent event = new EntityDamageByBlockEvent(damager, damagee, EntityDamageEvent.DamageCause.CONTACT, 1);
            world.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                entity.damageEntity((Entity) null, event.getDamage());
            }
            return;
        }
        // CraftBukkit end

        entity.damageEntity((Entity) null, cactusStateService.contactDamage());
    }

    private ColumnPlantGrowthBehaviour.BlockIdQuery blockIdQuery(final World world) {
        return new ColumnPlantGrowthBehaviour.BlockIdQuery() {
            public int getTypeId(int x, int y, int z) {
                return world.getTypeId(x, y, z);
            }
        };
    }

    private CactusStateBehaviour.SupportQuery supportQuery(final World world) {
        return new CactusStateBehaviour.SupportQuery() {
            public boolean isBuildableMaterial(int x, int y, int z) {
                return world.getMaterial(x, y, z).isBuildable();
            }

            public int getTypeId(int x, int y, int z) {
                return world.getTypeId(x, y, z);
            }
        };
    }
}
