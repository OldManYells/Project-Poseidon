package org.bukkit.craftbukkit.block;

import com.legacyminecraft.poseidon.compat.bukkit.DispenserActivationBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.TileEntityBlockStateUpdateBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.TileEntityInventoryBridgeBehaviour;
import net.minecraft.server.TileEntityDispenser;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.inventory.Inventory;

public class CraftDispenser extends CraftBlockState implements Dispenser {
    private static final DispenserActivationBehaviour DISPENSER_ACTIVATION_BEHAVIOUR =
            DispenserActivationBehaviour.getInstance();
    private static final TileEntityInventoryBridgeBehaviour TILE_ENTITY_INVENTORY_BRIDGE_BEHAVIOUR =
            TileEntityInventoryBridgeBehaviour.getInstance();
    private static final TileEntityBlockStateUpdateBehaviour TILE_ENTITY_BLOCK_STATE_UPDATE_BEHAVIOUR =
            TileEntityBlockStateUpdateBehaviour.getInstance();

    private final CraftWorld world;
    private final TileEntityDispenser dispenser;

    public CraftDispenser(final Block block) {
        super(block);

        world = (CraftWorld) block.getWorld();
        dispenser = (TileEntityDispenser) world.getTileEntityAt(getX(), getY(), getZ());
    }

    public Inventory getInventory() {
        return TILE_ENTITY_INVENTORY_BRIDGE_BEHAVIOUR.createInventory(dispenser);
    }

    public boolean dispense() {
        return DISPENSER_ACTIVATION_BEHAVIOUR.tryDispense(getBlock(), world.getHandle(), getX(), getY(), getZ());
    }

    @Override
    public boolean update(boolean force) {
        return TILE_ENTITY_BLOCK_STATE_UPDATE_BEHAVIOUR.finalizeUpdate(super.update(force), dispenser);
    }
}
