package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for legacy LongHashtable chunk-key validation diagnostics.
 */
public final class LongHashtableChunkKeyValidationBehaviour {
    private static final LongHashtableChunkKeyValidationBehaviour INSTANCE =
            new LongHashtableChunkKeyValidationBehaviour();

    private LongHashtableChunkKeyValidationBehaviour() {
    }

    public static LongHashtableChunkKeyValidationBehaviour getInstance() {
        return INSTANCE;
    }

    public void validateChunkCoordinates(int expectedX, int expectedZ, Object value) {
        if (!(value instanceof Chunk)) {
            return;
        }

        Chunk chunk = (Chunk) value;
        if (expectedX != chunk.x || expectedZ != chunk.z) {
            MinecraftServer.log.info(
                    "Chunk (" + chunk.x + ", " + chunk.z + ") stored at  (" + expectedX + ", " + expectedZ + ")"
            );
            Throwable stack = new Throwable();
            stack.fillInStackTrace();
            stack.printStackTrace();
        }
    }
}
