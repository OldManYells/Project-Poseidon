package com.legacyminecraft.poseidon.world;

/**
 * Canonical validation behaviour for reading chunk payloads from region sectors.
 */
public final class RegionChunkReadValidationBehaviour {
    private static final RegionChunkReadValidationBehaviour INSTANCE = new RegionChunkReadValidationBehaviour();

    private RegionChunkReadValidationBehaviour() {
    }

    public static RegionChunkReadValidationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean hasInvalidSectorRange(int sectorOffset, int sectorCount, int sectorMapSize) {
        return sectorOffset + sectorCount > sectorMapSize;
    }

    public boolean hasInvalidPayloadLength(int payloadLengthBytes, int sectorCount) {
        return payloadLengthBytes > 4096 * sectorCount;
    }

    public String invalidLengthMessage(int payloadLengthBytes, int sectorCount) {
        return "invalid length: " + payloadLengthBytes + " > 4096 * " + sectorCount;
    }
}
