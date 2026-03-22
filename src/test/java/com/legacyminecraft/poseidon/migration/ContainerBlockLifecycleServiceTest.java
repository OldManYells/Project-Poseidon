package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.ContainerBlockLifecycleBehaviour;
import net.minecraft.server.TileEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class ContainerBlockLifecycleServiceTest {
    @Test
    public void tileEntityMarkPlaceAndRemoveRulesMatchLegacyBehavior() {
        ContainerBlockLifecycleBehaviour service = ContainerBlockLifecycleBehaviour.getInstance();
        boolean[] flags = new boolean[256];

        service.markTileEntity(flags, 54);
        Assert.assertTrue(flags[54]);

        AtomicBoolean placed = new AtomicBoolean(false);
        AtomicBoolean removed = new AtomicBoolean(false);
        TileEntity tile = new TileEntity();
        service.placeTileEntity(new ContainerBlockLifecycleBehaviour.WorldTileEntityAccess() {
            public void setTileEntity(int x, int y, int z, TileEntity tileEntity) {
                placed.set(x == 10 && y == 64 && z == 10 && tileEntity == tile);
            }

            public void removeTileEntity(int x, int y, int z) {
            }
        }, 10, 64, 10, tile);
        Assert.assertTrue(placed.get());

        service.removeTileEntity(new ContainerBlockLifecycleBehaviour.WorldTileEntityAccess() {
            public void setTileEntity(int x, int y, int z, TileEntity tileEntity) {
            }

            public void removeTileEntity(int x, int y, int z) {
                removed.set(x == 10 && y == 64 && z == 10);
            }
        }, 10, 64, 10);
        Assert.assertTrue(removed.get());
    }
}
