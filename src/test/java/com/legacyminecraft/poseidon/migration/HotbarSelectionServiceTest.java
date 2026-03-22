package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.inventory.HotbarSelectionBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class HotbarSelectionServiceTest {
    @Test
    public void validatesHotbarIndexBounds() {
        HotbarSelectionBehaviour service = HotbarSelectionBehaviour.getInstance();

        Assert.assertTrue(service.isValidSelectionIndex(0));
        Assert.assertTrue(service.isValidSelectionIndex(8));
        Assert.assertTrue(service.isValidSelectionIndex(9));
        Assert.assertFalse(service.isValidSelectionIndex(-1));
        Assert.assertFalse(service.isValidSelectionIndex(10));
    }
}
