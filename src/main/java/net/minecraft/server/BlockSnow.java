package net.minecraft.server;

import com.legacyminecraft.poseidon.block.FrozenBlockMeltBehaviour;
import com.legacyminecraft.poseidon.block.SnowLayerStateBehaviour;
import org.bukkit.craftbukkit.event.CraftEventFactory;

import java.util.Random;

public class BlockSnow extends Block {
    private final SnowLayerStateBehaviour snowLayerStateService = SnowLayerStateBehaviour.getInstance();
    private final FrozenBlockMeltBehaviour frozenBlockMeltService = FrozenBlockMeltBehaviour.getInstance();

    protected BlockSnow(int i, int j) {
        super(i, j, Material.SNOW_LAYER);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        this.a(true);
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return snowLayerStateService.resolveCollisionBox(
                i,
                j,
                k,
                world.getData(i, j, k),
                this.minX,
                this.minY,
                this.minZ,
                this.maxX,
                this.maxZ
        );
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public void a(IBlockAccess iblockaccess, int i, int j, int k) {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, snowLayerStateService.resolveSelectionHeight(iblockaccess.getData(i, j, k)), 1.0F);
    }

    public boolean canPlace(World world, int i, int j, int k) {
        int l = world.getTypeId(i, j - 1, k);
        Block block = l >= 0 && l < Block.byId.length ? Block.byId[l] : null;

        return snowLayerStateService.canPlace(l, block != null && block.a(), world.getMaterial(i, j - 1, k).isSolid());
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        this.g(world, i, j, k);
    }

    private boolean g(World world, int i, int j, int k) {
        if (!this.canPlace(world, i, j, k)) {
            this.g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
            return false;
        } else {
            return true;
        }
    }

    public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
        float f = 0.7F;
        int i1 = snowLayerStateService.resolveDropItemId(Item.SNOW_BALL.id);
        double d0 = snowLayerStateService.resolveDropOffset(world.random, f);
        double d1 = snowLayerStateService.resolveDropOffset(world.random, f);
        double d2 = snowLayerStateService.resolveDropOffset(world.random, f);
        EntityItem entityitem = new EntityItem(world, (double) i + d0, (double) j + d1, (double) k + d2, new ItemStack(i1, 1, 0));

        entityitem.pickupDelay = 10;
        world.addEntity(entityitem);
        world.setTypeId(i, j, k, 0);
        entityhuman.a(StatisticList.C[this.id], 1);
    }

    public int a(int i, Random random) {
        return snowLayerStateService.resolveDropItemId(Item.SNOW_BALL.id);
    }

    public int a(Random random) {
        return 0;
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (frozenBlockMeltService.shouldMelt(world.a(EnumSkyBlock.BLOCK, i, j, k), frozenBlockMeltService.snowMeltThreshold())) {
            // CraftBukkit start
            if (CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(i, j, k), 0).isCancelled()) {
                return;
            }
            // CraftBukkit end

            this.g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
        }
    }
}
