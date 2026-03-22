package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.SandstoneTextureBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class SandstoneTextureServiceTest {
    @Test
    public void textureRulesMatchLegacySideMapping() {
        SandstoneTextureBehaviour service = SandstoneTextureBehaviour.getInstance();

        Assert.assertEquals(176, service.resolveTextureBySide(1, 192));
        Assert.assertEquals(208, service.resolveTextureBySide(0, 192));
        Assert.assertEquals(192, service.resolveTextureBySide(2, 192));
        Assert.assertEquals(192, service.resolveTextureBySide(5, 192));
    }
}
