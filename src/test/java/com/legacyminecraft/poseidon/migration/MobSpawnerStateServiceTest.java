package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.MobSpawnerStateBehaviour;
import net.minecraft.server.TileEntity;
import net.minecraft.server.TileEntityMobSpawner;
import org.junit.Assert;
import org.junit.Test;

public class MobSpawnerStateServiceTest {
    @Test
    public void tileDropAndOpacityRulesMatchLegacyMobSpawnerBehavior() {
        MobSpawnerStateBehaviour service = MobSpawnerStateBehaviour.getInstance();

        TileEntity tileEntity = service.createTileEntity();
        Assert.assertTrue(tileEntity instanceof TileEntityMobSpawner);
        Assert.assertEquals(0, service.resolveDropItemId());
        Assert.assertEquals(0, service.resolveDropCount());
        Assert.assertFalse(service.isOpaqueCube());
    }
}
