package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.WorldIdentityStore;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.UUID;

public class WorldIdentityStoreTest {
    @Test
    public void createsAndReloadsStableWorldUuid() throws Exception {
        File tempDir = createTempDirectory("poseidon-world-identity");
        WorldIdentityStore store = WorldIdentityStore.getInstance();

        UUID first = store.loadOrCreateWorldUuid(tempDir);
        UUID second = store.loadOrCreateWorldUuid(tempDir);

        Assert.assertNotNull(first);
        Assert.assertEquals(first, second);
    }

    private static File createTempDirectory(String prefix) throws Exception {
        File temp = File.createTempFile(prefix, "");
        if (!temp.delete()) {
            throw new IllegalStateException("Failed to prepare temp dir");
        }
        if (!temp.mkdirs()) {
            throw new IllegalStateException("Failed to create temp dir");
        }
        temp.deleteOnExit();
        return temp;
    }
}
