package com.legacyminecraft.poseidon.world;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

/**
 * Canonical chunk-compression stream behaviour for region file IO.
 */
public final class RegionChunkCompressionBehaviour {
    private static final RegionChunkCompressionBehaviour INSTANCE = new RegionChunkCompressionBehaviour();

    private RegionChunkCompressionBehaviour() {
    }

    public static RegionChunkCompressionBehaviour getInstance() {
        return INSTANCE;
    }

    public DataInputStream createChunkInputStream(byte version, byte[] compressedPayload) throws IOException {
        if (version == 1) {
            return new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(compressedPayload)));
        }

        if (version == 2) {
            return new DataInputStream(new InflaterInputStream(new ByteArrayInputStream(compressedPayload)));
        }

        return null;
    }

    public DataOutputStream createChunkOutputStream(boolean outOfBounds, OutputStream outputStream) {
        if (outOfBounds) {
            return null;
        }

        return new DataOutputStream(new DeflaterOutputStream(outputStream));
    }
}
