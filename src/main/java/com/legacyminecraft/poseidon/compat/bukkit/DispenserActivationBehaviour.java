package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.BlockDispenser;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Random;

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

    public boolean tryDispense(Block block, net.minecraft.server.World world, int x, int y, int z) {
        synchronized (block) {
            if (block.getType() != Material.DISPENSER) {
                return false;
            }

            BlockDispenser dispenser = (BlockDispenser) net.minecraft.server.Block.DISPENSER;
            dispenser.dispense(world, x, y, z, new Random());
            return true;
        }
    }
}
