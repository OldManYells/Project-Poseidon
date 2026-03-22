package net.minecraft.server;

import com.legacyminecraft.poseidon.block.SignStateBehaviour;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.Random;

public class BlockSign extends BlockContainer {

    private Class a;
    private boolean b;
    private final SignStateBehaviour signStateService = SignStateBehaviour.getInstance();

    protected BlockSign(int i, Class oclass, boolean flag) {
        super(i, Material.WOOD);
        this.b = flag;
        this.textureId = 4;
        this.a = oclass;
        float f = 0.25F;
        float f1 = 1.0F;

        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return null;
    }

    public void a(IBlockAccess iblockaccess, int i, int j, int k) {
        if (!this.b) {
            SignStateBehaviour.Bounds bounds = signStateService.resolveWallBounds(iblockaccess.getData(i, j, k));
            this.a(bounds.minX, bounds.minY, bounds.minZ, bounds.maxX, bounds.maxY, bounds.maxZ);
        }
    }

    public boolean b() {
        return false;
    }

    public boolean a() {
        return false;
    }

    protected TileEntity a_() {
        return signStateService.instantiateTileEntity(this.a);
    }

    public int a(int i, Random random) {
        return signStateService.resolveDropItemId(Item.SIGN.id);
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        boolean flag = false;

        if (this.b) {
            flag = signStateService.shouldDropStandingSign(world.getMaterial(i, j - 1, k).isBuildable());
        } else {
            int i1 = world.getData(i, j, k);
            flag = signStateService.shouldDropWallSign(signStateService.hasValidWallAttachment(
                    i1,
                    world.getMaterial(i, j, k + 1).isBuildable(),
                    world.getMaterial(i, j, k - 1).isBuildable(),
                    world.getMaterial(i + 1, j, k).isBuildable(),
                    world.getMaterial(i - 1, j, k).isBuildable()
            ));
        }

        if (flag) {
            this.g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
        }

        super.doPhysics(world, i, j, k, l);

        // CraftBukkit start
        if (net.minecraft.server.Block.byId[l] != null && signStateService.shouldFireRedstoneNeighborEvent(l, net.minecraft.server.Block.byId[l].isPowerSource())) {
            org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            int power = block.getBlockPower();

            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, power, power);
            world.getServer().getPluginManager().callEvent(eventRedstone);
        }
        // CraftBukkit end
    }
}
