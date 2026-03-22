package net.minecraft.server;

import com.legacyminecraft.poseidon.block.PumpkinStateBehaviour;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockPumpkin extends Block {

    private boolean a;
    private final PumpkinStateBehaviour pumpkinStateService = PumpkinStateBehaviour.getInstance();

    protected BlockPumpkin(int i, int j, boolean flag) {
        super(i, Material.PUMPKIN);
        this.textureId = j;
        this.a(true);
        this.a = flag;
    }

    public int a(int i, int j) {
        return pumpkinStateService.resolveTextureBySideAndData(i, j, this.textureId, this.a);
    }

    public int a(int i) {
        return pumpkinStateService.resolveTextureBySide(i, this.textureId);
    }

    public void c(World world, int i, int j, int k) {
        super.c(world, i, j, k);
    }

    public boolean canPlace(World world, int i, int j, int k) {
        int l = world.getTypeId(i, j, k);
        return pumpkinStateService.canPlace((l == 0 || Block.byId[l].material.isReplacable()), world.e(i, j - 1, k));
    }

    public void postPlace(World world, int i, int j, int k, EntityLiving entityliving) {
        world.setData(i, j, k, pumpkinStateService.resolvePlacementDataFromYaw(entityliving.yaw));
    }

    // CraftBukkit start
    public void doPhysics(World world, int i, int j, int k, int l) {
        if (net.minecraft.server.Block.byId[l] != null && net.minecraft.server.Block.byId[l].isPowerSource()) {
            org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            int power = block.getBlockPower();

            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, power, power);
            world.getServer().getPluginManager().callEvent(eventRedstone);
        }
    }
    // CraftBukkit end
}
