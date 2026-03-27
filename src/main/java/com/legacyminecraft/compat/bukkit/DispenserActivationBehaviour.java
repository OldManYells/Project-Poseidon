package com.legacyminecraft.compat.bukkit;


import java.util.Random;
import net.minecraft.server.Block;
import net.minecraft.server.BlockDispenser;
import net.minecraft.server.World;
import org.bukkit.Material;

/**
 * Canonical behaviour for CraftDispenser activation policy and random-source orchestration.
 */
public final class DispenserActivationBehaviour {
    private static final DispenserActivationBehaviour INSTANCE = new DispenserActivationBehaviour();

    private DispenserActivationBehaviour() {
    }

    public static DispenserActivationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean tryDispense(org.bukkit.block.Block block, World world, int x, int y, int z) {
        synchronized (block) {
            if (block.getType() != Material.DISPENSER) {
                return false;
            }

            BlockDispenser dispenser = (BlockDispenser) Block.DISPENSER;
            dispenser.dispense(world, x, y, z, new Random());
            return true;
        }
    }
}
