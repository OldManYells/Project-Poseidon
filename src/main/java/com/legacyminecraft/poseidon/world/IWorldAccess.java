package com.legacyminecraft.poseidon.world;

/**
 * World listener callback contract used by world access notification behaviours.
 */
public interface IWorldAccess {
    void a(int x, int y, int z);

    void a(int minX, int minY, int minZ, int maxX, int maxY, int maxZ);

    void a(String sound, double x, double y, double z, float volume, float pitch);

    void a(String event, int x, int y, int z);

    void a(String particle, double x, double y, double z, double velocityX, double velocityY, double velocityZ);

    void a(Entity entity);

    void b(Entity entity);

    void a();

    void a(int x, int y, int z, TileEntity tileEntity);

    void a(EntityHuman source, int effectId, int x, int y, int data, int extraData);
}
