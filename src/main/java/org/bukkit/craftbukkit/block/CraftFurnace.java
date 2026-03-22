package org.bukkit.craftbukkit.block;

import com.legacyminecraft.poseidon.compat.bukkit.FurnaceBlockStateBehaviour;
import net.minecraft.server.TileEntityFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.inventory.Inventory;

public class CraftFurnace extends CraftBlockState implements Furnace {
    private static final FurnaceBlockStateBehaviour FURNACE_BLOCK_STATE_BEHAVIOUR =
            FurnaceBlockStateBehaviour.getInstance();
    private final TileEntityFurnace furnace;

    public CraftFurnace(final Block block) {
        super(block);

        CraftWorld world = (CraftWorld) block.getWorld();
        furnace = (TileEntityFurnace) world.getTileEntityAt(getX(), getY(), getZ());
    }

    public Inventory getInventory() {
        return FURNACE_BLOCK_STATE_BEHAVIOUR.createInventory(furnace);
    }

    @Override
    public boolean update(boolean force) {
        return FURNACE_BLOCK_STATE_BEHAVIOUR.finalizeUpdate(super.update(force), furnace);
    }

    public short getBurnTime() {
        return FURNACE_BLOCK_STATE_BEHAVIOUR.getBurnTime(furnace);
    }

    public void setBurnTime(short burnTime) {
        FURNACE_BLOCK_STATE_BEHAVIOUR.setBurnTime(furnace, burnTime);
    }

    public short getCookTime() {
        return FURNACE_BLOCK_STATE_BEHAVIOUR.getCookTime(furnace);
    }

    public void setCookTime(short cookTime) {
        FURNACE_BLOCK_STATE_BEHAVIOUR.setCookTime(furnace, cookTime);
    }
}
