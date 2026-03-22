package com.legacyminecraft.poseidon.world;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Canonical world identity (uid.dat) persistence.
 */
public final class WorldIdentityStore {
    private static final WorldIdentityStore INSTANCE = new WorldIdentityStore();

    private WorldIdentityStore() {
    }

    public static WorldIdentityStore getInstance() {
        return INSTANCE;
    }

    public UUID loadOrCreateWorldUuid(File worldDirectory) throws IOException {
        File uidFile = new File(worldDirectory, "uid.dat");
        if (!uidFile.exists()) {
            DataOutputStream output = new DataOutputStream(new FileOutputStream(uidFile));
            try {
                UUID uuid = UUID.randomUUID();
                output.writeLong(uuid.getMostSignificantBits());
                output.writeLong(uuid.getLeastSignificantBits());
                return uuid;
            } finally {
                output.close();
            }
        }

        DataInputStream input = new DataInputStream(new FileInputStream(uidFile));
        try {
            return new UUID(input.readLong(), input.readLong());
        } finally {
            input.close();
        }
    }
}
