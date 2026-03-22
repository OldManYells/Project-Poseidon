package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Packet61;
import net.minecraft.server.TileEntity;
import net.minecraft.server.WorldServer;

public final class WorldAccessDispatchBehaviour {
    private static final WorldAccessDispatchBehaviour INSTANCE = new WorldAccessDispatchBehaviour();

    private WorldAccessDispatchBehaviour() {
    }

    public static WorldAccessDispatchBehaviour getInstance() {
        return INSTANCE;
    }

    public void onEntityAdded(MinecraftServer server, WorldServer world, Entity entity) {
        server.getTracker(world.dimension).track(entity);
    }

    public void onEntityRemoved(MinecraftServer server, WorldServer world, Entity entity) {
        server.getTracker(world.dimension).untrackEntity(entity);
    }

    public void markBlockDirty(MinecraftServer server, WorldServer world, int i, int j, int k) {
        server.serverConfigurationManager.flagDirty(i, j, k, world.dimension);
    }

    public void onTileEntityChanged(MinecraftServer server, int i, int j, int k, TileEntity tileentity) {
        server.serverConfigurationManager.a(i, j, k, tileentity);
    }

    public void sendAuxSfx(MinecraftServer server, WorldServer world, EntityHuman entityhuman, int i, int j, int k, int l, int i1) {
        server.serverConfigurationManager.sendPacketNearby(entityhuman, (double) j, (double) k, (double) l, 64.0D, world.dimension, new Packet61(i, j, k, l, i1));
    }
}
