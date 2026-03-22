package net.minecraft.server;

import com.legacyminecraft.poseidon.block.RedstoneNeighborEventBehaviour;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockBloodStone extends Block {
    private final RedstoneNeighborEventBehaviour redstoneNeighborEventService = RedstoneNeighborEventBehaviour.getInstance();

    public BlockBloodStone(int i, int j) {
        super(i, j, Material.STONE);
    }

    // CraftBukkit start
    public void doPhysics(World world, int i, int j, int k, int l) {
        if (redstoneNeighborEventService.shouldFireNeighborPowerEvent(
                net.minecraft.server.Block.byId[l] != null,
                net.minecraft.server.Block.byId[l] != null && net.minecraft.server.Block.byId[l].isPowerSource()
        )) {
            org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            int power = block.getBlockPower();

            BlockRedstoneEvent event = new BlockRedstoneEvent(block, power, power);
            world.getServer().getPluginManager().callEvent(event);
        }
    }
    // CraftBukkit end
}
