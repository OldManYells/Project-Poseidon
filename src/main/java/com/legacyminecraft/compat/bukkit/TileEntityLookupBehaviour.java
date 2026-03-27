package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftBukkit block-wrapper tile-entity lookup orchestration.
 */
public final class TileEntityLookupBehaviour {
    private static final TileEntityLookupBehaviour INSTANCE = new TileEntityLookupBehaviour();
    private static final WorldHandleBridgeBehaviour WORLD_HANDLE_BRIDGE_BEHAVIOUR =
            WorldHandleBridgeBehaviour.getInstance();

    private TileEntityLookupBehaviour() {
    }

    public static TileEntityLookupBehaviour getInstance() {
        return INSTANCE;
    }

    public org.bukkit.craftbukkit.CraftWorld resolveWorld(org.bukkit.block.Block block) {
        return WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveCraftWorld(block.getWorld());
    }

    public net.minecraft.server.TileEntityChest resolveChest(org.bukkit.block.Block block, int x, int y, int z) {
        return (net.minecraft.server.TileEntityChest) resolveWorld(block).getTileEntityAt(x, y, z);
    }

    public net.minecraft.server.TileEntityDispenser resolveDispenser(org.bukkit.block.Block block, int x, int y, int z) {
        return (net.minecraft.server.TileEntityDispenser) resolveWorld(block).getTileEntityAt(x, y, z);
    }

    public net.minecraft.server.TileEntityFurnace resolveFurnace(org.bukkit.block.Block block, int x, int y, int z) {
        return (net.minecraft.server.TileEntityFurnace) resolveWorld(block).getTileEntityAt(x, y, z);
    }

    public net.minecraft.server.TileEntityMobSpawner resolveMobSpawner(org.bukkit.block.Block block, int x, int y, int z) {
        return (net.minecraft.server.TileEntityMobSpawner) resolveWorld(block).getTileEntityAt(x, y, z);
    }

    public net.minecraft.server.TileEntityNote resolveNote(org.bukkit.block.Block block, int x, int y, int z) {
        return (net.minecraft.server.TileEntityNote) resolveWorld(block).getTileEntityAt(x, y, z);
    }

    public net.minecraft.server.TileEntitySign resolveSign(org.bukkit.block.Block block, int x, int y, int z) {
        return (net.minecraft.server.TileEntitySign) resolveWorld(block).getTileEntityAt(x, y, z);
    }
}
