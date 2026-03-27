package org.bukkit.craftbukkit.block;

import com.legacyminecraft.compat.bukkit.FurnaceBlockStateBehaviour;
import com.legacyminecraft.compat.bukkit.TileEntityLookupBehaviour;
import net.minecraft.server.TileEntityFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.Inventory;

public class CraftFurnace extends CraftBlockState implements Furnace {
    private static final FurnaceBlockStateBehaviour FURNACE_BLOCK_STATE_BEHAVIOUR =
            FurnaceBlockStateBehaviour.getInstance();
    private static final TileEntityLookupBehaviour TILE_ENTITY_LOOKUP_BEHAVIOUR =
            TileEntityLookupBehaviour.getInstance();
    private final TileEntityFurnace furnace;

    public CraftFurnace(final Block block) {
        super(block);
        furnace = TILE_ENTITY_LOOKUP_BEHAVIOUR.resolveFurnace(block, getX(), getY(), getZ());
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
