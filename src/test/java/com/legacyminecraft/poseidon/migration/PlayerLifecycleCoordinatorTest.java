package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.player.PlayerLifecycleCoordinator;
import org.junit.Assert;
import org.junit.Test;

public class PlayerLifecycleCoordinatorTest {
    private final PlayerLifecycleCoordinator coordinator = PlayerLifecycleCoordinator.getInstance();

    @Test
    public void formatsUpdateMessagePlaceholders() {
        String formatted = coordinator.buildUpdateAvailableMessage(
                "Update %newversion% (current %currentversion%)",
                "1.2.3",
                "1.2.2");

        Assert.assertEquals("Update 1.2.3 (current 1.2.2)", formatted);
    }

    @Test
    public void shouldNotifyUpdateIsFalseForNullPlayer() {
        Assert.assertFalse(coordinator.shouldNotifyUpdate(null));
    }
}
