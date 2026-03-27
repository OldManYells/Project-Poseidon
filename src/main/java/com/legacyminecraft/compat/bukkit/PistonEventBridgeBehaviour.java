package com.legacyminecraft.compat.bukkit;


/**
 * Canonical Bukkit bridge behaviour for piston extend/retract events.
 */
public final class PistonEventBridgeBehaviour {
    private static final PistonEventBridgeBehaviour INSTANCE = new PistonEventBridgeBehaviour();

    private PistonEventBridgeBehaviour() {
    }

    public static PistonEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isExtendAllowed(World world, int x, int y, int z, int movedBlockCount) {
        com.legacyminecraft.compat.bukkit.block.Block block = world.getWorld().getBlockAt(x, y, z);
        BlockPistonExtendEvent event = new BlockPistonExtendEvent(block, movedBlockCount);
        world.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public boolean isRetractAllowed(World world, int x, int y, int z) {
        com.legacyminecraft.compat.bukkit.block.Block block = world.getWorld().getBlockAt(x, y, z);
        BlockPistonRetractEvent event = new BlockPistonRetractEvent(block);
        world.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }
}
