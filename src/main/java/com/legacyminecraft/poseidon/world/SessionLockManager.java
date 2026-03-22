package com.legacyminecraft.poseidon.world;

import net.minecraft.server.MinecraftException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Canonical session-lock file manager for world data access.
 */
public final class SessionLockManager {
    private static final SessionLockManager INSTANCE = new SessionLockManager();

    private SessionLockManager() {
    }

    public static SessionLockManager getInstance() {
        return INSTANCE;
    }

    public void writeSessionLock(File worldDirectory, long lockValue) {
        try {
            File lockFile = new File(worldDirectory, "session.lock");
            DataOutputStream output = new DataOutputStream(new FileOutputStream(lockFile));
            try {
                output.writeLong(lockValue);
            } finally {
                output.close();
            }
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
            throw new RuntimeException("Failed to check session lock, aborting");
        }
    }

    public void verifySessionLock(File worldDirectory, long lockValue) {
        try {
            File lockFile = new File(worldDirectory, "session.lock");
            DataInputStream input = new DataInputStream(new FileInputStream(lockFile));
            try {
                if (input.readLong() != lockValue) {
                    throw new MinecraftException("The save is being accessed from another location, aborting");
                }
            } finally {
                input.close();
            }
        } catch (IOException ioexception) {
            throw new MinecraftException("Failed to check session lock, aborting");
        }
    }
}
