package com.legacyminecraft.poseidon.world;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Canonical behaviour for RegionFile chunk-read orchestration and validation flow.
 */
public final class RegionChunkReadOrchestrationBehaviour {
    private static final RegionChunkReadOrchestrationBehaviour INSTANCE = new RegionChunkReadOrchestrationBehaviour();

    private RegionChunkReadOrchestrationBehaviour() {
    }

    public static RegionChunkReadOrchestrationBehaviour getInstance() {
        return INSTANCE;
    }

    public ReadOutcome readChunkInputStream(
            int packedLocation,
            int sectorMapSize,
            RandomAccessFile regionHandle,
            RegionChunkLocationBehaviour locationBehaviour,
            RegionChunkReadValidationBehaviour readValidationBehaviour,
            RegionChunkCompressionBehaviour chunkCompressionBehaviour
    ) throws IOException {
        int sectorOffset = locationBehaviour.sectorOffset(packedLocation);
        int sectorCount = locationBehaviour.sectorCount(packedLocation);

        if (readValidationBehaviour.hasInvalidSectorRange(sectorOffset, sectorCount, sectorMapSize)) {
            return ReadOutcome.failure("invalid sector");
        }

        regionHandle.seek((long) (sectorOffset * 4096));
        int payloadLengthBytes = regionHandle.readInt();

        if (readValidationBehaviour.hasInvalidPayloadLength(payloadLengthBytes, sectorCount)) {
            return ReadOutcome.failure(readValidationBehaviour.invalidLengthMessage(payloadLengthBytes, sectorCount));
        }

        byte payloadVersion = regionHandle.readByte();
        byte[] payload = new byte[payloadLengthBytes - 1];
        regionHandle.read(payload);

        DataInputStream inputStream = chunkCompressionBehaviour.createChunkInputStream(payloadVersion, payload);
        if (inputStream == null) {
            return ReadOutcome.failure("unknown version " + payloadVersion);
        }

        return ReadOutcome.success(inputStream);
    }

    public static final class ReadOutcome {
        private final DataInputStream stream;
        private final String failureReason;

        private ReadOutcome(DataInputStream stream, String failureReason) {
            this.stream = stream;
            this.failureReason = failureReason;
        }

        public static ReadOutcome success(DataInputStream stream) {
            return new ReadOutcome(stream, null);
        }

        public static ReadOutcome failure(String failureReason) {
            return new ReadOutcome(null, failureReason);
        }

        public DataInputStream getStream() {
            return stream;
        }

        public String getFailureReason() {
            return failureReason;
        }

        public boolean isSuccessful() {
            return stream != null;
        }
    }
}

