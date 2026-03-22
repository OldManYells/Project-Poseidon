package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ServerWorldSaveService;
import org.junit.Assert;
import org.junit.Test;

public class ServerWorldSaveServiceTest {
    @Test
    public void playerSaveDecisionMatchesPrimaryWorldCanSaveFlag() {
        ServerWorldSaveService service = ServerWorldSaveService.getInstance();

        Assert.assertFalse(service.shouldSavePlayersAfterWorldSave(true));
        Assert.assertTrue(service.shouldSavePlayersAfterWorldSave(false));
    }
}
