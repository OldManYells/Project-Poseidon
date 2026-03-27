package com.legacyminecraft.poseidon.world;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Bridge contract for legacy world-data NBT read/write operations.
 */
public interface WorldStorageCompatGateway {
    Object readCompressed(InputStream inputStream);

    void writeCompressed(Object rootTag, OutputStream outputStream);

    Object extractDataTag(Object rootTag);

    Object createWorldData(Object dataTag);

    Object createRootTag();

    void setDataTag(Object rootTag, Object dataTag);
}
