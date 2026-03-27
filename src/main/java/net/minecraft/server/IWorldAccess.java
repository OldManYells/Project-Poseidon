package net.minecraft.server;

import com.legacyminecraft.poseidon.world.WorldAccessContract;

public interface IWorldAccess extends com.legacyminecraft.poseidon.world.IWorldAccess, WorldAccessContract {

    void a(int i, int j, int k);

    void a(int i, int j, int k, int l, int i1, int j1);

    void a(String s, double d0, double d1, double d2, float f, float f1);

    void a(String s, double d0, double d1, double d2, double d3, double d4, double d5);

    void a(Entity entity);

    void b(Entity entity);

    void a();

    void a(String s, int i, int j, int k);

    void a(int i, int j, int k, TileEntity tileentity);

    void a(EntityHuman entityhuman, int i, int j, int k, int l, int i1);

    default void onBlockChanged(int x, int y, int z) {
        this.a(x, y, z);
    }

    default void onBlockRangeChanged(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.a(minX, minY, minZ, maxX, maxY, maxZ);
    }

    default void playSound(String soundName, double x, double y, double z, float volume, float pitch) {
        this.a(soundName, x, y, z, volume, pitch);
    }

    default void spawnParticle(String particleName, double x, double y, double z, double motionX, double motionY, double motionZ) {
        this.a(particleName, x, y, z, motionX, motionY, motionZ);
    }

    default void onEntityAdded(Entity entity) {
        this.a(entity);
    }

    default void onEntityRemoved(Entity entity) {
        this.b(entity);
    }

    default void flush() {
        this.a();
    }

    default void playRecord(String recordName, int x, int y, int z) {
        this.a(recordName, x, y, z);
    }

    default void onTileEntityChanged(int x, int y, int z, TileEntity tileEntity) {
        this.a(x, y, z, tileEntity);
    }

    default void playAuxSfx(EntityHuman player, int effectId, int x, int y, int z, int data) {
        this.a(player, effectId, x, y, z, data);
    }
}
