package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.poseidon.entity.EntityHuman;

/**
 * Canonical world access callback contract bridged by legacy wrappers.
 */
public interface WorldAccessContract {
    void onBlockChanged(int x, int y, int z);

    void onBlockRangeChanged(int minX, int minY, int minZ, int maxX, int maxY, int maxZ);

    void playSound(String soundName, double x, double y, double z, float volume, float pitch);

    void spawnParticle(String particleName, double x, double y, double z, double motionX, double motionY, double motionZ);

    void onEntityAdded(Entity entity);

    void onEntityRemoved(Entity entity);

    void flush();

    void playRecord(String recordName, int x, int y, int z);

    void onTileEntityChanged(int x, int y, int z, TileEntity tileEntity);

    void playAuxSfx(EntityHuman player, int effectId, int x, int y, int z, int data);
}
