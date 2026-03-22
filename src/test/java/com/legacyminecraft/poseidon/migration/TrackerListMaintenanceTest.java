package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.TrackerListMaintenance;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TrackerListMaintenanceTest {
    @Test
    public void decrementsAndRemovesExpiredEntries() {
        Map trackerList = new HashMap();
        trackerList.put("keep", Integer.valueOf(2));
        trackerList.put("remove", Integer.valueOf(0));

        TrackerListMaintenance.getInstance().expireTrackerEntries(trackerList);

        Assert.assertEquals(Integer.valueOf(1), trackerList.get("keep"));
        Assert.assertFalse(trackerList.containsKey("remove"));
    }
}
