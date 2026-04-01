package org.bukkit.craftbukkit.block;

import com.legacyminecraft.poseidon.BlockDispenser;
import com.legacyminecraft.poseidon.TileEntityDispenser;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;

import java.util.Random;

public class CraftDispenser extends CraftBlockState implements Dispenser {
    private final CraftWorld world;
    private final TileEntityDispenser dispenser;

    public CraftDispenser(final Block block) {
        super(block);

        world = (CraftWorld) block.getWorld();
        dispenser = (TileEntityDispenser) world.getTileEntityAt(getX(), getY(), getZ());
    }

    public Inventory getInventory() {
        return new CraftInventory(dispenser);
    }

    public boolean dispense() {
        Block block = getBlock();

        synchronized (block) {
            if (block.getType() == Material.DISPENSER) {
                BlockDispenser dispense = (BlockDispenser) com.legacyminecraft.poseidon.Block.DISPENSER;

                dispense.dispense(world.getHandle(), getX(), getY(), getZ(), new Random());
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean update(boolean force) {
        boolean result = super.update(force);

        if (result) {
            dispenser.update();
        }

        return result;
    }
}
