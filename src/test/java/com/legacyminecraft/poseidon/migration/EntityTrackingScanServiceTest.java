package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.entity.EntityTrackingScanSystem;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;

public class EntityTrackingScanServiceTest {
    @Test
    public void scanningEmptyViewerListProducesNoMotionSnapshot() {
        EntityTrackingScanSystem service = EntityTrackingScanSystem.getInstance();

        Assert.assertNull(
                service.scanPlayers(
                        null,
                        Collections.emptyList(),
                        new HashSet(),
                        64,
                        0,
                        0,
                        false
                )
        );
    }
}
