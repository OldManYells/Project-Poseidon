package com.legacyminecraft.poseidon.runtime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Canonical maintenance for legacy tracker countdown maps.
 */
public final class TrackerListMaintenance {
    private static final TrackerListMaintenance INSTANCE = new TrackerListMaintenance();

    private TrackerListMaintenance() {
    }

    public static TrackerListMaintenance getInstance() {
        return INSTANCE;
    }

    public void expireTrackerEntries(Map trackerList) {
        List expiredKeys = new ArrayList();
        Iterator iterator = trackerList.keySet().iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            int ticksRemaining = ((Integer) trackerList.get(key)).intValue();

            if (ticksRemaining > 0) {
                trackerList.put(key, Integer.valueOf(ticksRemaining - 1));
            } else {
                expiredKeys.add(key);
            }
        }

        for (int i = 0; i < expiredKeys.size(); ++i) {
            trackerList.remove(expiredKeys.get(i));
        }
    }
}
