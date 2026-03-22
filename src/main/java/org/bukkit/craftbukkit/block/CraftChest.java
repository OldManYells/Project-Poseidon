package org.bukkit.craftbukkit.block;

import com.legacyminecraft.poseidon.compat.bukkit.TileEntityBlockStateUpdateBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.TileEntityInventoryBridgeBehaviour;
import net.minecraft.server.TileEntityChest;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.inventory.Inventory;

public class CraftChest extends CraftBlockState implements Chest {
    private static final TileEntityInventoryBridgeBehaviour TILE_ENTITY_INVENTORY_BRIDGE_BEHAVIOUR =
            TileEntityInventoryBridgeBehaviour.getInstance();
    private static final TileEntityBlockStateUpdateBehaviour TILE_ENTITY_BLOCK_STATE_UPDATE_BEHAVIOUR =
            TileEntityBlockStateUpdateBehaviour.getInstance();

    private final TileEntityChest chest;

    public CraftChest(final Block block) {
        super(block);

        CraftWorld world = (CraftWorld) block.getWorld();
        chest = (TileEntityChest) world.getTileEntityAt(getX(), getY(), getZ());
    }

    public Inventory getInventory() {
        return TILE_ENTITY_INVENTORY_BRIDGE_BEHAVIOUR.createInventory(chest);
    }

    @Override
    public boolean update(boolean force) {
        return TILE_ENTITY_BLOCK_STATE_UPDATE_BEHAVIOUR.finalizeUpdate(super.update(force), chest);
    }
}
