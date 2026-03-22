package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.FenceCollisionBehaviour;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.IBlockAccess;
import net.minecraft.server.Material;
import net.minecraft.server.TileEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FenceCollisionServiceTest {
    @Test
    public void canPlaceOnTopRespectsLegacyPlacementRules() {
        FenceCollisionBehaviour service = FenceCollisionBehaviour.getInstance();

        Assert.assertTrue(service.canPlaceOnTop(85, Material.AIR, 85, false));
        Assert.assertFalse(service.canPlaceOnTop(0, Material.AIR, 85, true));
        Assert.assertTrue(service.canPlaceOnTop(1, Material.STONE, 85, true));
    }

    @Test
    public void resolveCollisionBoxUsesLegacyAndModernBoundingPolicies() {
        FenceCollisionBehaviour service = FenceCollisionBehaviour.getInstance();
        StubBlockAccess access = new StubBlockAccess();
        access.put(0, 64, -1, 85);
        access.put(1, 64, 0, 85);

        AxisAlignedBB legacy = service.resolveCollisionBox(access, 0, 64, 0, 85, false);
        Assert.assertEquals(0.0D, legacy.a, 0.0D);
        Assert.assertEquals(0.0D, legacy.c, 0.0D);
        Assert.assertEquals(1.0D, legacy.d, 0.0D);
        Assert.assertEquals(1.0D, legacy.f, 0.0D);

        AxisAlignedBB modern = service.resolveCollisionBox(access, 0, 64, 0, 85, true);
        Assert.assertEquals(0.375D, modern.a, 0.0D);
        Assert.assertEquals(0.0D, modern.c, 0.0D);
        Assert.assertEquals(1.0D, modern.d, 0.0D);
        Assert.assertEquals(0.625D, modern.f, 0.0D);
    }

    @Test
    public void isConnectableMatchesLegacyBlockMaterialAndShapeRules() {
        FenceCollisionBehaviour service = FenceCollisionBehaviour.getInstance();
        StubBlockAccess access = new StubBlockAccess();

        access.put(0, 0, 0, 85);
        Assert.assertTrue(service.isConnectable(access, 0, 0, 0, 85));

        access.put(1, 0, 0, Block.STONE.id);
        Assert.assertTrue(service.isConnectable(access, 1, 0, 0, 85));

        access.put(2, 0, 0, Block.PUMPKIN.id);
        Assert.assertFalse(service.isConnectable(access, 2, 0, 0, 85));

        access.put(3, 0, 0, 0);
        Assert.assertFalse(service.isConnectable(access, 3, 0, 0, 85));
    }

    private static final class StubBlockAccess implements IBlockAccess {
        private final Map keyToType = new HashMap();

        public void put(int x, int y, int z, int typeId) {
            keyToType.put(key(x, y, z), Integer.valueOf(typeId));
        }

        public int getTypeId(int i, int j, int k) {
            Integer typeId = (Integer) keyToType.get(key(i, j, k));
            return typeId == null ? 0 : typeId.intValue();
        }

        public TileEntity getTileEntity(int i, int j, int k) {
            return null;
        }

        public int getData(int i, int j, int k) {
            return 0;
        }

        public Material getMaterial(int i, int j, int k) {
            int typeId = this.getTypeId(i, j, k);
            Block block = Block.byId[typeId];
            return block == null ? Material.AIR : block.material;
        }

        public boolean e(int i, int j, int k) {
            return false;
        }

        private String key(int x, int y, int z) {
            return x + ":" + y + ":" + z;
        }
    }
}
