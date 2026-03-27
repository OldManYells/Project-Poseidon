package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftChunk world/identity projections.
 */
public final class CraftChunkIdentityBehaviour {
    private static final CraftChunkIdentityBehaviour INSTANCE = new CraftChunkIdentityBehaviour();

    private CraftChunkIdentityBehaviour() {
    }

    public static CraftChunkIdentityBehaviour getInstance() {
        return INSTANCE;
    }

    public org.bukkit.World getWorld(net.minecraft.server.WorldServer worldServer) {
        return worldServer.getWorld();
    }

    public int getX(int x) {
        return x;
    }

    public int getZ(int z) {
        return z;
    }

    public String toString(int x, int z) {
        return "CraftChunk{" + "x=" + x + "z=" + z + '}';
    }
}
