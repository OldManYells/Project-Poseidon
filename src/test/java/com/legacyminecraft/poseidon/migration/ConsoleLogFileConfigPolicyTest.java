package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ConsoleLogFileConfigPolicy;
import org.junit.Assert;
import org.junit.Test;

public class ConsoleLogFileConfigPolicyTest {
    @Test
    public void exposesConsoleLogRoutingConfigKeys() {
        ConsoleLogFileConfigPolicy policy = ConsoleLogFileConfigPolicy.getInstance();

        Assert.assertEquals("settings.per-day-log-file.enabled", policy.perDayLogFileEnabledKey());
        Assert.assertEquals("settings.per-day-log-file.latest-log.enabled", policy.latestLogEnabledKey());
    }
}
