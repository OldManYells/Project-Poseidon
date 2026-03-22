package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.WorkbenchStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class WorkbenchStateServiceTest {
    @Test
    public void textureAndInteractionRulesMatchLegacyWorkbenchBehavior() {
        WorkbenchStateBehaviour service = WorkbenchStateBehaviour.getInstance();

        Assert.assertEquals(43, service.resolveTextureBySide(1, 59, 4));
        Assert.assertEquals(4, service.resolveTextureBySide(0, 59, 4));
        Assert.assertEquals(60, service.resolveTextureBySide(2, 59, 4));
        Assert.assertEquals(60, service.resolveTextureBySide(4, 59, 4));
        Assert.assertEquals(59, service.resolveTextureBySide(3, 59, 4));
        Assert.assertTrue(service.shouldIgnoreClientInteraction(true));
        Assert.assertFalse(service.shouldIgnoreClientInteraction(false));
    }
}
