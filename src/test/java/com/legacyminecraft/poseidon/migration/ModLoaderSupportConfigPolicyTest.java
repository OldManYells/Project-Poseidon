package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ModLoaderSupportConfigPolicy;
import org.junit.Assert;
import org.junit.Test;

public class ModLoaderSupportConfigPolicyTest {
    @Test
    public void exposesModLoaderSupportConfigKeyAndDefault() {
        ModLoaderSupportConfigPolicy policy = ModLoaderSupportConfigPolicy.getInstance();

        Assert.assertEquals("settings.support.modloader.enable", policy.modLoaderSupportEnabledKey());
        Assert.assertFalse(policy.modLoaderSupportEnabledDefault());
    }
}
