package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

public class LegacyLoginCompatibilityTest {
    @Test
    public void legacyConnectionPauseWrapsCanonicalPause() {
        com.legacyminecraft.poseidon.auth.login.ConnectionPause canonical =
                new com.legacyminecraft.poseidon.auth.login.ConnectionPause("PluginA", "PauseA");

        com.projectposeidon.johnymuffin.ConnectionPause legacy =
                com.projectposeidon.johnymuffin.ConnectionPause.fromCanonical(canonical, null, null);

        Assert.assertEquals("PluginA", legacy.getPluginName());
        Assert.assertEquals("PauseA", legacy.getConnectionPauseName());
        Assert.assertSame(canonical, legacy.getCanonicalConnectionPause());
    }
}
