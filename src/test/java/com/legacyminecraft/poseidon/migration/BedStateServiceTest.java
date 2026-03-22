package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.BedStateBehaviour;
import net.minecraft.server.BedBlockTextures;
import org.junit.Assert;
import org.junit.Test;

public class BedStateServiceTest {
    @Test
    public void bitOrientationTextureAndCounterpartRulesMatchLegacyBedBehavior() {
        BedStateBehaviour service = BedStateBehaviour.getInstance();
        int[][] offsets = new int[][]{{0, 1}, {-1, 0}, {0, -1}, {1, 0}};

        Assert.assertEquals(2, service.orientation(6));
        Assert.assertTrue(service.isHead(8));
        Assert.assertFalse(service.isHead(4));
        Assert.assertTrue(service.isOccupied(4));
        Assert.assertFalse(service.isOccupied(8));
        Assert.assertEquals(4, service.setOccupied(0, true));
        Assert.assertEquals(0, service.setOccupied(4, false));
        Assert.assertEquals(355, service.resolveDropItemId(0, 355));
        Assert.assertEquals(0, service.resolveDropItemId(8, 355));

        Assert.assertEquals(5, service.resolveTextureBySide(0, 0, 134, 5, BedBlockTextures.c));
        Assert.assertEquals(134, service.resolveTextureBySide(3, 0, 134, 5, BedBlockTextures.c));
        Assert.assertEquals(149, service.resolveTextureBySide(2, 0, 134, 5, BedBlockTextures.c));
        Assert.assertEquals(152, service.resolveTextureBySide(3, 8, 134, 5, BedBlockTextures.c));
        Assert.assertEquals(151, service.resolveTextureBySide(4, 8, 134, 5, BedBlockTextures.c));

        Assert.assertEquals(10, service.resolveHeadXFromPart(10, 0, offsets, true));
        Assert.assertEquals(10, service.resolveHeadXFromPart(10, 0, offsets, false));
        Assert.assertEquals(11, service.resolveHeadZFromPart(10, 0, offsets, false));
        Assert.assertEquals(11, service.resolveOtherHalfZFromHead(10, 0, offsets));
        Assert.assertEquals(11, service.resolveCounterpartXForPhysics(10, 1, offsets, true));
        Assert.assertEquals(9, service.resolveCounterpartXForPhysics(10, 1, offsets, false));
        Assert.assertTrue(service.shouldExplodeInDimension(false));
        Assert.assertFalse(service.shouldExplodeInDimension(true));
        Assert.assertTrue(service.shouldRemovePartForMissingCounterpart(0, 26));
        Assert.assertFalse(service.shouldRemovePartForMissingCounterpart(26, 26));
        Assert.assertTrue(service.shouldDropNaturally(0));
        Assert.assertFalse(service.shouldDropNaturally(8));
        Assert.assertEquals(10.5D, service.centeredCoordinate(10), 0.0D);
        Assert.assertEquals(3.0D, service.average(2.0D, 4.0D), 0.0D);
    }

    @Test
    public void spawnSearchRulesMatchLegacyBedBehavior() {
        BedStateBehaviour service = BedStateBehaviour.getInstance();
        int[][] offsets = new int[][]{{0, 1}, {-1, 0}, {0, -1}, {1, 0}};

        BedStateBehaviour.ChunkCoord first = service.findSpawnPosition(new BedStateBehaviour.SpawnQuery() {
            public boolean isSolidTopSurface(int x, int y, int z) {
                return true;
            }

            public boolean isEmpty(int x, int y, int z) {
                return true;
            }
        }, 10, 64, 10, 0, 0, offsets);
        Assert.assertNotNull(first);
        Assert.assertEquals(9, first.x);
        Assert.assertEquals(64, first.y);
        Assert.assertEquals(9, first.z);

        BedStateBehaviour.ChunkCoord second = service.findSpawnPosition(new BedStateBehaviour.SpawnQuery() {
            public boolean isSolidTopSurface(int x, int y, int z) {
                return true;
            }

            public boolean isEmpty(int x, int y, int z) {
                return true;
            }
        }, 10, 64, 10, 0, 1, offsets);
        Assert.assertNotNull(second);
        Assert.assertEquals(9, second.x);
        Assert.assertEquals(64, second.y);
        Assert.assertEquals(10, second.z);

        BedStateBehaviour.ChunkCoord none = service.findSpawnPosition(new BedStateBehaviour.SpawnQuery() {
            public boolean isSolidTopSurface(int x, int y, int z) {
                return false;
            }

            public boolean isEmpty(int x, int y, int z) {
                return false;
            }
        }, 10, 64, 10, 0, 0, offsets);
        Assert.assertNull(none);
    }
}
