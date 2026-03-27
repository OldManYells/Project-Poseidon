package com.legacyminecraft.poseidon.block;

/**
 * Canonical tile-entity lifecycle policy for legacy container-block wrappers.
 */
public final class ContainerBlockLifecycleBehaviour {
    private static final ContainerBlockLifecycleBehaviour INSTANCE = new ContainerBlockLifecycleBehaviour();

    private ContainerBlockLifecycleBehaviour() {
    }

    public static ContainerBlockLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public void markTileEntity(boolean[] isTileEntityById, int blockId) {
        isTileEntityById[blockId] = true;
    }

    public void placeTileEntity(WorldTileEntityAccess worldAccess, int x, int y, int z, Object tileEntity) {
        worldAccess.setTileEntity(x, y, z, tileEntity);
    }

    public void removeTileEntity(WorldTileEntityAccess worldAccess, int x, int y, int z) {
        worldAccess.removeTileEntity(x, y, z);
    }

    public interface WorldTileEntityAccess {
        void setTileEntity(int x, int y, int z, Object tileEntity);

        void removeTileEntity(int x, int y, int z);
    }
}
