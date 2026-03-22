package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.DiodeStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DiodeStateServiceTest {
    @Test
    public void placementTickTextureAndDelayRulesMatchLegacyBehavior() {
        DiodeStateBehaviour service = DiodeStateBehaviour.getInstance();

        Assert.assertTrue(service.canPlaceOnSupport(true, true));
        Assert.assertFalse(service.canPlaceOnSupport(false, true));
        Assert.assertTrue(service.canRemainOnSupport(true, true));
        Assert.assertFalse(service.canRemainOnSupport(false, true));
        Assert.assertTrue(service.shouldTurnOffOnTick(true, false));
        Assert.assertFalse(service.shouldTurnOffOnTick(true, true));
        Assert.assertTrue(service.shouldTurnOnOnTick(false));
        Assert.assertFalse(service.shouldTurnOnOnTick(true));
        Assert.assertTrue(service.shouldScheduleRecheckAfterTurnOn(false));
        Assert.assertFalse(service.shouldScheduleRecheckAfterTurnOn(true));
        Assert.assertEquals(2, service.resolveTickDelayFromData(0, new int[]{1, 2, 3, 4}));
        Assert.assertEquals(8, service.resolveTickDelayFromData(12, new int[]{1, 2, 3, 4}));
        Assert.assertEquals(115, service.resolveTextureBySideAndLit(0, false));
        Assert.assertEquals(99, service.resolveTextureBySideAndLit(0, true));
        Assert.assertEquals(131, service.resolveTextureBySideAndLit(1, false));
        Assert.assertEquals(147, service.resolveTextureBySideAndLit(1, true));
        Assert.assertEquals(5, service.resolveTextureBySideAndLit(2, true));
        Assert.assertTrue(service.shouldScheduleStateCheck(true, false));
        Assert.assertTrue(service.shouldScheduleStateCheck(false, true));
        Assert.assertFalse(service.shouldScheduleStateCheck(true, true));
        Assert.assertEquals(2, service.resolvePlacementDataFromYaw(0.0F));
        Assert.assertEquals(3, service.resolvePlacementDataFromYaw(90.0F));
        Assert.assertEquals(0, service.resolvePlacementDataFromYaw(180.0F));
        Assert.assertEquals(1, service.resolvePlacementDataFromYaw(270.0F));
        Assert.assertEquals(356, service.resolveDropItemId(356));
    }

    @Test
    public void inputPowerCycleAndNeighborPhysicsRulesMatchLegacyBehavior() {
        DiodeStateBehaviour service = DiodeStateBehaviour.getInstance();

        Assert.assertTrue(service.isPoweringSide(true, 0, 3));
        Assert.assertTrue(service.isPoweringSide(true, 1, 4));
        Assert.assertTrue(service.isPoweringSide(true, 2, 2));
        Assert.assertTrue(service.isPoweringSide(true, 3, 5));
        Assert.assertFalse(service.isPoweringSide(false, 0, 3));
        Assert.assertFalse(service.isPoweringSide(true, 0, 2));

        Assert.assertEquals(4, service.cycleDelayBits(0));
        Assert.assertEquals(8, service.cycleDelayBits(4));
        Assert.assertEquals(12, service.cycleDelayBits(8));
        Assert.assertEquals(0, service.cycleDelayBits(12));
        Assert.assertTrue(service.shouldScheduleImmediateTickOnPlace(true));
        Assert.assertFalse(service.shouldScheduleImmediateTickOnPlace(false));

        Assert.assertTrue(service.isInputPowered(new DiodeStateBehaviour.InputPowerQuery() {
            public boolean isBlockFaceIndirectlyPowered(int x, int y, int z, int face) {
                return x == 10 && y == 64 && z == 11 && face == 3;
            }

            public int getTypeId(int x, int y, int z) {
                return 0;
            }

            public int getData(int x, int y, int z) {
                return 0;
            }
        }, 10, 64, 10, 0, 55));

        Assert.assertTrue(service.isInputPowered(new DiodeStateBehaviour.InputPowerQuery() {
            public boolean isBlockFaceIndirectlyPowered(int x, int y, int z, int face) {
                return false;
            }

            public int getTypeId(int x, int y, int z) {
                return x == 9 && y == 64 && z == 10 ? 55 : 0;
            }

            public int getData(int x, int y, int z) {
                return x == 9 && y == 64 && z == 10 ? 1 : 0;
            }
        }, 10, 64, 10, 1, 55));

        Assert.assertFalse(service.isInputPowered(new DiodeStateBehaviour.InputPowerQuery() {
            public boolean isBlockFaceIndirectlyPowered(int x, int y, int z, int face) {
                return false;
            }

            public int getTypeId(int x, int y, int z) {
                return 0;
            }

            public int getData(int x, int y, int z) {
                return 0;
            }
        }, 10, 64, 10, 2, 55));

        List<String> updates = new ArrayList<String>();
        service.applyNeighborPhysics(new DiodeStateBehaviour.NeighborPhysicsApplier() {
            public void applyPhysics(int x, int y, int z, int blockId) {
                updates.add(x + ":" + y + ":" + z + ":" + blockId);
            }
        }, 10, 64, 10, 94);
        Assert.assertEquals(6, updates.size());
        Assert.assertTrue(updates.contains("11:64:10:94"));
        Assert.assertTrue(updates.contains("10:63:10:94"));
    }
}
