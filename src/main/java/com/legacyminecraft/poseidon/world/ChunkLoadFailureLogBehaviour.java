package com.legacyminecraft.poseidon.world;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical logging behavior for chunk load/serialization failure paths.
 */
public final class ChunkLoadFailureLogBehaviour {
    private static final ChunkLoadFailureLogBehaviour INSTANCE = new ChunkLoadFailureLogBehaviour();

    private ChunkLoadFailureLogBehaviour() {
    }

    public static ChunkLoadFailureLogBehaviour getInstance() {
        return INSTANCE;
    }

    public void logCorruptChunkRegeneration(Logger logger, int chunkX, int chunkZ, Exception exception) {
        if (logger == null) {
            return;
        }
        logger.log(
                Level.WARNING,
                "Poseidon failed loading chunk (" + chunkX + "," + chunkZ + "); regenerating empty chunk due to emergency setting.",
                exception
        );
    }

    public void logCorruptChunkFailure(Logger logger, int chunkX, int chunkZ, Exception exception) {
        if (logger == null) {
            return;
        }
        logger.log(
                Level.SEVERE,
                "Poseidon failed loading chunk (" + chunkX + "," + chunkZ + "); server may stall unless emergency chunk regeneration is enabled.",
                exception
        );
    }

    public void logChunkCoordinateMismatch(Logger logger, int expectedX, int expectedZ, int actualX, int actualZ, String chunkType) {
        if (logger == null) {
            return;
        }
        logger.warning(
                "Chunk coordinate mismatch: expected (" + expectedX + "," + expectedZ + "), got (" + actualX + "," + actualZ + ") [" + chunkType + "]"
        );
    }

    public void logChunkIoFailure(Logger logger, String operation, Exception exception) {
        if (logger == null) {
            return;
        }
        logger.log(Level.WARNING, "Chunk I/O failure during " + operation, exception);
    }
}
