package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.RedstoneTorchStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RedstoneTorchStateServiceTest {
    @Test
    public void canProvidePowerMatchesLegacyBlockedFaces() {
        RedstoneTorchStateBehaviour service = RedstoneTorchStateBehaviour.getInstance();

        Assert.assertFalse(service.canProvidePower(false, 5, 0));
        Assert.assertFalse(service.canProvidePower(true, 5, 1));
        Assert.assertFalse(service.canProvidePower(true, 3, 3));
        Assert.assertTrue(service.canProvidePower(true, 3, 1));
    }

    @Test
    public void receivingIndirectPowerUsesAttachmentSpecificFaceChecks() {
        RedstoneTorchStateBehaviour service = RedstoneTorchStateBehaviour.getInstance();

        boolean powered = service.isReceivingIndirectPower(
                5,
                10,
                20,
                30,
                new RedstoneTorchStateBehaviour.IndirectPowerQuery() {
                    @Override
                    public boolean isBlockFaceIndirectlyPowered(int x, int y, int z, int face) {
                        return x == 10 && y == 19 && z == 30 && face == 0;
                    }
                }
        );

        Assert.assertTrue(powered);
    }

    @Test
    public void purgeExpiredUpdatesRemovesOnlyOldEntries() {
        RedstoneTorchStateBehaviour service = RedstoneTorchStateBehaviour.getInstance();
        List updates = new ArrayList();
        updates.add(new RedstoneTorchStateBehaviour.UpdateInfo(1, 2, 3, 10L));
        updates.add(new RedstoneTorchStateBehaviour.UpdateInfo(1, 2, 3, 60L));

        service.purgeExpiredUpdates(updates, 150L, 100L);

        Assert.assertEquals(1, updates.size());
        Assert.assertEquals(60L, ((RedstoneTorchStateBehaviour.UpdateInfo) updates.get(0)).getTime());
    }

    @Test
    public void recordAndCheckBurnoutTripsAtThreshold() {
        RedstoneTorchStateBehaviour service = RedstoneTorchStateBehaviour.getInstance();
        List updates = new ArrayList();

        boolean burnedOut = false;
        for (int i = 0; i < 8; i++) {
            burnedOut = service.recordAndCheckBurnout(updates, 4, 5, 6, i, true, 8);
        }

        Assert.assertTrue(burnedOut);
    }
}
