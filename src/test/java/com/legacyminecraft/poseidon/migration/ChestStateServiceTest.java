package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.ChestStateBehaviour;
import net.minecraft.server.IInventory;
import net.minecraft.server.InventoryLargeChest;
import net.minecraft.server.TileEntityChest;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChestStateServiceTest {
    @Test
    public void texturePlacementAndBlockingRulesMatchLegacyChestBehavior() {
        ChestStateBehaviour service = ChestStateBehaviour.getInstance();

        Assert.assertEquals(25, service.resolveTextureBySide(1, 26));
        Assert.assertEquals(25, service.resolveTextureBySide(0, 26));
        Assert.assertEquals(27, service.resolveTextureBySide(3, 26));
        Assert.assertEquals(26, service.resolveTextureBySide(2, 26));

        Assert.assertEquals(2, service.countAdjacentChests(new ChestStateBehaviour.TypeIdQuery() {
            public int getTypeId(int x, int y, int z) {
                return x == 9 || x == 11 ? 54 : 0;
            }
        }, 10, 64, 10, 54));

        Assert.assertTrue(service.hasAdjacentDoubleChest(new ChestStateBehaviour.TypeIdQuery() {
            public int getTypeId(int x, int y, int z) {
                return x == 10 || x == 9 ? 54 : 0;
            }
        }, 10, 64, 10, 54));

        Assert.assertFalse(service.canPlace(2, false, false, false, false));
        Assert.assertFalse(service.canPlace(1, true, false, false, false));
        Assert.assertTrue(service.canPlace(1, false, false, false, false));
        Assert.assertTrue(service.shouldBlockAccessFromTop(true, false, false, false, false));
        Assert.assertFalse(service.shouldBlockAccessFromTop(false, false, false, false, false));
    }

    @Test
    public void composedInventoryAndDropRandomizationRulesMatchLegacyBehavior() {
        ChestStateBehaviour service = ChestStateBehaviour.getInstance();
        final Map<String, Integer> types = new HashMap<String, Integer>();
        final Map<String, TileEntityChest> chests = new HashMap<String, TileEntityChest>();
        TileEntityChest center = new TileEntityChest();
        TileEntityChest west = new TileEntityChest();
        types.put("9:64:10", 54);
        chests.put("9:64:10", west);

        IInventory composed = service.composeChestInventory(new ChestStateBehaviour.ChestAccess() {
            public int getTypeId(int x, int y, int z) {
                Integer id = types.get(x + ":" + y + ":" + z);
                return id == null ? 0 : id;
            }

            public TileEntityChest getChest(int x, int y, int z) {
                return chests.get(x + ":" + y + ":" + z);
            }
        }, 10, 64, 10, 54, center);
        Assert.assertTrue(composed instanceof InventoryLargeChest);

        Random seeded = new Random(13L);
        float offset = service.resolveDropOffset(seeded);
        Assert.assertTrue(offset >= 0.1F);
        Assert.assertTrue(offset <= 0.9F);
        Assert.assertEquals(10, service.resolveDropStackChunk(new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        }, 64));
        Assert.assertEquals(5, service.resolveDropStackChunk(new Random() {
            @Override
            public int nextInt(int bound) {
                return 20;
            }
        }, 5));

        double motionX = service.resolveDropHorizontalMotion(seeded, 0.05F);
        double motionY = service.resolveDropVerticalMotion(seeded, 0.05F, 0.2D);
        Assert.assertTrue(motionX > -1.0D && motionX < 1.0D);
        Assert.assertTrue(motionY > -1.0D && motionY < 1.0D);
    }
}
