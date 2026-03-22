package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.SessionLockManager;
import net.minecraft.server.MinecraftException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class SessionLockManagerTest {
    @Test
    public void writesAndVerifiesLockValue() throws Exception {
        File tempDir = createTempDirectory("poseidon-session-lock-ok");
        SessionLockManager manager = SessionLockManager.getInstance();

        manager.writeSessionLock(tempDir, 123L);
        manager.verifySessionLock(tempDir, 123L);
    }

    @Test
    public void throwsWhenLockValueMismatches() throws Exception {
        File tempDir = createTempDirectory("poseidon-session-lock-mismatch");
        SessionLockManager manager = SessionLockManager.getInstance();
        manager.writeSessionLock(tempDir, 123L);

        try {
            manager.verifySessionLock(tempDir, 999L);
            Assert.fail("Expected MinecraftException");
        } catch (MinecraftException expected) {
            Assert.assertTrue(expected.getMessage().contains("save is being accessed"));
        }
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
