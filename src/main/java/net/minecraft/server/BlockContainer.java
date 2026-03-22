package net.minecraft.server;

import com.legacyminecraft.poseidon.block.ContainerBlockLifecycleBehaviour;

public abstract class BlockContainer extends Block {
    private static final ContainerBlockLifecycleBehaviour CONTAINER_BLOCK_LIFECYCLE_SERVICE = ContainerBlockLifecycleBehaviour.getInstance();

    protected BlockContainer(int i, Material material) {
        super(i, material);
        CONTAINER_BLOCK_LIFECYCLE_SERVICE.markTileEntity(isTileEntity, i);
    }

    protected BlockContainer(int i, int j, Material material) {
        super(i, j, material);
        CONTAINER_BLOCK_LIFECYCLE_SERVICE.markTileEntity(isTileEntity, i);
    }

    public void c(World world, int i, int j, int k) {
        super.c(world, i, j, k);
        CONTAINER_BLOCK_LIFECYCLE_SERVICE.placeTileEntity(new ContainerBlockLifecycleBehaviour.WorldTileEntityAccess() {
            public void setTileEntity(int x, int y, int z, TileEntity tileEntity) {
                world.setTileEntity(x, y, z, tileEntity);
            }

            public void removeTileEntity(int x, int y, int z) {
                world.o(x, y, z);
            }
        }, i, j, k, this.a_());
    }

    public void remove(World world, int i, int j, int k) {
        super.remove(world, i, j, k);
        CONTAINER_BLOCK_LIFECYCLE_SERVICE.removeTileEntity(new ContainerBlockLifecycleBehaviour.WorldTileEntityAccess() {
            public void setTileEntity(int x, int y, int z, TileEntity tileEntity) {
                world.setTileEntity(x, y, z, tileEntity);
            }

            public void removeTileEntity(int x, int y, int z) {
                world.o(x, y, z);
            }
        }, i, j, k);
    }

    protected abstract TileEntity a_();
}
