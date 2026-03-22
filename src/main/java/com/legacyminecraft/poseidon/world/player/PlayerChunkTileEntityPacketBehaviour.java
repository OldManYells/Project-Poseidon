package com.legacyminecraft.poseidon.world.player;

import net.minecraft.server.Packet;
import net.minecraft.server.TileEntity;

/**
 * Canonical behaviour for chunk tile-entity packet extraction.
 */
public final class PlayerChunkTileEntityPacketBehaviour {
    private static final PlayerChunkTileEntityPacketBehaviour INSTANCE = new PlayerChunkTileEntityPacketBehaviour();

    private PlayerChunkTileEntityPacketBehaviour() {
    }

    public static PlayerChunkTileEntityPacketBehaviour getInstance() {
        return INSTANCE;
    }

    public Packet extractUpdatePacket(TileEntity tileEntity) {
        if (tileEntity == null) {
            return null;
        }

        return tileEntity.f();
    }
}
