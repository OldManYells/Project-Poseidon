package com.legacyminecraft.poseidon.world;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

/**
 * Canonical behaviour for RegionFile write-plan commit orchestration and timestamp update flow.
 */
public final class RegionChunkWriteCommitBehaviour {
    private static final RegionChunkWriteCommitBehaviour INSTANCE = new RegionChunkWriteCommitBehaviour();

    private RegionChunkWriteCommitBehaviour() {
    }

    public static RegionChunkWriteCommitBehaviour getInstance() {
        return INSTANCE;
    }

    public void commitWrite(
            int chunkX,
            int chunkZ,
            byte[] compressedPayload,
            int payloadSizeBytes,
            RegionChunkWritePlanBehaviour.WritePlan writePlan,
            List sectorUsage,
            RandomAccessFile regionStream,
            byte[] emptySectorBuffer,
            RegionChunkLocationBehaviour locationBehaviour,
            RegionSectorGrowBehaviour sectorGrowBehaviour,
            RegionSectorMapBehaviour sectorMapBehaviour,
            RegionTimestampBehaviour timestampBehaviour,
            CommitCallbacks callbacks
    ) throws IOException {
        if (writePlan.exceedsSectorLimit()) {
            return;
        }

        int targetSectorOffset = writePlan.getTargetSectorOffset();
        int requiredSectors = writePlan.getRequiredSectors();
        RegionSaveStrategyBehaviour.SaveStrategy saveStrategy = writePlan.getSaveStrategy();

        if (saveStrategy == RegionSaveStrategyBehaviour.SaveStrategy.REWRITE) {
            callbacks.logSave(payloadSizeBytes, "rewrite");
            callbacks.writeChunkPayload(targetSectorOffset, compressedPayload, payloadSizeBytes);
        } else if (saveStrategy == RegionSaveStrategyBehaviour.SaveStrategy.REUSE) {
            callbacks.logSave(payloadSizeBytes, "reuse");
            callbacks.writeChunkLocation(chunkX, chunkZ, locationBehaviour.compose(targetSectorOffset, requiredSectors));
            sectorMapBehaviour.markAllocated(sectorUsage, targetSectorOffset, requiredSectors);
            callbacks.writeChunkPayload(targetSectorOffset, compressedPayload, payloadSizeBytes);
        } else {
            callbacks.logSave(payloadSizeBytes, "grow");
            regionStream.seek(regionStream.length());
            targetSectorOffset = sectorUsage.size();

            sectorGrowBehaviour.writeEmptySectors(regionStream, emptySectorBuffer, requiredSectors);
            sectorMapBehaviour.appendAllocated(sectorUsage, requiredSectors);
            callbacks.incrementWrittenBytes(sectorGrowBehaviour.bytesAddedByGrow(requiredSectors));
            callbacks.writeChunkPayload(targetSectorOffset, compressedPayload, payloadSizeBytes);
            callbacks.writeChunkLocation(chunkX, chunkZ, locationBehaviour.compose(targetSectorOffset, requiredSectors));
        }

        callbacks.writeChunkTimestamp(chunkX, chunkZ, timestampBehaviour.currentUnixTimeSeconds());
    }

    public interface CommitCallbacks {
        void logSave(int payloadSizeBytes, String strategyName);

        void writeChunkPayload(int sectorOffset, byte[] compressedPayload, int payloadSizeBytes) throws IOException;

        void writeChunkLocation(int chunkX, int chunkZ, int packedLocation) throws IOException;

        void writeChunkTimestamp(int chunkX, int chunkZ, int timestampSeconds) throws IOException;

        void incrementWrittenBytes(int bytesAdded);
    }
}
