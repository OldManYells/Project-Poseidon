package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ServerTickTimingPolicy;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerTickTimingPolicyTest {
    private final ServerTickTimingPolicy policy = ServerTickTimingPolicy.getInstance();
    private final Logger logger = buildSilentLogger();

    @Test
    public void clampsElapsedTickTime() {
        Assert.assertEquals(2000L, policy.normalizeElapsedMillis(5000L, logger));
        Assert.assertEquals(0L, policy.normalizeElapsedMillis(-50L, logger));
        Assert.assertEquals(42L, policy.normalizeElapsedMillis(42L, logger));
    }

    private static Logger buildSilentLogger() {
        Logger logger = Logger.getLogger("test-server-timing");
        logger.setLevel(Level.OFF);
        return logger;
    }
}
